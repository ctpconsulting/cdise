package com.ctp.javaone.example.simple.helloworld;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@ApplicationScoped
public class SimpleHelloWorld {

	@Inject
	private @Parameters List<String> vargs;
	
	public void main(@Observes ContainerInitialized init) {
		if(vargs.isEmpty()) {
			System.out.println("Hello World");
		} else {
			System.out.println("Hello " + vargs.get(0));
		}
	}
}
