/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.units;

import com.ProcessException;

/**
 * Sexagesimal degree
 *
 * @author MIGUEL_ANGEL
 */
public class SexagesimalDegree
{

    /**
     * Sexagesimal degrees as double
     */
    private double value;

    /**
     *
     * @param obj Object
     * @return True if this equals object
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof SexagesimalDegree))
        {
            return false;
        }
        return this.getValue() == ((SexagesimalDegree) obj).getValue();
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    /**
     *
     * @return Sexagesimal degrees as double
     */
    public double getValue()
    {
        return value;
    }

    /**
     *
     * @param v Sexagesimal degrees as double to set
     */
    public void setValue(double v)
    {
        value = v;
    }

    /**
     *
     * @return Sexagesimal degrees as signed value
     */
    public double getSignedValue()
    {
        if (value > 180)
        {
            return -(360 - value);
        }
        else
        {
            return value;
        }
    }

    /**
     *
     * @param value Sexagesimal value as double
     * @return Reduced sexagesimal degrees to a value greater than zero and less
     * than 360
     */
    static public double Reduction(double value)
    {
        if (value < 0)
        {
            return value + java.lang.Math.ceil(-value / 360.0) * 360;
        }
        else if (value > 0 && value > 360)
        {
            return value - java.lang.Math.floor(value / 360.0) * 360;
        }
        else
        {
            return value;
        }
    }

    /**
     *
     * @return Reduced sexagesimal degrees
     */
    public SexagesimalDegree Reduction()
    {
        return new SexagesimalDegree(this.value);
    }

    /**
     *
     * @param signed If true final value greater than -180 and less than 180
     * @return Reduced sexagesimal degrees
     */
    public SexagesimalDegree Reduction(boolean signed)
    {
        if (signed)
        {
            SexagesimalDegree g = new SexagesimalDegree(this.value);
            double v = g.getSignedValue();
            if (v > 180)
            {
                v = -v + 180;
            }
            g.setValue(v);
            return g;
        }
        else
        {
            return new SexagesimalDegree(this.value);
        }
    }

    /**
     *
     * @param value Sexagesimal value as double
     */
    public SexagesimalDegree(double value)
    {
        this.value = SexagesimalDegree.Reduction(value);
    }

    /**
     *
     * @param hours Hour angle
     * @return Sexagesimal degrees
     */
    public static SexagesimalDegree valueOf(HourAngle hours)
    {
        return new SexagesimalDegree(hours.getValue() * 15.0);
    }

    /**
     *
     * @param radians Radians
     * @return Sexagesimal degrees
     */
    public static SexagesimalDegree valueOf(Radian radians)
    {
        return new SexagesimalDegree(radians.getValue() * 180.0 / java.lang.Math.PI);
    }

    /**
     *
     * @param string Sexagesimal degrees fomated as ###º##'##.###''L where is a
     * digit and L is N (North), S (South), E (East) or W (West)
     * @return Sexagesimal degrees
     * @throws ProcessException Format error
     */
    public static SexagesimalDegree valueOf(String string) throws ProcessException
    {
        SexagesimalDegree grados = new SexagesimalDegree(0);
        String str = string.replace("N", "").replace("S", "").replace(",", ".").replace("-", "");
        if (str.contains("º"))
        {
            String[] g = str.split("º");
            try
            {
                grados.value = Double.valueOf(g[0]);
                if (g.length == 2)
                {
                    String[] m = g[1].split("'");
                    grados.value += Integer.valueOf(m[0]) / 60.0;
                    if (m.length >= 2)
                    {
                        String s = m[1].replace("''", "");
                        grados.value += Double.valueOf(s) / 3600.0;
                        if (string.contains("S") || string.contains("-") || string.contains("O") || string.contains("W"))
                        {
                            grados.value = -1 * grados.value;
                        }

                    }

                }
                return grados;
            }
            catch (NumberFormatException ex)
            {
                throw new ProcessException("SexagesimalDegree: Format error (###º##'##.###''L where is a"
                        + " digit and L is N (North), S (South), E (East) or W (West)) in " + string);
            }
        }
        else
        {
            try
            {
                grados.value = Double.valueOf(str);
                if (string.contains("-"))
                {
                    grados.value = -1 * grados.value;
                }
                return grados;
            }
            catch (NumberFormatException ex)
            {
                throw new ProcessException("SexagesimalDegree: Format error (###º##'##.###''L where is a"
                        + " digit and L is N (North), S (South), E (East) or W (West)) in " + string);
            }
        }
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Current value plus degrees
     */
    public SexagesimalDegree plus(SexagesimalDegree degrees)
    {
        return new SexagesimalDegree(SexagesimalDegree.Reduction(this.value + degrees.value));
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Current value minus degrees
     */
    public SexagesimalDegree minus(SexagesimalDegree degrees)
    {
        return new SexagesimalDegree(SexagesimalDegree.Reduction(this.value - degrees.value));
    }

    /**
     *
     * @param degrees Current value plus degrees
     * @return Negative value of degrees
     */
    public static SexagesimalDegree negative(SexagesimalDegree degrees)
    {
        return new SexagesimalDegree(SexagesimalDegree.Reduction(-degrees.value));
    }

    /**
     *
     * @param f Factor
     * @return Current value multiplied by factor
     */
    public SexagesimalDegree by(double f)
    {
        return new SexagesimalDegree(SexagesimalDegree.Reduction(f * this.value));
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Cosine of degrees
     */
    public static double cos(SexagesimalDegree degrees)
    {
        return java.lang.Math.cos(Radian.valueOf(degrees).getValue());
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Sine of degrees
     */
    public static double sin(SexagesimalDegree degrees)
    {
        return java.lang.Math.sin(Radian.valueOf(degrees).getValue());
    }

    /**
     *
     * @param degrees Sexagesimal degrees
     * @return Tangent of degrees
     */
    public static double tan(SexagesimalDegree degrees)
    {
        return java.lang.Math.tan(Radian.valueOf(degrees).getValue());
    }

    /**
     *
     * @param value Value
     * @return Arccosine of value in sexagesimal degrees
     */
    public static SexagesimalDegree acos(double value)
    {
        return SexagesimalDegree.valueOf(new Radian(java.lang.Math.acos(value)));
    }

    /**
     *
     * @param value Value
     * @return Arcsine of value in sexagesimal degrees
     */
    public static SexagesimalDegree asin(double value)
    {
        return SexagesimalDegree.valueOf(new Radian(java.lang.Math.asin(value)));
    }

    /**
     *
     * @param y Ordinate
     * @param x Abcissa
     * @return Arctangent of y/x in sexagesimal degrees
     */
    public static SexagesimalDegree atan2(double y, double x)
    {
        return SexagesimalDegree.valueOf(new Radian(java.lang.Math.atan2(y, x)));
    }

    /**
     *
     * @return Degrees as integer
     */
    public int getDegrees()
    {
        double v = java.lang.Math.abs(value);
        return (int) v;
    }

    /**
     *
     * @return Minutes as integer
     */
    public int getMinutes()
    {
        double v = java.lang.Math.abs(value);
        int grados = (int) v;
        return (int) ((v - grados) * 60);
    }

    /**
     *
     * @return Seconds as integer
     */
    public int getSeconds()
    {
        double v = java.lang.Math.abs(value);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        return (int) ((v - grados - minutos / 60.0) * 3600);
    }

    /**
     *
     * @return Milliseconds as integer
     */
    public int getMilliseconds()
    {
        double v = java.lang.Math.abs(value);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        int segundos = (int) ((v - grados - minutos / 60.0) * 3600);
        return (int) (0.5 + (v - grados - minutos / 60.0 - segundos / 3600.0) * 3600000);
    }

    /**
     *
     * @return Degrees formatted as string ###º##'##.###'' where # us a digit
     */
    @Override
    public String toString()
    {
        double v = java.lang.Math.abs(value);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        int segundos = (int) ((v - grados - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + (v - grados - minutos / 60.0 - segundos / 3600.0) * 3600000);
        String h;
        if (value > 0)
        {
            h = "";
        }
        else
        {
            h = "-";
        }
        return String.format("%s%dº%d'%.3f''", h, grados, minutos, (double) segundos + milisegundos / 1000.0).replace(",", ".");

    }
}
