package com.loan.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loan.binding.CustomerDetailReport;
import com.loan.binding.CustomerReport;
import com.loan.entities.CustomerDetails;
import com.loan.searchcriteria.MySearchCriteria;
import com.loan.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;

	@GetMapping("/reg")
	public String showRegForm(Model model) {
		model.addAttribute("customer", new CustomerDetails());
		return "regForm";

	}

	@PostMapping("/handlereg")
	public String registerCustomer(CustomerDetails details, Model model) {
		boolean status = service.registerCustomer(details);
		model.addAttribute("status", status ? "Registration Successful" : "Failed to Register, Please try again...");
		model.addAttribute("customer", new CustomerDetails());
		return "regForm";
	}

	@GetMapping("/reports")
	public String showCustomerReportGenarator(Model model, MySearchCriteria criteria) {
		model.addAttribute("custReport", new CustomerReport());
		return "customerreport";
	}

	@GetMapping("/search")
	public String customerReportGenerator(Model model, MySearchCriteria criteria) {
		CustomerReport custReport = service.generateCustomerReport(criteria);
		if (criteria.getLoanNo() == null && criteria.getName() == null) {
			model.addAttribute("custReport", new CustomerReport());
		} else {
			model.addAttribute("custReport", custReport);
		}
		return "customerreport";
	}

	@GetMapping("/detailReport")
	public String showcustomerDetailReportGenerator(Model model) {
		model.addAttribute("detailReportObj", new CustomerDetailReport());

		return "customerDetailReport";
	}

	@GetMapping("/detailReports")
	public String customerDetailReportGenerator(Integer loanNo, Model model) {
		CustomerDetailReport detailReport = service.generateCustomerDetailReport(loanNo);
		model.addAttribute("custReport", detailReport.getData());
		model.addAttribute("paidAlready", detailReport.getPaidAlready());
		model.addAttribute("totalAmount", detailReport.getTotalAmount());
		model.addAttribute("pendingAmount", detailReport.getPendingAmount());
		return "customerDetailReport";
	}

	@GetMapping("/showclousure")
	public String showClousureForm(Model model) {
		model.addAttribute("custReport", new CustomerReport());
		model.addAttribute("searchobj", new MySearchCriteria());
		return "closure";
	}

	@GetMapping("/handleclousure")
	public String closeLoan(@RequestParam("loanNo") Integer loanNo, Model model) {
		service.modifyActiveFlag(loanNo);
		model.addAttribute("custReport", new CustomerReport());
		model.addAttribute("searchobj", new MySearchCriteria());

		return "closure";
	}

	@ResponseBody
	@GetMapping("/nameFromDB")
	public String retiriveNameBasedOnLoanNO(Integer loanNo) {
		return service.getNameByLoanNumber(loanNo);
	}

	@GetMapping("/editEndDate")
	public String modifyEndDate(@RequestParam("loanNo") Integer loanNo, @RequestParam("endDate") LocalDate endDate,
			Model model) {
		CustomerReport report = service.editEndDateFromClousre(loanNo, endDate);
		model.addAttribute("custReport", report);

		MySearchCriteria msc = new MySearchCriteria();
		msc.setLoanNo(loanNo);
		model.addAttribute("searchobj", msc);
		System.out.println(report);
		return "closure";
	}

}
