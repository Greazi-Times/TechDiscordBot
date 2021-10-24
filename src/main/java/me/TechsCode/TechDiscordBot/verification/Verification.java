package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.VerificationLogs;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerfication;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerficationMarketList;
import me.TechsCode.TechDiscordBot.mysql.Models.VerficationQ;
import me.TechsCode.TechDiscordBot.util.RoleAssigner;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.stream.Collectors;

public class Verification {


	public static void Verification(Member m, DbMarket market, TextChannel channel){
		User user = m.getUser();
		String discordId = m.getId();

		DbMember member = TechDiscordBot.getStorage().retrieveMemberByDiscordId(discordId);

		if(isVerified(m)) {
			TechDiscordBot.getStorage().retrieveMemberByDiscordId(discordId).getVerificationQ().delete();
			new TechEmbedBuilder("ERROR "+user.getAsMention()).error().text("Hello there,\nYou have previously verified yourself and your roles will be automatically updated as a result.\n\nThis update may take 15 to 20 minutes to complete.").sendTemporary(channel, 10);
			return;
		}
		if(TechDiscordBot.getStorage().verficationQExists(member)) {
			new TechEmbedBuilder("ERROR "+user.getAsMention()).error().text("You have already started a verification process please finish it first!").sendTemporary(channel, 10);
			return;
		}

		try {
			user.openPrivateChannel().complete()
				.sendMessageEmbeds(new TechEmbedBuilder(market.getName() + " Verification").text("Welcome to the verification system.\nTo verify you have bought one or more of our plugins, we will need some information from you.\n\n"+
						"**What is your paypal e-mail?**\n\n*Type* `why` *to get know why we need your e-mail*\n*Type* `cancel` *to cancel your verification*").build()).queue();
			new VerficationQ(member, market, "", "").save();
		} catch (ErrorResponseException ignored) {
			new TechEmbedBuilder("ERROR "+user.getAsMention()).error().text("The verification process could not be started!\n\nYou have disabled your DM's please enable them to verify your purchase!").sendTemporary(channel, 10);
		}
	}

	public static boolean isVerified(Member e) {
		String member = e.getUser().getId();

		DbMember dbMember = TechDiscordBot.getStorage().retrieveMemberByDiscordId(member);
		DbVerfication existingVerification = dbMember.getVerification();
		if(existingVerification == null) return false;

		VerficationMarketList markets = existingVerification.getMarkets();
		DbMarket qMarket = dbMember.getVerificationQ().getMarket();

		return markets.market(qMarket).stream().findFirst().isPresent();
	}

	public static void verify(PrivateMessageReceivedEvent e, DbMember member, Message message){
		String marketList;
		VerficationQ verficationQ = member.getVerificationQ();

		if(verficationQ == null) return;
		String email = verficationQ.getEmail();
		String transactionId = verficationQ.getTransactionId();
		String market = verficationQ.getMarket().getName();

		TransactionsList check = TechDiscordBot.getPaypalAPI().verifySearchTransaction(email, transactionId, market);
		if(check.isEmpty()){e.getMessage().replyEmbeds(new TechEmbedBuilder("No purchases found").text("The details you have given in do not exist in our system.\n\nIf you think this is a problem than please contact a @Senior-Support member inside <#311178000026566658>!").error().build()).queue(); verficationQ.delete(); return;}

		TransactionsList transactions = TechDiscordBot.getPaypalAPI().emailSearchTransaction(email, market);
		marketList = transactions.stream().map(transaction -> transaction.getPlugin().getName()).collect(Collectors.joining(", "));

		message.editMessageEmbeds(new TechEmbedBuilder("Verfication Success").text("Plugins that have been found on your account:\n`" + marketList + "`").build()).queue();

		VerificationLogs.log(new TechEmbedBuilder(e.getAuthor().getName() + "'s Verification Completed").success().text(e.getAuthor().getName() + " has successfully verified their SpigotMC Account!"));

		verficationQ.delete();
		RoleAssigner.addRoles(marketList, market, member);
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
