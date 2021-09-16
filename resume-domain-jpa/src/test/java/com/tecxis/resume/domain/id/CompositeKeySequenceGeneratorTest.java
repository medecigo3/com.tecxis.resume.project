package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.junit.Test;

import com.vladmihalcea.book.hpjp.util.AbstractTest;

public class CompositeKeySequenceGeneratorTest extends AbstractTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[] {
                CityNullId.class,UnsupportedCity.class,UncommonCity.class,AnotherUncommonCity.class,HappyCity.class
        };
    }

    @Test(expected=NullIdException.class)
    public void testNullId() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new CityNullId());
    
        });
    }
    
    @Test(expected=UnsupportedEntityException.class)
    public void testUnsuppportedEntity() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new UnsupportedCity());
    
        });
    }
        
    @Test(expected=UnsupportedSequenceException.class)
    public void testUnsuppportedSequentialIdType() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new UncommonCity());
    
        });
    }
    
    @Test(expected=NullSequenceException.class)
    public void testNullSequentialId() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new AnotherUncommonCity());
    
        });
    }
    
    @Test
    public void testHappyCase() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new HappyCity());
    
        });
    }

    @Entity(name = "CityNullId")
    @Table(name = "CITY_NULL_ID")
    public static class CityNullId implements  Serializable, CompositeIdentifiable  <CityId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CompositeKeySequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = KeySequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = KeySequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
    	@Column(name="CITY_ID")
        private CityId id; //id is null

        public CityNullId() {
        	//id not initialised
        }


        @Override
        public CityId getId() {
            return id;
        }


		@Override
		public void setId(CityId id) {
			this.id = id;		
		}
    }
    
    @Entity(name = "UnsupportedCity")
    @Table(name = "UNSUPPORTED_CITY")
    public static class UnsupportedCity implements  Serializable{
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CompositeKeySequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = KeySequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = KeySequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ")
    	@Column(name="CITY_ID")
        private CityId id;

        public UnsupportedCity() {
        	this.id = new CityId();
        }
        
		public CityId getId() {
			return id;
		}
		
		public void setId(CityId id) {
			this.id = id;
		}	
     
    }
    
    @Entity(name = "UncommonCity")
    @Table(name = "UNCOMMON_CITY")
    public static class UncommonCity implements  Serializable, CompositeIdentifiable  <SequentialId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CompositeKeySequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = KeySequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = KeySequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ") 
        private SequentialId id;
		
		

		public UncommonCity() {
			super();
			this.id = new SequentialId();
		}

		@Override
		public SequentialId getId() {
			return id;
		}

		@Override
		public void setId(SequentialId id) {
			this.id = id;
			
		}
    	
    }
    
    @Embeddable
    public static class SequentialId implements Serializable, Sequence <Float, Float> {

    	private static final long serialVersionUID = 1L;
    	@Column(name="CITY_ID")
    	private Float id;
    	
    	public SequentialId() {
			super();
			this.id = new Float(0.0);
		}
		public Float getId() {
    		return id;
    	}
    	public void setId(Float id) {
    		this.id = id;
    	}
		@Override
		public Float getSequentialValue() {			
			return id;
		}
		@Override
		public Float setSequentialValue(Float... t) {
			this.id = t[0];
			return this.getId();
		}	
    }
    
    @Entity(name = "AnotherUncommonCity")
    @Table(name = "ANOTHER_UNCOMMON_CITY")
    public static class AnotherUncommonCity implements  Serializable, CompositeIdentifiable  <NullSequenceId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CompositeKeySequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = KeySequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = KeySequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ") 
        private NullSequenceId id;
		
		

		public AnotherUncommonCity() {
			super();
			this.id = new NullSequenceId();
		}

		@Override
		public NullSequenceId getId() {
			return id;
		}

		@Override
		public void setId(NullSequenceId id) {
			this.id = id;
			
		}
    	
    }
    
    @Embeddable
    public static class NullSequenceId implements Serializable, Sequence <Long, Long> {

    	private static final long serialVersionUID = 1L;
    	@Column(name="CITY_ID")
    	private Long id; //Sequence not initilized. 	
    	
    	public NullSequenceId() {
			super();//Sequence not initilized. 			
		}
		public Long getId() {
    		return id;
    	}
    	public void setId(Long id) {
    		this.id = id;
    	}
		@Override
		public Long getSequentialValue() {			
			return id;
		}
		@Override
		public Long setSequentialValue(Long... t) {
			this.id = t[0];
			return this.getId();
		}	
    }
    
    @Entity(name = "HappyCity")
    @Table(name = "HAPPY_CITY")
    public static class HappyCity implements  Serializable, CompositeIdentifiable  <HappyCityId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.CompositeKeySequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = KeySequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = KeySequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ") 
        private HappyCityId id;
		
		

		public HappyCity() {
			super();
			this.id = new HappyCityId();
		}

		@Override
		public HappyCityId getId() {
			return id;
		}

		@Override
		public void setId(HappyCityId id) {
			this.id = id;
			
		}
    	
    }
    
    @Embeddable
    public static class HappyCityId implements Serializable, Sequence <Long, Long> {

    	private static final long serialVersionUID = 1L;
    	@Column(name="CITY_ID")
    	private Long id;  	
    	
    	public HappyCityId() {
			super();
			this.id= new Long(0);
		}
		public Long getId() {
    		return id;
    	}
    	public void setId(Long id) {
    		this.id = id;
    	}
		@Override
		public Long getSequentialValue() {			
			return id;
		}
		@Override
		public Long setSequentialValue(Long... t) {
			this.id = t[0];
			return this.getId();
		}	
    }

}
