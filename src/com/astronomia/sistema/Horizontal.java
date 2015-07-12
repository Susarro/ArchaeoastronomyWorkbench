/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.sistema;

import com.Excepcion;
import com.astronomia.DiaJuliano;
import com.astronomia.Planeta;
import com.enumPlaneta;
import static com.tipoCalculo.APROXIMADO;
import com.unidades.CoordenadasGeograficas;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static com.unidades.Herramienta.tangente;
import com.unidades.Hora;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Horizontal
{

    private Grados acimut;
    private Grados elevacion;

    @Override
    public String toString()
    {
        return "(" + getAcimut().toString() + "," + getElevacion().toString() + ")";
    }

    public Horizontal(Grados acimut, Grados elevacion)
    {
        this.acimut = acimut;
        this.elevacion = elevacion;
    }

    public Ecuatorial toEcuatorial(Grados latitud, Grados longitud, DiaJuliano diaJuliano) throws Excepcion
    {
        Grados oblicuidadEcliptica = diaJuliano.getOblicuidadEcliptica(APROXIMADO);
        Hora anguloHorarioLocal = Hora.atan2(seno(getAcimut()), coseno(getAcimut()) * seno(latitud) + tangente(getElevacion()) * coseno(latitud));
        Hora ascensionRecta = diaJuliano.getHoraSideralGreenwich(oblicuidadEcliptica).minus(Hora.valueOf(longitud)).minus(anguloHorarioLocal).Ajuste();
        Grados declinacion = Grados.asin(seno(latitud) * seno(getElevacion()) + coseno(latitud) * coseno(getElevacion()) * coseno(getAcimut())).Ajuste();
        return new Ecuatorial(declinacion, ascensionRecta);
    }

    public HorizontalAparente toAparente(enumPlaneta planeta, DiaJuliano dia) throws Excepcion
    {
        HorizontalAparente h = new HorizontalAparente(this.getAcimut(), this.getElevacion());
        Grados p = getParalaje(planeta, dia, h.getElevacion());
        h.setElevacion(this.getElevacion().plus(getRefraccionFromAstronomico(this.getElevacion())).minus(p));
        return h;
    }

    public HorizontalAparente toAparente() throws Excepcion
    {
        HorizontalAparente h = new HorizontalAparente(this.getAcimut(), this.getElevacion());
        h.setElevacion(this.getElevacion().plus(getRefraccionFromAstronomico(this.getElevacion())));
        return h;
    }

    static public Grados getRefraccionFromAparente(Grados elevacion) throws Excepcion
    {
        double R;
        double v = elevacion.getValor() + 7.31 / (elevacion.getValor() + 4.4);
        R = 60 / tangente(new Grados(v));
        return new Grados(R / 3600);
    }

    static public Grados getRefraccionFromAstronomico(Grados elevacion) throws Excepcion
    {
        if (elevacion.getSignedValue() > -2)
        {
            double R;
            double v = elevacion.getSignedValue() + 10.3 / (elevacion.getSignedValue() + 5.11);
            R = 60 * 1.02 / tangente(new Grados(v));
            return new Grados(R / 3600);
        }
        else
        {
            return elevacion;
        }
    }

    static public Grados getParalaje(CoordenadasGeograficas punto, enumPlaneta planeta, DiaJuliano dia, Grados elevacion) throws Excepcion
    {
        double distancia = Planeta.getDistancia(planeta, dia);
        if (distancia == 0)
        {
            return new Grados(0);
        }
        Grados paralaje = Grados.asin(seno(new Grados(8.794 / 3600)) / distancia);
        double a = 6378140;
        double f = 1 / 298.257;
        double b = a * (1 - f);
        Grados u = Grados.atan2(b * tangente(punto.getLatitud()), a);
        double A = b * seno(u) / a + punto.getAltitud() * seno(punto.getLatitud()) / 6378140;
        double B = coseno(u) + punto.getAltitud() * coseno(punto.getLatitud()) / 6378140;
        Grados temp = Grados.atan2(A, B);
        double ro = A / seno(temp);

        return Grados.asin(ro * seno(paralaje) * coseno(elevacion));
    }

    static public Grados getParalaje(enumPlaneta planeta, DiaJuliano dia, Grados elevacion) throws Excepcion
    {
        double distancia = Planeta.getDistancia(planeta, dia);
        if (distancia == 0)
        {
            return new Grados(0);
        }
        Grados paralaje = Grados.asin(seno(new Grados(8.794 / 3600)) / distancia);
        double ro = 1;

        return Grados.asin(ro * seno(paralaje) * coseno(elevacion));
    }

    static public Grados getParalaje(enumPlaneta planeta, Grados elevacion) throws Excepcion
    {
        switch (planeta)
        {
            case LUNA:
                double distancia = 384400.0 / 149597871.0;
                Grados paralaje = Grados.asin(seno(new Grados(8.794 / 3600)) / distancia);
                double ro = 1;

                return Grados.asin(ro * seno(paralaje) * coseno(elevacion));
            default:
                return new Grados(0);
        }

    }

    /**
     * @return the acimut
     */
    public Grados getAcimut()
    {
        return acimut;
    }

    /**
     * @param acimut the acimut to set
     */
    public void setAcimut(Grados acimut)
    {
        this.acimut = acimut;
    }

    /**
     * @return the elevacion
     */
    public Grados getElevacion()
    {
        return elevacion;
    }

    /**
     * @param elevacion the elevacion to set
     */
    public void setElevacion(Grados elevacion)
    {
        this.elevacion = elevacion;
    }

}
