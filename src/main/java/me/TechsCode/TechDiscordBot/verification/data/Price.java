package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonObject;

public class Price extends JsonSerializable {
    private String currency;
    private float amount;

    public Price(String currency, float amount){
        this.currency = currency;
        this.amount = amount;
    }

    public Price(JsonObject state){
        setState(state);
    }

    @Override
    public void setState(JsonObject jsonObject) {
        this.currency = jsonObject.get("currency").getAsString();
        this.amount = jsonObject.get("amount").getAsFloat();
    }

    @Override
    public JsonObject getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("currency", currency);
        jsonObject.addProperty("amount", amount);
        return jsonObject;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAmount() {
        return amount;
    }

}
