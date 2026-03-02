package com.abhi.messweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abhi.messweb.model.Payment;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.paidAmount) FROM Payment p")
Long getTotalRevenue();

@Query("SELECT SUM(p.dueAmount) FROM Payment p")
Long getTotalDue();
}