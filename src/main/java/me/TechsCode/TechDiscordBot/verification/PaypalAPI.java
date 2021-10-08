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

	public static void privateWhy(PrivateMessageReceivedEvent e){
		e.getMessage().replyEmbeds(new TechEmbedBuilder("Why we need your email").text("Our new verification system uses paypal to retrieve purchases.\nWith this system we need your e-mail so we cen get your purchases.\n\n*Type* `cancel` *to cancel your verification*").build()).queue();
	}
	public static void privateCancel(PrivateMessageReceivedEvent e, String id){
		//TechDiscordBot.getStorage().retrieveMemberByDiscordId()
		e.getMessage().replyEmbeds(new TechEmbedBuilder("Verification Canceled").text("Your verification has been canceled.").error().build()).queue();
	}
	public static void privateEmail(PrivateMessageReceivedEvent e, String id, String message){
		//TechDiscordBot.getStorage().addEmailVerificationQ(id, message);
		//e.getMessage().replyEmbeds(new TechEmbedBuilder(TechDiscordBot.getStorage().getSelectedVerificationQ(id) + " Verification").text("Now we need a transaction ID from one of your purchases.\n\n*Type* `transactionid` *to get a guide on how to get the ID*\n*Type* `cancel` *to cancel your verification*").build()).complete();
	}
	public static void privateId(PrivateMessageReceivedEvent e, String id, String message){
		//TechDiscordBot.getStorage().addTransactionIdVerificationQ(id, message);
		//e.getMessage().replyEmbeds(new TechEmbedBuilder(TechDiscordBot.getStorage().getSelectedVerificationQ(id) + " Verification").text("Your purchase will now be verified.").success().build()).complete();
		Verification.verify(e, id, message);
	}

	public static boolean givenEmail(String id){
		//String email = TechDiscordBot.getStorage().getVerificationQEmail(id);
		//TechDiscordBot.log(email);
		//return Objects.equals(email, "1");
		return false;
	}

}
