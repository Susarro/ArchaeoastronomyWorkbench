/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.astronomia.DiaJuliano;
import static com.astronomia.Disco.CENTRO;
import static com.astronomia.Disco.SUPERIOR;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.Geodesica;
import com.astronomia.sistema.Horizontal;
import com.astronomia.sistema.HorizontalAparente;
import com.unidades.Grados;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Principal
{

    public Principal() throws Excepcion
    {
        Geodesica peñafaciel = new Geodesica(Grados.valueOf("42º23'29''"), Grados.valueOf("-6º18'51''"), 1193);
        Geodesica teleno = new Geodesica(Grados.valueOf("42º20'45''"), Grados.valueOf("-6º23'37''"), 2183);
        HorizontalAparente ha = HorizontalAparente.valueOf(peñafaciel, teleno);
        Horizontal h = ha.toAstronomico(enumPlaneta.SOL, SUPERIOR);
        Ecuatorial e = h.toEcuatorial(peñafaciel.getLatitud(), peñafaciel.getLongitud(), DiaJuliano.valueOf("1/1/-2000"));
    }

    public static void main(String args[]) throws Excepcion
    {
        new Principal();

    }
}
