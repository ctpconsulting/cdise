package com.ctp.javaone.archiver.main;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.command.Result;
import com.ctp.javaone.archiver.command.Status;

public class CommandWorker implements Runnable {
    
    @Inject
    private Event<CommandExecutedEvent> event;

    private Command plugin;
    private String[] params;

    @Override
    public void run() {
        try {
            Result result = plugin.execute(params);
            event.fire(new CommandExecutedEvent(result.getReason(), result.getStatus()));
        } catch (Exception e) {
            event.fire(new CommandExecutedEvent("Command execution error: " + e.getMessage(), Status.FAILURE));
        }
    }
    
    public void setWorkerContext(Command plugin, String[] params) {
        this.plugin = plugin;
        this.params = params;
    }

    public Command getPlugin() {
        return plugin;
    }

    public String[] getParams() {
        return params;
    }

}
