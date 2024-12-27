package com.example.financetracking.controller;

//rest API endpoints
/*
create spendings: POST /api/spendings
retrieve all spendings: GET /api/spendings
get total spent: GET /api/spendings/total
 */

import com.example.financetracking.model.Income;
import com.example.financetracking.model.Spending;
import com.example.financetracking.service.IncomeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {
  private final IncomeService incomeService;

  public IncomeController(IncomeService incomeService){
    this.incomeService = incomeService;
  }

  @PostMapping
  public Income addIncome(@RequestBody Income income){
    return incomeService.addIncome(income);
  }

  @GetMapping
  public List<Income> getAllIncome(){
    return incomeService.getAllIncomes();
  }

  @GetMapping("/total")
  public double getTotalIncome(){
    return incomeService.getTotalIncome();
  }

  @GetMapping("/month")
  public double getMonthlyIncome(){
    return incomeService.getCurrentMonthTotal();
  }

  @GetMapping("/prevMonth")
  public double getPreviousMonthlyIncome(int n){
    return incomeService.getPreviousMonthTotal(n);
  }


  @GetMapping("/month/detail")
  public List<Income> getMonthlyIncomeDetail(){
    return incomeService.getCurrentMonth();
  }




}
