package com.abhi.messweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abhi.messweb.repository.*;

@Controller
public class DashboardController {

    private final MemberRepository memberRepo;
    private final AttendanceRepository attendanceRepo;
    private final PaymentRepository paymentRepo;

    public DashboardController(MemberRepository memberRepo,
                               AttendanceRepository attendanceRepo,
                               PaymentRepository paymentRepo) {
        this.memberRepo = memberRepo;
        this.attendanceRepo = attendanceRepo;
        this.paymentRepo = paymentRepo;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        Long totalMembers = memberRepo.count();
        Long totalAttendance = attendanceRepo.count();
        Long totalRevenue = paymentRepo.getTotalRevenue();
        Long totalDue = paymentRepo.getTotalDue();

        if (totalRevenue == null) totalRevenue = 0L;
        if (totalDue == null) totalDue = 0L;

        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("totalAttendance", totalAttendance);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalDue", totalDue);

        return "dashboard";
    }
}