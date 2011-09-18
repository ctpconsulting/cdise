package com.ctp.javaone.archiver.main;

import com.ctp.javaone.archiver.plugin.Status;

public class CommandExecutedEvent {

    private final String message;
    private final Status status;

    public CommandExecutedEvent(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
    
}
