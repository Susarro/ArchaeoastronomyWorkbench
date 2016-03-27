/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import javafx.scene.paint.Color;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class SimpleConfigSerie
{

    private Color color;
    private String nombre;
    private int grosor;
    private String simbolo;

    public SimpleConfigSerie(String nombre, Color color, int grosor, String simbolo)
    {
        this.nombre = nombre;
        this.color = color;
        this.grosor = grosor;
        this.simbolo = simbolo;

    }

    public SimpleConfigSerie(String nombre)
    {
        this.nombre = nombre;
        color = null;
        grosor = 3;
        simbolo = "nulo";

    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return the grosor
     */
    public int getGrosor()
    {
        return grosor;
    }

    /**
     * @param grosor the grosor to set
     */
    public void setGrosor(int grosor)
    {
        this.grosor = grosor;
    }

    /**
     * @return the simbolo
     */
    public String getSimbolo()
    {
        return simbolo;
    }

    /**
     * @param simbolo the simbolo to set
     */
    public void setSimbolo(String simbolo)
    {
        this.simbolo = simbolo;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    @Override
    public String toString()
    {
        return getNombre();
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

}
