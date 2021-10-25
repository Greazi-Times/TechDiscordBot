package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.VerificationQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationQList extends ArrayList<VerificationQ> {

    public VerificationQList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerificationQList() {}

    public VerificationQList(Collection<? extends VerificationQ> c) {
        super(c);
    }

    public VerificationQList id(int id){
        return stream().filter(VerificationQ -> VerificationQ.getId() == id).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList memberId(int memberId){
        return stream().filter(VerificationQ -> VerificationQ.getMemberId() == memberId).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList member(DbMember member){
        return stream().filter(VerificationQ -> VerificationQ.getMember().equals(member)).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList marketId(int marketId){
        return stream().filter(VerificationQ -> VerificationQ.getMarketId() == marketId).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList market(DbMarket market){
        return stream().filter(VerificationQ -> VerificationQ.getMarket().equals(market)).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList email(String email){
        return stream().filter(VerificationQ -> VerificationQ.getEmail().equalsIgnoreCase(email)).collect(Collectors.toCollection(VerificationQList::new));
    }

    public VerificationQList transactionId(String transactionId){
        return stream().filter(VerificationQ -> VerificationQ.getTransactionId().equalsIgnoreCase(transactionId)).collect(Collectors.toCollection(VerificationQList::new));
    }

}