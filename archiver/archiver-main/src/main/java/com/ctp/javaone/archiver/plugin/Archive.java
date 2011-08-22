package com.ctp.javaone.archiver.plugin;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.ctp.javaone.archiver.archive.ArchivingResult;
import com.ctp.javaone.archiver.archive.ArchivingTask;
import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.persistence.Auditable;
import com.ctp.javaone.archiver.multithreading.ThreadPool;

@Command("archive")
@ApplicationScoped
@Auditable
public class Archive implements Plugin {

    @Inject
    Instance<ArchivingTask> archivingInstance;

    @Inject
    ThreadPool threadPool;

    @Inject
    ArchivingResult result;

    public synchronized String executeCommand(String... params) {

        if (params == null || params.length == 0) {
            throw new NullPointerException("Please pass pathname of the folder to be archived");
        }

        File source = new File(params[0]);
        if (!source.exists()) {
            throw new NullPointerException("The passed pathname does not exist.");
        }

        // TODO Introduce targetFolder as a new parameter
        File target = new File("C:\\archive\\" + source.getName());

        try {
            copyDirectory(source, target);
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage());
        }

        while (!threadPool.isEnded()) {

        }

        int archivedFilesCount = result.getArchivedFilesCounter();
        result.resetArchivedFilesCounter();

        return "Archiving process concluded, total of archived files: " + archivedFilesCount;
    }

    private void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
            }
        } else {
            ArchivingTask archiverTask = archivingInstance.get();
            File[] params = new File[2];
            params[0] = sourceLocation;
            params[1] = targetLocation;
            threadPool.assign(archiverTask, params);
        }
    }
}
