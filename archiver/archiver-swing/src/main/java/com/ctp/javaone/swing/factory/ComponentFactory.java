package com.ctp.javaone.swing.factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import com.ctp.javaone.archiver.swing.AppWindow.FileSelected;
import com.ctp.javaone.swing.annotation.Action;
import com.ctp.javaone.swing.annotation.Clicked;
import com.ctp.javaone.swing.annotation.Text;

public class ComponentFactory {

    @Inject @Any
    Instance<Event<Object>> eventInstance;
    @Inject
    Instance<Object> instance;

    @Produces
    public JButton createJButton(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        // Type type = ip.getType();

        JButton result = new JButton();
        if (annotated.isAnnotationPresent(Text.class)) {
            Text textAnn = annotated.getAnnotation(Text.class);
            setButtonText(result, textAnn.value());
        }
        if (annotated.isAnnotationPresent(Action.class)) {
            Action actionAnn = annotated.getAnnotation(Action.class);
            setActionListener(result, actionAnn, ip);
        }
        return result;
    }
    
    @Produces @FileSelected
    public JButton createDefaultJButton(InjectionPoint ip) {
        return createJButton(ip);
    }

    private <T> void setActionListener(final AbstractButton button, Action actionAnn, InjectionPoint ip) {
        Class<T> eventClass = (Class<T>) ip.getType();

        Set<Annotation> qualifiers = new HashSet<Annotation>(ip.getQualifiers());
        Annotation clicked = new Qualifier() {
            public Class<? extends Annotation> annotationType() {
                return Clicked.class;
            }
        };
        qualifiers.add(clicked);
        Annotation[] wrappedQualifiers = qualifiers.toArray(new Annotation[0]);

        final Event<T> event = eventInstance.select(wrappedQualifiers).get().select(eventClass, wrappedQualifiers);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                event.fire((T) button);

            }
        });

    }
    

    private void setButtonText(AbstractButton button, String value) {
        button.setText(value);
    }
    

}
