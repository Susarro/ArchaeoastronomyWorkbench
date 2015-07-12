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
public class Radianes
{
    private double valor;
    
    public double getValor()
    {
        return valor;
    }        
    
    public Radianes(double valor)
    {
        this.valor=valor;
    }        
    
    static public Radianes valueOf(Grados grados)
    {
         return new Radianes(grados.getValor()*java.lang.Math.PI/180.0);
    }
    
    static public Radianes valueOf(Hora horas)
    {
         return new Radianes(horas.getValor()*15.0*java.lang.Math.PI/180.0);
    }
    
    @Override
    public String toString()
    {                
        return Grados.valueOf(this).toString();
    }
    
    public static Radianes valueOf(String cadena) throws Excepcion
    {
        try
        {
            return new Radianes(Double.valueOf(cadena.replace(",", ".")));
        }
        catch(NumberFormatException ex)
        {
             throw new Excepcion("Error formato radianes de " + cadena);
        }    
        
    }        
    
}
