/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.interfaz.esqueleto;

import javafx.scene.control.Button;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class RoundButton extends Button
{
   public RoundButton()
   {
       super();              
       getStyleClass().remove("button");
       getStyleClass().add("roundbutton");
   }
   
   public RoundButton(String text)
   {
       super(text);       
       getStyleClass().remove("button");
       getStyleClass().add("roundbutton");
   }
}
