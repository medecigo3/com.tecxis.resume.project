package com.tecxis.commons.persistence.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.tecxis.resume.StrongEntity;

public class SequenceGenerator extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		if (object instanceof StrongEntity) {
			
			StrongEntity strongEntity = (StrongEntity)object;	        
			if (strongEntity.getId() > 0L) {  
	            return strongEntity.getId();  
	        }
			else
				return super.generate(session, object);
			}					
		   	
	    return super.generate(session, object);

	}


	

}
