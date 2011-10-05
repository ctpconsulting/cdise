package com.ctp.javaone.archiver.scope;

import java.io.File;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

public class ArchiveScopeExtension implements Extension {

    public void registerContext(@Observes final AfterBeanDiscovery event, 
            final BeanManager manager) {
        event.addContext(new ArchiveScopeContext(manager, new File(".")));
    }
}
