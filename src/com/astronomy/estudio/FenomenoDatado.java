/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class FenomenoDatado
{
    private final Fenomeno fenomeno;
    private final int anno;
    
    public FenomenoDatado(Fenomeno fenomeno, int anno)
    {
       this.fenomeno=fenomeno;
       this.anno=anno;
    }        

    /**
     * @return the fenomeno
     */
    public Fenomeno getFenomeno()
    {
        return fenomeno;
    }

    /**
     * @return the anno
     */
    public int getAnno()
    {
        return anno;
    }
}
