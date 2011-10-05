package com.ctp.javaone.archiver.scope;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import com.ctp.javaone.archiver.persistence.model.Archive;

@Singleton
public class ArchiveScopeContext implements Context {
    
    @Inject
    private Logger logger;

    private Archive current;
    private File currentDirectory;
    private BeanManager manager;
    
    private Map<Contextual<?>, Object> components = new ConcurrentHashMap<Contextual<?>, Object>();
    private Map<Contextual<?>, Object> contexts = new ConcurrentHashMap<Contextual<?>, Object>();

    @Inject
    public ArchiveScopeContext(BeanManager manager, File currentDirectory) {
        this.manager = manager;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ArchiveScoped.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        T instance = get(contextual);
        if (instance == null) {
            instance = (T) components.get(contextual);
            if (instance == null) {
                instance = contextual.create(creationalContext);
                components.put(contextual, instance);
                contexts.put(contextual, creationalContext);
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Contextual<T> contextual) {
        return (T) components.get(contextual);
    }

    @Override
    public boolean isActive() {
        Archive currentArchive = getCurrent();
        return currentArchive != null;
    }
    
    @Produces
    @Current
    @ArchiveScoped
    public Archive currentArchive() {
        return current;
    }
    
    @PostConstruct
    public void init() {
        File archiveDir = searchArchive();
        if (archiveDir != null) {
            File archiveDesc = new File(archiveDir.getAbsolutePath() + File.separator + ".archive");
            current = new Archive(archiveDesc.list()[0]);
        }
    }
    
    @SuppressWarnings("unchecked")
    Archive getCurrent() {
        Bean<ArchiveScopeContext> bean = (Bean<ArchiveScopeContext>) manager.resolve(manager
                .getBeans(ArchiveScopeContext.class));
        CreationalContext<ArchiveScopeContext> context = manager.createCreationalContext(bean);
        ArchiveScopeContext scopedContext = (ArchiveScopeContext) manager
                .getReference(bean, ArchiveScopeContext.class, context);
        return scopedContext.current;
    }
    
    void currentDirChanged(@Observes File directory) {
        File newArchive = searchArchive(canonical(directory));
        Archive currentArchive = getCurrent();
        if (currentArchive == null && newArchive != null) {
            logger.info("Starting archive context");
            File archiveDesc = new File(directory.getAbsolutePath() + File.separator + ".archive");
            current = new Archive(archiveDesc.list()[0]);
        }
        if (currentArchive != null && newArchive == null) {
            logger.info("Stopping archive context");
            shutdown();
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PreDestroy
    void shutdown() {
        current = null;
        for (Entry<Contextual<?>, Object> componentEntry : components.entrySet()) {
           Contextual contextual = componentEntry.getKey();
           Object instance = componentEntry.getValue();
           CreationalContext creational = (CreationalContext) contexts.get(contextual);
           contextual.destroy(instance, creational);
        }
        components.clear();
        contexts.clear();
    }
    
    private File searchArchive() {
        return searchArchive(currentDirectory);
    }
    
    private File searchArchive(File directory) {
        while (directory != null) {
            if (containsArchive(directory))
                return directory;
            directory = directory.getParentFile();
        }
        return null;
    }
    
    private boolean containsArchive(File file) {
        for (String child : file.list()) {
            if (child.equals(".archive"))
                return true;
        }
        return false;
    }
    
    private File canonical(File file) {
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
