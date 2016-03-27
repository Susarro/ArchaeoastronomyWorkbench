/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.ProcessException;
import com.astronomy.coordinate_system.Geographic;
import com.units.SexagesimalDegree;
import java.util.Objects;
import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Lugar
{

    private String nombre;
    private String tipo;
    private Geographic coordenadas;

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Lugar))
        {
            return false;
        }
        Lugar a = (Lugar) obj;
        if (!this.nombre.equals(a.nombre))
        {
            return false;
        }
        if (this.coordenadas != null && a.coordenadas == null)
        {
            return false;
        }
        if (this.coordenadas == null && a.coordenadas != null)
        {
            return false;
        }
        if (this.coordenadas != null && a.coordenadas != null)
        {
            if (!this.coordenadas.getLatitude().equals(a.coordenadas.getLatitude()))
            {
                return false;
            }
            if (!this.coordenadas.getLongitude().equals(a.coordenadas.getLongitude()))
            {
                return false;
            }
            if (this.coordenadas.getAltitud() != a.coordenadas.getAltitud())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + Objects.hashCode(this.tipo);
        hash = 79 * hash + Objects.hashCode(this.coordenadas);
        return hash;
    }

    public Lugar(String nombre, Geographic coordenadas, String tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.coordenadas = coordenadas;
    }

    public Lugar(Element e) throws ProcessException
    {
        this.tipo = e.getAttributeValue("tipo");
        this.nombre = e.getChild("nombre").getText();
        try
        {
            coordenadas = new Geographic(
                    SexagesimalDegree.valueOf(e.getChild("coordenadas").getChild("latitud").getText()),
                    SexagesimalDegree.valueOf(e.getChild("coordenadas").getChild("longitud").getText()),
                    Double.valueOf(e.getChild("coordenadas").getChild("altitud").getText()));
        }
        catch (NullPointerException ex)
        {
            coordenadas = null;
        }
    }

    @Override
    public String toString()
    {
        return getNombre() + " " + getCoordenadas().toString();
    }

    public Element getElementoXML()
    {
        Element raiz = new Element("lugar");
        raiz.setAttribute("tipo", getTipo());
        Element nombreElemento = new Element("nombre");
        nombreElemento.setText(getNombre());
        raiz.addContent(nombreElemento);
        if (getCoordenadas() != null)
        {
            Element coordenadasElemento = new Element("coordenadas");
            Element latitudElemento = new Element("latitud");
            Element longitudElemento = new Element("longitud");
            Element altitudElemento = new Element("altitud");
            latitudElemento.setText(getCoordenadas().getLatitude().toString());
            longitudElemento.setText(getCoordenadas().getLongitude().toString());
            altitudElemento.setText(String.valueOf(getCoordenadas().getAltitud()));
            coordenadasElemento.addContent(latitudElemento);
            coordenadasElemento.addContent(longitudElemento);
            coordenadasElemento.addContent(altitudElemento);
            raiz.addContent(coordenadasElemento);
        }
        return raiz;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo()
    {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    /**
     * @return the coordenadas
     */
    public Geographic getCoordenadas()
    {
        return coordenadas;
    }

    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(Geographic coordenadas)
    {
        this.coordenadas = coordenadas;
    }

}
