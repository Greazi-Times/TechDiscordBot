package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerficationMarketList;

public class DbVerfication {

    private final int id, memberId;
    private final String payerId;

    public DbVerfication(int id, int MemberId, String PayerId) {
        this.id = id;
        this.memberId = MemberId;
        this.payerId = PayerId;
    }

    public DbVerfication(DbMember member, String PayerId) {
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

    public String getPayerId() {
        return payerId;
    }

    public VerficationMarketList getMarkets(){
        return TechDiscordBot.getStorage().retrieveVerficationMarkets(this);
    }

    public void save(){
        TechDiscordBot.getStorage().saveVerfication(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteVerfication(this);
    }
}