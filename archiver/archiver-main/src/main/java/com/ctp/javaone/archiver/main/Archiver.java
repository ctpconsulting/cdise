package com.ctp.javaone.archiver.main;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.archiver.command.Status;
import com.ctp.javaone.archiver.shell.Shell;
import com.ctp.javaone.archiver.shell.ShellColor;

@ApplicationScoped
public class Archiver {

    @Inject
    private CommandExecutor executor;

    @Inject
    private Shell shell;
    
    private volatile boolean shutdownRequested = false;

    public void archive(@Observes ContainerInitialized init) {
        shell.info(greet());
        while (!shutdownRequested) {
            final String command = shell.readLine(ShellColor.GREEN, "$ ");
            runCommand(command);
        }
    }

    private void runCommand(String command) {
        executor.executeCommand(command);
    }

    private String greet() {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Welcome to archiver!\n");
        greeting.append("Run 'list' for a list of all available commands. \n");
        return greeting.toString();
    }

    void printCommandResult(@Observes CommandExecutedEvent event) {
        String message = event.getMessage();
        if (event.getStatus() == Status.SUCCESS && StringUtils.isNotEmpty(message))
            shell.info(message);
        else
            shell.error(message);
    }

    void terminate(@Observes ExitEvent event) {
        shutdownRequested = true;
        shell.info(event.getReason());
    }

}
