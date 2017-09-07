/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calendar;

import com.ProcessException;
import com.astronomy.JulianDay;
import com.astronomy.Solstice;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class CrossQuarterFestivals
{

    /**
     * Cross-quarter festival between winter solstice and apparent spring equinox
     */
    public JulianDay imbolc;
    /**
     * Cross-quarter festival between apparent spring equinox and summer solstice
     */
    public JulianDay beltaine;
    /**
     * Cross-quarter festival between summer solstice and apparent autumn equinox
     */
    public JulianDay lugnasad;
    /**
     * Cross-quarter festival between apparent autumn equinox and winter solstice
     */
    public JulianDay samain;
    /**
     * Midpoint between winter solstice and summer solstice
     */
    public JulianDay apparentSpringEquinox;
    /**
     * Midpoint between summer solstice and winter solstice
     */
    public JulianDay apparentAutumnEquinox;
    /**
     * Summer solstice
     */
    public JulianDay summerSolstice;
    /**
     * Winter solstice
     */
    public JulianDay winterSolstice;

    /**
     * 
     * @param year Year 
     * @throws ProcessException Format error
     */
    public CrossQuarterFestivals(int year) throws ProcessException
    {

        Solstice s = new Solstice(year);
        winterSolstice=new JulianDay(s.getWinterSolstice());
        summerSolstice=new JulianDay(s.getSummerSolstice());

        if (s.getSummerSolstice().isBefore(s.getWinterSolstice()))
        {
            apparentSpringEquinox = new JulianDay((winterSolstice.getValue() + summerSolstice.getValue()-365.25) / 2);
            apparentAutumnEquinox = new JulianDay((summerSolstice.getValue() + winterSolstice.getValue()) / 2);
            imbolc = new JulianDay((winterSolstice.getValue() + apparentSpringEquinox.getValue() -365.25) / 2);
            beltaine = new JulianDay((apparentSpringEquinox.getValue() + summerSolstice.getValue()) / 2);
            lugnasad = new JulianDay((summerSolstice.getValue() + apparentAutumnEquinox.getValue()) / 2);
            samain = new JulianDay((apparentAutumnEquinox.getValue() + s.getWinterSolstice().getValue()) / 2);

        }
        else
        {
            apparentSpringEquinox = new JulianDay((winterSolstice.getValue() + summerSolstice.getValue()) / 2);
            apparentAutumnEquinox = new JulianDay((summerSolstice.getValue() + winterSolstice.getValue()+365.25) / 2);
            imbolc = new JulianDay((winterSolstice.getValue() + apparentSpringEquinox.getValue()) / 2);
            beltaine = new JulianDay((apparentSpringEquinox.getValue() + summerSolstice.getValue()) / 2);
            lugnasad = new JulianDay((summerSolstice.getValue() + apparentAutumnEquinox.getValue()) / 2);
            samain = new JulianDay((apparentAutumnEquinox.getValue() + winterSolstice.getValue()+365.25) / 2);
        }

    }

}
