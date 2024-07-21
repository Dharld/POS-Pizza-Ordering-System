package org.example.demo.models;

public class Category {
    private int id;
    private String name;
    private boolean active;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }
}
