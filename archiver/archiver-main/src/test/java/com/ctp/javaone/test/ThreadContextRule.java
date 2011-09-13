package com.ctp.javaone.test;

import org.jboss.weld.environment.se.WeldSEBeanRegistrant;
import org.jboss.weld.environment.se.contexts.ThreadContext;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ThreadContextRule implements MethodRule {

    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final ThreadContext threadContext = WeldSEBeanRegistrant.THREAD_CONTEXT;
                try {
                    threadContext.activate();
                    base.evaluate();
                } finally {
                    threadContext.invalidate();
                    threadContext.deactivate();
                }
            }
        };
    }

}
