package com.tecxis.resume.domain;

/**An entity with a simple or a composite primary key. 
 * @param <T> the underlying type of the entity's id. An integral data type for simple id or a dedicated object for a composite id. */
public interface StrongEntity <T>{
	
	public T getId();
	
	public void setId(T id);
	
	

}
