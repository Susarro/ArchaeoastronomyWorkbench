/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.interfaz.skeleton;

import javafx.scene.control.Button;

/**
 * Round button
 * 
 * @author MIGUEL_ANGEL
 */
public class RoundButton extends Button
{
   /**
    * default constructor
    */
    public RoundButton()
   {
       super();              
       getStyleClass().remove("button");
       getStyleClass().add("roundbutton");
   }
   
    /**
     * 
     * @param text Button text
     */
   public RoundButton(String text)
   {
       super(text);       
       getStyleClass().remove("button");
       getStyleClass().add("roundbutton");
   }
}
