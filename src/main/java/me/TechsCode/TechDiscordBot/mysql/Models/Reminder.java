package me.TechsCode.TechDiscordBot.mysql.Models;

public class Reminder {

    private final int id, memberId;
    private final String channelId, type, reminder;
    private final long time;

    public Reminder(int id, int MemberId, String ChannelId, String Type, String Reminder, long Time) {
        this.id = id;
        this.memberId = MemberId;
        this.channelId = ChannelId;
        this.type = Type;
        this.reminder = Reminder;
        this.time = Time;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getType() {
        return type;
    }

    public String getReminder() {
        return reminder;
    }

    public long getTime() {
        return time;
    }
}
