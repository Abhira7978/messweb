package com.abhi.messweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

import com.abhi.messweb.repository.*;
import com.abhi.messweb.model.*;

@Controller
public class BillingController {

    private final AttendanceRepository attendanceRepo;
    private final MemberRepository memberRepo;
    private final PaymentRepository paymentRepo;

    public BillingController(AttendanceRepository attendanceRepo,
                             MemberRepository memberRepo,
                             PaymentRepository paymentRepo) {
        this.attendanceRepo = attendanceRepo;
        this.memberRepo = memberRepo;
        this.paymentRepo = paymentRepo;
    }

    @GetMapping("/billing")
    public String showBilling(Model model) {
        model.addAttribute("members", memberRepo.findAll());
        return "billing";
    }

    @PostMapping("/billing")
public String generateBill(@RequestParam Long memberId,
                           @RequestParam int month,
                           @RequestParam int year,
                           @RequestParam(required = false) Long paidAmount,
                           Model model) {

    Long presentDays = attendanceRepo.countMonthlyPresent(memberId, month, year);
    if (presentDays == null) presentDays = 0L;

    int total = 0;

    int total = 0;

    total += attendance.isBreakfast() ? 20 : 0;
    total += attendance.isLunch() ? 50 : 0;
    total += attendance.isDinner() ? 30 : 0;

    if (total > 0) total += 10;

    if (total > 0) total += 10; // service charge

    long totalAmount = presentDays * (mealCost + serviceCharge);

    if (paidAmount != null) {

        long dueAmount = totalAmount - paidAmount;

        Payment payment = new Payment();
        payment.setMember(memberRepo.findById(memberId).orElseThrow());
        payment.setMonth(month);
        payment.setYear(year);
        payment.setPresentDays(presentDays);
        payment.setTotalAmount(totalAmount);
        payment.setPaidAmount(paidAmount);
        payment.setDueAmount(dueAmount);
        payment.setPaymentDate(java.time.LocalDate.now());

        paymentRepo.save(payment);

        model.addAttribute("message", "Payment Saved Successfully!");
    }

    model.addAttribute("member", memberRepo.findById(memberId).orElseThrow());
    model.addAttribute("presentDays", presentDays);
    model.addAttribute("totalAmount", totalAmount);

    return "billing";
}
}