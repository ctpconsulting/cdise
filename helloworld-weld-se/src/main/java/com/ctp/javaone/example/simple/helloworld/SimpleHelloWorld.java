package com.ctp.javaone.example.simple.helloworld;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@ApplicationScoped
public class SimpleHelloWorld {

    /*
     * The Weld container fires the event ContainerInitialized once the
     * bootstrap is completed.
     */
    public void hello(@Observes ContainerInitialized init, @Parameters List<String> vargs) {
        if (vargs.isEmpty()) {
            System.out.println("Hello World");
        } else {
            System.out.println("Hello " + vargs.get(0));
        }
    }

}
