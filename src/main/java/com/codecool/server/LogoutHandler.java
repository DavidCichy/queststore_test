package com.codecool.server;

import com.codecool.DAO.DAOImplementations.SessionDAOImplementation;
import com.codecool.helper.Helper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;

public class LogoutHandler implements HttpHandler {
    private SessionDAOImplementation sessionDAO;
    private Helper helper;

    public LogoutHandler(SessionDAOImplementation sessionDAO, Helper helper){
        this. sessionDAO = sessionDAO;
        this.helper = helper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            cookie.setMaxAge(0);
            if (sessionDAO.isCurrent(cookie.getValue())) {
                sessionDAO.delete(cookie.getValue());

                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                helper.switchUserToPage(httpExchange, "/login");
            } else {
                helper.switchUserToPage(httpExchange, "/login");
            }
        } else {
            helper.switchUserToPage(httpExchange, "/login");
        }
        helper.switchUserToPage(httpExchange, "/login");
    }
}