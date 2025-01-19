package com.project.uwd.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.repositories.ReportRepository;
import com.project.uwd.services.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository _reportRepository;
	@Override
	public List<int[]> getReport(LocalDate from, LocalDate to) {
		return _reportRepository.getReport(from, to);
	}
	
	@Override
	public int[] calculateTotals(List<int[]> dataSet) {
		int sumOfSoldSeats = 0;
		double incomeSum = 0;
		for (int[] data : dataSet) {
			sumOfSoldSeats += data[2];
			incomeSum += data[3];
		}
		
		int[] totals = new int[] {sumOfSoldSeats, (int)incomeSum};
		return totals;
	}
}
