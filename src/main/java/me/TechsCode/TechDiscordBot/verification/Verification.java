package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.VerificationLogs;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.DbVerfication;
import me.TechsCode.TechDiscordBot.mysql.Models.VerficationQ;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class Verification {

	public static void Verification(Member m, DbMarket market, TextChannel channel){
		User user = m.getUser();
		String discordId = m.getId();

		DbMember member = TechDiscordBot.getStorage().retrieveMemberByDiscordId(discordId);
		//TODO Verfication
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

	public static boolean isVerified(ButtonClickEvent e, TextChannel channel, TechEmbedBuilder errorMessage) {
		Member member = e.getMember();
		assert member != null;

		DbMember dbMember = TechDiscordBot.getStorage().retrieveMemberByDiscordId(member.getId());
		DbVerfication existingVerification = TechDiscordBot.getStorage().retrieveMemberVerfication(dbMember);
		if (existingVerification != null) {
			errorMessage.text("You've already linked to your Marketplace Account and your roles will be updated automatically!").sendTemporary(channel, 10);
			return true;
		}
		return false;
	}

	public static void verify(PrivateMessageReceivedEvent e, int id, String message){
		VerficationQ verficationQ = TechDiscordBot.getStorage().retrieveVerficationQById(id);
		if(verficationQ == null)return;
		String email = verficationQ.getEmail();
		String transactionId = verficationQ.getTransactionId();

		TransactionsList transactions = TechDiscordBot.getPaypalAPI().searchTransaction(email, transactionId);
		TechDiscordBot.log(transactions.toString());

		VerificationLogs.log(new TechEmbedBuilder(e.getAuthor().getName() + "'s Verification Completed")
				.success().text(e.getAuthor().getName() + " has successfully verified their SpigotMC Account!"));
	}
}
