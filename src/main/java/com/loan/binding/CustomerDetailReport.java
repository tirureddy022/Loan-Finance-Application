package com.loan.binding;

import java.util.List;

import org.springframework.stereotype.Component;

import com.loan.entities.DailyPaidDetails;

import lombok.Data;
import lombok.ToString;

@Data
@Component
@ToString
public class CustomerDetailReport {

	private List<DailyPaidDetails> data;
	private Double totalAmount;
	private Double paidAlready;
	private Double pendingAmount;

}
