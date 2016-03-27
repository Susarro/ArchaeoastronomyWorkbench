/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calendario;

import com.ProcessException;
import com.astronomy.JulianDay;
import com.astronomy.Solstice;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class FiestasMediaEstacion
{

    public JulianDay imbolc;
    public JulianDay beltaine;
    public JulianDay lugnasad;
    public JulianDay samain;
    public JulianDay equinoccioPrimavera;
    public JulianDay equinoccioOtoño;
    public JulianDay solsticioVerano;
    public JulianDay solsticioInvierno;

    public FiestasMediaEstacion(int año) throws ProcessException
    {

        Solstice s = new Solstice(año);
        solsticioInvierno=new JulianDay(s.getWinterSolstice());
        solsticioVerano=new JulianDay(s.getSummerSolstice());

        if (s.getSummerSolstice().isBefore(s.getWinterSolstice()))
        {
            Solstice s1 = new Solstice(año - 1);
            equinoccioPrimavera = new JulianDay((s1.getWinterSolstice().getValue() + s.getSummerSolstice().getValue()) / 2);
            equinoccioOtoño = new JulianDay((s.getSummerSolstice().getValue() + s.getWinterSolstice().getValue()) / 2);
            imbolc = new JulianDay((s1.getWinterSolstice().getValue() + equinoccioPrimavera.getValue()) / 2);
            beltaine = new JulianDay((equinoccioPrimavera.getValue() + s.getSummerSolstice().getValue()) / 2);
            lugnasad = new JulianDay((s.getSummerSolstice().getValue() + equinoccioOtoño.getValue()) / 2);
            samain = new JulianDay((equinoccioOtoño.getValue() + s.getWinterSolstice().getValue()) / 2);

        }
        else
        {
            Solstice s2 = new Solstice(año + 1);
            equinoccioPrimavera = new JulianDay((s.getWinterSolstice().getValue() + s.getSummerSolstice().getValue()) / 2);
            equinoccioOtoño = new JulianDay((s.getSummerSolstice().getValue() + s2.getWinterSolstice().getValue()) / 2);
            imbolc = new JulianDay((s.getWinterSolstice().getValue() + equinoccioPrimavera.getValue()) / 2);
            beltaine = new JulianDay((equinoccioPrimavera.getValue() + s.getSummerSolstice().getValue()) / 2);
            lugnasad = new JulianDay((s.getSummerSolstice().getValue() + equinoccioOtoño.getValue()) / 2);
            samain = new JulianDay((equinoccioOtoño.getValue() + s2.getWinterSolstice().getValue()) / 2);
        }

    }

}
