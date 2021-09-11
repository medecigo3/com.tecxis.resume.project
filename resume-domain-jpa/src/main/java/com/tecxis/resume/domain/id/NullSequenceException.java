package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class NullSequenceException extends IdentifierGenerationException {

	private static final long serialVersionUID = 1L;

	public NullSequenceException(String msg) {
		super(msg);

	}

	public NullSequenceException(String msg, Throwable t) {
		super(msg, t);
	
	}

}
