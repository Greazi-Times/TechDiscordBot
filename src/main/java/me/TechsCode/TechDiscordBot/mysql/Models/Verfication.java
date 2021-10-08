package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class Verfication {

    private final int id, memberId;
    private final String payerId;

    public Verfication(int id, int MemberId, String PayerId) {
        this.id = id;
        this.memberId = MemberId;
        this.payerId = PayerId;
    }

    public Verfication(DbMember member, String PayerId) {
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
}
