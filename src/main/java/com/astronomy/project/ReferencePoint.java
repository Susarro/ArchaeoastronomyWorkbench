/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.astronomy.coordinate_system.Geographic;
import com.units.SexagesimalDegree;
import java.util.Objects;
import org.jdom2.Element;

/**
 * Name and geographical coordinates of a reference point
 *
 * @author MIGUEL_ANGEL
 */
public class ReferencePoint
{

    /**
     * Name
     */
    private String name;
    /**
     * Type
     */
    private String type;
    /**
     * Geographic coordinates
     */
    private Geographic coordinates;

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ReferencePoint))
        {
            return false;
        }
        ReferencePoint a = (ReferencePoint) obj;
        if (!this.name.equals(a.name))
        {
            return false;
        }
        if (this.coordinates != null && a.coordinates == null)
        {
            return false;
        }
        if (this.coordinates == null && a.coordinates != null)
        {
            return false;
        }
        if (this.coordinates != null && a.coordinates != null)
        {
            if (!this.coordinates.getLatitude().equals(a.coordinates.getLatitude()))
            {
                return false;
            }
            if (!this.coordinates.getLongitude().equals(a.coordinates.getLongitude()))
            {
                return false;
            }
            if (this.coordinates.getElevation() != a.coordinates.getElevation())
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
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.coordinates);
        return hash;
    }

    /**
     * 
     * @param name Name
     * @param coordinates Geographic coordinates
     * @param type Type
     */
    public ReferencePoint(String name, Geographic coordinates, String type)
    {
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    /**
     * 
     * @param e Reference point as XML element
     * @throws ProcessException Format error
     */
    public ReferencePoint(Element e) throws ProcessException
    {
        this.type = e.getAttributeValue("tipo");
        this.name = e.getChild("nombre").getText();
        try
        {
            coordinates = new Geographic(
                    SexagesimalDegree.valueOf(e.getChild("coordenadas").getChild("latitud").getText()),
                    SexagesimalDegree.valueOf(e.getChild("coordenadas").getChild("longitud").getText()),
                    Double.valueOf(e.getChild("coordenadas").getChild("altitud").getText()));
        }
        catch (NullPointerException ex)
        {
            coordinates = null;
        }
    }

    @Override
    public String toString()
    {
        return getName() + " " + getCoordinates().toString();
    }

    /**
     * 
     * @return Reference point as XML element
     */
    public Element getElementoXML()
    {
        Element raiz = new Element("lugar");
        raiz.setAttribute("tipo", getType());
        Element nombreElemento = new Element("nombre");
        nombreElemento.setText(getName());
        raiz.addContent(nombreElemento);
        if (getCoordinates() != null)
        {
            Element coordenadasElemento = new Element("coordenadas");
            Element latitudElemento = new Element("latitud");
            Element longitudElemento = new Element("longitud");
            Element altitudElemento = new Element("altitud");
            latitudElemento.setText(getCoordinates().getLatitude().toString());
            longitudElemento.setText(getCoordinates().getLongitude().toString());
            altitudElemento.setText(String.valueOf(getCoordinates().getElevation()));
            coordenadasElemento.addContent(latitudElemento);
            coordenadasElemento.addContent(longitudElemento);
            coordenadasElemento.addContent(altitudElemento);
            raiz.addContent(coordenadasElemento);
        }
        return raiz;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return geographic coordenadas
     */
    public Geographic getCoordinates()
    {
        return coordinates;
    }

    /**
     * @param coordinates geographic coordenadas to set
     */
    public void setCoordinates(Geographic coordinates)
    {
        this.coordinates = coordinates;
    }

}
