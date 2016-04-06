/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import javafx.scene.paint.Color;

/**
 * Series configuration
 * @author MIGUEL_ANGEL
 */
public class SimpleSeriesConfiguration
{
/**
 * Color
 */
    private Color color;
    /**
     * series name
     */
    private String name;
    /**
    * Width
    */
    private int width;
    /**
     * Shape name
     */
    private String shapeName;

    /**
     * 
     * @param name Series name
     * @param color Color
     * @param width Line width
     * @param shapeName Shape name
     */
    public SimpleSeriesConfiguration(String name, Color color, int width, String shapeName)
    {
        this.name = name;
        this.color = color;
        this.width = width;
        this.shapeName = shapeName;

    }

    /**
     * 
     * @param name Series name 
     */
    public SimpleSeriesConfiguration(String name)
    {
        this.name = name;
        color = null;
        width = 3;
        shapeName = "null";

    }

    /**
     * @return color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @param color color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return line width
     */
    public int getLineWidth()
    {
        return width;
    }

    /**
     * @param width line width to set
     */
    public void setLineWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return shape name
     */
    public String getShapeName()
    {
        return shapeName;
    }

    /**
     * @param shapeName shape name to set
     */
    public void setShapeName(String shapeName)
    {
        this.shapeName = shapeName;
    }

    /**
     * @return series name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @return series name
     */
    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * @param name series name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
