/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.units;

import com.ProcessException;

/**
 * Radian
 *
 * @author MIGUEL_ANGEL
 */
public class Radian
{

    /**
     * radian value as double
     */
    private final double value;

    /**
     *
     * @return radians as double
     */
    public double getValue()
    {
        return value;
    }

    /**
     *
     * @param value radian value as double
     */
    public Radian(double value)
    {
        this.value = value;
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Radian
     */
    static public Radian valueOf(SexagesimalDegree degrees)
    {
        return new Radian(degrees.getValue() * java.lang.Math.PI / 180.0);
    }

    /**
     *
     * @param hours Hour angle
     * @return Radian
     */
    static public Radian valueOf(HourAngle hours)
    {
        return new Radian(hours.getValue() * 15.0 * java.lang.Math.PI / 180.0);
    }

    /**
     *
     * @return Angle as sexagesimal degree
     */
    @Override
    public String toString()
    {
        return SexagesimalDegree.valueOf(this).toString();
    }

    /**
     *
     * @param str Radians value as string
     * @return Radian
     * @throws ProcessException Format error
     */
    public static Radian valueOf(String str) throws ProcessException
    {
        try
        {
            return new Radian(Double.valueOf(str.replace(",", ".")));
        }
        catch (NumberFormatException ex)
        {
            throw new ProcessException("Radian: Format error in " + str);
        }

    }

}
