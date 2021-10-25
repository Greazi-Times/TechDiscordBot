package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.SubVerification;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class SubVerificationList extends ArrayList<SubVerification> {

    public SubVerificationList(int initialCapacity) {
        super(initialCapacity);
    }

    public SubVerificationList() {}

    public SubVerificationList(Collection<? extends SubVerification> c) {
        super(c);
    }

    public SubVerificationList id(int id){
        return stream().filter(subVerification -> subVerification.getId() == id).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList subMemberId(int subMemberId){
        return stream().filter(subVerification -> subVerification.getSubMemberId() == subMemberId).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList subMember(DbMember member){
        return stream().filter(subVerification -> subVerification.getSubMember().equals(member)).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList verificationId(int verificationId){
        return stream().filter(subVerification -> subVerification.getVerificationId() == verificationId).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList verification(DbVerification Verification){
        return stream().filter(subVerification -> subVerification.getVerification().equals(Verification)).collect(Collectors.toCollection(SubVerificationList::new));
    }

}