/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.orbital;

import com.astronomia.DiaJuliano;
import com.astronomia.sistema.Ecliptica;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ElementosOrbitalesPlaneta
{

    Grados inclinacion;
    Grados argumentoPerihelion;
    Grados longitudNodoAscendente;
    
    

    DiaJuliano epoch;

    public ElementosOrbitalesPlaneta(Grados inclinacion, 
                           Grados argumentoPerihelion, 
                           Grados longitudNodoAscendente,
                           DiaJuliano epoch)
    {
        this.inclinacion = inclinacion;
        this.argumentoPerihelion = argumentoPerihelion;
        this.longitudNodoAscendente = longitudNodoAscendente;
        this.epoch = epoch;
    }

    public ElementosOrbitalesPlaneta Reduccion(DiaJuliano nuevoEpoch)
    {
        double T = epoch.getSiglosDesde2000();
        double t = (nuevoEpoch.getValue() - epoch.getValue()) / 36525;

        double a1 = ((47.0029 - 0.06603 * T + 0.000598 * pow(T, 2)) * t + (-0.03302 + 0.000598 * T) * pow(t, 2) + 0.000060 * pow(t, 3)); //segundos
        double a2 = 174.876384 + ((3289.4789 * T + 0.60622 * pow(T, 2)) - (869.8089 + 0.50491 * T) * t + 0.03536 * pow(t, 2)) / 3600;//segundos
        double a3 = (5029.0966 + 2.22226 * T - 0.000042 * pow(T, 2)) * t + (1.11113 - 0.000042 * T) * pow(t, 2) - 0.000006 * pow(t, 3);//segundos

        Grados A1 = new Grados(a1 / 3600);
        Grados A2 = new Grados(a2);
        Grados A3 = new Grados(a3 / 3600);
        Grados A4 = A2.plus(A3);

        double A = seno(inclinacion) * seno(longitudNodoAscendente.minus(A2));
        double B = -seno(A1) * coseno(inclinacion) + coseno(A1) * seno(inclinacion) * coseno(longitudNodoAscendente.minus(A2));

        Grados nuevaInclinacion = Grados.asin(sqrt(pow(A, 2) + pow(B, 2)));
        Grados nuevaLongitudNodoAscendente = Grados.atan2(A, B).plus(A4);

        double C = -seno(A1) * seno(longitudNodoAscendente.minus(A2));
        double D = seno(inclinacion) * coseno(A1) - coseno(inclinacion) * seno(A1) * coseno(longitudNodoAscendente.minus(A2));

        Grados incrArgumentoPerihelion = Grados.asin(C / seno(nuevaInclinacion));
        Grados nuevoArgumentoPerihelion = argumentoPerihelion.plus(incrArgumentoPerihelion);

        return new ElementosOrbitalesPlaneta(nuevaInclinacion, nuevoArgumentoPerihelion, nuevoArgumentoPerihelion, nuevoEpoch);
    }
}
