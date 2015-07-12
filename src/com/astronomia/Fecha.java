/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Fecha
{
    private int dia;
    private int mes;
    private int anno;
    
    @Override
    public String toString()
    {
        return String.format("%d/%d/%d", dia, mes, anno);
    }        

    /**
     * @return the dia
     */
    public int getDia()
    {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia)
    {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes()
    {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes)
    {
        this.mes = mes;
    }

    /**
     * @return the anno
     */
    public int getAnno()
    {
        return anno;
    }

    /**
     * @param anno the anno to set
     */
    public void setAnno(int anno)
    {
        this.anno = anno;
    }    
}
