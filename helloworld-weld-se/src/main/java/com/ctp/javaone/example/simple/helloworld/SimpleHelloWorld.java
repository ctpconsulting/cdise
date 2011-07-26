package com.ctp.javaone.example.simple.helloworld;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.slf4j.Logger;

@ApplicationScoped
public class SimpleHelloWorld {
    
    @Inject
    private Logger log;

	/* The Weld container fires the event ContainerInitialized
	 * once the bootstrap is completed.
	 */
	public void hello(@Observes ContainerInitialized init, @Parameters List<String> vargs) {
		if(vargs.isEmpty()) {
		    log.info("Hello World");
		} else {
			log.info("Hello " + vargs.get(0));
		}
	}

}
