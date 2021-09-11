package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class UnsupportedIdException extends IdentifierGenerationException {
		
	private static final long serialVersionUID = 1L;

	public UnsupportedIdException(String msg) {
		super(msg);
		
	}

	public UnsupportedIdException(String msg, Throwable t) {
		super(msg, t);
	}

}
