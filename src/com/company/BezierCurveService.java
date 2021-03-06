package com.company;

import java.awt.*;
import java.util.ArrayList;

public class BezierCurveService {

     private  final  PascalTriangleService pascalTriangleService = new PascalTriangleService();
    public ArrayList<Point> drawCurve(ArrayList<Point> points) {
        float t = 0;
        int[] pascals= pascalTriangleService.getPascalRow(points.size()+1);
        ArrayList<Point> calculatedPoints=  new ArrayList<>();
        calculatePoints(points, t, pascals, calculatedPoints);
        return calculatedPoints;
    }

    private void calculatePoints(ArrayList<Point> points, float t, int[] pascals, ArrayList<Point> calculatedPoints) {
        for(int i =0 ;i<201;i++)
        {
            float x=0;
            float y=0;
            for(int j = 0; j< points.size(); j++)
            {
                x+= pascals[j]*Math.pow((1- t), points.size()-1-j)*Math.pow(t,j)* points.get(j).getX();
                y+= pascals[j]*Math.pow((1- t), points.size()-1-j)*Math.pow(t,j)* points.get(j).getY();
            }
            Point p = new Point((int)x,(int)y);
            calculatedPoints.add(p);
            t +=0.005;
        }
    }
}
