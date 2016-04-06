/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

/**
 *
 * @author Miguel Ángel
 */
public class Tools
{

    /**
     *
     * @param value Numeric value
     * @return 1 if positive, -1 if negative and 0 if 0
     */
    public static int sign(double value)
    {
        if (value > 0)
        {
            return 1;
        }
        else if (value < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    
}
