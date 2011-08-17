package com.ctp.javaone.archiver.persistence.model;


import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(name = Action.FIND_ALL, query = "SELECT a FROM Action a ORDER BY a.id ASC")
})
public class Action {

    public static final String FIND_ALL = "findAllActions";

    @Id
    @GeneratedValue
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private String action;
    
    public Action() {
    }
    
    public String toString() {
        return MessageFormat.format("[{0}]: [{1,date,dd.MM.yyyy-HH:mm}] {2}", String.format("%05d", id), timestamp, action);
    }

    public Action(String action) {
        this.action = action;
        this.timestamp = new Date();
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

    public Date getTimestamp() {
        return timestamp;
    }

}
