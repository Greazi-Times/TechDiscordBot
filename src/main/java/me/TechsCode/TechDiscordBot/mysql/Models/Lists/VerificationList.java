package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationList extends ArrayList<DbVerification> {

    public VerificationList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerificationList() {}

    public VerificationList(Collection<? extends DbVerification> c) {
        super(c);
    }

    public VerificationList id(int id){
        return stream().filter(Verification -> Verification.getId() == id).collect(Collectors.toCollection(VerificationList::new));
    }

    public VerificationList memberId(int memberId){
        return stream().filter(Verification -> Verification.getMemberId() == memberId).collect(Collectors.toCollection(VerificationList::new));
    }

    public VerificationList member(DbMember member){
        return stream().filter(Verification -> Verification.getMember().equals(member)).collect(Collectors.toCollection(VerificationList::new));
    }

    public VerificationList payerId(String payerId){
        return stream().filter(Verification -> Verification.getPayerId().equalsIgnoreCase(payerId)).collect(Collectors.toCollection(VerificationList::new));
    }

}