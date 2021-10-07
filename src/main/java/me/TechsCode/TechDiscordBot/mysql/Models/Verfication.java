package me.TechsCode.TechDiscordBot.mysql.Models;

public class Verfication {

    private final int id, memberId;
    private final String payerId;

    public Verfication(int id, int MemberId, String PayerId) {
        this.id = id;
        this.memberId = MemberId;
        this.payerId = PayerId;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getPayerId() {
        return payerId;
    }
}
