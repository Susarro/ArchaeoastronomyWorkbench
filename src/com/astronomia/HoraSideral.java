/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.astronomia;

import com.unidades.Hora;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class HoraSideral extends Hora
{

    public HoraSideral(Hora hora)
    {
        super(hora.getValor());
    }
    
    public HoraSideral(double valor)
    {
        super(valor);
    }
    
   
    
}
