/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.ProcessException;
import com.astronomy.coordinate_system.Equatorial;
import com.StarEnum;
import static com.StarEnum.BETA_CRUCIS;
import com.PlanetEnum;
import com.units.SexagesimalDegree;
import com.units.HourAngle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Catalogue of stars and planets
 *
 * @author MIGUEL_ANGEL
 */
public class Catalogue
{

    /**
     *
     * @param star Star enum item
     * @return Star object
     *
     * @throws ProcessException Format error
     */
    public static Star getStar(StarEnum star) throws ProcessException
    {
        Equatorial coord;
        Equatorial mp;
        switch (star)
        {
            case ALPHA_AQUILAE:
                coord = new Equatorial(SexagesimalDegree.valueOf("8º52'05.9563''"), HourAngle.valueOf("19:50:46.99855"));
                mp = new Equatorial(new SexagesimalDegree(385.29 / 3600000), new HourAngle(536.23 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.77);
            case ALPHA_AURIGAE:
                coord = new Equatorial(SexagesimalDegree.valueOf("45º59'52.768''"), HourAngle.valueOf("5:16:41.3591"));
                mp = new Equatorial(new SexagesimalDegree(-427.11 / 3600000), new HourAngle(75.52 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.91);
            case ALPHA_BOOTIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("19º10'56.7''"), HourAngle.valueOf("14:15:39.67"));
                mp = new Equatorial(new SexagesimalDegree(-1999.4 / 3600000), new HourAngle(-1093.45 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, -0.04);
            case ALPHA_CANIS_MAJORIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-16º42'58.017''"), HourAngle.valueOf("06:45:09.9173"));
                mp = new Equatorial(new SexagesimalDegree(-1223.14 / 3600000), new HourAngle(-546.05 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, -1.47);
            case ALPHA_CANIS_MINORIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("5º13'29.9552''"), HourAngle.valueOf("7:39:18.11950"));
                mp = new Equatorial(new SexagesimalDegree(-1036.8 / 3600000), new HourAngle(-714.590 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.34);
            case ALPHA_CASSIOPEIA:
                coord = new Equatorial(SexagesimalDegree.valueOf("56º32'14.392''"), HourAngle.valueOf("00:40:30.4405"));
                mp = new Equatorial(new SexagesimalDegree(-32.77 / 3600000), new HourAngle(50.36 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.24);
            case ALPHA_CENTAURI:
                coord = new Equatorial(SexagesimalDegree.valueOf("-60º50'02.308''"), HourAngle.valueOf("14:39:36.4951"));
                mp = new Equatorial(new SexagesimalDegree(699 / 3600000), new HourAngle(-3642 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, -0.01);
            case ALPHA_CYGNI:
                coord = new Equatorial(SexagesimalDegree.valueOf("45º16'49''"), HourAngle.valueOf("20:41:25.9"));
                mp = new Equatorial(new SexagesimalDegree(1.95 / 3600000), new HourAngle(1.99 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.25);
            case ALPHA_CORONAE:
                coord = new Equatorial(SexagesimalDegree.valueOf("26º42'52.89''"), HourAngle.valueOf("15:34:41.268"));
                mp = new Equatorial(new SexagesimalDegree(-89.58 / 3600000), new HourAngle(-120.27 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.23);
            case ALPHA_CRUCIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-63º05'56.7343''"), HourAngle.valueOf("12:26:35.89522"));
                mp = new Equatorial(new SexagesimalDegree(-14.86 / 3600000), new HourAngle(-35.83 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.77);
            case ALPHA_GEMINORUM:
                coord = new Equatorial(SexagesimalDegree.valueOf("31º53'17.8160''"), HourAngle.valueOf("07:34:35.87319"));
                mp = new Equatorial(new SexagesimalDegree(-145.19 / 3600000), new HourAngle(-191.45 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.93);
            case ALPHA_LYRAE:
                coord = new Equatorial(SexagesimalDegree.valueOf("38º47'01.2802''"), HourAngle.valueOf("18:36:56.33635"));
                mp = new Equatorial(new SexagesimalDegree(286.23 / 3600000), new HourAngle(200.94 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.03);
            case ALPHA_ORIONIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("7º24'25.426''"), HourAngle.valueOf("5:55:10.30530"));
                mp = new Equatorial(new SexagesimalDegree(9.56 / 3600000), new HourAngle(24.95 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.42);
            case ALPHA_PISCIS_AUSTRINI:
                coord = new Equatorial(SexagesimalDegree.valueOf("-29º37'20.050''"), HourAngle.valueOf("22:57:39.0465"));
                mp = new Equatorial(new SexagesimalDegree(-158.98 / 3600000), new HourAngle(-331.11 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.16);
            case ALPHA_SCORPII:
                coord = new Equatorial(SexagesimalDegree.valueOf("-26º25'55.2094''"), HourAngle.valueOf("16:29:24.45970"));
                mp = new Equatorial(new SexagesimalDegree(-23.30 / 3600000), new HourAngle(-12.11 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.16);
            case ALPHA_TAURI:
                coord = new Equatorial(SexagesimalDegree.valueOf("16º30'33.49''"), HourAngle.valueOf("04:35:55.239"));
                mp = new Equatorial(new SexagesimalDegree(-189.35 / 3600000), new HourAngle(62.78 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.75);
            case ALPHA_VIRGINIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-11º09'40.75''"), HourAngle.valueOf("13:25:11.579"));
                mp = new Equatorial(new SexagesimalDegree(-30.65 / 3600000), new HourAngle(-42.35 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.04);
            case BETA_CASSIOPEIA:
                coord = new Equatorial(SexagesimalDegree.valueOf("59º08'59.2120''"), HourAngle.valueOf("00:09:10.68518"));
                mp = new Equatorial(new SexagesimalDegree(-179.77 / 3600000), new HourAngle(523.50 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.28);
            case BETA_CENTAURI:
                coord = new Equatorial(SexagesimalDegree.valueOf("-60º22'22.9266''"), HourAngle.valueOf("14:03:49.40535"));
                mp = new Equatorial(new SexagesimalDegree(-23.16 / 3600000), new HourAngle(-33.27 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.6);
            case BETA_CRUCIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-59º41'19.5792''"), HourAngle.valueOf("12:47:43.26877"));
                mp = new Equatorial(new SexagesimalDegree(-16.185 / 3600000), new HourAngle(-42.97 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.25);
            case BETA_GEMINORUM:
                coord = new Equatorial(SexagesimalDegree.valueOf("28º01'34.3160''"), HourAngle.valueOf("07:45:18.94987"));
                mp = new Equatorial(new SexagesimalDegree(-45.8 / 3600000), new HourAngle(-626.55 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.14);
            case BETA_ORIONIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-8º12'05.8981''"), HourAngle.valueOf("05:14:32.27210"));
                mp = new Equatorial(new SexagesimalDegree(0.5 / 3600000), new HourAngle(1.31 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 0.13);
            case DELTA_CRUCIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-58º44'56.1369''"), HourAngle.valueOf("12:15:08.71673"));
                mp = new Equatorial(new SexagesimalDegree(-10.36 / 3600000), new HourAngle(-35.81 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.79);
            case DELTA_ORIONIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-0º17'56.7424''"), HourAngle.valueOf("05:32:00.40009"));
                mp = new Equatorial(new SexagesimalDegree(-0.69 / 3600000), new HourAngle(0.64 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.23);
            case EPSILON_ORIONIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-0.1º12'06.90''"), HourAngle.valueOf("05:36:12.81"));
                mp = new Equatorial(new SexagesimalDegree(-1.06 / 3600000), new HourAngle(1.49 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.70);
            case ETA_TAURI:
                coord = new Equatorial(SexagesimalDegree.valueOf("24º6'18.49''"), HourAngle.valueOf("03:47:29.077"));
                mp = new Equatorial(new SexagesimalDegree(-43.67 / 3600000), new HourAngle(19.34 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 2.873);
            case ETA_URSAE_MAJORIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("49º18'48''"), HourAngle.valueOf("13:47:32.4"));
                mp = new Equatorial(new SexagesimalDegree(14.91 / 3600000), new HourAngle(121.17 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.85);
            case GAMMA_CRUCIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-57º06'47.5684''"), HourAngle.valueOf("12:31:09.95961"));
                mp = new Equatorial(new SexagesimalDegree(-265.08 / 3600000), new HourAngle(-28.23 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.63);
            case PLEYADES:
                coord = new Equatorial(SexagesimalDegree.valueOf("24º7'"), HourAngle.valueOf("03:47:24"));
                mp = new Equatorial(new SexagesimalDegree(0 / 3600000), new HourAngle(0 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.60);
            case ZETA_ORIONIS:
                coord = new Equatorial(SexagesimalDegree.valueOf("-1º56'34.2649''"), HourAngle.valueOf("05:40:45.52666"));
                mp = new Equatorial(new SexagesimalDegree(2.03 / 3600000), new HourAngle(3.19 / (3600000 * 15)));
                return new Star(star.toString(), coord, mp, 1.77);
            default:
                return null;
        }
    }

    /**
     *
     * @return Array of star names
     */
    public static String[] getStarStrings()
    {
        return Arrays.asList(StarEnum.values()).stream().sorted((StarEnum o1, StarEnum o2) -> o1.toString().compareTo(o2.toString())).map(s -> s.toString()).toArray(s -> new String[s]);
    }

    /**
     *
     * @param name Star name
     * @return Staer enum item
     */
    public static StarEnum getEnumEstrella(String name)
    {
        for (StarEnum star : StarEnum.values())
        {
            if (name.equals(star.toString()))
            {
                return star;
            }
        }
        return null;
    }

    /**
     *
     * @param name Planet name
     * @return Planet enumeration item
     */
    public static PlanetEnum getPlanetEnum(String name)
    {
        for (PlanetEnum planeta : PlanetEnum.values())
        {
            if (name.equals(planeta.toString()))
            {
                return planeta;
            }
        }
        return null;
    }
}
