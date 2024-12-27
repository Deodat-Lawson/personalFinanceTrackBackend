package com.example.financetracking.service;

import com.example.financetracking.model.Income;
import com.example.financetracking.model.Spending;
import com.example.financetracking.repository.SpendingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpendingService{
  private final SpendingRepository spendingRepository;
  public SpendingService(SpendingRepository spendingRepository){
    this.spendingRepository = spendingRepository;
  }

  public Spending addSpending(Spending spending){
    return spendingRepository.save(spending);
  }

  public List<Spending> getAllSpendings(){
    return spendingRepository.findAll();
  }

  public double getTotalSpent(){
    return spendingRepository.findAll().stream().mapToDouble(Spending::getAmount).sum();
  }


  public double getCurrentMonthTotal() {
    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);

    return spendingRepository.findAll().stream()
            .filter(spending -> spending.getDateSpent() != null &&
                    spending.getDateSpent().isAfter(startOfMonth.minusDays(1)) &&
                    spending.getDateSpent().isBefore(LocalDate.now().plusDays(1)))
            .mapToDouble(Spending::getAmount)
            .sum();
  }


  public List<Spending> getCurrentMonth() {
    LocalDate now = LocalDate.now();
    // First day of the current month
    LocalDate startOfMonth = now.withDayOfMonth(1);
    // First day of the next month (so it's exclusive)
    LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

    return spendingRepository.findAll().stream()
            .filter(spending -> spending.getDateSpent() != null
                    && !spending.getDateSpent().isBefore(startOfMonth)  // >= startOfMonth
                    && spending.getDateSpent().isBefore(startOfNextMonth)) // < startOfNextMonth
            .collect(Collectors.toList());
  }
  public double getPreviousMonthTotal(int n) {
    LocalDate startOfPreviousMonth = LocalDate.now().minusMonths(n).withDayOfMonth(1);
    LocalDate startOfCurrentMonth = LocalDate.now().minusMonths(n-1).withDayOfMonth(1);

    return spendingRepository.findAll().stream()
            .filter(spending -> spending.getDateSpent() != null &&
                    spending.getDateSpent().isAfter(startOfPreviousMonth.minusDays(1)) &&
                    spending.getDateSpent().isBefore(startOfCurrentMonth))
            .mapToDouble(Spending::getAmount)
            .sum();
  }

}