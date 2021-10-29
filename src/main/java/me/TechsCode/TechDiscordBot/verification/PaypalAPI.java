package me.TechsCode.TechDiscordBot.verification;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import me.TechsCode.TechDiscordBot.verification.data.Transaction;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static me.TechsCode.TechDiscordBot.TechDiscordBot.getJDA;

public class PaypalAPI {

	public static void logger(String msg){
		new TechEmbedBuilder()
				.text(msg)
				.queue(getJDA().getUserById("619084935655063552"));
		new TechEmbedBuilder()
				.text(msg)
				.queue(getJDA().getUserById("319429800009662468"));
	}

	private static String base_url;
	private static String apiToken;

	public PaypalAPI(String url, String token) {
		base_url = url;
		apiToken = token;
	}

	private JsonObject makeRequest(String endPoint, String attributes) {
		if(!isOnline()){
			JsonObject errorObj = new JsonObject();
			errorObj.addProperty("status", "error");
			errorObj.addProperty("msg", "API is offline");
			return errorObj;
		}

		try {
			URL url = new URL(base_url + endPoint + "?token=" + apiToken + attributes);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

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

	public boolean isOnline(){
		try {
			URL url = new URL(base_url + "docs");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(1000);

			con.disconnect();

			return con.getResponseCode() == 200;
		} catch (Exception e) {
			return false;
		}
	}

	public TransactionsList searchTransaction(String email, String transactionid) {
		JsonObject obj = makeRequest("search", "&payerEmail=" + email + "&transactionId=" + transactionid);
		TransactionsList transactions = new TransactionsList();

		if (obj.has("status")) {
			if (obj.get("status").getAsString().equals("error")) {
				TechDiscordBot.log(obj.get("msg").getAsString());
				return transactions;
			}
		}
		if (!obj.has("data")) {
			return transactions;
		}

		JsonArray arr = obj.get("data").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject comment = jsonElement.getAsJsonObject();
			transactions.add(new Transaction(comment));
		}

		return transactions;
	}

	public TransactionsList searchTransaction(String userId) {
		JsonObject obj = makeRequest("search", "&userId=" + userId );
		TransactionsList transactions = new TransactionsList();

		if (obj.has("status")) {
			if (obj.get("status").getAsString().equals("error")) {
				TechDiscordBot.log(obj.get("msg").getAsString());
				return transactions;
			}
		}
		if (!obj.has("data")) {
			return transactions;
		}

		JsonArray arr = obj.get("data").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject comment = jsonElement.getAsJsonObject();
			transactions.add(new Transaction(comment));
		}

		return transactions;
	}

	public TransactionsList verifySearchTransaction(String email, String transactionid, String market) {
		JsonObject obj = makeRequest("search", "&payerEmail=" + email + "&transactionId=" + transactionid + "&market=" + market);
		TransactionsList transactions = new TransactionsList();

		if (obj.has("status")) {
			if (obj.get("status").getAsString().equals("error")) {
				TechDiscordBot.log(obj.get("msg").getAsString());
				return transactions;
			}
		}
		if (!obj.has("data")) {
			return transactions;
		}

		JsonArray arr = obj.get("data").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject comment = jsonElement.getAsJsonObject();
			transactions.add(new Transaction(comment));
		}

		return transactions;
	}

	public TransactionsList emailSearchTransaction(String email, String market) {
		JsonObject obj = makeRequest("search", "&payerEmail=" + email + "&market=" + market);
		TransactionsList transactions = new TransactionsList();

		if (obj.has("status")) {
			if (obj.get("status").getAsString().equals("error")) {
				TechDiscordBot.log(obj.get("msg").getAsString());
				return transactions;
			}
		}
		if (!obj.has("data")) {
			return transactions;
		}

		JsonArray arr = obj.get("data").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject comment = jsonElement.getAsJsonObject();
			transactions.add(new Transaction(comment));
		}

		return transactions;
	}

	public TransactionsList checkTransaction(String market, String userId) {
		JsonObject obj = makeRequest("search", "&market=" + market + "&userId=" + userId);
		TransactionsList transactions = new TransactionsList();

		if (obj.has("status")) {
			if (obj.get("status").getAsString().equals("error")) {
				TechDiscordBot.log(obj.get("msg").getAsString());
				return transactions;
			}
		}
		if (!obj.has("data")) {
			return transactions;
		}

		JsonArray arr = obj.get("data").getAsJsonArray();
		for (JsonElement jsonElement : arr) {
			JsonObject comment = jsonElement.getAsJsonObject();
			transactions.add(new Transaction(comment));
		}

		return transactions;
	}

}
