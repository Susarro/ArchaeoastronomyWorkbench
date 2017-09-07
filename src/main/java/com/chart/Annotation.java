/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;

/**
 * Notes in charts
 * 
 * @author MIGUEL_ANGEL
 */
class Annotation
{
/**
 * Node
 */
    private Node node;
    /**
     * x coordinate
     */
    private double x;
    /**
     * y coordinate
     */
    private double y;
    /**
     * Line chart
     */
    private LineChart chart;
    /**
     * Name
     */
    private String name;

    /**
     * 
     * @param node Node
     * @param x x coordinate
     * @param y y coordinate
     * @param chart Line chart
     */
    public Annotation(final Node node, final double x, final double y, LineChart chart)
    {
        this.node = node;
        this.x = x;
        this.y = y;
        this.chart = chart;
        name = "";
    }

    /**
     * @return node
     */
    public Node getNode()
    {
        return node;
    }

    /**
     * @param node node to set
     */
    public void setNode(Node node)
    {
        this.node = node;
    }

    /**
     * @return x coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * @param x x coordinate to set
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * @return y coordinate
     */
    public double getY()
    {
        return y;
    }

    /**
     * @param y y coordinate to set
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * @return Line chart
     */
    public LineChart getChart()
    {
        return chart;
    }

    /**
     * @param chart Line chart to set
     */
    public void setChart(LineChart chart)
    {
        this.chart = chart;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
