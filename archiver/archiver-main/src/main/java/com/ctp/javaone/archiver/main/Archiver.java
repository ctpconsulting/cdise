package com.ctp.javaone.archiver.main;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.archiver.persistence.Auditable;
import com.ctp.javaone.archiver.shell.Shell;
import com.ctp.javaone.archiver.shell.ShellColor;

@ApplicationScoped
public class Archiver {

    private static final int POOL_SIZE = 3;

    @SuppressWarnings("unused")
    @Inject @Parameters
    private List<String> vargs;

    @Inject
    private Instance<CommandWorker> commandWorkers;

    @Inject
    private Shell shell;

    private ExecutorService executor;

    public void archive(@Observes ContainerInitialized init) {
        shell.info(greet());
        while (true) {
            final String command = shell.readLine(ShellColor.GREEN, ">> ");
            runCommand(command);
        }
    }

    @Auditable
    private void runCommand(String command) {
        CommandWorker commandExecuter = commandWorkers.get();
        commandExecuter.setCommand(command);
        executor.execute(commandExecuter);
    }

    private String greet() {
        StringBuilder greeting = new StringBuilder();
        greeting.append("Welcome to archiver!\n");
        greeting.append("Run 'list' for a list of all available commands. \n\n");
        return greeting.toString();
    }

    @PostConstruct
    void init() {
        executor = Executors.newFixedThreadPool(POOL_SIZE);
    }

    synchronized void printCommandResult(@Observes CommandExecutedEvent event) {
        shell.info(event.getMessage());
    }

    synchronized void terminate(@Observes ExitEvent event) {
        shell.info(event.getReason());
        executor.shutdownNow();
        System.exit(0);
    }

}
