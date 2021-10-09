package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.SubVerfication;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerfication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class SubVerificationList extends ArrayList<SubVerfication> {

    public SubVerificationList(int initialCapacity) {
        super(initialCapacity);
    }

    public SubVerificationList() {}

    public SubVerificationList(Collection<? extends SubVerfication> c) {
        super(c);
    }

    public SubVerificationList id(int id){
        return stream().filter(subVerfication -> subVerfication.getId() == id).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList subMemberId(int subMemberId){
        return stream().filter(subVerfication -> subVerfication.getSubMemberId() == subMemberId).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList subMember(DbMember member){
        return stream().filter(subVerfication -> subVerfication.getSubMember().equals(member)).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList verificationId(int verificationId){
        return stream().filter(subVerfication -> subVerfication.getVerificationId() == verificationId).collect(Collectors.toCollection(SubVerificationList::new));
    }

    public SubVerificationList verification(DbVerfication verfication){
        return stream().filter(subVerfication -> subVerfication.getVerification().equals(verfication)).collect(Collectors.toCollection(SubVerificationList::new));
    }

}