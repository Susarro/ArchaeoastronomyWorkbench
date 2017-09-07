/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * Legend pane for axis
 * 
 * @author MIGUEL_ANGEL
 */
class LegendAxis extends FlowPane
{
    /**
     * Axis name
     */
    private final String name;
    /**
     * Selected
     */
    public boolean selected;

    /**
     * 
     * @param name Axis name
     */
    public LegendAxis(String name)
    {
        super(10, 10);
        this.name = name;
        setAlignment(Pos.CENTER);
    }

    /**
     * @return axis name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @param name
     * @return Label by name
     */
    public Label getLabel(String name)
    {
        for (Node n : getChildren())
        {
            if (n instanceof Label)
            {
                if (((Label) n).getText().equals(name))
                {
                    return (Label) n;
                }
            }
        }
        return null;
    }
}
