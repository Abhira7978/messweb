package com.abhi.messweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import com.abhi.messweb.repository.*;
import com.abhi.messweb.model.*;

@Controller
public class AttendanceController {

    private final AttendanceRepository attendanceRepo;
    private final MemberRepository memberRepo;

    public AttendanceController(AttendanceRepository attendanceRepo,
                                MemberRepository memberRepo) {
        this.attendanceRepo = attendanceRepo;
        this.memberRepo = memberRepo;
    }

    @GetMapping("/attendance")
    public String showAttendanceForm(Model model) {
        model.addAttribute("members", memberRepo.findAll());
        model.addAttribute("attendanceList", attendanceRepo.findAll());
        return "attendance";
    }

    @PostMapping("/attendance")
public String markAttendance(@RequestParam Long memberId,
                             @RequestParam boolean present,
                             Model model) {

    LocalDate today = LocalDate.now();

    if (attendanceRepo.findByMemberIdAndDate(memberId, today).isPresent()) {
        model.addAttribute("error", "Attendance already marked for today!");
        model.addAttribute("members", memberRepo.findAll());
        model.addAttribute("attendanceList", attendanceRepo.findAll());
        return "attendance";
    }

    Attendance attendance = new Attendance();
    attendance.setDate(today);
    attendance.setPresent(present);
    attendance.setMember(memberRepo.findById(memberId).orElseThrow());

    attendanceRepo.save(attendance);

    return "redirect:/attendance";
}
}