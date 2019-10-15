package com.codecool.model;

import java.util.Objects;

public class Quest {
    private int id;
    private String name;
    private String description;
    private int reward;
    private String category;
    private boolean isCompleted;
    private int timesCompleted;

    public Quest(int id, String name, String description, int reward, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.category = category;
    }

    public Quest(int id, String name, String description, int reward, String category, Boolean isCompleted, int timesCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.category = category;
        this.isCompleted = isCompleted;
        this.timesCompleted = timesCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getTimesCompleted() {
        return timesCompleted;
    }

    public void setTimesCompleted(int timesCompleted) {
        this.timesCompleted = timesCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return id == quest.id &&
                reward == quest.reward &&
                isCompleted == quest.isCompleted &&
                name.equals(quest.name) &&
                description.equals(quest.description) &&
                category.equals(quest.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, reward, category, isCompleted);
    }
}
