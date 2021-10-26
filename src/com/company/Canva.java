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
    private final JButton addButton = new JButton();
    private final JTextField controlPoints = new JTextField();
    private final JTextField xPoint = new JTextField();
    private final JTextField yPoint = new JTextField();
    private final BezierCurveService bezierCurveService = new BezierCurveService();
    private int counter = 0;
    private final ArrayList<Point> points = new ArrayList<>();
    private final JSlider pointsSlider = new JSlider(3, 20);
    private ArrayList<Point> calculatedPoints = new ArrayList<>();
    private int index = -1;
    private Point endPoint;
    Canva() {
        this.setLayout(null);
        setButtons();
    }
    {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (!isAllowedToDraw) {
                    for (int i = 0; i < points.size(); i++) {
                        getSelectedPointData(e, i);

                    }
                }
            }

            private void getSelectedPointData(MouseEvent e, int i) {
                if (e.getX() >= points.get(i).getX() - 5 && e.getY() >= points.get(i).getY() - 5 &&
                        e.getX() <= points.get(i).getX() + 5 && e.getY() <= points.get(i).getY() + 5) {
                    index = i;
                    xPoint.setText(String.valueOf(points.get(i).getX()));
                    yPoint.setText(String.valueOf(points.get(i).getY()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isAllowedToDraw) {
                    points.add(e.getPoint());
                }
                counter++;
                drawBezier();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (!isAllowedToDraw) {

                    endPoint = e.getPoint();
                    xPoint.setText(String.valueOf(endPoint.getX()));
                    yPoint.setText(String.valueOf(endPoint.getY()));
                    points.set(index, endPoint);
                    calculatedPoints = bezierCurveService.drawCurve(points);

                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });


    }





    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        if (!calculatedPoints.isEmpty()) {
            for (int i = 0; i < calculatedPoints.size() - 1; i++) {
                graphics2D.drawLine(
                        (int) calculatedPoints.get(i).getX(),
                        (int) calculatedPoints.get(i).getY(),
                        (int) calculatedPoints.get(i + 1).getX(),
                        (int) calculatedPoints.get(i + 1).getY());

            }

        }
        for (Point point : points) {
            graphics2D.drawOval(
                    (int) point.getX() - 5,
                    (int) point.getY() - 5,
                    10,
                    10
            );
        }
        repaint();
    }

    private void setButtons() {
        startButton.setBounds(30, 730, 100, 25);
        xPoint.setBounds(550, 730, 50, 25);
        yPoint.setBounds(620, 730, 50, 25);
        addButton.setBounds(690, 730, 100, 25);
        addButton.addActionListener(e -> modifyOrAdd());
        startButton.addActionListener(e -> this.isAllowedToDraw = true);
        resetButton.setBounds(160, 730, 100, 25);
        resetButton.addActionListener(e -> resetCanvas());
        controlPoints.setBounds(500, 730, 30, 25);
        startButton.setText("Start");
        resetButton.setText("Reset");
        addButton.setText("Add");
        pointsSlider.setBounds(290, 730, 200, 25);
        pointsSlider.addChangeListener(e -> controlPoints.setText(String.valueOf(pointsSlider.getValue())));
        this.add(startButton);
        this.add(resetButton);
        this.add(controlPoints);
        this.add(pointsSlider);
        this.add(addButton);
        this.add(xPoint);
        this.add(yPoint);

    }

    private void modifyOrAdd() {
        Point point = new Point(
                Integer.parseInt(xPoint.getText()),
                Integer.parseInt(yPoint.getText()));
        if (isAllowedToDraw) {
            points.add(point);
            counter++;
            drawBezier();
        } else {
            points.set(index, point);
            calculatedPoints = bezierCurveService.drawCurve(points);
            repaint();

        }
    }

    private void resetCanvas() {
        addButton.setText("Add");
        counter = 0;
        index = 0;
        points.clear();
        calculatedPoints.clear();
    }
    private void drawBezier() {
        if (counter == pointsSlider.getValue()) {
            isAllowedToDraw = false;
            counter = 0;
            index = 0;
            calculatedPoints = bezierCurveService.drawCurve(points);
            addButton.setText("Modify");

        }
    }
}
