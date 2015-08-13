/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.astronomia.DiaJuliano;
import static com.astronomia.Disco.SUPERIOR;
import com.astronomia.Estrella;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.Geodesica;
import com.astronomia.sistema.Horizontal;
import com.astronomia.sistema.HorizontalAparente;
import com.unidades.Grados;
import com.unidades.Hora;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Principal
{

    public Principal() throws Excepcion
    {
        Ecuatorial coord = new Ecuatorial(Grados.valueOf("49º13'42.48''"), Hora.valueOf("2:44:11.986"));
        Ecuatorial mp = new Ecuatorial(new Grados(-89.5 / 3600000), new Hora(34.25 / 3600000));
        Estrella estrella = new Estrella("Polar", coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.77);
        
        Ecuatorial eq=estrella.Precesion(new DiaJuliano(2462088.69), tipoCalculo.PRECISO);

      
    }

    public static void main(String args[]) throws Excepcion
    {
        new Principal();

    }
}
