/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Excepcion;
import com.unidades.Grados;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Declinacion extends Grados
{

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Declinacion)) return false;
        Declinacion a= (Declinacion) obj;
        return this.getValor() == a.getValor();
    } 
    
    public Declinacion(double valor)
    {
        super(valor);
    }
    
    public Declinacion(Grados valor)
    {
        super(valor.getValor());
    }
    
    public Declinacion(Element e)
    {
        super(Double.valueOf(e.getValue()));
    }
    
    public void setDeclinacion(String valor) throws Excepcion
    {        
        valueOf(valor);
    }        
    
    public Element getElementoXML()
    {
        Element raiz = new Element("declinacion");
        raiz.setText(String.format("%.1f", this.getSignedValue()).replace(",", "."));
        return raiz;
    }       

    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }
    @Override
    public String toString()
    {
        return String.format("%.1fº",getSignedValue()).replace(",", ".");
    } 
    
    
}
