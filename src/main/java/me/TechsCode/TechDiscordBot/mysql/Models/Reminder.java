package me.TechsCode.TechDiscordBot.mysql.Models;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Reminder {

    private final int id;
    private int memberId;
    private String channelId, reminder;
    private ReminderType type;
    private long time;

    public Reminder(int id, int MemberId, String ChannelId, int Type, String Reminder, long Time) {
        this.id = id;
        this.memberId = MemberId;
        this.channelId = ChannelId;
        this.type = Type == 0 ? ReminderType.CHANNEL : ReminderType.DMs;
        this.reminder = Reminder;
        this.time = Time;
    }

    public Reminder(DbMember member, String ChannelId, int Type, String Reminder, long Time) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().REMINDERS_TABLE);
        this.memberId = member.getId();
        this.channelId = ChannelId;
        this.type = Type == 0 ? ReminderType.CHANNEL : ReminderType.DMs;
        this.reminder = Reminder;
        this.time = Time;
    }

    public Reminder(DbMember member, String ChannelId, ReminderType Type, String Reminder, long Time) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().REMINDERS_TABLE);
        this.memberId = member.getId();
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

    public DbMember getMember(){
        return TechDiscordBot.getStorage().retrieveMemberById(memberId);
    }

    public String getChannelId() {
        return channelId;
    }

    public TextChannel getChannel(){
        return TechDiscordBot.getJDA().getTextChannelById(channelId);
    }

    public ReminderType getType() {
        return type;
    }

    public String getReminder() {
        return reminder;
    }

    public long getTime() {
        return time;
    }

    public void setMemberId(DbMember member){
        this.memberId = member.getId();
    }

    public void setChannelId(String ChannelId){
        this.channelId = ChannelId;
    }

    public void setType(ReminderType Type){
        this.type = Type;
    }

    public void setReminder(String Reminder){
        this.reminder = Reminder;
    }

    public void setTime(long Time){
        this.time = Time;
    }

    public void save(){
        TechDiscordBot.getStorage().saveReminder(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteReminder(this);
    }

    public void send() {
        User user = getMember().getDiscordMember().getUser();

        if(user != null) {
            ReminderType type = this.type;
            TextChannel channel = TechDiscordBot.getJDA().getTextChannelById(channelId);

            if(channel == null) type = ReminderType.DMs;

            if(type == ReminderType.DMs) {
                try {
                    user.openPrivateChannel().queue(msg -> msg.sendMessage("**Reminder**: " + reminder).queue());
                } catch(Exception ex) {
                    if(channel == null)
                        return;

                    sendReminder(user, channel);
                }
            } else {
                sendReminder(user, channel);
            }
        }

        delete();
    }

    private void sendReminder(User user, TextChannel channel) {
        channel.sendMessage("**Reminder for " + user.getAsMention() + "**: " + reminder).queue();
    }

}
