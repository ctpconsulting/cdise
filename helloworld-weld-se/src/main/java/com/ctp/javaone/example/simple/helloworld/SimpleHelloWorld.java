package com.ctp.javaone.example.simple.helloworld;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SimpleHelloWorld {
    
    private final Logger log = LoggerFactory.getLogger(SimpleHelloWorld.class);

    @Inject
    private @Parameters List<String> vargs;

    public void main(@Observes ContainerInitialized init) {
        if (vargs.isEmpty()) {
            log.info("Hello World");
        } else {
            log.info("Hello " + vargs.get(0));
        }
    }

}
