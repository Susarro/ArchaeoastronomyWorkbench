/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.coordinate_system;

import com.ProcessException;
import com.astronomy.JulianDay;
import com.astronomy.Disk;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import com.PlanetEnum;
import com.units.SexagesimalDegree;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.astronomy.coordinate_system.Horizontal.getParallax;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;

/**
 * Apparent Horizontal coordinates
 * 
 * @author MIGUEL_ANGEL
 */
public class ApparentHorizontal extends Horizontal
{

    /**
     * 
     * @param azimuth Azimuth
     * @param altitude Apparent altitude
     */
    public ApparentHorizontal(SexagesimalDegree azimuth, SexagesimalDegree altitude)
    {
        super(azimuth, altitude);
    }

    /**
     * 
     * @param planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param julianDay Julian day
     * @param disk UPPER_LIMB, LOWER_LIMB or CENTER
     * @return Astronomical o true Horizontal coordinates
     * @throws ProcessException Format error
     */
    public Horizontal toTrue(PlanetEnum planet, JulianDay julianDay, Disk disk) throws ProcessException
    {
        Horizontal h = new Horizontal(this.getAzimuth(), this.getAltitude());
        h.setAltitude(getTrueAltitudeAffectedByAtmosphericRefraction(this.getAltitude()));
        SexagesimalDegree p = getParallax(planet, julianDay, h.getAltitude());
        h.setAltitude(h.getAltitude().plus(p));

        SexagesimalDegree semidisco = null;
        switch (planet)
        {
            case SUN:
                semidisco = new SexagesimalDegree(959.0 / 3600);
                break;
            case MOON:
                semidisco = new SexagesimalDegree(961.0 / 3600);
                break;
            default:
                semidisco = new SexagesimalDegree(0);
        }
        switch (disk)
        {
            case UPPER_LIMB:
                h.setAltitude(h.getAltitude().minus(semidisco));
                break;
            case LOWER_LIMB:
                h.setAltitude(h.getAltitude().plus(semidisco));
                break;
        }
        return h;
    }

    /**
     * 
     * @param planet MERCURY, VENUS, MARS, JUPITER, SATURN, SUN or MOON
     * @param disk UPPER_LIMB, LOWER_LIMB or CENTER
     * @return Astronomical o true Horizontal coordinates
     * @throws ProcessException Format error
     */
    public Horizontal toTrue(PlanetEnum planet, Disk disk) throws ProcessException
    {
        Horizontal h = new Horizontal(this.getAzimuth(), this.getAltitude());
        h.setAltitude(getTrueAltitudeAffectedByAtmosphericRefraction(this.getAltitude()));
        SexagesimalDegree p = getParallax(planet, h.getAltitude());
        h.setAltitude(h.getAltitude().plus(p));

        SexagesimalDegree semidisco = null;
        switch (planet)
        {
            case SUN:
                semidisco = new SexagesimalDegree(959.0 / 3600);
                break;
            case MOON:
                semidisco = new SexagesimalDegree(961.0 / 3600);
                break;
            default:
                semidisco = new SexagesimalDegree(0);
        }
        switch (disk)
        {
            case UPPER_LIMB:
                h.setAltitude(h.getAltitude().minus(semidisco));
                break;
            case LOWER_LIMB:
                h.setAltitude(h.getAltitude().plus(semidisco));
                break;
        }

        return h;
    }

    /**
     * Obtain apparent horizontal coordinates based on coordinates of observation point and horizontal rerefence point
     * @param observation Geographic ccordinates of observation site
     * @param reference Geographic ccordinates of reference horizontal point
     * @return Apparen horizontal coordinates
     */
    public static ApparentHorizontal valueOf(Geographic observation, Geographic reference)
    {
        SexagesimalDegree d = SexagesimalDegree.acos(sine(reference.getLatitude()) * sine(observation.getLatitude()) + cosine(reference.getLatitude()) * cosine(observation.getLatitude()) * cosine(reference.getLongitude().minus(observation.getLongitude())));
        SexagesimalDegree A = SexagesimalDegree.asin(cosine(reference.getLatitude()) * sine(reference.getLongitude().minus(observation.getLongitude())) / sine(d));
        if (reference.getLongitude().getSignedValue() == observation.getLongitude().getSignedValue())
        {
            if (reference.getLatitude().getSignedValue() >= observation.getLatitude().getSignedValue())
            {
                A = new SexagesimalDegree(0);
            }
            else
            {
                A = new SexagesimalDegree(180);
            }
        }
        else
        {
            if (reference.getLatitude().getSignedValue() < observation.getLatitude().getSignedValue())
            {
                A = new SexagesimalDegree(180).minus(A);
            }
        }

        double R = 6378135;
        SexagesimalDegree h = SexagesimalDegree.atan2((R + reference.getElevation()) * cosine(d) - (R + observation.getElevation() + 1.7), (R + observation.getElevation()) * sine(d));

        return new ApparentHorizontal(A, h);
    }

}
