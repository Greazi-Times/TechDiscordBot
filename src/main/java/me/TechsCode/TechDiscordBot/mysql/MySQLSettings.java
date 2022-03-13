package me.TechsCode.TechDiscordBot.mysql;

public class MySQLSettings {

    private final String host, username, password, port;
    private String database;

    private MySQLSettings(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public static MySQLSettings of(String host, String port, String database, String username, String password) {
        return new MySQLSettings(host, port, database, username, password);
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}