/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Excepcion;
import com.astronomia.sistema.Geodesica;
import com.unidades.Grados;
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
    private Geodesica coordenadas;

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
            if (!this.coordenadas.getLatitud().equals(a.coordenadas.getLatitud()))
            {
                return false;
            }
            if (!this.coordenadas.getLongitud().equals(a.coordenadas.getLongitud()))
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

    public Lugar(String nombre, Geodesica coordenadas, String tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.coordenadas = coordenadas;
    }

    public Lugar(Element e) throws Excepcion
    {
        this.tipo = e.getAttributeValue("tipo");
        this.nombre = e.getChild("nombre").getText();
        try
        {
            coordenadas = new Geodesica(
                    Grados.valueOf(e.getChild("coordenadas").getChild("latitud").getText()),
                    Grados.valueOf(e.getChild("coordenadas").getChild("longitud").getText()),
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
            latitudElemento.setText(getCoordenadas().getLatitud().toString());
            longitudElemento.setText(getCoordenadas().getLongitud().toString());
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
    public Geodesica getCoordenadas()
    {
        return coordenadas;
    }

    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(Geodesica coordenadas)
    {
        this.coordenadas = coordenadas;
    }

}
