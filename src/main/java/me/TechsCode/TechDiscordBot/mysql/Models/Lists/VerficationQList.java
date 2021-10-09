package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.VerficationQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerficationQList extends ArrayList<VerficationQ> {

    public VerficationQList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerficationQList() {}

    public VerficationQList(Collection<? extends VerficationQ> c) {
        super(c);
    }

    public VerficationQList id(int id){
        return stream().filter(verficationQ -> verficationQ.getId() == id).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList memberId(int memberId){
        return stream().filter(verficationQ -> verficationQ.getMemberId() == memberId).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList member(DbMember member){
        return stream().filter(verficationQ -> verficationQ.getMember().equals(member)).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList marketId(int marketId){
        return stream().filter(verficationQ -> verficationQ.getMarketId() == marketId).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList market(DbMarket market){
        return stream().filter(verficationQ -> verficationQ.getMarket().equals(market)).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList email(String email){
        return stream().filter(verficationQ -> verficationQ.getEmail().equalsIgnoreCase(email)).collect(Collectors.toCollection(VerficationQList::new));
    }

    public VerficationQList transactionId(String transactionId){
        return stream().filter(verficationQ -> verficationQ.getTransactionId().equalsIgnoreCase(transactionId)).collect(Collectors.toCollection(VerficationQList::new));
    }

}