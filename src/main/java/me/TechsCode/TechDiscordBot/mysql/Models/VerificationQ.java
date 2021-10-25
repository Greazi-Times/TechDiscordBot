package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class VerificationQ {

    private final int id, memberId, marketId;
    private String email;
    private String transactionId;

    public VerificationQ(int id, int memberId, int marketId, String email, String transactionId) {
        this.id = id;
        this.memberId = memberId;
        this.marketId = marketId;
        this.email = email;
        this.transactionId = transactionId;
    }

    public VerificationQ(DbMember member, DbMarket market, String email, String transactionId) {
        this.id = 0;
        this.memberId = member.getId();
        this.marketId = market.getId();
        this.email = email;
        this.transactionId = transactionId;
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

    public int getMarketId() {
        return marketId;
    }

    public DbMarket getMarket(){
        return TechDiscordBot.getStorage().retrieveMarketById(marketId);
    }

    public String getEmail() {
        return email;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerificationQ(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerificationQ(this);
    }


}
