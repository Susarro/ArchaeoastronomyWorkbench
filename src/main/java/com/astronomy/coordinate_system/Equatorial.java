/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.coordinate_system;

import com.ProcessException;
import com.astronomy.JulianDay;
import static com.CalculusType.APPROXIMATE;
import static com.CalculusType.PRECISE;
import com.units.SexagesimalDegree;
import com.units.HourAngle;
import com.units.Radian;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static com.units.Tools.sine;
import static com.units.Tools.tangent;
import static com.units.Tools.cosine;

/**
 * Equatorial coordinate system
 *
 * @author MIGUEL_ANGEL
 */
public class Equatorial
{

    /**
     * Declination
     */
    private SexagesimalDegree declination;
    /**
     * Right ascension
     */
    private HourAngle rightAscension;
    /**
     * Distance
     */
    private double distance = 0;

    @Override
    public String toString()
    {
        return "(" + getDeclination().toString() + "," + getRightAscension().toString() + ")";
    }

    /**
     *
     * @param declination Declination
     * @param rightAscension Right ascension
     */
    public Equatorial(SexagesimalDegree declination, HourAngle rightAscension)
    {
        this.declination = declination;
        this.rightAscension = rightAscension;
    }

    /**
     *
     * @param declination Declination
     * @param rightAscension Right ascension
     * @param distance Distance
     */
    public Equatorial(SexagesimalDegree declination, HourAngle rightAscension, double distance)
    {
        this.declination = declination;
        this.rightAscension = rightAscension;
        this.distance = distance;
    }

    /**
     *
     * @param coordinates Equatorial coordinates
     */
    public Equatorial(Equatorial coordinates)
    {
        this.declination = coordinates.declination;
        this.rightAscension = coordinates.rightAscension;
        this.distance = coordinates.distance;
    }

    /**
     * 
     * @param obliquity Obliquity angle
     * @return coordinates transformed to ecliptic coordinate system
     */
    public Ecliptic toEcliptic(SexagesimalDegree obliquity)
    {
        SexagesimalDegree longitud = SexagesimalDegree.atan2(sine(getRightAscension()) * cosine(obliquity) + tangent(getDeclination()) * sine(obliquity), cosine(getRightAscension())).reduction();
        SexagesimalDegree latitud = SexagesimalDegree.asin(sine(getDeclination()) * cosine(obliquity) - cosine(getDeclination()) * sine(obliquity) * sine(getRightAscension())).reduction();
        return new Ecliptic(longitud, latitud);
    }

    /**
     * 
     * @param latitude Latitude
     * @param longitude Longitude
     * @param julianDay Julian day
     * @return coordinates transformed to horizontal (but geogentric) coordinate system
     * @throws ProcessException Format error
     */
    public Horizontal toHorizontal(SexagesimalDegree latitude, SexagesimalDegree longitude, JulianDay julianDay) throws ProcessException
    {
        SexagesimalDegree oblicuidadEcliptica = julianDay.getTrueObliquityEcliptic(APPROXIMATE);
        HourAngle anguloHorarioLocal = julianDay.getApparrentSiderealTimeAtGreenwich(oblicuidadEcliptica).minus(HourAngle.valueOf(longitude)).minus(getRightAscension());
        SexagesimalDegree acimut = SexagesimalDegree.atan2(sine(anguloHorarioLocal), cosine(anguloHorarioLocal) * sine(latitude) - tangent(getDeclination()) * cosine(latitude)).plus(new SexagesimalDegree(180)).reduction();
        SexagesimalDegree elevacion = SexagesimalDegree.asin(sine(latitude) * sine(getDeclination()) + cosine(latitude) * cosine(getDeclination()) * cosine(anguloHorarioLocal)).reduction();
        return new Horizontal(acimut, elevacion);
    }

    /**
     * 
     * @param ce1 Position 1 in equatorial coordinate system
     * @param ce2 Position 2 in equatorial coordinate system
     * @return Angular separation between ce1 and ce2
     */
    static public SexagesimalDegree getAngularSeparation(Equatorial ce1, Equatorial ce2)
    {
        double c = sine(ce1.getDeclination()) * sine(ce2.getDeclination()) + cosine(ce1.getDeclination()) * cosine(ce2.getDeclination()) * cosine(ce1.getRightAscension().minus(ce2.getRightAscension()));
        if (c < 0.999995)
        {
            return SexagesimalDegree.acos(c);
        }
        else
        {
            double c1 = SexagesimalDegree.valueOf(ce1.getRightAscension().minus(ce2.getRightAscension())).getValue();
            double c2 = ce1.getDeclination().minus(ce2.getDeclination()).getValue();
            double c3 = (ce1.getDeclination().getValue() + ce2.getDeclination().getValue()) / 2;
            return new SexagesimalDegree(sqrt(pow(c1 * cosine(new SexagesimalDegree(c3)), 2) + pow(c2, 2)));
        }

    }

    /**
     * 
     * @param day Julian day
     * @return stellar aberration correction in declination and right ascension
     * @throws ProcessException Format error
     */
    public Equatorial stellarAberrationCorrection(JulianDay day) throws ProcessException
    {
        double T = day.getCenturiesSince2000();

        double L2 = 3.1761467 + 1021.3285546 * T; //mean longitude of Venus referred to the mean equinox of J2000 in radians
        double L3 = 1.7534703 + 628.3075849 * T; 
        double L4 = 6.2034809 + 334.0612431 * T;
        double L5 = 0.5995465 + 52.9690965 * T;
        double L6 = 0.8740168 + 21.3299095 * T;
        double L7 = 5.4812939 + 7.4781599 * T;
        double L8 = 5.3118863 + 3.8133036 * T; //mean longitude of Neptune referred to the mean equinox of J2000 in radians
        double l = 3.8103444 + 8399.6847337 * T;//mean longitude of the Moon referred to the mean equinox of J2000 in radians
        double D = 5.19844667 + 7771.3771486 * T;
        double m = 2.3555559 + 8328.6914289 * T;
        double F = 1.6279052 + 8433.4661601 * T;

        double xs = 0;
        xs += (-1719914 - 2 * T) * sin(L3);
        xs += (6434 + 141 * T) * sin(2 * L3);
        xs += (715) * sin(L5);
        xs += (715) * sin(l);
        xs += (486 - 5 * T) * sin(3 * L3);
        xs += (159) * sin(L6);
        xs += (0) * sin(F);
        xs += (39) * sin(l + m);
        xs += (33) * sin(2 * L5);
        xs += (31) * sin(2 * L3 - L5);
        xs += (8) * sin(3 * L3 - 8 * L4 + 3 * L5);
        xs += (8) * sin(5 * L3 - 8 * L4 + 3 * L5);
        xs += (21) * sin(2 * L2 - L3);
        xs += (-19) * sin(L2);
        xs += (17) * sin(L7);
        xs += (16) * sin(L3 - 2 * L5);
        xs += (16) * sin(L8);
        xs += (11) * sin(L3 + L5);
        xs += (0) * sin(2 * L2 - 2 * L3);
        xs += (-11) * sin(L3 - L5);
        xs += (-7) * sin(4 * L3);
        xs += (-10) * sin(3 * L3 - 2 * L5);
        xs += (-9) * sin(L2 - 2 * L3);
        xs += (-9) * sin(2 * L2 - 3 * L3);
        xs += (0) * sin(2 * L6);
        xs += (0) * sin(2 * L2 - 4 * L3);
        xs += (8) * sin(3 * L3 - 2 * L4);
        xs += (8) * sin(l + 2 * D - m);
        xs += (-4) * sin(8 * L2 - 12 * L3);
        xs += (-4) * sin(8 * L2 - 14 * L3);
        xs += (-6) * sin(2 * L4);
        xs += (-1) * sin(3 * L2 - 4 * L3);
        xs += (4) * sin(2 * L3 - 2 * L5);
        xs += (0) * sin(3 * L2 - 3 * L3);
        xs += (5) * sin(2 * L3 - 2 * L4);
        xs += (5) * sin(l - 2 * D);

        double xc = 0;
        xc += (-25) * cos(L3);
        xc += (28007 - 107 * T) * cos(2 * L3);
        xc += (0) * cos(L5);
        xc += (0) * cos(l);
        xc += (-236 - 4 * T) * cos(3 * L3);
        xc += (0) * cos(L6);
        xc += (0) * cos(F);
        xc += (0) * cos(l + m);
        xc += (-10) * cos(2 * L5);
        xc += (1) * cos(2 * L3 - L5);
        xc += (-28) * cos(3 * L3 - 8 * L4 + 3 * L5);
        xc += (-28) * cos(5 * L3 - 8 * L4 + 3 * L5);
        xc += (0) * cos(2 * L2 - L3);
        xc += (0) * cos(L2);
        xc += (0) * cos(L7);
        xc += (0) * cos(L3 - 2 * L5);
        xc += (0) * cos(L8);
        xc += (-1) * cos(L3 + L5);
        xc += (-11) * cos(2 * L2 - 2 * L3);
        xc += (-2) * cos(L3 - L5);
        xc += (-8) * cos(4 * L3);
        xc += (0) * cos(3 * L3 - 2 * L5);
        xc += (0) * cos(L2 - 2 * L3);
        xc += (0) * cos(2 * L2 - 3 * L3);
        xc += (-9) * cos(2 * L6);
        xc += (-9) * cos(2 * L2 - 4 * L3);
        xc += (0) * cos(3 * L3 - 2 * L4);
        xc += (0) * cos(l + 2 * D - m);
        xc += (-7) * cos(8 * L2 - 12 * L3);
        xc += (-7) * cos(8 * L2 - 14 * L3);
        xc += (-5) * cos(2 * L4);
        xc += (-1) * cos(3 * L2 - 4 * L3);
        xc += (-6) * cos(2 * L3 - 2 * L5);
        xc += (-7) * cos(3 * L2 - 3 * L3);
        xc += (-5) * cos(2 * L3 - 2 * L4);
        xc += (0) * cos(l - 2 * D);

        double ys = 0;
        ys += (25 - 13 * T) * sin(L3);
        ys += (25697 - 95 * T) * sin(2 * L3);
        ys += (6) * sin(L5);
        ys += (0) * sin(l);
        ys += (-216 - 4 * T) * sin(3 * L3);
        ys += (2) * sin(L6);
        ys += (0) * sin(F);
        ys += (0) * sin(l + m);
        ys += (-9) * sin(2 * L5);
        ys += (1) * sin(2 * L3 - L5);
        ys += (25) * sin(3 * L3 - 8 * L4 + 3 * L5);
        ys += (-25) * sin(5 * L3 - 8 * L4 + 3 * L5);
        ys += (0) * sin(2 * L2 - L3);
        ys += (0) * sin(L2);
        ys += (0) * sin(L7);
        ys += (0) * sin(L3 - 2 * L5);
        ys += (1) * sin(L8);
        ys += (-1) * sin(L3 + L5);
        ys += (-10) * sin(2 * L2 - 2 * L3);
        ys += (-2) * sin(L3 - L5);
        ys += (-8) * sin(4 * L3);
        ys += (0) * sin(3 * L3 - 2 * L5);
        ys += (0) * sin(L2 - 2 * L3);
        ys += (0) * sin(2 * L2 - 3 * L3);
        ys += (-8) * sin(2 * L6);
        ys += (8) * sin(2 * L2 - 4 * L3);
        ys += (0) * sin(3 * L3 - 2 * L4);
        ys += (0) * sin(l + 2 * D - m);
        ys += (-6) * sin(8 * L2 - 12 * L3);
        ys += (6) * sin(8 * L2 - 14 * L3);
        ys += (-4) * sin(2 * L4);
        ys += (-2) * sin(3 * L2 - 4 * L3);
        ys += (-5) * sin(2 * L3 - 2 * L5);
        ys += (-6) * sin(3 * L2 - 3 * L3);
        ys += (-4) * sin(2 * L3 - 2 * L4);
        ys += (0) * sin(l - 2 * D);

        double yc = 0;
        yc += (1578089 + 156 * T) * cos(L3);
        yc += (-5904 - 130 * T) * cos(2 * L3);
        yc += (-657) * cos(L5);
        yc += (-656) * cos(l);
        yc += (-446 + 5 * T) * cos(3 * L3);
        yc += (-147) * cos(L6);
        yc += (26) * cos(F);
        yc += (-36) * cos(l + m);
        yc += (-30) * cos(2 * L5);
        yc += (-28) * cos(2 * L3 - L5);
        yc += (8) * cos(3 * L3 - 8 * L4 + 3 * L5);
        yc += (-8) * cos(5 * L3 - 8 * L4 + 3 * L5);
        yc += (-19) * cos(2 * L2 - L3);
        yc += (17) * cos(L2);
        yc += (-16) * cos(L7);
        yc += (15) * cos(L3 - 2 * L5);
        yc += (-15) * cos(L8);
        yc += (-10) * cos(L3 + L5);
        yc += (0) * cos(2 * L2 - 2 * L3);
        yc += (9) * cos(L3 - L5);
        yc += (6) * cos(4 * L3);
        yc += (9) * cos(3 * L3 - 2 * L5);
        yc += (-9) * cos(L2 - 2 * L3);
        yc += (-8) * cos(2 * L2 - 3 * L3);
        yc += (0) * cos(2 * L6);
        yc += (0) * cos(2 * L2 - 4 * L3);
        yc += (-8) * cos(3 * L3 - 2 * L4);
        yc += (-7) * cos(l + 2 * D - m);
        yc += (4) * cos(8 * L2 - 12 * L3);
        yc += (-4) * cos(8 * L2 - 14 * L3);
        yc += (5) * cos(2 * L4);
        yc += (-7) * cos(3 * L2 - 4 * L3);
        yc += (-4) * cos(2 * L3 - 2 * L5);
        yc += (0) * cos(3 * L2 - 3 * L3);
        yc += (-5) * cos(2 * L3 - 2 * L4);
        yc += (-5) * cos(l - 2 * D);

        double zs = 0;
        zs += (10 + 32 * T) * sin(L3);
        zs += (11141 - 48 * T) * sin(2 * L3);
        zs += (-15) * sin(L5);
        zs += (0) * sin(l);
        zs += (-94) * sin(3 * L3);
        zs += (-6) * sin(L6);
        zs += (0) * sin(F);
        zs += (0) * sin(l + m);
        zs += (-5) * sin(2 * L5);
        zs += (0) * sin(2 * L3 - L5);
        zs += (11) * sin(3 * L3 - 8 * L4 + 3 * L5);
        zs += (-11) * sin(5 * L3 - 8 * L4 + 3 * L5);
        zs += (0) * sin(2 * L2 - L3);
        zs += (0) * sin(L2);
        zs += (0) * sin(L7);
        zs += (1) * sin(L3 - 2 * L5);
        zs += (-3) * sin(L8);
        zs += (-1) * sin(L3 + L5);
        zs += (-4) * sin(2 * L2 - 2 * L3);
        zs += (-1) * sin(L3 - L5);
        zs += (-3) * sin(4 * L3);
        zs += (0) * sin(3 * L3 - 2 * L5);
        zs += (0) * sin(L2 - 2 * L3);
        zs += (0) * sin(2 * L2 - 3 * L3);
        zs += (-3) * sin(2 * L6);
        zs += (3) * sin(2 * L2 - 4 * L3);
        zs += (0) * sin(3 * L3 - 2 * L4);
        zs += (0) * sin(l + 2 * D - m);
        zs += (-3) * sin(8 * L2 - 12 * L3);
        zs += (3) * sin(8 * L2 - 14 * L3);
        zs += (-2) * sin(2 * L4);
        zs += (1) * sin(3 * L2 - 4 * L3);
        zs += (-2) * sin(2 * L3 - 2 * L5);
        zs += (-3) * sin(3 * L2 - 3 * L3);
        zs += (-2) * sin(2 * L3 - 2 * L4);
        zs += (0) * sin(l - 2 * D);

        double zc = 0;
        zc += (684185 - 358 * T) * cos(L3);
        zc += (-2559 - 55 * T) * cos(2 * L3);
        zc += (-282) * cos(L5);
        zc += (-285) * cos(l);
        zc += (-193) * cos(3 * L3);
        zc += (-61) * cos(L6);
        zc += (-59) * cos(F);
        zc += (-16) * cos(l + m);
        zc += (-13) * cos(2 * L5);
        zc += (-12) * cos(2 * L3 - L5);
        zc += (3) * cos(3 * L3 - 8 * L4 + 3 * L5);
        zc += (-3) * cos(5 * L3 - 8 * L4 + 3 * L5);
        zc += (-8) * cos(2 * L2 - L3);
        zc += (8) * cos(L2);
        zc += (-7) * cos(L7);
        zc += (7) * cos(L3 - 2 * L5);
        zc += (-6) * cos(L8);
        zc += (-5) * cos(L3 + L5);
        zc += (0) * cos(2 * L2 - 2 * L3);
        zc += (4) * cos(L3 - L5);
        zc += (3) * cos(4 * L3);
        zc += (4) * cos(3 * L3 - 2 * L5);
        zc += (-4) * cos(L2 - 2 * L3);
        zc += (-4) * cos(2 * L2 - 3 * L3);
        zc += (0) * cos(2 * L6);
        zc += (0) * cos(2 * L2 - 4 * L3);
        zc += (-3) * cos(3 * L3 - 2 * L4);
        zc += (-3) * cos(l + 2 * D - m);
        zc += (2) * cos(8 * L2 - 12 * L3);
        zc += (-2) * cos(8 * L2 - 14 * L3);
        zc += (2) * cos(2 * L4);
        zc += (-4) * cos(3 * L2 - 4 * L3);
        zc += (-2) * cos(2 * L3 - 2 * L5);
        zc += (0) * cos(3 * L2 - 3 * L3);
        zc += (-2) * cos(2 * L3 - 2 * L4);
        zc += (-2) * cos(l - 2 * D);

        double X = xs + xc;
        double Y = ys + yc;
        double Z = zs + zc;
        double c = 17314463350.0; //velocidad de la luz

        double ar = (Y * cosine(getRightAscension()) - X * sine(getRightAscension())) / (c * cosine(getDeclination())); //en radianes
        double d = -((X * cosine(rightAscension) + Y * sine(rightAscension)) * sine(declination) - Z * cosine(declination)) / c;
        return new Equatorial(getDeclination().plus(SexagesimalDegree.valueOf(new Radian(d))), getRightAscension().plus(HourAngle.valueOf(new Radian(ar))));

    }

    /**
     * 
     * @param surfacePosition earth surface coordinates
     * @param day Juliuan day
     * @return coordinates transformed from geocentric to topocentric coordinate system
     * @throws ProcessException  Format error
     */
    public Equatorial geocentric2Topocentric(Geographic surfacePosition, JulianDay day) throws ProcessException
    {
        if (getDistance() == 0)
        {
            return new Equatorial(getDeclination(), getRightAscension());
        }
        else
        {
            SexagesimalDegree paralaje = SexagesimalDegree.asin(sine(new SexagesimalDegree(8.794 / 3600)) / getDistance());
            double a = 6378140;
            double f = 1 / 298.257;
            double b = a * (1 - f);

            SexagesimalDegree u = SexagesimalDegree.atan2(b * tangent(surfacePosition.getLatitude()), a);

            double A = b * sine(u) / a + surfacePosition.getElevation() * sine(surfacePosition.getLatitude()) / 6378140;
            double B = cosine(u) + surfacePosition.getElevation() * cosine(surfacePosition.getLatitude()) / 6378140;
            SexagesimalDegree oblicuidadEcliptica = day.getTrueObliquityEcliptic(PRECISE);
            HourAngle anguloHorarioLocal = day.getApparrentSiderealTimeAtGreenwich(oblicuidadEcliptica).minus(HourAngle.valueOf(surfacePosition.getLongitude())).minus(getRightAscension());

            HourAngle incrAR = HourAngle.atan2(-B * sine(paralaje) * sine(anguloHorarioLocal), cosine(getDeclination()) - B * sine(paralaje) * cosine(anguloHorarioLocal));
            SexagesimalDegree nuevaDE = SexagesimalDegree.atan2((sine(getDeclination()) - A * sine(paralaje)) * cosine(incrAR), cosine(getDeclination()) - B * sine(paralaje) * cosine(anguloHorarioLocal));

            return new Equatorial(nuevaDE, getRightAscension().plus(incrAR), getDistance());
        }
    }

    /**
     * @return Declination
     */
    public SexagesimalDegree getDeclination()
    {
        return declination;
    }

    /**
     * @param declination Declination to set
     */
    public void setDeclinacion(SexagesimalDegree declination)
    {
        this.declination = declination;
    }

    /**
     * @return right ascension
     */
    public HourAngle getRightAscension()
    {
        return rightAscension;
    }

    /**
     * @param rightAscension Right ascension to set
     */
    public void setRightAscension(HourAngle rightAscension)
    {
        this.rightAscension = rightAscension;
    }

    /**
     * @return Distance
     */
    public double getDistance()
    {
        return distance;
    }

    /**
     * @param distance Distance to set
     */
    public void setDistance(double distance)
    {
        this.distance = distance;
    }
}
