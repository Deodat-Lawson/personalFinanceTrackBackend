package com.example.financetracking.service;

import com.example.financetracking.model.Income;
import com.example.financetracking.model.Spending;
import com.example.financetracking.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class IncomeService{
  private final IncomeRepository incomeRepository;
  public IncomeService(IncomeRepository incomeRepository){
    this.incomeRepository = incomeRepository;
  }

  public Income addIncome(Income income){
    return incomeRepository.save(income);
  }

  public List<Income> getAllIncomes(){
    return incomeRepository.findAll();
  }

  public double getTotalIncome(){
    return incomeRepository.findAll().stream().mapToDouble(Income::getAmount).sum();
  }

  public double getCurrentMonthTotal() {
    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);

    return incomeRepository.findAll().stream()
            .filter(income -> income.getDateReceived() != null &&
                    income.getDateReceived().isAfter(startOfMonth.minusDays(1)) &&
                    income.getDateReceived().isBefore(LocalDate.now().plusDays(1)))
            .mapToDouble(Income::getAmount)
            .sum();
  }
  public List<Income> getCurrentMonth() {
    LocalDate now = LocalDate.now();
    // First day of the current month
    LocalDate startOfMonth = now.withDayOfMonth(1);
    // First day of the next month (so it's exclusive)
    LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

    return incomeRepository.findAll().stream()
            .filter(income -> income.getDateReceived() != null
                    && !income.getDateReceived().isBefore(startOfMonth)  // >= startOfMonth
                    && income.getDateReceived().isBefore(startOfNextMonth)) // < startOfNextMonth
            .collect(Collectors.toList());
  }

  public double getPreviousMonthTotal(int n) {
    LocalDate startOfPreviousMonth = LocalDate.now().minusMonths(n).withDayOfMonth(1);
    LocalDate startOfCurrentMonth = LocalDate.now().minusMonths(n-1).withDayOfMonth(1);

    return incomeRepository.findAll().stream()
            .filter(income -> income.getDateReceived() != null &&
                    income.getDateReceived().isAfter(startOfPreviousMonth.minusDays(1)) &&
                    income.getDateReceived().isBefore(startOfCurrentMonth))
            .mapToDouble(Income::getAmount)
            .sum();
  }




}