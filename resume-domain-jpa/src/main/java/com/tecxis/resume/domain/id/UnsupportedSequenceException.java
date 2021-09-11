package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class UnsupportedSequenceException extends IdentifierGenerationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedSequenceException(String msg) {
		super(msg);
	}

	public UnsupportedSequenceException(String msg, Throwable t) {
		super(msg, t);	
	}

}
