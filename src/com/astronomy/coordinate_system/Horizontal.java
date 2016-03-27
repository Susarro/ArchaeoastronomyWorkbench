/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.coordinate_system;

import com.ProcessException;
import com.astronomy.JulianDay;
import com.astronomy.Planet;
import com.PlanetEnum;
import static com.CalculusType.APPROXIMATE;
import com.units.SexagesimalDegree;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.units.Tools.tangent;
import com.units.HourAngle;

/**
 * Horizontal coordinate system
 *
 * @author MIGUEL_ANGEL
 */
public class Horizontal
{

    /**
     * Horizontal azimuth
     */
    private SexagesimalDegree azimuth;
    /**
     * Horizontal altitude
     */
    private SexagesimalDegree altitude;

    @Override
    public String toString()
    {
        return "(" + getAzimuth().toString() + "," + getAltitude().toString() + ")";
    }

    /**
     *
     * @param azimuth Horizontal azimuth
     * @param altitude Horizontal altitude
     */
    public Horizontal(SexagesimalDegree azimuth, SexagesimalDegree altitude)
    {
        this.azimuth = azimuth;
        this.altitude = altitude;
    }

    /**
     *
     * @param latitude Geographic latitude
     * @param longitude Geographic longitude
     * @param JulianDay Julian day
     * @return coordinates transformed to equatorial coordinate system
     * @throws ProcessException Format error
     */
    public Equatorial toEquatorial(SexagesimalDegree latitude, SexagesimalDegree longitude, JulianDay JulianDay) throws ProcessException
    {
        SexagesimalDegree oblicuidadEcliptica = JulianDay.getTrueObliquityEcliptic(APPROXIMATE);
        HourAngle anguloHorarioLocal = HourAngle.atan2(sine(getAzimuth()), cosine(getAzimuth()) * sine(latitude) + tangent(getAltitude()) * cosine(latitude));
        HourAngle ascensionRecta = JulianDay.getApparrentSiderealTimeAtGreenwich(oblicuidadEcliptica).minus(HourAngle.valueOf(longitude)).minus(anguloHorarioLocal).Reduction();
        SexagesimalDegree declinacion = SexagesimalDegree.asin(sine(latitude) * sine(getAltitude()) + cosine(latitude) * cosine(getAltitude()) * cosine(getAzimuth())).Reduction();
        return new Equatorial(declinacion, ascensionRecta);
    }

    /**
     *
     * @param site Geographic coordinates of observation place
     * @param JulianDay Julian day
     * @return coordinates transformed to equatorial coordinate system
     * @throws ProcessException Format error
     */
    public Equatorial toEquatorial(Geographic site, JulianDay JulianDay) throws ProcessException
    {
        SexagesimalDegree oblicuidadEcliptica = JulianDay.getTrueObliquityEcliptic(APPROXIMATE);
        HourAngle anguloHorarioLocal = HourAngle.atan2(sine(getAzimuth()), cosine(getAzimuth()) * sine(site.getLatitude()) + tangent(getAltitude()) * cosine(site.getLatitude()));
        HourAngle ascensionRecta = JulianDay.getApparrentSiderealTimeAtGreenwich(oblicuidadEcliptica).minus(HourAngle.valueOf(site.getLongitude())).minus(anguloHorarioLocal).Reduction();
        SexagesimalDegree declinacion = SexagesimalDegree.asin(sine(site.getLatitude()) * sine(getAltitude()) + cosine(site.getLatitude()) * cosine(getAltitude()) * cosine(getAzimuth())).Reduction();
        return new Equatorial(declinacion, ascensionRecta);
    }

    /**
     * True horizontal coordinates to apparent horizontal coordinates, considering parallax
     * @param planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param day Julian day
     * @return Apparent horizontal coordinates
     * @throws ProcessException
     */
    public ApparentHorizontal toApparent(PlanetEnum planet, JulianDay day) throws ProcessException
    {
        ApparentHorizontal h = new ApparentHorizontal(this.getAzimuth(), this.getAltitude());
        SexagesimalDegree p = Horizontal.getParallax(planet, day, h.getAltitude());
        h.setElevacion(getAparentAltitudeAffectedByAtmosphericRefraction(this.getAltitude()).minus(p));
        return h;
    }

    /**
     * True horizontal coordinates to apparent horizontal coordinates, no considering parallax
     * @return  Apparent horizontal coordinates
     * @throws ProcessException 
     */
    public ApparentHorizontal toApparent() throws ProcessException
    {
        ApparentHorizontal h = new ApparentHorizontal(this.getAzimuth(), this.getAltitude());
        h.setElevacion(getAparentAltitudeAffectedByAtmosphericRefraction(this.getAltitude()));
        return h;
    }

    /**
     * True altitude affected by atmospheric refraction
     *
     * @param apparentAltitude apparent altitude
     * @return True altitude
     * @throws ProcessException Format error
     */
    static public SexagesimalDegree getTrueAltitudeAffectedByAtmosphericRefraction(SexagesimalDegree apparentAltitude) throws ProcessException
    {
        double R;
        double v = apparentAltitude.getValue() + 7.31 / (apparentAltitude.getValue() + 4.4);
        R = (1 / tangent(new SexagesimalDegree(v))) + 0.00135152167375625415468205231256;
        return apparentAltitude.minus(new SexagesimalDegree(R / 60));
    }
    /**
     * apparent altitude affected by atmospheric refraction
     * @param trueAltitude True altitude
     * @return Apparent altitude
     * @throws ProcessException Format error
     */
    static public SexagesimalDegree getAparentAltitudeAffectedByAtmosphericRefraction(SexagesimalDegree trueAltitude) throws ProcessException
    {
        double R;
        double v = trueAltitude.getSignedValue() + 10.3 / (trueAltitude.getSignedValue() + 5.11);
        R = (1.02 / tangent(new SexagesimalDegree(v))) + 0.00196535584287727557399420258844;
        return trueAltitude.plus(new SexagesimalDegree(R / 60));

    }

    /**
     *
     * @param site Geographic coordinates of observation place
     * @param planet Planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param day Julian day
     * @param altitude altitude
     * @return Parallax error
     * @throws ProcessException Format error
     */
    static public SexagesimalDegree getParallax(Geographic site, PlanetEnum planet, JulianDay day, SexagesimalDegree altitude) throws ProcessException
    {
        double distancia = Planet.getTrueDistanceToEarth(planet, day);
        if (distancia == 0)
        {
            return new SexagesimalDegree(0);
        }
        SexagesimalDegree paralaje = SexagesimalDegree.asin(sine(new SexagesimalDegree(8.794 / 3600)) / distancia);
        double a = 6378140;
        double f = 1 / 298.257;
        double b = a * (1 - f);
        SexagesimalDegree u = SexagesimalDegree.atan2(b * tangent(site.getLatitude()), a);
        double A = b * sine(u) / a + site.getAltitud() * sine(site.getLatitude()) / 6378140;
        double B = cosine(u) + site.getAltitud() * cosine(site.getLatitude()) / 6378140;
        SexagesimalDegree temp = SexagesimalDegree.atan2(A, B);
        double ro = A / sine(temp);

        return SexagesimalDegree.asin(ro * sine(paralaje) * cosine(altitude));
    }

    /**
     *
     * @param planet Planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param day Julian day
     * @param altitude altitude
     * @return Parallax error
     * @throws ProcessException Format error
     */
    static public SexagesimalDegree getParallax(PlanetEnum planet, JulianDay day, SexagesimalDegree altitude) throws ProcessException
    {
        double distancia = Planet.getTrueDistanceToEarth(planet, day);
        if (distancia == 0)
        {
            return new SexagesimalDegree(0);
        }
        SexagesimalDegree paralaje = SexagesimalDegree.asin(sine(new SexagesimalDegree(8.794 / 3600)) / distancia);
        double ro = 1;

        return SexagesimalDegree.asin(ro * sine(paralaje) * cosine(altitude));
    }

    /**
     *
     * @param planet Planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param altitude Altitude
     * @return Approximate parallax error
     * @throws ProcessException Format error
     */
    static public SexagesimalDegree getParallax(PlanetEnum planet, SexagesimalDegree altitude) throws ProcessException
    {
        switch (planet)
        {
            case MOON:
                double distancia = 384400.0 / 149597871.0;
                SexagesimalDegree paralaje = SexagesimalDegree.asin(sine(new SexagesimalDegree(8.794 / 3600)) / distancia);
                double ro = 1;

                return SexagesimalDegree.asin(ro * sine(paralaje) * cosine(altitude));
            default:
                return new SexagesimalDegree(0);
        }

    }

    /**
     * @return azimuth
     */
    public SexagesimalDegree getAzimuth()
    {
        return azimuth;
    }

    /**
     * @param acimut azimuth to set
     */
    public void setAcimut(SexagesimalDegree acimut)
    {
        this.azimuth = acimut;
    }

    /**
     * @return altitude
     */
    public SexagesimalDegree getAltitude()
    {
        return altitude;
    }

    /**
     * @param altitude altitude to set
     */
    public void setElevacion(SexagesimalDegree altitude)
    {
        this.altitude = altitude;
    }

}
