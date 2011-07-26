package com.ctp.javaone.example.helloworld;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.example.helloworld.service.HelloWorldService;

@ApplicationScoped
public class HelloWorld {

	@Inject
	private @Parameters List<String> vargs;
	
	@Inject
	private HelloWorldService helloService;
	
	public void printHelloWorld(@Observes ContainerInitialized init ) {

		if(!vargs.isEmpty()) {
			System.out.println(helloService.sayHello(vargs.get(0)));
		}
		
	}
	
}
