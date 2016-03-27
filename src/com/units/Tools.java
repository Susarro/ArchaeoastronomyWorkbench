/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.units;

import com.ProcessException;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

/**
 * Tools
 * @author MIGUEL_ANGEL
 */
public class Tools
{
   /**
    * 
    * @param degrees Sexagesimal degrees
    * @return Sine of degrees
    */
    public static double sine(SexagesimalDegree degrees)
   {
      return SexagesimalDegree.sin(degrees);
   }
   
    /**
     * 
     * @param degrees Sexagesimal degrees as double value
     * @return Sine of degrees
     */
   public static double sine(double degrees)
   {
      return sin(degrees*PI/180);
   }
   
   /**
    * 
    * @param degrees Sexagesimal degrees as double value
    * @return Cosine of degrees
    */
    public static double cosine(double degrees)
   {
      return cos(degrees*PI/180);
   }
    
    /**
     * 
     * @param degrees Sexagesimal degrees as double value
     * @return Tangent of degrees
     */
     public static double tangent(double degrees)
   {
      return tan(degrees*PI/180);
   }
   
   
   /**
    * 
    * @param hours Hour angle
    * @return Sine of hour angle
    */
   public static double sine(HourAngle hours)
   {
      return HourAngle.sin(hours);
   }
   
   /**
    * 
    * @param radians Radians
    * @return Sine of radians
    */
   public static double sine(Radian radians)
   {
      return java.lang.Math.sin(radians.getValue());
   }
   
   /**
    * 
    * @param degrees Sexagesimal degrees
    * @return Cosine of degrees
    */
   public static double cosine(SexagesimalDegree degrees)
   {
      return SexagesimalDegree.cos(degrees);
   }
   
   /**
    * 
    * @param hours Hour angle
    * @return Cosine of hour angle
    */
   public static double cosine(HourAngle hours)
   {
      return HourAngle.cos(hours);
   }
   
   /**
    * 
    * @param radians Radians
    * @return Cosine of radians
    */
   public static double cosine(Radian radians)
   {
      return java.lang.Math.cos(radians.getValue());
   }
   
   /**
    * 
    * @param degrees Sexagesimal degrees
    * @return Tangent of degrees
    */
   public static double tangent(SexagesimalDegree degrees)
   {
      return SexagesimalDegree.tan(degrees);
   }
   
   /**
    * 
    * @param hours Hour angle
    * @return Tangent of hour angle
    */
   public static double tangent(HourAngle hours)
   {
      return HourAngle.tan(hours);
   }
   
   /**
    * 
    * @param radians Radians
    * @return Tangent of radians
    */
   public static double tangent(Radian radians)
   {
      return java.lang.Math.tan(radians.getValue());
   }
   
   /**
    * 
    * @param weights Weights
    * @param values Values affected by weights 
    * @param factor Final factor 
    * @return Sine value of a weighted sum of values affected by a factor
    * @throws ProcessException Lengths of weights and values are different 
    */ 
   public static double coefficientOfSineOfArgument(double[] weights, double[] values, double factor) throws ProcessException
    {
        if (weights.length != values.length)
        {
            throw new ProcessException("Lengths of weights and values are different");
        }
        double argumento = 0;
        for (int i = 0; i < weights.length; i++)
        {
            argumento += weights[i] * values[i];
        }
        double v = factor * sin(argumento * PI / 180.0);
        return v;

    }
    
   /**
    * 
    * @param weights Weights
    * @param values Values affected by weights 
    * @param coefficient  Final factor 
    * @return Cosine value of a weighted sum of values affected by a factor
    * @throws ProcessException Lengths of weights and values are different"
    */
    public static double coefficientOfCosineOfArgument(double[] weights, double[] values, double coefficient) throws ProcessException
    {
        if (weights.length != values.length)
        {
            throw new ProcessException("Lengths of weights and values are different");
        }
        double argumento = 0;
        for (int i = 0; i < weights.length; i++)
        {
            argumento += weights[i] * values[i];
        }
        double v = coefficient * cos(argumento * PI / 180.0);
        return v;

    }
   
   
}
