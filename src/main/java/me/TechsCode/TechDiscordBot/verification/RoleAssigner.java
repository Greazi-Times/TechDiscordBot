package me.TechsCode.TechDiscordBot.verification;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.objects.RoleManager;
import me.TechsCode.TechDiscordBot.util.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.Objects;

public class RoleAssigner {

	public static void addRoles(String marketList, String market, DbMember dbMember){
		String id = dbMember.getDiscordId();
		Member member = TechDiscordBot.getGuild().getMemberById(id);
		RoleManager roleManager = dbMember.getRoleManager();

		// Check if member has the verified role
		if(!roleManager.hasRole(Roles.VERIFIED())){
			roleManager.addRole(Roles.VERIFIED());
			roleManager.save();
		}

		// Check if the member has the market role
		if(Objects.equals(market, "spigotmc")) {
			roleManager.addRole(Roles.SPIGOT());
			roleManager.save();
		}
		if(Objects.equals(market, "mc-market")) {
			roleManager.addRole(Roles.MCMARKET());
			roleManager.save();
		}
		if(Objects.equals(market, "songoda")) {
			roleManager.addRole(Roles.SONGODA());
			roleManager.save();
		}
		if(Objects.equals(market, "polymart")) {
			roleManager.addRole(Roles.POLYMART());
			roleManager.save();
		}

		// Check if the member has the plugin roles
		String[] plugins = marketList.split(", ");
		for (String plugin : plugins) {
			if (plugin.equals("Ultra Permissions")) {
				roleManager.addRole(Roles.ULTRA_PERMISSIONS());
				roleManager.save();
			}
			if (plugin.equals("Ultra Customizer")) {
				roleManager.addRole(Roles.ULTRA_CUSTOMIZER());
				roleManager.save();
			}
			if (plugin.equals("Ultra Economy")) {
				roleManager.addRole(Roles.ULTRA_ECONOMY());
				roleManager.save();
			}
			if (plugin.equals("Ultra Regions")) {
				roleManager.addRole(Roles.ULTRA_REGIONS());
				roleManager.save();
			}
			if (plugin.equals("Ultra Punishments")) {
				roleManager.addRole(Roles.ULTRA_PUNISHMENTS());
				roleManager.save();
			}
			if (plugin.equals("Ultra Scoreboards")) {
				roleManager.addRole(Roles.ULTRA_SCOREBOARDS());
				roleManager.save();
			}
			if (plugin.equals("Insane Shops")) {
				roleManager.addRole(Roles.INSANE_SHOPS());
				roleManager.save();
			}
		}
	}
}
