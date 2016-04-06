/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

/**
 * Gaussian kernel probability density estimation parameters
 * @author MIGUEL_ANGEL
 */
public class GaussianKernelDensityEstimationParameters
{
    /**
     * Standard deviation
     */
    private final double standardDeviation;
    /**
     * Minimum value of the variable
     */
    private final double minimumValue;
    /**
     * Maximum value of the variable
     */
    private final double maximumValue;
    /**
     * Step value of the variable
     */
    private final double stepValue;
    
    /**
     * 
     * @param standardDeviation Standard deviation
     * @param minimumValue Minimum value of the variable
     * @param maximumValue Maximum value of the variable
     * @param stepValue Step value of the variable
     */
    public GaussianKernelDensityEstimationParameters(double standardDeviation, 
            double minimumValue, double maximumValue, double stepValue)
    {
        this.standardDeviation=standardDeviation;
        this.minimumValue=minimumValue;
        this.maximumValue=maximumValue;
        this.stepValue=stepValue;
    }        

    /**
     * @return Standard deviation
     */
    public double getStandardDeviation()
    {
        return standardDeviation;
    }

    /**
     * @return Minimum value of the variable
     */
    public double getMinimumValue()
    {
        return minimumValue;
    }

    /**
     * @return Maximum value of the variable
     */
    public double getMaximumValue()
    {
        return maximumValue;
    }

    /**
     * @return Step value of the variable
     */
    public double getStepValue()
    {
        return stepValue;
    }
    
}
