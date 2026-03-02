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

    @GetMapping
    public String showMembers(Model model) {
        model.addAttribute("members", repository.findAll());
        model.addAttribute("member", new Member());
        return "members";
    }

    @PostMapping
    public String addMember(Member member) {
        repository.save(member);
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String editMember(@PathVariable Long id, Model model) {
        Member member = repository.findById(id).orElseThrow();
        model.addAttribute("member", member);
        model.addAttribute("members", repository.findAll());
        return "members";
    }

    @PostMapping("/update")
    public String updateMember(Member member) {
        repository.save(member);
        return "redirect:/members";
    }
}