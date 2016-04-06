/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

/**
 * Pseudomodal dialog 
 * @author MIGUEL_ANGEL
 */
public abstract class PseudoModalDialog extends ModalDialog
{

    /**
     * 
     * @param parent Skeleton parent
     * @param pane User pane
     * @param keyEnter If true key ENTER causes OK
     */
    public PseudoModalDialog(Skeleton parent, Pane pane, boolean keyEnter)
    {
        super(parent, pane, keyEnter);
        btnOK.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(validation()) OK();
            }
        });
    }
    
    
    public abstract boolean validation();

}
