package com.loan.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DailyPaidDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sno;
	private String date;
	private Integer loanNo;
	private Double amount;
	private Character activeFlag;

}
