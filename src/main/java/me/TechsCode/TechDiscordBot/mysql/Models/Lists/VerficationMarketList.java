package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerfication;
import me.TechsCode.TechDiscordBot.mysql.Models.VerficationMarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class VerficationMarketList extends ArrayList<VerficationMarket> {

    public VerficationMarketList(int initialCapacity) {
        super(initialCapacity);
    }

    public VerficationMarketList() {}

    public VerficationMarketList(Collection<? extends VerficationMarket> c) {
        super(c);
    }

    public VerficationMarketList id(int id){
        return stream().filter(verficationMarket -> verficationMarket.getId() == id).collect(Collectors.toCollection(VerficationMarketList::new));
    }

    public VerficationMarketList marketId(int marketId){
        return stream().filter(verficationMarket -> verficationMarket.getMarketId() == marketId).collect(Collectors.toCollection(VerficationMarketList::new));
    }

    public VerficationMarketList market(DbMarket market){
        return stream().filter(verficationMarket -> verficationMarket.getMarket().equals(market)).collect(Collectors.toCollection(VerficationMarketList::new));
    }

    public VerficationMarketList verificationId(int verificationId){
        return stream().filter(verficationMarket -> verficationMarket.getVerificationId() == verificationId).collect(Collectors.toCollection(VerficationMarketList::new));
    }

    public VerficationMarketList verification(DbVerfication verfication){
        return stream().filter(verficationMarket -> verficationMarket.getVerification().equals(verfication)).collect(Collectors.toCollection(VerficationMarketList::new));
    }

    public VerficationMarketList userId(int userId){
        return stream().filter(verficationMarket -> verficationMarket.getUserId() == userId).collect(Collectors.toCollection(VerficationMarketList::new));
    }

}