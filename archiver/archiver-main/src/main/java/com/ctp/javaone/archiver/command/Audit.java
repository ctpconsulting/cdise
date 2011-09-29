package com.ctp.javaone.archiver.command;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.weld.environment.se.contexts.ThreadScoped;

import com.ctp.javaone.archiver.persistence.model.Action;

@ThreadScoped
@ShellCommand("audit")
public class Audit implements Command {

    @Inject
    private EntityManager entityManager;

    public Result execute(String... params) {
        StringBuilder result = new StringBuilder();

        Iterator<Action> iterator = entityManager.createNamedQuery(Action.FIND_ALL, Action.class).getResultList().iterator();
        while (iterator.hasNext()) {
            Action action = (Action) iterator.next();
            result.append(action.toString()).append("\n");
        }
        return new Result(result.toString(), Status.SUCCESS);
    }

}
