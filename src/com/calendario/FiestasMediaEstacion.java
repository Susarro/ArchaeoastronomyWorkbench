/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calendario;

import com.Excepcion;
import com.astronomia.DiaJuliano;
import com.astronomia.Solsticio;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class FiestasMediaEstacion
{

    public DiaJuliano imbolc;
    public DiaJuliano beltaine;
    public DiaJuliano lugnasad;
    public DiaJuliano samain;
    public DiaJuliano equinoccioPrimavera;
    public DiaJuliano equinoccioOtoño;
    public DiaJuliano solsticioVerano;
    public DiaJuliano solsticioInvierno;

    public FiestasMediaEstacion(int año) throws Excepcion
    {

        Solsticio s = new Solsticio(año);
        solsticioInvierno=new DiaJuliano(s.invierno);
        solsticioVerano=new DiaJuliano(s.verano);

        if (s.verano.isBefore(s.invierno))
        {
            Solsticio s1 = new Solsticio(año - 1);
            equinoccioPrimavera = new DiaJuliano((s1.invierno.getValue() + s.verano.getValue()) / 2);
            equinoccioOtoño = new DiaJuliano((s.verano.getValue() + s.invierno.getValue()) / 2);
            imbolc = new DiaJuliano((s1.invierno.getValue() + equinoccioPrimavera.getValue()) / 2);
            beltaine = new DiaJuliano((equinoccioPrimavera.getValue() + s.verano.getValue()) / 2);
            lugnasad = new DiaJuliano((s.verano.getValue() + equinoccioOtoño.getValue()) / 2);
            samain = new DiaJuliano((equinoccioOtoño.getValue() + s.invierno.getValue()) / 2);

        }
        else
        {
            Solsticio s2 = new Solsticio(año + 1);
            equinoccioPrimavera = new DiaJuliano((s.invierno.getValue() + s.verano.getValue()) / 2);
            equinoccioOtoño = new DiaJuliano((s.verano.getValue() + s2.invierno.getValue()) / 2);
            imbolc = new DiaJuliano((s.invierno.getValue() + equinoccioPrimavera.getValue()) / 2);
            beltaine = new DiaJuliano((equinoccioPrimavera.getValue() + s.verano.getValue()) / 2);
            lugnasad = new DiaJuliano((s.verano.getValue() + equinoccioOtoño.getValue()) / 2);
            samain = new DiaJuliano((equinoccioOtoño.getValue() + s2.invierno.getValue()) / 2);
        }

    }

}
