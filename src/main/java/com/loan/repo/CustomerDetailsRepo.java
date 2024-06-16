package com.loan.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.entities.CustomerDetails;


public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Serializable> {

	CustomerDetails findByLoanNoAndActiveFlagOrNameAndActiveFlag(Integer loanNo, Character activeFlag, String name,
			Character activeFlag1);
	
	CustomerDetails findByLoanNoAndActiveFlag(Integer loanNo, Character activeFlag);

}
