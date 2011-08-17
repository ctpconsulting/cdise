package com.ctp.javaone.archiver.persistence;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ctp.javaone.archiver.persistence.model.Action;

@Auditable
@Interceptor
public class AuditInterceptor {

    @Inject
    private EntityManager entityManager;

    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();

        Action action = new Action(ctx.getMethod().getDeclaringClass().getSimpleName());
        transaction.begin();
        entityManager.persist(action);
        transaction.commit();

        return ctx.proceed();
    }
}
