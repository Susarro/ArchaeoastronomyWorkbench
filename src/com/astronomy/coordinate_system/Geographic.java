/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.coordinate_system;

import com.units.SexagesimalDegree;

/**
 * Geographic coordinate system
 *
 * @author MIGUEL_ANGEL
 */
public class Geographic
{

    /**
     * Geographic latitude
     */
    private SexagesimalDegree latitude;
    /**
     * Geographic longitude
     */
    private SexagesimalDegree longitude;
    /**
     * Elevation
     */
    private double elevation;

    /**
     *
     * @param latitude Geographic latitude
     * @param longitude Geographic longitude
     * @param elevation Elevation
     */
    public Geographic(SexagesimalDegree latitude, SexagesimalDegree longitude, double elevation)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    @Override
    public String toString()
    {
        return "(" + getLatitude().toString() + "," + getLongitude().toString() + "," + String.valueOf(getAltitud()) + ")";
    }

    /**
     * @return Geographic latitude
     */
    public SexagesimalDegree getLatitude()
    {
        return latitude;
    }

    /**
     * @param latitud Geographic latitude to set
     */
    public void setLatitude(SexagesimalDegree latitud)
    {
        this.latitude = latitud;
    }

    /**
     * @return Geographic longitude
     */
    public SexagesimalDegree getLongitude()
    {
        return longitude;
    }

    /**
     * @param longitud Geographic longitude to set
     */
    public void setLongitude(SexagesimalDegree longitud)
    {
        this.longitude = longitud;
    }

    /**
     * @return elevation
     */
    public double getAltitud()
    {
        return elevation;
    }

    /**
     * @param altitud elevation to set
     */
    public void setAltitud(double altitud)
    {
        this.elevation = altitud;
    }
}
