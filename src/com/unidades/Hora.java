/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidades;

import com.Excepcion;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Hora
{

    private double valor;

    public double getValor()
    {
        return valor;
    }

    public double getSignedValue()
    {
        if (valor > 12)
        {
            return -(24 - valor);
        }
        else
        {
            return valor;
        }
    }

    public double Ajuste(double valor)
    {
        if (valor < 0)
        {
            return valor + java.lang.Math.ceil(-valor / 24.0) * 24;
        }
        else if (valor > 0 && valor > 24)
        {
            return valor - java.lang.Math.floor(valor / 24.0) * 24;
        }
        else
        {
            return valor;
        }
    }

    public Hora Ajuste()
    {
        return new Hora(this.valor);
    }

    public Hora(double valor)
    {
        this.valor = Ajuste(valor);
    }

    public Hora(double h, double m, double s)
    {
        this.valor = h + m / 60.0 + s / 3600.0;
    }

    public static Hora valueOf(Grados grados)
    {
        return new Hora(grados.getValor() / 15.0);
    }

    public static Hora valueOf(Radianes radianes)
    {
        return new Hora(radianes.getValor() * 180.0 / (java.lang.Math.PI * 15.0));
    }

    public Hora plus(Hora horas)
    {
        return new Hora(Ajuste(this.valor + horas.valor));
    }

    public Hora minus(Hora horas)
    {
        return new Hora(Ajuste(this.valor - horas.valor));
    }

    public Hora by(double f)
    {
        return new Hora(Ajuste(f * this.valor));
    }

    public static double cos(Hora horas)
    {
        return java.lang.Math.cos(Radianes.valueOf(horas).getValor());
    }

    public static double sin(Hora horas)
    {
        return java.lang.Math.sin(Radianes.valueOf(horas).getValor());
    }

    public static double tan(Hora horas)
    {
        return java.lang.Math.tan(Radianes.valueOf(horas).getValor());
    }

    public static Hora acos(double v)
    {
        return Hora.valueOf(new Radianes(java.lang.Math.acos(v)));
    }

    public static Hora asin(double v)
    {
        return Hora.valueOf(new Radianes(java.lang.Math.asin(v)));
    }

    public static Hora atan2(double y, double x)
    {
        return Hora.valueOf(new Radianes(java.lang.Math.atan2(y, x)));
    }

    public int getHora()
    {
        double v = java.lang.Math.abs(valor);
        int horas = (int) v;
        return horas;
    }

    public int getMinuto()
    {
        double v = java.lang.Math.abs(valor);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        return minutos;
    }

    public double getSegundo()
    {
        double v = java.lang.Math.abs(valor);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        int segundos = (int) ((v - horas - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + ((v - horas - minutos / 60.0 - segundos / 3600.0) * 3600000));
        return segundos + milisegundos / 1000.0;
    }

    @Override
    public String toString()
    {
        double v = java.lang.Math.abs(valor);
        int horas = (int) v;
        int minutos = (int) ((v - horas) * 60);
        int segundos = (int) ((v - horas - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + ((v - horas - minutos / 60.0 - segundos / 3600.0) * 3600000));
        String h;

        return String.format("%02d:%02d:%.3f", horas, minutos, (double) segundos + milisegundos / 1000.0).replace(",", ".");

    }

    public static Hora valueOf(String cadena) throws Excepcion
    {
        Hora horas = new Hora(0);
        String str = cadena.replace(",", ".");
        double h;
        if (!str.contains(":"))
        {
            try
            {
                horas.valor = Double.valueOf(str);
                return horas;
            }
            catch (NumberFormatException ex)
            {
                throw new Excepcion("Error formato horas de " + cadena);
            }
        }
        else
        {
            try
            {
                String[] t = str.split(":");
                horas.valor += Integer.valueOf(t[0]);
                if (t.length == 2)
                {
                    horas.valor += Integer.valueOf(t[1]) / 60.0;
                }
                else if (t.length == 3)
                {
                    horas.valor += Integer.valueOf(t[1]) / 60.0 + Double.valueOf(t[2]) / 3600.0;
                }
                else
                {
                    throw new Excepcion("Error formato horas de " + cadena);
                }
            }
            catch (NumberFormatException ex)
            {
                throw new Excepcion("Error formato horas de " + cadena);
            }
        }
        return horas;
    }

}
