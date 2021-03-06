/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import javafx.beans.property.SimpleStringProperty;
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
    private SimpleStringProperty value;

    /**
     * 
     * @param name Tag name
     * @param value Tag value
     */
    public Tag(String name, String value)
    {
        this.name = name;
        this.value = new SimpleStringProperty(value);
    }

    /**
     * 
     * @param name Tag name
     * @param e XML element for tag
     */
    public Tag(String name, Element e)
    {
        this.name = name;
        value = new SimpleStringProperty(e.getText());
    }

    /**
     * 
     * @return tag value
     */
    public String getValue()
    {
        return value.get();
    }
    
    /**
     * 
     * @return tag value nproperty
     */
    public SimpleStringProperty getValueProperty()
    {
        return value;
    }

    /**
     * 
     * @param value Tag value to set
     */
    public void setValue(String value)
    {
        this.value.set(value);
    }

    /**
     * 
     * @return XML element for tag
     */
    public Element getXMLElement()
    {
        Element raiz = new Element(name);
        raiz.setText(value.get());
        return raiz;
    }
}
