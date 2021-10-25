package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationMarketList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationPluginList;

public class DbVerification {

    private final int id, memberId;
    private final String payerId;

    public DbVerification(int id, int MemberId, String PayerId) {
        this.id = id;
        this.memberId = MemberId;
        this.payerId = PayerId;
    }

    public DbVerification(DbMember member, String PayerId) {
        this.id = 0;
        this.memberId = member.getId();
        this.payerId = PayerId;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public DbMember getMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(memberId);
    }

    public VerificationMarketList getMarkets(){
        return TechDiscordBot.getStorage().retrieveVerificationMarkets(this);
    }

    public String getPayerId() {
        return payerId;
    }


    public VerificationPluginList getPlugins() {
        return TechDiscordBot.getStorage().retrieveVerificationPlugins(this);
    }

    public void addMarket(DbMarket dbMarket){
        VerificationMarket VerificationMarket = new VerificationMarket(dbMarket, this, memberId);
        VerificationMarket.save();
    }

    public void addPlugin(DbMarket dbMarket, Resource resource, String transaction, String purchaseData, boolean reviewed){
        VerificationPlugin VerificationPlugin = new VerificationPlugin(dbMarket, this, resource, transaction, purchaseData, reviewed);
        VerificationPlugin.save();
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerification(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerification(this);
    }
}
