/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 * @author Miguel Ángel
 */
public class ProbabilityDensityPair
{

    private double probabilityDensity;
    private double value;

    public ProbabilityDensityPair(double value)
    {
        this.value = value;
        this.probabilityDensity = 0;
    }

    public void updateDensity(double origin, double standardDeviation, int total)
    {
        double d = getValue();
        double d0 = origin;
        double s = standardDeviation;
        int n = total;
        this.probabilityDensity=this.probabilityDensity + (exp(-pow(d - d0, 2) / (2 * pow(s, 2))) / sqrt(2 * PI)) / (n * s);
    }

    /**
     * @return the probabilityDensity
     */
    public double getProbabilityDensity()
    {
        return probabilityDensity;
    }

    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    

}
