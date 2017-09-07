/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

/**
 * ROSENFELDT, G., A statistical method of evaluating megalithic observatories, Archaeoastronomy nº 7, Journal for the History of Astronomy, XV, 1984, pp. S111-S118 
 * or
 * Bernoulli process @see <a href="https://en.wikipedia.org/wiki/Bernoulli_process">https://en.wikipedia.org/wiki/Bernoulli_process</a>
 * @author MIGUEL_ANGEL
 */
public enum FortuitousProbabilityOption
{

    ROSENFELDT("Rosenfeldt"), BERNOULLI("Bernoulli");

    String name;

    /**
     * 
     * @param name Option name
     */
    FortuitousProbabilityOption(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
