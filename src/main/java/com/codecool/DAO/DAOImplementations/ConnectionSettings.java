package com.codecool.DAO.DAOImplementations;


public class ConnectionSettings {


    public static String url ="jdbc:postgresql://localhost:5432/QuestStore";
    public static String user ="postgres";
    public static String password ="root";

    public static void setUrl(String url) {
        ConnectionSettings.url = url;
    }

    public static void setUser(String user) {
        ConnectionSettings.user = user;
    }

    public static void setPassword(String password) {
        ConnectionSettings.password = password;
    }
}
