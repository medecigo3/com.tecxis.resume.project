package com.tecxis.resume.domain.id;

import org.hibernate.id.IdentifierGenerationException;

public class UnsupportedSequenceDataTypeException extends IdentifierGenerationException {
	private static final long serialVersionUID = 1L;

	public UnsupportedSequenceDataTypeException(String arg0) {
		super(arg0);
		
	}

	public UnsupportedSequenceDataTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
