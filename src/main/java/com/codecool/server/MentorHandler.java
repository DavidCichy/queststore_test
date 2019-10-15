package com.codecool.server;

import com.codecool.DAO.DAOImplementations.*;
import com.codecool.helper.Helper;
import com.codecool.model.Artifact;
import com.codecool.model.Mentor;
import com.codecool.model.Quest;
import com.codecool.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class MentorHandler implements HttpHandler {
    private MentorDAOImplementation mentorDAO;
    private CoinDAOImplementation coinDAO;
    private ExperienceDAOImplementation expDAO;
    private ArtifactDAOImplementation artifactDAO;
    private SessionDAOImplementation sessionDAO;
    private QuestDAOImplementation questDAO;
    private StudentDAOImplementation studentDAO;
    private int userID;
    private Helper helper;


    public MentorHandler(Helper helper, SessionDAOImplementation sessionDAO,
                                     CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                                     ArtifactDAOImplementation artifactDAO, QuestDAOImplementation questDAO,
                                     StudentDAOImplementation studentDAO,
                                    MentorDAOImplementation mentorDAO) {
        this.sessionDAO = sessionDAO;
        this.coinDAO = coinDAO;
        this.expDAO = expDAO;
        this.artifactDAO = artifactDAO;
        this.helper = helper;
        this.questDAO = questDAO;
        this.studentDAO = studentDAO;
        this.mentorDAO = mentorDAO;
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

    private String handleRequest(HttpExchange httpExchange, int userId, String method) throws IOException {
        String response = "";
        URI uri = httpExchange.getRequestURI();
        Map<String, String> parsedURI = helper.parseURI(uri.getPath());
        String operation = "";
        String id = "0";

        if (!parsedURI.isEmpty()) {
            operation = parsedURI.keySet().iterator().next();
            id = parsedURI.get(operation);
        }

        switch(operation){
            case "":
                response = getStartingTemplate();
                break;
            case "quests":
                response = getQuestManagementTemplate();
                break;
            case "students":
                response = getStudentManagementTemplate();
                break;
            case "artifacts":
                response = getArtifactManagementTemplate();
                break;
            case "deleteArtifact":
                deleteArtifact(id, httpExchange);
                break;
            case "deleteQuest":
                deleteQuest(id, httpExchange);
                break;
            case "mark":
                markQuestDone(id, httpExchange);
                break;
            case "class":
                setupClass(id, httpExchange);
                break;
            case "addArtifact":
                addArtifact(httpExchange, method);
                break;
            case "addQuest":
                addQuest(httpExchange, method);
                break;

        }
        return response;
    }

    private String getStartingTemplate() {
        Mentor mentor = mentorDAO.getByID(userID);
        List<String> classes = mentor.getClasses();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/Mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("classes", classes);
        return template.render(model);
    }

    private String getArtifactManagementTemplate() {
        JtwigTemplate artifactsManagement = JtwigTemplate.classpathTemplate("templates/MentorArtifactsManagement.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Artifact> artifactsList = artifactDAO.getAll();
        model.with("artifacts", artifactsList);
        return artifactsManagement.render(model);
    }

    private String getQuestManagementTemplate() {
        JtwigTemplate questsManagement = JtwigTemplate.classpathTemplate("templates/MentorQuestsManagement.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Quest> questsList = questDAO.getAllQuests();
        model.with("quests", questsList);
        return questsManagement.render(model);
    }

    private String getStudentManagementTemplate() {
        JtwigTemplate studentsManagement = JtwigTemplate.classpathTemplate("templates/MentorStudentsManagement.twig");
        JtwigModel model = JtwigModel.newModel();
        StudentDAOImplementation students = new StudentDAOImplementation();
        List<Student> studentsList = students.getAll(coinDAO, expDAO, artifactDAO);
        List<Quest> questsList = questDAO.getAllQuests();
        model.with("quests", questsList);
        model.with("studentsList", studentsList);
        return studentsManagement.render(model);
    }

    private void addArtifact(HttpExchange httpExchange, String method) throws IOException {
        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> inputs = helper.parseFormData(formData);
            String n = inputs.get("title");
            String d = inputs.get("description").replace("+", " ");
            Integer p = Integer.parseInt(inputs.get("price"));
            String c = (inputs.get("category"));
            artifactDAO.add(n, d, p, handleAddingArtifactCategory(c));
            helper.switchUserToPage(httpExchange, "/mentor/artifacts");
        }
    }

    private void deleteArtifact(String id, HttpExchange httpExchange) throws IOException {
        artifactDAO.delete(Integer.parseInt(id));
        helper.switchUserToPage(httpExchange, "/mentor/artifacts");
    }

    private void addQuest(HttpExchange httpExchange, String method) throws IOException {
        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> inputs = helper.parseFormData(formData);
            String n = inputs.get("title");
            String d = inputs.get("description").replace("+", " ");
            Integer r = Integer.parseInt(inputs.get("reward"));
            String c = (inputs.get("category"));
            questDAO.add(n, d, r,handleAddingQuestCategory(c));
            helper.switchUserToPage(httpExchange, "/mentor/quests");
        }
    }

    private void deleteQuest(String id, HttpExchange httpExchange) throws IOException {
        questDAO.delete(Integer.parseInt(id));
        helper.switchUserToPage(httpExchange, "/mentor/quests");
    }

    private void markQuestDone(String content, HttpExchange httpExchange) throws IOException {
        int userID = Integer.parseInt(content.split("&")[0]);
        int questID = Integer.parseInt(content.split("&")[1]);
        questDAO.markAsDone(userID, questID);
        helper.switchUserToPage(httpExchange, "/mentor/students");
    }

    private Integer handleAddingArtifactCategory(String category) {
        switch (category) {
            case "magic":
                return 1;
            case "basic":
                return 2;
        }
        return 1;
    }

    private Integer handleAddingQuestCategory(String category) {
        switch (category) {
            case "easy":
                return 1;
            case "medium":
                return 2;
            case "hard":
                return 3;
        }
        return 1;
    }

    private void setupClass(String content, HttpExchange httpExchange) throws IOException {
        int userId = Integer.parseInt(content.split("&")[0]);
        String className = content.split("&")[1];
        int classId = getClassId(className);
        studentDAO.updateClass(userId, classId);
        helper.switchUserToPage(httpExchange, "/mentor/students");
    }

    private int getClassId(String className) {
        switch (className) {
            case "progbasics":
                return 1;
            case "java":
                return 2;
            case "web":
                return 3;
            case "advanced":
                return 4;
        }
        return 1;
    }
}

