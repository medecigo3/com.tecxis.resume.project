package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.SUCCESS;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_ENDDATE_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_IS_NOT_VALID;
import static com.tecxis.resume.domain.util.function.ValidationResult.SUPPLYCONTRACT_STARTDATE_NOT_VALID;

import java.util.Date;
import java.util.function.Function;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecxis.resume.domain.Contract;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;

@FunctionalInterface
public interface SupplyContractValidator extends Function<SupplyContract, ValidationResult> {
	Logger logger = LoggerFactory.getLogger(SupplyContractValidator.class);
	
	static SupplyContractValidator isSupplyContractValid(Supplier supplier, Contract contract) {
		return supplyContract -> supplyContract.getSupplier().equals(supplier) && supplyContract.getContract().equals(contract) ? SUCCESS : SUPPLYCONTRACT_IS_NOT_VALID;
	}
	
	static SupplyContractValidator isStartDateValid(@NotNull final Date startDate) {
		SupplyContractValidator ret = supplyContract -> {
			logger.debug("Comparing SupplyContract startDate: '" +  supplyContract.getStartDate() + "' vs. param: '" + startDate + "'");
			
			if (supplyContract.getStartDate() != null && startDate != null )
				return supplyContract.getStartDate().compareTo(startDate) == 0 ? SUCCESS : SUPPLYCONTRACT_STARTDATE_NOT_VALID;
			
			else
				
				return SUPPLYCONTRACT_STARTDATE_NOT_VALID;
		};
		return ret;
	}
	
	static SupplyContractValidator isEndDateValid(@Null final Date endDate) {
		SupplyContractValidator ret  = supplyContract -> {
			final Date supplyContractEndDate = supplyContract.getEndDate();
			logger.debug("Comparing SupplyContract endDate: '" +  supplyContractEndDate + "' vs. param: '" + endDate + "'");
			
			if (supplyContractEndDate == null && endDate == null )			
				return SUCCESS;			
			
			if (supplyContractEndDate != null && endDate != null )				
				return supplyContractEndDate.compareTo(endDate) == 0 ? SUCCESS : SUPPLYCONTRACT_ENDDATE_NOT_VALID;			
			
			else				
				return SUPPLYCONTRACT_ENDDATE_NOT_VALID;
			
				
		};
		return ret; 
	}
}
