/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com;

/**
 * Enumeration of planets visible to the naked eye
 * 
 * @author MIGUEL_ANGEL
 */
public enum PlanetEnum
{
  SUN("Sun"), 
  MOON("Moon"),
  EARTH("Earth"), 
  MERCURY("Mercury"), 
  VENUS("Venus"), 
  MARS("Mars"), 
  JUPITER("Jupiter"), 
  SATURN("Saturn"), 
  URANUS("Uranus"), 
  NEPTUNE("Neptune");
  
  /**
   * Planet name
   */
  String name;
  
  /**
   * 
   * @param name Planet name
   */
  PlanetEnum(String name)
  {
      this.name=name;
  }
  @Override
  public String toString()
  {
      return name;
  }         
}
