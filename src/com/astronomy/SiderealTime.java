/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.units.HourAngle;

/**
 * Sidereal time
 *
 * @author MIGUEL_ANGEL
 */
public class SiderealTime extends HourAngle
{

    /**
     *
     * @param hours Hour angle to set
     */
    public SiderealTime(HourAngle hours)
    {
        super(hours.getValue());
    }

    /**
     *
     * @param value Hour angle as double
     */
    public SiderealTime(double value)
    {
        super(value);
    }

}
