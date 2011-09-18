package com.ctp.javaone.archiver.main;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javassist.util.proxy.ProxyFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;

import com.ctp.javaone.archiver.command.Async;
import com.ctp.javaone.archiver.command.CommandQualifier;
import com.ctp.javaone.archiver.plugin.Plugin;
import com.ctp.javaone.archiver.plugin.Status;

@ApplicationScoped
public class CommandExecutor {
    
    private static final int POOL_SIZE = 3;

    @Inject @Any
    private Instance<Plugin> plugins;

    @Inject
    private Instance<CommandWorker> commandWorker;
    
    @Inject
    private Event<CommandExecutedEvent> event;
    
    private ExecutorService executor;
    
    @PostConstruct
    void init() {
        executor = Executors.newFixedThreadPool(POOL_SIZE);
    }
    
    @PreDestroy
    void terminate() {
        if (!executor.isShutdown())
            executor.shutdownNow();
    }
    
    void exit(@Observes ExitEvent event) {
        terminate();
    }
    
    public void executeCommand(String command) {
        try {
            String[] tokens = command.split(" ");
            String[] params = null;
            if (tokens.length > 1) {
                params = Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            
            Plugin plugin = resolvePlugin(tokens[0]);
            CommandWorker worker = commandWorker.get();
            worker.setWorkerContext(plugin, params);
            if (isAsync(plugin)) {
                executor.execute(worker);
            } else {
                worker.run();
            }
        } catch (UnsatisfiedResolutionException e) {
            event.fire(new CommandExecutedEvent("Unknown command " + command, Status.FAILURE));
        }
    }
    
    private Plugin resolvePlugin(String command) {
        CommandQualifier qualifier = new CommandQualifier(command);
        Instance<Plugin> select = plugins.select(qualifier);
        return select.get();
    }
    
    private boolean isAsync(Plugin plugin) {
        Class<?> pluginClass = plugin.getClass();
        if (ProxyFactory.isProxyClass(pluginClass)) {
            pluginClass = plugin.getClass().getSuperclass();
            return pluginClass.isAnnotationPresent(Async.class);
        }
        return false;
    }
}
