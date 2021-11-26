package me.TechsCode.TechDiscordBot;

import me.TechsCode.TechDiscordBot.module.ModulesManager;
import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.MemberList;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.VerificationList;
import me.TechsCode.TechDiscordBot.mysql.MySQLSettings;
import me.TechsCode.TechDiscordBot.mysql.storage.Storage;
import me.TechsCode.TechDiscordBot.objects.ChannelQuery;
import me.TechsCode.TechDiscordBot.objects.Query;
import me.TechsCode.TechDiscordBot.reminders.ReminderManager;
import me.TechsCode.TechDiscordBot.spiget.SpigetAPI;
import me.TechsCode.TechDiscordBot.util.Config;
import me.TechsCode.TechDiscordBot.util.ConsoleColor;
import me.TechsCode.TechDiscordBot.util.PterodactylAPI;
import me.TechsCode.TechDiscordBot.util.Roles;
import me.TechsCode.TechDiscordBot.verification.PaypalAPI;
import me.TechsCode.TechDiscordBot.verification.data.Lists.TransactionsList;
import me.TechsCode.TechDiscordBot.verification.data.Transaction;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TechDiscordBot {

    private static JDA jda;
    private static TechDiscordBot i;

    private static Guild guild;
    private static Member self;

    private static PaypalAPI paypalAPI;
    private static SpigetAPI spigetAPI;
//    private static SongodaAPIClient songodaAPIClient;
//    private static List<SongodaPurchase> songodaPurchases;

    private static Storage storage;

    private static String githubToken;

    private static ModulesManager modulesManager;
    private static ReminderManager remindersManager;

    private static PterodactylAPI pterodactylAPI;

    public static void main(String[] args) {
        if (!Config.getInstance().isConfigured()) {
            log(ConsoleColor.RED + "Invalid config file. Please enter the information in config.json");
            return;
        }

        try {
            new TechDiscordBot(Config.getInstance().getToken(), Config.getInstance().getApiToken(), Config.getInstance().getSongodaApiToken(), MySQLSettings.of(Config.getInstance().getMySqlHost(), Config.getInstance().getMySqlPort(), Config.getInstance().getMySqlDatabase(), Config.getInstance().getMySqlUsername(), Config.getInstance().getMySqlPassword()), Config.getInstance().getGithubToken(), Config.getInstance().getPteroUrl(), Config.getInstance().getPteroClientToken(), Config.getInstance().getPteroApiToken());
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public TechDiscordBot(String token, String apiToken, String songodaApiToken, MySQLSettings mySQLSettings, String githubToken, String pteroUrl, String pteroClientToken, String pteroApiToken) throws LoginException, InterruptedException {
        i = this;

        jda = JDABuilder.createDefault(token)
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.DEFAULT | GatewayIntent.GUILD_MEMBERS.getRawValue() | GatewayIntent.GUILD_BANS.getRawValue()))
                .setDisabledIntents(GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_TYPING)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setActivity(Activity.watching("for help."))
                .setEventManager(new AnnotatedEventManager())
                .build().awaitReady();

        List<Guild> guilds = jda.getGuilds();

        if(guilds.size() > 2) {
            log(ConsoleColor.RED + "The bot is a member of too many guilds. Please leave them all except two!");
            return;
        }

        if(guilds.size() == 0) {
            log(ConsoleColor.RED + "The bot is not a member of any guild. Please join a guild!");
            return;
        }

        guild = jda.getGuildById("311178000026566658"); //Get main guild
        if (guild == null){
            guild = jda.getGuildById("877248189424615525"); //Get test guild if main not exists
        }

        self = guild != null ? guild.getSelfMember() : null;

        if(guild == null) {
            log(ConsoleColor.RED + "The bot is not in the right guild!");
            return;
        }

        TechDiscordBot.githubToken = githubToken;

        paypalAPI = new PaypalAPI("https://paypalapi.techscode.com/", apiToken);
        spigetAPI = new SpigetAPI();

//        songodaAPIClient = new SongodaAPIClient(songodaApiToken);

        log("Initializing MySQL Storage " + mySQLSettings.getHost() + ":" + mySQLSettings.getPort() + "!");
        storage = Storage.of(mySQLSettings);

        if(!storage.isConnected()) {
            log(ConsoleColor.RED + "Failed to connect to MySQL!");
            log(storage.getLatestErrorMessage());
        }

        pterodactylAPI = new PterodactylAPI();
        pterodactylAPI.setup(pteroUrl, pteroClientToken, pteroApiToken);

        modulesManager = new ModulesManager();
        log("Loading modules..");
        modulesManager.load();

        remindersManager = new ReminderManager();
        log("Loading reminders..");
        remindersManager.load();

        jda.addEventListener(modulesManager);
        jda.addEventListener(remindersManager);

        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.OFF);

        log("Successfully loaded the bot and logged into " + guild.getName() + " as " + self.getEffectiveName() + "!");

        log("Fetching spigot resources");
        getSpigetAPI().fetchResources("29620");
        log("Done fetching spigot resources");

        log("");

        log("Guild:");
        log("  > Members: " + getGuild().getMembers().size());
        log("  > Verified Members: TODO"); //TODO Verified Members
        log("  > Review Squad Members: " + getGuild().getMembers().stream().filter(member -> member.getRoles().stream().anyMatch(role -> role.getName().equals("Review Squad"))).count());
        log("  > Donators: " + getGuild().getMembers().stream().filter(member -> member.getRoles().stream().anyMatch(role -> role.getName().contains("Donator"))).count());

        log("");

//        HashMap<Integer, String> oldVerifications = getStorage().retrieveOldVerfications();
//        for (Map.Entry<Integer, String> me : oldVerifications.entrySet()) {
//            String discordId = me.getValue();
//            Integer spigotId = me.getKey();
//
//            TransactionsList transactions = getPaypalAPI().searchTransaction(String.valueOf(spigotId));
//            if (transactions.isEmpty()) {
//                TechDiscordBot.log("no transactions");
//                continue;
//            }
//
//            Member discordMember = getGuild().getMemberById(discordId);
//            if (discordMember == null) {
//                TechDiscordBot.log("member not found");
//                continue;
//            }
//
//            DbMember dbMember;
//            if(getStorage().memberExists(discordId)){
//                dbMember = getStorage().retrieveMemberByDiscordId(discordId);
//            }else{
//                dbMember = new DbMember(discordId, discordMember.getUser().getName(), discordMember.getTimeJoined().toEpochSecond(), discordMember.getRoles().contains(Roles.STAFF()));
//                dbMember.save();
//            }
//
//            Transaction firstTransaction = transactions.get(0);
//            if (firstTransaction == null) continue;
//
//            String payerId = firstTransaction.getPayerInfo().getId();
//
//            DbVerification dbVerification;
//            if(getStorage().verificationExists(dbMember)){
//                dbVerification = getStorage().retrieveMemberVerification(dbMember);
//            }else{
//                dbVerification = new DbVerification(dbMember, payerId);
//                dbVerification.save();
//            }
//
//            transactions.forEach((transaction) -> {
//                DbMarket dbMarket = getStorage().retrieveMarketByName(transaction.getMarketplace().getMarket());
//                if (dbMarket == null) {
//                    TechDiscordBot.log("Market with name "+transaction.getMarketplace().getMarket()+" not found");
//                    return;
//                }
//
//                Resource resource = getStorage().retrieveResourceByName(transaction.getPlugin().getName());
//                if (resource == null) {
//                    TechDiscordBot.log("Resource with name "+transaction.getPlugin().getName()+" not found");
//                    return;
//                }
//
//                if(!getStorage().verificationMarketExists(dbVerification, dbMarket)){
//                    dbVerification.addMarket(dbMarket, Integer.parseInt(transaction.getMarketplace().getUserId()));
//                    log("added market: "+dbMarket.getName());
//                }
//
//                if(!getStorage().verificationPluginExists(dbVerification, dbMarket, resource)){
//                    dbVerification.addPlugin(dbMarket, resource, transaction.getId(), transaction.getPayerInfo().getState().toString(), transaction.getPrice().getAmount(), transaction.getInitiation_date(), false);
//                    log("added plugin: "+resource.getName());
//                }
//
//            });
//        }

//        List<Member> members = getGuild().getMembers();
//        members.forEach(member -> {
//            if (!getStorage().memberExists(member.getId())){
//                DbMember dbMember = new DbMember(member.getId(), member.getUser().getName(), member.getTimeJoined().toEpochSecond(), member.getRoles().contains(Roles.STAFF()));
//                dbMember.save();
//                log("imported "+member.getUser().getName());
//            }
//        });

//        for (DbVerification ver : storage.retrieveVerifications()) {
//            Member m = ver.getMember().getDiscordMember();
//            if (m == null) continue;
//            if (!ver.getMarkets().isEmpty()){
//                for (VerificationMarket verificationMarket : ver.getMarkets()){
//                    if (verificationMarket.getMarket().getName().equals("spigotmc")){
//                        TechDiscordBot.getGuild().addRoleToMember(m, Roles.SPIGOT()).queue();
//                    }
//                    if (verificationMarket.getMarket().getName().equals("mcmarket")){
//                        TechDiscordBot.getGuild().addRoleToMember(m, Roles.MCMARKET()).queue();
//                    }
//                    if (verificationMarket.getMarket().getName().equals("songoda")){
//                        TechDiscordBot.getGuild().addRoleToMember(m, Roles.SONGODA()).queue();
//                    }
//                    if (verificationMarket.getMarket().getName().equals("polymart")){
//                        TechDiscordBot.getGuild().addRoleToMember(m, Roles.POLYMART()).queue();
//                    }
//                }
//            }
//        }

        getModulesManager().logLoad();

        log("");
        log("Startup Completed! The bot has successfully started!");
        log("The bot is ready");
    }

    public static JDA getJDA() {
        return jda;
    }

    public static TechDiscordBot getBot() {
        return i;
    }

    public static Guild getGuild() {
        return guild;
    }

    public static Member getSelf() {
        return self;
    }

    public static Storage getStorage() {
        return storage;
    }

    public static PaypalAPI getPaypalAPI() {
        return paypalAPI;
    }

    public static PterodactylAPI getPterodactylAPI(){return pterodactylAPI;};

//    public static SongodaAPIClient getSongodaAPI() {
//        return songodaAPIClient;
//    }

    public static ModulesManager getModulesManager() {
        return modulesManager;
    }

    public static ReminderManager getRemindersManager() {
        return remindersManager;
    }

    public Query<Role> getRoles(String... names) {
        List<Role> roles = Arrays.stream(names).flatMap(name -> guild.getRolesByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(roles);
    }

    public ChannelQuery getChannels(String... names) {
        List<TextChannel> channels = Arrays.stream(names).flatMap(name -> guild.getTextChannelsByName(name, true).stream()).collect(Collectors.toList());
        return new ChannelQuery(channels);
    }

    public TextChannel getChannel(String id) {
        return guild.getTextChannelById(id);
    }

    public Query<Category> getCategories(String... names) {
        List<Category> channels = Arrays.stream(names).flatMap(name -> guild.getCategoriesByName(name, true).stream()).collect(Collectors.toList());
        return new Query<>(channels);
    }

    public Query<Member> getMembers(String... names) {
        List<Member> channels = Arrays.stream(names).flatMap(name -> guild.getMembersByName(name, true).stream()).collect(Collectors.toList());
        return new Query<>(channels);
    }

    public Member getMember(String id) {
        return guild.getMemberById(id);
    }

    public Query<Emote> getEmotes(String... names) {
        List<Emote> emotes = Arrays.stream(names).flatMap(name -> guild.getEmotesByName(name, true).stream()).collect(Collectors.toList());

        return new Query<>(emotes);
    }

    public static Member getMemberFromString(Message msg, String s) {
        if (msg.getMentionedMembers().size() > 0) {
            return msg.getMentionedMembers().get(0);
        } else if (getGuild().getMembers().stream().anyMatch(mem -> (mem.getUser().getName() + "#" + mem.getUser().getDiscriminator()).equalsIgnoreCase(s) || mem.getUser().getId().equalsIgnoreCase(s))) {
            return getGuild().getMembers().stream().filter(mem -> (mem.getUser().getName() + "#" + mem.getUser().getDiscriminator()).equalsIgnoreCase(s) || mem.getUser().getId().equalsIgnoreCase(s)).findFirst().orElse(null);
        }
        return null;
    }

    public static boolean isStaff(Member member) {
        return member.getRoles().stream().anyMatch(r -> r.getName().contains("Supporter") || r.getName().contains("Staff"));
    }

    public static void log(String prefix, String message) {
        System.out.println(prefix + " " + ConsoleColor.RESET + message + ConsoleColor.RESET);
    }

    public static void log(String message) {
        System.out.println("[TechPluginSupport] " + message + ConsoleColor.RESET);
    }

    public static String getGithubToken() {
        return githubToken;
    }

    public static SpigetAPI getSpigetAPI() {
        return spigetAPI;
    }
}
