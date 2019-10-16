package com.codecool.server;

import com.codecool.DAO.DAOImplementations.LoginDAOImplementation;
import com.codecool.DAO.DAOImplementations.SessionDAOImplementation;
import com.codecool.DAO.DAOImplementations.UserDAOImplementation;
import com.codecool.helper.Helper;
import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import com.sun.net.httpserver.HttpExchange;
import org.mockito.stubbing.OngoingStubbing;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

class LoginHandlerTest {

    @Test
    void handle() throws IOException {
        //MOCKS
        HttpExchange httpExchange = mock(HttpExchange.class);
        SessionDAOImplementation sessionDAO = mock(SessionDAOImplementation.class);
        LoginDAOImplementation loginDAO = mock(LoginDAOImplementation.class);
        UserDAOImplementation userDAO = mock(UserDAOImplementation.class);
        Helper helper = mock(Helper.class);

        Map <String, String> inputs = new HashMap<String, String>();
        inputs.put("codecooler", "password");



        Map<String, LinkedList<String>> headersExpected = new HashMap<>();
        LinkedList<String> headersExpectedValue = new LinkedList<>();
        headersExpected.put("Location", headersExpectedValue);

        Map<String, LinkedList<String>> headers = new HashMap<>();
        LinkedList<String> headersLocation = new LinkedList<>();
        LinkedList<String> headersCookies = new LinkedList<>();
        headersLocation.add("/");
        headersCookies.add("/");
        headers.put("Location", headersLocation);
        headers.put("Cookie", headersCookies);



//        when(httpExchange.getRequestHeaders()).thenReturn(headers); <--- TO KURKA JAK ZROBIĆ?!?!

        when(httpExchange.getRequestMethod()).thenReturn("POST");
        String formData = "";
        when(helper.parseFormData(formData)).thenReturn(inputs);



        when(loginDAO.checkIfLoginExists(inputs.get("login"))).thenReturn(true);
        when(loginDAO.checkIfPasswordMatches(inputs.get("login"), inputs.get("password"))).thenReturn(true);


        LoginHandler loginHandler = new LoginHandler(sessionDAO, loginDAO, userDAO, helper);

        loginHandler.handle(httpExchange);
    }
}