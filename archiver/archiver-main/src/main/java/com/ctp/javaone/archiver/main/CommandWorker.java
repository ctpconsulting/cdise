package com.ctp.javaone.archiver.main;

import java.util.Arrays;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;

import com.ctp.javaone.archiver.command.CommandQualifier;
import com.ctp.javaone.archiver.plugin.Plugin;

public class CommandWorker implements Runnable {

    @Inject
    @Any
    private Instance<Plugin> plugins;

    @Inject
    private Event<CommandExecutedEvent> event;

    private String command;

    public void run() {
        try {
            String[] tokens = command.split(" ");

            String[] params = null;
            if (tokens.length > 1) {
                params = Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            CommandQualifier qualifier = new CommandQualifier(tokens[0]);
            Instance<Plugin> select = plugins.select(qualifier);
            Plugin plugin = select.get();

            String result = plugin.executeCommand(params);
            event.fire(new CommandExecutedEvent(result));
        } catch (UnsatisfiedResolutionException e) {
            event.fire(new CommandExecutedEvent("Unknown command " + command));
        } catch (Exception e) {
            e.printStackTrace();
            event.fire(new CommandExecutedEvent("Command execution error: " + e.getMessage()));
        }
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
