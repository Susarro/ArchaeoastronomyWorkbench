/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

import com.Excepcion;
import com.tipoCalculo;
import com.unidades.Grados;
import static com.unidades.Herramienta.aportacionCoseno;
import static com.unidades.Herramienta.aportacionSeno;
import static com.unidades.Herramienta.coseno;
import com.unidades.Hora;
import static java.lang.Math.pow;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class DiaJuliano
{

    double diaJuliano;

    public DiaJuliano plus(DiaJuliano dia)
    {
        return new DiaJuliano(this.diaJuliano + dia.diaJuliano);
    }

    public DiaJuliano minus(DiaJuliano dia)
    {
        return new DiaJuliano(this.diaJuliano - dia.diaJuliano);
    }

    public boolean isBefore(DiaJuliano dia)
    {
        return this.diaJuliano < dia.diaJuliano;
    }

    public boolean isAfter(DiaJuliano dia)
    {
        return this.diaJuliano > dia.diaJuliano;
    }

    public double getValue()
    {
        return diaJuliano;
    }

    public DiaJuliano(double diaJuliano)
    {
        this.diaJuliano = diaJuliano;
    }

    public DiaJuliano(DiaJuliano diaJuliano)
    {
        this.diaJuliano = diaJuliano.diaJuliano;
    }

    public DiaJuliano(double dia, double mes, double año, HoraUniversal tiempoUniversal)
    {
        double d;
        if (mes == 1 || mes == 2)
        {
            año = año - 1;
            mes = mes + 12;
        }

        int a = (int) (año / 100.0);
        int b = 0;
        if (año > 1582)
        {
            b = 2 - a + (int) (a / 4);
        }
        else if (año == 1582)
        {
            if (mes > 10)
            {
                b = 2 - a + (int) (a / 4);
            }
        }

        d = (int) (365.25 * (año + 4716)) + (int) (30.6001 * (mes + 1)) + dia + b - 1524.5;

        diaJuliano = d + tiempoUniversal.getValor() / 24.0;
    }

    public DiaJuliano(double dia, double mes, double año)
    {
        this(dia, mes, año, new HoraUniversal(0));
    }

    public Fecha getFecha()
    {
        Fecha fecha = new Fecha();
        
        int iZ = (int) (diaJuliano + 0.5);
        double iF = diaJuliano + 0.5 - iZ;
        int iA;
        if (iZ < 2299161)
        {
            iA = iZ;
        }
        else
        {
            int temp = (int) ((iZ - 1867216.25) / 36524.25);
            iA = iZ + 1 + temp - (int) (temp / 4);
        }
        int iB = iA + 1524;
        int iC = (int) ((iB - 122.1) / 365.25);
        int iD = (int) (365.25 * iC);
        int iE = (int) ((iB - iD) / 30.6001);
        int dia = (int) (iB - iD - (int) (30.6001 * iE) + iF);
        int mes = 0;
        int año = 0;
        if (iE < 14)
        {
            mes = iE - 1;
        }
        else if (iE == 14 || iE == 15)
        {
            mes = iE - 13;
        }
        if (mes > 2)
        {
            año = iC - 4716;
        }
        else if (mes == 1 || mes == 2)
        {
            año = iC - 4715;
        }
        fecha.setDia(dia);
        fecha.setAnno(año);
        fecha.setMes(mes);
        Hora h=getTiempoUniversal();
        
        return fecha;
    }

    @Override
    public String toString()
    {
        Fecha fecha=getFecha();        
        return fecha.toString() + " " + getTiempoUniversal().toString();
    }

    public Hora getTiempoUniversal()
    {
        return new Hora((this.getValue() + 0.5 - (int) (this.getValue() + 0.5)) * 24.0);
    }

    public static DiaJuliano valueOf(String cadena) throws Excepcion
    {
        double dia, mes, año, horaUniversal;
        try
        {
            String str = cadena.replace(",", ".");
            String[] t = str.split(" ");
            String f = t[0];
            String h;

            if (t.length == 2)
            {
                h = t[1];
            }
            else
            {
                h = "00:00:00";
            }
            String[] ff = f.split("/");
            String[] hh = h.split(":");

            if (ff.length == 3)
            {
                dia = Integer.valueOf(ff[0]);
                mes = Integer.valueOf(ff[1]);
                año = Integer.valueOf(ff[2]);
            }
            else
            {
                throw new Excepcion("Error formato fecha de " + cadena);
            }

            if (hh.length == 1)
            {
                horaUniversal = Integer.valueOf(hh[0]);

            }
            else if (hh.length == 2)
            {
                horaUniversal = Integer.valueOf(hh[0]) + Integer.valueOf(hh[1]) / 60.0;

            }
            else if (hh.length == 3)
            {
                horaUniversal = Integer.valueOf(hh[0]) + Integer.valueOf(hh[1]) / 60.0 + Double.valueOf(hh[2]) / 3600.0;

            }
            else
            {
                throw new Excepcion("Error formato fecha de " + cadena);
            }
        }
        catch (NumberFormatException ex)
        {
            throw new Excepcion("Error formato fecha de " + cadena);
        }
        return new DiaJuliano(dia, mes, año, new HoraUniversal(horaUniversal));

    }

    public double getSiglosDesde2000()
    {
        //double d=diaJuliano-getTiempoUniversal().getValue()/24;
        double d = diaJuliano;
        return (d - 2451545) / 36525;
    }

    static public double getSiglosDesde2000(double dj)
    {

        return (dj - 2451545) / 36525;
    }

    // Ángulo horario Greenwich medio para el punto vernal
    public Hora getHoraSideralMedia() throws Excepcion
    {
        double T = DiaJuliano.getSiglosDesde2000(diaJuliano - getTiempoUniversal().getValor() / 24);
        Hora mst = Hora.valueOf("6:41:50.54841");
        double ss = 8640184.812866 * T + 0.093104 * pow(T, 2) - 0.0000062 * pow(T, 3);
        return new Hora(mst.getValor() + ss / 3600);
    }

    public Grados getNutacionLongitud() throws Excepcion
    {
        double T = getSiglosDesde2000();
        //longitud del nodo ascendente de la órbita lunar media en la ecliptica medido desde el equinoccio medio
        double omega = 125.04452 - 1934.136261 * T + 0.0020708 * pow(T, 2) + pow(T, 3) / 450000;
        //longitud media del sol
        Grados LS = new Grados(280.4665 + 36000.7698 * T);
        //longitud media de la luna
        Grados LM = new Grados(218.3165 + 481267.8813 * T);
        //elongación media de la luna desde el sol
        double D = 297.85036 + 445267.111480 * T - 0.001914 * pow(T, 2) + pow(T, 3) / 189474;
        //anomalía media del sol (Tierra)
        double MS = 357.52772 + 35999.050340 * T - 0.0001603 * pow(T, 2) - pow(T, 3) / 300000;
        //anomalía media de la Luna
        double MM = 134.96298 + 477198.867398 * T + 0.0086972 * pow(T, 2) + pow(T, 3) / 56250;
        //argumento de latitud de la luna
        double F = 93.27191 + 483202.017538 * T - 0.0036825 * pow(T, 2) + pow(T, 3) / 327270;

        double nl = 0;
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -171996 - 174.2 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -13187 - 1.6 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -2274 - 0.2 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 0, 0, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 2062 + 0.2 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 1426 - 3.4 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 712 + 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -517 + 1.2 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -386 - 0.4 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -301));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 217 - 0.5 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -158));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 129 + 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 123));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 63));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 63 + 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -59));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -58 - 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -51));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 2, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 48));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, -2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 46));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -38));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -31));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 2, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 29));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 29));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 0, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 26));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 0, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -22));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 21));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 2, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 17 - 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 16));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 2, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -16 + 0.1 * T));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -15));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -13));
        nl = (nl + aportacionSeno(new double[]
        {
            0, -1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -12));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 2, -2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 11));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -10));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -8));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + aportacionSeno(new double[]
        {
            0, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, -2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -6));
        nl = (nl + aportacionSeno(new double[]
        {
            2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -6));
        nl = (nl + aportacionSeno(new double[]
        {
            0, -1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, -1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -5));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 0, 2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, -2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 4));
        nl = (nl + aportacionSeno(new double[]
        {
            -1, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + aportacionSeno(new double[]
        {
            -2, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + aportacionSeno(new double[]
        {
            1, 0, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -4));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 1, 2, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, -2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            -1, -1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 1, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            0, -1, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            2, -1, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            0, 0, 3, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionSeno(new double[]
        {
            2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = nl / 10000;

        return new Grados(nl / 3600);
    }

    public HoraSideral getHoraSideralGreenwich(Grados oblicuidadEcliptica) throws Excepcion
    {
        double hs = this.getTiempoUniversal().getValor() * 1.00273790935 + getHoraSideralMedia().getValor();
        double corr = getNutacionLongitud().getValor();
        if (corr > 180)
        {
            corr = -(360 - corr);
        }
        corr = corr * coseno(oblicuidadEcliptica) / 15;
        return new HoraSideral(hs + corr);
    }

    public Grados getNutacionOblicuidadEcliptica() throws Excepcion
    {
        double T = getSiglosDesde2000();
        //longitud del nodo ascendente de la órbita lunar media en la ecliptica medido desde el equinoccio medio
        double omega = 125.04452 - 1934.136261 * T + 0.0020708 * pow(T, 2) + pow(T, 3) / 450000;
        //longitud media del sol
        Grados LS = new Grados(280.4665 + 36000.7698 * T);
        //longitud media de la luna
        Grados LM = new Grados(218.3165 + 481267.8813 * T);
        //elongación media de la luna desde el sol
        double D = 297.85036 + 445267.111480 * T - 0.001914 * pow(T, 2) + pow(T, 3) / 189474;
        //anomalía media del sol (Tierra)
        double MS = 357.52772 + 35999.050340 * T - 0.0001603 * pow(T, 2) - pow(T, 3) / 300000;
        //anomalía media de la Luna
        double MM = 134.96298 + 477198.867398 * T + 0.0086972 * pow(T, 2) + pow(T, 3) / 56250;
        //argumento de latitud de la luna
        double F = 93.27191 + 483202.017538 * T - 0.0036825 * pow(T, 2) + pow(T, 3) / 327270;

        double nl = 0;
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 92025 + 8.9 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5736 - 3.1 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 977 - 0.5 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 0, 0, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -895 + 0.5 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 1, 0, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 54 - 0.1 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 1, 0, 0
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -7));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 224 - 0.6 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 200));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 129 - 0.1 * T));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -95 + 0.3 * T));

        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -70));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -53));

        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -33));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, -1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 26));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 32));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 27));

        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, -2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -24));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 16));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 13));

        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -12));

        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -10));

        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, -1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -8));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 2, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 9));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 1, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 7));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, -1, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 6));

        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, -1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 5));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, 1, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));

        nl = (nl + aportacionCoseno(new double[]
        {
            0, -1, 0, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 2, 2, 2
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 1, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, -3));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, -2, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionCoseno(new double[]
        {
            2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = (nl + aportacionCoseno(new double[]
        {
            -2, -1, 0, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionCoseno(new double[]
        {
            -2, 0, 0, 0, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));
        nl = (nl + aportacionCoseno(new double[]
        {
            0, 0, 2, 2, 1
        }, new double[]
        {
            D, MS, MM, F, omega
        }, 3));

        nl = nl / 10000;

        return new Grados(nl / 3600);
    }

    public Grados getOblicuidadEclipticaMedia(tipoCalculo tipo) throws Excepcion
    {
        double T = getSiglosDesde2000();
        double a;
        if (tipo == tipoCalculo.PRECISO)
        {
            double U = T / 100;
            a = -4680.93 * U - 1.55 * pow(U, 2) + 1999.25 * pow(U, 3) - 51.38 * pow(U, 4) - 249.67 * pow(U, 5) - 39.05 * pow(U, 6) + 7.12 * pow(U, 7) + 27.87 * pow(U, 8) + 5.79 * pow(U, 9) + 2.45 * pow(U, 10);
        }
        else
        {
            a = -46.8150 * T - 0.00059 * pow(T, 2) + 0.001813 * pow(T, 3);
        }

        return Grados.valueOf("23º26'21.448''").plus(new Grados(a / 3600));
    }

    public Grados getOblicuidadEcliptica(tipoCalculo tipo) throws Excepcion
    {
        Grados oe = getOblicuidadEclipticaMedia(tipo);
        Grados noe = getNutacionOblicuidadEcliptica();
        return oe.plus(noe);
    }

    public Grados getExcentricidadOrbitaTierra()
    {
        double T = getSiglosDesde2000();
        double e = 0.016708617 - 0.000042037 * T - 0.0000001236 * pow(T, 2);
        return new Grados(e);
    }

    public Grados getLongitudPerihelionTierra()
    {
        double T = getSiglosDesde2000();
        double pi = 102.93735 + 1.71953 * T + 0.00046 * pow(T, 2);
        return new Grados(pi);
    }

}
