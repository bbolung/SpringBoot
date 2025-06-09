package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member/signup")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("/join")
    public String join(MemberForm form){
        log.info("MemberForm : {}", form);

        Member member = form.toEntity();

        Member saved = memberRepository.save(member);
        log.info("New member: {}", saved);

        return "redirect:/member/" + saved.getId();
    }

    @GetMapping("/member/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("Show member");

        Member memberEntity = memberRepository.findById(id).orElse(null);
        log.info("memberEntity : {}", memberEntity);

        model.addAttribute("member", memberEntity);

        return "member/show";
    }

    @GetMapping("/members")
    public String index(Model model) {

        List<Member> memberEntityList = memberRepository.findAll();

        memberEntityList.forEach(list -> {log.info("list : {}", list);});

        model.addAttribute("memberList", memberEntityList);

        return "member/index";
    }
}
