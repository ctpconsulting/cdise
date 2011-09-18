package com.ctp.javaone.archiver.main;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.ctp.javaone.archiver.plugin.Plugin;
import com.ctp.javaone.archiver.plugin.Result;
import com.ctp.javaone.archiver.plugin.Status;

public class CommandWorker implements Runnable {
    
    @Inject
    private Event<CommandExecutedEvent> event;

    private Plugin plugin;
    private String[] params;

    @Override
    public void run() {
        try {
            Result result = plugin.executeCommand(params);
            event.fire(new CommandExecutedEvent(result.getReason(), result.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
            event.fire(new CommandExecutedEvent("Command execution error: " + e.getMessage(), Status.FAILURE));
        }
    }
    
    public void setWorkerContext(Plugin plugin, String[] params) {
        this.plugin = plugin;
        this.params = params;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String[] getParams() {
        return params;
    }

}
