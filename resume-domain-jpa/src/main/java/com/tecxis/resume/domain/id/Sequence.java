package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**
 * A Sequence is an object with a generated value. 
 * @param <T> the underlying type of the generated value. An integral data type or a String (an UUID value for instance). 
 */
public interface Sequence <T> extends Serializable{
	
	/**@return the sequence generated value.*/
	public T getSequentialValue();	
	
}
