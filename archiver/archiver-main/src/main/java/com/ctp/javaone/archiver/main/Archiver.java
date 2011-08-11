package com.ctp.javaone.archiver.main;

import java.util.List;
import java.util.Scanner;

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
        	System.out.println(archiverService.getGreeting());
        	
			Scanner scanner = new Scanner(System.in);

			while(true) {
				System.out.print("\n>> ");
			    String command = scanner.nextLine();
			    switch (ArchiverService.Command.toCommand(command)) {
					case exit:
						return;
						
					case help:
					case unknown:
					default:
						System.out.println(archiverService.getHelp());
						break;
				}
			}
	}

}
