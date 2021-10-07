package me.TechsCode.TechDiscordBot.verification.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Dataset {

    private final long timeCreated;

    private final TransactionsList transactions;

    public Dataset(JsonObject jsonObject){
        this.transactions = StreamSupport.stream(jsonObject.getAsJsonArray("transactions").spliterator(), false)
                .map(state -> new me.TechsCode.TechDiscordBot.verification.data.Transaction((JsonObject) state))
                .collect(Collectors.toCollection(TransactionsList::new));

        this.timeCreated = jsonObject.get("timeCreated").getAsLong();

        this.transactions.forEach(x -> x.inject(this));
    }

    public Dataset(long timeCreated, TransactionsList transactions) {
        this.timeCreated = timeCreated;
        this.transactions = transactions;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public JsonObject toJsonObject(){
        JsonObject jsonObject = new JsonObject();

        JsonArray transactionsArray = new JsonArray();

        this.transactions.stream().map(me.TechsCode.TechDiscordBot.verification.data.Transaction::getState).forEach(transactionsArray::add);

        jsonObject.add("transactions", transactionsArray);
        jsonObject.addProperty("timeCreated", timeCreated);

        return jsonObject;
    }
}
