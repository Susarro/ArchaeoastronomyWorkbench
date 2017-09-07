/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.astronomy;

import com.units.HourAngle;

/**
 * Universal time
 * 
 * @author MIGUEL_ANGEL
 */
public class UniversalTime extends HourAngle
{

    /**
     * Constructor
     * @param hours Hours angle 
     */
    public UniversalTime(HourAngle hours)
    {
        super(hours.getValue());
    }
    
    /**
     * 
     * @param value Hours as double value
     */
    public UniversalTime(double value)
    {
        super(value);
    }
    
    
}
