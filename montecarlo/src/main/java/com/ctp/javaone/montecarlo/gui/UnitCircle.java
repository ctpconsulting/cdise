package com.ctp.javaone.montecarlo.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.swing.JPanel;

import com.ctp.javaone.montecarlo.event.SampleEvent;

@Singleton
public class UnitCircle extends JPanel {

    private static final long serialVersionUID = -7694004411292484731L;

    private static final int xOffset = 20;
    private static final int yOffset = 20;
    private static final int axisOffset = 10;
    
    //Use these constants to control Circle and points size 
    private static final int radius = 300;
    private static final int pointSize = 1;
    
    private Map<Point2D, Boolean> points;
    
    @PostConstruct
    public void initialize() {
        this.setBackground(Color.WHITE);
        this.points = new HashMap<Point2D, Boolean>();
    }
    
    public void addPoint(@Observes SampleEvent sampleEvent) {
        addPoint(sampleEvent.getX(), sampleEvent.getY());
    }
    
    public void addPoint(double x, double y) {
        double relativeX = x*radius + radius + xOffset;
        double relativeY = -y*radius + radius + yOffset;
        
        boolean isInsideUC = false;
        if (x*x + y*y <= 1) isInsideUC = true;
        
        Point2D point = new Point2D.Double(relativeX, relativeY);
        synchronized (points) {
            points.put(point, isInsideUC);
        }
        repaint();
    }
    
    public Dimension getPreferredSize() {
        int width = 2 * (radius + xOffset) + axisOffset;
        int height = 2 * (radius + yOffset) + axisOffset;
        return new Dimension(width,height);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2*pointSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        
        g2.draw(new Ellipse2D.Double(xOffset, yOffset, 2*radius, 2*radius));
        g2.draw(new Rectangle2D.Double(xOffset, yOffset, 2*radius, 2*radius));
        
        float dash1[] = {3.0f};
        BasicStroke dashed = new BasicStroke(2.0f,
                                              BasicStroke.CAP_BUTT,
                                              BasicStroke.JOIN_MITER,
                                              3.0f, dash1, 0.0f);
        g2.setStroke(dashed);
        g2.draw(new Line2D.Double(axisOffset, yOffset + radius, 2 * (xOffset + radius) - axisOffset, yOffset + radius));
        g2.draw(new Line2D.Double(xOffset + radius, axisOffset, xOffset + radius, 2 * (yOffset + radius) - axisOffset));
        
        g2.setStroke(new BasicStroke(2*pointSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        synchronized (points) {
            Iterator<Point2D> iterator = points.keySet().iterator();
            while (iterator.hasNext()) {
                Point2D point2d = (Point2D) iterator.next();
                if (points.get(point2d).equals(true)) {
                    g2.setColor(Color.BLUE);
                } else {
                    g2.setColor(Color.RED);
                }
                int x = (int) point2d.getX();
                int y = (int) point2d.getY();
                g2.drawOval(x - pointSize, y - pointSize, 2*pointSize, 2*pointSize);
            }
        }
    }
}
