package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**
 * Defines a generic API to deal with sequential data.* 
 */
public interface Sequence <T extends Serializable, X>  extends Serializable{
	
	/**@return the internal value.
	 * */	
	T getSequentialValue();
	
	
	/** Generates and sets the internal state by the given input data. 
	 * @param <T> the underlying type of the internal state of the sequence, for instance an integral data type or a String to represent an UUID value.
	 * @param <X> the input data type given to generate the sequence, for instance any integral data type or an instance of {@link java.time.LocalDate} for time based sequences.  
	 * The responsibility to make safe usage of the varargs parameters relies on implementing classes. 
	 * @see <a href="https://www.geeksforgeeks.org/what-is-heap-pollution-in-java-and-how-to-resolve-it/">What is heap pollution in java and how to resolve it</a> 
	 * @see <a href="https://www.baeldung.com/java-varargs">Varargs ihn Java</a> 
	 * */
	T setSequentialValue(X... t);
	
}
