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
public class Grados
{

    private double valor;

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Grados))
        {
            return false;
        }
        return this.getValor() == ((Grados) obj).getValor();
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.valor) ^ (Double.doubleToLongBits(this.valor) >>> 32));
        return hash;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double v)
    {
        valor = v;
    }

    public double getSignedValue()
    {
        if (valor > 180)
        {
            return -(360 - valor);
        }
        else
        {
            return valor;
        }
    }

    static public double Ajuste(double valor)
    {
        if (valor < 0)
        {
            return valor + java.lang.Math.ceil(-valor / 360.0) * 360;
        }
        else if (valor > 0 && valor > 360)
        {
            return valor - java.lang.Math.floor(valor / 360.0) * 360;
        }
        else
        {
            return valor;
        }
    }

    public Grados Ajuste()
    {
        return new Grados(this.valor);
    }

    public Grados AjusteSignado()
    {
        Grados g = new Grados(this.valor);
        double v = g.getSignedValue();
        if (v > 180)
        {
            v = -v + 180;
        }
        g.setValor(v);
        return g;
    }

    public Grados(double valor)
    {
        this.valor = Ajuste(valor);
    }

    public static Grados valueOf(Hora horas)
    {
        return new Grados(horas.getValor() * 15.0);
    }

    public static Grados valueOf(Radianes radianes)
    {
        return new Grados(radianes.getValor() * 180.0 / java.lang.Math.PI);
    }

    public static Grados valueOf(String cadena) throws Excepcion
    {
        Grados grados = new Grados(0);
        String str = cadena.replace("N", "").replace("S", "").replace(",", ".").replace("-", "");
        if (str.contains("º"))
        {
            String[] g = str.split("º");
            try
            {
                grados.valor = Double.valueOf(g[0]);
                if (g.length == 2)
                {
                    String[] m = g[1].split("'");
                    grados.valor += Integer.valueOf(m[0]) / 60.0;
                    if (m.length >= 2)
                    {
                        String s = m[1].replace("''", "");
                        grados.valor += Double.valueOf(s) / 3600.0;
                        if (cadena.contains("S") || cadena.contains("-") || cadena.contains("O"))
                        {
                            grados.valor = -1 * grados.valor;
                        }

                    }

                }
                return grados;
            }
            catch (NumberFormatException ex)
            {
                throw new Excepcion("Error formato grados (gºmm'ss.sss''N) de " + cadena);
            }
        }
        else
        {
            try
            {
                grados.valor = Double.valueOf(str);
                if (cadena.contains("-"))
                {
                    grados.valor = -1 * grados.valor;
                }
                return grados;
            }
            catch (NumberFormatException ex)
            {
                throw new Excepcion("Error formato grados (gºmm'ss.sss''N) de " + cadena);
            }
        }
    }

    public Grados plus(Grados grados)
    {
        return new Grados(Ajuste(this.valor + grados.valor));
    }

    public Grados minus(Grados grados)
    {
        return new Grados(Ajuste(this.valor - grados.valor));
    }

    public static Grados negative(Grados grados)
    {
        return new Grados(Ajuste(-grados.valor));
    }

    public Grados by(double f)
    {
        return new Grados(Ajuste(f * this.valor));
    }

    public static double cos(Grados grados)
    {
        return java.lang.Math.cos(Radianes.valueOf(grados).getValor());
    }

    public static double sin(Grados grados)
    {
        return java.lang.Math.sin(Radianes.valueOf(grados).getValor());
    }

    public static double tan(Grados grados)
    {
        return java.lang.Math.tan(Radianes.valueOf(grados).getValor());
    }

    public static Grados acos(double v)
    {
        return Grados.valueOf(new Radianes(java.lang.Math.acos(v)));
    }

    public static Grados asin(double v)
    {
        return Grados.valueOf(new Radianes(java.lang.Math.asin(v)));
    }

    public static Grados atan2(double y, double x)
    {
        return Grados.valueOf(new Radianes(java.lang.Math.atan2(y, x)));
    }
    
    public int getGrados()
    {
        double v = java.lang.Math.abs(valor);
        return (int) v;
    }
    
    public int getMinutos()
    {
        double v = java.lang.Math.abs(valor);
         int grados = (int) v;
        return (int) ((v - grados) * 60);
    }
    
    public int getSegundos()
    {
        double v = java.lang.Math.abs(valor);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        return (int) ((v - grados - minutos / 60.0) * 3600);
    }
    
    public int getMilisegundos()
    {
        double v = java.lang.Math.abs(valor);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        int segundos = (int) ((v - grados - minutos / 60.0) * 3600);
        return (int) (0.5 + (v - grados - minutos / 60.0 - segundos / 3600.0) * 3600000);
    }

    @Override
    public String toString()
    {
        double v = java.lang.Math.abs(valor);
        int grados = (int) v;
        int minutos = (int) ((v - grados) * 60);
        int segundos = (int) ((v - grados - minutos / 60.0) * 3600);
        int milisegundos = (int) (0.5 + (v - grados - minutos / 60.0 - segundos / 3600.0) * 3600000);
        String h;
        if (valor > 0)
        {
            h = "";
        }
        else
        {
            h = "-";
        }
        return String.format("%s%dº%d'%.3f''", h, grados, minutos, (double) segundos + milisegundos / 1000.0).replace(",", ".");

    }
}
