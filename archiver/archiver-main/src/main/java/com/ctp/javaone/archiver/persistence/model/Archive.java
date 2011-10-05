package com.ctp.javaone.archiver.persistence.model;

public class Archive {

    private String name;
    
    public Archive() {
    }

    public Archive(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
