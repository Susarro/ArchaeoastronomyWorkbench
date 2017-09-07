/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.astronomy;

import com.astronomy.coordinate_system.Equatorial;

/**
 *Some information about planet's position and its apparent magnitude
 * @author MIGUEL_ANGEL
 */
public class InfoPlanet
{
    /**
     * Planet position in the Ecuatorial Coordinate System
     */
    Equatorial position;
    /**
     * Apparent magnitude
     */
    double apparentMagnitude;
    
    /**
     * 
     * @param coordinates Planet position in the Ecuatorial Coordinate System
     * @param am Apparent magnitude
     */
    public InfoPlanet(Equatorial coordinates, double am)
    {
        this.position=coordinates;
        this.apparentMagnitude=am;
    }        
}
