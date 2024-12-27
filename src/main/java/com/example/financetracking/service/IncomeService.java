package com.example.financetracking.service;

import com.example.financetracking.model.Income;
import com.example.financetracking.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


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

  public double getPreviousMonthTotal() {
    LocalDate startOfPreviousMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
    LocalDate startOfCurrentMonth = LocalDate.now().withDayOfMonth(1);

    return incomeRepository.findAll().stream()
            .filter(income -> income.getDateReceived() != null &&
                    income.getDateReceived().isAfter(startOfPreviousMonth.minusDays(1)) &&
                    income.getDateReceived().isBefore(startOfCurrentMonth))
            .mapToDouble(Income::getAmount)
            .sum();
  }



}