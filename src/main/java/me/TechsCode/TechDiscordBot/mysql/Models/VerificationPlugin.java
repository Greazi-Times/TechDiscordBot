package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class VerificationPlugin {

    private final int id;
    private int marketId;
    private int verificationId;
    private int resourceId;
    private String transactionId, purchaseData;
    private float price;
    public long date;
    private boolean reviewed;

    public VerificationPlugin(int id, int marketId, int verificationId, int resourceId, String transactionId, String purchaseData, float price, long date, boolean reviewed) {
        this.id = id;
        this.marketId = marketId;
        this.verificationId = verificationId;
        this.resourceId = resourceId;
        this.transactionId = transactionId;
        this.purchaseData = purchaseData;
        this.price = price;
        this.date = date;
        this.reviewed = reviewed;
    }

    public VerificationPlugin(DbMarket market, DbVerification verification, Resource resource, String transactionId, String purchaseData, float price, long date, boolean reviewed) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().PURCHASEDPLUGINS_TABLE);
        this.marketId = market.getId();
        this.verificationId = verification.getId();
        this.resourceId = resource.getId();
        this.transactionId = transactionId;
        this.purchaseData = purchaseData;
        this.price = price;
        this.date = date;
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

    public DbVerification getVerification(){
        return TechDiscordBot.getStorage().retrieveVerificationById(verificationId);
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

    public float getPrice() {
        return price;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public int isReviewedNumber() {
        return reviewed ? 1 : 0;
    }

    public void setMarketId(DbMarket market){
        this.marketId = market.getId();
    }

    public void setVerificationId(DbVerification verification){
        this.verificationId = verification.getId();
    }

    public void setResourceId(Resource resource){
        this.resourceId = resource.getId();
    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }

    public void setPurchaseData(String purchaseData){
        this.purchaseData = purchaseData;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public void setReviewed(boolean reviewed){
        this.reviewed = reviewed;
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerificationPlugin(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerificationPlugin(this);
    }

}
