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
public class InfoFuncionDensidad
{
    private final double desviacionTipica;
    private final double declinacionMinima;
    private final double declinacionMaxima;
    private final double pasoDeclinacion;
    
    public InfoFuncionDensidad(double desviacionTipica, 
            double declinacionMinima, double declinacionMaxima, double pasoDeclinacion)
    {
        this.desviacionTipica=desviacionTipica;
        this.declinacionMinima=declinacionMinima;
        this.declinacionMaxima=declinacionMaxima;
        this.pasoDeclinacion=pasoDeclinacion;
    }        

    /**
     * @return the desviacionTipica
     */
    public double getDesviacionTipica()
    {
        return desviacionTipica;
    }

    /**
     * @return the declinacionMinima
     */
    public double getDeclinacionMinima()
    {
        return declinacionMinima;
    }

    /**
     * @return the declinacionMaxima
     */
    public double getDeclinacionMaxima()
    {
        return declinacionMaxima;
    }

    /**
     * @return the pasoDeclinacion
     */
    public double getPasoDeclinacion()
    {
        return pasoDeclinacion;
    }
    
}
