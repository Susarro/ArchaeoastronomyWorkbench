/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.ProcessException;
import com.astronomy.coordinate_system.ApparentHorizontal;
import com.units.SexagesimalDegree;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Direccion extends ApparentHorizontal
{

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Direccion)) return false;
        Direccion a= (Direccion) obj;       
        if(!this.getAzimuth().equals(a.getAzimuth())) return false;
        if(!this.getAltitude().equals(a.getAltitude())) return false;
        return true;        
    }
    
    
    public Direccion(SexagesimalDegree acimut, SexagesimalDegree elevacion)
    {
        super(acimut, elevacion);
    }
    
    public Direccion(ApparentHorizontal ha)
    {
        super(ha.getAzimuth(), ha.getAltitude());
    }
    
    public Direccion(Element e) throws ProcessException
    {
        super(SexagesimalDegree.valueOf(e.getChild("acimut").getText()),SexagesimalDegree.valueOf(e.getChild("elevacion").getText()));        
    }
    
    public Element getElementoXML()
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
