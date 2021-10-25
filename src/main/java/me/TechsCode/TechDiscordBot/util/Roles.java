package me.TechsCode.TechDiscordBot.util;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class Roles {
	public static JDA api = TechDiscordBot.getJDA();

	//TODO change to the main discord

	// Staff roles
	public static Role STAFF(){
		return api.getRoleById("877248189483343899");
	}
	public static Role TRUSTED_HELPER(){
		return api.getRoleById("877248189525266452");
	}
	public static Role JUNIOR_SUPPORT(){
		return api.getRoleById("877248189525266453");
	}
	public static Role SUPPORT(){
		return api.getRoleById("877248189525266454");
	}
	public static Role SENIOR_SUPPORT(){
		return api.getRoleById("877248189525266455");
	}
	public static Role ASSISTANT(){
		return api.getRoleById("877248189525266458");
	}
	public static Role DEVELOPER(){
		return api.getRoleById("877248189525266459");
	}
	public static Role CODING_WIZARD(){
		return api.getRoleById("877248189525266460");
	}
	public static Role RETIRED(){
		return api.getRoleById("877248189424615533");
	}
	public static Role RETIRED_DEVELOPER(){
		return api.getRoleById("877248189424615534");
	}

	// Verified roles
	public static Role VERIFIED(){
		return api.getRoleById("877248189483343890");
	}
	public static Role SPIGOT(){
		return api.getRoleById("901855109590429776");
	}
	public static Role MCMARKET(){
		return api.getRoleById("877248189424615532");
	}
	public static Role SONGODA(){
		return api.getRoleById("877248189453959197");
	}
	public static Role POLYMART(){
		return api.getRoleById("901854809362153483");
	}
	public static Role SUB_VERIFIED(){
		return api.getRoleById("877248189453959196");
	}

	// Plugin roles
	public static Role PLUGIN_LAB(){
		return api.getRoleById("877248189453959195");
	}
	public static Role ULTRA_ECONOMY(){
		return api.getRoleById("877248189453959194");
	}
	public static Role ULTRA_PERMISSIONS(){
		return api.getRoleById("877248189453959192");
	}
	public static Role ULTRA_SCOREBOARDS(){
		return api.getRoleById("877248189453959193");
	}
	public static Role ULTRA_PUNISHMENTS(){
		return api.getRoleById("877248189453959191");
	}
	public static Role ULTRA_CUSTOMIZER(){
		return api.getRoleById("877248189453959190");
	}
	public static Role ULTRA_REGIONS(){
		return api.getRoleById("877248189453959188");
	}
	public static Role INSANE_SHOPS(){
		return api.getRoleById("877248189453959189");
	}

	// Permission roles
	public static Role KEEPROLES(){
		return api.getRoleById("877248189424615530");
	}
	public static Role MUTED(){
		return api.getRoleById("877248189483343898");
	}
	public static Role PATREON(){
		return api.getRoleById("877248189483343897");
	}
	public static Role ARTIST(){
		return api.getRoleById("877248189483343896");
	}
	public static Role DONATOR(){
		return api.getRoleById("877248189483343895");
	}
	public static Role WIKI_EDITOR(){
		return api.getRoleById("877248189483343894");
	}
	public static Role VERIFIED_CREATOR(){
		return api.getRoleById("877248189483343893");
	}
	public static Role FUNERAL_DONATOR(){
		return api.getRoleById("877248189483343892");
	}
	public static Role REVIEW_SQUAD(){
		return api.getRoleById("877248189483343891");
	}
	public static Role REQUESTED_TRANSFER(){
		return api.getRoleById("877248189424615531");
	}
	public static Role ANNOUNCEMENTS(){
		return api.getRoleById("877248189424615529");
	}
	public static Role UPDATES(){
		return api.getRoleById("877248189424615528");
	}
	public static Role GIVEAWAYS(){
		return api.getRoleById("877248189424615527");
	}
	public static Role PATREON_NEWS(){
		return api.getRoleById("877248189424615526");
	}

	public static ArrayList<String> PLUGIN_ROLES = new ArrayList<String>() {{
		add(ULTRA_PERMISSIONS().getName());
		add(ULTRA_CUSTOMIZER().getName());
		add(ULTRA_PUNISHMENTS().getName());
		add(ULTRA_ECONOMY().getName());
		add(ULTRA_SCOREBOARDS().getName());
		add(ULTRA_ECONOMY().getName());
		add(INSANE_SHOPS().getName());
	}};

	public static ArrayList<String> MARKET_ROLES = new ArrayList<String>() {{
		add(SPIGOT().getName());
		add(MCMARKET().getName());
		add(SONGODA().getName());
		add(POLYMART().getName());
	}};

	public static ArrayList<String> STAFF_ROLES = new ArrayList<String>() {{
		add(CODING_WIZARD().getName());
		add(DEVELOPER().getName());
		add(ASSISTANT().getName());
		add(SENIOR_SUPPORT().getName());
		add(SUPPORT().getName());
		add(JUNIOR_SUPPORT().getName());
		add(STAFF().getName());
	}};

	public static ArrayList<String> STAFF_ROLES_MINOR = new ArrayList<String>() {{
		add(SENIOR_SUPPORT().getName());
		add(SUPPORT().getName());
		add(JUNIOR_SUPPORT().getName());
		add(STAFF().getName());
	}};
}
