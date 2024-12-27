package com.example.financetracking.repository;

import com.example.financetracking.model.Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long>{

}