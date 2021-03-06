/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.CalculusType;
import com.ProcessException;
import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * Solstice's time calculation
 *
 * @author MIGUEL_ANGEL
 */
public class Solstice
{

    /**
     * Summer solstice
     */
    private final JulianDay summer;
    /**
     * Winter solstice
     */
    private final JulianDay winter;
    
    

    /**
     *
     * @param year Year
     * @throws ProcessException Format error in string
     */
    public Solstice(int year) throws ProcessException
    {
       JulianDay start = new JulianDay(1, 1, year);
        JulianDay end = new JulianDay(1, 1, year + 1);

        double maximum = Double.MIN_VALUE;
        double minimum = Double.MAX_VALUE;

        double error = Double.MAX_VALUE;

        double p = start.julianDate + random() * (end.julianDate - start.julianDate);
        double p_temp = Double.MAX_VALUE;

        while (true)
        {
            do
            {
                double pplus = p + 1 / 24.0;
                double pminus = p - 1 / 24.0;
                double f = Sun.getApparentEquatorialPosition(new JulianDay(p), CalculusType.PRECISE).getDeclination().getSignedValue();
                if (f > 0)
                {
                    p = start.julianDate + random() * (end.julianDate - start.julianDate);
                    p_temp = Double.MAX_VALUE;
                    continue;
                }
                double fplus = Sun.getApparentEquatorialPosition(new JulianDay(pplus), CalculusType.PRECISE).getDeclination().getSignedValue();
                double fminus = Sun.getApparentEquatorialPosition(new JulianDay(pminus), CalculusType.PRECISE).getDeclination().getSignedValue();
                double df = (fplus - f) / (pplus - p);
                double dfminus = (f - fminus) / (p - pminus);
                double ddf = (df - dfminus) / (p - pminus);
                p_temp = p;
                p = p - df / ddf;
                
                JulianDay pointer = new JulianDay(p);
                JulianDay pointer_temp = new JulianDay(p_temp);
                error = abs(p - p_temp);
            }
            while (error > 1e-4);
            JulianDay pointer = new JulianDay(p);
            if (pointer.isBefore(start))
            {
                p += 365.25;
                p_temp = Double.MAX_VALUE;
            }
            else if (pointer.isAfter(end))
            {
                p -= 365.25;
                p_temp = Double.MAX_VALUE;
            }
            else
            {
                this.winter = new JulianDay(p);
                break;
            }
        }

        error = Double.MAX_VALUE;
        p = start.julianDate + random() * (end.julianDate - start.julianDate);
        p_temp = Double.MAX_VALUE;

        while (true)
        {
            do
            {
                double pplus = p + 1 / 24.0;
                double pminus = p - 1 / 24.0;
                double f = -Sun.getApparentEquatorialPosition(new JulianDay(p), CalculusType.PRECISE).getDeclination().getSignedValue();
                if (f > 0)
                {
                    p = start.julianDate + random() * (end.julianDate - start.julianDate);
                    p_temp = Double.MAX_VALUE;
                    continue;
                }
                double fplus = -Sun.getApparentEquatorialPosition(new JulianDay(pplus), CalculusType.PRECISE).getDeclination().getSignedValue();
                double fminus = -Sun.getApparentEquatorialPosition(new JulianDay(pminus), CalculusType.PRECISE).getDeclination().getSignedValue();
                double df = (fplus - f) / (pplus - p);
                double dfminus = (f - fminus) / (p - pminus);
                double ddf = (df - dfminus) / (p - pminus);
                p_temp = p;
                p = p - df / ddf;
                JulianDay pointer = new JulianDay(p);
                error = abs(p - p_temp);
            }
            while (error > 1e-4);

            JulianDay pointer = new JulianDay(p);
            if (pointer.isBefore(start))
            {
                p += 365.25;
                p_temp = Double.MAX_VALUE;
            }
            else if (pointer.isAfter(end))
            {
                p -= 365.25;
                p_temp = Double.MAX_VALUE;
            }
            else
            {
                this.summer = new JulianDay(p);
                break;
            }
        }
    }

    /**
     * @return the summer solstice
     */
    public JulianDay getSummerSolstice()
    {
        return summer;
    }

    /**
     * @return the winter solstice
     */
    public JulianDay getWinterSolstice()
    {
        return winter;
    }
}
