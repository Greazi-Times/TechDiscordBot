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
import java.util.Random;

public class Storage {

    private final MySQL mysql;
    private boolean connected;

    public final String VERIFICATIONS_TABLE = "Verification";
    public final String PURCHASEDPLUGINS_TABLE = "PurchasedPlugins";
    public final String SUBVERIFIED_TABLE = "SubVerified";
    public final String MEMBERS_TABLE = "Members";
    public final String VERIFICATIONQ_TABLE = "VerificationQ";
    public final String VERIFICATIONMARKETS_TABLE = "VerificationMarkets";
    public final String PUNISHMENTS_TABLE = "Punishments";
    public final String REMINDERS_TABLE = "Reminders";
    public final String TRANSCRIPTS_TABLE = "Transcripts";
    public final String RESOURCES_TABLE = "Resources";
    public final String UPDATES_TABLE = "Updates";
    public final String REVIEWS_TABLE = "Reviews";
    public final String MARKETS_TABLE = "Markets";

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
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `memberId` int NOT NULL, `payerID` varchar(100) NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_89` (`memberId`), CONSTRAINT `FK_87` FOREIGN KEY `fkIdx_89` (`memberId`) REFERENCES `Members` (`id`), UNIQUE KEY `UQ_memberId` (`memberId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+PUNISHMENTS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `type` varchar(100) NOT NULL, `memberId` int NOT NULL, `reason` text NOT NULL, `date` timestamp NOT NULL, `expired` timestamp NOT NULL, `punisherId` int NOT NULL, PRIMARY KEY (`id`),KEY `fkIdx_119` (`memberId`), CONSTRAINT `FK_117` FOREIGN KEY `fkIdx_119` (`memberId`) REFERENCES `Members` (`id`),KEY `fkIdx_123` (`punisherId`), CONSTRAINT `FK_121` FOREIGN KEY `fkIdx_123` (`punisherId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+REMINDERS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `channelID` varchar(100) NOT NULL, `time` timestamp NOT NULL, `type` varchar(100) NOT NULL, `reminder` text NOT NULL, `memberId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_113` (`memberId`), CONSTRAINT `FK_111` FOREIGN KEY `fkIdx_113` (`memberId`) REFERENCES `Members` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+RESOURCES_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `name` varchar(100) NOT NULL, `spigotId` int NOT NULL, `discordRoleId` bigint(20) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `UQ_spigotId` (`spigotId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+REVIEWS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `memberId` int NOT NULL, `date` timestamp NOT NULL, `reviewId` int NOT NULL, `resourceId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_132` (`memberId`), CONSTRAINT `FK_130` FOREIGN KEY `fkIdx_132` (`memberId`) REFERENCES `Members` (`id`), KEY `fkIdx_135` (`resourceId`), CONSTRAINT `FK_133` FOREIGN KEY `fkIdx_135` (`resourceId`) REFERENCES `Resources` (`id`), UNIQUE KEY `UQ_reviewId` (`reviewId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+SUBVERIFIED_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `subMembersId` int NOT NULL, `verificationId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_126` (`subMembersId`), CONSTRAINT `FK_124` FOREIGN KEY `fkIdx_126` (`subMembersId`) REFERENCES `Members` (`id`),KEY `fkIdx_129` (`verificationId`), CONSTRAINT `FK_127` FOREIGN KEY `fkIdx_129` (`verificationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+TRANSCRIPTS_TABLE+"`(`id` int NOT NULL, `value` text NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+PURCHASEDPLUGINS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `marketId` int NOT NULL, `transactionId` varchar(255) NOT NULL, `purchaseData` varchar(255) NOT NULL, `reviewed` boolean NOT NULL DEFAULT false, `verificationId` int NOT NULL, `resourceId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_107` (`marketId`), CONSTRAINT `FK_105` FOREIGN KEY `fkIdx_107` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_110` (`verificationId`), CONSTRAINT `FK_108` FOREIGN KEY `fkIdx_110` (`verificationId`) REFERENCES `Verification` (`id`),KEY `fkIdx_116` (`resourceId`),CONSTRAINT `FK_114` FOREIGN KEY `fkIdx_116` (`resourceId`) REFERENCES `Resources` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+UPDATES_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `resourceId` int NOT NULL, `name` varchar(100) NOT NULL, `date` timestamp NOT NULL, `updateId` bigint(20) NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_138` (`resourceId`), CONSTRAINT `FK_136` FOREIGN KEY `fkIdx_138` (`resourceId`) REFERENCES `Resources` (`id`), UNIQUE KEY `UQ_resourceId` (`resourceId`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONMARKETS_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `marketId` int NOT NULL, `verificationId` int NOT NULL, `userID` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_100` (`marketId`), CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_100` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_103` (`verificationId`), CONSTRAINT `FK_101` FOREIGN KEY `fkIdx_103` (`verificationId`) REFERENCES `Verification` (`id`));");
        mysql.update("CREATE TABLE IF NOT EXISTS `"+VERIFICATIONQ_TABLE+"`(`id` int NOT NULL AUTO_INCREMENT, `memberId` int NOT NULL, `email` varchar(255) NOT NULL, `transactionId` varchar(255) NOT NULL, `marketId` int NOT NULL, PRIMARY KEY (`id`), KEY `fkIdx_141` (`marketId`), CONSTRAINT `FK_139` FOREIGN KEY `fkIdx_141` (`marketId`) REFERENCES `Markets` (`id`), KEY `fkIdx_96` (`memberId`), CONSTRAINT `FK_94` FOREIGN KEY `fkIdx_96` (`memberId`) REFERENCES `Members` (`id`), UNIQUE KEY `UQ_memberId` (`memberId`));");

        this.connected = true;
    }

    public int getAvailableId(String TABLE){
        Random rng = new Random();
        int min = 1;
        int max = 11;
        int upperBound = max - min + 1; // upper bound is exclusive, so +1
        int num = min + rng.nextInt(upperBound);
        boolean idExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE + " WHERE `id`='" + num + "';");

            ResultSet rs = preparedStatement.executeQuery();
            idExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (idExists){
            return getAvailableId(TABLE);
        }else{
            return num;
        }
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
        data.put(1, String.valueOf(reminder.getId()));
        data.put(2, String.valueOf(reminder.getMemberId()));
        data.put(3, reminder.getChannelId());
        data.put(4, String.valueOf(reminder.getType().getIndex()));
        data.put(5, reminder.getReminder());
        data.put(6, String.valueOf(reminder.getTime()));
        data.put(7, String.valueOf(reminder.getMemberId()));
        data.put(8, reminder.getChannelId());
        data.put(9, String.valueOf(reminder.getType().getIndex()));
        data.put(10, reminder.getReminder());
        data.put(11, String.valueOf(reminder.getTime()));

        mysql.update("INSERT INTO " + REMINDERS_TABLE + " (id, memberId, channelId, type, reminder, time) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE memberId=?, channelId=?, type=?, reminder=?, time=?;", data);
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
        mysql.update("INSERT INTO " + MARKETS_TABLE + " (id, name) VALUES ('"+market.getId()+"', '"+market.getName()+"') ON DUPLICATE KEY UPDATE name='"+market.getName()+"';");
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
        mysql.update("INSERT INTO " + MEMBERS_TABLE + " (id, discordID, name, joined, staff) VALUES ('"+dbMember.getId()+"', '" + dbMember.getDiscordId() + "', '" + dbMember.getName() + "', " + dbMember.getJoined() + ", " + dbMember.isStaff() + ") ON DUPLICATE KEY UPDATE discordID='" + dbMember.getDiscordId() + "', name='" + dbMember.getName() + "', joined=" + dbMember.getJoined() + ", staff=" + dbMember.isStaff() + ";");
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
        mysql.update("INSERT INTO " + REVIEWS_TABLE + " (id, memberId, resourceId, date) VALUES ('"+review.getId()+"', '" + review.getMemberId() + "', '" + review.getResourceId() + "', '" + review.getDate() + "') ON DUPLICATE KEY UPDATE memberId='" + review.getMemberId() + "', resourceId='" + review.getResourceId() + "', date='" + review.getDate() + "';");
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
                updateList.add(new DbUpdate(rs.getInt("id"), rs.getInt("resourceId"), rs.getString("name"), rs.getLong("date"), rs.getLong("updateId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateList;
    }

    public DbUpdate retrieveResourceUpdateByName(@NotNull int resourceId, String Name) {
        DbUpdate dbUpdate = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + UPDATES_TABLE + " WHERE `resourceId`='"+resourceId+"' AND `name`='"+Name+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                dbUpdate = new DbUpdate(rs.getInt("id"), rs.getInt("resourceId"), rs.getString("name"), rs.getLong("date"), rs.getLong("updateId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbUpdate;
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
        mysql.update("INSERT INTO " + UPDATES_TABLE + " (id, resourceId, name, date) VALUES ('"+update.getId()+"', '" + update.getResourceId() + "', '" + update.getName() + "', '" + update.getDate() + "') ON DUPLICATE KEY UPDATE resourceId='" + update.getResourceId() + "', name='" + update.getName() + "', date='" + update.getDate() + "';");
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
                resourcesList.add(new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId"), rs.getLong("discordRoleId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resourcesList;
    }

    public Resource retrieveResourceByName(String resourceName) {
        Resource resource = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE name='"+resourceName+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                resource = new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId"), rs.getLong("discordRoleId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resource;
    }

    public Resource retrieveResourceById(int resourceId) {
        Resource resource = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + RESOURCES_TABLE + " WHERE id='"+resourceId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                resource = new Resource(rs.getInt("id"), rs.getString("name"), rs.getInt("spigotId"), rs.getLong("discordRoleId"));

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
        mysql.update("INSERT INTO " + RESOURCES_TABLE + " (id, name, spigotId, discordRoleId) VALUES ('"+ resource.getId()+"', '" + resource.getName() + "', '" + resource.getSpigotId() + "', '"+ resource.getDiscordRoleId()+"') ON DUPLICATE KEY UPDATE name='" + resource.getName() + "', spigotId='" + resource.getSpigotId() + "', discordRoleId='" + resource.getDiscordRoleId() + "';");
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
        mysql.update("INSERT INTO " + RESOURCES_TABLE + " (id, memberId, punisherId, type, reason, date, expired) VALUES ('"+ punishment.getId()+"', '" + punishment.getMemberId() + "', '" + punishment.getPunisherId() + "', '" + punishment.getType() + "', '" + punishment.getReason() + "', '" + punishment.getDate() + "', '" + punishment.getExpired() + "') ON DUPLICATE KEY UPDATE memberId='" + punishment.getMemberId() + "', punisherId='" + punishment.getPunisherId() + "', type='" + punishment.getType() + "', reason='" + punishment.getReason() + "', date='" + punishment.getDate() + "', expired='" + punishment.getExpired() + "';");
    }

    public void deletePunishment(@NotNull Punishment punishment) {
        mysql.update("DELETE FROM " + RESOURCES_TABLE + " WHERE id='" + punishment.getId() + "';");
    }

    //-----------------------------
    //----VERIFICATION_MARKETS-----
    //-----------------------------
    public VerificationMarketList retrieveVerificationMarkets(@NotNull DbVerification Verification) {
        VerificationMarketList VerificationMarketList = new VerificationMarketList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE VerificationId='"+Verification.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                VerificationMarketList.add(new VerificationMarket(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("VerificationId"), rs.getInt("userId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationMarketList;
    }

    public VerificationMarketList retrieveVerificationMarket(@NotNull int userId) {
        VerificationMarketList VerificationMarketList = new VerificationMarketList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE userId='"+userId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                VerificationMarketList.add(new VerificationMarket(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("VerificationId"), rs.getInt("userId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationMarketList;
    }

    public void saveVerificationMarket(@NotNull VerificationMarket verificationMarket) {
        mysql.update("INSERT INTO " + VERIFICATIONMARKETS_TABLE + " (id, marketId, verificationId, userId) VALUES ('"+ verificationMarket.getId()+"', '" + verificationMarket.getMarketId() + "', '" + verificationMarket.getVerificationId() + "', '" + verificationMarket.getUserId() + "') ON DUPLICATE KEY UPDATE marketId='" + verificationMarket.getMarketId() + "', VerificationId='" + verificationMarket.getVerificationId() + "', userId='" + verificationMarket.getUserId() + "';");
    }

    public void deleteVerificationMarket(@NotNull VerificationMarket VerificationMarket) {
        mysql.update("DELETE FROM " + VERIFICATIONMARKETS_TABLE + " WHERE id='" + VerificationMarket.getId() + "';");
    }

    public boolean verificationMarketExists(DbVerification verification, DbMarket market) {
        boolean verificationMarketExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONMARKETS_TABLE + " WHERE `verificationId`='"+verification.getId()+"' AND `marketId`='"+ market.getId()+"';");

            ResultSet rs = preparedStatement.executeQuery();
            verificationMarketExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationMarketExists;
    }


    //-----------------------------
    //-------VERIFICATION_Q--------
    //-----------------------------
    public VerificationQList retrieveVerificationQ() {
        VerificationQList VerificationQList = new VerificationQList();
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                VerificationQList.add(new VerificationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationQList;
    }

    public VerificationQ retrieveMemberVerificationQ(@NotNull DbMember member) {
        VerificationQ VerificationQ = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE memberId='"+member.getId()+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                VerificationQ = new VerificationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationQ;
    }

    public VerificationQ retrieveVerificationQById(@NotNull int verificationQId) {
        VerificationQ VerificationQ = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE id='"+verificationQId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                VerificationQ = new VerificationQ(rs.getInt("id"), rs.getInt("memberId"), rs.getInt("marketId"), rs.getString("email"), rs.getString("transactionId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationQ;
    }

    public boolean VerificationQExists(DbMember member) {
        boolean VerificationQExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `memberId`='" + member.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            VerificationQExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationQExists;
    }

    public boolean VerificationQExists(int transactionId) {
        boolean VerificationQExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `transactionId`='" + transactionId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            VerificationQExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationQExists;
    }

    public void saveVerificationQ(@NotNull VerificationQ verificationQ) {
        mysql.update("INSERT INTO " + VERIFICATIONQ_TABLE + " (id, memberId, marketId, email, transactionId) VALUES ('"+ verificationQ.getId()+"', '" + verificationQ.getMemberId() + "', '" + verificationQ.getMarketId() + "', '" + verificationQ.getEmail() + "', '" + verificationQ.getTransactionId() + "') ON DUPLICATE KEY UPDATE memberId='" + verificationQ.getMemberId() + "', marketId='" + verificationQ.getMarketId() + "', email='" + verificationQ.getEmail() + "', transactionId='" + verificationQ.getTransactionId() + "';");
    }

    public void deleteVerificationQ(@NotNull VerificationQ VerificationQ) {
        mysql.update("DELETE FROM " + VERIFICATIONQ_TABLE + " WHERE id='" + VerificationQ.getId() + "';");
    }


    //-----------------------------
    //------SUB_VERIFICATION-------
    //-----------------------------
    public SubVerificationList retrieveSubVerifications(@NotNull DbVerification Verification) {
        SubVerificationList subVerificationList = new SubVerificationList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE VerificationId='"+Verification.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                subVerificationList.add(new SubVerification(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("VerificationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subVerificationList;
    }

    public SubVerificationList retrieveMemberSubVerifications(@NotNull DbMember member) {
        SubVerificationList subVerificationList = new SubVerificationList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUBVERIFIED_TABLE + " WHERE subMemberId='"+member.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                subVerificationList.add(new SubVerification(rs.getInt("id"), rs.getInt("subMemberId"), rs.getInt("VerificationId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subVerificationList;
    }

    public void saveSubVerification(@NotNull SubVerification subVerification) {
        mysql.update("INSERT INTO " + SUBVERIFIED_TABLE + " (id, subMemberId, VerificationId) VALUES ('"+ subVerification.getId()+"', '" + subVerification.getSubMemberId() + "', '" + subVerification.getVerification().getId() + "') ON DUPLICATE KEY UPDATE subMemberId='" + subVerification.getSubMemberId() + "', VerificationId='" + subVerification.getVerification().getId() + "';");
    }

    public void deleteSubVerification(@NotNull SubVerification subVerification) {
        mysql.update("DELETE FROM " + SUBVERIFIED_TABLE + " WHERE id='" + subVerification.getId() + "';");
    }


    //-----------------------------
    //--------VERIFICATION---------
    //-----------------------------
    public VerificationList retrieveVerifications() {
        VerificationList Verifications = new VerificationList();
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + ";");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                Verifications.add(new DbVerification(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Verifications;
    }

    public DbVerification retrieveMemberVerification(@NotNull DbMember member) {
        DbVerification Verification = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE memberId='"+member.getId()+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                Verification = new DbVerification(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Verification;
    }

    public DbVerification retrieveVerificationById(@NotNull int verificationId) {
        DbVerification Verification = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE id='"+verificationId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                Verification = new DbVerification(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Verification;
    }

    public DbVerification retrieveVerificationByMemberId(@NotNull int memberId) {
        DbVerification Verification = null;
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE memberId='"+memberId+"';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first())
                Verification = new DbVerification(rs.getInt("id"), rs.getInt("memberId"), rs.getString("payerId"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Verification;
    }

    public boolean VerificationExists(DbMember member) {
        boolean VerificationExistsMemberId = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE `memberId`='" + member.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            VerificationExistsMemberId = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationExistsMemberId;
    }

    public void saveVerification(@NotNull DbVerification verification) {
        mysql.update("INSERT INTO " + VERIFICATIONS_TABLE + " (id, memberId, payerId) VALUES ('"+ verification.getId()+"', '" + verification.getMemberId() + "', '" + verification.getPayerId() + "') ON DUPLICATE KEY UPDATE memberId='" + verification.getMemberId() + "', payerId='" + verification.getPayerId() + "';");
    }

    public void deleteVerification(@NotNull DbVerification verification) {
        mysql.update("DELETE FROM " + VERIFICATIONS_TABLE + " WHERE id='" + verification.getId() + "';");
    }

    public boolean verificationExists(DbMember member) {
        boolean verificationExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + " WHERE `memberId`='" + member.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            verificationExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationExists;
    }

    //-----------------------------
    //------PURCHASED_PLUGINS------
    //-----------------------------
    public VerificationPluginList retrieveVerificationPlugins(@NotNull DbVerification Verification) {
        VerificationPluginList VerificationPluginList = new VerificationPluginList();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PURCHASEDPLUGINS_TABLE + " WHERE VerificationId='"+Verification.getId()+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                VerificationPluginList.add(new VerificationPlugin(rs.getInt("id"), rs.getInt("marketId"), rs.getInt("verificationId"), rs.getInt("resourceId"), rs.getString("transactionId"), rs.getString("purchaseData"), rs.getBoolean("reviewed")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VerificationPluginList;
    }

    public void saveVerificationPlugin(@NotNull VerificationPlugin verificationPlugin) {
        mysql.update("INSERT INTO " + PURCHASEDPLUGINS_TABLE + " (id, marketId, verificationId, resourceId, transactionId, purchaseData, reviewed) VALUES ('"+ verificationPlugin.getId()+"', '" + verificationPlugin.getMarketId() + "', '" + verificationPlugin.getVerificationId() + "', '" + verificationPlugin.getResourceId() + "', '" + verificationPlugin.getTransactionId() + "', '" + verificationPlugin.getPurchaseData() + "', '" + verificationPlugin.isReviewedNumber() + "') ON DUPLICATE KEY UPDATE marketId='" + verificationPlugin.getMarketId() + "', verificationId='" + verificationPlugin.getVerificationId() + "', resourceId='" + verificationPlugin.getResourceId() + "', transactionId='" + verificationPlugin.getTransactionId() + "', purchaseData='" + verificationPlugin.getPurchaseData() + "', reviewed='" + verificationPlugin.isReviewedNumber() + "';");
    }

    public void deleteVerificationPlugin(@NotNull VerificationPlugin verificationPlugin) {
        mysql.update("DELETE FROM " + PURCHASEDPLUGINS_TABLE + " WHERE id='" + verificationPlugin.getId() + "';");
    }

    public boolean verificationPluginExists(DbVerification verification, DbMarket market, Resource resource) {
        boolean verificationPluginExists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PURCHASEDPLUGINS_TABLE + " WHERE `verificationId`='"+verification.getId()+"' AND `marketId`='" + market.getId() + "' AND `resourceId`='" + resource.getId() + "';");

            ResultSet rs = preparedStatement.executeQuery();
            verificationPluginExists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verificationPluginExists;
    }

    //-----------------------------
    //-----OLD_VERIFICATIONS-------
    //-----------------------------
    public HashMap<Integer, String> retrieveOldVerfications() {
        HashMap<Integer, String> verfications = new HashMap<>();
        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `VerificationsOld`;");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                verfications.put(rs.getInt("userid"), rs.getString("discordid"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return verfications;
    }

}
