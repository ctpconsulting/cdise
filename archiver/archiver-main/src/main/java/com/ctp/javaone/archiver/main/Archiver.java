package com.ctp.javaone.archiver.main;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.archiver.command.Command;
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

    public void archive(@Observes ContainerInitialized init) {

        shell.info(getGreeting());

        while (true) {
            final String command = shell.readLine(ShellColor.GREEN, ">> ");

            try {
                runCommand(command);
            } catch (Exception e) {
                shell.warn("Unknown command {0}", command);
            }
        }
    }

    @SuppressWarnings("all")
    @Auditable private void runCommand(final String command) {
        abstract class CommandQualifier extends AnnotationLiteral<Command> implements Command {
        }
        Command selectedCommand = new CommandQualifier() {
            public String value() {
                return command;
            }
        };

        shell.info(plugins.select(selectedCommand).get().executeCommand());
    }

    private String getGreeting() {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Welcome to archiver!\n");
        greeting.append("Run 'list' for a list of all available commands. \n\n");
        return greeting.toString();
    }

}
