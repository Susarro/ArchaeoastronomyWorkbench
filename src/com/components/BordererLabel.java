/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import javafx.scene.control.Label;

/**
 * Borderer label
 *
 * @author MIGUEL_ANGEL
 */
public class BordererLabel extends Label
{
    /**
     *  Constructor
     * 
     * @param name Name
     */
    public BordererLabel(String name)
    {
        super(name);
        getStyleClass().remove("label");
        getStyleClass().add("borderedLabel");
        setMaxWidth(Double.MAX_VALUE);
    }

    /**
     * Constructor
     */
    public BordererLabel()
    {
        this("");
    }

}
