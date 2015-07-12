/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com;

/**
 *
 * @author MIGUEL_ANGEL
 */
public enum enumPlaneta
{
  SOL("Sol"), 
  LUNA("Luna"), 
  TIERRA("Tierra"), 
  MERCURIO("Mercurio"), 
  VENUS("Venus"), 
  MARTE("Marte"), 
  JUPITER("Júpiter"), 
  SATURNO("Saturno"), 
  URANO("Urano"), 
  NEPTUNO("Neptuno");
  
  String nombre;
  enumPlaneta(String nombre)
  {
      this.nombre=nombre;
  }
  @Override
  public String toString()
  {
      return nombre;
  }         
}
