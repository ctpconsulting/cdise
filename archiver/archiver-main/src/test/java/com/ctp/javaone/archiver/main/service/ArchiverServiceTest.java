package com.ctp.javaone.archiver.main.service;

import static junit.framework.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArchiverServiceTest {

    @Inject
    ArchiverService archiverService;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(ArchiverService.class)
                .addClasses(TestCommand.class)
                .addAsManifestResource(new ByteArrayAsset("<beans/>".getBytes()), 
                                       ArchivePaths.create("beans.xml"));
    }
    
    @Test
    public void testSayHello() {
        String helloMessage = archiverService.sayHello("Amy Winehouse");
        assertEquals("Hello Amy Winehouse", helloMessage);
    }

}
