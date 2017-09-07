/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

/**
 * Dated astronomical event
 *
 * @author MIGUEL_ANGEL
 */
public class DatedAstronomicalEvent
{

    private final AstronomicalEvent event;
    private final int anno;

    /**
     *
     * @param event astronomical event
     * @param year Year
     */
    public DatedAstronomicalEvent(AstronomicalEvent event, int year)
    {
        this.event = event;
        this.anno = year;
    }

    /**
     * @return astronomical event
     */
    public AstronomicalEvent getAstronomicalEvent()
    {
        return event;
    }

    /**
     * @return year
     */
    public int getYear()
    {
        return anno;
    }
}
