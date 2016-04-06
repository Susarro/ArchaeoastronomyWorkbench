/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.CalculusType;
import com.Global;
import com.HorizontalTime;
import com.PlanetEnum;
import com.ProcessException;
import com.TemporalTask;
import com.astronomy.JulianDay;
import com.astronomy.Planet;
import com.astronomy.Star;
import com.astronomy.coordinate_system.ApparentHorizontal;
import com.astronomy.coordinate_system.Equatorial;
import com.chart.AxisChart;
import com.chart.JulianChart;
import com.chart.SimpleSeriesConfiguration;
import static com.main.Main.latitude;
import static com.main.Main.longitude;
import static com.main.Tools.sign;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * It calculates the star and planet coincidence in altitude 
 * 
 * @author Miguel Ángel
 */
class CoincidenceInAltitude extends TemporalTask
{
    /**
     * Rising or setting
     */
    HorizontalTime time;
    /**
     * Chart axes
     */
    List<AxisChart> axes;
    /**
     * Current chart axis
     */
    AxisChart axis;
    /**
     * Julian daychart
     */
    JulianChart chart;
    
    Double h_temp = null;
    Double temp = null;
    Double temp2 = null;

    int state = 0;
    boolean rising = false;

    /**
     * Planet
     */
    Planet planet;
    /**
     * Star
     */
    Star star;

    /**
     * Chart which shows the coincidence in alttude between a star and a planet
     * @param name Chart name
     * @param star Star
     * @param planet Planet
     * @param start Interval start 
     * @param end Interval end
     * @param time Rise or set
     * @throws ProcessException Format error
     */
    public CoincidenceInAltitude(String name, Star star, Planet planet, JulianDay start, JulianDay end, HorizontalTime time) throws ProcessException
    {
        super(name, start, end, 0.005);
        this.time = time;
        this.star = star;
        this.planet = planet;
        axes = new ArrayList<>();

        axes.add(axis = new AxisChart("Declination"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("DE " + planet.toString(), Color.ORANGE, 4, "rectangle"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("DE " + star.toString(), Color.ORANGE, 2, "crux"));
        axes.add(axis = new AxisChart("Azimuth"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("A " + planet.toString(), Color.RED, 4, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("A " + star.toString(), Color.RED, 2, "null"));
        axes.add(axis = new AxisChart("Altitude"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("h " + planet.toString(), Color.WHITE, 4, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("h " + star.toString(), Color.WHITE, 2, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("planet-star", Color.CYAN, 2, "null"));
        chart = new JulianChart("", axes, "DJ");
        if (time==HorizontalTime.RISE)
        {
            rising = true;
        }
    }

    @Override
    public void cycle()
    {
        try
        {
            Equatorial eS = planet.getApparentPosition(getCurrent());
            Equatorial eT = star.getApparentPosition(getCurrent(), CalculusType.PRECISE);
            ApparentHorizontal hS = eS.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.SATURN, getCurrent());
            ApparentHorizontal hT = eT.toHorizontal(latitude, longitude, getCurrent()).toApparent();

            double difH = hS.getAltitude().getSignedValue() - hT.getAltitude().getSignedValue();

            if (temp != null)
            {
                if (state == 0)
                {
                    if ((!rising & hS.getAltitude().getSignedValue() < temp)
                            || (rising & hS.getAltitude().getSignedValue() > temp))
                    {
                        if ((!rising && hS.getAltitude().getSignedValue() <= 0 && temp > 0)
                                || (rising && hS.getAltitude().getSignedValue() >= 0 && temp < 0))
                        {
                            if (temp2 != null)
                            {
                                if ((!rising && difH >= 0 && temp2 < 0)
                                        || (rising && difH <= 0 && temp2 > 0))
                                {
                                    if (sign(hS.getAzimuth().getSignedValue()) != sign(hT.getAzimuth().getSignedValue()))
                                    {
                                        switch (planet.toString())
                                        {
                                            case "Saturn":
                                                addToCurrent(0.8);
                                                break;
                                            case "Mars":
                                                addToCurrent(0.8);
                                                break;
                                            case "Jupiter":
                                                addToCurrent(0.8);
                                                break;
                                            case "Venus":
                                                addToCurrent(0.8);
                                                break;
                                            case "Mercury":
                                                addToCurrent(0.8);
                                                break;
                                        }
                                        temp = null;
                                        temp2 = null;
                                        return;
                                    }
                                    else
                                    {
                                        chart.addSample(getCurrent().getValue(), eS.getDeclination().getSignedValue(), "DE " + planet.toString());
                                        chart.addSample(getCurrent().getValue(), eT.getDeclination().getSignedValue(), "DE " + star.toString());
                                        chart.addSample(getCurrent().getValue(), hS.getAzimuth().getSignedValue(), "A " + planet.toString());
                                        chart.addSample(getCurrent().getValue(), hT.getAzimuth().getSignedValue(), "A " + star.toString());
                                        chart.addSample(getCurrent().getValue(), hS.getAltitude().getSignedValue(), "h " + planet.toString());
                                        chart.addSample(getCurrent().getValue(), hT.getAltitude().getSignedValue(), "h " + star.toString());
                                        chart.addSample(getCurrent().getValue(), difH, "planet-star");
                                        switch (planet.toString())
                                        {
                                            case "Saturn":
                                                addToCurrent(365 * 29);
                                                break;
                                            case "Mars":
                                                addToCurrent(0.8);
                                                break;
                                            case "Jupiter":
                                                addToCurrent(365 * 10);
                                                break;
                                            case "Venus":
                                                addToCurrent(0.8);
                                                break;
                                            case "Mercury":
                                                addToCurrent(0.8);
                                                break;
                                        }
                                        temp = null;
                                        temp2 = null;
                                        return;
                                    }

                                }
                                else
                                {
                                    addToCurrent(0.95);
                                    temp = null;
                                    temp2 = difH;
                                    return;
                                }
                            }
                            temp2 = difH;
                        }
                    }
                }
                else if (state == 1)
                {
                    chart.addSample(getCurrent().getValue(), eS.getDeclination().getSignedValue(), "DE Saturn");
                    chart.addSample(getCurrent().getValue(), eT.getDeclination().getSignedValue(), "DE Taurus");
                    chart.addSample(getCurrent().getValue(), hS.getAzimuth().getSignedValue(), "A Saturn");
                    chart.addSample(getCurrent().getValue(), hT.getAzimuth().getSignedValue(), "A Taurus");
                    chart.addSample(getCurrent().getValue(), hS.getAltitude().getSignedValue(), "h Saturn");
                    chart.addSample(getCurrent().getValue(), hT.getAltitude().getSignedValue(), "h Taurus");
                    chart.addSample(getCurrent().getValue(), difH, "difference");

                }
            }
            temp = hS.getAltitude().getSignedValue();

        }
        catch (ProcessException ex)
        {
            Global.info.log(ex);
        }
    }

    /**
     * 
     * @return chart
     */
    public Node getChart()
    {
        return chart.getChart();
    }

    @Override
    public void taskEnd()
    {
    }

}
