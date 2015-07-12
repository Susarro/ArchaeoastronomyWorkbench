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
public class InfoLuna
{

    public Ecuatorial coordenadas;
    public double discoIluminado;

    public InfoLuna(Ecuatorial coordenadas, double di)
    {
        this.coordenadas = coordenadas;
        this.discoIluminado = di;
    }
}
