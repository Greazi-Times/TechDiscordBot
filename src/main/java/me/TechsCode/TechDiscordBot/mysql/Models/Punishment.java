package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.MySQL;

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

    public Punishment(DbMember member, DbMember punisher, String Type, String Reason, long Date, long Expired) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().PUNISHMENTS_TABLE);
        this.memberId = member.getId();
        this.punisherId = punisher.getId();
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

    public DbMember getMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(memberId);
    }

    public int getPunisherId() {
        return punisherId;
    }

    public DbMember getPunisher(){
        return TechDiscordBot.getStorage().retrieveMemberById(punisherId);
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

    public void save(){
        TechDiscordBot.getStorage().savePunishment(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deletePunishment(this);
    }
}
