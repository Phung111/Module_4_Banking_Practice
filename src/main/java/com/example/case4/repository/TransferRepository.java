package com.example.case4.repository;

import com.example.case4.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
