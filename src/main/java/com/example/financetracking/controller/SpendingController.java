package com.example.financetracking.controller;

//rest API endpoints
/*
create spendings: POST /api/spendings
retrieve all spendings: GET /api/spendings
get total spent: GET /api/spendings/total
 */

import com.example.financetracking.model.Spending;
import com.example.financetracking.service.SpendingService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/spendings")
@CrossOrigin(origins = "*")
public class SpendingController {
  private final SpendingService spendingService;

  public SpendingController(SpendingService spendingService){
    this.spendingService = spendingService;
  }

  @PostMapping
  public Spending addSpending(@RequestBody Spending spending){
    return spendingService.addSpending(spending);
  }

  @GetMapping
  public List<Spending> getAllSpendings(){
    return spendingService.getAllSpendings();
  }

  @GetMapping("/total")
  public double getTotalSpent(){
    return spendingService.getTotalSpent();
  }

  @GetMapping("/month")
  public double getMonthlySpend(){
    return spendingService.getCurrentMonthTotal();
  }

  @GetMapping("/prevMonth")
  public double getPreviousMonthlySpend(){
    return spendingService.getPreviousMonthTotal();
  }

}
