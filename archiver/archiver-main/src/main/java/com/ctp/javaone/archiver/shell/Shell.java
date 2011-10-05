package com.ctp.javaone.archiver.shell;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import com.ctp.javaone.archiver.persistence.model.Archive;
import com.ctp.javaone.archiver.scope.ArchiveScoped;
import com.ctp.javaone.archiver.scope.Current;

@ApplicationScoped
public class Shell {
    
    private Scanner scanner;
    
    private File currentDirectory = new File(".");
    
    @Inject @Current @ArchiveScoped
    private Instance<Archive> archive;
    
    // ------------------------------------------------------------------
    // PUBLIC METHODS
    // ------------------------------------------------------------------

    public void print(ShellColor color, String message, Object... args) {
        System.out.print(transform(color, message, args));
    }
    
    public void print(String message, Object... args) {
        print(ShellColor.NONE, message, args);
    }
    
    public void info(String message, Object... args) {
        println(ShellColor.GREEN, message, args);
    }
    
    public void warn(String message, Object... args) {
        println(ShellColor.YELLOW, message, args);
    }
    
    public void error(String message, Object... args) {
        println(ShellColor.RED, message, args);
    }
    
    public void println(String message, Object... args) {
        println(ShellColor.NONE, message, args);
    }
    
    public String readLine(String message) {
        return readLine(ShellColor.NONE, message);
    }
    
    public String readLine(ShellColor color, String message) {
        printArchive();
        print(ShellColor.BLUE, "[" + currentDirectory() + "] ");
        print(color, message);
        AnsiConsole.out.flush();
        return scanner.nextLine();
    }
    
    private void printArchive() {
        try {
            if (!archive.isUnsatisfied()) {
                Archive currentArchive = archive.get();
                print(ShellColor.RED, "[" + currentArchive.getName() + "] ");
            }
        } catch (ContextNotActiveException e) {}
    }

    @Produces
    public File getCurrentDirectory() {
        return currentDirectory;
    }
    
    // ------------------------------------------------------------------
    // PACKAGE METHODS
    // ------------------------------------------------------------------
    
    @PostConstruct
    void initialize() {
        AnsiConsole.systemInstall();
        scanner = new Scanner(System.in);
    }
    
    @PreDestroy
    void shutdown() {
        AnsiConsole.systemUninstall();
    }
    
    void directoryChanged(@Observes File file) {
        currentDirectory = file;
    }
    
    // ------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------
    
    private void println(ShellColor color, String message, Object... args) {
        AnsiConsole.out.println(transform(color, message, args));
    }
    
    private String transform(ShellColor color, String message, Object[] args) {
        return color.applyTo(new Ansi()).render(MessageFormat.format(message, args)).reset().toString();
    }
    
    private String currentDirectory() {
        try {
            String currentPath = currentDirectory.getCanonicalPath();
            return currentPath.substring(currentPath.lastIndexOf(File.separator) + 1);
        } catch (IOException e) {
            return "";
        }
    }

}
