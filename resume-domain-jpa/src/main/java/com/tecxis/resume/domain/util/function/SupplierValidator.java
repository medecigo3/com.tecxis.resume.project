package com.tecxis.resume.domain.util.function;

import com.tecxis.resume.domain.Agreement;
import com.tecxis.resume.domain.EmploymentContract;
import com.tecxis.resume.domain.Supplier;
import com.tecxis.resume.domain.SupplyContract;

import java.util.List;
import java.util.function.Function;

import static com.tecxis.resume.domain.util.function.ValidationResult.*;

public interface SupplierValidator extends Function<Supplier, ValidationResult> {//RES-58

    static SupplierValidator isSupplierNameValid(String supplierName){
        return supplier -> supplier.getName().equals(supplierName)? SUCCESS : ValidationResult.SUPPLIER_NAME_IS_NOT_VALID;
    }

    static SupplierValidator areSupplyContractsValid(List<SupplyContract> supplyContracts) {
        SupplierValidator ret = contract -> {
            if (supplyContracts == null || contract.getSupplyContracts() == null)
                return SUPPLIER_SUPPLYCONTRACTS_ARE_NULL;
            else if (supplyContracts.size() != contract.getSupplyContracts().size())
                return SUPPLIER_SIZE_SUPPLYCONTRACTS_ARE_DIFFERENT;
            else
                return supplyContracts.containsAll(contract.getSupplyContracts()) ? SUCCESS : SUPPLIER_SUPPLYCONTRACTS_ARE_NOT_VALID;
        };
        return ret;
    }

    static SupplierValidator areEmploymentContractsValid(List<EmploymentContract> employmentContracts) {
        SupplierValidator ret = supplier -> {
            if (employmentContracts == null || supplier.getEmploymentContracts() == null)
                return SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NULL;
            else if (employmentContracts.size() != supplier.getEmploymentContracts().size())
                return SUPPLIER_SIZE_EMPLOYMENTCONTRACTS_ARE_DIFFERENT;
            else
                return employmentContracts.containsAll(supplier.getEmploymentContracts()) ? SUCCESS : SUPPLIER_EMPLOYMENTCONTRACTS_ARE_NOT_VALID;
        };
        return ret;
    }
}
