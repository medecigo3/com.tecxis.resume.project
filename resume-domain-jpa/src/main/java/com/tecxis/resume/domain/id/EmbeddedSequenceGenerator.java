package com.tecxis.resume.domain.id;

import java.io.Serializable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import com.tecxis.resume.domain.Project;
import com.tecxis.resume.domain.StrongEntity;
/** Supports entities implementing {@link com.tecxis.resume.domain.StrongEntity} with composite primary key implementing {@link com.tecxis.resume.domain.id.SequencedEntityId}  in the need of a DB sequence generator. */
public class EmbeddedSequenceGenerator extends SequenceStyleGenerator {
	
	public static final String ALLOCATION_SIZE_PARAMETER = "AllocationSize";
	public static final int ALLOCATION_SIZE_DEFAULT = 1;	
	public static final String INITIAL_VALUE_PARAMETER = "InitialValue";
	public static final int INITIAL_VALUE_DEFAULT = 1;
	private final static Logger LOG = LogManager.getLogger();
	
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


	/**Entities with sequence id of type Long are only supported, however other integral data types can be easily implemented in the future.*/ 
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {  		
		LOG.debug("Processing entity: " + object);		
		if(object instanceof StrongEntity) {
			LOG.debug("Detected entity of type: " + StrongEntity.class.getName());			
			StrongEntity <?> entity = (StrongEntity<?>) object;			
			Object id = entity.getId();
			LOG.debug("Processing entity's id:" + id);		
			if (id instanceof SequencedEntityId) {
				LOG.debug("Detected entity with composite primary key type: " + SequencedEntityId.class.getName());
				SequencedEntityId <?> sequenceId = (SequencedEntityId <?>) id;			
				if(entity.getId() != null) {
					Object sequencedValue = sequenceId.getSequenceValue();
					if (sequencedValue instanceof Long) {
						LOG.debug("Detected entity with id integral data type: " + Long.class.getName());
						if ( ((Long) sequencedValue).longValue()  > 0L ) {
							LOG.debug("Entity with sequence id is not null, returning id: " + sequenceId);
							return sequenceId;	
						}
						LOG.debug("Generating " + Long.class.getSimpleName() + " sequence id for entity: " + object);
				   	 	long seqValue = ((Number) ((Session) session).createNativeQuery(sequenceCallSyntax).uniqueResult()).longValue();
				   	 	LOG.debug("Generated sequence value: " + seqValue);
				   	 	Project project = (Project)object;   	 	
				   	 	ProjectId projectId = project.getId(); //TODO start refactoring from here...
				   	 	projectId.setProjectId(seqValue); 
				   	 	projectId.setClientId(project.getClient().getId());
				   	 	LOG.debug("Returning id with generated value: " + projectId);				   	 	
				        return projectId;
					} else{
						LOG.debug("Entity with sequence id type not supported: " + sequencedValue.getClass());
					}
					
				}
				throw new IdentifierGenerationException("Cannot determine entity's id integral data type, id is null.");
			}
			throw new IdentifierGenerationException("Enity with unknown id type: " + id.getClass());				
			}  
			throw new IdentifierGenerationException("Unknown entity type: " + object.getClass());
			
	}
}