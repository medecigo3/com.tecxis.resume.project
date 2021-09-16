package com.tecxis.resume.domain.id;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** Generic sequence id generator for composite primary keys.*/
public class EmbeddedSequenceGenerator <T extends Serializable> extends SequenceStyleGenerator {
	
	public static final String ALLOCATION_SIZE_PARAMETER = "AllocationSize";
	public static final int ALLOCATION_SIZE_DEFAULT = 1;	
	public static final String INITIAL_VALUE_PARAMETER = "InitialValue";
	public static final int INITIAL_VALUE_DEFAULT = 1;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private String sequenceCallSyntax;
	

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		params.put(ALLOCATION_SIZE_PARAMETER, ConfigurationHelper.getInt(ALLOCATION_SIZE_PARAMETER, params, ALLOCATION_SIZE_DEFAULT));
		params.put(INITIAL_VALUE_PARAMETER, ConfigurationHelper.getInt(INITIAL_VALUE_PARAMETER, params, INITIAL_VALUE_DEFAULT));		
		JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
		Dialect dialect = jdbcEnvironment.getDialect();
		final String sequencePerEntitySuffix = ConfigurationHelper.getString(SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, params, SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX);
        boolean preferSequencePerEntity = ConfigurationHelper.getBoolean(SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, params, false);
		final String defaultSequenceName = preferSequencePerEntity ? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix : ConfigurationHelper.getString(SequenceStyleGenerator.GENERATOR_NAME, params);
		sequenceCallSyntax = dialect.getSequenceNextValString(ConfigurationHelper.getString(SequenceStyleGenerator.SEQUENCE_PARAM, params, defaultSequenceName));
		LOG.debug("Configuring sequence generator with params:" +  params);
		super.configure(type, params, serviceRegistry);
	}


	/**Generates and sets the new sequence to the composite primary key. 
	 * @param object the entity implementing {@link com.tecxis.resume.domain.id.Identifiable} with a composite primary key implementing {@link com.tecxis.resume.domain.id.Sequence}.
	 * @return and instance of {@link com.tecxis.resume.domain.id.Sequence} with the new sequence. 
	 * @throws UnsupportedSequenceDataTypeException if id sequence data type is different from {@link java.lang.Long}. 
	 * */	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {  		
		LOG.debug("Processing entity: " + object);		
		if(object instanceof Identifiable) {
			LOG.debug("Detected entity of type: " + Identifiable.class.getName());			
			Identifiable <?> entity = (Identifiable<?>) object;			
			Object id = entity.getId();
			if(id != null) {
				LOG.debug("Processing entity's id:" + id);		
				if (id instanceof Sequence) {
					LOG.debug("Detected entity with composite primary key type: " + Sequence.class.getName());
					Sequence <T, Long> sequenceId  = null;
					try {
						sequenceId = (Sequence <T,Long>) id;
					} catch (ClassCastException e) {
						throw new UnsupportedSequenceDataTypeException("Sequence data type not supported: " + id, e); //TODO test with other unsupported sequence data type, for instance String.
					}
						Object sequentialValue = sequenceId.getSequentialValue();
						if (sequentialValue != null) {
							if (sequentialValue instanceof Long) {
								LOG.debug("Detected entity with id integral data type: " + Long.class.getName());
								if ( ((Long) sequentialValue).longValue()  > 0L ) {
									LOG.debug("Entity with sequence id is not null, returning id: " + sequenceId);
									return sequenceId;	
								}
								LOG.debug("Generating " + Long.class.getSimpleName() + " sequence in DB for the entity: " + object);
						   	 	long seqValue = ((Number) ((Session) session).createNativeQuery(sequenceCallSyntax).uniqueResult()).longValue();
						   	 	LOG.debug("Integral DB generated sequence value is: " + seqValue);
						   	 	sequenceId.setSequentialValue(seqValue);
						   	 	LOG.debug("Returning id with generated sequence value: [" + sequenceId + "]");
						   	 	return sequenceId;
							}
							throw new UnsupportedSequenceException("Entity with sequential type [" + sequentialValue.getClass() +"]  not supported." );
						}
						throw new NullSequenceException("Cannot determine entity's id integral data type: id is null.");
			
			}
			throw new UnsupportedIdException("Id [" + id + "] not instance of "+Sequence.class+" for entity [" + entity + "]");
			}
			throw new NullIdException("Cannot determine entity's id integral data type, id is null.");
			}  
			throw new UnsupportedEntityException("Entity type [" + object + "] not an instance of [" + Identifiable.class+"]");
			
	}
}