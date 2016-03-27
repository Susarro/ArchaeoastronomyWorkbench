/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.coordinate_system;

import com.units.SexagesimalDegree;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static com.units.Tools.tangent;
import com.units.HourAngle;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Ecliptic coordinate system
 * @author MIGUEL_ANGEL
 */
public class Ecliptic
{

    /**
     * Ecliptic longitude
     */
    private SexagesimalDegree longitude;
    /**
     * Ecliptic latitude
     */
    private SexagesimalDegree latitude;
    /**
     * Distance
     */
    private double distance = 0;

    /**
     * 
     * @param longitude Ecliptic longitude
     * @param latitude  Ecliptic latitude
     */
    public Ecliptic(SexagesimalDegree longitude, SexagesimalDegree latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * 
     * @param longitude Ecliptic longitude
     * @param latitude Ecliptic latitude
     * @param distance Distance
     */
    public Ecliptic(SexagesimalDegree longitude, SexagesimalDegree latitude, double distance)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    /**
     * 
     * @param coordinates Ecliptic coordinates 
     */
    public Ecliptic(Ecliptic coordinates)
    {
        this.longitude = coordinates.longitude;
        this.latitude = coordinates.latitude;
        this.distance = coordinates.distance;
    }

    /**
     * 
     * @param obliquity Obliquity angle
     * @return coordinates transformed to equatorial coordinate system
     */
    public Equatorial toEquatorial(SexagesimalDegree obliquity)
    {
        HourAngle ascensionRecta = HourAngle.atan2(sine(getLongitude()) * cosine(obliquity) - tangent(getLatitude()) * sine(obliquity), cosine(getLongitude())).Reduction();
        SexagesimalDegree declinacion = SexagesimalDegree.asin(sine(getLatitude()) * cosine(obliquity) + cosine(getLatitude()) * sine(obliquity) * sine(getLongitude())).Reduction();
        if (getDistancia() != 0)
        {
            return new Equatorial(declinacion, ascensionRecta, getDistancia());
        }
        else
        {
            return new Equatorial(declinacion, ascensionRecta);
        }
    }

    /**
     * 
     * @param obliquity Obliquity angle
     * @param distance 
     * @return coordinates transformed to equatorial coordinate system, including distance
     */
    public Equatorial toEquatorial(SexagesimalDegree obliquity, double distance)
    {
        HourAngle ascensionRecta = HourAngle.atan2(sine(getLongitude()) * cosine(obliquity) - tangent(getLatitude()) * sine(obliquity), cosine(getLongitude())).Reduction();
        SexagesimalDegree declinacion = SexagesimalDegree.asin(sine(getLatitude()) * cosine(obliquity) + cosine(getLatitude()) * sine(obliquity) * sine(getLongitude())).Reduction();
        return new Equatorial(declinacion, ascensionRecta, distance);
    }

    /**
     * 
     * @param ce1 Position 1 in ecliptic coordinate system
     * @param ce2 Position 2 in ecliptic coordinate system
     * @return Angular separation between ce1 and ce2
     */
    static public SexagesimalDegree getAngularSeparation(Ecliptic ce1, Ecliptic ce2)
    {
        double c = sine(ce1.getLatitude()) * sine(ce2.getLatitude()) + cosine(ce1.getLatitude()) * cosine(ce2.getLatitude()) * cosine(ce1.getLongitude().minus(ce2.getLongitude()));
        if (c < 0.999995)
        {
            return SexagesimalDegree.acos(c);
        }
        else
        {
            double c1 = ce1.getLongitude().minus(ce2.getLongitude()).getValue();
            double c2 = ce1.getLatitude().minus(ce2.getLatitude()).getValue();
            double c3 = (ce1.getLatitude().getValue() + ce2.getLatitude().getValue()) / 2;
            return new SexagesimalDegree(sqrt(pow(c1 * cosine(new SexagesimalDegree(c3)), 2) + pow(c2, 2)));
        }
    }

    /**
     * @return Ecliptic longitude
     */
    public SexagesimalDegree getLongitude()
    {
        return longitude;
    }

    /**
     * @param longitude Ecliptic longitude to set
     */
    public void setLongitude(SexagesimalDegree longitude)
    {
        this.longitude = longitude;
    }

    /**
     * @return Ecliptic latitude
     */
    public SexagesimalDegree getLatitude()
    {
        return latitude;
    }

    /**
     * @param latitude Ecliptic latitude to set
     */
    public void setLatitude(SexagesimalDegree latitude)
    {
        this.latitude = latitude;
    }

    /**
     * @return distance
     */
    public double getDistancia()
    {
        return distance;
    }

    /**
     * @param distance Distance to set
     */
    public void setDistancia(double distance)
    {
        this.distance = distance;
    }
}
