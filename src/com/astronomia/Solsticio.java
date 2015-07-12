/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.tipoCalculo;
import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Solsticio
{

    public DiaJuliano verano;
    public DiaJuliano invierno;

    public Solsticio(int año) throws Excepcion
    {
        DiaJuliano desde = new DiaJuliano(1, 1, año);
        DiaJuliano hasta = new DiaJuliano(1, 1, año + 1);

        double maximo = Double.MIN_VALUE;
        double minimo = Double.MAX_VALUE;

        double error=Double.MAX_VALUE;

        double p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
        double p_temp = Double.MAX_VALUE;

        do
        {
            double pmas = p + 1 / 24.0;
            double pmenos = p - 1 / 24.0;
            double f = Sol.getPosicionAparente(new DiaJuliano(p), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            if (f > 0)
            {
                p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
                p_temp = Double.MAX_VALUE;
                continue;
            }
            double fmas = Sol.getPosicionAparente(new DiaJuliano(pmas), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            double fmenos = Sol.getPosicionAparente(new DiaJuliano(pmenos), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            double df = (fmas - f) / (pmas - p);
            double dfmenos = (f - fmenos) / (p - pmenos);
            double ddf = (df - dfmenos) / (p - pmenos);
            p_temp = p;
            p = p - df / ddf;
            DiaJuliano puntero = new DiaJuliano(p);
            if (puntero.isBefore(desde) || puntero.isAfter(hasta))
            {
                p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
                p_temp = Double.MAX_VALUE;
            }
            error = abs(p - p_temp);
        }
        while (error > 1e-4);
        this.invierno = new DiaJuliano(p);

        error=Double.MAX_VALUE;
        p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
        p_temp = Double.MAX_VALUE;

        do
        {
            double pmas = p + 1 / 24.0;
            double pmenos = p - 1 / 24.0;
            double f = -Sol.getPosicionAparente(new DiaJuliano(p), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            if (f > 0)
            {
                p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
                p_temp = Double.MAX_VALUE;
                continue;
            }
            double fmas = -Sol.getPosicionAparente(new DiaJuliano(pmas), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            double fmenos = -Sol.getPosicionAparente(new DiaJuliano(pmenos), tipoCalculo.PRECISO).getDeclinacion().getSignedValue();
            double df = (fmas - f) / (pmas - p);
            double dfmenos = (f - fmenos) / (p - pmenos);
            double ddf = (df - dfmenos) / (p - pmenos);
            p_temp = p;
            p = p - df / ddf;
            DiaJuliano puntero = new DiaJuliano(p);
            if (puntero.isBefore(desde) || puntero.isAfter(hasta))
            {
                p = desde.diaJuliano + random() * (hasta.diaJuliano - desde.diaJuliano);
                p_temp = Double.MAX_VALUE;
            }
            error = abs(p - p_temp);
        }
        while (error > 1e-4);
        this.verano = new DiaJuliano(p);
    }
}
