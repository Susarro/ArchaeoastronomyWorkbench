/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.units.SexagesimalDegree;
import org.jdom2.Element;

/**
 * Declination as sexagesimal degree
 * 
 * @author MIGUEL_ANGEL
 */
public class Declination extends SexagesimalDegree
{

    /**
     * 
     * @param obj Object
     * @return True if equal to object
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Declination)) return false;
        Declination a= (Declination) obj;
        return this.getValue() == a.getValue();
    } 
    /**
     * 
     * @param value declination as sexagesimal degrees as double
     */
    public Declination(double value)
    {
        super(value);
    }
    
    /**
     * 
     * @param degrees Sexagesimal degrees
     */
    public Declination(SexagesimalDegree degrees)
    {
        super(degrees.getValue());
    }
    
    /**
     * 
     * @param e XML element
     */
    public Declination(Element e)
    {
        super(Double.valueOf(e.getValue()));
    }
    
    /**
     *
     * @param strValue Sexagesimal degrees fomated as ###º##'##.###''L where is a
     * digit and L is N (North), S (South), E (East) or W (West)
     * @throws ProcessException Format error
     */
    public void setDeclinacion(String strValue) throws ProcessException
    {        
        valueOf(strValue);
    }        
    
    /**
     * 
     * @return XML element for declination
     */
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
