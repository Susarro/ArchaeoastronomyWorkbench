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
public class HoraUniversal extends Hora
{

    public HoraUniversal(Hora hora)
    {
        super(hora.getValor());
    }
    
    public HoraUniversal(double valor)
    {
        super(valor);
    }
    
    
}
