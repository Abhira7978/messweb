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

    // Get all attendance records for that member in that month
    List<Attendance> attendanceList =
            attendanceRepo.findByMemberIdAndMonthAndYear(memberId, month, year);

    long presentDays = 0;
    long totalAmount = 0;

    for (Attendance attendance : attendanceList) {

        int mealCost = 0;

        if (attendance.isBreakfast()) mealCost += 20;
        if (attendance.isLunch()) mealCost += 50;
        if (attendance.isDinner()) mealCost += 30;

        if (mealCost > 0) {
            mealCost += 10; // service charge per present day
            presentDays++;
        }

        totalAmount += mealCost;
    }

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
        payment.setPaymentDate(LocalDate.now());

        paymentRepo.save(payment);

        model.addAttribute("message", "Payment Saved Successfully!");
    }

    model.addAttribute("member", memberRepo.findById(memberId).orElseThrow());
    model.addAttribute("presentDays", presentDays);
    model.addAttribute("totalAmount", totalAmount);

    return "billing";
}}