/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.ProcessException;
import com.astronomy.coordinate_system.Ecliptic;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.Heliocientric;
import static com.PlanetEnum.EARTH;
import com.CalculusType;
import com.units.SexagesimalDegree;
import static com.units.Tools.coefficientOfCosineOfArgument;
import static com.units.Tools.coefficientOfSineOfArgument;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static java.lang.Math.pow;

/**
 * Moon
 *
 * @author MIGUEL_ANGEL
 */
public class Moon
{

    /**
     *
     * @param julianDay Julian day
     * @return True distance to Earth
     * @throws ProcessException Format error
     */
    public static double getTrueDistanceToEarth(JulianDay julianDay) throws ProcessException
    {
        InfoMoon il = Moon.getInfoLuna(julianDay);
        return il.position.getDistancia();
    }

    /**
     *
     * @param julianDay Julian day
     * @return Moon's mean logitude
     */
    public static SexagesimalDegree getMeanLongitude(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(218.3164591 + 481267.88134236 * T - 0.0013268 * pow(T, 2) + pow(T, 3) / 538841 - pow(T, 4) / 65194000);
    }

    /**
     *
     * @param julianDay Julian day
     * @return Mean elongation of the Moon
     */
    public static SexagesimalDegree getMeanElongation(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(297.8502042 + 445267.1115168 * T - 0.0016300 * pow(T, 2) + pow(T, 3) / 545868 - pow(T, 4) / 113065000);
    }

    /**
     *
     * @param julianDay Julian day
     * @return Moon's mean anomaly
     */
    public static SexagesimalDegree getMeanAnomaly(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(134.9634114 + 477198.8676313 * T + 0.0089970 * pow(T, 2) + pow(T, 3) / 69699 - pow(T, 4) / 14712000);
    }

    /**
     *
     * @param julianDay Julian day
     * @return Moon's argument of latitude (Mean distance of the Moon from its
     * ascending node)
     */
    public static SexagesimalDegree getArgumentOfLatitude(JulianDay julianDay)
    {
        double T = julianDay.getCenturiesSince2000();
        return new SexagesimalDegree(93.2720993 + 483202.0175273 * T - 0.0034029 * pow(T, 2) - pow(T, 3) / 3526000 + pow(T, 4) / 863310000);
    }

    /**
     *
     * @param julianDay Jualian day
     * @param D Moon's mean elongation
     * @param M Sun's mean anomaly
     * @param MM Moon's mean anomaly
     * @param F Moon's argument of latitude
     * @param E Correction due to the eccentricity of Earth's orbit around the
     * Sun
     * @param LM Moon's mean longitude
     * @param A1
     * @param A2
     * @return Moon's longitude
     * @throws ProcessException
     */
    private static SexagesimalDegree calculateLongitude(JulianDay julianDay, double D, double M, double MM, double F, double E, double LM, double A1, double A2) throws ProcessException
    {
        double sumL = 0;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6288774);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1274027);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 658314);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 213618);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -185116) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -114332);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 58793);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 57066) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 53322);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 45758) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -40923) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34720);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -30383) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 15327);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -12528);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10980);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10675);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10034);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 8548);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7888) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -6766) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -5163);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4987) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4036) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3994);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3861);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3665);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2689) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -2602);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2390) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2348);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2236) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2120) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2069) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2048) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, -1773);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1595);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1215) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1110);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -892);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -810) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 759) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -713) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -700) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 691) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 596) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 549);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, 537);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 520) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -487);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, -399) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -381);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 351) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -340);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 330);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 327) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -323) * E * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 299) * E;
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 294);
        sumL += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumL += 3958 * sine(A1) + 1962 * sine(LM - F) + 318 * sine(A2);

        SexagesimalDegree longitude = new SexagesimalDegree(LM).plus(new SexagesimalDegree(sumL / 1000000));
        SexagesimalDegree nutacion = julianDay.getNutationInLongitude();
        return longitude.plus(nutacion);
    }

    /**
     *
     * @param julianDay Jualian day
     * @param D Moon's mean elongation
     * @param M Sun's mean anomaly
     * @param MM Moon's mean anomaly
     * @param F Moon's argument of latitude
     * @param E Correction due to the eccentricity of Earth's orbit around the
     * Sun
     * @param LM Moon's mean longitude
     * @param A1
     * @param A2
     * @return Moon's distance
     * @throws ProcessException
     */
    private static double calculateDistance(JulianDay julianDay, double D, double M, double MM, double F, double E, double LM, double A1, double A2) throws ProcessException
    {
        double sumR = 0;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -20905355);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3699111);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2955968);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -569925);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 48888) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -3149);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 246158);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -152138) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -170733);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -204586) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -129620) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 108743);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 104755) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10321);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 79661);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34782);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, -23210);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -21636);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 24208) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 30824) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -8379);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -16675) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -12831) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -10445);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -11650);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 14403);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7003) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10056) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6322);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -9884) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 5751) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -4950) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 4130);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3958) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3258);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2616) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1897) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2117) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2354) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1423);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1117);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1571) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1739);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -4421);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1165) * E * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 8752);

        return (385000.56 + sumR / 1000) / 1.4960e8;

    }

    /**
     *
     * @param julianDay Jualian day
     * @param D Moon's mean elongation
     * @param M Sun's mean anomaly
     * @param MM Moon's mean anomaly
     * @param F Moon's argument of latitude
     * @param E Correction due to the eccentricity of Earth's orbit around the
     * Sun
     * @param LM Moon's mean longitude
     * @param A1
     * @param A2
     * @param A3
     * @return Moon's latitude
     * @throws ProcessException
     */
    private static SexagesimalDegree calculateLatitude(JulianDay julianDay, double D, double M, double MM, double F, double E, double LM, double A1, double A2, double A3) throws ProcessException
    {
        double sumB = 0;

        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 5128122);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 280602);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 277693);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 173237);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 55413);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 46271);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 32573);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 17198);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 9266);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8822);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8216) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 4324);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 4200);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -3359) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2463) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2211) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 2065) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1870) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1828);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1794) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 3
        }, new double[]
        {
            D, M, MM, F
        }, -1749);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1565) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1491);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1475) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1410) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1344) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1335);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 3, 1
        }, new double[]
        {
            D, M, MM, F
        }, 1107);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1021);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 833);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, -3
        }, new double[]
        {
            D, M, MM, F
        }, 777);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 671);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, -3
        }, new double[]
        {
            D, M, MM, F
        }, 607);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 596);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 491) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -451);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 439);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 422);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 0, -3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 421);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -366) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -351) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 331);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 315) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -2, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 302) * E * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 3
        }, new double[]
        {
            D, M, MM, F
        }, -283);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -229) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -185);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 181) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            0, 1, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -177) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 176);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 166) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -164);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 132);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            1, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -119);
        sumB += coefficientOfSineOfArgument(new double[]
        {
            4, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 115) * E;
        sumB += coefficientOfSineOfArgument(new double[]
        {
            2, -2, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 107) * E * E;
        sumB += -2235 * sine(LM) + 382 * sine(A3) + 175 * sine(A1 - F) + 175 * sine(A1 + F) + 127 * sine(LM - MM) - 115 * sine(LM + MM);
        return new SexagesimalDegree(sumB / 1000000);
    }

    /**
     *
     * @param julianDay Julian day
     * @return Apparent position of the Moon in the Ecuatorial Coordinate System
     * @throws ProcessException Format error
     */
    public static Equatorial getPosicionAparente(JulianDay julianDay) throws ProcessException
    {
        double LM = getMeanLongitude(julianDay).getValue();
        double D = getMeanElongation(julianDay).getValue();
        double T = julianDay.getCenturiesSince2000();
        double M = new SexagesimalDegree(357.5291092 + 35999.0502909 * T - 0.0001536 * pow(T, 2) + pow(T, 3) / 24490000).getValue();
        double MM = getMeanAnomaly(julianDay).getValue();
        double F = getArgumentOfLatitude(julianDay).getValue();
        double A1 = new SexagesimalDegree(119.75 + 131.849 * T).getValue();
        double A2 = new SexagesimalDegree(53.09 + 479264.290 * T).getValue();
        double A3 = new SexagesimalDegree(313.45 + 481266.484 * T).getValue();
        double E = 1 - 0.002516 * T - 0.0000074 * pow(T, 2);

        SexagesimalDegree longitude = calculateLongitude(julianDay, D, M, MM, F, E, LM, A1, A2);
        double distance = calculateDistance(julianDay, D, M, MM, F, E, LM, A1, A2);
        SexagesimalDegree latitude = calculateLatitude(julianDay, D, M, MM, F, E, LM, A1, A2, A3);

        return new Ecliptic(longitude, latitude, distance).toEquatorial(julianDay.getTrueObliquityEcliptic(CalculusType.PRECISE));

    }

    /**
     *
     * @param julianDay Julian day
     * @return Moon's position and its fraction illuminated
     * @throws ProcessException Format error
     */
    public static InfoMoon getInfoLuna(JulianDay julianDay) throws ProcessException
    {
        double LM = getMeanLongitude(julianDay).getValue();
        double D = getMeanElongation(julianDay).getValue();
        double T = julianDay.getCenturiesSince2000();
        double M = new SexagesimalDegree(357.5291092 + 35999.0502909 * T - 0.0001536 * pow(T, 2) + pow(T, 3) / 24490000).getValue();
        double MM = getMeanAnomaly(julianDay).getValue();
        double F = getArgumentOfLatitude(julianDay).getValue();
        double A1 = new SexagesimalDegree(119.75 + 131.849 * T).getValue();
        double A2 = new SexagesimalDegree(53.09 + 479264.290 * T).getValue();
        double A3 = new SexagesimalDegree(313.45 + 481266.484 * T).getValue();
        double E = 1 - 0.002516 * T - 0.0000074 * pow(T, 2);

        double distance = calculateDistance(julianDay, D, M, MM, F, E, LM, A1, A2);
        SexagesimalDegree longitud = calculateLongitude(julianDay, D, M, MM, F, E, LM, A1, A2);
        SexagesimalDegree latitude = calculateLatitude(julianDay, D, M, MM, F, E, LM, A1, A2, A3);

        SexagesimalDegree longitudSol = Heliocientric.getLongitude(EARTH, julianDay).plus(new SexagesimalDegree(180));
        double R = Heliocientric.getRadius(EARTH, julianDay);

        SexagesimalDegree elongacionGeocentrica = SexagesimalDegree.acos(cosine(latitude) * cosine(longitud.minus(longitudSol)));

        SexagesimalDegree angulosFase = SexagesimalDegree.atan2(R * sine(elongacionGeocentrica), distance - R * cosine(elongacionGeocentrica));

        double k = (1 + cosine(angulosFase)) / 2;

        return new InfoMoon(new Ecliptic(longitud, latitude, distance).toEquatorial(julianDay.getTrueObliquityEcliptic(CalculusType.PRECISE)), k);

    }

}
