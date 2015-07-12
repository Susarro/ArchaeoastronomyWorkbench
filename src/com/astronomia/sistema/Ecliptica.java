/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.sistema;

import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static com.unidades.Herramienta.tangente;
import com.unidades.Hora;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Ecliptica
{

    private Grados longitudEcliptica;
    private Grados latitudEcliptica;
    private double distancia = 0;

    public Ecliptica(Grados longitud, Grados latitud)
    {
        this.longitudEcliptica = longitud;
        this.latitudEcliptica = latitud;
    }

    public Ecliptica(Grados longitud, Grados latitud, double distancia)
    {
        this.longitudEcliptica = longitud;
        this.latitudEcliptica = latitud;
        this.distancia = distancia;
    }

    public Ecliptica(Ecliptica coordenadas)
    {
        this.longitudEcliptica = coordenadas.longitudEcliptica;
        this.latitudEcliptica = coordenadas.latitudEcliptica;
        this.distancia = coordenadas.distancia;
    }

    public Ecuatorial toEcuatorial(Grados oblicuidadEcliptica)
    {
        Hora ascensionRecta = Hora.atan2(seno(getLongitudEcliptica()) * coseno(oblicuidadEcliptica) - tangente(getLatitudEcliptica()) * seno(oblicuidadEcliptica), coseno(getLongitudEcliptica())).Ajuste();
        Grados declinacion = Grados.asin(seno(getLatitudEcliptica()) * coseno(oblicuidadEcliptica) + coseno(getLatitudEcliptica()) * seno(oblicuidadEcliptica) * seno(getLongitudEcliptica())).Ajuste();
        if (getDistancia() != 0)
        {
            return new Ecuatorial(declinacion, ascensionRecta, getDistancia());
        }
        else
        {
            return new Ecuatorial(declinacion, ascensionRecta);
        }
    }

    public Ecuatorial toEcuatorial(Grados oblicuidadEcliptica, double distancia)
    {
        Hora ascensionRecta = Hora.atan2(seno(getLongitudEcliptica()) * coseno(oblicuidadEcliptica) - tangente(getLatitudEcliptica()) * seno(oblicuidadEcliptica), coseno(getLongitudEcliptica())).Ajuste();
        Grados declinacion = Grados.asin(seno(getLatitudEcliptica()) * coseno(oblicuidadEcliptica) + coseno(getLatitudEcliptica()) * seno(oblicuidadEcliptica) * seno(getLongitudEcliptica())).Ajuste();
        return new Ecuatorial(declinacion, ascensionRecta, distancia);
    }

    static public Grados getSeparacionAngular(Ecliptica ce1, Ecliptica ce2)
    {
        double c = seno(ce1.getLatitudEcliptica()) * seno(ce2.getLatitudEcliptica()) + coseno(ce1.getLatitudEcliptica()) * coseno(ce2.getLatitudEcliptica()) * coseno(ce1.getLongitudEcliptica().minus(ce2.getLongitudEcliptica()));
        if (c < 0.999995)
        {
            return Grados.acos(c);
        }
        else
        {
            double c1 = ce1.getLongitudEcliptica().minus(ce2.getLongitudEcliptica()).getValor();;
            double c2 = ce1.getLatitudEcliptica().minus(ce2.getLatitudEcliptica()).getValor();
            double c3 = (ce1.getLatitudEcliptica().getValor() + ce2.getLatitudEcliptica().getValor()) / 2;
            return new Grados(sqrt(pow(c1 * coseno(new Grados(c3)), 2) + pow(c2, 2)));
        }
    }

    /**
     * @return the longitudEcliptica
     */
    public Grados getLongitudEcliptica()
    {
        return longitudEcliptica;
    }

    /**
     * @param longitudEcliptica the longitudEcliptica to set
     */
    public void setLongitudEcliptica(Grados longitudEcliptica)
    {
        this.longitudEcliptica = longitudEcliptica;
    }

    /**
     * @return the latitudEcliptica
     */
    public Grados getLatitudEcliptica()
    {
        return latitudEcliptica;
    }

    /**
     * @param latitudEcliptica the latitudEcliptica to set
     */
    public void setLatitudEcliptica(Grados latitudEcliptica)
    {
        this.latitudEcliptica = latitudEcliptica;
    }

    /**
     * @return the distancia
     */
    public double getDistancia()
    {
        return distancia;
    }

    /**
     * @param distancia the distancia to set
     */
    public void setDistancia(double distancia)
    {
        this.distancia = distancia;
    }
}
