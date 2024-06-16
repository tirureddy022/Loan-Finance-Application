package com.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.binding.DailyEntryFormBinding;
import com.loan.binding.DataStatastics;
import com.loan.entities.DailyPaidDetails;
import com.loan.repo.DailyPaidDetailsRepo;

@Service
public class DailyDetailsServiceImpl implements DailyDetailsService {

	@Autowired
	private DailyPaidDetailsRepo repo;

	public static Map<Integer, DailyEntryFormBinding> map = new HashMap<>();

	int index;

	@Override
	public DailyEntryFormBinding saveToMap(DailyEntryFormBinding binding) {
		DailyPaidDetails details = new DailyPaidDetails();
		BeanUtils.copyProperties(binding, details);
		details.setActiveFlag('Y');
		map.put(index++, binding);
		return binding;
	}

	@Override
	public boolean saveToDB() {

		List<DailyPaidDetails> list = map.values().stream().map(e -> {
			DailyPaidDetails details = new DailyPaidDetails();
			BeanUtils.copyProperties(e, details);
			details.setActiveFlag('Y');
			return details;
		}).collect(Collectors.toList());
		map.clear();
		return !repo.saveAll(list).isEmpty();
	}

	@Override
	public Map<Integer, DailyEntryFormBinding> getAllFromMap() {
		return map;
	}

	@Override
	public DataStatastics getStastics() {
		int count = map.size();
		Double sum = map.values().stream().collect(Collectors.summingDouble(DailyEntryFormBinding::getAmount));
		List<Integer> dupNumbers = map.values().stream()
				.collect(Collectors.groupingBy(DailyEntryFormBinding::getLoanNo, Collectors.counting())).entrySet()
				.stream().filter(e -> e.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
		return new DataStatastics(sum, count, dupNumbers.toString());
	}

	@Override
	public DailyEntryFormBinding delete(Integer num) {
		return map.remove(num);
	}

}
