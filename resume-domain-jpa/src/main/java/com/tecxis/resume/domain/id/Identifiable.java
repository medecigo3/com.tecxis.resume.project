package com.tecxis.resume.domain.id;

/**Simple primary key. 
 * @param <T> the underlying type of the id. */
public interface Identifiable <T>{
	
	public T getId();
	
	public void setId(T id);
}
