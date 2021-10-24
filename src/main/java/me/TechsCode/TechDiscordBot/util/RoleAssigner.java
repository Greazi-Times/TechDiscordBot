package me.TechsCode.TechDiscordBot.util;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.Objects;

public class RoleAssigner {

	public static void addRoles(String marketList, String market, DbMember member){
		if(Objects.equals(market, "spigotmc")) {
			long id = member.getId();
			User user = User.fromId(id);
			String roles = user.getJDA().getRoles().toString();
			TechDiscordBot.log(roles);
			//TODO JDA roll getter + Role assigner system needs to be made.

		} if(Objects.equals(market, "mc-market")) {

		} if(Objects.equals(market, "songoda")) {

		} if(Objects.equals(market, "polymart")) {

		}
		String[] plugins = marketList.split(", ");
	}
}
