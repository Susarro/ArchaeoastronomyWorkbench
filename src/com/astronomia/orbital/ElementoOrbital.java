/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.orbital;

import com.astronomia.DiaJuliano;
import com.enumPlaneta;
import com.unidades.Grados;
import static java.lang.Math.pow;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ElementoOrbital
{

    public Grados longitudMedia;
    public double ejeSemimayor;
    public double excentricidad;
    public Grados inclinacion;
    public Grados longitudNodoAscendente;
    public Grados longitudPerihelion;

    public static ElementoOrbital get(enumPlaneta planeta, DiaJuliano epoch)
    {
        ElementoOrbital e = new ElementoOrbital();
        double T = epoch.getSiglosDesde2000();
        switch (planeta)
        {
            case MERCURIO:
                e.longitudMedia = new Grados(252.250906 + 149474.0722491 * T + 0.00030397 * pow(T, 2) + 0.000000018 * pow(T, 3));
                e.ejeSemimayor = 0.387098310;
                e.excentricidad = 0.20563175 + 0.000020406 * T - 0.0000000284 * pow(T, 2) - 0.00000000017 * pow(T, 3);
                e.inclinacion = new Grados(7.004986 + 0.0018215 * T - 0.00001809 * pow(T, 2) + 0.000000053 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(48.330893 + 1.1861890 * T + 0.00017587 * pow(T, 2) + 0.000000211 * pow(T, 3));
                e.longitudPerihelion = new Grados(77.456119 + 1.5564775 * T + 0.00029589 * pow(T, 2) + 0.000000056 * pow(T, 3));
                return e;
           case VENUS:
                e.longitudMedia = new Grados(181.979801 + 58519.2130302 * T + 0.00031060 * pow(T, 2) + 0.000000015 * pow(T, 3));
                e.ejeSemimayor = 0.723329820;
                e.excentricidad = 0.00677188 - 0.000047766 * T + 0.0000000975 * pow(T, 2) + 0.00000000044 * pow(T, 3);
                e.inclinacion = new Grados(3.394662 + 0.0010037 * T - 0.00000088 * pow(T, 2) - 0.000000007 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(76.679920 + 0.9011190 * T + 0.00040665 * pow(T, 2) - 0.000000080 * pow(T, 3));
                e.longitudPerihelion = new Grados(131.563707 + 1.4022188 * T - 0.00107337 * pow(T, 2) - 0.000005315 * pow(T, 3));
                return e;
           case TIERRA:
                e.longitudMedia = new Grados(100.466449 + 36000.7698231 * T + 0.00030368 * pow(T, 2) + 0.000000021 * pow(T, 3));
                e.ejeSemimayor = 1.000001018;
                e.excentricidad = 0.01670862 - 0.000042037 * T - 0.0000001236 * pow(T, 2) + 0.00000000004 * pow(T, 3);
                e.inclinacion = new Grados(0);
                e.longitudNodoAscendente = null;
                e.longitudPerihelion = new Grados(102.937348 + 1.7195269 * T + 0.00045962 * pow(T, 2) + 0.000000499 * pow(T, 3));
                return e;                       
           case MARTE:
                e.longitudMedia = new Grados(355.433275 + 19141.6964746 * T + 0.00031097 * pow(T, 2) + 0.000000015 * pow(T, 3));
                e.ejeSemimayor = 1.523679342;
                e.excentricidad = 0.09340062 + 0.000090483 * T - 0.0000000806 * pow(T, 2) - 0.00000000035 * pow(T, 3);
                e.inclinacion = new Grados(1.849726 - 0.0006010 * T + 0.00001276 * pow(T, 2) - 0.000000006 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(49.558093 + 0.7720923 * T + 0.00001605 * pow(T, 2) + 0.000002325 * pow(T, 3));
                e.longitudPerihelion = new Grados(336.060234 + 1.8410331 * T + 0.00013515 * pow(T, 2) + 0.000000318 * pow(T, 3));
                return e;                   
           case JUPITER:
                e.longitudMedia = new Grados(34.351484 + 3036.3027889 * T + 0.00022374 * pow(T, 2) + 0.000000025 * pow(T, 3));
                e.ejeSemimayor = 5.202603191+0.0000001913*T;
                e.excentricidad = 0.04849485 + 0.000163244 * T - 0.0000004719 * pow(T, 2) - 0.00000000197 * pow(T, 3);
                e.inclinacion = new Grados(1.3032270 - 0.0054966 * T + 0.00000465 * pow(T, 2) - 0.000000004 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(100.464441 + 1.0209550 * T + 0.00040117 * pow(T, 2) + 0.000000569 * pow(T, 3));
                e.longitudPerihelion = new Grados(14.331309 + 1.6126668 * T + 0.00103127 * pow(T, 2) - 0.000004569 * pow(T, 3));
                return e;                
            case SATURNO:
                e.longitudMedia = new Grados(50.077471 + 1223.5110141 * T + 0.00051952 * pow(T, 2) - 0.000000003 * pow(T, 3));
                e.ejeSemimayor = 9.554909596 -0.0000021389*T;
                e.excentricidad = 0.05550862 - 0.000346818 * T - 0.0000006456 * pow(T, 2) + 0.00000000338 * pow(T, 3);
                e.inclinacion = new Grados(2.488878 - 0.0037363 * T - 0.00001516 * pow(T, 2) + 0.000000089 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(113.665524 + 0.8770979 * T - 0.00012067 * pow(T, 2) - 0.000002380 * pow(T, 3));
                e.longitudPerihelion = new Grados(93.056787 + 1.9637694 * T + 0.00083757 * pow(T, 2) + 0.000004899 * pow(T, 3));
                return e;                  
            case URANO:
                e.longitudMedia = new Grados(314.055005 + 429.8640561 * T + 0.00030434 * pow(T, 2) + 0.000000026 * pow(T, 3));
                e.ejeSemimayor = 19.218446062 -0.0000000372*T +0.00000000098*pow(T,2);
                e.excentricidad = 0.04629590 - 0.000027337 * T + 0.0000000790 * pow(T, 2) + 0.00000000025 * pow(T, 3);
                e.inclinacion = new Grados(0.773196 + 0.0007744 * T + 0.00003749 * pow(T, 2) - 0.000000092 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(74.005947 + 0.5211258 * T + 0.00133982 * pow(T, 2) + 0.000018516 * pow(T, 3));
                e.longitudPerihelion = new Grados(173.005159 + 1.4863784 * T + 0.00021450 * pow(T, 2) + 0.000000433 * pow(T, 3));
                return e;                                    
           case NEPTUNO:
                e.longitudMedia = new Grados(304.348665 + 219.8833092 * T + 0.00030926 * pow(T, 2) + 0.000000018 * pow(T, 3));
                e.ejeSemimayor = 30.110386869 -0.0000001663*T +0.00000000069*pow(T,2);
                e.excentricidad = 0.00898809 + 0.000006408 * T - 0.0000000008 * pow(T, 2) - 0.00000000005 * pow(T, 3);
                e.inclinacion = new Grados(1.769952 - 0.0093082 * T - 0.00000708 * pow(T, 2) + 0.000000028 * pow(T, 3));
                e.longitudNodoAscendente = new Grados(131.784057 + 1.1022057 * T + 0.00026006 * pow(T, 2) - 0.000000636 * pow(T, 3));
                e.longitudPerihelion = new Grados(48.123691 + 1.4262677 * T + 0.00037918 * pow(T, 2) - 0.000000003 * pow(T, 3));
                return e;         
                
            default: return null;
        }

    }

}
