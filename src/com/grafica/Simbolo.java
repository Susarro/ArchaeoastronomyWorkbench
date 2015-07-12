/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

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
 *
 * @author MIGUEL_ANGEL
 */
class Simbolo extends Label
{

    Node nodo;
    Node representacion;
    private Shape shapeAWT;
    private final String nombre;
    Color color;

    public Simbolo(String nombre, Color color)
    {
        this.nombre = nombre;
        this.color = color;
        updateNode();
    }

    @Override
    public String toString()
    {
        return getNombre();
    }

    private void updateNode()
    {
        double size = 10.0;
        double delta = size / 2.0;

        if (getNombre().equals("rectángulo"))
        {
            Rectangle r = new Rectangle(size, size);
            r.setFill(color);
            r.setStroke(color);
            nodo = r;
            representacion = r;
            shapeAWT = new Rectangle2D.Double(-delta, -delta, size, size);
            //shapeAWT=ShapeUtilities.create
        }
        if (getNombre().equals("círculo"))
        {
            Circle c = new Circle(delta);
            c.setFill(color);
            c.setStroke(color);
            nodo = c;
            representacion = c;
            shapeAWT = new Ellipse2D.Double(-delta, -delta, size, size);
        }
        if (getNombre().equals("triángulo"))
        {
            nodo = new SVGPath();
            ((SVGPath) nodo).setContent("M5,0 L10,8 L0,8 Z");
            ((SVGPath) nodo).setStroke(color);
            ((SVGPath) nodo).setFill(color);
            shapeAWT = ShapeUtilities.createUpTriangle((float)delta);
            representacion = nodo;

        }
        if (getNombre().equals("cruz"))
        {
            nodo = new SVGPath();
            ((SVGPath) nodo).setContent("M2,0 L5,4 L8,0 L10,0 L10,2 L6,5 L10,8 L10,10 L8,10 L5,6 L2,10 L0,10 L0,8 L4,5 L0,2 L0,0 Z");
            ((SVGPath) nodo).setStroke(color);
            ((SVGPath) nodo).setFill(color);
            representacion = nodo;
            shapeAWT = ShapeUtilities.createDiagonalCross((float)delta, 1);
        }
        if (getNombre().equals("diamante"))
        {
            nodo = new SVGPath();
            ((SVGPath) nodo).setContent("M5,0 L10,9 L5,18 L0,9 Z");
            ((SVGPath) nodo).setStroke(color);
            ((SVGPath) nodo).setFill(color);
            representacion = nodo;
            shapeAWT = ShapeUtilities.createDiamond(10);

        }
        if (getNombre().equals("rectángulo hueco"))
        {
            Rectangle r = new Rectangle(10, 10);
            r.setFill(Color.TRANSPARENT);
            r.setStroke(color);
            nodo = r;
            representacion = nodo;
            shapeAWT = new Rectangle2D.Double(-delta, -delta, size, size);
        }
        if (getNombre().equals("círculo hueco"))
        {
            Circle c = new Circle(5);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(color);
            nodo = c;
            representacion = nodo;
            shapeAWT = new Ellipse2D.Double(-delta, -delta, size, size);
        }
        if (getNombre().equals("triángulo hueco"))
        {
            nodo = new SVGPath();
            ((SVGPath) nodo).setContent("M5,0 L10,8 L0,8 Z");
            ((SVGPath) nodo).setStroke(color);
            ((SVGPath) nodo).setFill(Color.TRANSPARENT);
            representacion = nodo;

            shapeAWT = ShapeUtilities.createUpTriangle((float)delta);

        }
        switch (getNombre())
        {
            case "diamante hueco":
                nodo = new SVGPath();
                ((SVGPath) nodo).setContent("M5,0 L10,9 L5,18 L0,9 Z");
                ((SVGPath) nodo).setStroke(color);
                ((SVGPath) nodo).setFill(Color.TRANSPARENT);
                representacion = nodo;
                shapeAWT = ShapeUtilities.createDiamond(10);
                break;
            case "nulo":
                nodo = null;
                representacion = new SVGPath();
                ((SVGPath) representacion).setContent("M0,5 L10,5");
                ((SVGPath) representacion).setStroke(color);
                ((SVGPath) representacion).setFill(Color.TRANSPARENT);
                break;
        }
    }

    public Node getNodo()
    {
        return nodo;
    }

    public Node getRepresentacion()
    {
        return representacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @return the shapeAWT
     */
    public Shape getShapeAWT()
    {
        return shapeAWT;
    }
}
