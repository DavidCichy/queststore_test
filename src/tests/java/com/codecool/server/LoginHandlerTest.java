package com.codecool.server;

import com.codecool.DAO.DAOImplementations.SessionDAOImplementation;
import com.codecool.DAO.DAOImplementations.UserDAOImplementation;
import com.codecool.helper.Helper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginHandlerTest {

    @Test
    void ifCookieIsNullGetLoginPage() throws IOException {

        //MOCKS
        HttpExchange httpExchange = mock(HttpExchange.class);
        Headers headers = new Headers();
        when(httpExchange.getRequestHeaders()).thenReturn(headers);//kiedy wywolam metode zwracam
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
        //act
        Helper helper = new Helper();
        LoginHandler loginHandler = new LoginHandler(null, null, null, helper);
        loginHandler.handle(httpExchange);

        OutputStream actual = httpExchange.getResponseBody();
        String expected = getOutputStream();
        //asert
        assertEquals(expected, actual.toString());

    }

    @Test
    void ifCookieIsNotNullGetMainPage() throws IOException {
        //MOCKS
        HttpExchange httpExchange = mock(HttpExchange.class);
        UserDAOImplementation userDAOImplementation = mock(UserDAOImplementation.class);
        SessionDAOImplementation sessionDAO = mock(SessionDAOImplementation.class);
        Headers headers = new Headers();
        Headers headers1 = mock(Headers.class);
        //todo dlaczego ?????


        when(httpExchange.getRequestHeaders()).thenReturn(headers1);//kiedy wywolam metode zwracam
        when(httpExchange.getRequestMethod()).thenReturn("GET");
        when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
        when(httpExchange.getResponseHeaders()).thenReturn(headers);
        when(sessionDAO.isCurrent(anyString())).thenReturn(true);
        when(sessionDAO.getUserIDFromSessionID(anyString())).thenReturn(1);
        when(httpExchange.getRequestHeaders().getFirst("Cookie")).thenReturn(String.valueOf(new HttpCookie("sessionId", "sdf")));
        when(userDAOImplementation.getTypeByID(anyInt())).thenReturn("student");
//todo czy to mozliwe ze do mocka nie dodaje nowych rekordow ?


        Helper helper = new Helper();
        LoginHandler loginHandler = new LoginHandler(sessionDAO, null, userDAOImplementation, helper);
        loginHandler.handle(httpExchange);
        String actual = httpExchange.getResponseHeaders().getFirst("Location");
        String expected = "/student";
        assertEquals(expected, actual);
    }

    private String getOutputStream() throws IOException {
        return ("<!DOCTYPE HTML>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Login</title>\n" +
                "    <link rel=\"icon\" href=\"/static/images/loginIcon.png\" type=\"image/icon type\">\n" +
                "\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.6.1/css/all.css\" integrity=\"sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP\" crossorigin=\"anonymous\">\n" +
                "    <link rel=\"stylesheet\" href=\"/static/css/login.css\" />\n" +
                "\n" +
                "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\n" +
                "    <script src=\"/static/js/login.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container-fluid\">\n" +
                "    <div class=\"row h-100\">\n" +
                "        <div class=\"d-flex flex-column\" id=\"content\">\n" +
                "            <div class=\"d-flex justify-content-center align-items-center\" id=\"header\">\n" +
                "                <p>QuestStore</p>\n" +
                "            </div>\n" +
                "            <div class=\"d-flex justify-content-center\" id=\"loginForm\">\n" +
                "                <form method=\"POST\" id=\"form\">\n" +
                "                    <div class=\"form-group d-flex flex-row\">\n" +
                "                        <div class=\"input-group-prepend\">\n" +
                "                            <span class=\"input-group-text\"><i class=\"fas fa-user\"></i></span>\n" +
                "                        </div>\n" +
                "                        <input type=\"text\" name=\"login\" class=\"form-control\" placeholder=\"Enter login\" onKeyup=\"checkForm()\">\n" +
                "                    </div>\n" +
                "                    <div class=\"form-group d-flex flex-row\">\n" +
                "                        <div class=\"input-group-prepend\">\n" +
                "                            <span class=\"input-group-text\"><i class=\"fas fa-key\"></i></span>\n" +
                "                        </div>\n" +
                "                        <input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Password\" onKeyup=\"checkForm()\">\n" +
                "                    </div>\n" +
                "                        <button type=\"submit\" class=\"btn btn-primary\" id=\"loginButton\" disabled=\"disabled\">Submit</button>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "            <div class=\"d-flex justify-content-center align-items-center\" id=\"warning\">\n" +
                "                 <p><b></b></p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }
}