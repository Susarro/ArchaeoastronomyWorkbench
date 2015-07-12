/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

/**
 *
 * @author MIGUEL_ANGEL
 */
public enum ProbabilidadCasualidad
{

    ROSENFELDT("Rosenfeldt"), BERNOULLI("Bernoulli");

    String nombre_formal;

    ProbabilidadCasualidad(String formal)
    {
        this.nombre_formal = formal;
    }

    @Override
    public String toString()
    {
        return nombre_formal;
    }
}
