package com.ctp.javaone.archiver.plugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.archiver.archive.ArchivingResult;
import com.ctp.javaone.archiver.archive.ArchivingTask;
import com.ctp.javaone.archiver.command.Async;
import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.persistence.Auditable;

@Async
@ThreadScoped
@Auditable
@Command("archive")
public class Archive implements Plugin {
    
    public static final int SIZE = 2;

    @Inject
    private Instance<ArchivingTask> archivingInstance;

    private ExecutorService executor;

    @Inject
    private ArchivingResult result;

    public Result executeCommand(String... params) {
        result.resetArchivedFilesCounter();
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("Please pass pathname of the folder to be archived");
        }
        File source = new File(params[0]);
        if (!source.exists()) {
            throw new IllegalArgumentException("The passed pathname does not exist.");
        }
        long sleep = 0;
        if (params.length > 1) {
            sleep = Long.valueOf(params[1]);
        }
        
        // TODO Introduce targetFolder as a new parameter
        File target = new File("." + File.separator + "target/" + params[0]);
        executor = Executors.newFixedThreadPool(SIZE);

        try {
            copyDirectory(source, target, sleep);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        int archivedFilesCount = result.getArchivedFilesCounter();
        
        return new Result("Archiving process concluded, total of archived files: " + archivedFilesCount, Status.SUCCESS);
    }

    private void copyDirectory(File sourceLocation, File targetLocation, long sleep) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]), sleep);
            }
        } else {
            ArchivingTask archiverTask = archivingInstance.get();
            archiverTask.setSource(sourceLocation);
            archiverTask.setTarget(targetLocation);
            archiverTask.setSleep(sleep);
            archiverTask.execute();
            //executor.execute(archiverTask);
        }
    }
}
