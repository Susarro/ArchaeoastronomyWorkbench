/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.astronomy.coordinate_system.Equatorial;

/**
 * Some information about Moon's position and its fraction illuminated
 * 
 * @author MIGUEL_ANGEL
 */
public class InfoMoon
{

    /**
     * Moon position in the Ecuatorial Coordinate System
     */
    public Equatorial position;
    /**
     * Moon fraction illuminated
     */
    public double fraction;

    /**
     * 
     * @param coordinates Moon position in the Ecuatorial Coordinate System
     * @param fraction Moon fraction illuminated
     */
    public InfoMoon(Equatorial coordinates, double fraction)
    {
        this.position = coordinates;
        this.fraction = fraction;
    }
}
