/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FX;

import javafx.scene.control.Label;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class LabelBorde extends Label
    {

        public LabelBorde(String nombre)
        {
            super(nombre);
            getStyleClass().remove("label");
            getStyleClass().add("borderedLabel");
            setMaxWidth(Double.MAX_VALUE);
        }

        public LabelBorde()
        {
            this("");
        }

    }
