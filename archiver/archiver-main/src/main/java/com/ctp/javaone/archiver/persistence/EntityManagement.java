package com.ctp.javaone.archiver.persistence;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagement {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("archiverPU");

    @Produces
    public EntityManager createEntityManager(InjectionPoint injection) {
        return entityManagerFactory.createEntityManager();
    }

    void close(@Disposes EntityManager entityManager) {
        entityManager.close();
        entityManagerFactory.close();
    }
}
