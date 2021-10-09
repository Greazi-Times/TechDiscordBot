package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerfication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerficationList extends ArrayList<DbVerfication> {

    public VerficationList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerficationList() {}

    public VerficationList(Collection<? extends DbVerfication> c) {
        super(c);
    }

    public VerficationList id(int id){
        return stream().filter(verfication -> verfication.getId() == id).collect(Collectors.toCollection(VerficationList::new));
    }

    public VerficationList memberId(int memberId){
        return stream().filter(verfication -> verfication.getMemberId() == memberId).collect(Collectors.toCollection(VerficationList::new));
    }

    public VerficationList member(DbMember member){
        return stream().filter(verfication -> verfication.getMember().equals(member)).collect(Collectors.toCollection(VerficationList::new));
    }

    public VerficationList payerId(String payerId){
        return stream().filter(verfication -> verfication.getPayerId().equalsIgnoreCase(payerId)).collect(Collectors.toCollection(VerficationList::new));
    }

}