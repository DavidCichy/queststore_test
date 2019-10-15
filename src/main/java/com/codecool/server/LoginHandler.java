package com.codecool.server;

import com.codecool.DAO.DAOImplementations.LoginDAOImplementation;
import com.codecool.DAO.DAOImplementations.SessionDAOImplementation;
import com.codecool.DAO.DAOImplementations.UserDAOImplementation;
import com.codecool.helper.Helper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.util.*;

public class LoginHandler implements HttpHandler {
    private SessionDAOImplementation sessionDAO;
    private LoginDAOImplementation loginDAO;
    private UserDAOImplementation userDAO;
    private Helper helper;

    public LoginHandler(SessionDAOImplementation sessionDAO, LoginDAOImplementation loginDAO,
                        UserDAOImplementation userDAO, Helper helper){
        this.sessionDAO = sessionDAO;
        this.loginDAO = loginDAO;
        this.userDAO = userDAO;
        this. helper = helper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = null;
        String method = httpExchange.getRequestMethod();

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;

        if (method.equals("GET")) {
            if (cookieStr != null) {
                cookie = HttpCookie.parse(cookieStr).get(0);
                if (sessionDAO.isCurrent(cookie.getValue())) {
                    int userID = sessionDAO.getUserIDFromSessionID((cookie.getValue()));
                    helper.loadStartingPage(httpExchange, userDAO.getTypeByID(userID));
                } else {
                    response = helper.getLoginTemplate();
                }
            } else {
                response = helper.getLoginTemplate();
            }
        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> inputs = helper.parseFormData(formData);
            if (loginDAO.checkIfLoginExists(inputs.get("login"))) {
                if (loginDAO.checkIfPasswordMatches(inputs.get("login"), inputs.get("password"))) {
                    String uniqueID = UUID.randomUUID().toString();
                    cookie = new HttpCookie("sessionID", uniqueID);

                    httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                    int userID = loginDAO.getUserIDByLogin(inputs.get("login"));
                    sessionDAO.add(uniqueID, userID);

                    helper.loadStartingPage(httpExchange, userDAO.getTypeByID(userID));
                } else {
                    response = helper.getTemplateAfterLoggingFail();
                }
            } else {
                response = helper.getTemplateAfterLoggingFail();
            }
        }
        helper.sendResponse(httpExchange, response);
    }
}