package com.ctp.javaone.swing.factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import com.ctp.javaone.swing.annotation.Action;
import com.ctp.javaone.swing.annotation.Text;

public class ComponentFactory {
<<<<<<< HEAD

    @Inject
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
=======
    
    @Inject Instance<Event<?>> eventInstance;
    @Inject Instance<?> instance;
    
    
    @Produces
    public JButton createJButton(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
//        Type type = ip.getType();
        
        JButton result = new JButton();
        if (annotated.isAnnotationPresent(Text.class)){
            Text textAnn = annotated.getAnnotation(Text.class);
            setButtonText(result, textAnn.value());
        }
        if (annotated.isAnnotationPresent(Action.class)){
>>>>>>> 8155a2d00518726d796c3208ffbb975d604ef268
            Action actionAnn = annotated.getAnnotation(Action.class);
            setActionListener(result, actionAnn);
        }
        return result;
    }

<<<<<<< HEAD
    private <T> void setActionListener(AbstractButton button, Action actionAnn) {
        Class<T> eventClass = (Class<T>) actionAnn.value();

        Qualifier[] qualifiers = actionAnn.qualifiers();

        final Event<T> event = eventInstance.get().select(eventClass, qualifiers);
        final T object = instance.select(eventClass, qualifiers).get();
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                event.fire(object);

            }
        });

    }

    private void setButtonText(AbstractButton button, String value) {
        button.setText(value);

=======
    private void setActionListener(AbstractButton button, Action actionAnn) {
        Class<?> eventClass = actionAnn.value();
        try {
            Object newInstance = eventClass.newInstance();
        } catch (Exception e1) {
            System.out.println("Unable to create event class");
            return;
        } 
        Qualifier[] qualifiers = actionAnn.qualifiers();
//        instance.select(newInstance, qualifiers);
//        eventInstance.select(eventClass, qualifiers);
        button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        
    }
    
//    @Inject
//    private void getEvent(Instance<Event<?>> evtInstance){
//        evtInstance.select(subtype, qualifiers)
//    }

    private void setButtonText(AbstractButton button, String value) {
        button.setText(value);
        
>>>>>>> 8155a2d00518726d796c3208ffbb975d604ef268
    }

}
