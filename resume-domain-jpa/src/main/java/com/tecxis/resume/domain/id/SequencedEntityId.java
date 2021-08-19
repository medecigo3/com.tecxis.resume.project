package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**
 * An entity dedicated primary key object with a sequence generated value. 
 * @param <T> the underlying type of the entity id. An integral data type or a String for instance for an UUID value. 
 */
public interface SequencedEntityId <T> extends Serializable{
	
	/**@return the sequence generated value.*/
	public T getSequenceValue();	
	
}
