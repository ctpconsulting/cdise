package com.ctp.javaone.archiver.persistence.model;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = Action.FIND_ALL, query = "SELECT a FROM Action a")
})
public class Action {

    public static final String FIND_ALL = "findAllActions";

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp timestamp;
    private String action;
    
    public Action() {
    }
    
    public String toString() {
        return id + " " + action + " " + timestamp;
    }

    public Action(String action) {
        this.action = action;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
