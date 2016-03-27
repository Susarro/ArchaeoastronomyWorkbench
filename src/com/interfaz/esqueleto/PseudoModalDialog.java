/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.esqueleto;

import com.interfaz.esqueleto.ModalDialog;
import com.interfaz.esqueleto.Skeleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

/**
 *
 * @author MIGUEL_ANGEL
 */
public abstract class PseudoModalDialog extends ModalDialog
{

    public PseudoModalDialog(Skeleton padre, Pane pane, boolean keyEnter)
    {
        super(padre, pane, keyEnter);
        btnAceptar.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {                
                if(Validacion()) Aceptar();
            }
        });
    }
    
    public abstract boolean Validacion();

}
