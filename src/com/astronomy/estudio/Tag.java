/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Tag
{

    private String nombre;
    private String valor;

    public Tag(String n, String v)
    {
        this.nombre = n;
        this.valor = v;
    }
    
    public Tag(String n, Element e)
    {
        this.nombre = n;
        valor=e.getText();
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String cadena)
    {
        this.valor = cadena;
    }

    public Element getElementoXML()
    {
        Element raiz = new Element(nombre);
        raiz.setText(valor);
        return raiz;
    }        
}
