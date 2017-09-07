/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * Indicates process cancellation by user
 * @author MIGUEL_ANGEL
 */
public class CancelExcepcion extends Exception
{
    /**
     * 
     * @param msg Message of exception
     */
    public CancelExcepcion(String msg)
    {
        super(msg);
    }
}
