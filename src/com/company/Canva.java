package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;

public class Canva extends JPanel {

    private boolean isAllowedToDraw = false;
    private final JButton startButton = new JButton();
    private final JButton resetButton = new JButton();
    private final JTextField controlPoints = new JTextField();
    private final BezierCurveService bezierCurveService= new BezierCurveService();
    private int counter =0;
    private ArrayList<Point> points = new ArrayList<>();
    private final JSlider pointsSlider = new JSlider(3,20);
    private ArrayList<Point> calculatedPoints = new ArrayList<>();

    {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isAllowedToDraw) points.add(e.getPoint());
                counter++;
                if(counter==pointsSlider.getValue()) {
                    isAllowedToDraw = false;
                    counter = 0;
                    calculatedPoints=bezierCurveService.drawCurve(points);
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {


            }
        });


    }
    Canva(){
        this.setLayout(null);
        setButtons();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        if (calculatedPoints.isEmpty()==false)
        {
            for(int i=0;i<calculatedPoints.size()-1;i++)
            {
                graphics2D.drawLine(
                        (int)calculatedPoints.get(i).getX(),
                        (int)calculatedPoints.get(i).getY(),
                        (int)calculatedPoints.get(i+1).getX(),
                        (int) calculatedPoints.get(i+1).getY());
            }
        }
        repaint();
    }

    private void setButtons() {
        startButton.setBounds(30, 730, 100, 25);
        startButton.addActionListener(e->this.isAllowedToDraw=true);
        resetButton.setBounds(160, 730, 100, 25);
        controlPoints.setBounds(550, 730, 100, 25);
        startButton.setText("Start");
        resetButton.setText("Reset");
        pointsSlider.setBounds(290,730,200,25);
        pointsSlider.addChangeListener(e->controlPoints.setText(String.valueOf(pointsSlider.getValue())));
        this.add(startButton);
        this.add(resetButton);
        this.add(controlPoints);
        this.add(pointsSlider);

    }
}
