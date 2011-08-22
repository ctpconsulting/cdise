package com.ctp.javaone.archiver.archive;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.ctp.javaone.archiver.shell.Shell;

@ApplicationScoped
public class ArchivingResult {

    private int archivedFilesCounter;

    @Inject
    private Shell shell;

    @PostConstruct
    private void init() {
        archivedFilesCounter = 0;
    }

    public void onArchive(@Observes FileArchivingEvent event) {
        shell.info("Archiving task finished, source file: {0} , target: {1}", event.getSource(), event.getTarget());
        archivedFilesCounter++;
    }

    public int getArchivedFilesCounter() {
        return archivedFilesCounter;
    }

    public void resetArchivedFilesCounter() {
        this.archivedFilesCounter = 0;
    }
}
