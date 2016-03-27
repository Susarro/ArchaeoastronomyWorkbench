/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import com.ProcessException;
import com.units.SexagesimalDegree;
import javafx.beans.value.ObservableValue;

/**
 * Text field in sexageximal degrees
 * 
 * @author MIGUEL_ANGEL
 */
public class TextFieldInDegrees extends FixedWidthTextField
{

    /**
     * 
     * @param degrees Sexageximal degrees
     */
    public TextFieldInDegrees(SexagesimalDegree degrees)
    {
        super(degrees.toString());
        textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            try
            {
                SexagesimalDegree g = SexagesimalDegree.valueOf(getText());
                setStyle("");
            }
            catch (ProcessException ex)
            {
                setStyle("-fx-background-color: red");
            }
        });
    }

    /**
     * 
     * @return SexagesimalDegree value. Null if exception
     */
    public SexagesimalDegree getValue()
    {
        try
        {
            return SexagesimalDegree.valueOf(getText());

        }
        catch (ProcessException ex)
        {
            return null;
        }
    }

}
