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

public class EmbeddedSequenceGeneratorTest extends AbstractTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[] {
                CityNullId.class,UnsupportedCity.class,CommonCity.class,UncommonCity.class,AnotherUncommonCity.class
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
    
    @Test(expected=UnsupportedIdException.class)
    public void testUnsuppportedId() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new CommonCity());
    
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

    @Entity(name = "CityNullId")
    @Table(name = "CITY_NULL_ID")
    public static class CityNullId implements  Serializable, Identifiable  <CityId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
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
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
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
    
    @Entity(name = "CommonCity")
    @Table(name = "COMMON_CITY")
    public static class CommonCity implements  Serializable, Identifiable  <UnsupportedCityId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CITY_SEQ") 
        private UnsupportedCityId id;
		
		

		public CommonCity() {
			super();
			this.id = new UnsupportedCityId();
		}

		@Override
		public UnsupportedCityId getId() {
			return id;
		}

		@Override
		public void setId(UnsupportedCityId id) {
			this.id = id;
			
		}
    	
    }
    
    @Embeddable
    public static class UnsupportedCityId implements Serializable {

    	private static final long serialVersionUID = 1L;
    	@Column(name="CITY_ID")
    	private long id;
    	public long getId() {
    		return id;
    	}
    	public void setId(long id) {
    		this.id = id;
    	}	
    }
    
    @Entity(name = "UncommonCity")
    @Table(name = "UNCOMMON_CITY")
    public static class UncommonCity implements  Serializable, Identifiable  <SequentialId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
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
    public static class SequentialId implements Serializable, Sequence <String> {

    	private static final long serialVersionUID = 1L;
    	@Column(name="CITY_ID")
    	private String id;
    	
    	public SequentialId() {
			super();
			this.id = "";
		}
		public String getId() {
    		return id;
    	}
    	public void setId(String id) {
    		this.id = id;
    	}
		@Override
		public String getSequentialValue() {			
			return id;
		}	
    }
    
    @Entity(name = "AnotherUncommonCity")
    @Table(name = "ANOTHER_UNCOMMON_CITY")
    public static class AnotherUncommonCity implements  Serializable, Identifiable  <NullSequenceId> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.EmbeddedSequenceGenerator", name="CITY_SEQ", 
    	 parameters = {
    	            @Parameter(name = CustomSequenceGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = CustomSequenceGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
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
    public static class NullSequenceId implements Serializable, Sequence <Long> {

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
    }

}
