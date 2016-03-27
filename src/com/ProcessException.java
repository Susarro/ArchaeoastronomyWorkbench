/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * Indicates an exception during process
 * @author MIGUEL_ANGEL
 */
public class ProcessException extends Exception
{
    /**
     * 
     * @param msg Message of exception
     */
    public ProcessException(String msg)
    {
        super(msg);
    }
}
