/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import org.jfree.util.ShapeUtilities;

/**
 * Series shape 
 * @author MIGUEL_ANGEL
 */
class SeriesShape extends Label
{

    /**
     * Node
     */
    Node node;
    /**
     * Graphic
     */
    Node graphic;
    /**
     * AWT shape
     */
    private Shape shapeAWT;
    /**
     * Name
     */
    private final String name;
    /**
     * Color
     */
    Color color;

    /**
     * 
     * @param name Shape name
     * @param color Color
     */
    public SeriesShape(String name, Color color)
    {
        this.name = name;
        this.color = color;
        updateNode();
    }

    /**
     * 
     * @return Shape name
     */
    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * Update node
     */
    private void updateNode()
    {
        double size = 10.0;
        double delta = size / 2.0;

        if (getName().equals("rectangle"))
        {
            Rectangle r = new Rectangle(size, size);
            r.setFill(color);
            r.setStroke(color);
            node = r;
            graphic = r;
            shapeAWT = new Rectangle2D.Double(-delta, -delta, size, size);
            //shapeAWT=ShapeUtilities.create
        }
        if (getName().equals("circle"))
        {
            Circle c = new Circle(delta);
            c.setFill(color);
            c.setStroke(color);
            node = c;
            graphic = c;
            shapeAWT = new Ellipse2D.Double(-delta, -delta, size, size);
        }
        if (getName().equals("triangle"))
        {
            node = new SVGPath();
            ((SVGPath) node).setContent("M5,0 L10,8 L0,8 Z");
            ((SVGPath) node).setStroke(color);
            ((SVGPath) node).setFill(color);
            shapeAWT = ShapeUtilities.createUpTriangle((float)delta);
            graphic = node;

        }
        if (getName().equals("crux"))
        {
            node = new SVGPath();
            ((SVGPath) node).setContent("M2,0 L5,4 L8,0 L10,0 L10,2 L6,5 L10,8 L10,10 L8,10 L5,6 L2,10 L0,10 L0,8 L4,5 L0,2 L0,0 Z");
            ((SVGPath) node).setStroke(color);
            ((SVGPath) node).setFill(color);
            graphic = node;
            shapeAWT = ShapeUtilities.createDiagonalCross((float)delta, 1);
        }
        if (getName().equals("diamond"))
        {
            node = new SVGPath();
            ((SVGPath) node).setContent("M5,0 L10,9 L5,18 L0,9 Z");
            ((SVGPath) node).setStroke(color);
            ((SVGPath) node).setFill(color);
            graphic = node;
            shapeAWT = ShapeUtilities.createDiamond(10);

        }
        if (getName().equals("empty rectangle"))
        {
            Rectangle r = new Rectangle(10, 10);
            r.setFill(Color.TRANSPARENT);
            r.setStroke(color);
            node = r;
            graphic = node;
            shapeAWT = new Rectangle2D.Double(-delta, -delta, size, size);
        }
        if (getName().equals("empty circle"))
        {
            Circle c = new Circle(5);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(color);
            node = c;
            graphic = node;
            shapeAWT = new Ellipse2D.Double(-delta, -delta, size, size);
        }
        if (getName().equals("empty triangle"))
        {
            node = new SVGPath();
            ((SVGPath) node).setContent("M5,0 L10,8 L0,8 Z");
            ((SVGPath) node).setStroke(color);
            ((SVGPath) node).setFill(Color.TRANSPARENT);
            graphic = node;

            shapeAWT = ShapeUtilities.createUpTriangle((float)delta);

        }
        switch (getName())
        {
            case "empty diamond":
                node = new SVGPath();
                ((SVGPath) node).setContent("M5,0 L10,9 L5,18 L0,9 Z");
                ((SVGPath) node).setStroke(color);
                ((SVGPath) node).setFill(Color.TRANSPARENT);
                graphic = node;
                shapeAWT = ShapeUtilities.createDiamond(10);
                break;
            case "null":
                node = null;
                graphic = new SVGPath();
                ((SVGPath) graphic).setContent("M0,5 L10,5");
                ((SVGPath) graphic).setStroke(color);
                ((SVGPath) graphic).setFill(Color.TRANSPARENT);
                break;
        }
    }

    /**
     * 
     * @return Node
     */
    public Node getNode()
    {
        return node;
    }

    /**
     * 
     * @return shape graphic
     */
    public Node getShapeGraphic()
    {
        return graphic;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return AWT shape
     */
    public Shape getShapeAWT()
    {
        return shapeAWT;
    }
}
