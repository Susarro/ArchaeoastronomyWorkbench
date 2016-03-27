/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.units.SexagesimalDegree;

/**
 *
 * @author MIGUEL_ANGEL
 */
enum Fenomeno
{

    NINGUNO("Ninguno"),
    SOLSTICIO_INVIERNO("Solsticio de invierno"),
    SOLSTICIO_VERANO("Solsticio de verano"),
    IMBOLC_SAMAIN("Imbolc/Samain"),
    BELTAINE_LUGNASAD("Beltaine/Lugnasad"),
    LUNASTICIO_MAYOR_SUR("Lunasticio Mayor Sur"),
    LUNASTICIO_MAYOR_NORTE("Lunasticio Mayor Norte"),
    LUNASTICIO_MENOR_SUR("Lunasticio Menor Sur"),
    LUNASTICIO_MENOR_NORTE("Lunasticio Menor Norte");

    String nombre_formal;
    SexagesimalDegree declinacion;

    Fenomeno(String formal)
    {
        this.nombre_formal = formal;
    }

    @Override
    public String toString()
    {
        return nombre_formal;
    }

    public static Fenomeno fromString(String text)
    {
        if (text != null)
        {
            for (Fenomeno b : Fenomeno.values())
            {
                if (text.equalsIgnoreCase(b.nombre_formal))
                {
                    return b;
                }
            }
        }
        return null;
    }

    public void setDeclinacion(SexagesimalDegree g)
    {
        declinacion = g;
    }

    public SexagesimalDegree getDeclinacion()
    {
        return declinacion;
    }

}
