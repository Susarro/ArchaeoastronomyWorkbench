/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import org.jfree.data.xy.XYSeries;

/**
 * Series for JFreeChart
 *
 * @author Miguel Ángel
 */
public class Series extends XYSeries
{

    /**
     * Format style
     */
    private String style;
    /**
     * Axis index
     */
    private final int axisIndex;
    /**
     * Series index
     */
    private final int seriesIndex;

    /**
     *
     * @param key Name
     * @param style Format style
     * @param axisIndex Axis index
     * @param seriesIndex Series index
     */
    public Series(Comparable key, String style, int axisIndex, int seriesIndex)
    {
        super(key);
        this.style = style;
        this.axisIndex = axisIndex;
        this.seriesIndex = seriesIndex;
    }

    /**
     * @return style
     */
    public String getStyle()
    {
        return style;
    }

    /**
     * @param style to set
     */
    public void setStyle(String style)
    {
        this.style = style;
    }

    /**
     * @return axis index
     */
    public int getAxisIndex()
    {
        return axisIndex;
    }

    /**
     * @return series index
     */
    public int getSeriesIndex()
    {
        return seriesIndex;
    }

}
