/*package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.logs.VerificationLogs;
import me.TechsCode.TechDiscordBot.spigotmc.data.ProfileComment;
import me.TechsCode.TechDiscordBot.spigotmc.data.Purchase;
import me.TechsCode.TechDiscordBot.spigotmc.data.lists.ProfileCommentList;
import me.TechsCode.TechDiscordBot.util.Plugin;
import me.TechsCode.TechDiscordBot.util.TechEmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static me.TechsCode.TechDiscordBot.TechDiscordBot.*;

public class Spigot {

	private static List<String> verificationQueue;
	private static boolean isDone = false;
	private static String vCode;

	public static boolean verify(GuildMessageReceivedEvent e){
		if(e.getMember() == null)return false;
		verificationQueue = new ArrayList<>();

		TextChannel channel = e.getChannel();

		String username = e.getMessage().getContentDisplay();
		TechEmbedBuilder errorMessage = new TechEmbedBuilder("Error (" + e.getAuthor().getName() + ")").error();

		if (verificationQueue.contains(e.getAuthor().getId())) {
			errorMessage.text("Please follow the instruction above!").sendTemporary(channel, 15);
		}

		if (username.contains(" ")) {
			errorMessage.text("Please type in your SpigotMC Username!").sendTemporary(channel, 5);
		}

		Purchase[] purchases = TechDiscordBot.getSpigotAPI().getSpigotPurchases().username(username).toArray(new Purchase[0]);

		username = purchases[0].getUser().getUsername();
		String userId = purchases[0].getUser().getUserId();
		String avatarUrl = purchases[0].getUser().getAvatar();

		String code = UUID.randomUUID().toString().split("-")[0];

		vCode = "TechVerification." + code;
		TechEmbedBuilder verifyInstructions = new TechEmbedBuilder("Verify " + e.getAuthor().getName())
				.thumbnail(avatarUrl)
				.text("Now go to your SpigotMC Profile and post `" + vCode + "`\n\nLink to your Profile:\nhttps://www.spigotmc.org/members/" + username.toLowerCase() + "." + userId + "\n\n**The bot will check your spigot profile after 3 minutes!**");

		Message m = e.getMessage().getChannel().sendMessage(verifyInstructions.build()).complete();
		verificationQueue.add(e.getAuthor().getId());
		String finalUsername = username;

		new Thread(() -> {
			try {
				Thread.sleep(TimeUnit.MINUTES.toMillis(3));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			ProfileCommentList comments = getSpigotAPI().getSpigotProfileComments(finalUsername + "." + userId, false);

			for (ProfileComment user : comments) {
				if (user.getText().equals(vCode)) {
					m.delete().complete();
					TechDiscordBot.getStorage().createVerification(userId, e.getAuthor().getId());
					verificationQueue.remove(e.getAuthor().getId());

					new TechEmbedBuilder("Verification Complete!")
							.text("You've been successfully verified!\n\nHere are your purchased plugins: " + Plugin.getMembersPluginsinEmojis(e.getMember()) + "\n\n*Your roles will be updated automatically from now on!*")
							.thumbnail(avatarUrl)
							.queue(Objects.requireNonNull(e.getMember()));

					new TechEmbedBuilder()
							.text("You may now delete the message on your profile! [Go to Comment](https://www.spigotmc.org/profile-posts/" + user.getCommentId() + ")")
							.queue(e.getMember());

					String msg = "<@" + e.getMember().getId() + "> verified as https://www.spigotmc.org/members/" + finalUsername.toLowerCase() + "." + userId;
					alertMsg(msg);
					VerificationLogs.log(
							new TechEmbedBuilder(e.getAuthor().getName() + "'s Verification Completed")
									.success().text(e.getAuthor().getName() + " has successfully verified their SpigotMC Account!")
									.thumbnail(avatarUrl)
					);

					isDone = true;
					return;
				}
			}

			verificationQueue.remove(e.getAuthor().getId());
			m.editMessage(errorMessage.text("**You took too long!**\n\nThe Verification process has timed out! Please try again.").build())
					.complete()
					.delete()
					.queueAfter(10, TimeUnit.SECONDS);
			isDone = true;
		}).start();

		while (!isDone){
			new Thread(() -> {
				try{
					Thread.sleep(TimeUnit.MINUTES.toMillis(3));
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}).start();
		}

		m.editMessageEmbeds(errorMessage.text("The bot did not find any messages containing `" + vCode + "` on your profile!").build()).complete().delete().completeAfter(20, TimeUnit.SECONDS);

		return true;

	}

	public static TechEmbedBuilder sendInstructions() {
		TechEmbedBuilder embed = new TechEmbedBuilder("Spigot Verification")
				.text("To verify your Spigot purchase we need your spigot username.\n\n**Type in your spigot username below and press enter.**")
				.thumbnail("https://i.ibb.co/tZ0gGZp/728421352721088542a.png");
		return embed;
	}

	private static void alertMsg(String msg) {
		new TechEmbedBuilder()
				.text(msg)
				.queue(getJDA().getUserById("619084935655063552"));
		new TechEmbedBuilder()
				.text(msg)
				.queue(getJDA().getUserById("319429800009662468"));
	}
}
*/