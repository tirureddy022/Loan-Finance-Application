package com.loan.binding;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@Component
@ToString
public class CustomerReport {

	private Integer loanNo;
	private String name;
	private String location;
	private Long phno;
	private String gender;
	private LocalDate startDate;
	private LocalDate endDate;
	private String loanType;
	private Double loanAmount;
	private Double intrest;
	private Double totalAmount;
	private Character activeFlag;
	
	private Long daysCrossed;
	private Double paidAlready;
	private Double pendingAmount;

}
