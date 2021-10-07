package me.TechsCode.TechDiscordBot.mysql.Models;

public class VerficationQ {

    private final int id, memberId, marketId;
    private final String email, transactionId;

    public VerficationQ(int id, int memberId, int marketId, String email, String transactionId) {
        this.id = id;
        this.memberId = memberId;
        this.marketId = marketId;
        this.email = email;
        this.transactionId = transactionId;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public int getMarketId() {
        return marketId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
