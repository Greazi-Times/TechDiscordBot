package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class DbPreorder {

    private final int id;
    private final String email, discordName, transactionId;
    private final boolean isPatreon;
    private final long discordId;

    public DbPreorder(int id, String email, long discordId, String discordName, String transactionId, boolean isPatreon) {
        this.id = id;
        this.email = email;
        this.discordId = discordId;
        this.discordName = discordName;
        this.transactionId = transactionId;
        this.isPatreon = isPatreon;
    }

    public DbPreorder(String email, long discordId, String discordName, String transactionId, boolean isPatreon) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().MARKETS_TABLE);
        this.email = email;
        this.discordId = discordId;
        this.discordName = discordName;
        this.transactionId = transactionId;
        this.isPatreon = isPatreon;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public long getDiscordId() {
        return discordId;
    }

    public String getDiscordName() {
        return discordName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean isPatreon() {
        return isPatreon;
    }

}
