/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.astronomy.coordinate_system.Equatorial;
import com.ProcessException;
import com.CalculusType;
import com.units.SexagesimalDegree;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.units.Tools.tangent;
import com.units.HourAngle;
import static java.lang.Math.pow;

/**
 * Stars
 *
 * @author MIGUEL_ANGEL
 */
public class Star extends Equatorial
{

    /**
     * Proper motion
     */
    Equatorial properMotion;
    /**
     * Epoch
     */
    JulianDay julianDay;
    /**
     * Apparent magnitude
     */
    public double apparentMagnitude;
    /**
     * Name
     */
    String name;

    /**
     *
     * @return Star's name
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     *
     * @param name Star's name
     * @param positionOnEpochDate Star's position for the epoch of J2000
     * @param properMotion Proper motion
     * @param apparentMagnitude Apparent magnitude
     * @throws com.ProcessException Format error
     */
    public Star(String name, Equatorial positionOnEpochDate, Equatorial properMotion, double apparentMagnitude) throws ProcessException
    {
        this(name, positionOnEpochDate, JulianDay.valueOf("1/1/2000 12:00"), properMotion, apparentMagnitude);        
    }
    
     /**
     *
     * @param name Star's name
     * @param positionOnJulianDay Star's position
     * @param julianDay Hulian day
     * @param properMotion Proper motion
     * @param apparentMagnitude Apparent magnitude
     * @throws com.ProcessException Format error
     */
    public Star(String name, Equatorial positionOnJulianDay, JulianDay julianDay, Equatorial properMotion, double apparentMagnitude) throws ProcessException
    {
        super(positionOnJulianDay);
        this.name = name;
        this.properMotion = properMotion;
        this.julianDay = julianDay;
        this.apparentMagnitude = apparentMagnitude;
    }

    /**
     * 
     * @param newDate New Julian day
     * @param option APPROXIMATE or PRECISE
     * @return Star's precession
     * @throws ProcessException Format error
     */
    public Equatorial Precession(JulianDay newDate, CalculusType option) throws ProcessException
    {
        double T = julianDay.getCenturiesSince2000();
        double t = (newDate.getValue() - julianDay.getValue()) / 36525;

        SexagesimalDegree D = getDeclination().plus(new SexagesimalDegree(properMotion.getDeclination().getSignedValue() * t * 100));
        HourAngle AR = getRightAscension().plus(new HourAngle(properMotion.getRightAscension().getSignedValue() * t * 100));

        Equatorial ce = new Equatorial(D, AR);

        if (option == CalculusType.PRECISE)
        {
            ce = ce.StellarAberrationCorrection(newDate);
        }

        double a1 = (2306.2181 + 1.39656 * T - 0.000139 * pow(T, 2)) * t + (0.30188 - 0.000344 * T) * pow(t, 2) + 0.017998 * pow(t, 3); //segundos
        double a2 = (2306.2181 + 1.39656 * T - 0.000139 * pow(T, 2)) * t + (1.09468 + 0.000066 * T) * pow(t, 2) + 0.018203 * pow(t, 3);//segundos
        double a3 = (2004.3109 - 0.85330 * T - 0.000217 * pow(T, 2)) * t - (0.42665 + 0.000217 * T) * pow(t, 2) - 0.041833 * pow(t, 3);//segundos

        HourAngle A1 = HourAngle.valueOf(new SexagesimalDegree(a1 / 3600));
        HourAngle A2 = HourAngle.valueOf(new SexagesimalDegree(a2 / 3600));
        HourAngle A3 = HourAngle.valueOf(new SexagesimalDegree(a3 / 3600));

        double A = cosine(ce.getDeclination()) * sine(ce.getRightAscension().plus(A1));
        double B = cosine(A3) * cosine(ce.getDeclination()) * cosine(ce.getRightAscension().plus(A1)) - sine(A3) * sine(ce.getDeclination());
        double C = sine(A3) * cosine(ce.getDeclination()) * cosine(ce.getRightAscension().plus(A1)) + cosine(A3) * sine(ce.getDeclination());

        HourAngle rightAscension = HourAngle.atan2(A, B).plus(A2);
        SexagesimalDegree declination = SexagesimalDegree.asin(C);

        double nutationLongitude = newDate.getNutationInLongitude().getSignedValue();
        double nutationEcliptic = newDate.getNutationInObliquity().getSignedValue();
        SexagesimalDegree ecliptic = newDate.getTrueObliquityEcliptic(option.PRECISE);

        HourAngle rightAscensionCorrection = new HourAngle(nutationLongitude * (cosine(ecliptic) + sine(ecliptic) * sine(rightAscension) * tangent(declination)) - (nutationEcliptic * (cosine(rightAscension) * tangent(declination))));
        SexagesimalDegree declinationCorrection = new SexagesimalDegree(nutationLongitude * sine(ecliptic) * cosine(rightAscension) + nutationEcliptic * sine(rightAscension));
       
        declination = declination.plus(declinationCorrection);
        rightAscension = rightAscension.plus(rightAscensionCorrection);

        return new Equatorial(declination, rightAscension);
    }

    /**
     * 
     * @param newDate New Julian day
     * @param option APPROXIMATE or PRECISE
     * @return Star's new apparent position for the new Julian day
     * @throws ProcessException Format error
     */
    public Equatorial getApparentPosition(JulianDay newDate, CalculusType option) throws ProcessException
    {
        double T = julianDay.getCenturiesSince2000();
        double t = (newDate.getValue() - julianDay.getValue()) / 36525;

        Equatorial ce = Precession(newDate, option);

        SexagesimalDegree D = ce.getDeclination();
        HourAngle AR = ce.getRightAscension();

        SexagesimalDegree nl = newDate.getNutationInLongitude();
        SexagesimalDegree oe = newDate.getTrueObliquityEcliptic(option);
        SexagesimalDegree ne = newDate.getNutationInObliquity();

        SexagesimalDegree stl = Sun.getTrueLongitude(newDate);

        double incrAR = 3600 * ((cosine(oe) + sine(oe) * sine(AR) * tangent(D)) * nl.getSignedValue() - cosine(AR) * tangent(D) * ne.getSignedValue());
        double incrD = 3600 * (sine(oe) * cosine(AR) * nl.getSignedValue() + sine(AR) * ne.getSignedValue());

        double k = 20.49552; //constante de aberración

        double incrAR2 = 0;
        double incrD2 = 0;

        if (option == CalculusType.APPROXIMATE)
        {
            SexagesimalDegree e = newDate.getEccentricityOfEarthOrbit();
            SexagesimalDegree pi = newDate.getLongitudeOfPerihelionOfEarthOrbit();

            incrAR2 = -k * (cosine(AR) * cosine(stl) * cosine(oe) + sine(AR) * sine(stl)) / cosine(D)
                    + e.getValue() * k * (cosine(AR) * cosine(pi) * cosine(oe) + sine(AR) * sine(pi)) / cosine(D);

            incrD2 = -k * (cosine(stl) * cosine(oe) * (tangent(oe) * cosine(D) - sine(AR) * sine(D)) + cosine(AR) * sine(D) * sine(stl))
                    + e.getValue() * k * (cosine(pi) * cosine(oe) * (tangent(oe) * cosine(D) - sine(AR) * sine(D)) + cosine(AR) * sine(D) * sine(pi));
        }
        SexagesimalDegree D2 = D.plus(new SexagesimalDegree((incrD + incrD2) / 3600));
        HourAngle AR2 = AR.plus(HourAngle.valueOf(new SexagesimalDegree((incrAR + incrAR2) / 3600)));

        return new Equatorial(D2, AR2);

    }
}
