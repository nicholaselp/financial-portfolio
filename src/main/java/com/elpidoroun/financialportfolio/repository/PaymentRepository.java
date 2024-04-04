package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
