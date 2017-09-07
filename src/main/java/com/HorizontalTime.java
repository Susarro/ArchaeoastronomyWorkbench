/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Miguel Ángel
 */
public enum HorizontalTime
{
  RISE("Rise"), SET("Set");
  
  String name;
  
  HorizontalTime(String name)
  {
      this.name=name;
  }        
}
