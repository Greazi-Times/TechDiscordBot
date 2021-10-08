package me.TechsCode.TechDiscordBot.mysql.Models;

public class ReminderType {

    private int index;
    private ReminderTypes type;

    public ReminderType(int index) {
        this.index = index;
        this.type = index == 0 ? ReminderTypes.CHANNEL : ReminderTypes.DMs;
    }

    public ReminderType(boolean isDm) {
        this.index = isDm ? ReminderTypes.DMs.getI() : ReminderTypes.CHANNEL.getI();
        this.type = isDm ? ReminderTypes.DMs : ReminderTypes.CHANNEL;
    }

    public ReminderType(ReminderTypes type) {
        this.index = type.getI();
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public ReminderTypes getType() {
        return type;
    }

    public void setType(ReminderTypes type){
        this.index = type.getI();
        this.type = type;
    }
}
