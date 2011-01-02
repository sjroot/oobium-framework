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
package org.oobium.persist;

import static org.oobium.utils.StringUtils.blank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class ModelAdapter {

	private static final Map<Class<?>, ModelAdapter> adapters = new HashMap<Class<?>, ModelAdapter>();
	
	public static ModelAdapter getAdapter(Class<? extends Model> clazz) {
		if(clazz != null) {
			ModelAdapter adapter = adapters.get(clazz);
			if(adapter == null) {
				synchronized(adapters) {
					adapter = adapters.get(clazz);
					if(adapter == null) {
						adapter = new ModelAdapter(clazz);
						adapter.init();
						adapters.put(clazz, adapter);
					}
				}
			}
			return adapter;
		}
		return null;
	}

	public static ModelAdapter getAdapter(Model model) {
		if(model != null) {
			return getAdapter(model.getClass());
		}
		return null;
	}

	private Class<? extends Model> clazz;
	
	private Map<String, Attribute> attribute;
	private Map<String, Relation> hasOne;
	private Map<String, Relation> hasMany;
	private Set<String> fields;
	private Set<String> modelFields;
	private Set<String> virtualFields;
	private boolean dateStamped;
	private boolean timeStamped;
	private boolean deletable;
	private boolean updatable;

	
	private ModelAdapter(Class<? extends Model> clazz) {
		this.clazz = clazz;
		this.hasOne = new HashMap<String,Relation>();
		this.hasMany = new HashMap<String,Relation>();
		this.fields = new HashSet<String>();
		this.modelFields = new HashSet<String>();
		this.virtualFields = new HashSet<String>();
		this.attribute = new HashMap<String, Attribute>();
	} 

	public Set<String> getAttributeFields() {
		return attribute.keySet();
	}

	public Class<?> getClass(String field) {
		if(attribute.containsKey(field)) {
			return getType(attribute.get(field));
		} else if(hasOne.containsKey(field)) {
			return hasOne.get(field).type();
		} else {
			return getHasManyClass(field);
		}
	}
	
	public String[] getFields() {
		return fields.toArray(new String[fields.size()]);
	}
	
	public Class<?> getHasManyClass(String field) {
		if(hasMany.containsKey(field)) {
			if(isThrough(field) || isManyToNone(field)) {
				return LinkedHashSet.class;
			} else if(isOppositeRequired(field)) {
				return RequiredSet.class;
			} else {
				return ActiveSet.class;
			}
		}
		return null;
	}
	
	public Set<String> getHasManyFields() {
		return hasMany.keySet();
	}
	
	public Class<? extends Model> getHasManyMemberClass(String field) {
		if(hasMany.containsKey(field)) {
			Class<?> clazz = hasMany.get(field).type();
			if(Model.class.isAssignableFrom(clazz)) {
				return clazz.asSubclass(Model.class);
			}
		}
		return null;
	}
	
	public Class<? extends Model> getHasOneClass(String field) {
		if(hasOne.containsKey(field)) {
			Class<?> clazz = hasOne.get(field).type();
			if(Model.class.isAssignableFrom(clazz)) {
				return clazz.asSubclass(Model.class);
			}
		}
		return null;
	}
	
	public Set<String> getHasOneFields() {
		return hasOne.keySet();
	}

	public Class<? extends Model> getModelClass() {
		return this.clazz;
	}
	
	public String[] getModelFields() {
		return modelFields.toArray(new String[modelFields.size()]);
	}
	
	public String getOpposite(String field) {
		String opposite = null;
		if(hasMany.containsKey(field)) {
			opposite = hasMany.get(field).opposite();
		}
		if(hasOne.containsKey(field)) {
			opposite = hasOne.get(field).opposite();
		}
		return blank(opposite) ? null : opposite;
	}
	
	public Class<?> getRawClass(String field) {
		if(attribute.containsKey(field)) {
			return attribute.get(field).type();
		} else if(hasOne.containsKey(field)) {
			return int.class;
		}
		return null;
	}
	
	public Relation getRelation(String field) {
		if(hasOne.containsKey(field)) {
			return hasOne.get(field);
		} else if(hasMany.containsKey(field)) {
			return hasMany.get(field);
		}
		return null;
	}
	
	public Class<? extends Model> getRelationClass(String field) {
		Class<?> clazz = null;
		if(hasOne.containsKey(field)) {
			clazz = hasOne.get(field).type();
		} else if(hasMany.containsKey(field)) {
			clazz = hasMany.get(field).type();
		}
		if(clazz != null && Model.class.isAssignableFrom(clazz)) {
			return clazz.asSubclass(Model.class);
		}
		return null;
	}
	
	public Set<String> getRelations() {
		Set<String> rels = new HashSet<String>();
		rels.addAll(hasOne.keySet());
		rels.addAll(hasMany.keySet());
		return rels;
	}

	public String getThrough(String field) {
		if(hasMany.containsKey(field)) {
			return hasMany.get(field).through();
		}
		return null;
	}

	public Class<? extends Model> getThroughClass(String field) {
		String through = getThrough(field);
		if(through != null) {
			return getRelationClass(through);
		}
		return null;
	}

	private Class<?> getType(Attribute attr) {
		Class<?> type = attr.type();
		if(type == Text.class) {
			return String.class;
		}
		if(type == Binary.class) {
			return byte[].class;
		}
		return type;
	}
	
	public boolean hasAttribute(String field) {
		return attribute.containsKey(field);
	}

	public boolean hasField(String field) {
		return fields.contains(field);
	}

	public boolean hasMany(String field) {
		return hasMany.containsKey(field);
	}

	public boolean hasOne(String field) {
		return hasOne.containsKey(field);
	}

	public boolean hasOpposite(String field) {
		Relation relation = hasMany.get(field);
		if(relation == null) {
			relation = hasOne.get(field);
		}
		if(relation != null) {
			String opposite = relation.opposite();
			if(opposite != null) {
				ModelDescription md = relation.type().getAnnotation(ModelDescription.class);
				if(md != null) {
					for(Relation r : md.hasOne()) {
						if(r.name().equals(opposite)) {
							if(r.type().isAssignableFrom(this.clazz) && r.opposite().equals(field)) {
								return true;
							} else {
								return false;
							}
						}
					}
					for(Relation r : md.hasMany()) {
						if(r.name().equals(opposite)) {
							if(r.type().isAssignableFrom(this.clazz) && r.opposite().equals(field)) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	void init() {
		LinkedList<Class<?>> classes = new LinkedList<Class<?>>();
		Class<?> c = this.clazz;
		while(c != Object.class) {
			classes.addFirst(c);
			c = c.getSuperclass();
		}
		for(Class<?> clazz : classes) {
			ModelDescription description = clazz.getAnnotation(ModelDescription.class);
			if(description != null) {
				for(Attribute attr : description.attrs()) {
					attribute.put(attr.name(), attr);
					fields.add(attr.name());
					if(attr.virtual()) {
						virtualFields.add(attr.name());
					} else {
						modelFields.add(attr.name());
					}
				}
				for(Relation relation : description.hasOne()) {
					hasOne.put(relation.name(), relation);
					fields.add(relation.name());
					modelFields.add(relation.name());
				}
				for(Relation relation : description.hasMany()) {
					hasMany.put(relation.name(), relation);
					fields.add(relation.name());
				}
				if(!dateStamped && description.datestamps()) {
					dateStamped = true;
					attribute.put(ModelDescription.CREATED_ON, ModelAttributes.createdOn);
					attribute.put(ModelDescription.UPDATED_ON, ModelAttributes.updatedOn);
					fields.add(ModelDescription.CREATED_ON);
					fields.add(ModelDescription.UPDATED_ON);
					modelFields.add(ModelDescription.CREATED_ON);
					modelFields.add(ModelDescription.UPDATED_ON);
				}
				if(!timeStamped && description.timestamps()) {
					timeStamped = true;
					attribute.put(ModelDescription.CREATED_AT, ModelAttributes.createdAt);
					attribute.put(ModelDescription.UPDATED_AT, ModelAttributes.updatedAt);
					fields.add(ModelDescription.CREATED_AT);
					fields.add(ModelDescription.UPDATED_AT);
					modelFields.add(ModelDescription.CREATED_AT);
					modelFields.add(ModelDescription.UPDATED_AT);
				}
				deletable = description.allowDelete();
				updatable = description.allowUpdate();
			}
		}
	}

	public boolean isBoolean(String field) throws NoSuchFieldException {
		Class<?> type = getClass(field);
		return type == Boolean.class || type == boolean.class;
	}

	public boolean isDateStamped() {
		return dateStamped;
	}
	
	public boolean isDeletable() {
		return deletable;
	}

	public boolean isIncluded(String field) {
		if(hasOne.containsKey(field)) {
			return hasOne.get(field).include();
		}
		if(hasMany.containsKey(field)) {
			return hasMany.get(field).include();
		}
		return false;
	}
	
	public boolean isInitialized(String field) {
		if(attribute.containsKey(field)) {
			return attribute.get(field).init().length() > 0;
		}
		return false;
	}
	
	public boolean isManyToMany(String field) {
		Relation relation = hasMany.get(field);
		if(relation != null) {
			String opposite = relation.opposite();
			if(opposite != null) {
				ModelDescription md = relation.type().getAnnotation(ModelDescription.class);
				if(md != null) {
					for(Relation r : md.hasMany()) {
						if(r.name().equals(opposite)) {
							if(r.type().isAssignableFrom(this.clazz) && r.opposite().equals(field)) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isManyToNone(String field) {
		Relation relation = hasMany.get(field);
		if(relation != null) {
			return relation.opposite() == null;
		}
		return false;
	}
	
	public boolean isManyToOne(String field) {
		Relation relation = hasMany.get(field);
		if(relation != null) {
			String opposite = relation.opposite();
			if(opposite != null) {
				ModelDescription md = relation.type().getAnnotation(ModelDescription.class);
				if(md != null) {
					for(Relation r : md.hasOne()) {
						if(r.name().equals(opposite)) {
							if(r.type().isAssignableFrom(this.clazz) && r.opposite().equals(field)) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean isOneToNone(String field) {
		Relation relation = hasOne.get(field);
		if(relation != null) {
			return relation.opposite() == null;
		}
		return false;
	}

	public boolean isOppositeRequired(String field) {
		Relation relation = hasMany.get(field);
		if(relation == null) {
			relation = hasOne.get(field);
		}
		if(relation != null) {
			String opposite = relation.opposite();
			if(opposite != null) {
				ModelDescription md = relation.type().getAnnotation(ModelDescription.class);
				if(md != null) {
					for(Relation r : md.hasOne()) {
						if(r.name().equals(opposite)) {
							if(r.type().isAssignableFrom(this.clazz) && r.opposite().equals(field)) {
								return r.required();
							} else {
								return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isReadOnly(String field) {
		if(attribute.containsKey(field)) {
			return attribute.get(field).readOnly();
		}
		if(hasOne.containsKey(field)) {
			return hasOne.get(field).readOnly();
		}
		if(hasMany.containsKey(field)) {
			return hasMany.get(field).readOnly();
		}
		return ModelDescription.ID.equals(field);
	}
	
	public boolean isRequired(String field) {
		if(hasOne.containsKey(field)) {
			return hasOne.get(field).required();
		}
		return field.equals("createdAt") || field.equals("createdOn") || field.equals("updatedAt") || field.equals("updatedOn");
	}

	public boolean isThrough(String field) {
		return !blank(getThrough(field));
	}
	
	public boolean isTimeStamped() {
		return timeStamped;
	}
	
	public boolean isUpdatable() {
		return updatable;
	}
	
	public boolean isVirtual(String field) {
		return virtualFields.contains(field);
	}
	
	@Override
	public String toString() {
		return "ClassAdapter {" + clazz.getCanonicalName() + "}";
	}
	
}
