/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.astronomia.sistema.Ecuatorial;
import com.enumEstrella;
import static com.enumEstrella.BETA_CRUCIS;
import com.enumPlaneta;
import com.unidades.Grados;
import com.unidades.Hora;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Catalogo
{

    public static Estrella getEstrella(enumEstrella estrella) throws Excepcion
    {
        Ecuatorial coord;
        Ecuatorial mp;
        switch (estrella)
        {
            case ALPHA_CRUCIS:
                coord = new Ecuatorial(Grados.valueOf("-63º05'56.7343''"), Hora.valueOf("12:26:35.89522"));
                mp = new Ecuatorial(new Grados(-14.86 / 3600000), new Hora(-35.83 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.77);
            case BETA_CRUCIS:
                coord = new Ecuatorial(Grados.valueOf("-59º41'19.5792''"), Hora.valueOf("12:47:43.26877"));
                mp = new Ecuatorial(new Grados(-16.185 / 3600000), new Hora(-42.97 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.25);
            case DELTA_CRUCIS:
                coord = new Ecuatorial(Grados.valueOf("-58º44'56.1369''"), Hora.valueOf("12:15:08.71673"));
                mp = new Ecuatorial(new Grados(-10.36 / 3600000), new Hora(-35.81 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 2.79);            
            case GAMMA_CRUCIS:
                coord = new Ecuatorial(Grados.valueOf("-57º06'47.5684''"), Hora.valueOf("12:31:09.95961"));
                mp = new Ecuatorial(new Grados(-265.08 / 3600000), new Hora(-28.23 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.63);                
            case ALPHA_CENTAURI:
                coord = new Ecuatorial(Grados.valueOf("-60º50'02.308''"), Hora.valueOf("14:39:36.4951"));
                mp = new Ecuatorial(new Grados(699 / 3600000), new Hora(-3642 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), -0.01);
            case BETA_CENTAURI:
                coord = new Ecuatorial(Grados.valueOf("-60º22'22.9266''"), Hora.valueOf("14:03:49.40535"));
                mp = new Ecuatorial(new Grados(-23.16 / 3600000), new Hora(-33.27 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.6);
            case ETA_URSAE_MAJORIS:
                coord = new Ecuatorial(Grados.valueOf("49º18'48''"), Hora.valueOf("13:47:32.4"));
                mp = new Ecuatorial(new Grados(14.91 / 3600000), new Hora(121.17 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.85);
            case ALPHA_BOOTIS:
                coord = new Ecuatorial(Grados.valueOf("21º36'47.35''"), Hora.valueOf("14:21:23.29"));
                mp = new Ecuatorial(new Grados(-1999.4 / 3600000), new Hora(-1093.45 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), -0.04);
            case EPSILON_ORIONIS:
                coord = new Ecuatorial(Grados.valueOf("-0.1º12'06.90''"), Hora.valueOf("05:36:12.81"));
                mp = new Ecuatorial(new Grados(-1.06 / 3600000), new Hora(1.49 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.70);
            case PLEYADES:
                coord = new Ecuatorial(Grados.valueOf("24º7'"), Hora.valueOf("03:47:24"));
                mp = new Ecuatorial(new Grados(0 / 3600000), new Hora(0 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.60);
            case ETA_TAURI:
                coord = new Ecuatorial(Grados.valueOf("24º6'18.49''"), Hora.valueOf("03:47:29.077"));
                mp = new Ecuatorial(new Grados(-43.67 / 3600000), new Hora(19.34 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 2.873);
            case ALPHA_TAURI:
                coord = new Ecuatorial(Grados.valueOf("16º30'33.49''"), Hora.valueOf("04:35:55.239"));
                mp = new Ecuatorial(new Grados(-189.35 / 3600000), new Hora(62.78 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.75);
            case ALPHA_CANIS_MAJORIS:
                coord = new Ecuatorial(Grados.valueOf("-16º42'58.017''"), Hora.valueOf("06:45:09.9173"));
                mp = new Ecuatorial(new Grados(-1223.14 / 3600000), new Hora(-546.05 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), -1.47);
            case ALPHA_VIRGINIS:
                coord = new Ecuatorial(Grados.valueOf("-11º09'40.75''"), Hora.valueOf("13:25:11.579"));
                mp = new Ecuatorial(new Grados(-30.65 / 3600000), new Hora(-42.35 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.04);
            case ALPHA_PISCIS_AUSTRINI:
                coord = new Ecuatorial(Grados.valueOf("-29º37'20.050''"), Hora.valueOf("22:57:39.0465"));
                mp = new Ecuatorial(new Grados(-158.98 / 3600000), new Hora(-331.11 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.16);
            case ALPHA_SCORPII:
                coord = new Ecuatorial(Grados.valueOf("-26º25'55.2094''"), Hora.valueOf("16:29:24.45970"));
                mp = new Ecuatorial(new Grados(-23.30 / 3600000), new Hora(-12.11 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.16);
            case ALPHA_CANIS_MINORIS:
                coord = new Ecuatorial(Grados.valueOf("5º13'29.9552''"), Hora.valueOf("7:39:18.11950"));
                mp = new Ecuatorial(new Grados(-1036.8 / 3600000), new Hora(-714.590 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.34);
            case ALPHA_ORIONIS:
                coord = new Ecuatorial(Grados.valueOf("7º24'25.426''"), Hora.valueOf("5:55:10.30530"));
                mp = new Ecuatorial(new Grados(9.56 / 3600000), new Hora(24.95 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.42);
            case ALPHA_AURIGAE:
                coord = new Ecuatorial(Grados.valueOf("45º59'52.768''"), Hora.valueOf("5:16:41.3591"));
                mp = new Ecuatorial(new Grados(-427.11 / 3600000), new Hora(75.52 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.91);    
            case ALPHA_CYGNI:
                coord = new Ecuatorial(Grados.valueOf("45º16'49''"), Hora.valueOf("20:41:25.9"));
                mp = new Ecuatorial(new Grados(1.95 / 3600000), new Hora(1.99 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.25);        
            case ALPHA_LYRAE:
                coord = new Ecuatorial(Grados.valueOf("38º47'01.2802''"), Hora.valueOf("18:36:56.33635"));
                mp = new Ecuatorial(new Grados(286.23 / 3600000), new Hora(200.94 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.03);            
            case ALPHA_AQUILAE:
                coord = new Ecuatorial(Grados.valueOf("8º52'05.9563''"), Hora.valueOf("19:50:46.99855"));
                mp = new Ecuatorial(new Grados(385.29 / 3600000), new Hora(536.23 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 0.77);
            case ALPHA_GEMINORUM:
                coord = new Ecuatorial(Grados.valueOf("31º53'17.8160''"), Hora.valueOf("07:34:35.87319"));
                mp = new Ecuatorial(new Grados(-145.19 / 3600000), new Hora(-191.45 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.93);    
            case BETA_GEMINORUM:
                coord = new Ecuatorial(Grados.valueOf("28º01'34.3160''"), Hora.valueOf("07:45:18.94987"));
                mp = new Ecuatorial(new Grados(-45.8 / 3600000), new Hora(-626.55 / 3600000));
                return new Estrella(estrella.toString(), coord, mp, DiaJuliano.valueOf("1/1/2000"), 1.14);        

            default:
                return null;
        }
    }

    public static String[] getStrEstrellas()
    {
        List<String> nombres = new ArrayList<>();
        for (enumEstrella estrella : enumEstrella.values())
        {
            nombres.add(estrella.toString());
        }
        return nombres.toArray(new String[nombres.size()]);
    }

    public static enumEstrella getEnumEstrella(String strEstrella)
    {
        for (enumEstrella estrella : enumEstrella.values())
        {
            if (strEstrella.equals(estrella.toString()))
            {
                return estrella;
            }
        }
        return null;
    }

    public static enumPlaneta getEnumPlaneta(String strPlaneta)
    {
        for (enumPlaneta planeta : enumPlaneta.values())
        {
            if (strPlaneta.equals(planeta.toString()))
            {
                return planeta;
            }
        }
        return null;
    }
}
