package com.ctp.javaone.example.helloworld;

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
public class HelloWorldServiceTest {

    @Inject
    HelloWorldService helloWorldService;
    
    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addClasses(HelloWorldService.class)
                .addAsManifestResource(new ByteArrayAsset("<beans/>".getBytes()), 
                                       ArchivePaths.create("beans.xml"));
    }
    
    @Test
    public void testSayHello() {
        String helloMessage = helloWorldService.sayHello("Amy Winehouse");
        assertEquals("Hello Amy Winehouse", helloMessage);
    }

}
