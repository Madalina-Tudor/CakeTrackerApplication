package org.datavid.cake_tracker.controller;

import org.datavid.cake_tracker.entity.Member;
import org.datavid.cake_tracker.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        try {
            Member savedMember = memberService.addMember(member);
            return new ResponseEntity<>("Member successfully added. :) " , HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to add member: " + e.getMessage());
            return new ResponseEntity<>("Failed to add member: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all_members")

    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/sorted-by-birthday")
    public ResponseEntity<List<Member>> getMembersSortedByBirthday() {
        List<Member> members = memberService.getMembersSortedByBirthday();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
