package com.loan.service;

import java.util.Map;

import com.loan.binding.DailyEntryFormBinding;
import com.loan.binding.DataStatastics;

public interface DailyDetailsService {

	public DailyEntryFormBinding saveToMap(DailyEntryFormBinding binding);

	public boolean saveToDB();

	public Map<Integer, DailyEntryFormBinding> getAllFromMap();

	public DataStatastics getStastics();

	public DailyEntryFormBinding delete(Integer num);

}
