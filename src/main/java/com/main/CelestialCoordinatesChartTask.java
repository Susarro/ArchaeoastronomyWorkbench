/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.CalculusType;
import com.Global;
import com.ProcessException;
import com.TemporalTask;
import com.astronomy.Catalogue;
import com.astronomy.JulianDay;
import com.astronomy.Moon;
import com.astronomy.Planet;
import com.astronomy.Sun;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.Horizontal;
import com.chart.AxisChart;
import com.chart.JulianChart;
import com.chart.SimpleSeriesConfiguration;
import static com.main.Main.latitude;
import static com.main.Main.longitude;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Ángel
 */
class CelestialCoordinatesChartTask extends TemporalTask
{

    String[] variables;
    List<AxisChart> axes = new ArrayList<>();
    JulianChart chart;

    /**
     * 
     * @param variables Equatorial and horizontal coordinates of celestial bodies
     * @param start Interval start
     * @param end Interval end
     * @param increment Increment 
     */
    public CelestialCoordinatesChartTask(String[] variables, JulianDay start, JulianDay end, double increment)
    {
        super("", start, end, increment);
        this.variables = variables;
        for (String var : variables)
        {
            String[] v = var.split(",");
            boolean found = false;
            AxisChart axis = null;
            for (AxisChart c : axes)
            {
                if (c.getName().equals(v[1]))
                {
                    found = true;
                    axis = c;
                }
            }
            if (!found)
            {
                axes.add(axis = new AxisChart(v[1]));
            }
            axis.configSerieList.add(new SimpleSeriesConfiguration(var));
        }
        chart = new JulianChart("", axes, "JD");

    }

    @Override
    public void taskEnd()
    {

    }

    @Override
    public void cycle()
    {
        Equatorial e;
        Horizontal h;

        for (String var : variables)
        {
            try
            {
                String[] v = var.split(",");
                double valor = 0;
                if (v[0].equals("Sun"))
                {
                    switch (v[1])
                    {
                        case "DE":
                            e = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            valor = e.getDeclination().getSignedValue();
                            break;
                        case "AR":
                            e = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            valor = e.getRightAscension().getSignedValue();
                            break;
                        case "A":
                            e = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAzimuth().getValue();
                            break;
                        case "h":
                            e = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAltitude().getSignedValue();
                            break;
                        default:
                    }
                }
                else if (v[0].equals("Moon"))
                {
                    switch (v[1])
                    {
                        case "DE":
                            e = Moon.getApparentPosition(getCurrent());
                            valor = e.getDeclination().getSignedValue();
                            break;
                        case "AR":
                            e = Moon.getApparentPosition(getCurrent());
                            valor = e.getRightAscension().getSignedValue();
                            break;
                        case "A":
                            e = Moon.getApparentPosition(getCurrent());
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAzimuth().getValue();
                            break;
                        case "h":
                            e = Moon.getApparentPosition(getCurrent());
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAltitude().getSignedValue();
                            break;
                        default:
                    }
                }
                else if ("Mercury,Venus,Mars,Jupiter,Saturn".contains(v[0]))
                {
                    switch (v[1])
                    {
                        case "DE":
                            e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                            valor = e.getDeclination().getSignedValue();
                            break;
                        case "AR":
                            e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                            valor = e.getRightAscension().getSignedValue();
                            break;
                        case "A":
                            e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAzimuth().getValue();
                            break;
                        case "h":
                            e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAltitude().getSignedValue();
                            break;
                        default:
                    }
                }
                else
                {
                    switch (v[1])
                    {
                        case "DE":
                            e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            valor = e.getDeclination().getSignedValue();
                            break;
                        case "AR":
                            e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            valor = e.getRightAscension().getSignedValue();
                            break;
                        case "A":
                            e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAzimuth().getValue();
                            break;
                        case "h":
                            e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            h = e.toHorizontal(latitude, longitude, getCurrent());
                            valor = h.getAltitude().getSignedValue();
                            break;
                        default:
                    }
                }
                chart.addSample(getCurrent().getValue(), valor, var);
            }
            catch (ProcessException ex)
            {
                Global.info.log(ex);
            }
        }
    }
}
