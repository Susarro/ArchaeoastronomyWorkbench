/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.esqueleto;

import com.interfaz.esqueleto.Esqueleto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class MessageDialog
{

    public enum TipoMensaje
    {

        AVISO, INFO, ERROR
    };
    
    ModalDialog dialogo;

    public MessageDialog(Esqueleto padre, String msg, TipoMensaje tipoMensaje)
    {
        Image icono;

        HBox p = new HBox(20);
        p.setAlignment(Pos.CENTER_LEFT);

        if (tipoMensaje == TipoMensaje.AVISO)
        {
            icono = new Image(getClass().getResourceAsStream("/com/resources/Cute-Ball-Warning-icon.png"));
            p.getChildren().add(new ImageView(icono));
           
        }
        else if (tipoMensaje == TipoMensaje.INFO)
        {
            icono = new Image(getClass().getResourceAsStream("/com/resources/Cute-Ball-Info-icon.png"));
            p.getChildren().add(new ImageView(icono));          
            
        }
        else if (tipoMensaje == TipoMensaje.ERROR)
        {
            icono = new Image(getClass().getResourceAsStream("/com/resources/Cute-Ball-Stop-icon.png"));
            p.getChildren().add(new ImageView(icono));
        }
        p.getChildren().add(new Label(msg));

        dialogo = new ModalDialog(padre, p, true);
        if (tipoMensaje == TipoMensaje.INFO)
        {
           dialogo.boxButtons.getChildren().remove(dialogo.btnCancelar);            
        }
    }

    public boolean Show()
    {
        return dialogo.ShowModal();
    }

}
