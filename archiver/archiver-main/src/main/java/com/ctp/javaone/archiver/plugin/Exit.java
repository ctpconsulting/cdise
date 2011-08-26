package com.ctp.javaone.archiver.plugin;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.main.ExitEvent;
import com.ctp.javaone.archiver.persistence.Auditable;

@Command("exit")
@ThreadScoped
@Auditable
public class Exit implements Plugin {
    
    @Inject Event<ExitEvent> exitEvent;
    private static final String RESULT = ""; 

    public String executeCommand(String... params) {
        exitEvent.fire(new ExitEvent());
        return RESULT;
    }

}
