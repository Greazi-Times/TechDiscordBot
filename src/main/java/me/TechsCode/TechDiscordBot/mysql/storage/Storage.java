package me.TechsCode.TechDiscordBot.mysql.storage;

import me.TechsCode.TechDiscordBot.TechDiscordBot;
import me.TechsCode.TechDiscordBot.mysql.Models.*;
import me.TechsCode.TechDiscordBot.mysql.Models.Lists.*;
import me.TechsCode.TechDiscordBot.mysql.MySQL;
import me.TechsCode.TechDiscordBot.mysql.MySQLSettings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
        mysql.update("CREATE TABLE IF NOT EXISTS `"+MARKETS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT , `name` varchar(100) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `UQ_name` (`name`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+MEMBERS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `discordID` varchar(100) NOT NULL, `name` varchar(100) NOT NULL, `joined` bigint(20) NOT NULL, `staff` boolean NOT NULL , PRIMARY KEY (`id`), UNIQUE KEY `UQ_discordId` (`discordId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONS_TABLE+"`(`memberId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `payerID` varchar(100) NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_89` (`memberId`), CONSTRAINT `FK_87` FOREIGN KEY `fkIdx_89` (`memberId`) REFERENCES `Members` (`id`), UNIQUE KEY `UQ_memberId` (`memberId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+PUNISHMENTS_TABLE+"`(`type` varchar(100) NOT NULL, `memberId` int NOT NULL, `reason` text NOT NULL, `date` timestamp NOT NULL, `expired` timestamp NOT NULL, `punisherId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`),KEY `fkIdx_119` (`memberId`), CONSTRAINT `FK_117` FOREIGN KEY `fkIdx_119` (`memberId`) REFERENCES `Members` (`id`),KEY `fkIdx_123` (`punisherId`), CONSTRAINT `FK_121` FOREIGN KEY `fkIdx_123` (`punisherId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+PURCHASEDPLUGINS_TABLE+"`(`marketId` int NOT NULL, `transactionID` varchar(255) NOT NULL, `purchaseData` varchar(255) NOT NULL, `reviewed` boolean NOT NULL DEFAULT false, `verificationID` int NOT NULL, `resourceId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_107` (`marketId`), CONSTRAINT `FK_105` FOREIGN KEY `fkIdx_107` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_110` (`verificationID`), CONSTRAINT `FK_108` FOREIGN KEY `fkIdx_110` (`verificationID`) REFERENCES `Verification` (`id`),KEY `fkIdx_116` (`resourceId`),CONSTRAINT `FK_114` FOREIGN KEY `fkIdx_116` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+REMINDERS_TABLE+"`(`channelID` varchar(100) NOT NULL, `time` timestamp NOT NULL, `type` varchar(100) NOT NULL, `reminder` text NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `memberId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_113` (`memberId`), CONSTRAINT `FK_111` FOREIGN KEY `fkIdx_113` (`memberId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+RESOURCES_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `name` varchar(100) NOT NULL, `spigotId` int NOT NULL, `discordRoleId` BIGINT NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `UQ_spigotId` (`spigotId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+REVIEWS_TABLE+"`(`memberId` int NOT NULL, `date` timestamp NOT NULL, `reviewId` int NOT NULL, `resourceId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_132` (`memberId`), CONSTRAINT `FK_130` FOREIGN KEY `fkIdx_132` (`memberId`) REFERENCES `Members` (`id`), KEY `fkIdx_135` (`resourceId`), CONSTRAINT `FK_133` FOREIGN KEY `fkIdx_135` (`resourceId`) REFERENCES `Resources` (`id`), UNIQUE KEY `UQ_reviewId` (`reviewId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+SUBVERIFIED_TABLE+"`(`subMembersId` int NOT NULL, `verificationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_126` (`subMembersId`), CONSTRAINT `FK_124` FOREIGN KEY `fkIdx_126` (`subMembersId`) REFERENCES `Members` (`id`),KEY `fkIdx_129` (`verificationId`), CONSTRAINT `FK_127` FOREIGN KEY `fkIdx_129` (`verificationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+TRANSCRIPTS_TABLE+"`(`id` int NOT NULL, `value` text NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+UPDATES_TABLE+"`(`resourceId` int NOT NULL, `name` varchar(100) NOT NULL, `date` timestamp NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_138` (`resourceId`), CONSTRAINT `FK_136` FOREIGN KEY `fkIdx_138` (`resourceId`) REFERENCES `Resources` (`id`), UNIQUE KEY `UQ_resourceId` (`resourceId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONMARKETS_TABLE+"`(`marketId` int NOT NULL, `verficationId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, `userID` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_100` (`marketId`), CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_100` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_103` (`verficationId`), CONSTRAINT `FK_101` FOREIGN KEY `fkIdx_103` (`verficationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONQ_TABLE+"`(`memberId` int NOT NULL, `email` varchar(255) NOT NULL, `transactionId` varchar(255) NOT NULL, `marketId` int NOT NULL, `id` int NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`), KEY `fkIdx_141` (`marketId`), CONSTRAINT `FK_139` FOREIGN KEY `fkIdx_141` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_96` (`memberId`), CONSTRAINT `FK_94` FOREIGN KEY `fkIdx_96` (`memberId`) REFERENCES `Members` (`id`), UNIQUE KEY `UQ_memberId` (`memberId`));");

        this.connected = true;
    }

    //-----------------------------
    //---------REMINDERS-----------
    //-----------------------------
    public ReminderList retrieveReminders() {
        ReminderList reminderList = new ReminderList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REMINDERS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                reminderList.add(new Reminder(rs.getInt("id"), rs.getInt("memberId"), rs.getString("channelId"), rs.getInt("type"), rs.getString("reminder"), rs.getLong("time")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reminderList;
    }

    public ReminderList retrieveMemberReminders(DbMember member) {
        ReminderList reminderList = new ReminderList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REMINDERS_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                reminderList.add(new Reminder(rs.getInt("id"), rs.getInt("memberId"), rs.getString("channelId"), rs.getInt("type"), rs.getString("reminder"), rs.getLong("time")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reminderList;
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
    public MarketList retrieveMarkets() {
        MarketList marketList = new MarketList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                marketList.add(new DbMarket(rs.getInt("id"), rs.getString("name")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marketList;
    }

    public DbMarket retrieveMarketByName(String name) {
        DbMarket dbMarket = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `name`='"+name+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                dbMarket = new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbMarket;
    }

    public DbMarket retrieveMarketById(int id) {
        DbMarket dbMarket = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `id`='"+id+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                dbMarket = new DbMarket(rs.getInt("id"), rs.getString("name"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbMarket;
    }

    public boolean marketExists(String name) {
        boolean marketExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MARKETS_TABLE + " WHERE `name`='" + name + "';");

            ResultSet rs = preparedStatement.executeQuery();
            marketExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marketExists;
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
    public MemberList retrieveMembers() {
        MemberList memberList = new MemberList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                memberList.add(new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberList;
    }

    public DbMember retrieveMemberByDiscordId(String discordId) {
        DbMember dbMember = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + MEMBERS_TABLE + "` WHERE `discordId`='"+discordId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.first())
                dbMember = new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbMember;
    }

    public DbMember retrieveMemberById(int id) {
        DbMember dbMember = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `id`='"+id+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                dbMember = new DbMember(rs.getInt("id"), rs.getString("discordId"), rs.getString("name"), rs.getLong("joined"), rs.getBoolean("staff"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbMember;
    }

    public boolean memberExists(String discordId) {
        boolean memberExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MEMBERS_TABLE + " WHERE `discordId`='" + discordId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            memberExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberExists;
    }

    public void saveMember(@NotNull DbMember dbMember) {
        mysql.update("INSERT INTO " + MEMBERS_TABLE + " (id, discordID, name, joined, staff) VALUES (NULL, '" + dbMember.getDiscordId() + "', '" + dbMember.getName() + "', " + dbMember.getJoined() + ", " + dbMember.isStaff() + ") ON DUPLICATE KEY UPDATE discordID='" + dbMember.getDiscordId() + "', name='" + dbMember.getName() + "', joined=" + dbMember.getJoined() + ", staff=" + dbMember.isStaff() + ";");
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
    public ReviewList retrieveReviews() {
        ReviewList reviewList = new ReviewList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REVIEWS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                reviewList.add(new Review(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("reviewId"), rs.getInt("resourceId"), rs.getLong("date")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

    public ReviewList retrieveMemberReviews(@NotNull DbMember member) {
        ReviewList reviewList = new ReviewList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + REVIEWS_TABLE + " WHERE `memberId`="+member.getId()+";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                reviewList.add(new Review(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("reviewId"), rs.getInt("resourceId"), rs.getLong("date")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviewList;
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

    public UpdateList retrieveResourceUpdates(@NotNull Resource resource) {
        UpdateList updateList = new UpdateList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + UPDATES_TABLE + " WHERE `resourceId`="+resource.getId()+";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                updateList.add(new DbUpdate(rs.getInt("id"), rs.getInt("resourceId"), rs.getString("version"), rs.getLong("date"), rs.getLong("updateId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateList;
    }

    public boolean updateExists(String updateId) {
        boolean updateExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + UPDATES_TABLE + " WHERE `updateId`='" + updateId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            updateExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateExists;
    }

    public void saveUpdate(@NotNull DbUpdate update) {
        mysql.update("INSERT INTO " + UPDATES_TABLE + " (id, resourceId, name, date) VALUES (NULL, '" + update.getResourceId() + "', '" + update.getName() + "', '" + update.getDate() + "') ON DUPLICATE KEY UPDATE resourceId='" + update.getResourceId() + "', name='" + update.getName() + "', date='" + update.getDate() + "';");
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

    public ResourcesList retrieveResources() {
        ResourcesList resourcesList = new ResourcesList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                resourcesList.add(new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resourcesList;
    }

    public Resource retrieveResourceById(int resourceId) {
        Resource resource = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE id='"+resourceId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                resource = new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resource;
    }

    public boolean resourceExists(Resource resource) {
        boolean resourceExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE `id`='" + resource.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            resourceExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resourceExists;
    }

    public boolean resourceExists(int spigotId) {
        boolean resourceExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE `spigotId`='" + spigotId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            resourceExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resourceExists;
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
    public PunishmentList retrievePunishments() {
        PunishmentList punishmentList = new PunishmentList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                punishmentList.add(new Punishment(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("punisherId"), rs.getString("type"), rs.getString("reason"), rs.getLong("date"), rs.getLong("expired")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return punishmentList;
    }

    public PunishmentList retrieveMemberPunishments(@NotNull DbMember member) {
        PunishmentList punishmentList = new PunishmentList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE memberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                punishmentList.add(new Punishment(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("punisherId"), rs.getString("type"), rs.getString("reason"), rs.getLong("date"), rs.getLong("expired")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return punishmentList;
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
    public VerficationMarketList retrieveVerficationMarkets(@NotNull DbVerfication verfication) {
        VerficationMarketList verficationMarketList = new VerficationMarketList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verficationMarketList.add(new VerficationMarket(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verficationId"), rs.getInt("userId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationMarketList;
    }

    public VerficationMarketList retrieveVerficationMarket(@NotNull int userId) {
        VerficationMarketList verficationMarketList = new VerficationMarketList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE userId='"+userId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verficationMarketList.add(new VerficationMarket(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verficationId"), rs.getInt("userId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationMarketList;
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
    public VerficationQList retrieveVerficationQ() {
        VerficationQList verficationQList = new VerficationQList();
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verficationQList.add(new VerficationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationQList;
    }

    public VerficationQ retrieveMemberVerficationQ(@NotNull DbMember member) {
        VerficationQ verficationQ = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE memberId='"+member.getId()+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                verficationQ = new VerficationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationQ;
    }

    public VerficationQ retrieveVerficationQById(@NotNull int verificationQId) {
        VerficationQ verficationQ = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE id='"+verificationQId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                verficationQ = new VerficationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationQ;
    }

    public boolean verficationQExists(DbMember member) {
        boolean verficationQExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `memberId`='" + member.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            verficationQExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationQExists;
    }

    public boolean verficationQExists(int transactionId) {
        boolean verficationQExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `transactionId`='" + transactionId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            verficationQExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationQExists;
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
    public SubVerificationList retrieveSubVerfications(@NotNull DbVerfication verfication) {
        SubVerificationList subVerificationList = new SubVerificationList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                subVerificationList.add(new SubVerfication(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("verficationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subVerificationList;
    }

    public SubVerificationList retrieveMemberSubVerfications(@NotNull DbMember member) {
        SubVerificationList subVerificationList = new SubVerificationList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE subMemberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                subVerificationList.add(new SubVerfication(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("verficationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subVerificationList;
    }

    public void saveSubVerfication(@NotNull SubVerfication subVerfication) {
        mysql.update("INSERT INTO " + SUBVERIFIED_TABLE + " (id, subMemberId, verficationId) VALUES (NULL, '" + subVerfication.getSubMemberId() + "', '" + subVerfication.getVerificationId() + "') ON DUPLICATE KEY UPDATE subMemberId='" + subVerfication.getSubMemberId() + "', verficationId='" + subVerfication.getVerificationId() + "';");
    }

    public void deleteSubVerfication(@NotNull SubVerfication subVerfication) {
        mysql.update("DELETE FROM " + SUBVERIFIED_TABLE + " WHERE id='" + subVerfication.getId() + "';");
    }


    //-----------------------------
    //--------VERIFICATION---------
    //-----------------------------
    public VerficationList retrieveVerfications() {
        VerficationList verfications = new VerficationList();
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verfications.add(new DbVerfication(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verfications;
    }

    public DbVerfication retrieveMemberVerfication(@NotNull DbMember member) {
        DbVerfication verfication = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE memberId='"+member.getId()+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                verfication = new DbVerfication(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verfication;
    }

    public DbVerfication retrieveVerficationById(@NotNull int verificationId) {
        DbVerfication verfication = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE id='"+verificationId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                verfication = new DbVerfication(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verfication;
    }

    public boolean verficationExists(DbMember member) {
        boolean verficationExistsMemberId = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE `memberId`='" + member.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            verficationExistsMemberId = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationExistsMemberId;
    }

    public void saveVerfication(@NotNull DbVerfication verfication) {
        mysql.update("INSERT INTO " + VERIFICATIONS_TABLE + " (id, memberId, payerId) VALUES (NULL, '" + verfication.getMemberId() + "', '" + verfication.getPayerId() + "') ON DUPLICATE KEY UPDATE memberId='" + verfication.getMemberId() + "', payerId='" + verfication.getPayerId() + "';");
    }

    public void deleteVerfication(@NotNull DbVerfication verfication) {
        mysql.update("DELETE FROM " + VERIFICATIONS_TABLE + " WHERE id='" + verfication.getId() + "';");
    }


    //-----------------------------
    //------PURCHASED_PLUGINS------
    //-----------------------------
    public VerficationPluginList retrieveVerficationPlugins(@NotNull DbVerfication verfication) {
        VerficationPluginList verficationPluginList = new VerficationPluginList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PURCHASEDPLUGINS_TABLE + " WHERE verficationId='"+verfication.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verficationPluginList.add(new VerficationPlugin(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verificationId"), rs.getInt("resourceId"), rs.getString("transactionId"), rs.getString("purchaseData"), rs.getBoolean("reviewed")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verficationPluginList;
    }

    public void saveVerficationPlugin(@NotNull VerficationPlugin verficationPlugin) {
        mysql.update("INSERT INTO " + PURCHASEDPLUGINS_TABLE + " (id, marketId, verificationId, resourceId, transactionId, purchaseData, reviewed) VALUES (NULL, '" + verficationPlugin.getMarketId() + "', '" + verficationPlugin.getVerificationId() + "', '" + verficationPlugin.getResourceId() + "', '" + verficationPlugin.getTransactionId() + "', '" + verficationPlugin.getPurchaseData() + "', '" + verficationPlugin.isReviewed() + "') ON DUPLICATE KEY UPDATE marketId='" + verficationPlugin.getMarketId() + "', verificationId='" + verficationPlugin.getVerificationId() + "', resourceId='" + verficationPlugin.getResourceId() + "', transactionId='" + verficationPlugin.getTransactionId() + "', purchaseData='" + verficationPlugin.getPurchaseData() + "', reviewed='" + verficationPlugin.isReviewed() + "';");
    }

    public void deleteVerficationPlugin(@NotNull VerficationPlugin verficationPlugin) {
        mysql.update("DELETE FROM " + PURCHASEDPLUGINS_TABLE + " WHERE id='" + verficationPlugin.getId() + "';");
    }

}
