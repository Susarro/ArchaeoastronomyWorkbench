/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.sistema;

import com.unidades.Grados;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Geodesica
{
    private Grados latitud;
    private Grados longitud;
    private double altitud;
    
    public Geodesica(Grados latitud, Grados longitud, double altitud)
    {
        this.latitud=latitud;
        this.longitud=longitud;
        this.altitud=altitud;
    }
    
    @Override
    public String toString()
    {
        return "("+getLatitud().toString()+","+getLongitud().toString()+","+String.valueOf(getAltitud())+")";
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
