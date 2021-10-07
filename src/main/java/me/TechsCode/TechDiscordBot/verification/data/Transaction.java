package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.verification.data.MarketPlace;
import me.TechsCode.TechDiscordBot.verification.data.PayerInfo;
import me.TechsCode.TechDiscordBot.verification.data.Price;

public class Transaction extends JsonSerializable {

    private String id;
    private long initiation_date;
    private long updated_date;
    private Price price;
    private PayerInfo payerInfo;
    private Plugin plugin;
    private MarketPlace marketplace;

    public Transaction(String id, long initiation_date, long updated_date, Price price, PayerInfo payerInfo, Plugin plugin, MarketPlace marketplace){
        this.id = id;
        this.initiation_date = initiation_date;
        this.updated_date = updated_date;
        this.price = price;
        this.payerInfo = payerInfo;
        this.plugin = plugin;
        this.marketplace = marketplace;
    }

    public Transaction(JsonObject state){
        setState(state);
    }

    @Override
    public void setState(JsonObject jsonObject) {
        this.id = jsonObject.get("id").getAsString();
        this.initiation_date = jsonObject.get("initiation_date").getAsLong();
        this.updated_date = jsonObject.get("updated_date").getAsLong();
        this.price = new Price(jsonObject.get("price").getAsJsonObject());
        this.payerInfo = new PayerInfo(jsonObject.get("payerInfo").getAsJsonObject());
        this.plugin = new Plugin(jsonObject.get("plugin").getAsJsonObject());
        this.marketplace = new MarketPlace(jsonObject.get("marketplace").getAsJsonObject());
    }

    @Override
    public JsonObject getState() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("initiation_date", initiation_date);
        jsonObject.addProperty("updated_date", updated_date);
        jsonObject.add("price", price.getState());
        jsonObject.add("payerInfo", payerInfo.getState());
        jsonObject.add("plugin", plugin.getState());
        jsonObject.add("marketplace", marketplace.getState());
        return jsonObject;
    }

    public JsonObject getStateCensored() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("initiation_date", initiation_date);
        jsonObject.addProperty("updated_date", updated_date);
        jsonObject.add("price", price.getState());
        jsonObject.add("payerInfo", payerInfo.getStateCensored());
        jsonObject.add("plugin", plugin.getState());
        jsonObject.add("marketplace", marketplace.getState());
        return jsonObject;
    }

    public String getId() {
        return id;
    }

    public long getInitiation_date() {
        return initiation_date;
    }

    public long getUpdated_date() {
        return updated_date;
    }

    public Price getPrice() {
        return price;
    }

    public PayerInfo getPayerInfo() {
        return payerInfo;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public MarketPlace getMarketplace() {
        return marketplace;
    }
}
