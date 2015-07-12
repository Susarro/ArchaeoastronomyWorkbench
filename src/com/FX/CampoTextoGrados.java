/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FX;

import com.Excepcion;
import com.unidades.Grados;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class CampoTextoGrados extends CampoTexto
{

    public CampoTextoGrados(Grados grados)
    {
        super(grados.toString());
        textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            try
            {
                Grados g = Grados.valueOf(getText());
                setStyle("");
            }
            catch (Excepcion ex)
            {
                setStyle("-fx-background-color: red");
            }
        });
    }

    public Grados getGrados()
    {
        try
        {
            return Grados.valueOf(getText());

        }
        catch (Excepcion ex)
        {
            return null;
        }
    }

}
