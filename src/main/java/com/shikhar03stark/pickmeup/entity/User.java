package com.shikhar03stark.pickmeup.entity;

import com.shikhar03stark.util.IdGenerator;

public class User {
    private final int id;
    private final String name;
    private final String gender;
    
    public User(String name, String gender) {
        this.id = IdGenerator.generateId();
        this.name = name;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
}
