/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Excepcion;
import com.Global;
import com.astronomia.DiaJuliano;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.HorizontalAparente;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Alineamiento
{

    private Lugar origen;
    private Lugar referencia;
    private Direccion direccion;
    private Declinacion declinacion;
    private Tag descripcion;
    private Tag imagenPath;

    SimpleStringProperty pOrigen;
    SimpleStringProperty pDestino;
    SimpleStringProperty pAcimut;
    SimpleStringProperty pElevacion;
    SimpleStringProperty pDeclinacion;
    SimpleStringProperty pObservaciones;

    public StringProperty origenProperty()
    {
        return pOrigen;
    }

    public StringProperty destinoProperty()
    {
        return pDestino;
    }

    public StringProperty acimutProperty()
    {
        return pAcimut;
    }

    public StringProperty elevacionProperty()
    {
        return pElevacion;
    }

    public StringProperty declinacionProperty()
    {
        return pDeclinacion;
    }

    public StringProperty observacionesProperty()
    {
        return pObservaciones;
    }
    
      
    
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Alineamiento)) return false;
        Alineamiento a= (Alineamiento) obj;
        if(!this.origen.equals(a.origen)) return false;
        if(!this.referencia.equals(a.referencia)) return false;
        if(!this.direccion.equals(a.direccion)) return false;
        if(!this.declinacion.equals(a.declinacion)) return false;
        return true;
    }        

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.origen);
        hash = 59 * hash + Objects.hashCode(this.referencia);
        hash = 59 * hash + Objects.hashCode(this.direccion);
        hash = 59 * hash + Objects.hashCode(this.declinacion);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        hash = 59 * hash + Objects.hashCode(this.pOrigen);
        hash = 59 * hash + Objects.hashCode(this.pDestino);
        hash = 59 * hash + Objects.hashCode(this.pAcimut);
        hash = 59 * hash + Objects.hashCode(this.pElevacion);
        hash = 59 * hash + Objects.hashCode(this.pDeclinacion);
        hash = 59 * hash + Objects.hashCode(this.pObservaciones);
        return hash;
    }
    
    public void set(Alineamiento alineamiento)
    {
        this.origen = alineamiento.origen;
        pOrigen.set(origen.getNombre());
        this.referencia = alineamiento.referencia;
        pDestino.set(referencia.getNombre());
        this.direccion = alineamiento.direccion;
        pAcimut.set(String.format("%.1f", direccion.getAcimut().getSignedValue()).replace(",", "."));
        pElevacion.set(String.format("%.1f", direccion.getElevacion().getSignedValue()).replace(",", "."));
        this.descripcion = alineamiento.descripcion;
        this.imagenPath = alineamiento.imagenPath;
        this.declinacion=alineamiento.declinacion;
        pDeclinacion.set(String.format("%.1f", getDeclinacion().getSignedValue()).replace(",", "."));        
    }        

    public Alineamiento(Lugar origen, String referencia, HorizontalAparente direccion)
    {
        this.origen = origen;
        pOrigen = new SimpleStringProperty(origen.getNombre());
        this.referencia = new Lugar(referencia, null, "referencia");
        pDestino = new SimpleStringProperty(referencia);
        this.direccion = new Direccion(direccion);
        pAcimut = new SimpleStringProperty(String.format("%.1f", direccion.getAcimut().getSignedValue()).replace(",", "."));
        pElevacion = new SimpleStringProperty(String.format("%.1f", direccion.getElevacion().getSignedValue()).replace(",", "."));

        this.descripcion = new Tag("descripcion", "");
        this.imagenPath = new Tag("imagen", "");
        try
        {
            Ecuatorial e = direccion.toEcuatorial(origen.getCoordenadas().getLatitud(), origen.getCoordenadas().getLongitud(), DiaJuliano.valueOf("1/1/-2000"));
            declinacion = new Declinacion(e.getDeclinacion());
            pDeclinacion = new SimpleStringProperty(String.format("%.1f", getDeclinacion().getSignedValue()).replace(",", "."));
        }
        catch (Excepcion ex)
        {
            Global.info.Registra(ex);
        }

    }

    public Alineamiento(Lugar origen, Lugar referencia)
    {
        this.origen = origen;
        pOrigen = new SimpleStringProperty(origen.getNombre());
        this.referencia = referencia;
        pDestino = new SimpleStringProperty(referencia.getNombre());
        this.descripcion = new Tag("descripcion", "");
        this.imagenPath = new Tag("imagen", "");
        try
        {
            this.direccion = new Direccion(HorizontalAparente.valueOf(origen.getCoordenadas(), referencia.getCoordenadas()));
            Ecuatorial e = this.direccion.toEcuatorial(origen.getCoordenadas().getLatitud(), origen.getCoordenadas().getLongitud(), DiaJuliano.valueOf("1/1/-2000"));
            declinacion = new Declinacion(e.getDeclinacion());
            pAcimut = new SimpleStringProperty(String.format("%.1f", direccion.getAcimut().getSignedValue()).replace(",", "."));
            pElevacion = new SimpleStringProperty(String.format("%.1f", direccion.getElevacion().getSignedValue()).replace(",", "."));
            pDeclinacion = new SimpleStringProperty(String.format("%.1f", getDeclinacion().getSignedValue()).replace(",", "."));
        }
        catch (Excepcion ex)
        {
            Logger.getLogger(Alineamiento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Alineamiento(Element e) throws Excepcion
    {
        pObservaciones=new SimpleStringProperty();
        for (Element el : e.getChildren())
        {
            switch (el.getName())
            {
                case "lugar":
                    if (el.getAttributeValue("tipo").equals("origen"))
                    {
                        origen = new Lugar(el);
                        pOrigen = new SimpleStringProperty(origen.getNombre());
                    }
                    else if (el.getAttributeValue("tipo").equals("referencia"))
                    {
                        referencia = new Lugar(el);
                        pDestino = new SimpleStringProperty(referencia.getNombre());
                    }
                    break;
                case "direccion":
                    direccion = new Direccion(el);
                    pAcimut = new SimpleStringProperty(String.format("%.1f", direccion.getAcimut().getSignedValue()).replace(",", "."));
                    pElevacion = new SimpleStringProperty(String.format("%.1f", direccion.getElevacion().getSignedValue()).replace(",", "."));
                    break;
                case "descripcion":
                    descripcion = new Tag("descripcion", el);
                    pObservaciones.set(descripcion.getValor());
                    break;
                 case "imagen":
                    imagenPath = new Tag("imagen", el);                    
                    break;   
                case "declinacion":
                    declinacion = new Declinacion(el);
                    pDeclinacion = new SimpleStringProperty(String.format("%.1f", getDeclinacion().getSignedValue()).replace(",", "."));
                    break;
            }
        }
        if(imagenPath==null) imagenPath = new Tag("imagen", "");  
    }

    public Element getElementoXML()
    {
        Element raiz = new Element("alineamiento");
        raiz.addContent(origen.getElementoXML());
        raiz.addContent(referencia.getElementoXML());
        raiz.addContent(direccion.getElementoXML());
        raiz.addContent(descripcion.getElementoXML());
        raiz.addContent(imagenPath.getElementoXML());
        raiz.addContent(getDeclinacion().getElementoXML());
        return raiz;
    }

    /**
     * @return the origen
     */
    public Lugar getOrigen()
    {
        return origen;
    }

    /**
     * @return the referencia
     */
    public Lugar getReferencia()
    {
        return referencia;
    }

    /**
     * @return the direccion
     */
    public HorizontalAparente getDireccion()
    {
        return direccion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion()
    {
        return descripcion.getValor();
    }
    
    public void setDescripcion(String texto)
    {
        descripcion.setValor(texto);
        pObservaciones.set(texto);        
        
    } 

    /**
     * @return the declinacion
     */
    public Declinacion getDeclinacion()
    {
        return declinacion;
    }

    /**
     * @return the imagen
     */
    public String getImagenPath()
    {
        return imagenPath.getValor();
    }
    
    public void setImagenPath(String texto)
    {
        imagenPath.setValor(texto);
    } 
    
           
}
