package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerification;
import me.TechsCode.TechDiscordBot.mysql.Models.VerificationMarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerificationMarketList extends ArrayList<VerificationMarket> {

    public VerificationMarketList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerificationMarketList() {}

    public VerificationMarketList(Collection<? extends VerificationMarket> c) {
        super(c);
    }

    public VerificationMarketList id(int id){
        return stream().filter(verificationMarket -> verificationMarket.getId() == id).collect(Collectors.toCollection(VerificationMarketList::new));
    }

    public VerificationMarketList marketId(int marketId){
        return stream().filter(verificationMarket -> verificationMarket.getMarketId() == marketId).collect(Collectors.toCollection(VerificationMarketList::new));
    }

    public VerificationMarketList market(DbMarket market){
        return stream().filter(verificationMarket -> verificationMarket.getMarket().equals(market)).collect(Collectors.toCollection(VerificationMarketList::new));
    }

    public VerificationMarketList verificationId(int verificationId){
        return stream().filter(verificationMarket -> verificationMarket.getVerificationId() == verificationId).collect(Collectors.toCollection(VerificationMarketList::new));
    }

    public VerificationMarketList verification(DbVerification Verification){
        return stream().filter(verificationMarket -> verificationMarket.getVerification().equals(Verification)).collect(Collectors.toCollection(VerificationMarketList::new));
    }

    public VerificationMarketList userId(int userId){
        return stream().filter(verificationMarket -> verificationMarket.getUserId() == userId).collect(Collectors.toCollection(VerificationMarketList::new));
    }

}