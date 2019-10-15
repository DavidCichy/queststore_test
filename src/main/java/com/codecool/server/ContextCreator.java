package com.codecool.server;

import com.codecool.DAO.DAOImplementations.*;

import com.codecool.helper.Helper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class ContextCreator {
    public void createContexts(HttpServer server, ArtifactDAOImplementation artifactDAO, ClassDAOImplementation classDAO,
                               CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                               LoginDAOImplementation loginDAO, MentorDAOImplementation mentorDAO,
                               QuestDAOImplementation questDAO, SessionDAOImplementation sessionDAO,
                               StudentDAOImplementation studentDAO, UserDAOImplementation userDAO, Helper helper) throws IOException {

        server.createContext("/login", new LoginHandler(sessionDAO, loginDAO, userDAO, helper));
        server.createContext("/student", new StudentHandler(helper, sessionDAO, studentDAO, coinDAO, expDAO,
                artifactDAO, questDAO));
        server.createContext("/student/store", new StudentHandler(helper, sessionDAO, studentDAO, coinDAO, expDAO,
                artifactDAO, questDAO));
        server.createContext("/student/quests", new StudentHandler(helper, sessionDAO, studentDAO, coinDAO, expDAO,
                artifactDAO, questDAO));

        server.createContext("/mentor", new MentorHandler(helper, sessionDAO, coinDAO, expDAO, artifactDAO, questDAO,
                studentDAO, mentorDAO));
        server.createContext("/mentor/quests", new MentorHandler(helper, sessionDAO, coinDAO, expDAO, artifactDAO,
                questDAO, studentDAO, mentorDAO));
        server.createContext("/mentor/students", new MentorHandler(helper, sessionDAO, coinDAO, expDAO, artifactDAO,
                questDAO, studentDAO, mentorDAO));
        server.createContext("/mentor/artifacts", new MentorHandler(helper, sessionDAO, coinDAO, expDAO, artifactDAO,
                questDAO, studentDAO, mentorDAO));

        server.createContext("/static", new StaticHandler());
        server.createContext("/logout", new LogoutHandler(sessionDAO, helper));

        server.setExecutor(null);
        server.start();
    }
}