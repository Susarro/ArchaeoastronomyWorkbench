/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Message dialog
 * 
 * @author MIGUEL_ANGEL
 */
public class MessageDialog
{

    public enum MessageType
    {

        WARNING, INFO, ERROR
    };
    
    ModalDialog dialog;

    /**
     * 
     * @param skeleton Skeleton parent
     * @param msg Message
     * @param messageType WARNING, INFO, ERROR
     */
    public MessageDialog(Skeleton skeleton, String msg, MessageType messageType)
    {
        Image icono;

        HBox p = new HBox(20);
        p.setAlignment(Pos.CENTER_LEFT);

        if (null != messageType)
        switch (messageType)
        {
            case WARNING:
                icono = new Image(getClass().getResourceAsStream("/images/Cute-Ball-Warning-icon.png"));
                p.getChildren().add(new ImageView(icono));
                break;
            case INFO:
                icono = new Image(getClass().getResourceAsStream("/images/Cute-Ball-Info-icon.png"));
                p.getChildren().add(new ImageView(icono));
                break;
            case ERROR:
                icono = new Image(getClass().getResourceAsStream("/images/Cute-Ball-Stop-icon.png"));
                p.getChildren().add(new ImageView(icono));
                break;
            default:
                break;
        }
        p.getChildren().add(new Label(msg));

        dialog = new ModalDialog(skeleton, p, true);
        if (messageType == MessageType.INFO)
        {
           dialog.boxButtons.getChildren().remove(dialog.btnCancel);            
        }
    }

    /**
     * Show dialog
     * @return true if OK
     */
    public boolean show()
    {
        return dialog.showModal();
    }

}
