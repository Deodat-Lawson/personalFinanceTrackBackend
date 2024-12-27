package com.example.financetracking.service;

import com.example.financetracking.model.Income;
import com.example.financetracking.model.Spending;
import com.example.financetracking.repository.SpendingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

  public double getPreviousMonthTotal() {
    LocalDate startOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
    LocalDate startOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

    return spendingRepository.findAll().stream()
            .filter(spending -> spending.getDateSpent() != null &&
                    spending.getDateSpent().isAfter(startOfPreviousMonth.minusDays(1)) &&
                    spending.getDateSpent().isBefore(startOfCurrentMonth))
            .mapToDouble(Spending::getAmount)
            .sum();
  }

}