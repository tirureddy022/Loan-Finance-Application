package com.loan.binding;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataStatastics {

	private double sum;
	private long count;
	private String dupNos;

}
