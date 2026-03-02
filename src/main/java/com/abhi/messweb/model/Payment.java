package com.abhi.messweb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@Column(name = "payment_month")
private int month;

@Column(name = "payment_year")
private int year;

    private Long presentDays;
    private Long totalAmount;
    private Long paidAmount;
    private Long dueAmount;

    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Long getPresentDays() { return presentDays; }
    public void setPresentDays(Long presentDays) { this.presentDays = presentDays; }

    public Long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }

    public Long getPaidAmount() { return paidAmount; }
    public void setPaidAmount(Long paidAmount) { this.paidAmount = paidAmount; }

    public Long getDueAmount() { return dueAmount; }
    public void setDueAmount(Long dueAmount) { this.dueAmount = dueAmount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}