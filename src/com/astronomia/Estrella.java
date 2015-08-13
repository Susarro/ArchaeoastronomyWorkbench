/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.astronomia.sistema.Ecuatorial;
import com.Excepcion;
import com.tipoCalculo;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static com.unidades.Herramienta.tangente;
import com.unidades.Hora;
import static java.lang.Math.pow;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Estrella extends Ecuatorial
{

    Ecuatorial movimientoPropio;
    DiaJuliano epoch;
    public double magnitudVisual;
    String nombre;

    public String toString()
    {
        return nombre;
    }

    public Estrella(String nombre, Ecuatorial coordenadasEcuatoriales, Ecuatorial movimientoPropio, DiaJuliano diaJuliano, double magnitud)
    {
        super(coordenadasEcuatoriales);
        this.nombre = nombre;
        this.movimientoPropio = movimientoPropio;
        this.epoch = diaJuliano;
        this.magnitudVisual = magnitud;
    }

    public Ecuatorial Precesion(DiaJuliano nuevoEpoch, tipoCalculo tipo) throws Excepcion
    {
        double T = epoch.getSiglosDesde2000();
        double t = (nuevoEpoch.getValue() - epoch.getValue()) / 36525;

        Grados D = getDeclinacion().plus(new Grados(movimientoPropio.getDeclinacion().getSignedValue() * t * 100));
        Hora AR = getAscensionRecta().plus(new Hora(movimientoPropio.getAscensionRecta().getSignedValue() * t * 100));

        Ecuatorial ce = new Ecuatorial(D, AR);

        if (tipo == tipoCalculo.PRECISO)
        {
            ce = ce.CorreccionAberracion(nuevoEpoch);
        }

        double a1 = (2306.2181 + 1.39656 * T - 0.000139 * pow(T, 2)) * t + (0.30188 - 0.000344 * T) * pow(t, 2) + 0.017998 * pow(t, 3); //segundos
        double a2 = (2306.2181 + 1.39656 * T - 0.000139 * pow(T, 2)) * t + (1.09468 + 0.000066 * T) * pow(t, 2) + 0.018203 * pow(t, 3);//segundos
        double a3 = (2004.3109 - 0.85330 * T - 0.000217 * pow(T, 2)) * t - (0.42665 + 0.000217 * T) * pow(t, 2) - 0.041833 * pow(t, 3);//segundos

        Hora A1 = Hora.valueOf(new Grados(a1 / 3600));
        Hora A2 = Hora.valueOf(new Grados(a2 / 3600));
        Hora A3 = Hora.valueOf(new Grados(a3 / 3600));

        double A = coseno(ce.getDeclinacion()) * seno(ce.getAscensionRecta().plus(A1));
        double B = coseno(A3) * coseno(ce.getDeclinacion()) * coseno(ce.getAscensionRecta().plus(A1)) - seno(A3) * seno(ce.getDeclinacion());
        double C = seno(A3) * coseno(ce.getDeclinacion()) * coseno(ce.getAscensionRecta().plus(A1)) + coseno(A3) * seno(ce.getDeclinacion());

        Hora ascensionRecta = Hora.atan2(A, B).plus(A2);
        Grados declinacion = Grados.asin(C);

        double nutacionLongitud = nuevoEpoch.getNutacionLongitud().getSignedValue();
        double nutacionEcliptica = nuevoEpoch.getNutacionOblicuidadEcliptica().getSignedValue();
        Grados ecliptica = nuevoEpoch.getOblicuidadEcliptica(tipo.PRECISO);

        Hora correccionAscensionRecta = new Hora(nutacionLongitud*(coseno(ecliptica) + seno(ecliptica) * seno(ascensionRecta) * tangente(declinacion))-(nutacionEcliptica*(coseno(ascensionRecta) * tangente(declinacion))));
        Grados correccionDeclinacion =  new Grados(nutacionLongitud*seno(ecliptica) * coseno(ascensionRecta)+nutacionEcliptica*seno(ascensionRecta));
        
        //Grados correccionDeclinacion = nutacionLongitud.by(seno(ecliptica) * coseno(ascensionRecta)).plus(nutacionEcliptica.by(seno(ascensionRecta)));

        declinacion=declinacion.plus(correccionDeclinacion);
        ascensionRecta=ascensionRecta.plus(correccionAscensionRecta);
        
        return new Ecuatorial(declinacion, ascensionRecta);
    }

    /*public Ecliptica Precesion(DiaJuliano nuevoEpoch, tipoCalculo tipo) throws Excepcion
     {
     double T = epoch.getSiglosDesde2000();
     double t = (nuevoEpoch.getValue() - epoch.getValue()) / 36525;

     Grados D = coordenadasEcuatoriales.declinacion.plus(movimientoPropio.declinacion.by(t * 100));
     Hora AR = coordenadasEcuatoriales.ascensionRecta.plus(movimientoPropio.ascensionRecta.by(t * 100));

     Ecuatorial ce = new Ecuatorial(D, AR);

     if (tipo == tipoCalculo.PRECISO)
     {
     ce = ce.CorreccionAberracion(nuevoEpoch);
     }

     double a1 = ((47.0029 - 0.06603 * T + 0.000598 * pow(T, 2)) * t + (-0.03302 + 0.000598 * T) * pow(t, 2) + 0.000060 * pow(t, 3)); //segundos
     double a2 = (174.876384*3600 + 3289.4789 * T + 0.60622 * pow(T, 2)) * t - (869.8089 + 0.50491 * T) * pow(t, 2 + 0.03536 * pow(t, 3));//segundos
     double a3 = (5029.0966 + 2.22226 * T - 0.000042 * pow(T, 2)) * t + (1.11113 - 0.000042 * T) * pow(t, 2) - 0.000006 * pow(t, 3);//segundos

     Hora A1 = Hora.valueOf(new Grados(a1 / 3600));
     Hora A2 = Hora.valueOf(new Grados(a2 / 3600));
     Hora A3 = Hora.valueOf(new Grados(a3 / 3600));

     double A = coseno(ce.declinacion) * seno(ce.ascensionRecta.plus(A1));
     double B = coseno(A3) * coseno(ce.declinacion) * coseno(ce.ascensionRecta.plus(A1)) - seno(A3) * seno(ce.declinacion);
     double C = seno(A3) * coseno(ce.declinacion) * coseno(ce.ascensionRecta.plus(A1)) + coseno(A3) * seno(ce.declinacion);

     Hora ascensionRecta = Hora.atan2(A, B).plus(A2);
     Grados declinacion = Grados.asin(C);
     return new Ecuatorial(declinacion, ascensionRecta);
     }*/
    public Ecuatorial getPosicionAparente(DiaJuliano nuevoEpoch, tipoCalculo tipo) throws Excepcion
    {
        double T = epoch.getSiglosDesde2000();
        double t = (nuevoEpoch.getValue() - epoch.getValue()) / 36525;

        Ecuatorial ce = Precesion(nuevoEpoch, tipo);

        Grados D = ce.getDeclinacion();
        Hora AR = ce.getAscensionRecta();

        Grados nl = nuevoEpoch.getNutacionLongitud();
        Grados oe = nuevoEpoch.getOblicuidadEcliptica(tipo);
        Grados ne = nuevoEpoch.getNutacionOblicuidadEcliptica();

        Grados stl = Sol.getLongitudVerdaderaSol(nuevoEpoch);

        double incrAR = 3600 * ((coseno(oe) + seno(oe) * seno(AR) * tangente(D)) * nl.getSignedValue() - coseno(AR) * tangente(D) * ne.getSignedValue());
        double incrD = 3600 * (seno(oe) * coseno(AR) * nl.getSignedValue() + seno(AR) * ne.getSignedValue());

        double k = 20.49552; //constante de aberración

        double incrAR2 = 0;
        double incrD2 = 0;

        if (tipo == tipoCalculo.APROXIMADO)
        {
            Grados e = nuevoEpoch.getExcentricidadOrbitaTierra();
            Grados pi = nuevoEpoch.getLongitudPerihelionTierra();

            incrAR2 = -k * (coseno(AR) * coseno(stl) * coseno(oe) + seno(AR) * seno(stl)) / coseno(D)
                    + e.getValor() * k * (coseno(AR) * coseno(pi) * coseno(oe) + seno(AR) * seno(pi)) / coseno(D);

            incrD2 = -k * (coseno(stl) * coseno(oe) * (tangente(oe) * coseno(D) - seno(AR) * seno(D)) + coseno(AR) * seno(D) * seno(stl))
                    + e.getValor() * k * (coseno(pi) * coseno(oe) * (tangente(oe) * coseno(D) - seno(AR) * seno(D)) + coseno(AR) * seno(D) * seno(pi));
        }
        Grados D2 = D.plus(new Grados((incrD + incrD2) / 3600));
        Hora AR2 = AR.plus(Hora.valueOf(new Grados((incrAR + incrAR2) / 3600)));

        return new Ecuatorial(D2, AR2);

    }
}
