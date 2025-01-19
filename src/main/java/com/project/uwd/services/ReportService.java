package com.project.uwd.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ReportService {
	public List<int[]> getReport(LocalDate from, LocalDate to);
	public int[] calculateTotals(List<int[]> dataSet);
}
