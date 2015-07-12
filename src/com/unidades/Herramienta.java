/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unidades;

import com.Excepcion;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Herramienta
{
   public static double seno(Grados grados)
   {
      return Grados.sin(grados);
   }
   
   public static double seno(double grados)
   {
      return sin(grados*PI/180);
   }
   
    public static double coseno(double grados)
   {
      return cos(grados*PI/180);
   }
    
     public static double tangente(double grados)
   {
      return tan(grados*PI/180);
   }
   
   
   
   public static double seno(Hora horas)
   {
      return Hora.sin(horas);
   }
   
   public static double seno(Radianes radianes)
   {
      return java.lang.Math.sin(radianes.getValor());
   }
   
   public static double coseno(Grados grados)
   {
      return Grados.cos(grados);
   }
   
   public static double coseno(Hora horas)
   {
      return Hora.cos(horas);
   }
   
   public static double coseno(Radianes radianes)
   {
      return java.lang.Math.cos(radianes.getValor());
   }
   
   public static double tangente(Grados grados)
   {
      return Grados.tan(grados);
   }
   
   public static double tangente(Hora horas)
   {
      return Hora.tan(horas);
   }
   
   public static double tangente(Radianes radianes)
   {
      return java.lang.Math.tan(radianes.getValor());
   }
   
    public static double aportacionSeno(double[] multiplos, double[] variables, double coeficiente) throws Excepcion
    {
        if (multiplos.length != variables.length)
        {
            throw new Excepcion("Longitudes de múltiplos y variables de cálculo de aportación no coinciden");
        }
        double argumento = 0;
        for (int i = 0; i < multiplos.length; i++)
        {
            argumento += multiplos[i] * variables[i];
        }
        double v = coeficiente * sin(argumento * PI / 180.0);
        return v;

    }
            
    public static double aportacionCoseno(double[] multiplos, double[] variables, double coeficiente) throws Excepcion
    {
        if (multiplos.length != variables.length)
        {
            throw new Excepcion("Longitudes de múltiplos y variables de cálculo de aportación no coinciden");
        }
        double argumento = 0;
        for (int i = 0; i < multiplos.length; i++)
        {
            argumento += multiplos[i] * variables[i];
        }
        double v = coeficiente * cos(argumento * PI / 180.0);
        return v;

    }
   
   
}
