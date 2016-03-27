/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.units;

import com.ProcessException;

/**
 * Hour angle
 * 
 * @author MIGUEL_ANGEL
 */
public class HourAngle
{
    /**
     * Hour angle as double
     */
    private double value;

    /**
     * 
     * @return Hour angle as double 
     */
    public double getValue()
    {
        return value;
    }

    /**
     * 
     * @return Hour angle as signed value 
     */
    public double getSignedValue()
    {
        if (value > 12)
        {
            return -(24 - value);
        }
        else
        {
            return value;
        }
    }

    /**
     * 
     * @param value Hour angle as double
     * @return Reduced hour angle to a value greater tha zero and less than 24 
     */
    public double Reduction(double value)
    {
        if (value < 0)
        {
            return value + java.lang.Math.ceil(-value / 24.0) * 24;
        }
        else if (value > 0 && value > 24)
        {
            return value - java.lang.Math.floor(value / 24.0) * 24;
        }
        else
        {
            return value;
        }
    }

    /**
     * 
     * @return Reduced hour angle
     */
    public HourAngle Reduction()
    {
        return new HourAngle(this.value);
    }

    /**
     * Constructor
     * @param value Hour angle as double
     */
    public HourAngle(double value)
    {
        this.value = Reduction(value);
    }

    /**
     * Constructor
     * @param hours Hours 
     * @param minutes Minutes
     * @param seconds Seconds
     */
    public HourAngle(double hours, double minutes, double seconds)
    {
        this.value = hours + minutes / 60.0 + seconds / 3600.0;
    }

    /**
     * 
     * @param degrees Sexagesimal degree
     * @return Hour Angle from sexagesimal degrees
     */
    public static HourAngle valueOf(SexagesimalDegree degrees)
    {
        return new HourAngle(degrees.getValue() / 15.0);
    }

    /**
     * 
     * @param radians Radians
     * @return Hour angle from radians
     */
    public static HourAngle valueOf(Radian radians)
    {
        return new HourAngle(radians.getValue() * 180.0 / (java.lang.Math.PI * 15.0));
    }

    /**
     * 
     * @param hours Hour angle
     * @return Current value plus hours
     */
    public HourAngle plus(HourAngle hours)
    {
        return new HourAngle(Reduction(this.value + hours.value));
    }

    /**
     * 
     * @param hours Hour angle
     * @return Current value minus hours
     */
    public HourAngle minus(HourAngle hours)
    {
        return new HourAngle(Reduction(this.value - hours.value));
    }

    /**
     * 
     * @param factor Hour angle
     * @return Current value multiplied by factor
     */
    public HourAngle by(double factor)
    {
        return new HourAngle(Reduction(factor * this.value));
    }

    /**
     * 
     * @param hours Hour angle
     * @return Cosine of hours
     */
    public static double cos(HourAngle hours)
    {
        return java.lang.Math.cos(Radian.valueOf(hours).getValue());
    }

    /**
     * 
     * @param hours Hour angle
     * @return Sine of hours
     */
    public static double sin(HourAngle hours)
    {
        return java.lang.Math.sin(Radian.valueOf(hours).getValue());
    }

    /**
     * 
     * @param hours Hour angle
     * @return Tangent of hours
     */
    public static double tan(HourAngle hours)
    {
        return java.lang.Math.tan(Radian.valueOf(hours).getValue());
    }

    /**
     * 
     * @param value Cosine value
     * @return Arccosine of value in hours
     */
    public static HourAngle acos(double value)
    {
        return HourAngle.valueOf(new Radian(java.lang.Math.acos(value)));
    }

    /**
     * 
     * @param value Sine value
     * @return Arcsine of value in hours
     */
    public static HourAngle asin(double value)
    {
        return HourAngle.valueOf(new Radian(java.lang.Math.asin(value)));
    }

    /**
     * 
     * @param y Ordinate
     * @param x Abcissa
     * @return Arctangent of y/x in hours
     */
    public static HourAngle atan2(double y, double x)
    {
        return HourAngle.valueOf(new Radian(java.lang.Math.atan2(y, x)));
    }

    /**
     * 
     * @return Hours as integer value
     */
    public int getHours()
    {
        double v = java.lang.Math.abs(value);
        int hours = (int) v;
        return hours;
    }

    /**
     * 
     * @return Minutes as integer value
     */
    public int getMinutes()
    {
        double v = java.lang.Math.abs(value);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        return minutos;
    }

    /**
     * 
     * @return Seconds as double value
     */
    public double getSeconds()
    {
        double v = java.lang.Math.abs(value);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        int segundos = (int) ((v - horas - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + ((v - horas - minutos / 60.0 - segundos / 3600.0) * 3600000));
        return segundos + milisegundos / 1000.0;
    }

    /**
     * 
     * @return Hour angle formated to string HH:mm:ss.SSS
     */
    @Override
    public String toString()
    {
        double v = java.lang.Math.abs(value);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        int segundos = (int) ((v - horas - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + ((v - horas - minutos / 60.0 - segundos / 3600.0) * 3600000));
        String h;

        return String.format("%02d:%02d:%.3f", horas, minutos, (double) segundos + milisegundos / 1000.0).replace(",", ".");

    }

    /**
     * 
     * @param string Hour angle formated to string HH:mm:ss.SSS
     * @return Hour angle
     * @throws ProcessException Format error in string
     */
    public static HourAngle valueOf(String string) throws ProcessException
    {
        HourAngle horas = new HourAngle(0);
        String str = string.replace(",", ".");
        double h;
        if (!str.contains(":"))
        {
            try
            {
                horas.value = Double.valueOf(str);
                return horas;
            }
            catch (NumberFormatException ex)
            {
                throw new ProcessException("HourAngle: Format error in " + string);
            }
        }
        else
        {
            try
            {
                String[] t = str.split(":");
                horas.value += Integer.valueOf(t[0]);
                if (t.length == 2)
                {
                    horas.value += Integer.valueOf(t[1]) / 60.0;
                }
                else if (t.length == 3)
                {
                    horas.value += Integer.valueOf(t[1]) / 60.0 + Double.valueOf(t[2]) / 3600.0;
                }
                else
                {
                    throw new ProcessException("Error formato horas de " + string);
                }
            }
            catch (NumberFormatException ex)
            {
                throw new ProcessException("Error formato horas de " + string);
            }
        }
        return horas;
    }

}
