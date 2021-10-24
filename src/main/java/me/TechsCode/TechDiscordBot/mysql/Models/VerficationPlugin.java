package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class VerficationPlugin {

    private final int id, marketId, verificationId, resourceId;
    private final String transactionId, purchaseData;
    private final boolean reviewed;

    public VerficationPlugin(int id, int marketId, int verificationId, int resourceId, String transactionId, String purchaseData, boolean reviewed) {
        this.id = id;
        this.marketId = marketId;
        this.verificationId = verificationId;
        this.resourceId = resourceId;
        this.transactionId = transactionId;
        this.purchaseData = purchaseData;
        this.reviewed = reviewed;
    }

    public VerficationPlugin(DbMarket market, DbVerfication verification, Resource resource, String transactionId, String purchaseData, boolean reviewed) {
        this.id = 0;
        this.marketId = market.getId();
        this.verificationId = verification.getId();
        this.resourceId = resource.getId();
        this.transactionId = transactionId;
        this.purchaseData = purchaseData;
        this.reviewed = reviewed;
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

    public int getResourceId() {
        return resourceId;
    }

    public Resource getResource(){
        return TechDiscordBot.getStorage().retrieveResourceById(resourceId);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPurchaseData() {
        return purchaseData;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerficationPlugin(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerficationPlugin(this);
    }

}
