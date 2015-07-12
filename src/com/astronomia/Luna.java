/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.astronomia.sistema.Ecliptica;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.Heliocentrico;
import static com.enumPlaneta.TIERRA;
import com.tipoCalculo;
import com.unidades.Grados;
import static com.unidades.Herramienta.aportacionCoseno;
import static com.unidades.Herramienta.aportacionSeno;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import static java.lang.Math.pow;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Luna
{

    public static Grados getLongitudMedia(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(218.3164591 + 481267.88134236 * T - 0.0013268 * pow(T, 2) + pow(T, 3) / 538841 - pow(T, 4) / 65194000);
    }

    public static Grados getElongacionMedia(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(297.8502042 + 445267.1115168 * T - 0.0016300 * pow(T, 2) + pow(T, 3) / 545868 - pow(T, 4) / 113065000);
    }

    public static Grados getAnomaliaMedia(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(134.9634114 + 477198.8676313 * T + 0.0089970 * pow(T, 2) + pow(T, 3) / 69699 - pow(T, 4) / 14712000);
    }

    public static Grados getDistanciaMediaNodoAscendente(DiaJuliano dia)
    {
        double T = dia.getSiglosDesde2000();
        return new Grados(93.2720993 + 483202.0175273 * T - 0.0034029 * pow(T, 2) - pow(T, 3) / 3526000 + pow(T, 4) / 863310000);
    }

    public static Ecuatorial getPosicionAparente(DiaJuliano dia) throws Excepcion
    {
        double LM = getLongitudMedia(dia).getValor();
        double D = getElongacionMedia(dia).getValor();
        double T = dia.getSiglosDesde2000();
        double M = new Grados(357.5291092 + 35999.0502909 * T - 0.0001536 * pow(T, 2) + pow(T, 3) / 24490000).getValor();
        double MM = getAnomaliaMedia(dia).getValor();
        double F = getDistanciaMediaNodoAscendente(dia).getValor();
        double A1 = new Grados(119.75 + 131.849 * T).getValor();
        double A2 = new Grados(53.09 + 479264.290 * T).getValor();
        double A3 = new Grados(313.45 + 481266.484 * T).getValor();
        double E = 1 - 0.002516 * T - 0.0000074 * pow(T, 2);

        double sumL = 0;
        double sumB = 0;
        double sumR = 0;

        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6288774);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1274027);
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 658314);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 213618);
        sumL += aportacionSeno(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -185116) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -114332);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 58793);
        sumL += aportacionSeno(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 57066) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 53322);
        sumL += aportacionSeno(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 45758) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -40923) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34720);
        sumL += aportacionSeno(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -30383) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 15327);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -12528);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10980);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10675);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10034);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 8548);
        sumL += aportacionSeno(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7888) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -6766) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -5163);
        sumL += aportacionSeno(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4987) * E;
        sumL += aportacionSeno(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4036) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3994);
        sumL += aportacionSeno(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3861);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3665);
        sumL += aportacionSeno(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2689) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -2602);
        sumL += aportacionSeno(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2390) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2348);
        sumL += aportacionSeno(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2236) * E * E;
        sumL += aportacionSeno(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2120) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2069) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2048) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, -1773);
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1595);
        sumL += aportacionSeno(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1215) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1110);
        sumL += aportacionSeno(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -892);
        sumL += aportacionSeno(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -810) * E;
        sumL += aportacionSeno(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 759) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -713) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -700) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 691) * E;
        sumL += aportacionSeno(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 596) * E;
        sumL += aportacionSeno(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 549);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, 537);
        sumL += aportacionSeno(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 520) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -487);
        sumL += aportacionSeno(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, -399) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -381);
        sumL += aportacionSeno(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 351) * E;
        sumL += aportacionSeno(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -340);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 330);
        sumL += aportacionSeno(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 327) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -323) * E * E;
        sumL += aportacionSeno(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 299) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 294);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumL += 3958 * seno(A1) + 1962 * seno(LM - F) + 318 * seno(A2);

        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -20905355);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3699111);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2955968);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -569925);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 48888) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -3149);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 246158);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -152138) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -170733);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -204586) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -129620) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 108743);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 104755) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10321);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 79661);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34782);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, -23210);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -21636);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 24208) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 30824) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -8379);
        sumR += aportacionCoseno(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -16675) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -12831) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -10445);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -11650);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 14403);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7003) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10056) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6322);
        sumR += aportacionCoseno(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -9884) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 5751) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -4950) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 4130);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3958) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3258);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2616) * E;
        sumR += aportacionCoseno(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1897) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2117) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2354) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1423);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1117);
        sumR += aportacionCoseno(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1571) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1739);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -4421);
        sumR += aportacionCoseno(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1165) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 8752);

        sumB += aportacionSeno(new double[]
        {
            0, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 5128122);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 280602);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 277693);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 173237);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 55413);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 46271);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 32573);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 17198);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 9266);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8822);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8216) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 4324);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 4200);
        sumB += aportacionSeno(new double[]
        {
            2, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -3359) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2463) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2211) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 2065) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1870) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1828);
        sumB += aportacionSeno(new double[]
        {
            0, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1794) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 0, 0, 3
        }, new double[]
        {
            D, M, MM, F
        }, -1749);
        sumB += aportacionSeno(new double[]
        {
            0, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1565) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1491);
        sumB += aportacionSeno(new double[]
        {
            0, 1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1475) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1410) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1344) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1335);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 3, 1
        }, new double[]
        {
            D, M, MM, F
        }, 1107);
        sumB += aportacionSeno(new double[]
        {
            4, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1021);
        sumB += aportacionSeno(new double[]
        {
            4, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 833);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, -3
        }, new double[]
        {
            D, M, MM, F
        }, 777);
        sumB += aportacionSeno(new double[]
        {
            4, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 671);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, -3
        }, new double[]
        {
            D, M, MM, F
        }, 607);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 596);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 491) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -451);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 439);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 422);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 421);
        sumB += aportacionSeno(new double[]
        {
            2, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -366) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -351) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 331);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 315) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -2, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 302) * E * E;
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, 3
        }, new double[]
        {
            D, M, MM, F
        }, -283);
        sumB += aportacionSeno(new double[]
        {
            2, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -229) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -185);
        sumB += aportacionSeno(new double[]
        {
            2, -1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 181) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -177) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 176);
        sumB += aportacionSeno(new double[]
        {
            4, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 166) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -164);
        sumB += aportacionSeno(new double[]
        {
            4, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 132);
        sumB += aportacionSeno(new double[]
        {
            1, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -119);
        sumB += aportacionSeno(new double[]
        {
            4, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 115) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -2, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 107) * E * E;
        sumB += -2235 * seno(LM) + 382 * seno(A3) + 175 * seno(A1 - F) + 175 * seno(A1 + F) + 127 * seno(LM - MM) - 115 * seno(LM + MM);

        Grados longitud = new Grados(LM).plus(new Grados(sumL / 1000000));
        Grados latitud = new Grados(sumB / 1000000);
        double distancia = (385000.56 + sumR / 1000) / 1.4960e8;

        Grados nutacion = dia.getNutacionLongitud();

        longitud = longitud.plus(nutacion);

        return new Ecliptica(longitud, latitud, distancia).toEcuatorial(dia.getOblicuidadEcliptica(tipoCalculo.PRECISO));

    }

    public static InfoLuna getInfoLuna(DiaJuliano dia) throws Excepcion
    {
        double LM = getLongitudMedia(dia).getValor();
        double D = getElongacionMedia(dia).getValor();
        double T = dia.getSiglosDesde2000();
        double M = new Grados(357.5291092 + 35999.0502909 * T - 0.0001536 * pow(T, 2) + pow(T, 3) / 24490000).getValor();
        double MM = getAnomaliaMedia(dia).getValor();
        double F = getDistanciaMediaNodoAscendente(dia).getValor();
        double A1 = new Grados(119.75 + 131.849 * T).getValor();
        double A2 = new Grados(53.09 + 479264.290 * T).getValor();
        double A3 = new Grados(313.45 + 481266.484 * T).getValor();
        double E = 1 - 0.002516 * T - 0.0000074 * pow(T, 2);

        double sumL = 0;
        double sumB = 0;
        double sumR = 0;

        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6288774);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1274027);
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 658314);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 213618);
        sumL += aportacionSeno(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -185116) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -114332);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 58793);
        sumL += aportacionSeno(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 57066) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 53322);
        sumL += aportacionSeno(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 45758) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -40923) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34720);
        sumL += aportacionSeno(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -30383) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 15327);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -12528);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10980);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10675);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10034);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 8548);
        sumL += aportacionSeno(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7888) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -6766) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -5163);
        sumL += aportacionSeno(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4987) * E;
        sumL += aportacionSeno(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 4036) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3994);
        sumL += aportacionSeno(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3861);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3665);
        sumL += aportacionSeno(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2689) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, -2602);
        sumL += aportacionSeno(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2390) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2348);
        sumL += aportacionSeno(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2236) * E * E;
        sumL += aportacionSeno(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2120) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2069) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2048) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, -1773);
        sumL += aportacionSeno(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1595);
        sumL += aportacionSeno(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1215) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, -1110);
        sumL += aportacionSeno(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -892);
        sumL += aportacionSeno(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -810) * E;
        sumL += aportacionSeno(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 759) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -713) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -700) * E * E;
        sumL += aportacionSeno(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 691) * E;
        sumL += aportacionSeno(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 596) * E;
        sumL += aportacionSeno(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 549);
        sumL += aportacionSeno(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, 537);
        sumL += aportacionSeno(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 520) * E;
        sumL += aportacionSeno(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -487);
        sumL += aportacionSeno(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, -399) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -381);
        sumL += aportacionSeno(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 351) * E;
        sumL += aportacionSeno(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -340);
        sumL += aportacionSeno(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 330);
        sumL += aportacionSeno(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 327) * E;
        sumL += aportacionSeno(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -323) * E * E;
        sumL += aportacionSeno(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 299) * E;
        sumL += aportacionSeno(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 294);
        sumL += aportacionSeno(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumL += 3958 * seno(A1) + 1962 * seno(LM - F) + 318 * seno(A2);

        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -20905355);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3699111);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2955968);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -569925);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 48888) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, -3149);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 246158);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -152138) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -170733);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -204586) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -129620) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 108743);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 104755) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 10321);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 79661);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -34782);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, -23210);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -21636);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 24208) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 30824) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -8379);
        sumR += aportacionCoseno(new double[]
        {
            1, 1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -16675) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -12831) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -10445);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -11650);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 14403);
        sumR += aportacionCoseno(new double[]
        {
            0, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -7003) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 10056) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 6322);
        sumR += aportacionCoseno(new double[]
        {
            2, -2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -9884) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 5751) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -4950) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 4130);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 0, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            4, -1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -3958) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, 2
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            3, 0, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 3258);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2616) * E;
        sumR += aportacionCoseno(new double[]
        {
            4, -1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1897) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -2117) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 2, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 2354) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 1, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            4, 0, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1423);
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 4, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1117);
        sumR += aportacionCoseno(new double[]
        {
            4, -1, 0, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1571) * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, -1739);
        sumR += aportacionCoseno(new double[]
        {
            2, 1, 0, -2
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 0, 2, -2
        }, new double[]
        {
            D, M, MM, F
        }, -4421);
        sumR += aportacionCoseno(new double[]
        {
            1, 1, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            3, 0, -2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            4, 0, -3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, -1, 2, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            0, 2, 1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 1165) * E * E;
        sumR += aportacionCoseno(new double[]
        {
            1, 1, -1, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0) * E;
        sumR += aportacionCoseno(new double[]
        {
            2, 0, 3, 0
        }, new double[]
        {
            D, M, MM, F
        }, 0);
        sumR += aportacionCoseno(new double[]
        {
            2, 0, -1, -2
        }, new double[]
        {
            D, M, MM, F
        }, 8752);

        sumB += aportacionSeno(new double[]
        {
            0, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 5128122);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 280602);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 277693);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 173237);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 55413);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 46271);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 32573);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 17198);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 9266);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8822);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 8216) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 4324);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 4200);
        sumB += aportacionSeno(new double[]
        {
            2, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -3359) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2463) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 2211) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 2065) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1870) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1828);
        sumB += aportacionSeno(new double[]
        {
            0, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1794) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 0, 0, 3
        }, new double[]
        {
            D, M, MM, F
        }, -1749);
        sumB += aportacionSeno(new double[]
        {
            0, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1565) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1491);
        sumB += aportacionSeno(new double[]
        {
            0, 1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -1475) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1410) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1344) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, -1335);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 3, 1
        }, new double[]
        {
            D, M, MM, F
        }, 1107);
        sumB += aportacionSeno(new double[]
        {
            4, 0, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 1021);
        sumB += aportacionSeno(new double[]
        {
            4, 0, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 833);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, -3
        }, new double[]
        {
            D, M, MM, F
        }, 777);
        sumB += aportacionSeno(new double[]
        {
            4, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 671);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 0, -3
        }, new double[]
        {
            D, M, MM, F
        }, 607);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 596);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 491) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 0, -2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -451);
        sumB += aportacionSeno(new double[]
        {
            0, 0, 3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 439);
        sumB += aportacionSeno(new double[]
        {
            2, 0, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, 422);
        sumB += aportacionSeno(new double[]
        {
            2, 0, -3, -1
        }, new double[]
        {
            D, M, MM, F
        }, 421);
        sumB += aportacionSeno(new double[]
        {
            2, 1, -1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -366) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, -351) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 331);
        sumB += aportacionSeno(new double[]
        {
            2, -1, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, 315) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -2, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 302) * E * E;
        sumB += aportacionSeno(new double[]
        {
            0, 0, 1, 3
        }, new double[]
        {
            D, M, MM, F
        }, -283);
        sumB += aportacionSeno(new double[]
        {
            2, 1, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -229) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 1, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 223) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += aportacionSeno(new double[]
        {
            2, 1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -220) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 1, 1
        }, new double[]
        {
            D, M, MM, F
        }, -185);
        sumB += aportacionSeno(new double[]
        {
            2, -1, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 181) * E;
        sumB += aportacionSeno(new double[]
        {
            0, 1, 2, 1
        }, new double[]
        {
            D, M, MM, F
        }, -177) * E;
        sumB += aportacionSeno(new double[]
        {
            4, 0, -2, -1
        }, new double[]
        {
            D, M, MM, F
        }, 176);
        sumB += aportacionSeno(new double[]
        {
            4, -1, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 166) * E;
        sumB += aportacionSeno(new double[]
        {
            1, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -164);
        sumB += aportacionSeno(new double[]
        {
            4, 0, 1, -1
        }, new double[]
        {
            D, M, MM, F
        }, 132);
        sumB += aportacionSeno(new double[]
        {
            1, 0, -1, -1
        }, new double[]
        {
            D, M, MM, F
        }, -119);
        sumB += aportacionSeno(new double[]
        {
            4, -1, 0, -1
        }, new double[]
        {
            D, M, MM, F
        }, 115) * E;
        sumB += aportacionSeno(new double[]
        {
            2, -2, 0, 1
        }, new double[]
        {
            D, M, MM, F
        }, 107) * E * E;
        sumB += -2235 * seno(LM) + 382 * seno(A3) + 175 * seno(A1 - F) + 175 * seno(A1 + F) + 127 * seno(LM - MM) - 115 * seno(LM + MM);

        Grados longitud = new Grados(LM).plus(new Grados(sumL / 1000000));
        Grados latitud = new Grados(sumB / 1000000);
        double distancia = (385000.56 + sumR / 1000) / 149597870.7;

        Grados nutacion = dia.getNutacionLongitud();

        longitud = longitud.plus(nutacion);

        Grados longitudSol = Heliocentrico.getLongitud(TIERRA, dia).plus(new Grados(180));
        double R = Heliocentrico.getRadio(TIERRA, dia);

        Grados elongacionGeocentrica = Grados.acos(coseno(latitud) * coseno(longitud.minus(longitudSol)));

        Grados angulosFase = Grados.atan2(R * seno(elongacionGeocentrica), distancia - R * coseno(elongacionGeocentrica));

        double k = (1 + coseno(angulosFase)) / 2;

        return new InfoLuna(new Ecliptica(longitud, latitud, distancia).toEcuatorial(dia.getOblicuidadEcliptica(tipoCalculo.PRECISO)), k);

    }

   

}
