package me.TechsCode.TechDiscordBot.spiget;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.DbUpdate;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.ResourcesList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationMarketList;
import me.TechsCode.TechDiscordBot.mysql.Models.Resource;
import me.TechsCode.TechDiscordBot.mysql.Models.Review;
import net.dv8tion.jda.api.entities.Role;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SpigetAPI {

    private final String BASE_URI = "https://api.spiget.org/v2/";
    private final String USER_AGENT  = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";

    private JsonObject makeObjectRequest(String endPoint) {
        if(!isOnline()){
            JsonObject errorObj = new JsonObject();
            errorObj.addProperty("status", "error");
            errorObj.addProperty("msg", "API is offline");
            return errorObj;
        }

        try {
            URL url = new URL(BASE_URI + endPoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            if(con.getResponseCode() == 200){
                Gson gson = new Gson();
                return gson.fromJson(content.toString(), JsonObject.class);
            }else{
                JsonObject errorObj = new JsonObject();
                errorObj.addProperty("status", "error");
                errorObj.addProperty("msg", "API is offline");
                return errorObj;
            }
        } catch (Exception e) {
            JsonObject errorObj = new JsonObject();
            errorObj.addProperty("status", "error");
            errorObj.addProperty("msg", e.getMessage());
            return errorObj;
        }
    }

    private JsonArray makeArrayRequest(String endPoint) {
        JsonArray arr = new JsonArray();

        if(!isOnline()){
            JsonObject errorObj = new JsonObject();
            errorObj.addProperty("status", "error");
            errorObj.addProperty("msg", "API is offline");
            arr.add(errorObj);
            return arr;
        }

        try {
            URL url = new URL(BASE_URI + endPoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.addRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            if(con.getResponseCode() == 200){
                Gson gson = new Gson();
                return gson.fromJson(content.toString(), JsonArray.class);
            }else{
                JsonObject errorObj = new JsonObject();
                errorObj.addProperty("status", "error");
                errorObj.addProperty("msg", "API is offline");
                arr.add(errorObj);
                return arr;
            }
        } catch (Exception e) {
            JsonObject errorObj = new JsonObject();
            errorObj.addProperty("status", "error");
            errorObj.addProperty("msg", e.getMessage());
            arr.add(errorObj);
            return arr;
        }
    }

    public boolean isOnline(){
        try {
            URL url = new URL("https://api.spiget.org/v2/status");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(1000);

            con.disconnect();

            return con.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void fetchResources(String authorId) {
        JsonArray arr = makeArrayRequest("authors/"+authorId+"/resources?size=50");

        for (JsonElement jsonElement : arr) {
            JsonObject resource = jsonElement.getAsJsonObject();
            List<Role> roles = TechDiscordBot.getJDA().getRolesByName(resource.get("name").getAsString(), true);
            TechDiscordBot.log(resource.get("name").getAsString());
            if (roles.stream().findFirst().isPresent()){
                TechDiscordBot.log("found role");
                Role resourceRole = roles.stream().findFirst().get();
                Resource dbResource = new Resource(resource.get("name").getAsString(), resource.get("id").getAsInt(), resourceRole.getIdLong());
                dbResource.save();
            }
        }

    }

    public JsonObject fetchResourceDetails(String resourceId) {
        return makeObjectRequest("resources/"+resourceId);
    }

    public void fetchUpdates(String authorId) {
        ResourcesList resources = TechDiscordBot.getStorage().retrieveResources();
        resources.forEach(resource -> {
            JsonArray arr = makeArrayRequest("resources/"+authorId+"/versions");
            for (JsonElement jsonElement : arr) {
                JsonObject update = jsonElement.getAsJsonObject();
                new DbUpdate(resource, update.get("name").getAsString(), update.get("releaseDate").getAsLong(), update.get("id").getAsLong()).save();
            }
        });
    }

    public void fetchReviews(String authorId) {
        ResourcesList resources = TechDiscordBot.getStorage().retrieveResources();
        resources.forEach(resource -> {
            JsonArray arr = makeArrayRequest("resources/"+authorId+"/reviews");
            for (JsonElement jsonElement : arr) {
                JsonObject update = jsonElement.getAsJsonObject();
                VerificationMarketList verificationMarkets = TechDiscordBot.getStorage().retrieveVerificationMarket(update.get("author").getAsJsonObject().get("id").getAsInt());
                if(!verificationMarkets.stream().findFirst().isPresent()) return;
                DbMember member = verificationMarkets.stream().findFirst().get().getVerification().getMember();
                new Review(member, resource, update.get("id").getAsInt(), update.get("date").getAsLong()).save();
            }
        });
    }

}