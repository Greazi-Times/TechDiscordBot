package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class VerficationMarket {

    private final int id, marketId, verificationId, userId;

    public VerficationMarket(int id, int marketId, int verficationId, int userId) {
        this.id = id;
        this.marketId = marketId;
        this.verificationId = verficationId;
        this.userId = userId;
    }

    public VerficationMarket(DbMarket market, DbVerfication verfication, int userId) {
        this.id = 0;
        this.marketId = market.getId();
        this.verificationId = verfication.getId();
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

    public DbVerfication getVerification(){
        return TechDiscordBot.getStorage().retrieveVerficationById(verificationId);
    }

    public int getUserId() {
        return userId;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerficationMarket(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerficationMarket(this);
    }

}
