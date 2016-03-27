/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy;

/**
 * Calendar date
 * @author MIGUEL_ANGEL
 */
public class CalendarDate
{

    private int day;
    private int month;
    private int year;

    @Override
    public String toString()
    {
        return String.format("%d/%d/%d", day, month, year);
    }

    /**
     * @return Day
     */
    public int getDay()
    {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day)
    {
        this.day = day;
    }

    /**
     * @return the month
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month)
    {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year)
    {
        this.year = year;
    }
}
