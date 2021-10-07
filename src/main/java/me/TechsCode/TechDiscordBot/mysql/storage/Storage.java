package me.TechsCode.TechDiscordBot.mysql.storage;

import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.mysql.MySQL;
import me.TechsCode.TechDiscordBot.mysql.MySQLSettings;
import me.TechsCode.TechDiscordBot.reminders.Reminder;
import me.TechsCode.TechDiscordBot.reminders.ReminderType;
import me.TechsCode.TechDiscordBot.spigotmc.data.Update;
import me.TechsCode.TechDiscordBot.verification.data.MarketPlace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Storage {

    private final MySQL mysql;
    private boolean connected;

    private final String VERIFICATIONS_TABLE = "Verification";
    private final String PURCHASEDPLUGINS_TABLE = "PurchasedPlugins";
    private final String SUBVERIFIED_TABLE = "SubVerified";
    private final String MEMBERS_TABLE = "Members";
    private final String VERIFICATIONQ_TABLE = "VerificationQ";
    private final String VERIFICATIONMARKETS_TABLE = "VerificationMarkets";
    private final String PUNISHMENTS_TABLE = "Punishments";
    private final String REMINDERS_TABLE = "Reminders";
    private final String TRANSCRIPTS_TABLE = "Transcripts";
    private final String RESOURCES_TABLE = "Resources";
    private final String UPDATES_TABLE = "Updates";
    private final String REVIEWS_TABLE = "Reviews";
    private final String MARKETS_TABLE = "Markets";

    private Storage(MySQLSettings mySQLSettings) {
        this.connected = false;
        this.mysql = MySQL.of(mySQLSettings);

        createDefault();
    }

    public static Storage of(MySQLSettings mySQLSettings) {
        return new Storage(mySQLSettings);
    }

    public String getLatestErrorMessage() {
        return mysql.getLatestErrorMessage();
    }

    public boolean isConnected() {
        return connected;
    }

    public void createDefault() {
        //Create tables
        mysql.update("CREATE TABLE `"+MARKETS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT , `name` varchar(100) NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+MEMBERS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `discordID` varchar(100) NOT NULL, `name` varchar(100) NOT NULL, `joined` timestamp NOT NULL, `staff` boolean NOT NULL , PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+PUNISHMENTS_TABLE+"`(`type` varchar(100) NOT NULL, `memberId` int NOT NULL, `reason` text NOT NULL, `date` timestamp NOT NULL, `expired` timestamp NOT NULL, `punisherId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`),KEY `fkIdx_119` (`memberId`), CONSTRAINT `FK_117` FOREIGN KEY `fkIdx_119` (`memberId`) REFERENCES `Members` (`id`),KEY `fkIdx_123` (`punisherId`), CONSTRAINT `FK_121` FOREIGN KEY `fkIdx_123` (`punisherId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE `"+PURCHASEDPLUGINS_TABLE+"`(`marketId` int NOT NULL, `transactionID` varchar(255) NOT NULL, `purchaseData` varchar(255) NOT NULL, `reviewed` boolean NOT NULL DEFAULT false, `verificationID` int NOT NULL, `resourceId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_107` (`marketId`), CONSTRAINT `FK_105` FOREIGN KEY `fkIdx_107` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_110` (`verificationID`), CONSTRAINT `FK_108` FOREIGN KEY `fkIdx_110` (`verificationID`) REFERENCES `Verification` (`id`),KEY `fkIdx_116` (`resourceId`),CONSTRAINT `FK_114` FOREIGN KEY `fkIdx_116` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE `"+REMINDERS_TABLE+"`(`channelID` varchar(100) NOT NULL, `time` timestamp NOT NULL, `type` varchar(100) NOT NULL, `reminder` text NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `memberId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_113` (`memberId`), CONSTRAINT `FK_111` FOREIGN KEY `fkIdx_113` (`memberId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE `"+RESOURCES_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `name` varchar(100) NOT NULL, `spigotID` int NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+REVIEWS_TABLE+"`(`memberId` int NOT NULL, `date` timestamp NOT NULL, `reviewID` int NOT NULL, `resourceId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_132` (`memberId`), CONSTRAINT `FK_130` FOREIGN KEY `fkIdx_132` (`memberId`) REFERENCES `Members` (`id`), KEY `fkIdx_135` (`resourceId`), CONSTRAINT `FK_133` FOREIGN KEY `fkIdx_135` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE `"+SUBVERIFIED_TABLE+"`(`subMembersId` int NOT NULL, `verificationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMEN, PRIMARY KEY (`id`), KEY `fkIdx_126` (`subMembersId`), CONSTRAINT `FK_124` FOREIGN KEY `fkIdx_126` (`subMembersId`) REFERENCES `Members` (`id`),KEY `fkIdx_129` (`verificationId`), CONSTRAINT `FK_127` FOREIGN KEY `fkIdx_129` (`verificationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE `"+TRANSCRIPTS_TABLE+"`(`id` int NOT NULL, `value` text NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+UPDATES_TABLE+"`(`resourceId` int NOT NULL, `version` varchar(100) NOT NULL, `date` timestamp NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_138` (`resourceId`), CONSTRAINT `FK_136` FOREIGN KEY `fkIdx_138` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONS_TABLE+"`(`memberId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `payerID` varchar(100) NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_89` (`memberId`), CONSTRAINT `FK_87` FOREIGN KEY `fkIdx_89` (`memberId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONMARKETS_TABLE+"`(`marketId` int NOT NULL, `verficationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `userID` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_100` (`marketId`), CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_100` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_103` (`verficationId`), CONSTRAINT `FK_101` FOREIGN KEY `fkIdx_103` (`verficationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONQ_TABLE+"`(`memberId` int NOT NULL, `marketId` int NOT NULL, `email` varchar(100) NOT NULL, `transactionId` varchar(100) NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_96` (`memberId`), CONSTRAINT `FK_94` FOREIGN KEY `fkIdx_96` (`memberId`) REFERENCES `Members` (`id`));");

        this.connected = true;
    }

    //-----------------------------
    //---------REMINDERS-----------
    //-----------------------------
    public Set<Reminder> retrieveReminders() {
        Set<Reminder> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REMINDERS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Reminder(rs.getString("user_id"), rs.getString("channel_id"), Long.parseLong(rs.getString("time")), null, (rs.getInt("type") == 0 ? ReminderType.CHANNEL : ReminderType.DMs), rs.getString("reminder")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveReminder(Reminder reminder) {
        mysql.update("INSERT INTO " + REMINDERS_TABLE + " (user_id, channel_id, time, type, reminder, message_id) VALUES ('" + reminder.getUserId() + "', " + (reminder.getChannelId() == null ? "NULL" : "'" + reminder.getChannelId() + "'") + ", '" + reminder.getTime() + "', " + reminder.getType().getI() + ", '" + reminder.getReminder().replace("'", "''") + "', 'XXXX');");
    }

    public void deleteReminder(Reminder reminder) {
        mysql.update("DELETE FROM " + REMINDERS_TABLE + " WHERE user_id='" + reminder.getUserId() + "' AND channel_id='" + (reminder.getChannelId() == null ? "NULL" : "'" + reminder.getChannelId() + "'") + "' AND time='" + reminder.getTime() + "' AND type=" + reminder.getType().getI() + " AND reminder='" + reminder.getReminder().replace("'", "''") + "';");
    }

    //-----------------------------
    //--------TRANSCRIPTS----------
    //-----------------------------
    public void saveTranscript(JsonObject transcript) {
        mysql.update("INSERT INTO " + TRANSCRIPTS_TABLE + " (id, value) VALUES (?, ?);", transcript.get("id").getAsString(), transcript.toString());
    }

    //-----------------------------
    //----------MARKETS------------
    //-----------------------------
    public Set<DbMarket> retrieveMarkets() {
        Set<DbMarket> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new DbMarket(rs.getInt("id"), rs.getString("name")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public DbMarket retrieveMarketByName(String name) {
        DbMarket market = new DbMarket(0, "");
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `name`='"+name+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                market = new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return market;
    }

    public DbMarket retrieveMarketById(int id) {
        DbMarket market = new DbMarket(0, "");
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `id`='"+id+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                market = new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return market;
    }

    public void saveMarket(String marketName) {
        mysql.update("INSERT INTO " + MARKETS_TABLE + " (id, Name) VALUES (NULL, '"+marketName+"');");
    }

    public void deleteMarketByName(String marketName) {
        mysql.update("DELETE FROM " + MARKETS_TABLE + " WHERE Name='" + marketName + "';");
    }

    public void deleteMarketById(int Id) {
        mysql.update("DELETE FROM " + MARKETS_TABLE + " WHERE id='" + Id + "';");
    }

    //-----------------------------
    //----------MEMBERS------------
    //-----------------------------
    public Set<DbMember> retrieveMembers() {
        Set<DbMember> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public DbMember retrieveMemberByDiscordId(String discordId) {
        DbMember member = new DbMember(0, "", "", 0, false);
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `discordId`='"+discordId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                member = new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    public DbMember retrieveMemberById(int id) {
        DbMember member = new DbMember(0, "", "", 0, false);
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `id`='"+id+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                member = new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    public void saveMember(DbMember dbMember) {
        mysql.update("INSERT INTO " + MEMBERS_TABLE + " (id, discordID, name, joined, staff) VALUES (NULL, '" + dbMember.getDiscordId() + "', '" + dbMember.getName() + "', '" + dbMember.getJoined() + "', '" + dbMember.isStaff() + "');");
    }

    public void deleteMember(DbMember dbMember) {
        mysql.update("DELETE FROM " + MEMBERS_TABLE + " WHERE id='" + dbMember.getId() + "';");
    }

    public void deleteMemberById(int id) {
        mysql.update("DELETE FROM " + MEMBERS_TABLE + " WHERE id='" + id + "';");
    }

    public void deleteMemberByDiscordId(String discordId) {
        mysql.update("DELETE FROM " + MEMBERS_TABLE + " WHERE discordID='" + discordId + "';");
    }

    //-----------------------------
    //----------REVIEWS------------
    //-----------------------------
    public Set<Review> retrieveReviews() {
        Set<Review> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REVIEWS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Review(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("resourceId"), rs.getLong("date")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Set<Review> retrieveMemberReviews(DbMember member) {
        Set<Review> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REVIEWS_TABLE + " WHERE `memberId`="+member.getId()+";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Review(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("resourceId"), rs.getLong("date")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveReview(Review review) {
        mysql.update("INSERT INTO " + REVIEWS_TABLE + " (id, memberId, resourceId, date) VALUES (NULL, '" + review.getMemberId() + "', '" + review.getResourceId() + "', '" + review.getDate() + "');");
    }

    public void deleteReview(Review review) {
        mysql.update("DELETE FROM " + REVIEWS_TABLE + " WHERE id='" + review.getId() + "';");
    }

    public void deleteReviewById(int id) {
        mysql.update("DELETE FROM " + REVIEWS_TABLE + " WHERE id='" + id + "';");
    }

    //-----------------------------
    //----------UPDATES------------
    //-----------------------------

    public Set<Resource> retrieveResourceUpdates(Resource resource) {
        Set<Resource> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + UPDATES_TABLE + " WHERE `resourceId`="+resource.getId()+";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveUpdate(DbUpdate update) {
        mysql.update("INSERT INTO " + UPDATES_TABLE + " (id, resourceId, version, date) VALUES (NULL, '" + update.getResourceId() + "', '" + update.getVersion() + "', '" + update.getDate() + "');");
    }

    public void deleteUpdate(Update update) {
        mysql.update("DELETE FROM " + UPDATES_TABLE + " WHERE id='" + update.getId() + "';");
    }

    public void deleteUpdatedById(int id) {
        mysql.update("DELETE FROM " + UPDATES_TABLE + " WHERE id='" + id + "';");
    }

    //-----------------------------
    //---------RESOURCES-----------
    //-----------------------------



    //-----------------------------
    //--------PUNISHMENTS----------
    //-----------------------------



    //-----------------------------
    //----VERIFICATION_MARKETS-----
    //-----------------------------



    //-----------------------------
    //-------VERIFICATION_Q--------
    //-----------------------------



    //-----------------------------
    //------SUB_VERIFICATION-------
    //-----------------------------



    //-----------------------------
    //--------VERIFICATION---------
    //-----------------------------



    //-----------------------------
    //------PURCHASED_PLUGINS------
    //-----------------------------


}
