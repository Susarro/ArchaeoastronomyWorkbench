/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

import com.ProcessException;
import com.CalculusType;
import com.units.SexagesimalDegree;
import static com.units.Tools.coefficientOfCosineOfArgument;
import static com.units.Tools.coefficientOfSineOfArgument;
import static com.units.Tools.cosine;
import com.units.HourAngle;
import static java.lang.Math.pow;

/**
 * Julian date
 *
 * @author MIGUEL_ANGEL
 */
public class JulianDay
{

    /**
     * Julian day as double
     */
    double julianDate;

    /**
     *
     * @param date Julian day
     * @return Current value plus date
     */
    public JulianDay plus(JulianDay date)
    {
        return new JulianDay(this.julianDate + date.julianDate);
    }

    /**
     *
     * @param date Julian date
     * @return Current value minus date
     */
    public JulianDay minus(JulianDay date)
    {
        return new JulianDay(this.julianDate - date.julianDate);
    }

    /**
     *
     * @param date Julian date
     * @return True if current value is before date
     */
    public boolean isBefore(JulianDay date)
    {
        return this.julianDate < date.julianDate;
    }

    /**
     *
     * @param date Julian date
     * @return True if current value is after date
     */
    public boolean isAfter(JulianDay date)
    {
        return this.julianDate > date.julianDate;
    }

    /**
     *
     * @return Julian day as double
     */
    public double getValue()
    {
        return julianDate;
    }

    /**
     * Constructor
     *
     * @param value Julian day as double
     */
    public JulianDay(double value)
    {
        this.julianDate = value;
    }

    /**
     * Constructor
     *
     * @param date Julian day
     */
    public JulianDay(JulianDay date)
    {
        this.julianDate = date.julianDate;
    }

    /**
     * Constructor from day, month, year and universal time
     * @param day Day
     * @param month Month
     * @param year Year ( 100 B.C. = 99)
     * @param hours Universal time
     */
    public JulianDay(double day, double month, double year, UniversalTime hours)
    {
        double d;
        if (month == 1 || month == 2)
        {
            year = year - 1;
            month = month + 12;
        }

        int a = (int) (year / 100.0);
        int b = 0;
        if (year > 1582)
        {
            b = 2 - a + (int) (a / 4);
        }
        else if (year == 1582)
        {
            if (month > 10)
            {
                b = 2 - a + (int) (a / 4);
            }
        }

        d = (int) (365.25 * (year + 4716)) + (int) (30.6001 * (month + 1)) + day + b - 1524.5;

        julianDate = d + hours.getValue() / 24.0;
    }

    /**
     * Constructor from day, month, year. Universal time = 0
     * @param day Day
     * @param month Month
     * @param year Year ( 100 B.C. = 99)
     */
    public JulianDay(double day, double month, double year)
    {
        this(day, month, year, new UniversalTime(0));
    }

    /**
     * 
     * @return Jualian day as a calendar date (day, month, year, universal time)
     */
    public CalendarDate getSimpleDate()
    {
        CalendarDate date = new CalendarDate();

        int iZ = (int) (julianDate + 0.5);
        double iF = julianDate + 0.5 - iZ;
        int iA;
        if (iZ < 2299161)
        {
            iA = iZ;
        }
        else
        {
            int temp = (int) ((iZ - 1867216.25) / 36524.25);
            iA = iZ + 1 + temp - (int) (temp / 4);
        }
        int iB = iA + 1524;
        int iC = (int) ((iB - 122.1) / 365.25);
        int iD = (int) (365.25 * iC);
        int iE = (int) ((iB - iD) / 30.6001);
        int day = (int) (iB - iD - (int) (30.6001 * iE) + iF);
        int month = 0;
        int year = 0;
        if (iE < 14)
        {
            month = iE - 1;
        }
        else if (iE == 14 || iE == 15)
        {
            month = iE - 13;
        }
        if (month > 2)
        {
            year = iC - 4716;
        }
        else if (month == 1 || month == 2)
        {
            year = iC - 4715;
        }
        date.setDay(day);
        date.setYear(year);
        date.setMonth(month);
        HourAngle h = getUniversalTime();

        return date;
    }

    /**
     * 
     * @return Julian day formatted as dd/MM/yyyy HH:mm:ss.SSS string
     */
    @Override
    public String toString()
    {
        CalendarDate date = getSimpleDate();
        return date.toString() + " " + getUniversalTime().toString();
    }

    /**
     * 
     * @return Universal time
     */
    public HourAngle getUniversalTime()
    {
        return new HourAngle((this.getValue() + 0.5 - (int) (this.getValue() + 0.5)) * 24.0);
    }

    /**
     * 
     * @param string Julian day formatted as dd/MM/yyyy HH:mm:ss.SSS string
     * @return Julian day
     * @throws ProcessException Format error in string
     */
    public static JulianDay valueOf(String string) throws ProcessException
    {
        double day, month, year, UT;
        try
        {
            String str = string.replace(",", ".");
            String[] t = str.split(" ");
            String f = t[0];
            String h;

            if (t.length == 2)
            {
                h = t[1];
            }
            else
            {
                h = "00:00:00";
            }
            String[] ff = f.split("/");
            String[] hh = h.split(":");

            if (ff.length == 3)
            {
                day = Integer.valueOf(ff[0]);
                month = Integer.valueOf(ff[1]);
                year = Integer.valueOf(ff[2]);
            }
            else
            {
                throw new ProcessException("JulianDay: Format error in " + string);
            }

            if (hh.length == 1)
            {
                UT = Integer.valueOf(hh[0]);

            }
            else if (hh.length == 2)
            {
                UT = Integer.valueOf(hh[0]) + Integer.valueOf(hh[1]) / 60.0;

            }
            else if (hh.length == 3)
            {
                UT = Integer.valueOf(hh[0]) + Integer.valueOf(hh[1]) / 60.0 + Double.valueOf(hh[2]) / 3600.0;

            }
            else
            {
                throw new ProcessException("JulianDay: Format error in " + string);
            }
        }
        catch (NumberFormatException ex)
        {
            throw new ProcessException("JulianDay: Format error in " + string);
        }
        return new JulianDay(day, month, year, new UniversalTime(UT));

    }

    /**
     * 
     * @return Number of centuries since 2000
     */
    public double getCenturiesSince2000()
    {      
        double d = julianDate;
        return (d - 2451545) / 36525;
    }

    /**
     * 
     * @param jd Julian day as double
     * @return Number of centuries since 2000
     */
    static public double getCenturiesSince2000(double jd)
    {
        return (jd - 2451545) / 36525;
    }

 
    /**
     * @return Mean Sidereal Time at Greenwich at 0h UT
     * @throws ProcessException Format error in string
     */
    public HourAngle getMeanSiderealTimeAtGreenwichAt0UT() throws ProcessException
    {
        double T = JulianDay.getCenturiesSince2000(julianDate - getUniversalTime().getValue() / 24);
        HourAngle mst = HourAngle.valueOf("6:41:50.54841");
        double ss = 8640184.812866 * T + 0.093104 * pow(T, 2) - 0.0000062 * pow(T, 3);
        return new HourAngle(mst.getValue() + ss / 3600);
    }
    
    /**
     * @return Mean Sidereal Time at Greenwich
     * @throws ProcessException Format error in string
     */
    public HourAngle getMeanSiderealTimeAtGreenwich() throws ProcessException
    {
        return new HourAngle(getMeanSiderealTimeAtGreenwichAt0UT().getValue() + getUniversalTime().getValue()*1.00273790935);
    }

    /**
     * 
     * @return Nutation in longitude
     * @throws ProcessException Format error in string
     */
    public SexagesimalDegree getNutationInLongitude() throws ProcessException
    {
        double T = getCenturiesSince2000();
        //Longitude of the ascending node of the Moon's mean orbit on the ecliptic, measured from the mean equinox of the date
        double omega = 125.04452 - 1934.136261 * T + 0.0020708 * pow(T, 2) + pow(T, 3) / 450000;
        //Mean longitude of the Sun
        SexagesimalDegree LS = new SexagesimalDegree(280.4665 + 36000.7698 * T);
        //Mean longitude of the Moon
        SexagesimalDegree LM = new SexagesimalDegree(218.3165 + 481267.8813 * T);
        //Mean elongation of the Moon from the Sun
        double D = 297.85036 + 445267.111480 * T - 0.001914 * pow(T, 2) + pow(T, 3) / 189474;
        //Mean anomaly of the Sun (Earth)
        double MS = 357.52772 + 35999.050340 * T - 0.0001603 * pow(T, 2) - pow(T, 3) / 300000;
        //Mean anomaly of the Moon
        double MM = 134.96298 + 477198.867398 * T + 0.0086972 * pow(T, 2) + pow(T, 3) / 56250;
        //Moon's argument of latitude
        double F = 93.27191 + 483202.017538 * T - 0.0036825 * pow(T, 2) + pow(T, 3) / 327270;

        double nl = 0;
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -171996 - 174.2 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -13187 - 1.6 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -2274 - 0.2 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 0, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 2062 + 0.2 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 1426 - 3.4 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 712 + 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -517 + 1.2 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -386 - 0.4 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -301));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 217 - 0.5 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -158));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 129 + 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 123));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 63));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 63 + 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -59));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -58 - 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -51));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 2, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 48));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, -2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 46));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -38));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -31));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 29));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 29));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 0, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 26));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 0, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -22));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 21));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 2, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 17 - 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 16));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 2, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -16 + 0.1 * T));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -15));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -13));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, -1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -12));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, -2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 11));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -10));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -8));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, -2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -6));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -6));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, -1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, -1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 0, 2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, -2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -1, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -2, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            1, 0, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 1, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, -2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            -1, -1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, -1, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, -1, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            0, 0, 3, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfSineOfArgument(new double[]
        {
            2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = nl / 10000;

        return new SexagesimalDegree(nl / 3600);
    }

    /**
     * 
     * @param obliquity True obliquity of the ecliptic
     * @return Apparrent Sidereal Time at Greenwich
     * @throws ProcessException Format error in string 
     */
    public SiderealTime getApparrentSiderealTimeAtGreenwich(SexagesimalDegree obliquity) throws ProcessException
    {
        double hs = this.getUniversalTime().getValue() * 1.00273790935 + getMeanSiderealTimeAtGreenwichAt0UT().getValue();
        double corr = getNutationInLongitude().getValue();
        if (corr > 180)
        {
            corr = -(360 - corr);
        }
        corr = corr * cosine(obliquity) / 15;
        return new SiderealTime(hs + corr);
    }

    /**
     * 
     * @return Nutation in obliquity
     * @throws ProcessException Format error in string
     */
    public SexagesimalDegree getNutationInObliquity() throws ProcessException
    {
        double T = getCenturiesSince2000();
        //Longitude of the ascending node of the Moon's mean orbit on the ecliptic, measured from the mean equinox of the date
        double omega = 125.04452 - 1934.136261 * T + 0.0020708 * pow(T, 2) + pow(T, 3) / 450000;
        //Mean longitude of the Sun
        SexagesimalDegree LS = new SexagesimalDegree(280.4665 + 36000.7698 * T);
        //Mean longitude of the Moon
        SexagesimalDegree LM = new SexagesimalDegree(218.3165 + 481267.8813 * T);
        //Mean elongation of the Moon from the Sun
        double D = 297.85036 + 445267.111480 * T - 0.001914 * pow(T, 2) + pow(T, 3) / 189474;
        //Mean anomaly of the Sun (Earth)
        double MS = 357.52772 + 35999.050340 * T - 0.0001603 * pow(T, 2) - pow(T, 3) / 300000;
        //Mean anomaly of the Moon
        double MM = 134.96298 + 477198.867398 * T + 0.0086972 * pow(T, 2) + pow(T, 3) / 56250;
        //Moon's argument of latitude
        double F = 93.27191 + 483202.017538 * T - 0.0036825 * pow(T, 2) + pow(T, 3) / 327270;

        double nl = 0;
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 92025 + 8.9 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5736 - 3.1 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 977 - 0.5 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 0, 0, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -895 + 0.5 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 54 - 0.1 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 224 - 0.6 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 200));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 129 - 0.1 * T));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -95 + 0.3 * T));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -70));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -53));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -33));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 26));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 32));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 27));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, -2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -24));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 16));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 13));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -12));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -10));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -8));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 2, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 9));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, -1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, -2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, -1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            -2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + coefficientOfCosineOfArgument(new double[]
        {
            0, 0, 2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = nl / 10000;

        return new SexagesimalDegree(nl / 3600);
    }

    /**
     * 
     * @param option APPROXIMATE or PRECISE 
     * @return Mean obliquity of the ecliptic
     * @throws ProcessException Format error in string
     */
    public SexagesimalDegree getMeanObliquityEcliptic(CalculusType option) throws ProcessException
    {
        double T = getCenturiesSince2000();
        double a;
        if (option == CalculusType.PRECISE)
        {
            double U = T / 100;
            a = -4680.93 * U - 1.55 * pow(U, 2) + 1999.25 * pow(U, 3) - 51.38 * pow(U, 4) - 249.67 * pow(U, 5) - 39.05 * pow(U, 6) + 7.12 * pow(U, 7) + 27.87 * pow(U, 8) + 5.79 * pow(U, 9) + 2.45 * pow(U, 10);
        }
        else
        {
            a = -46.8150 * T - 0.00059 * pow(T, 2) + 0.001813 * pow(T, 3);
        }

        return SexagesimalDegree.valueOf("23º26'21.448''").plus(new SexagesimalDegree(a / 3600));
    }

    /**
     * 
     * @param option APPROXIMATE or PRECISE
     * @return True obliquity of the ecliptic
     * @throws ProcessException Format error in string 
     */
    public SexagesimalDegree getTrueObliquityEcliptic(CalculusType option) throws ProcessException
    {
        SexagesimalDegree oe = getMeanObliquityEcliptic(option);
        SexagesimalDegree noe = getNutationInObliquity();
        return oe.plus(noe);
    }

    /**
     * 
     * @return Eccentricity of Earth's orbit
     */
    public SexagesimalDegree getEccentricityOfEarthOrbit()
    {
        double T = getCenturiesSince2000();
        double e = 0.016708617 - 0.000042037 * T - 0.0000001236 * pow(T, 2);
        return new SexagesimalDegree(e);
    }

    /**
     * 
     * @return Longitude of perihelion of Earth's orbit 
     */
    public SexagesimalDegree getLongitudeOfPerihelionOfEarthOrbit()
    {
        double T = getCenturiesSince2000();
        double pi = 102.93735 + 1.71953 * T + 0.00046 * pow(T, 2);
        return new SexagesimalDegree(pi);
    }

}
