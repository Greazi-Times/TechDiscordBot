package me.TechsCode.TechDiscordBot.mysql.storage;

import com.google.gson.JsonObject;
import me.TechsCode.TechDiscordBot.mysql.MySQL;
import me.TechsCode.TechDiscordBot.mysql.MySQLSettings;
import me.TechsCode.TechDiscordBot.reminders.Reminder;
import me.TechsCode.TechDiscordBot.reminders.ReminderType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Storage {

    private final MySQL mysql;
    private boolean connected;

<<<<<<< Updated upstream
    private final String VERIFICATIONS_TABLE = "VerificationsOld";
=======
    /* OLD DATBASES
    private final String VERIFICATIONS_TABLE_OLD = "VerificationsOld";
>>>>>>> Stashed changes
    private final String REMINDERS_TABLE = "Reminders";
    private final String MUTES_TABLE = "Mutes";
    private final String SUB_VERIFICATIONS_TABLE = "SubVerifications";
    private final String TRANSCRIPTS_TABLE = "Transcripts";
<<<<<<< Updated upstream
    private final String PLUGIN_UPDATES_TABLE = "PluginUpdates";
    private final String WARNINGS_TABLE = "Warnings";
=======
    private final String VERIFICATIONQ_TABLE = "VerificationQueue";

    private final String VERIFICATION_TABLE = "Verification";
    private final String VERIFICATION_PURCHASES = "PurchasedPlugins";
     */

    private final String VERIFICATION_TABLE = "Verification";
    private final String PURCHASEDPLUGINS_TABLE= "PurchasedPlugins";
    private final String SUBVERFIED_TABLE= "SubVerified";
    private final String MEMBERS_TABLE= "Members";
    private final String VERIFICATIONQ_TABLE= "VerificationQ";
    private final String VERIFICATIONMARKETS_TABLE= "VerificationMarkets";
    private final String PUNISHMENTS_TABLE= "Punishments";
    private final String REMINDERS_TABLE= "Reminders";
    private final String TRANSCRIPTS_TABLE= "Transcripts";
    private final String RESOURCES_TABLE= "Resources";
    private final String UPDATES_TABLE= "Updates";
    private final String REVIEWS_TABLE= "Reviews";
    private final String MARKETS_TABLE= "Markets";
>>>>>>> Stashed changes

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
        /* OLD DATABASE
        mysql.update("CREATE TABLE IF NOT EXISTS " + VERIFICATIONS_TABLE_OLD + " (userid varchar(10), discordid varchar(32));");
        mysql.update("CREATE TABLE IF NOT EXISTS " + MUTES_TABLE + " (memberId varchar(32), reason longtext, end varchar(32), expired tinyint(1));");
        mysql.update("CREATE TABLE IF NOT EXISTS " + REMINDERS_TABLE + " (user_id varchar(32), channel_id varchar(32), time varchar(32), type tinyint(1), reminder longtext);");
        mysql.update("CREATE TABLE IF NOT EXISTS " + SUB_VERIFICATIONS_TABLE + " (discordId_verified varchar(32), discordId_subVerified varchar(32));");
        mysql.update("CREATE TABLE IF NOT EXISTS " + TRANSCRIPTS_TABLE + " (id varchar(36), value longtext) DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;");
<<<<<<< Updated upstream
        mysql.update("CREATE TABLE IF NOT EXISTS " + PLUGIN_UPDATES_TABLE + " (resourceId varchar(32), updateId varchar(32), PRIMARY KEY (resourceId) ) DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;");
        mysql.update("CREATE TABLE IF NOT EXISTS " + WARNINGS_TABLE + " (id varchar(32), memberId varchar(32), reporterId varchar(32), reason longtext, time varchar(32), PRIMARY KEY (id) ) DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;");
=======
        mysql.update("CREATE TABLE IF NOT EXISTS " + VERIFICATIONQ_TABLE + " (discordid varchar(32), market varchar(10), email varchar(64), transactionid varchar(64)) DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci;");

        //Verification storage
        mysql.update("CREATE TABLE `" + VERIFICATION_TABLE + "` (`DiscordID` VARCHAR(255) NOT NULL AUTO_INCREMENT, `SpgitoID` INT, `McMarketID` INT, `PolymartID` INT, `SongodaID` INT, `PayerID` VARCHAR(255) NOT NULL, PRIMARY KEY (`DiscordID`));");
        mysql.update("CREATE TABLE `" + VERIFICATION_PURCHASES + "` ( `DiscordID` VARCHAR(255) NOT NULL, `Plugin` VARCHAR(255) NOT NULL, `TransactionID` VARCHAR(255) NOT NULL, `PurchaseData` VARCHAR(255) NOT NULL, `Reviewed` BOOLEAN NOT NULL DEFAULT false, `Market` VARCHAR(255) NOT NULL, PRIMARY KEY (`DiscordID`));");
        mysql.update("ALTER TABLE `" + VERIFICATION_TABLE + "` ADD CONSTRAINT `Verification_fk0` FOREIGN KEY (`DiscordID`) REFERENCES `" + VERIFICATION_PURCHASES + "`(`DiscordID`);");
        */


        mysql.update("CREATE TABLE `"+ MEMBERS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `DiscordID` VARCHAR(255) NOT NULL, `Name` VARCHAR(255) NOT NULL, `Joined` VARCHAR(255) NOT NULL, `Staff` BOOLEAN NOT NULL, PRIMARY KEY (`id`));");

        mysql.update("CREATE TABLE `"+ VERIFICATIONQ_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `MembersID` VARCHAR(255) NOT NULL, `Market` VARCHAR(255) NOT NULL, `Email` VARCHAR(255) NOT NULL, `TransactionId` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ VERIFICATION_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `MembersID` VARCHAR(255) NOT NULL, `PayerID` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ PURCHASEDPLUGINS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `VerificationID` VARCHAR(255) NOT NULL, `PluginID` VARCHAR(255) NOT NULL, `TransactionID` VARCHAR(255) NOT NULL, `PurchaseData` VARCHAR(255) NOT NULL, `Reviewed` BOOLEAN NOT NULL DEFAULT false, `Market` INT NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ VERIFICATIONMARKETS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `VerificationID` INT NOT NULL, `Market` INT NOT NULL, `UserID` INT NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ SUBVERFIED_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `VerificationID` INT NOT NULL, `SubMembersID` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));");

        mysql.update("CREATE TABLE `"+ PUNISHMENTS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `MemberID` INT NOT NULL, `Type` VARCHAR(255) NOT NULL, `Reason` TEXT NOT NULL, `Date` TIMESTAMP NOT NULL, `Expired` TIMESTAMP NOT NULL, `Punisher` INT NOT NULL, PRIMARY KEY (`id`));");

        mysql.update("CREATE TABLE `"+ REMINDERS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `MemberID` INT NOT NULL, `ChannelID` VARCHAR(255) NOT NULL, `Time` TIMESTAMP NOT NULL, `Type` VARCHAR(255) NOT NULL, `Reminder` TEXT NOT NULL, PRIMARY KEY (`id`));");

        mysql.update("CREATE TABLE `"+ TRANSCRIPTS_TABLE +"` (`id` INT NOT NULL, `value` TEXT NOT NULL, PRIMARY KEY (`id`));");

        mysql.update("CREATE TABLE `"+ RESOURCES_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(255) NOT NULL, `SpigotID` INT NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ UPDATES_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `ResourceID` INT NOT NULL, `Version` VARCHAR(255) NOT NULL, `Date` TIMESTAMP NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ REVIEWS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `MemberID` INT NOT NULL, `PluginID` INT NOT NULL, `Date` TIMESTAMP NOT NULL, `ReviewID` INT NOT NULL, PRIMARY KEY (`id`));");
        mysql.update("CREATE TABLE `"+ MARKETS_TABLE +"` (`id` INT NOT NULL AUTO_INCREMENT, `Name` VARCHAR(255) NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`));");

        mysql.update("ALTER TABLE `"+ VERIFICATION_TABLE +"` ADD CONSTRAINT `Verification_fk0` FOREIGN KEY (`MembersID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ PURCHASEDPLUGINS_TABLE +"` ADD CONSTRAINT `PurchasedPlugins_fk0` FOREIGN KEY (`VerificationID`) REFERENCES `Verification`(`id`);");
        mysql.update("ALTER TABLE `"+ PURCHASEDPLUGINS_TABLE +"` ADD CONSTRAINT `PurchasedPlugins_fk1` FOREIGN KEY (`PluginID`) REFERENCES `Resources`(`id`);");
        mysql.update("ALTER TABLE `"+ PURCHASEDPLUGINS_TABLE +"` ADD CONSTRAINT `PurchasedPlugins_fk2` FOREIGN KEY (`Market`) REFERENCES `Markets`(`id`);");
        mysql.update("ALTER TABLE `"+ SUBVERFIED_TABLE +"` ADD CONSTRAINT `SubVerified_fk0` FOREIGN KEY (`VerificationID`) REFERENCES `Verification`(`id`);");
        mysql.update("ALTER TABLE `"+ SUBVERFIED_TABLE +"` ADD CONSTRAINT `SubVerified_fk1` FOREIGN KEY (`SubMembersID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ VERIFICATIONQ_TABLE +"` ADD CONSTRAINT `VerificationQ_fk0` FOREIGN KEY (`MembersID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ VERIFICATIONMARKETS_TABLE +"` ADD CONSTRAINT `VerificationMarkets_fk0` FOREIGN KEY (`VerificationID`) REFERENCES `Verification`(`id`);");
        mysql.update("ALTER TABLE `"+ VERIFICATIONMARKETS_TABLE +"` ADD CONSTRAINT `VerificationMarkets_fk1` FOREIGN KEY (`Market`) REFERENCES `Markets`(`id`);");
        mysql.update("ALTER TABLE `"+ PUNISHMENTS_TABLE +"` ADD CONSTRAINT `Punishments_fk0` FOREIGN KEY (`MemberID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ PUNISHMENTS_TABLE +"` ADD CONSTRAINT `Punishments_fk1` FOREIGN KEY (`Punisher`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ REMINDERS_TABLE +"` ADD CONSTRAINT `Reminders_fk0` FOREIGN KEY (`MemberID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ UPDATES_TABLE +"` ADD CONSTRAINT `Updates_fk0` FOREIGN KEY (`ResourceID`) REFERENCES `Resources`(`id`);");
        mysql.update("ALTER TABLE `"+ REVIEWS_TABLE +"` ADD CONSTRAINT `Reviews_fk0` FOREIGN KEY (`MemberID`) REFERENCES `Members`(`id`);");
        mysql.update("ALTER TABLE `"+ REVIEWS_TABLE +"` ADD CONSTRAINT `Reviews_fk1` FOREIGN KEY (`PluginID`) REFERENCES `Resources`(`id`);");

>>>>>>> Stashed changes

        this.connected = true;
    }

    //Verification Queue
    public void createVerificationQ(String discordId, String market) {
        mysql.update("INSERT INTO " + VERIFICATIONQ_TABLE + " (discordid, market, email, transactionid) VALUES ('" + discordId + "', '" + market + "', '1', '1');");
    }

    public void addEmailVerificationQ(String discordId, String email) {
        mysql.update("UPDATE " + VERIFICATIONQ_TABLE + " SET `email`='" + email + "' WHERE `discordid`='" + discordId + "';");
    }

    public void addTransactionIdVerificationQ(String discordId, String transactionId) {
        mysql.update("UPDATE " + VERIFICATIONQ_TABLE + " SET `transactionid`='" + transactionId + "' WHERE `discordid`='" + discordId + "';");
    }

    public void removeVerificationQ(String discordId) {
        mysql.update("DELETE FROM " + VERIFICATIONQ_TABLE + " WHERE `discordid`='" + discordId + "';");
    }

    public String getSelectedVerificationQ(String id){
        String selectedMarket = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `discordid`='" + id + "';");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                selectedMarket = rs.getString("market");
            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selectedMarket;
    }

    public String getVerificationQEmail(String discordId){
        String information = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `discordid`='" + discordId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                information = rs.getString("email");

            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return information;
    }

    public String getVerificationQTransaction(String discordId){
        String information = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `discordid`='" + discordId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                information = rs.getString("transactionid");

            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return information;
    }

    public boolean isVerificationQ(String discordId) {
        boolean isVerificationQ = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONQ_TABLE + " WHERE `discordid`='" + discordId + "';");

            ResultSet rs = preparedStatement.executeQuery();
            isVerificationQ = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isVerificationQ;
    }

    //Verification
    public void createVerification(String userId, String discordId) {
        mysql.update("INSERT INTO " + VERIFICATION_TABLE + " (userid, discordid) VALUES ('" + userId + "', '" + discordId + "');");
    }

    public void removeVerification(Verification verification) {
        mysql.update("DELETE FROM " + VERIFICATION_TABLE + " WHERE `userid`=" + verification.getUserId());
    }

    //Sub Verification
    public void addSubVerification(String discordId_verified, String discordId_subVerified) {
        mysql.update("INSERT INTO " + SUB_VERIFICATION_TABLE + " (discordId_verified, discordId_subVerified) VALUES ('" + discordId_verified + "', '" + discordId_subVerified + "');");
    }

    public void removeSubVerification(String discordId_verified) {
        mysql.update("DELETE FROM " + SUB_VERIFICATION_TABLE + " WHERE `discordId_verified`=" + discordId_verified);
    }

    public boolean hasSubVerification(String discordId_verified) {
        boolean hasSubVerification = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUB_VERIFICATIONS_TABLE + " WHERE `discordId_verified`=" + discordId_verified);

            ResultSet rs = preparedStatement.executeQuery();
            hasSubVerification = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

       return hasSubVerification;
    }

    public boolean isSubVerifiedUser(String discordId_subVerified) {
        boolean isSubVerifiedUser = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUB_VERIFICATIONS_TABLE + " WHERE `discordId_subVerified`=" + discordId_subVerified);

            ResultSet rs = preparedStatement.executeQuery();
            isSubVerifiedUser = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSubVerifiedUser;
    }

    public String getVerifiedIdFromSubVerifiedId(String discordId_subVerified) {
        String pluginHolder = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUB_VERIFICATIONS_TABLE + " WHERE `discordId_subVerified`=" + discordId_subVerified);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pluginHolder = rs.getString("discordId_verified");
            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pluginHolder;
    }

    public String getSubVerifiedIdFromVerifiedId(String discordId_verified) {
        String pluginHolder = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SUB_VERIFICATIONS_TABLE + " WHERE `discordId_verified`=" + discordId_verified);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pluginHolder = rs.getString("discordId_subVerified");
            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pluginHolder;
    }

    public Verification retrieveVerificationWithDiscord(User user) { return retrieveVerificationWithDiscord(user.getId()); }

    public Verification retrieveVerificationWithDiscord(Member member) { return retrieveVerificationWithDiscord(member.getUser().getId()); }

    public Verification retrieveVerificationWithDiscord(String discordId) { return retrieveVerifications().stream().filter(verification -> verification.getDiscordId().equals(discordId)).findFirst().orElse(null); }

    public Verification retrieveVerificationWithSpigot(String userId) { return retrieveVerifications().stream().filter(verification -> verification.getUserId().equals(userId)).findFirst().orElse(null); }

    public Set<Verification> retrieveVerifications() {
        Set<Verification> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + VERIFICATIONS_TABLE + ";");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                ret.add(new Verification(this, rs.getString("userid"), rs.getString("discordid")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    //Mutes
    public Set<Mute> getMutes(Member member) {
        Set<Mute> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + MUTES_TABLE + (member == null ? ";" : " WHERE memberId='" + member.getId() + "';"));
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Mute(rs.getString("memberId"), rs.getString("reason"), Long.parseLong(rs.getString("time")), rs.getBoolean("expired")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Mute muteExpired(Mute mute, boolean expired) {
        if(mute.getId() == -1) return mute;

        mysql.update("UPDATE " + MUTES_TABLE + " SET expired=? WHERE ", expired);

        return new Mute(mute.getId(), mute.getMemberId(), mute.getReason(), mute.getEnd(), expired);
    }

    public Mute uploadMute(Mute mute) {
        int id = getMutes(null).size() + 1;

        mysql.update("INSERT INTO " + MUTES_TABLE + " (memberId, reason, end, expired) VALUES (?, ?, ?, ?);", mute.getMemberId(), mute.getReason(), mute.getEnd(), mute.isExpired());

        return new Mute(id, mute.getMemberId(), mute.getReason(), mute.getEnd(), mute.isExpired());
    }

    //Preorders
    public Set<Preorder> getPreorders(String plugin, boolean all) {
        Set<Preorder> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + plugin.replace(" ", "") + "Preorders;");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String transactionId = rs.getString("transactionId");

                if(!transactionId.equals("NONE") || all) {
                    ret.add(new Preorder(plugin, rs.getString("email"), rs.getLong("discordId"), rs.getString("discordName"), transactionId));
                }
            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    //Reminders
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

    public void saveTranscript(JsonObject transcript) {
        mysql.update("INSERT INTO " + TRANSCRIPTS_TABLE + " (id, value) VALUES (?, ?);", transcript.get("id").getAsString(), transcript.toString());
    }

    public void deleteReminder(Reminder reminder) {
        mysql.update("DELETE FROM " + REMINDERS_TABLE + " WHERE user_id='" + reminder.getUserId() + "' AND channel_id='" + (reminder.getChannelId() == null ? "NULL" : "'" + reminder.getChannelId() + "'") + "' AND time='" + reminder.getTime() + "' AND type=" + reminder.getType().getI() + " AND reminder='" + reminder.getReminder().replace("'", "''") + "';");
    }

    public String getLastPluginUpdate(String resourceId){
        String update = "unknown";

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PLUGIN_UPDATES_TABLE + " WHERE `resourceId`='"+resourceId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                update = rs.getString("updateId");

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update;
    }

    public void updateLastPluginUpdate(String resourceId, String newUpdateId){
        mysql.update("UPDATE " + PLUGIN_UPDATES_TABLE + " SET `updateId`=? WHERE `resourceId`=?;", newUpdateId, resourceId);
    }

    public void insertLastPluginUpdate(String resourceId, String newUpdateId){
        mysql.update("INSERT INTO " + PLUGIN_UPDATES_TABLE + " (resourceId, updateId) VALUES (?, ?)", resourceId, newUpdateId);
    }

    public boolean lastPluginExists(String resourceId){
        boolean exists = false;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + PLUGIN_UPDATES_TABLE + " WHERE `resourceId`='"+resourceId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            exists = rs.next();

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public void addWarning(Warning warning) {
        mysql.update("INSERT INTO " + WARNINGS_TABLE + " (id, memberId, reporterId, reason, time) VALUES (?, ?, ?, ?, ?);", warning.getId(), warning.getMemberId(), warning.getReporterId(), warning.getReason(), warning.getTime());
    }

    public void deleteWarning(int warningId) {
        mysql.update("DELETE FROM " + WARNINGS_TABLE + " WHERE `id`=?;", warningId);
    }

    public Set<Warning> retrieveWarningsByUserID(String userId) {
        Set<Warning> ret = new HashSet<>();

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + WARNINGS_TABLE + " WHERE `memberId`='"+userId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                ret.add(new Warning(rs.getInt("id"), rs.getString("memberId"), rs.getString("reporterId"), rs.getString("reason"), rs.getLong("time")));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Warning retrieveWarningById(String warningId){
        Warning warning = null;

        try {
            Connection connection = mysql.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + WARNINGS_TABLE + " WHERE `id`='"+warningId+"';");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
                warning = new Warning(rs.getInt("id"), rs.getString("memberId"), rs.getString("reporterId"), rs.getString("reason"), rs.getLong("time"));

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warning;
    }

}
