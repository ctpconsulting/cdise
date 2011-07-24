package com.ctp.javaone.example.helloworld;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ctp.javaone.example.helloworld.HelloWorldService;

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
		Assert.assertEquals("Hello Amy Winehouse", helloMessage);
	}

}
