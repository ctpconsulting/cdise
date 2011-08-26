package com.ctp.javaone.archiver.main;

public class CommandExecutedEvent {

    private String message;

    public CommandExecutedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
