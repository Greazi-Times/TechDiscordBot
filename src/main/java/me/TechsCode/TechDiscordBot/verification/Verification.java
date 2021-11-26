package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.VerificationLogs;
import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.MarketList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationMarketList;
import me.TechsCode.TechDiscordBot.mysql.storage.Storage;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import me.TechsCode.TechDiscordBot.verification.data.MarketPlace;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Verification {

	public static boolean isVerified(Member e) {
		String member = e.getUser().getId();

		DbMember dbMember = TechDiscordBot.getStorage().retrieveMemberByDiscordId(member);
		if(dbMember == null) return false;
		DbVerification existingVerification = dbMember.getVerification();
		if(existingVerification == null) return false;

		return true;
	}

	public static void verify(PrivateMessageReceivedEvent e, DbMember member, Message message){
		String marketList;
		String payerId;

		Storage storage = TechDiscordBot.getStorage();

		VerificationQ verificationQ = member.getVerificationQ();
		DbVerification dbVerification = member.getVerification();

		if(verificationQ == null) return;
		String email = verificationQ.getEmail();
		String transactionId = verificationQ.getTransactionId();
		String market = verificationQ.getMarket().getName();

		TransactionsList check = TechDiscordBot.getPaypalAPI().verifySearchTransaction(email, transactionId, market);
		if(check.isEmpty()){e.getMessage().replyEmbeds(new TechEmbedBuilder("No purchases found").text("The details you have given in do not exist in our system.\n\nIf you think this is a problem than please contact a @Senior-Support member inside <#311178000026566658>!").error().build()).queue(); verificationQ.delete(); return;}

		TransactionsList transactions = TechDiscordBot.getPaypalAPI().emailSearchTransaction(email, market);
		marketList = transactions.stream().map(transaction -> transaction.getPlugin().getName()).collect(Collectors.joining(", "));

		message.editMessageEmbeds(new TechEmbedBuilder("Verification Success").text("Plugins that have been found on your account:\n`" + marketList + "`").success().build()).queue();

		VerificationLogs.log(new TechEmbedBuilder(e.getAuthor().getName() + "'s Verification Completed").success().text(e.getAuthor().getName() + " has successfully verified their SpigotMC Account!").thumbnail(e.getAuthor().getAvatarUrl()));

		verificationQ.delete();
		RoleAssigner.addRoles(marketList, market, member);

		Stream<String> payerIdCheck = transactions.stream().map(transaction -> transaction.getPayerInfo().getId());
		if(payerIdCheck.findFirst().isPresent()){
			payerId = payerIdCheck.findFirst().get();
			new DbVerification(member, payerId);
		}

		DbMarket marketId = storage.retrieveMarketByName(market);
		DbVerification verification = storage.retrieveMemberVerification(member);
		transactions.forEach(transaction -> {
			Resource resource = storage.retrieveResourceByName(transaction.getPlugin().getName());
			verification.addPlugin(marketId, resource, transaction.getId(), transaction.getPayerInfo().toString(), transaction.getPrice().getAmount(), transaction.getInitiation_date(), false);
		});

		String marketUserId = transactions.stream().findFirst().get().getMarketplace().getUserId();
		verification.addMarket(marketId, Integer.parseInt(marketUserId));
	}

	public static void privateWhy(PrivateMessageReceivedEvent e){
		e.getMessage().replyEmbeds(new TechEmbedBuilder("Why we need your email").text("Our new verification system uses paypal to retrieve purchases.\nWith this system we need your e-mail so we cen get your purchases.\n\n*Type* `cancel` *to cancel your verification*").build()).queue();
	}

	public static void privateCancel(PrivateMessageReceivedEvent e, DbMember member){
		e.getMessage().replyEmbeds(new TechEmbedBuilder(member.getVerificationQ().getMarket().getName() + "Verification Canceled").text("Your verification has been canceled.").error().build()).queue();
	}

	public static void privateEmail(PrivateMessageReceivedEvent e, DbMember member){
		e.getMessage().replyEmbeds(new TechEmbedBuilder(member.getVerificationQ().getMarket().getName() + " Verification").text("Now we need a transaction ID from one of your purchases.\n\n*Type* `transactionid` *to get a guide on how to get the ID*\n*Type* `cancel` *to cancel your verification*").build()).complete();
	}

	public static void privateTransactionId(PrivateMessageReceivedEvent e, DbMember member){
		Message message = e.getMessage().replyEmbeds(new TechEmbedBuilder(member.getVerificationQ().getMarket().getName() + " Verification").text("Verifying purchase....").build()).complete();
		verify(e, member, message);
	}
}
