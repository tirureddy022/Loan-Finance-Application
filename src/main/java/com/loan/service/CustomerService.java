package com.loan.service;

import java.time.LocalDate;
import java.util.List;

import com.loan.binding.CustomerDetailReport;
import com.loan.binding.CustomerReport;
import com.loan.entities.CustomerDetails;
import com.loan.searchcriteria.MySearchCriteria;

public interface CustomerService {

	public boolean registerCustomer(CustomerDetails details);

	public List<CustomerDetails> getAllFromDB();

	public List<CustomerDetails> filterRecords(MySearchCriteria criteria);

	public String getNameByLoanNumber(Integer loanNo);

	public CustomerReport generateCustomerReport(MySearchCriteria criteria);

	public CustomerDetailReport generateCustomerDetailReport(Integer loanNo);

	boolean modifyActiveFlag(Integer loanNo);

	CustomerReport editEndDateFromClousre(Integer loanNo, LocalDate enddate);

}
