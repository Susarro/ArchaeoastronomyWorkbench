/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import javafx.scene.control.TextField;

/**
 * Fixed width text field
 *
 * @author MIGUEL_ANGEL
 */
public class FixedWidthTextField extends TextField
{

    /**
     *
     * @param text Text
     */
    public FixedWidthTextField(String text)
    {
        super(text);
        setMaxWidth(100);
    }

}
