package com.tecxis.resume.domain.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.junit.Test;

import com.tecxis.resume.domain.Country;
import com.vladmihalcea.book.hpjp.util.AbstractTest;

public class SequenceKeyGeneratorTest extends AbstractTest {

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[] {
                CountryNullId.class,UnsupportedCountry.class
        };
    }
    
 
    @Test(expected=NullIdException.class)
    public void testNullId() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new CountryNullId());
    
        });
    }
    
    @Test(expected=UnsupportedEntityException.class)
    public void testUnsupportedEntity() throws Throwable {       
        doInJPA(entityManager -> {
            entityManager.persist(new UnsupportedCountry());
    
        });
    }

    @Entity(name = "countryNullId")
    @Table(name = "countryNullId")
    public static class CountryNullId implements  Serializable, Identifiable  <Long> {
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="COUNTRY_SEQ", 
    	 parameters = {
    	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTRY_SEQ")
    	@Column(name="COUNTRY_ID")
        private Long id; //id is null

        public CountryNullId() {
        	//id not initialised
        }

		@Override
		public Long getId() {	
			return id;
		}

		@Override
		public void setId(Long id) {
				this.id = id;
			
		}     
    }
    
    @Entity(name = "UnsupportedCountry")
    @Table(name = "unsupportedCountry")
    public static class UnsupportedCountry implements  Serializable{
		private static final long serialVersionUID = 1L;
		@Id
    	@GenericGenerator(strategy="com.tecxis.resume.domain.id.SequenceKeyGenerator", name="COUNTRY_SEQ", 
    	 parameters = {
    	            @Parameter(name = SequenceKeyGenerator.ALLOCATION_SIZE_PARAMETER, value = "1"),
    	            @Parameter(name = SequenceKeyGenerator.INITIAL_VALUE_PARAMETER, value = "1")}
    	)
    	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COUNTRY_SEQ")
    	@Column(name="COUNTRY_ID")
        private Long id;

        public UnsupportedCountry() {
        	this.id = new Long(0);
        }
        
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}		
     
    }

}
