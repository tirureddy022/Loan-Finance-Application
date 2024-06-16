package com.loan.binding;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DailyEntryFormBinding {

	private Integer sno;
	private String date;
	private Integer loanNo;
	private String name;
	private Double amount;
}
