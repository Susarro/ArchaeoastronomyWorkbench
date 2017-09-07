/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.ProcessException;
import com.astronomy.coordinate_system.Heliocientric;
import com.astronomy.coordinate_system.Ecliptic;
import com.astronomy.coordinate_system.Equatorial;
import static com.PlanetEnum.EARTH;
import com.CalculusType;
import com.units.SexagesimalDegree;
import com.units.HourAngle;
import static java.lang.Math.pow;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;

/**
 * Sun
 * @author MIGUEL_ANGEL
 */
public class Sun
{

    /**
     * 
     * @param julianDay Julian day
     * @return Geometric mean longitude of the Sun
     */
    public static SexagesimalDegree getGeometricMeanLongitude(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(280.46645 + 36000.76983 * T + 0.0003032 * pow(T, 2));
    }

    /**
     * 
     * @param julianDay Julian day
     * @return Apparent longitude of the Sun
     */
    public static SexagesimalDegree getApparentLongitude(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        SexagesimalDegree omega = new SexagesimalDegree(125.04 - 1934.136 * T);
        SexagesimalDegree temp = new SexagesimalDegree(0.00569 + 0.00478 * sine(omega));
        return getGeometricMeanLongitude(julianDay).minus(temp);
    }

    /**
     * 
     * @param julianDay Julian day
     * @return Sun's mean anomaly
     */
    public static SexagesimalDegree getMeanAnomaly(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(357.52910 + 35999.05030 * T + 0.000155 * pow(T, 2) - 0.00000048 * pow(T, 3));
    }

    /**
     * 
     * @param julianDay Julian day
     * @return Sun's true longitude
     */
    public static SexagesimalDegree getTrueLongitude(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        SexagesimalDegree M = getMeanAnomaly(julianDay);
        SexagesimalDegree center = new SexagesimalDegree((1.914600 - 0.004817 * T - 0.000014 * pow(T, 2)) * sine(M)
                + (0.019993 - 0.000101 * T) * sine(M.by(2)) + 0.000290 * sine(M.by(3)));
        return Sun.getGeometricMeanLongitude(julianDay).plus(center);
    }

    
    /**
     * 
     * @param julianDay Julian day
     * @return True distance to Earth
     * @throws ProcessException Format error 
     */
    public static double getTrueDistanceToEarth(JulianDay julianDay) throws ProcessException
    {
        return Heliocientric.getRadius(EARTH, julianDay);            
    }  

    /**
     * 
     * @param julianDay Julian day
     * @return Apparent position of Sun in the Ecliptical coordinate system
     * @throws ProcessException Format error 
     */
    public static Ecliptic getApparentEclipticalPosition(JulianDay julianDay) throws ProcessException
    {
        double T = julianDay.getCenturiesSince2000();
        SexagesimalDegree L = Heliocientric.getLongitude(EARTH, julianDay);
        SexagesimalDegree B = Heliocientric.getLatitude(EARTH, julianDay);
        double R = Heliocientric.getRadius(EARTH, julianDay);
        SexagesimalDegree SunLongitude = L.plus(new SexagesimalDegree(180));
        SexagesimalDegree SunLatitude = new SexagesimalDegree(-B.getValue());
        //Correction to de FK5 system
        SexagesimalDegree corr = SunLongitude.minus(new SexagesimalDegree(1.397 * T + 0.00031 * pow(T, 2)));
        SunLongitude = SunLongitude.plus(new SexagesimalDegree(-0.09033 / 3600));
        SunLatitude = SunLatitude.plus(new SexagesimalDegree(0.03916 * (cosine(corr) - sine(corr)) / 3600));
        SunLongitude = SunLongitude.plus(julianDay.getNutationInLongitude()); 
        SunLongitude = SunLongitude.plus(new SexagesimalDegree(-20.4898 / (R * 3600)));
        return new Ecliptic(SunLongitude, SunLatitude, R);

    }

    /**
     * 
     * @param julianDay Julian day
     * @param option APPROXIMATE or PRECISE 
     * @return Apparent position
     * @throws ProcessException Format error
     */
    public static Equatorial getApparentEquatorialPosition(JulianDay julianDay, CalculusType option) throws ProcessException
    {
        double T = julianDay.getCenturiesSince2000();

        if (option == CalculusType.APPROXIMATE)
        {
            SexagesimalDegree omega = new SexagesimalDegree(125.04 - 1934.136 * T);
            SexagesimalDegree temp = new SexagesimalDegree(0.00569 + 0.00478 * sine(omega));
            SexagesimalDegree O = getTrueLongitude(julianDay);
            O = O.minus(temp);
            SexagesimalDegree e = julianDay.getTrueObliquityEcliptic(CalculusType.APPROXIMATE).plus(new SexagesimalDegree(0.00256 * cosine(omega)));
            HourAngle ascensionRecta = HourAngle.atan2(cosine(e) * sine(O), cosine(O));
            SexagesimalDegree declinacion = SexagesimalDegree.asin(sine(e) * sine(O));
            return new Equatorial(declinacion, ascensionRecta);
        }
        else
        {
            return getApparentEclipticalPosition(julianDay).toEquatorial(julianDay.getTrueObliquityEcliptic(CalculusType.PRECISE));
        }
    }

    

}
