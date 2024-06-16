package com.loan.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.loan.binding.CustomerDetailReport;
import com.loan.binding.CustomerReport;
import com.loan.entities.CustomerDetails;
import com.loan.entities.DailyPaidDetails;
import com.loan.repo.CustomerDetailsRepo;
import com.loan.repo.DailyPaidDetailsRepo;
import com.loan.searchcriteria.MySearchCriteria;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDetailsRepo repo;

	@Autowired
	private DailyPaidDetailsRepo drepo;

	@Override
	public boolean registerCustomer(CustomerDetails details) {
		CustomerDetails cd = new CustomerDetails();
		BeanUtils.copyProperties(details, cd);
		cd.setActiveFlag('Y');
		Double totalAmount = calculateTotalAmount(details.getLoanAmount(), details.getIntrest());
		cd.setTotalAmount(totalAmount);
		CustomerDetails save = repo.save(cd);
		return save != null && save.getLoanNo() != null;

	}

	@Override
	public List<CustomerDetails> getAllFromDB() {
		return repo.findAll();
	}

	@Override
	public List<CustomerDetails> filterRecords(MySearchCriteria criteria) {
		CustomerDetails obj = new CustomerDetails();

		if (criteria.getLoanNo() != null && criteria.getLoanNo() != 0) {
			obj.setLoanNo(criteria.getLoanNo());
		}
		if (criteria.getName() != null && !criteria.getName().isEmpty()) {
			obj.setName(criteria.getName());
		}

		Example<CustomerDetails> of = Example.of(obj);

		return repo.findAll(of);
	}

	@Override
	public String getNameByLoanNumber(Integer loanNo) {
		Optional<CustomerDetails> byId = repo.findById(loanNo);
		return byId.isPresent() ? byId.get().getName() : "Invalid Loan No";
	}

	@Override
	public CustomerReport generateCustomerReport(MySearchCriteria criteria) {
		CustomerReport reportObj = new CustomerReport();

		if (criteria != null) {
			CustomerDetails customer = null;

			if (criteria.getLoanNo() != null)
				customer = repo.findByLoanNoAndActiveFlagOrNameAndActiveFlag(criteria.getLoanNo(), 'Y', null, 'Y');
			else if (criteria.getName() != null)
				customer = repo.findByLoanNoAndActiveFlagOrNameAndActiveFlag(null, 'Y', criteria.getName(), 'Y');
			else
				customer = null;

			if (customer != null) {
				BeanUtils.copyProperties(customer, reportObj);
				reportObj.setDaysCrossed(calculateDaysCrossed(customer.getStartDate()));
				Double paidAmount = calculatePaidAmoount(customer.getLoanNo());
				reportObj.setPaidAlready(paidAmount);
				reportObj.setPendingAmount(calculatePendingAmount(customer.getTotalAmount(), paidAmount));
			}
		}
		return reportObj;
	}

	@Override
	public CustomerDetailReport generateCustomerDetailReport(Integer loanNo) {
		Double totalAmount = 0.0;
		CustomerDetailReport detailReport = new CustomerDetailReport();

		CustomerDetails byLoanNoAndActiveFlag = repo.findByLoanNoAndActiveFlag(loanNo, 'Y');

		if (byLoanNoAndActiveFlag != null)
			totalAmount = byLoanNoAndActiveFlag.getTotalAmount();

		List<DailyPaidDetails> all = drepo.findByLoanNoAndActiveFlag(loanNo, 'Y');

		double paidAmount = all.stream().collect(Collectors.summarizingDouble(DailyPaidDetails::getAmount)).getSum();
		detailReport.setData(all);
		detailReport.setTotalAmount(totalAmount);
		detailReport.setPaidAlready(paidAmount);
		detailReport.setPendingAmount(calculatePendingAmount(totalAmount, paidAmount));
		return detailReport;

	}

	@Override
	public boolean modifyActiveFlag(Integer loanNo) {
		CustomerDetails byId = repo.findById(loanNo).get();
		byId.setActiveFlag('N');
		CustomerDetails save = repo.save(byId);

		List<DailyPaidDetails> byLoanNo = drepo.findByLoanNo(loanNo);
		List<DailyPaidDetails> updatedData = byLoanNo.stream().map(e -> {
			e.setActiveFlag('N');
			return e;
		}).collect(Collectors.toList());
		List<DailyPaidDetails> saveAll = drepo.saveAll(updatedData);

		return save.getLoanNo() != null && !saveAll.isEmpty();
	}

	@Override
	public CustomerReport editEndDateFromClousre(Integer loanNo, LocalDate endDate) {
		CustomerDetails customerDetails = repo.findByLoanNoAndActiveFlag(loanNo, 'Y');
		customerDetails.setEndDate(endDate);

		Double paidAmount = calculatePaidAmoount(customerDetails.getLoanNo());
		CustomerReport customerReport = createCustomerReport(customerDetails, paidAmount);

		return repo.save(customerDetails) != null ? customerReport : null;
	}

	private Double calculateTotalAmount(Double amount, Double interest) {
		return amount + ((amount * interest) / 100);
	}

	private Long calculateDaysCrossed(LocalDate startDate) {
		LocalDate presentDate = LocalDate.now();
		return ChronoUnit.DAYS.between(startDate, presentDate);
	}

	private Double calculatePaidAmoount(Integer loanNo) {
		List<DailyPaidDetails> list = drepo.findByLoanNo(loanNo);
		double paidAmount = list.stream().collect(Collectors.summarizingDouble(DailyPaidDetails::getAmount)).getSum();
		return paidAmount;
	}

	private Double calculatePendingAmount(Double totalAmount, Double paidAmount) {

		return totalAmount - paidAmount;
	}

	private CustomerReport createCustomerReport(CustomerDetails customerDetails, Double paidAmount) {
		CustomerReport report = new CustomerReport();
		BeanUtils.copyProperties(customerDetails, report);
		report.setDaysCrossed(calculateDaysCrossed(customerDetails.getStartDate()));
		report.setPaidAlready(paidAmount);
		report.setPendingAmount(customerDetails.getTotalAmount() - paidAmount);
		return report;
	}

}
