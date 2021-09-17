package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**Composite primary key. 
 * @param <T> the underlying type of the id object. */
public interface CompositeIdentifiable   <T extends Sequence <? extends Serializable, ?>>{
	
	/** 
	 * @return the id object.
	 */
	public T getId();
	
	/**
	 * @param the id object.
	 */
	public void setId(T id);
}
