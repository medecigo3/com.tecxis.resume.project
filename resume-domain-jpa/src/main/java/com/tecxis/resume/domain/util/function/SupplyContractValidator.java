package com.tecxis.resume.domain.util.function;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

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
		SupplyContractValidator ret = supplyContract -> {//RES-60
			if (supplyContract.getContract().equals(contract) != true)
				return SUPPLYCONTRACT_CONTRACT_IS_NOT_VALID;

			else if (supplyContract.getSupplier().equals(supplier) != true)
				return SUPPLYCONTRACT_SUPPLIER_IS_NOT_VALID;
			else
				return SUCCESS;
		};
		return ret;

	}
	
	static SupplyContractValidator isStartDateValid(@NotNull final Date startDate) {
		SupplyContractValidator ret = supplyContract -> {
			final Date supplyContractStartDate = supplyContract.getStartDate();
			logger.debug("Comparing SupplyContract startDate: '" +  supplyContractStartDate + "' vs. param: '" + startDate + "'");

			if (supplyContractStartDate == null && startDate == null )//RES-60
				return SUCCESS;

			else if (supplyContractStartDate != null && startDate != null )//RES-60
				return supplyContractStartDate.compareTo(startDate) == 0 ? SUCCESS : SUPPLYCONTRACT_STARTDATE_NOT_VALID;
			
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
			
			if (supplyContractEndDate != null && endDate == null || supplyContractEndDate == null && endDate != null)
				return SUPPLYCONTRACT_ENDDATE_NOT_VALID;
			
			if (supplyContractEndDate != null && endDate != null )				
				return supplyContractEndDate.compareTo(endDate) == 0 ? SUCCESS : SUPPLYCONTRACT_ENDDATE_NOT_VALID;			
			
			else				
				return SUPPLYCONTRACT_ENDDATE_NOT_VALID;
			
				
		};
		return ret; 
	}
}
