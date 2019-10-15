package com.codecool.server;

import com.codecool.DAO.DAOImplementations.*;
import com.codecool.helper.Helper;
import com.codecool.model.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentHandler implements HttpHandler {
    private Helper helper;
    private SessionDAOImplementation sessionDAO;
    private StudentDAOImplementation studentDAO;
    private CoinDAOImplementation coinDAO;
    private ExperienceDAOImplementation expDAO;
    private ArtifactDAOImplementation artifactDAO;
    private QuestDAOImplementation questDAO;
    private int userID;

    public StudentHandler(Helper helper, SessionDAOImplementation sessionDAO, StudentDAOImplementation studentDAO,
                          CoinDAOImplementation coinDAO,
                          ExperienceDAOImplementation expDAO,
                          ArtifactDAOImplementation artifactDAO,
                          QuestDAOImplementation questDAO) {
        this.helper = helper;
        this.sessionDAO = sessionDAO;
        this.studentDAO = studentDAO;
        this.coinDAO = coinDAO;
        this.expDAO = expDAO;
        this.artifactDAO = artifactDAO;
        this.questDAO = questDAO;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            if (sessionDAO.isCurrent(cookie.getValue())) {
                userID = sessionDAO.getUserIDFromSessionID(cookie.getValue());
                response = handleRequest(httpExchange, userID, method);
            } else {
                response = getStartingTemplate();
            }
        } else {
            response = getStartingTemplate();
        }
        helper.sendResponse(httpExchange, response);
    }

    private String handleRequest(HttpExchange httpExchange, int userID, String method) throws IOException {
        String response = "";
        URI uri = httpExchange.getRequestURI();
        Map<String, String> parsedURI = helper.parseURI(uri.getPath());
        String operation = "";
        String artifactID = "0";

        if(!parsedURI.isEmpty()) {
            operation = parsedURI.keySet().iterator().next();
            artifactID = parsedURI.get(operation);
        }

        switch(operation){
            case "":
                response = getStartingTemplate();
                break;
            case "store":
                response = getStoreTemplate();
                break;
            case "buy":
                buyArtifact(Integer.parseInt(artifactID), userID, method, httpExchange);
                break;
            case "quests":
                response = getQuestsTemplate();
                break;
            case "inventory":
                response = getInventoryTemplate();
                break;
            case "use":
                useArtifact(Integer.parseInt(artifactID), userID, method, httpExchange);
                break;
            case "transactions":
                response = getTransactionsTemplate();
                break;

        }
        return response;
    }

    private String getStartingTemplate() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Student.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("student", studentDAO.getByID(userID, coinDAO, expDAO, artifactDAO));
        return template.render(model);
    }

    private String getQuestsTemplate() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/StudentQuests.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", getAllQuestsWithCompletedByStudent(userID));
        return template.render(model);
    }

    private String getStoreTemplate() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/StudentArtifactStore.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("artifacts", artifactDAO.getAll());
        model.with("coinsAmount", coinDAO.getAmount(userID, expDAO));
        return template.render(model);
    }

    private String getInventoryTemplate(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/StudentInventory.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("boughtArtifacts", artifactDAO.getBought(userID));
        return template.render(model);
    }

    private String getTransactionsTemplate(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Photos.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private List<Quest> getAllQuestsWithCompletedByStudent(int userID) {
        List<Quest> allQuests = new ArrayList<>(questDAO.getAllQuests());
        List<Quest> completedQuests = new ArrayList<>(questDAO.getStudentsCompletedQuests(userID));

        List<Quest> markedQuests = new ArrayList<>();

        for(Quest quest : allQuests){
            int timesCompleted = 0;
            for(Quest completedQuest : completedQuests){
                if(quest.getId() == completedQuest.getId()){
                    timesCompleted ++;
                }
            }
            if(timesCompleted > 0){
                quest.setIsCompleted(true);
                quest.setTimesCompleted(timesCompleted);
                markedQuests.add(quest);
            }else{
                quest.setIsCompleted(false);
                markedQuests.add(quest);
            }
        }
        return markedQuests;
    }

    private void buyArtifact(int artifactID, int userID, String method, HttpExchange httpExchange) throws IOException {
        if(method.equals("POST")){
            artifactDAO.buy(userID, artifactID);
            helper.switchUserToPage(httpExchange, "/student/store");
        }
    }

    private void useArtifact(int artifactID, int userID, String method, HttpExchange httpExchange)throws IOException {
        if(method.equals("POST")){
            artifactDAO.use(userID, artifactID);
            helper.switchUserToPage(httpExchange, "/student/inventory");
        }
    }
}