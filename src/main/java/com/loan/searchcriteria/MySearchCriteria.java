package com.loan.searchcriteria;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MySearchCriteria {

	private Integer loanNo;
	private String name;

}
