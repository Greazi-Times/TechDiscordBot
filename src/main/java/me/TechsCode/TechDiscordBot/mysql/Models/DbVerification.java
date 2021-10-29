package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationMarketList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationPluginList;

public class DbVerification {

    private final int id;
    private int memberId;
    private String payerId;

    public DbVerification(int id, int MemberId, String PayerId) {
        this.id = id;
        this.memberId = MemberId;
        this.payerId = PayerId;
    }

    public DbVerification(DbMember member, String PayerId) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().VERIFICATIONS_TABLE);
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

    public void addMarket(DbMarket dbMarket, int userId){
        TechDiscordBot.log("verifi Id: "+this.id);
        VerificationMarket VerificationMarket = new VerificationMarket(dbMarket, this, userId);
        VerificationMarket.save();
    }

    public void addPlugin(DbMarket dbMarket, Resource resource, String transactionId, String purchaseData, boolean reviewed){
        VerificationPlugin VerificationPlugin = new VerificationPlugin(dbMarket, this, resource, transactionId, purchaseData, reviewed);
        VerificationPlugin.save();
    }

    public void setMember(DbMember dbMember){
        this.memberId = dbMember.getId();
    }

    public void setPayerId(String payerId){
        this.payerId = payerId;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerification(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerification(this);
    }
}
