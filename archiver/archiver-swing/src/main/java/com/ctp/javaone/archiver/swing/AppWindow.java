package com.ctp.javaone.archiver.swing;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.awt.EventQueue;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;

import com.ctp.javaone.swing.annotation.Action;
import com.ctp.javaone.swing.annotation.Clicked;
import com.ctp.javaone.swing.annotation.Text;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Singleton
public class AppWindow {

    private JFrame frame;
    private JTextField filePathText;
    private JLabel lblPleaseChoosA;
    @Inject
    @Text("Submit")
    @Action
    private JButton btnSubmit;
    @Inject
    @Text("browse")
    @Action
    @FileSelected
    private JButton btnBrowse;
    /**
     * @wbp.nonvisual location=104,279
     */
    private final JFileChooser fileChooser = new JFileChooser();

    private File selectedFile;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Weld weld = new Weld();
                    WeldContainer container = weld.initialize();
                    container.event().select(ContainerInitialized.class).fire(new ContainerInitialized());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void start(@Observes ContainerInitialized event) {
        System.out.println("Application started.");
    }
    
    public void browsePressed(@Observes @Clicked @FileSelected JButton button) {
        int returnValue = fileChooser.showDialog(frame, "Select");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            filePathText.setText(selectedFile.getAbsolutePath());
        }
    }

    public void submitPressed(@Observes @Clicked JButton button) {
        System.out.println("File " + selectedFile + " selected.");
    }
    
    @Qualifier
    @Inherited
    @Target({
            TYPE, METHOD, PARAMETER, FIELD
    })
    @Retention(RUNTIME)
    @Documented
    public @interface FileSelected {

    }

    /**
     * Initialize the contents of the frame.
     */
    @PostConstruct
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 451, 137);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        filePathText = new JTextField();
        frame.getContentPane().add(filePathText, BorderLayout.CENTER);
        filePathText.setColumns(10);

        
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                int returnValue = fileChooser.showDialog(frame, "Select");
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    selectedFile = fileChooser.getSelectedFile();
//                    filePathText.setText(selectedFile.getAbsolutePath());
//                }
            }
        });
        frame.getContentPane().add(btnBrowse, BorderLayout.EAST);

        // JButton btnSubmit = new JButton("Submit");
        // btnSubmit.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // System.out.println("File " + selectedFile + " selected.");
        // }
        // });
        frame.getContentPane().add(btnSubmit, BorderLayout.SOUTH);

        lblPleaseChoosA = new JLabel("Please choose a file");
        lblPleaseChoosA.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblPleaseChoosA, BorderLayout.NORTH);

        frame.setVisible(true);
    }

}
