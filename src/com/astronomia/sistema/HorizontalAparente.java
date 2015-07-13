/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.sistema;

import com.Excepcion;
import com.astronomia.DiaJuliano;
import com.astronomia.Disco;
import static com.astronomia.sistema.Horizontal.getParalaje;
import static com.astronomia.sistema.Horizontal.getRefraccionFromAparente;
import com.enumPlaneta;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class HorizontalAparente extends Horizontal
{

    public HorizontalAparente(Grados acimut, Grados elevacion)
    {
        super(acimut, elevacion);
    }

    public Horizontal toAstronomico(enumPlaneta planeta, DiaJuliano dia, Disco disco) throws Excepcion
    {
        Horizontal h = new Horizontal(this.getAcimut(), this.getElevacion());
        h.setElevacion(this.getElevacion().minus(getRefraccionFromAparente(this.getElevacion())));
        Grados p = getParalaje(planeta, dia, h.getElevacion());
        h.setElevacion(h.getElevacion().plus(p));

        Grados semidisco = null;
        switch (planeta)
        {
            case SOL:
                semidisco = new Grados(959.0 / 3600);
                break;
            case LUNA:
                semidisco = new Grados(961.0 / 3600);
                break;
            default:
                semidisco = new Grados(0);
        }
        switch (disco)
        {
            case SUPERIOR:
                h.setElevacion(h.getElevacion().minus(semidisco));
                break;
            case INFERIOR:
                h.setElevacion(h.getElevacion().plus(semidisco));
                break;
        }
        return h;
    }

    public Horizontal toAstronomico(enumPlaneta planeta, Disco disco) throws Excepcion
    {
        Horizontal h = new Horizontal(this.getAcimut(), this.getElevacion());
        Grados R = getRefraccionFromAparente(this.getElevacion());
        h.setElevacion(this.getElevacion().minus(R));
        Grados p = getParalaje(planeta, h.getElevacion());
        h.setElevacion(h.getElevacion().plus(p));

        Grados semidisco = null;
        switch (planeta)
        {
            case SOL:
                semidisco = new Grados(959.0 / 3600);
                break;
            case LUNA:
                semidisco = new Grados(961.0 / 3600);
                break;
            default:
                semidisco = new Grados(0);
        }
        switch (disco)
        {
            case SUPERIOR:
                h.setElevacion(h.getElevacion().minus(semidisco));
                break;
            case INFERIOR:
                h.setElevacion(h.getElevacion().plus(semidisco));
                break;
        }

        return h;
    }

    public static HorizontalAparente valueOf(Geodesica desde, Geodesica hasta)
    {
        Grados d = Grados.acos(seno(hasta.getLatitud()) * seno(desde.getLatitud()) + coseno(hasta.getLatitud()) * coseno(desde.getLatitud()) * coseno(hasta.getLongitud().minus(desde.getLongitud())));
        Grados A = Grados.asin(coseno(hasta.getLatitud()) * seno(hasta.getLongitud().minus(desde.getLongitud())) / seno(d));
        if (hasta.getLongitud().getSignedValue() == desde.getLongitud().getSignedValue())
        {
            if (hasta.getLatitud().getSignedValue() >= desde.getLatitud().getSignedValue())
            {
                A = new Grados(0);
            }
            else
            {
                A = new Grados(180);
            }
        }
        else 
        {
            if (hasta.getLatitud().getSignedValue() < desde.getLatitud().getSignedValue())
            {
                A = new Grados(180).minus(A);
            }                
        }        

        double R = 6378135;
        Grados h = Grados.atan2((R + hasta.getAltitud()) * coseno(d) - (R + desde.getAltitud() + 1.7), (R + desde.getAltitud()) * seno(d));

        return new HorizontalAparente(A, h);
    }

}
