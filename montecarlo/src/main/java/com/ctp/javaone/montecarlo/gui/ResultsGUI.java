package com.ctp.javaone.montecarlo.gui;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jboss.weld.environment.se.events.ContainerInitialized;

@Singleton
public class ResultsGUI extends JFrame {
    
    private static final long serialVersionUID = -9123770367840691259L;
    
    private JLabel unitCircleLabel;
    private JLabel resultPiLabel;
    private JTextField resultPiTextField;
    
    @Inject
    private UnitCircle unitCircle;
    
    public void start(@Observes ContainerInitialized event) {
        System.out.println("Application started.");
    }
    
    public void updateResult(double result) {
        resultPiTextField.setText("" + result);
    }
    
    @SuppressWarnings("unused")
    @PostConstruct
    private void initialize() {
        resultPiTextField = new JTextField();
        unitCircleLabel = new JLabel();
        resultPiLabel = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Montecarlo");

        unitCircleLabel.setText("Unit Circle:");
        resultPiLabel.setText("Calculated Pi:");
        resultPiTextField.setEditable(false);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(unitCircleLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(unitCircle))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resultPiLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(resultPiTextField)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {unitCircleLabel, resultPiLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitCircleLabel)
                    .addComponent(unitCircle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultPiLabel)
                    .addComponent(resultPiTextField))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pack();
        this.setVisible(true);
    }
}