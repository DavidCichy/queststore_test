package com.codecool.helper;

import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Helper{
    public void switchUserToPage(HttpExchange httpExchange, String newLocation) throws IOException {
        httpExchange.getResponseHeaders().set("Location", newLocation);
        httpExchange.sendResponseHeaders(302, -1);
    }

    public void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void loadStartingPage(HttpExchange httpExchange, String userType) throws IOException {
        switch(userType){
            case "student": switchUserToPage(httpExchange, "/student");
                break;
            case "mentor": switchUserToPage(httpExchange, "/mentor");
                break;
            case "admin": switchUserToPage(httpExchange, "/admin");
        }
    }

    public String getLoginTemplate(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Login.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    public String getTemplateAfterLoggingFail(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Login.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("warning", "Invalid password or login!");
        return template.render(model);
    }

    public Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] values = pair.split("=");
            map.put(values[0], values[1]);
        }
        return map;
    }

    public Map<String, String> parseURI (String URI) {
        Map<String, String> parsedURI = new HashMap<>();
        String[] splittedURI = URI.split("/");
        String operation;
        String id;
        if(splittedURI.length > 3) {
            operation = splittedURI[3];
            id = splittedURI[4];
            parsedURI.put(operation, id);
        } else if(splittedURI.length > 2) {
            operation = splittedURI[2];
            id = "0";
            parsedURI.put(operation, id);
        }
        return parsedURI;
    }
}