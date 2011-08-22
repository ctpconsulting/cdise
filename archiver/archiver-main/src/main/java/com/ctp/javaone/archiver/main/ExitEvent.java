package com.ctp.javaone.archiver.main;

public class ExitEvent {
    private String reason;
    // ****************************************************************************
    // PUBLIC METHODS
    // ****************************************************************************
    public ExitEvent() {
        this("Good Bye!");
    }
    
    public String getReason() {
        return reason;
    }

    // ****************************************************************************
    // ACCESSORS AND MUTATORS
    // ****************************************************************************
    public void setReason(String reason) {
        this.reason = reason;
    }

    public ExitEvent(String reason) {
        this.reason = reason;
    }
}
