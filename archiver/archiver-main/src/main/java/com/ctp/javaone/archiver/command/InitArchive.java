package com.ctp.javaone.archiver.command;

import java.io.File;
import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.archiver.persistence.Auditable;

@ThreadScoped
@Auditable
@ShellCommand("create-archive")
public class InitArchive implements Command {

    @Inject
    private File current;
    
    @Inject
    private Event<File> archiveCreated;

    @Override
    public Result execute(String... params) {
        try {
            if (params == null || params.length == 0) {
                return new Result("Archive name missing", Status.FAILURE);
            }
            String path = current.getAbsolutePath() + File.separator + ".archive";
            File archive = new File(path);
            if (!archive.exists())
                FileUtils.forceMkdir(archive);
            File name = new File(path + File.separator + params[0]);
            if (!name.exists())
                name.createNewFile();
            archiveCreated.fire(current);
            return new Result("Archive initialized", Status.SUCCESS);
        } catch (IOException e) {
            return new Result("Archive creation failed: " + e.getMessage(), Status.FAILURE);
        }
    }
}
