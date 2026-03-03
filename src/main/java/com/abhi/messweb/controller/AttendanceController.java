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

    @PostMapping
public String saveAttendance(
        @RequestParam Long memberId,
        @RequestParam(required = false) boolean breakfast,
        @RequestParam(required = false) boolean lunch,
        @RequestParam(required = false) boolean dinner
) {

    Member member = memberRepo.findById(memberId).orElseThrow();

    Attendance attendance = new Attendance();
    attendance.setMember(member);
    attendance.setDate(LocalDate.now());
    attendance.setBreakfast(breakfast);
    attendance.setLunch(lunch);
    attendance.setDinner(dinner);

    attendanceRepo.save(attendance);

    return "redirect:/attendance";
}
}