/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.astronomia.sistema.Heliocentrico;
import com.astronomia.sistema.Ecliptica;
import com.astronomia.sistema.Ecuatorial;
import static com.enumPlaneta.TIERRA;
import com.tipoCalculo;
import com.unidades.Grados;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import com.unidades.Hora;
import static java.lang.Math.pow;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Sol
{

    public static Grados getLongitudGeometricaMedia(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(280.46645 + 36000.76983 * T + 0.0003032 * pow(T, 2));
    }

    public static Grados getLongitudGeometricaAparente(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        Grados omega = new Grados(125.04 - 1934.136 * T);
        Grados temp = new Grados(0.00569 + 0.00478 * seno(omega));
        return getLongitudGeometricaMedia(dia).minus(temp);
    }

    public static Grados getAnomaliaMedia(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(357.52910 + 35999.05030 * T + 0.000155 * pow(T, 2) - 0.00000048 * pow(T, 3));
    }

    public static Grados getLongitudVerdaderaSol(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        Grados M = getAnomaliaMedia(dia);
        Grados centro = new Grados((1.914600 - 0.004817 * T - 0.000014 * pow(T, 2)) * seno(M)
                + (0.019993 - 0.000101 * T) * seno(M.by(2)) + 0.000290 * seno(M.by(3)));
        return Sol.getLongitudGeometricaMedia(dia).plus(centro);
    }

    public static Grados getAnomaliaVerdaderaSol(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        Grados M = getAnomaliaMedia(dia);
        Grados centro = new Grados((1.914600 - 0.004817 * T - 0.000014 * pow(T, 2)) * seno(M)
                + (0.019993 - 0.000101 * T) * seno(M.by(2)) + 0.000290 * seno(M.by(3)));
        return M.plus(centro);
    }

    public static Ecliptica getPosicionEclipticaAparente(DiaJuliano dia) throws Excepcion
    {
        double T = dia.getSiglosDesde2000();
        Grados L = Heliocentrico.getLongitud(TIERRA, dia);
        Grados B = Heliocentrico.getLatitud(TIERRA, dia);
        double R = Heliocentrico.getRadio(TIERRA, dia);
        Grados longitudSol = L.plus(new Grados(180));
        Grados latitudSol = new Grados(-B.getValor());
        Grados corr = longitudSol.minus(new Grados(1.397 * T + 0.00031 * pow(T, 2)));
        longitudSol = longitudSol.plus(new Grados(-0.09033 / 3600));
        latitudSol = latitudSol.plus(new Grados(0.03916 * (coseno(corr) - seno(corr)) / 3600));
        longitudSol = longitudSol.plus(dia.getNutacionLongitud()); //Nutación en longitud
        longitudSol = longitudSol.plus(new Grados(-20.4898 / (R * 3600)));
        return new Ecliptica(longitudSol, latitudSol, R);

    }

    public static Ecuatorial getPosicionAparente(DiaJuliano dia, tipoCalculo tipo) throws Excepcion
    {
        double T = dia.getSiglosDesde2000();

        if (tipo == tipoCalculo.APROXIMADO)
        {
            Grados omega = new Grados(125.04 - 1934.136 * T);
            Grados temp = new Grados(0.00569 + 0.00478 * seno(omega));
            Grados O = getLongitudVerdaderaSol(dia);
            O = O.minus(temp);
            Grados e = dia.getOblicuidadEcliptica(tipoCalculo.APROXIMADO).plus(new Grados(0.00256 * coseno(omega)));
            Hora ascensionRecta = Hora.atan2(coseno(e) * seno(O), coseno(O));
            Grados declinacion = Grados.asin(seno(e) * seno(O));
            return new Ecuatorial(declinacion, ascensionRecta);
        }
        else
        {
            return getPosicionEclipticaAparente(dia).toEcuatorial(dia.getOblicuidadEcliptica(tipoCalculo.PRECISO));
        }
    }

    

}
