package me.TechsCode.TechDiscordBot.module.modules;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.module.Module;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.VerficationQ;
import me.TechsCode.TechDiscordBot.objects.Requirement;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import me.TechsCode.TechDiscordBot.verification.Verification;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class PrivateMessageEvent extends Module {

	public PrivateMessageEvent(TechDiscordBot bot) {
		super(bot);
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public String getName() {
		return "PrivateMessage";
	}

	@Override
	public Requirement[] getRequirements() {
		return new Requirement[0];
	}

	@SubscribeEvent
	public void OnPrivateMessage(PrivateMessageReceivedEvent e) {
		String id = e.getAuthor().getId();
		String message = e.getMessage().getContentRaw();
		String[] array = message.trim().split(" ");

		if (e.getAuthor().isBot()) return;
		DbMember member = TechDiscordBot.getStorage().retrieveMemberByDiscordId(id);
		VerficationQ verificationQ = member.getVerificationQ();

		if (member.isInVerificationQ()) {
			if (array.length == 1 && message.contains("why")) {
				Verification.privateWhy(e);

			} else if (array.length == 1 && message.contains("cancel")) {
				Verification.privateCancel(e, member);

			} else if (member.getVerificationQ().getEmail().equals("")) {
				if (array.length == 1 && message.contains("@") && message.contains(".")) {
					verificationQ.setEmail(message);
					verificationQ.save();

					Verification.privateEmail(e, member);

				} else {
					e.getMessage().replyEmbeds(new TechEmbedBuilder("ERROR").text("Your message does not contain a email address").error().build()).complete();

				}
			} else if (array.length == 1 && check(message)) {
				verificationQ.setTransactionId(message);
				verificationQ.save();

				Verification.privateTransactionId(e, member);

			} else {
				e.getMessage().replyEmbeds(new TechEmbedBuilder(member.getVerificationQ().getMarket().getName() + " Verification").text("Somethings is wrong please type `cancel` to cancel the verification and try again. If the new one fails as well please contact a staff member.").error().build()).complete();

			}
		} else {
			e.getMessage().addReaction("👀").queue();
		}
	}

	private boolean check(String s) {
		if (s == null) { // checks if the String is null
			return false;
		}
		int len = s.length();
		for (int i = 0; i < len; i++) {
			// checks whether the character is neither a letter nor a digit
			// if it is neither a letter nor a digit then it will return false
			if ((!Character.isLetterOrDigit(s.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
}

