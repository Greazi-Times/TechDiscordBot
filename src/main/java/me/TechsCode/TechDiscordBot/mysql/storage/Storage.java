package me.TechsCode.TechDiscordBot.mysql.storage;

import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.mysql.MySQL;
import me.TechsCode.TechDiscordBot.mysql.MySQLSettings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    @Contract("_ -> new")
    public static @NotNull Storage of(MySQLSettings mySQLSettings) {
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
        mysql.update("CREATE TABLE `"+RESOURCES_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `name` varchar(100) NOT NULL, `spigotId` int NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+REVIEWS_TABLE+"`(`memberId` int NOT NULL, `date` timestamp NOT NULL, `reviewID` int NOT NULL, `resourceId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_132` (`memberId`), CONSTRAINT `FK_130` FOREIGN KEY `fkIdx_132` (`memberId`) REFERENCES `Members` (`id`), KEY `fkIdx_135` (`resourceId`), CONSTRAINT `FK_133` FOREIGN KEY `fkIdx_135` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE `"+SUBVERIFIED_TABLE+"`(`subMembersId` int NOT NULL, `verificationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMEN, PRIMARY KEY (`id`), KEY `fkIdx_126` (`subMembersId`), CONSTRAINT `FK_124` FOREIGN KEY `fkIdx_126` (`subMembersId`) REFERENCES `Members` (`id`),KEY `fkIdx_129` (`verificationId`), CONSTRAINT `FK_127` FOREIGN KEY `fkIdx_129` (`verificationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE `"+TRANSCRIPTS_TABLE+"`(`id` int NOT NULL, `value` text NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+UPDATES_TABLE+"`(`resourceId` int NOT NULL, `version` varchar(100) NOT NULL, `date` timestamp NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_138` (`resourceId`), CONSTRAINT `FK_136` FOREIGN KEY `fkIdx_138` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONS_TABLE+"`(`memberId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `payerID` varchar(100) NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_89` (`memberId`), CONSTRAINT `FK_87` FOREIGN KEY `fkIdx_89` (`memberId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONMARKETS_TABLE+"`(`marketId` int NOT NULL, `verficationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `userID` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_100` (`marketId`), CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_100` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_103` (`verficationId`), CONSTRAINT `FK_101` FOREIGN KEY `fkIdx_103` (`verficationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE `"+VERIFICATIONQ_TABLE+"`(`memberId` int NOT NULL, `email` varchar(255) NOT NULL, `transactionId` varchar(255) NOT NULL, `marketId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_141` (`marketId`), CONSTRAINT `FK_139` FOREIGN KEY `fkIdx_141` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_96` (`memberId`), CONSTRAINT `FK_94` FOREIGN KEY `fkIdx_96` (`memberId`) REFERENCES `Members` (`id`));");

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
                ret.add(new Reminder(rs.getInt("id"), rs.getInt("memberId"), rs.getString("channelId"), new ReminderType(0), rs.getString("reminder"), rs.getLong("time")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Set<Reminder> retrieveMemberReminders(DbMember member) {
        Set<Reminder> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REMINDERS_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Reminder(rs.getInt("id"), rs.getInt("memberId"), rs.getString("channelId"), new ReminderType(0), rs.getString("reminder"), rs.getLong("time")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveReminder(@NotNull Reminder reminder) {
        Map<Integer, String> data =new HashMap<Integer, String>();
        data.put(1, String.valueOf(reminder.getMemberId()));
        data.put(2, reminder.getChannelId());
        data.put(3, String.valueOf(reminder.getType().getIndex()));
        data.put(4, reminder.getReminder());
        data.put(5, String.valueOf(reminder.getTime()));
        data.put(6, String.valueOf(reminder.getMemberId()));
        data.put(7, reminder.getChannelId());
        data.put(8, String.valueOf(reminder.getType().getIndex()));
        data.put(9, reminder.getReminder());
        data.put(10, String.valueOf(reminder.getTime()));

        mysql.update("INSERT INTO " + REMINDERS_TABLE + " (id, memberId, channelId, type, reminder, time) VALUES (NULL, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE memberId=?, channelId=?, type=?, reminder=?, time=?;", data);
    }

    public void deleteReminder(@NotNull Reminder reminder) {
        mysql.update("DELETE FROM " + REMINDERS_TABLE + " WHERE memberId='" + reminder.getMemberId() + "' AND channelId=" + (reminder.getChannelId() == null ? "NULL" : "'" + reminder.getChannelId() + "'") + " AND time='" + reminder.getTime() + "' AND type=" + reminder.getType() + " AND reminder='" + reminder.getReminder().replace("'", "''") + "';");
    }

    //-----------------------------
    //--------TRANSCRIPTS----------
    //-----------------------------
    public void saveTranscript(@NotNull Transcript transcript) {
        mysql.update("INSERT INTO " + TRANSCRIPTS_TABLE + " (id, value) VALUES (?, ?) ON DUPLICATE KEY UPDATE value=?;", transcript.getId(), transcript.getValue(), transcript.getValue());
    }

    public void deleteTranscript(@NotNull Transcript transcript) {
        mysql.update("DELETE FROM " + TRANSCRIPTS_TABLE + " WHERE id='" + transcript.getId() + "';");
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
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `name`='"+name+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public DbMarket retrieveMarketById(int id) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `id`='"+id+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveMarket(@NotNull DbMarket market) {
        mysql.update("INSERT INTO " + MARKETS_TABLE + " (id, name) VALUES (NULL, '"+market.getName()+"') ON DUPLICATE KEY UPDATE name='"+market.getName()+"';");
    }

    public void deleteMarket(@NotNull DbMarket market) {
        mysql.update("DELETE FROM " + MARKETS_TABLE + " WHERE id='" + market.getId() + "';");
    }

    public void deleteMarketByName(String marketName) {
        mysql.update("DELETE FROM " + MARKETS_TABLE + " WHERE name='" + marketName + "';");
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
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `discordId`='"+discordId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public DbMember retrieveMemberById(int id) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `id`='"+id+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveMember(@NotNull DbMember dbMember) {
        mysql.update("INSERT INTO " + MEMBERS_TABLE + " (id, discordID, name, joined, staff) VALUES (NULL, '" + dbMember.getDiscordId() + "', '" + dbMember.getName() + "', '" + dbMember.getJoined() + "', '" + dbMember.isStaff() + "') ON DUPLICATE KEY UPDATE discordID='" + dbMember.getDiscordId() + "', name='" + dbMember.getName() + "', joined='" + dbMember.getJoined() + "', staff='" + dbMember.isStaff() + "';");
    }

    public void deleteMember(@NotNull DbMember dbMember) {
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

    public Set<Review> retrieveMemberReviews(@NotNull DbMember member) {
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

    public void saveReview(@NotNull Review review) {
        mysql.update("INSERT INTO " + REVIEWS_TABLE + " (id, memberId, resourceId, date) VALUES (NULL, '" + review.getMemberId() + "', '" + review.getResourceId() + "', '" + review.getDate() + "') ON DUPLICATE KEY UPDATE memberId='" + review.getMemberId() + "', resourceId='" + review.getResourceId() + "', date='" + review.getDate() + "';");
    }

    public void deleteReview(@NotNull Review review) {
        mysql.update("DELETE FROM " + REVIEWS_TABLE + " WHERE id='" + review.getId() + "';");
    }

    public void deleteReviewById(int id) {
        mysql.update("DELETE FROM " + REVIEWS_TABLE + " WHERE id='" + id + "';");
    }

    //-----------------------------
    //----------UPDATES------------
    //-----------------------------

    public Set<DbUpdate> retrieveResourceUpdates(@NotNull Resource resource) {
        Set<DbUpdate> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + UPDATES_TABLE + " WHERE `resourceId`="+resource.getId()+";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new DbUpdate(rs.getInt("id"), rs.getInt("resourceId"), rs.getString("version"), rs.getLong("date")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveUpdate(@NotNull DbUpdate update) {
        mysql.update("INSERT INTO " + UPDATES_TABLE + " (id, resourceId, version, date) VALUES (NULL, '" + update.getResourceId() + "', '" + update.getVersion() + "', '" + update.getDate() + "') ON DUPLICATE KEY UPDATE resourceId='" + update.getResourceId() + "', version='" + update.getVersion() + "', date='" + update.getDate() + "';");
    }

    public void deleteUpdate(@NotNull DbUpdate update) {
        mysql.update("DELETE FROM " + UPDATES_TABLE + " WHERE id='" + update.getId() + "';");
    }

    public void deleteUpdatedById(int id) {
        mysql.update("DELETE FROM " + UPDATES_TABLE + " WHERE id='" + id + "';");
    }

    //-----------------------------
    //---------RESOURCES-----------
    //-----------------------------

    public Set<Resource> retrieveResources() {
        Set<Resource> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + ";");
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

    public Resource retrieveResourceById(int resourceId) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE id='"+resourceId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveResource(@NotNull Resource resource) {
        mysql.update("INSERT INTO " + RESOURCES_TABLE + " (id, name, spigotId) VALUES (NULL, '" + resource.getName() + "', '" + resource.getSpigotId() + "') ON DUPLICATE KEY UPDATE name='" + resource.getName() + "', spigotId='" + resource.getSpigotId() + "';");
    }

    public void deleteResource(@NotNull Resource resource) {
        mysql.update("DELETE FROM " + RESOURCES_TABLE + " WHERE id='" + resource.getId() + "';");
    }


    //-----------------------------
    //--------PUNISHMENTS----------
    //-----------------------------
    public Set<Punishment> retrievePunishments() {
        Set<Punishment> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Punishment(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("punisherId"), rs.getString("type"), rs.getString("reason"), rs.getLong("date"), rs.getLong("expired")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Set<Punishment> retrieveMemberPunishments(@NotNull DbMember member) {
        Set<Punishment> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Punishment(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("punisherId"), rs.getString("type"), rs.getString("reason"), rs.getLong("date"), rs.getLong("expired")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void savePunishment(@NotNull Punishment punishment) {
        mysql.update("INSERT INTO " + RESOURCES_TABLE + " (id, memberId, punisherId, type, reason, date, expired) VALUES (NULL, '" + punishment.getMemberId() + "', '" + punishment.getPunisherId() + "', '" + punishment.getType() + "', '" + punishment.getReason() + "', '" + punishment.getDate() + "', '" + punishment.getExpired() + "') ON DUPLICATE KEY UPDATE memberId='" + punishment.getMemberId() + "', punisherId='" + punishment.getPunisherId() + "', type='" + punishment.getType() + "', reason='" + punishment.getReason() + "', date='" + punishment.getDate() + "', expired='" + punishment.getExpired() + "';");
    }

    public void deletePunishment(@NotNull Punishment punishment) {
        mysql.update("DELETE FROM " + RESOURCES_TABLE + " WHERE id='" + punishment.getId() + "';");
    }


    //-----------------------------
    //----VERIFICATION_MARKETS-----
    //-----------------------------
    public Set<VerficationMarket> retrieveVerficationMarkets(@NotNull Verfication verfication) {
        Set<VerficationMarket> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new VerficationMarket(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verficationId"), rs.getInt("userId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveVerficationMarket(@NotNull VerficationMarket verficationMarket) {
        mysql.update("INSERT INTO " + VERIFICATIONMARKETS_TABLE + " (id, marketId, verficationId, userId) VALUES (NULL, '" + verficationMarket.getMarketId() + "', '" + verficationMarket.getVerificationId() + "', '" + verficationMarket.getUserId() + "') ON DUPLICATE KEY UPDATE marketId='" + verficationMarket.getMarketId() + "', verficationId='" + verficationMarket.getVerificationId() + "', userId='" + verficationMarket.getUserId() + "';");
    }

    public void deleteVerficationMarket(@NotNull VerficationMarket verficationMarket) {
        mysql.update("DELETE FROM " + VERIFICATIONMARKETS_TABLE + " WHERE id='" + verficationMarket.getId() + "';");
    }


    //-----------------------------
    //-------VERIFICATION_Q--------
    //-----------------------------
    public VerficationQ retrieveMemberVerficationQ(@NotNull DbMember member) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new VerficationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveVerficationQ(@NotNull VerficationQ verficationQ) {
        mysql.update("INSERT INTO " + VERIFICATIONQ_TABLE + " (id, memberId, marketId, email, transactionId) VALUES (NULL, '" + verficationQ.getMemberId() + "', '" + verficationQ.getMarketId() + "', '" + verficationQ.getEmail() + "', '" + verficationQ.getTransactionId() + "') ON DUPLICATE KEY UPDATE memberId='" + verficationQ.getMemberId() + "', marketId='" + verficationQ.getMarketId() + "', email='" + verficationQ.getEmail() + "', transactionId='" + verficationQ.getTransactionId() + "';");
    }

    public void deleteVerficationQ(@NotNull VerficationQ verficationQ) {
        mysql.update("DELETE FROM " + VERIFICATIONQ_TABLE + " WHERE id='" + verficationQ.getId() + "';");
    }


    //-----------------------------
    //------SUB_VERIFICATION-------
    //-----------------------------
    public Set<SubVerfication> retrieveSubVerfications(@NotNull Verfication verfication) {
        Set<SubVerfication> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new SubVerfication(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("verficationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Set<SubVerfication> retrieveMemberSubVerfications(@NotNull DbMember member) {
        Set<SubVerfication> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE subMemberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new SubVerfication(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("verficationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveSubVerfication(@NotNull SubVerfication subVerfication) {
        mysql.update("INSERT INTO " + SUBVERIFIED_TABLE + " (id, subMemberId, verficationId) VALUES (NULL, '" + subVerfication.getSubMembersId() + "', '" + subVerfication.getVerificationId() + "') ON DUPLICATE KEY UPDATE subMemberId='" + subVerfication.getSubMembersId() + "', verficationId='" + subVerfication.getVerificationId() + "';");
    }

    public void deleteSubVerfication(@NotNull SubVerfication subVerfication) {
        mysql.update("DELETE FROM " + SUBVERIFIED_TABLE + " WHERE id='" + subVerfication.getId() + "';");
    }


    //-----------------------------
    //--------VERIFICATION---------
    //-----------------------------
    public Verfication retrieveMemberVerfication(@NotNull DbMember member) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new Verfication(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Verfication retrieveVerficationById(@NotNull int verificationId) {
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE id='"+verificationId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                return new Verfication(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveVerfication(@NotNull Verfication verfication) {
        mysql.update("INSERT INTO " + VERIFICATIONS_TABLE + " (id, memberId, payerId) VALUES (NULL, '" + verfication.getMemberId() + "', '" + verfication.getPayerId() + "') ON DUPLICATE KEY UPDATE memberId='" + verfication.getMemberId() + "', payerId='" + verfication.getPayerId() + "';");
    }

    public void deleteVerfication(@NotNull Verfication verfication) {
        mysql.update("DELETE FROM " + VERIFICATIONS_TABLE + " WHERE id='" + verfication.getId() + "';");
    }


    //-----------------------------
    //------PURCHASED_PLUGINS------
    //-----------------------------
    public Set<VerficationPlugin> retrieveVerficationPlugins(@NotNull Verfication verfication) {
        Set<VerficationPlugin> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PURCHASEDPLUGINS_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new VerficationPlugin(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verificationId"), rs.getInt("resourceId"), rs.getString("transactionId"), rs.getString("purchaseData"), rs.getBoolean("reviewed")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void saveVerficationPlugin(@NotNull VerficationPlugin verficationPlugin) {
        mysql.update("INSERT INTO " + PURCHASEDPLUGINS_TABLE + " (id, marketId, verificationId, resourceId, transactionId, purchaseData, reviewed) VALUES (NULL, '" + verficationPlugin.getMarketId() + "', '" + verficationPlugin.getVerificationId() + "', '" + verficationPlugin.getResourceId() + "', '" + verficationPlugin.getTransactionId() + "', '" + verficationPlugin.getPurchaseData() + "', '" + verficationPlugin.isReviewed() + "') ON DUPLICATE KEY UPDATE marketId='" + verficationPlugin.getMarketId() + "', verificationId='" + verficationPlugin.getVerificationId() + "', resourceId='" + verficationPlugin.getResourceId() + "', transactionId='" + verficationPlugin.getTransactionId() + "', purchaseData='" + verficationPlugin.getPurchaseData() + "', reviewed='" + verficationPlugin.isReviewed() + "';");
    }

    public void deleteVerficationPlugin(@NotNull VerficationPlugin verficationPlugin) {
        mysql.update("DELETE FROM " + PURCHASEDPLUGINS_TABLE + " WHERE id='" + verficationPlugin.getId() + "';");
    }

}
