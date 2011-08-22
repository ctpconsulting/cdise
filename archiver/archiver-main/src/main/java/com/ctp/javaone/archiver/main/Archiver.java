package com.ctp.javaone.archiver.main;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.archiver.command.CommandQualifier;
import com.ctp.javaone.archiver.persistence.Auditable;
import com.ctp.javaone.archiver.plugin.Plugin;
import com.ctp.javaone.archiver.shell.Shell;
import com.ctp.javaone.archiver.shell.ShellColor;

@ApplicationScoped
public class Archiver {

    @SuppressWarnings("unused")
    @Inject
    private @Parameters
    List<String> vargs;

    @Inject
    @Any
    private Instance<Plugin> plugins;

    @Inject
    private Shell shell;
    private boolean running = true;

    public void archive(@Observes ContainerInitialized init) {
        shell.info(greet());
        while (running) {
            final String command = shell.readLine(ShellColor.GREEN, ">> ");
            try {
                runCommand(command);
            } catch (UnsatisfiedResolutionException e) {
                shell.warn("Unknown command {0}", command);
            } catch (Exception e) {
                e.printStackTrace();
                shell.error("Command execution error: {0}", e.getMessage());
            }
        }
    }

    @Auditable
    private void runCommand(final String command) {
        CommandQualifier qualifier = new CommandQualifier(command);
        shell.info(plugins.select(qualifier).get().executeCommand());
    }

    private String greet() {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Welcome to archiver!\n");
        greeting.append("Run 'list' for a list of all available commands. \n\n");
        return greeting.toString();
    }
    
    private synchronized void terminate(@Observes ExitEvent event) {
        shell.info(event.getReason());
        running = false;
    }

}
