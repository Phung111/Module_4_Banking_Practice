package com.example.case4.repository;

import com.example.case4.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
