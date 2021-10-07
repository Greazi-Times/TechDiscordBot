package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.VerificationLogs;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class Verification {

	public static void Verification(Member m, String selectedmarket, TextChannel channel){
		User user = m.getUser();
		String id = m.getId();

		if(TechDiscordBot.getStorage().isVerificationQ(id)) {
			new TechEmbedBuilder("ERROR "+user.getAsMention()).error().text("You have already started a verification process please finish it first!").sendTemporary(channel, 10);
			return;
		}

		try {
			user.openPrivateChannel().complete()
				.sendMessageEmbeds(new TechEmbedBuilder(selectedmarket + " Verification").text("Welcome to the verification system.\nTo verify you have bought one or more of our plugins, we will need some information from you.\n\n"+
						"**What is your paypal e-mail?**\n\n*Type* `why` *to get know why we need your e-mail*\n*Type* `cancel` *to cancel your verification*").build()).queue();
			TechDiscordBot.getStorage().createVerificationQ(id, selectedmarket);
		} catch (ErrorResponseException ignored) {
			new TechEmbedBuilder("ERROR "+user.getAsMention()).error().text("The verification process could not be started!\n\nYou have disabled your DM's please enable them to verify your purchase!").sendTemporary(channel, 10);
		}
	}

	public static boolean isVerified(ButtonClickEvent e, TextChannel channel, TechEmbedBuilder errorMessage) {
		Member member = e.getMember();
		assert member != null;

		me.TechsCode.TechDiscordBot.mysql.storage.Verification existingVerification = TechDiscordBot.getStorage().retrieveVerificationWithDiscord(member.getId());
		if (existingVerification != null) {
			errorMessage.text("You've already linked to your Marketplace Account and your roles will be updated automatically!").sendTemporary(channel, 10);
			return true;
		}
		return false;
	}

	public static void verify(PrivateMessageReceivedEvent e, String id, String message){
		String email = TechDiscordBot.getStorage().getVerificationQEmail(id);
		String transactionId = TechDiscordBot.getStorage().getVerificationQTransaction(id);

		TransactionsList transactions = TechDiscordBot.getPaypalAPI().searchTransaction(email, transactionId);
		TechDiscordBot.log(transactions.toString());

		VerificationLogs.log(new TechEmbedBuilder(e.getAuthor().getName() + "'s Verification Completed")
				.success().text(e.getAuthor().getName() + " has successfully verified their SpigotMC Account!"));
	}
}
