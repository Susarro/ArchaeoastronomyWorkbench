/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Excepcion;
import com.astronomia.sistema.HorizontalAparente;
import com.unidades.Grados;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Direccion extends HorizontalAparente
{

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Direccion)) return false;
        Direccion a= (Direccion) obj;       
        if(!this.getAcimut().equals(a.getAcimut())) return false;
        if(!this.getElevacion().equals(a.getElevacion())) return false;
        return true;        
    }
    
    
    public Direccion(Grados acimut, Grados elevacion)
    {
        super(acimut, elevacion);
    }
    
    public Direccion(HorizontalAparente ha)
    {
        super(ha.getAcimut(), ha.getElevacion());
    }
    
    public Direccion(Element e) throws Excepcion
    {
        super(Grados.valueOf(e.getChild("acimut").getText()),Grados.valueOf(e.getChild("elevacion").getText()));        
    }
    
    public Element getElementoXML()
    {
       Element raiz=new Element("direccion");
       Element acimutElemento=new Element("acimut");
       acimutElemento.setText(String.valueOf(getAcimut().getSignedValue()));
       Element elevacionElemento=new Element("elevacion");
       elevacionElemento.setText(String.valueOf(getElevacion().getSignedValue()));
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
