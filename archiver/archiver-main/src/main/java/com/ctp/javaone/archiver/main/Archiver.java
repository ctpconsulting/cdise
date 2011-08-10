package com.ctp.javaone.archiver.main;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.slf4j.Logger;

import com.ctp.javaone.archiver.main.service.ArchiverService;

@ApplicationScoped
public class Archiver {

    @Inject
    private @Parameters List<String> vargs;
    
    @Inject
    private Logger log;

    @Inject 
    private ArchiverService archiverService;

    public void archive(@Observes ContainerInitialized init) {
        if (!vargs.isEmpty()) {
			log.info("Starting the archiving process...");
			
			//TODO archiving actions to be handeld here with help of ArchiverService
            //example of a service call:
			log.info(archiverService.sayHello(vargs.get(0)));
			
			log.info("End of archiving process");
        }
    }

}
