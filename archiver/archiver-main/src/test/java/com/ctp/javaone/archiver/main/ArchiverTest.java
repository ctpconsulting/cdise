package com.ctp.javaone.archiver.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.junit.Test;

public class ArchiverTest {

    @Test 
    public void testArchive() {
        Archiver archiver = mock(Archiver.class);
        ContainerInitialized ci = mock(ContainerInitialized.class);
        archiver.archive(ci);

        verify(archiver).archive(ci);
    }

}
