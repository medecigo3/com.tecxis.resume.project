package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class NullIdException extends IdentifierGenerationException {

	private static final long serialVersionUID = 1L;

	public NullIdException(String msg) {
		super(msg);

	}

	public NullIdException(String msg, Throwable t) {
		super(msg, t);
	
	}

}
