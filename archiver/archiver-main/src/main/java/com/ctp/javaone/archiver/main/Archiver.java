package com.ctp.javaone.archiver.main;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.archiver.main.service.ArchiverService;
import com.ctp.javaone.archiver.shell.Shell;
import com.ctp.javaone.archiver.shell.ShellColor;

@ApplicationScoped
public class Archiver {

    @SuppressWarnings("unused")
    @Inject
    private @Parameters List<String> vargs;
    
    @Inject
    private Shell shell;

    @Inject 
    private ArchiverService archiverService;

    public void archive(@Observes ContainerInitialized init) {
        shell.info(archiverService.getGreeting());

        while (true) {
            String command = shell.readLine(ShellColor.GREEN, ">> ");
            switch (ArchiverService.Command.toCommand(command)) {
            case exit:
                return;
            case unknown:
                shell.warn("Unknown command {0}", command);
            case help:
                shell.info(archiverService.getHelp());
            default:
                shell.error("Unhandled case");
            }
        }
    }

}
