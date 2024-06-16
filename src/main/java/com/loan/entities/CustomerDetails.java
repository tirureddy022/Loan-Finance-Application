package com.loan.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CustomerDetails  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
