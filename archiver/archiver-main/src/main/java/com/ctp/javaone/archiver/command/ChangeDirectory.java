package com.ctp.javaone.archiver.command;

import java.io.File;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

@ShellCommand("cd")
public class ChangeDirectory implements Command {
    
    @Inject
    private File currentDirectory;
    
    @Inject
    private Event<File> directoryChanged;

    @Override
    public Result execute(String... params) {
        if (params == null || params.length == 0) {
            directoryChanged.fire(FileUtils.getUserDirectory());
        }
        String path = currentDirectory.getAbsolutePath() + File.separator + params[0];
        File next = new File(path);
        if (!next.exists()) {
            return new Result("File " + params[0] + " does not exist", Status.FAILURE);
        }
        if (!next.isDirectory()) {
            return new Result("File " + params[0] + " is not a directory", Status.FAILURE);
        }
        directoryChanged.fire(next);
        return Result.SUCCESS;
    }

}
