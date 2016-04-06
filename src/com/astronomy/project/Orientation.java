/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.astronomy.coordinate_system.ApparentHorizontal;
import com.units.SexagesimalDegree;
import org.jdom2.Element;

/**
 * Orientation or apparent horizontal coordinates
 * 
 * @author MIGUEL_ANGEL
 */
public class Orientation extends ApparentHorizontal
{

    /**
     * 
     * @param obj Object
     * @return True if equal to object
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Orientation)) return false;
        Orientation a= (Orientation) obj;       
        if(!this.getAzimuth().equals(a.getAzimuth())) return false;
        if(!this.getAltitude().equals(a.getAltitude())) return false;
        return true;        
    }
    
    
    /**
     * 
     * @param azimuth Azimuth
     * @param altitude Apparent altitude
     */
    public Orientation(SexagesimalDegree azimuth, SexagesimalDegree altitude)
    {
        super(azimuth, altitude);
    }
    
    /**
     * 
     * @param ha Apparent horizontal coordinates
     */
    public Orientation(ApparentHorizontal ha)
    {
        super(ha.getAzimuth(), ha.getAltitude());
    }
    
    /**
     * 
     * @param e XML elemento for orientation
     * @throws ProcessException Format error
     */
    public Orientation(Element e) throws ProcessException
    {
        super(SexagesimalDegree.valueOf(e.getChild("acimut").getText()),SexagesimalDegree.valueOf(e.getChild("elevacion").getText()));        
    }
    
    /**
     * 
     * @return XML element for orientation
     */
    public Element getXMLElement()
    {
       Element raiz=new Element("direccion");
       Element acimutElemento=new Element("acimut");
       acimutElemento.setText(String.valueOf(getAzimuth().getSignedValue()));
       Element elevacionElemento=new Element("elevacion");
       elevacionElemento.setText(String.valueOf(getAltitude().getSignedValue()));
       raiz.addContent(acimutElemento);
       raiz.addContent(elevacionElemento);
       return raiz;
    }  

    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }
        
}
