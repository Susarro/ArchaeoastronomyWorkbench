/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.astronomy.JulianDay;

/**
 * Temporal task using Julian dates
 *
 * @author MIGUEL_ANGEL
 */
public abstract class TemporalTask extends TemporalTaskTemplate<JulianDay>
{

    /**
     * @param name Task name
     * @param start Start time
     * @param end End time
     * @param increment Increment time
     */
    public TemporalTask(String name, JulianDay start, JulianDay end, double increment)
    {
        super(name, start, end, new JulianDay(increment));
    }

    /**
     *
     * @param input Time
     * @return Time as double
     */
    @Override
    public double doubleValue(JulianDay input)
    {
        return input.getValue();
    }

    /**
     *
     * @param input Time
     * @return Time as string
     */
    @Override
    public String toString(JulianDay input)
    {
        return input.toString();
    }

    /**
     *
     * @param input Time to clone as current time
     */
    @Override
    public void cloneCurrent(JulianDay input)
    {
        setCurrent(new JulianDay(input.getValue()));
    }

    /**
     *
     * @param increment Time as double to add to current time
     */
    public void addToCurrent(double increment)
    {
        setCurrent(new JulianDay(getCurrent().getValue() + increment));
    }

    /**
     *
     * @param increment Time to add to current time
     */
    @Override
    public void addToCurrent(JulianDay increment)
    {
        TemporalTask.this.addToCurrent(increment.getValue());
    }

    /**
     * Comapare times
     *
     * @param time_1 Julian day 1
     * @param time_2 Julian day 2
     * @return a negative integer, zero, or a positive integer as time_1 is less
     * than, equal to, or greater than time_2
     */
    @Override
    public int compare(JulianDay time_1, JulianDay time_2)
    {
        if (time_1.isBefore(time_2))
        {
            return -1;
        }
        else if (time_1.isAfter(time_2))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Task end
     */
    @Override
    abstract public void taskEnd();

    /**
     * Cyclic process
     */
    @Override
    abstract public void cycle();

}
