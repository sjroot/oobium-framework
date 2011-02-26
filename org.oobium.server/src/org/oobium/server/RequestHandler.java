/*******************************************************************************
 * Copyright (c) 2010 Oobium, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jeremy Dowdall <jeremy@oobium.com> - initial API and implementation
 ******************************************************************************/
package org.oobium.server;

import static org.oobium.http.constants.RequestType.DELETE;
import static org.oobium.http.constants.RequestType.GET;
import static org.oobium.http.constants.RequestType.HEAD;
import static org.oobium.http.constants.RequestType.PUT;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

import org.oobium.http.HttpRequest;
import org.oobium.http.HttpRequest404Handler;
import org.oobium.http.HttpRequest500Handler;
import org.oobium.http.HttpRequestHandler;
import org.oobium.http.HttpResponse;
import org.oobium.http.constants.Header;
import org.oobium.http.constants.RequestType;
import org.oobium.http.impl.Headers;
import org.oobium.logging.Logger;
import org.oobium.logging.LogProvider;

class RequestHandler implements Runnable {

	private final Logger logger;
	
	private final ServerSelector selector;
	private final SelectionKey key;
	private final Data data;

	RequestHandler(ServerSelector selector, SelectionKey key, Data data) {
		logger = LogProvider.getLogger(Server.class);
		this.selector = selector;
		this.key = key;
		this.data = data;
	}

	private Request createRequest(Data data) {
		String fullPath = data.getPath();
		if(fullPath.length() > 1 && fullPath.charAt(fullPath.length()-1) == '/') {
			fullPath = fullPath.substring(0, fullPath.length()-1);
		}
		
		String[] sa2 = fullPath.split("\\?", 2);
		String path = sa2[0];
		if(path.length() > 1 && path.charAt(path.length()-1) == '/') {
			path = path.substring(0, path.length()-1);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		if(sa2.length > 1) {
			String query = sa2[1];
			for(String s : query.split("&")) {
				String[] sa3 = s.split("=", 2);
				if(sa3.length == 2) {
					try {
						parameters.put(sa3[0].trim(), URLDecoder.decode(sa3[1].trim(), "UTF-8"));
					} catch(UnsupportedEncodingException e) {
						logger.equals(e);
					}
				} else {
					parameters.put(sa3[0].trim(), null);
				}
			}
		}
		
		String line;
		data.goToHeaders();
		Headers headers = new Headers();
		while((line = data.readLine()) != null && line.length() > 0) {
			headers.add(line);
		}

		RequestType type;
		String headerType = headers.get(Header.METHOD);
		if(HttpRequest.DELETE_METHOD.equals(headerType)) {
			type = DELETE;
		} else if(HttpRequest.PUT_METHOD.equals(headerType)) {
			type = PUT;
		} else {
			type = data.type;
		}
		
		Request request = new Request(type, path, fullPath, headers, parameters);
		if(GET != request.getType() || HEAD != request.getType()) { // allow DELETE?
			request.setInput(data);
		}

		request.setIpAddress(data.remoteIpAddress);
		request.setPort(data.localPort);
		
		return request;
	}

	private HttpResponse handle404(HttpRequest request) {
		for(HttpRequest404Handler handler : selector.getRequest404Handlers(request.getPort())) {
			HttpResponse response = handler.handle404(request);
			if(response != null) {
				return response;
			}
		}
		return null;
	}
	
	private HttpResponse handle500(HttpRequest request, Exception exception) {
		for(HttpRequest500Handler handler : selector.getRequest500Handlers(request.getPort())) {
			HttpResponse response = handler.handle500(request, exception);
			if(response != null) {
				return response;
			}
		}
		return null;
	}
	
	private HttpResponse handleRequest(Request request) throws Exception {
		for(HttpRequestHandler handler : selector.getRequestHandlers(request.getPort())) {
			request.setHandler(handler);
			HttpResponse response = handler.handleRequest(request);
			if(response != null) {
				return response;
			}
		}
		return null;
	}
	
	public void run() {
		Request request = null;
		try {
			request = createRequest(data);
			if(logger.isLoggingInfo()) {
				logger.info("request: " + data.remoteIpAddress + " -> " + request.getType() + " " + request.getHost() + request.getFullPath() +
							" referer: " + request.getHeader(Header.REFERER));
			}
		} catch(Exception e) {
			logger.warn("error creating request: " + data.toString());
			logger.warn(e);
			return; // could not create request, exit
		}
		
		HttpResponse response = null;
		try {
			response = handleRequest(request);
		} catch(Exception e) {
			logger.warn("exception thrown handling request (" + request.getFullPath() + ")", e);
			try {
				response = handle500(request, e);
			} catch(Exception e2) {
				logger.warn("error handling 500", e2);
				return; // just exit
			}
		}

		if(response == null) {
			if(logger.isLoggingInfo()) {
				logger.info(request.getFullPath() + " not found");
			}
			try {
				response = handle404(request);
			} catch(Exception e) {
				logger.warn("error handling 404", e);
				return; // just exit
			}
		}

		try {
			selector.send(key, response);
		} catch(Exception e) {
			logger.warn(e);
		}
	}

}
