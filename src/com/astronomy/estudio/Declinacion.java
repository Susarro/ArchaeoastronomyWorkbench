/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.ProcessException;
import com.units.SexagesimalDegree;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Declinacion extends SexagesimalDegree
{

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Declinacion)) return false;
        Declinacion a= (Declinacion) obj;
        return this.getValue() == a.getValue();
    } 
    
    public Declinacion(double valor)
    {
        super(valor);
    }
    
    public Declinacion(SexagesimalDegree valor)
    {
        super(valor.getValue());
    }
    
    public Declinacion(Element e)
    {
        super(Double.valueOf(e.getValue()));
    }
    
    public void setDeclinacion(String valor) throws ProcessException
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
