package com.abhi.messweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.abhi.messweb.repository.PaymentRepository;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepo;

    public PaymentController(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @GetMapping
    public String showPayments(Model model) {
        model.addAttribute("payments", paymentRepo.findAll());
        return "payments";
    }
}