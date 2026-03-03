package com.abhi.messweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.abhi.messweb.repository.MemberRepository;
import com.abhi.messweb.model.Member;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository repository;

    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    // Show Members Page
    @GetMapping
    public String showMembers(Model model) {
        model.addAttribute("members", repository.findAll());
        model.addAttribute("member", new Member());
        return "members";
    }

    // Add Member
    @PostMapping
    public String addMember(@ModelAttribute Member member) {
        repository.save(member);
        return "redirect:/members";
    }

    // Delete Member (Cascade will delete attendance + payments)
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/members";
    }

    // Edit Member
    @GetMapping("/edit/{id}")
    public String editMember(@PathVariable Long id, Model model) {
        Member member = repository.findById(id).orElseThrow();
        model.addAttribute("member", member);
        model.addAttribute("members", repository.findAll());
        return "members";
    }

    // Update Member
    @PostMapping("/update")
    public String updateMember(@ModelAttribute Member member) {
        repository.save(member);
        return "redirect:/members";
    }
}