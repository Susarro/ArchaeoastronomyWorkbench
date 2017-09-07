/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.astronomy.JulianDay;

/**
 *
 * @author Miguel Ángel
 */
public class Interval
{
    private final JulianDay start;
    private final JulianDay end;
    private final double increment;
    
    /**
     * 
     * @param start Interval start
     * @param end Interval end
     * @param increment Increment
     */
    public Interval(JulianDay start, JulianDay end, double increment)
    {
        this.start=start;
        this.end=end;
        this.increment=increment;
    } 

    /**
     * @return the start
     */
    public JulianDay getStart()
    {
        return start;
    }

    /**
     * @return the end
     */
    public JulianDay getEnd()
    {
        return end;
    }

    /**
     * @return the increment
     */
    public double getIncrement()
    {
        return increment;
    }
}
