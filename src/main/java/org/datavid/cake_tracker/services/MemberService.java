package org.datavid.cake_tracker.services;

import org.datavid.cake_tracker.entity.Member;
import org.datavid.cake_tracker.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MemberService {

    private static final int MINIMUM_AGE = 18;

    @Autowired
    private MemberRepository memberRepository;

    public Member addMember(Member member) {
        validateMemberFields(member);
        validateMemberUniqueness(member);
        validateMemberAge(member);
        return memberRepository.save(member);
    }


    private void validateMemberFields(Member member) {
        if (member.getFirstName() == null || member.getFirstName().trim().isEmpty() ||
                member.getLastName() == null || member.getLastName().trim().isEmpty() ||
                member.getBirthDate() == null ||
                member.getCountry() == null || member.getCountry().trim().isEmpty() ||
                member.getCity() == null || member.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("All fields are mandatory and must not be empty!");
        }
    }


    private void validateMemberUniqueness(Member member) {
        List<Member> existingMembers = memberRepository.findAll();
        for (Member existingMember : existingMembers) {
            if (existingMember.getFirstName().equalsIgnoreCase(member.getFirstName()) ||
                    existingMember.getLastName().equalsIgnoreCase(member.getLastName()) ||
                    existingMember.getCity().equalsIgnoreCase(member.getCity()) ||
                    existingMember.getCountry().equalsIgnoreCase(member.getCountry())) {
                throw new IllegalArgumentException("A member with the same first name, last name, city, and country already exists.");
            }
        }
    }




    private void validateMemberAge(Member member) {
        if (Period.between(member.getBirthDate(), LocalDate.now()).getYears() < MINIMUM_AGE) {
            throw new IllegalArgumentException("Member must be at least " + MINIMUM_AGE + " years old");
        }
    }






    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }





    public List<Member> getMembersSortedByBirthday() {
        List<Member> members = memberRepository.findAll();
        sortMembersByUpcomingBirthday(members);
        return members;
    }


    private void sortMembersByUpcomingBirthday(List<Member> members) {
        Collections.sort(members, new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                LocalDate now = LocalDate.now();
                LocalDate nextBirthday1 = getNextBirthday(m1.getBirthDate(), now);
                LocalDate nextBirthday2 = getNextBirthday(m2.getBirthDate(), now);
                return nextBirthday1.compareTo(nextBirthday2);
            }
        });
    }




    private LocalDate getNextBirthday(LocalDate birthDate, LocalDate referenceDate) {
        LocalDate nextBirthday = birthDate.withYear(referenceDate.getYear());
        if (nextBirthday.isBefore(referenceDate) || nextBirthday.isEqual(referenceDate)) {
            nextBirthday = nextBirthday.plusYears(1);
        }
        return nextBirthday;
    }
}
