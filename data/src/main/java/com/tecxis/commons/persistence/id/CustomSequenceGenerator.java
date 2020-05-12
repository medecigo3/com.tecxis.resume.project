package com.tecxis.commons.persistence.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.tecxis.resume.StrongEntity;

public class CustomSequenceGenerator extends SequenceStyleGenerator {
	
	public static final String ALLOCATION_SIZE_PARAMETER = "AllocationSize";
	public static final int ALLOCATION_SIZE_DEFAULT = 1;	
	public static final String INITIAL_VALUE_PARAMETER = "InitialValue";
	public static final int INITIAL_VALUE_DEFAULT = 1;
	
	public int allocationSize;
	public int initialValue;

	

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		allocationSize = ConfigurationHelper.getInt(ALLOCATION_SIZE_PARAMETER, params, ALLOCATION_SIZE_DEFAULT);
		initialValue = ConfigurationHelper.getInt(INITIAL_VALUE_PARAMETER, params, INITIAL_VALUE_DEFAULT);
		super.configure(type, params, serviceRegistry);
	}



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
