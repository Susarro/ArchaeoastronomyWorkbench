/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.units.SexagesimalDegree;

/**
 * Astronomical event enumeration
 * 
 * @author MIGUEL_ANGEL
 */
enum AstronomicalEvent
{

    NONE("None"),
    WINTER_SOLSTICE("Winter solstice"),
    SUMMER_SOLSTICE("Summer solstice"),
    IMBOLC_SAMAIN("Imbolc/Samain"),
    BELTAINE_LUGNASAD("Beltaine/Lugnasad"),
    SOUTHERN_LUNAR_MAJOR_STANDSTILL("Southern Lunar Major Standstill"),
    NORTHERN_LUNAR_MAJOR_STANDSTILL("Northern Lunar Major Standstill"),
    SOUTHERN_LUNAR_MINOR_STANDSTILL("Southern Lunar Minor Standstill"),
    NORTHERN_LUNAR_MINOR_STANDSTILL("Northern Lunar Minor Standstill");

    String name;
    SexagesimalDegree declination;

    AstronomicalEvent(String name)
    {
        this.name = name;
    }

    /**
     * 
     * @return astronomical event name
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     * 
     * @param name Astronomical event name
     * @return Astronomical event enumeration element
     */
    public static AstronomicalEvent fromString(String name)
    {
        if (name != null)
        {
            for (AstronomicalEvent b : AstronomicalEvent.values())
            {
                if (name.equalsIgnoreCase(b.name))
                {
                    return b;
                }
            }
        }
        return null;
    }

    
    /**
     * 
     * @param d Declination to set
     */
    public void setDeclination(SexagesimalDegree d)
    {
        declination = d;
    }

    /**
     * 
     * @return declination
     */
    public SexagesimalDegree getDeclination()
    {
        return declination;
    }

}
