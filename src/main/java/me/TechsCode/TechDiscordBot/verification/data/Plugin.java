package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonObject;

public class Plugin extends JsonSerializable {
    private String nameClean;
    private String name;

    public Plugin(String nameClean, String name) {
        this.nameClean = nameClean;
        this.name = name;
    }

    public Plugin(JsonObject state){
        setState(state);
    }

    @Override
    public void setState(JsonObject jsonObject) {
        this.nameClean = jsonObject.get("cleanName").getAsString();
        this.name = jsonObject.get("name").getAsString();
    }

    @Override
    public JsonObject getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cleanName", nameClean);
        jsonObject.addProperty("name", name);
        return jsonObject;
    }

    public String getNameClean() {
        return nameClean;
    }

    public String getName() {
        return name;
    }
}
