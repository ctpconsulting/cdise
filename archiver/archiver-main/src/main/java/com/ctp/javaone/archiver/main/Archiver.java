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
import com.ctp.javaone.archiver.main.service.ArchiverService;
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

    @Inject
    private ArchiverService archiverService;

    public void archive(@Observes
    ContainerInitialized init) {
        shell.info(archiverService.getGreeting());

        // Check Injected Plugins
        // shell.warn("isAmbiguous: " + plugins.isAmbiguous());
        // shell.warn("isUnsatisfied: " + plugins.isUnsatisfied());
        // for (Plugin plugin : this.plugins) {
        // shell.warn("added plugin: " + plugin.toString());
        // }

        while (true) {
            final String command = shell.readLine(ShellColor.GREEN, ">> ");

            try {
                abstract class CommandQualifier extends AnnotationLiteral<Command> implements Command {
                }
                Command selectedCommand = new CommandQualifier() {
                    public String value() {
                        return command;
                    }
                };

                shell.info(plugins.select(selectedCommand).get().executeCommand());
            } catch (Exception e) {
                switch (ArchiverService.CommandEnum.toCommand(command)) {
                case exit:
                    return;
                case unknown:
                    shell.warn("Unknown command {0}", command);
                    break;
                default:
                    shell.error("Unhandled case");
                }
            }
        }
    }

}
