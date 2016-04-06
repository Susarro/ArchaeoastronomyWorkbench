/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import org.jdom2.Element;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Tag
{

    /**
     * Tag name
     */
    private String name;
    /**
     * Tag value
     */
    private String value;

    /**
     * 
     * @param name Tag name
     * @param value Tag value
     */
    public Tag(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * 
     * @param name Tag name
     * @param e XML element for tag
     */
    public Tag(String name, Element e)
    {
        this.name = name;
        value = e.getText();
    }

    /**
     * 
     * @return tag value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * 
     * @param value Tag value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * 
     * @return XML element for tag
     */
    public Element getXMLElement()
    {
        Element raiz = new Element(name);
        raiz.setText(value);
        return raiz;
    }
}
