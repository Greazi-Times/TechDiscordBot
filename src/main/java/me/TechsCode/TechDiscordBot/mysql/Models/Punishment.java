package me.TechsCode.TechDiscordBot.mysql.Models;

public class Punishment {

    private final int id, memberId, punisherId;
    private final String type, reason;
    private final long date, expired;

    public Punishment(int id, int MemberId, int PunisherId, String Type, String Reason, long Date, long Expired) {
        this.id = id;
        this.memberId = MemberId;
        this.punisherId = PunisherId;
        this.type = Type;
        this.reason = Reason;
        this.date = Date;
        this.expired = Expired;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getPunisherId() {
        return punisherId;
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public long getDate() {
        return date;
    }

    public long getExpired() {
        return expired;
    }
}
