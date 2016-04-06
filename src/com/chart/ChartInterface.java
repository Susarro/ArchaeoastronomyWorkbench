/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import javafx.scene.layout.Pane;

/**
 *
 * @author MIGUEL_ANGEL
 */
public interface ChartInterface
{
    public void addAxis(String nombre);

    public void addSeries(String nombre, SimpleSeriesConfiguration cs);

    public void addSample(double x, double[] y);

    public void addSample(double x, double y, String nombreSerie);

    public Pane getChart();

    public void setAbcissaRange(double lower, double upper);
}
