/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author MIGUEL_ANGEL
 */
class LeyendaEje extends FlowPane
{

    private final String nombre;
    public boolean seleccionado;

    public LeyendaEje(String nombre)
    {
        super(10, 10);
        this.nombre = nombre;
        setAlignment(Pos.CENTER);
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    public Label getLabel(String nombre)
    {
        for (Node n : getChildren())
        {
            if (n instanceof Label)
            {
                if (((Label) n).getText().equals(nombre))
                {
                    return (Label) n;
                }
            }
        }
        return null;
    }
}
