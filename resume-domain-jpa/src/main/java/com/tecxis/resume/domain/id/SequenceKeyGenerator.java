package com.tecxis.resume.domain.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** Generic sequence id generator for simple primary keys.
 * *@See simple primary key definition in chapter 2.4 Primary Keys and Entity Identity of the JSR-338 JPA v2.2 specification.
 */
public class SequenceKeyGenerator extends SequenceStyleGenerator {
	
	public static final String ALLOCATION_SIZE_PARAMETER = "AllocationSize";
	public static final int ALLOCATION_SIZE_DEFAULT = 1;	
	public static final String INITIAL_VALUE_PARAMETER = "InitialValue";
	public static final int INITIAL_VALUE_DEFAULT = 1;
	private final static Logger LOG = LoggerFactory.getLogger(SequenceKeyGenerator.class);
	
	public int allocationSize;
	public int initialValue;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		params.put(ALLOCATION_SIZE_PARAMETER, ConfigurationHelper.getInt(ALLOCATION_SIZE_PARAMETER, params, ALLOCATION_SIZE_DEFAULT));
		params.put(INITIAL_VALUE_PARAMETER, ConfigurationHelper.getInt(INITIAL_VALUE_PARAMETER, params, INITIAL_VALUE_DEFAULT));		
		LOG.debug("Configuring sequence generator with params:" +  params);
		super.configure(type, params, serviceRegistry);
	}



	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		LOG.debug("Processing entity: " + object);
		if (object instanceof Identifiable) {			
			LOG.debug("Detected entity of type: " + Identifiable.class);
			Identifiable <?> entity = (Identifiable <?>)object;
			Object id = entity.getId();
			LOG.debug("Processing entity with simple primary key: " + id);
			if (id instanceof Long) {
				LOG.debug("Detected entity with id integral data type: " + Long.class.getName());
				Long sequenceId = (Long)id;
				if ( ((Long)sequenceId).longValue()  > 0L ) {  
					LOG.debug("Entity with sequence id is not null, returning id: [" + id + "]");
		            return sequenceId;  
		        }
				Serializable ret = super.generate(session, object);
				LOG.debug("Generated sequence value: [" + ret + "] for entity: [" + object + "]");
				return ret;
			} else
				throw new NullIdException("Id not an integral data type or id is null: [" + id + "]");
		}
		throw new UnsupportedEntityException("Entity [" + object + "] not instance of [" + Identifiable.class+"]");
	}
}
