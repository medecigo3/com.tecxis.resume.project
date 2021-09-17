package com.tecxis.resume.domain.id;

import java.io.Serializable;

/**
 * Defines a generic API to deal with sequential data.
 */
public interface Sequence <T extends Serializable, X>  extends Serializable{
	
	/**@return the internal value.
	 * */	
	T getSequentialValue();
	
	
	/** Generates and sets the internal state by the given input data. 
	 * @param <T> The underlying type of the internal state of the sequence. For instance an integral data type or a {@link java.lang.String String}.
	 * @param <X> The input data type given to generate the sequence, for instance any integral data type or an instance of {@link java.time.LocalDate LocalDate} for time based sequences.
	 * @param summation a series of input items to be summed or added to calculate the internal state of the sequence. It's responsibility of the implementing class to make safe usage of the varargs. 
	 * @see <a href="https://www.geeksforgeeks.org/what-is-heap-pollution-in-java-and-how-to-resolve-it/">What is heap pollution in java and how to resolve it</a> 
	 * @see <a href="https://www.baeldung.com/java-varargs">Varargs in Java</a> 
	 * @see <a href="https://howtodoinjava.com/java/basics/primitive-data-types-in-java/">Java Integral Data Types</a>
	 * */
	T setSequentialValue(X... summation);
	
}
