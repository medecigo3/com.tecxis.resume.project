package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class UnsupportedEntityException extends IdentifierGenerationException {

	private static final long serialVersionUID = 1L;

	public UnsupportedEntityException(String msg) {
		super(msg);
	
	}

	public UnsupportedEntityException(String msg, Throwable t) {
		super(msg, t);
		
	}

}
