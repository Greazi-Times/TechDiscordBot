package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonObject;

public class MarketPlace extends JsonSerializable {
    private String market;
    private String userId;

    public MarketPlace(String market, String userId) {
        this.market = market;
        this.userId = userId;
    }

    public MarketPlace(JsonObject state){
        setState(state);
    }

    @Override
    public void setState(JsonObject jsonObject) {
        this.market = jsonObject.get("market").getAsString();
        this.userId = jsonObject.get("userId").getAsString();
    }

    @Override
    public JsonObject getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("market", market);
        jsonObject.addProperty("userId", userId);
        return jsonObject;
    }

    public String getMarket() {
        return market;
    }

    public String getUserId() {
        return userId;
    }
}
