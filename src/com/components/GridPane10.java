/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class GridPane10 extends GridPane
    {
        public GridPane10()
        {
            super();
            setHgap(10);
            setVgap(10);
            setPadding(new Insets(10, 10, 10, 10));
        }
    }
