package com.ctp.javaone.archiver.plugin;

import java.util.Iterator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ctp.javaone.archiver.command.Command;
import com.ctp.javaone.archiver.persistence.model.Action;

@Command("audit")
@ApplicationScoped
public class Audit implements Plugin {

    @Inject
    private EntityManager entityManager;

    public String executeCommand(String... params) {
        StringBuilder result = new StringBuilder();

        Iterator<Action> iterator = entityManager.createNamedQuery(Action.FIND_ALL, Action.class).getResultList().iterator();
        while (iterator.hasNext()) {
            Action action = (Action) iterator.next();
            result.append(action.toString()).append("\n");
        }
        return result.toString();
    }

}
