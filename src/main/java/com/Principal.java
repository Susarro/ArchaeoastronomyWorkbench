/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.astronomy.JulianDay;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Principal
{

    public Principal() throws ProcessException
    {
        double d=JulianDay.valueOf("1/1/2000 12:00").getValue();
        //Ecuatorial coord = new Ecuatorial(SexagesimalDegree.valueOf("49º13'42.48''"), HourAngle.valueOf("2:44:11.986"));
        //Ecuatorial mp = new Ecuatorial(new SexagesimalDegree(-89.5 / 3600000), new HourAngle(34.25 / 3600000));
        //Star estrella = new Star("Polar", coord, mp, 0.77);
        
        //Ecuatorial eq=estrella.Precession(new JulianDay(2462088.69), CalculusType.PRECISE);

      
    }

    public static void main(String args[]) throws ProcessException
    {
        new Principal();

    }
}
