package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class VerificationMarket {

    private final int id;
    private int marketId, verificationId, userId;

    public VerificationMarket(int id, int marketId, int VerificationId, int userId) {
        this.id = id;
        this.marketId = marketId;
        this.verificationId = VerificationId;
        this.userId = userId;
    }

    public VerificationMarket(DbMarket market, DbVerification Verification, int userId) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().VERIFICATIONMARKETS_TABLE);
        this.marketId = market.getId();
        this.verificationId = Verification.getId();
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getMarketId() {
        return marketId;
    }

    public DbMarket getMarket(){
        return TechDiscordBot.getStorage().retrieveMarketById(marketId);
    }

    public int getVerificationId() {
        return verificationId;
    }

    public DbVerification getVerification(){
        return TechDiscordBot.getStorage().retrieveVerificationById(verificationId);
    }

    public int getUserId() {
        return userId;
    }

    public void setMarketId(DbMarket market){
        this.marketId = market.getId();
    }

    public void setVerificationId(DbVerification Verification){
        this.verificationId = Verification.getId();
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerificationMarket(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerificationMarket(this);
    }
}
