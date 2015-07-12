/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unidades;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class CoordenadasGeograficas
{
    private Grados latitud;
    private Grados longitud;
    private double altitud;//metros
    
    public CoordenadasGeograficas(Grados lat, Grados lon, double m)
    {
        this.latitud=lat;
        this.longitud=lon;
        this.altitud=m;
    }        

    /**
     * @return the latitud
     */
    public Grados getLatitud()
    {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Grados latitud)
    {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public Grados getLongitud()
    {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Grados longitud)
    {
        this.longitud = longitud;
    }

    /**
     * @return the altitud
     */
    public double getAltitud()
    {
        return altitud;
    }

    /**
     * @param altitud the altitud to set
     */
    public void setAltitud(double altitud)
    {
        this.altitud = altitud;
    }
}
