function $Extend(clazz, superClass) {
	function inheritance() {}
	inheritance.prototype = superClass.prototype;
	clazz.prototype = new inheritance();
	clazz.prototype.constructor = clazz;
	clazz.superConstructor = superClass;
	clazz.superClass = superClass.prototype;
}

function Monitor(interval) {
	var self = this;
	var commitInterval = interval || 0;
	var operations = [];
	var waiting = false;
	var timerId = null;
	var callbacks = {
		queue: $.Callbacks(),
		start: $.Callbacks(),
		complete: $.Callbacks()
	}
	
	this.add = function(request) {
		if(operations.length == 0) {
			fire('queue');
		}
		startTimer();
		var op;
		for(var i in operations) {
			var r = operations[i].req;
			if(r.type == request.type && r.url == request.url) {
				op = operations[i];
				break;
			}
		}
		if(op) {
			$.extend(true, op.req.data, request.data);
		} else {
			op = {
					dfd: $.Deferred(),
					fnc: function() {
						$.ajax(this.req).always( this.dfd.resolve );
					},
					req: request
			};
			operations.push(op);
		}
		return op.dfd.promise();
	};

	this.auto = function() {
		return commitInterval > 0;
	};
	
	var clearTimer = function() {
		if(timerId) {
			clearTimeout(timerId);
		}
	};
	
	this.commit = function() {
		clearTimer();
		if(operations.length > 0) {
			var deferreds = [];
			for(var i in operations) {
				var op = operations[i];
				op.fnc();
				deferreds.push(op.dfd);
			}
			$.when.apply($, deferreds)
				.then(function() {
					fire('complete');
				});
			operations = [];
			fire('start');
		} else {
			fire('start');
			fire('complete');
		}
	};

	this.complete = function(callback) {
		callbacks.complete.add(callback);
	};
	
	this.dirty = function() {
		return operations.length > 0;
	};

	var fire = function(eventName) {
		callbacks[eventName].fire(self);
	}
	
	this.queue = function(callback) {
		callbacks.queue.add(callback);
	};
	
	this.setInterval = function(interval) {
		if(interval) {
			commitInterval = parseInt(interval);
			if(operations.length > 0) {
				startTimer();
			}
		} else {
			clearTimer();
		}
	};
	
	this.start = function(callback) {
		callbacks.start.add(callback);
	};
	
	var startTimer = function() {
		clearTimer();
		if(commitInterval > 0) {
			timerId = setTimeout(self.commit, commitInterval);
		}
	};
	
	this.subscribe = function(eventName, callback) {
		callbacks[eventName].add(callback);
	};
	
	this.unsubscribe = function(eventName, callback) {
		callbacks[eventName].remove(callback);
	};
	
}

var $Monitor = new Monitor($oobenv.commitInterval || 3000);


var $Router = {
	routesUrl: '/',
	
	loadRoutes: function() {
		$.ajax({
			type: 'HEAD',
			async: true,
			url: routesUrl,
			complete: function(xhr, status) {
				if('success'.equals(status)) {
					var api = xhr.getResponseHeader('API-Location');
					if(api) {
						$.getJSON(api, function(data) {
							$oobenv.routes = data;
						});
						return;
					}
				}
				alert('error loading routes');
			}
		});
	},
	
	getType: function(action, model) {
		return $oobenv.routes[model.type || model][action]['method'];
	},
	
	getPath: function(action, model, plural, id) {
		var modelType, modelPlural, modelId;
		if(plural) {
			modelType = model;
			modelPlural = plural;
			modelId = id;
		} else {
			modelType = model.type;
			modelPlural = model.plural;
			modelId = model.id;
		}
		var route = $oobenv.routes[modelType][action];
		var path = route['path'];
		if(route['fixed']) {
			return path;
		}
		path = path.replace("{models}", modelPlural);
		path = path.replace("{id}", modelId);
		return path;
	}
}


function Model(params) {
	this.data = {};
	if(typeof params == 'number' || typeof params == 'string' ) {
		this.id = params;
	} else {
		if(params) {
			if(params.id) {
				this.id = params.id;
				delete params.id;
			}
			for(var key in params) {
				if(params.hasOwnProperty(key)) {
					var val = params[key];				
					if(typeof val == 'string' && val.indexOf('/Date(') == 0) {
						this.data[key] = new Date(parseInt(val.substring(6, val.length-2)));
					} else {
						this.data[key] = val;
					}
				}
			}
		}
	}
}

<Model.newInstance>

Model.find = function(params) {
	var request = {};
	request.type = $Router.getType('show', params.type);
	request.url = $Router.getPath('show', params.type, params.plural, params.id);
	request.dataType = 'json';
	request.success = function(data, status, xhr) {
		var model = Model.newInstance(params.type, data);
		if(params.success) params.success(model, status, xhr);
	}
	if(params.error) {
		request.error = function(xhr, status, errorThrown) {
			params.error(xhr, status, errorThrown);
		}
	}
	$.ajax(request);
}

Model.findAll = function(params) {
	var request = {};
	request.type = $Router.getType('showAll', params.type);
	request.url = $Router.getPath('showAll', params.type, params.plural);
	request.dataType = 'json';
	request.success = function(data, status, xhr) {
		var models = [];
		for(var i in data) {
			models.push(Model.newInstance(params.type, data[i]));
		}
		if(params.success) params.success(models, status, xhr);
	}
	if(params.error) {
		request.error = function(xhr, status, errorThrown) {
			params.error(xhr, status, errorThrown);
		}
	}
	$.ajax(request);
}

/**
 * @return the jqXHR object (see JQuery $.ajax for more information)
 */
Model.prototype.create = function() {
	var model = this;
	var request = {};
	request.type = $Router.getType('create', model);
	request.url = $Router.getPath('create', model),
	request.data = {};
	request.data[model.varName] = model.getJsonData();
	request.dataType = 'json';
	request.success = function(data, status, xhr) {
		model.id = xhr.getResponseHeader('id');
	}
	if($Monitor && $Monitor.auto()) {
		return $Monitor.add(request);
	} else {
		return $.ajax(request).promise();
	}
}

/**
 * @return a Promise object
 */
Model.prototype.destroy = function() {
	var model = this;
	var request = {};
	request.type = $Router.getType('destroy', model);
	request.url = $Router.getPath('destroy', model),
	request.dataType = 'json';
	request.success = function(data, status, xhr) {
		model.id = 0;
		model.data = null;
		model.destroyed = true;
	}
	return $.ajax(request);
}

Model.prototype.getJsonData = function() {
	var jdata = {};
	for(var key in this.data) {
		if(this.data.hasOwnProperty(key)) {
			var val = this.data[key];				
			if(val instanceof Date) {
				jdata[key] = '/Date(' + val.getTime() + ')/';
			} else {
				jdata[key] = val;
			}
		}
	}
	return jdata;
}

Model.prototype.retrieve = function(success, error) {
	var model = this;
	var request = {};
	request.type = $Router.getType('show', this);
	request.url = $Router.getPath('show', this),
	request.dataType = 'json';
	request.success = function(data, status, xhr) {
		model.data = data;
		if(success) success(model, status, xhr);
	}
	if(error) {
		request.error = function(data, status, xhr) {
			error(data, status, xhr);
		}
	}
	$.ajax(request);
}

/**
 * @return a Promise object
 */
Model.prototype.update = function() {
	var request = {};
	request.type = $Router.getType('update', this);
	request.url = $Router.getPath('update', this),
	request.data = {};
	request.data[this.varName] = this.getJsonData();
	request.dataType = 'json';
	if($Monitor && $Monitor.auto() > 0) {
		return $Monitor.add(request);
	} else {
		return $.ajax(request).promise();
	}
}

/**
 * @return a Promise object
 */
Model.prototype.save = function() {
	return (this.id == 0) ? this.create() : this.update();
}
