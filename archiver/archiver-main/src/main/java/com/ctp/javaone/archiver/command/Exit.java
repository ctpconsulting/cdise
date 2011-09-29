package com.ctp.javaone.archiver.command;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.archiver.main.ExitEvent;
import com.ctp.javaone.archiver.persistence.Auditable;

@ThreadScoped
@Auditable
@ShellCommand("exit")
public class Exit implements Command {
    
    @Inject 
    private Event<ExitEvent> exitEvent; 

    public Result execute(String... params) {
        exitEvent.fire(new ExitEvent());
        return Result.SUCCESS;
    }

}
