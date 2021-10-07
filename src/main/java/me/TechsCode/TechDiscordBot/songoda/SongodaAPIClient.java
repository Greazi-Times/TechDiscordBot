package me.TechsCode.TechDiscordBot.songoda;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import me.TechsCode.TechDiscordBot.client.APIClient;
import me.TechsCode.TechDiscordBot.spigotmc.data.Cost;
import me.TechsCode.TechDiscordBot.spigotmc.data.Time;
import me.TechsCode.TechDiscordBot.spigotmc.data.User;
import me.TechsCode.TechDiscordBot.util.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SongodaAPIClient extends APIClient {

    private final SongodaPurchaseList purchases;
    private long time;

    public SongodaAPIClient(String token) {
        super(token);

        this.time = 0;
        this.purchases = new SongodaPurchaseList();
    }

    public boolean isLoaded() {
        return purchases.size() != 0;
    }

    public long getRefreshTime() {
        return time;
    }

    public SongodaPurchaseList getSpigotPurchases() {
        return purchases;
    }

    @Override
    public void run() {
        while (true) {
            try {
                URL url = new URL("https://songoda.com/api/dashboard/payments?token=" + getToken() + "&per_page=2000000");
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
                httpcon.connect();

                JsonArray data = JsonParser.parseReader(new InputStreamReader((InputStream) httpcon.getContent())).getAsJsonObject().getAsJsonArray("data");

                this.time = System.currentTimeMillis();
                this.purchases.clear();

                data.forEach(d -> {
                    JsonObject object = d.getAsJsonObject();

                    String product = object.get("product").getAsString();
                    String username = object.get("username").getAsString();
                    String avatar = object.has("avatar") ? object.get("avatar").getAsString() : "https://imgproxy.songoda.com//fit/48/48/sm/0/plain/https://cdn2.songoda.com/avatars/default/avatar_5.png";
                    String currency = object.get("currency").getAsString();
                    float cost = Float.parseFloat(object.get("amount").getAsString());

                    String discord = object.get("discord").isJsonNull() ? null : object.get("discord").getAsString();
                    String discordId = null;
                    JsonObject discordData = object.has("discord_data") && !object.get("discord_data").isJsonNull() ? object.get("discord_data").getAsJsonObject() : new JsonObject();
                    if(discordData.has("id"))
                        discordId = discordData.get("id").getAsString();

                    long createdAt = object.get("created_at").getAsLong() * 1000;
                    int userId = object.get("user_id").getAsInt();

                    SongodaPurchase sp = new SongodaPurchase(Plugin.byEmojiName(product.replace(" ", "")).getResourceId(), new User(String.valueOf(userId), username, avatar), new Time(new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a").format(new Date(createdAt)), createdAt), new Cost(currency, cost), discordId == null ? discord : discordId);

                    this.purchases.add(sp);
                });

                httpcon.disconnect();
            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(3L));
            } catch (InterruptedException ignored) { }
        }
    }
}
