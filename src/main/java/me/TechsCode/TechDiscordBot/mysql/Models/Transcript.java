package me.TechsCode.TechDiscordBot.mysql.Models;

import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.TechDiscordBot;

public class Transcript {

    private final int id;
    private final String value;

    public Transcript(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public Transcript(String value) {
        this.id = TechDiscordBot.getStorage().getAvailableId(TechDiscordBot.getStorage().TRANSCRIPTS_TABLE);
        this.value = value;
    }

    public Transcript(JsonObject transcript) {
        this.id = transcript.get("id").getAsInt();
        this.value = transcript.toString();
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void save(){
        TechDiscordBot.getStorage().saveTranscript(this);
    }

    public void delete(){
        TechDiscordBot.getStorage().deleteTranscript(this);
    }

}
