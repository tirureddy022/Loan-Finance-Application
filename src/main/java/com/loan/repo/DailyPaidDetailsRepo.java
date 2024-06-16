package com.loan.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.entities.DailyPaidDetails;

public interface DailyPaidDetailsRepo extends JpaRepository<DailyPaidDetails, Serializable> {

	List<DailyPaidDetails> findByLoanNo(Integer loanNo);

	List<DailyPaidDetails> findByLoanNoAndActiveFlag(Integer loanNo, Character activeFlag);
}
