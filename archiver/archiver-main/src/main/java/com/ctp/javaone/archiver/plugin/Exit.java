package com.ctp.javaone.archiver.plugin;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.main.ExitEvent;
import com.ctp.javaone.archiver.persistence.Auditable;

@Command("exit")
@ApplicationScoped
@Auditable
public class Exit implements Plugin {
    
    @Inject Event<ExitEvent> exitEvent;
    private static final String RESULT = ""; 

    public String executeCommand(String... params) {
        exitEvent.fire(new ExitEvent());
        return RESULT;
    }

}
