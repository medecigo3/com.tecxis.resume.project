SELECT
    contract_id,
    contract,
    customer,
    supplier,
    role,
    start_date,
    end_date
    
FROM
    "v_Resume"
group by (contract_id, contract, customer, supplier, role, start_date, end_date)