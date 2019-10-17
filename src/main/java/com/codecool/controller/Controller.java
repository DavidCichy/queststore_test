package com.codecool.controller;

import com.codecool.DAO.DAOImplementations.*;
import com.codecool.helper.Helper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.codecool.server.ContextCreator;

public class Controller {
    private ContextCreator contextCreator;

    public Controller(ContextCreator contextCreator){
        this.contextCreator = contextCreator;
    }

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8010), 0);

        ArtifactDAOImplementation artifactDAO = new ArtifactDAOImplementation();
        ClassDAOImplementation classDAO = new ClassDAOImplementation();
        CoinDAOImplementation coinDAO = new CoinDAOImplementation();
        ExperienceDAOImplementation expDAO = new ExperienceDAOImplementation();
        LoginDAOImplementation loginDAO = new LoginDAOImplementation();
        MentorDAOImplementation mentorDAO = new MentorDAOImplementation();
        QuestDAOImplementation questDAO = new QuestDAOImplementation();
        SessionDAOImplementation sessionDAO = new SessionDAOImplementation();
        StudentDAOImplementation studentDAO = new StudentDAOImplementation();
        UserDAOImplementation userDAO = new UserDAOImplementation();
        Helper helper = new Helper();

        contextCreator.createContexts(server, artifactDAO, classDAO, coinDAO, expDAO, loginDAO,
                mentorDAO, questDAO, sessionDAO, studentDAO, userDAO, helper);
    }
}
