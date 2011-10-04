package com.ctp.javaone.example.helloworld;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.example.helloworld.service.HelloWorldService;

public class HelloWorldMain {

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        /* Alternative 1: Lookup bean with API and invoke */
        HelloWorldService helloWorld = container.instance().select(HelloWorldService.class).get();
        System.out.println(helloWorld.sayHello(args[0]));

        /* Alternative 2: Fire an event */
        container.event().select(ContainerInitialized.class).fire(new ContainerInitialized());

        weld.shutdown();
    }

}
