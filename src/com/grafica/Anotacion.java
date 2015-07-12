/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;

/**
 *
 * @author MIGUEL_ANGEL
 */
class Anotacion
{

    private Node node;
    private double x;
    private double y;
    private LineChart chart;
    private String nombre;

    public Anotacion(final Node node, final double x, final double y, LineChart chart)
    {
        this.node = node;
        this.x = x;
        this.y = y;
        this.chart = chart;
        nombre = "";
    }

    /**
     * @return the node
     */
    public Node getNode()
    {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(Node node)
    {
        this.node = node;
    }

    /**
     * @return the x
     */
    public double getX()
    {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY()
    {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * @return the chart
     */
    public LineChart getChart()
    {
        return chart;
    }

    /**
     * @param chart the chart to set
     */
    public void setChart(LineChart chart)
    {
        this.chart = chart;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
}
