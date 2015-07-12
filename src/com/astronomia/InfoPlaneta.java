/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.astronomia;

import com.astronomia.sistema.Ecuatorial;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class InfoPlaneta
{
    Ecuatorial coordenadas;
    double magnitudVisual;
    
    public InfoPlaneta(Ecuatorial coordenadas, double mv)
    {
        this.coordenadas=coordenadas;
        this.magnitudVisual=mv;
    }        
}
