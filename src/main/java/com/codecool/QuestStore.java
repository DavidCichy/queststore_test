package com.codecool;

import com.codecool.controller.Controller;
import com.codecool.server.ContextCreator;

import java.io.IOException;


public class QuestStore{
    public static void main(String[] args){
        try{
            ContextCreator contextCreator = new ContextCreator();
            Controller controller = new Controller(contextCreator);
            controller.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

