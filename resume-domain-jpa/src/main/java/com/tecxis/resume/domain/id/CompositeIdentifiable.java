package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**Composite primary key. 
 * @param <T> the underlying type of the dedicated id object. */
public interface CompositeIdentifiable   <T extends Sequence <? extends Serializable, ?>>{
	
	public T getId();
	
	public void setId(T id);
}
