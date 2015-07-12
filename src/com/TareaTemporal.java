/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.astronomia.DiaJuliano;
import com.interfaz.esqueleto.Esqueleto;

/**
 *
 * @author MIGUEL_ANGEL
 */
public abstract class TareaTemporal extends PlantillaTarea<DiaJuliano>
{

    public TareaTemporal(Esqueleto esqueleto, String nombre, DiaJuliano desde, DiaJuliano hasta, double incremento)
    {
        super(esqueleto, nombre, desde, hasta, new DiaJuliano(incremento));
    }

    @Override
    public double doubleValue(DiaJuliano entrada)
    {
        return entrada.getValue();
    }

    @Override
    public String toString(DiaJuliano entrada)
    {
        return entrada.toString();
    }

    @Override
    public void cloneActual(DiaJuliano entrada)
    {
        setActual(new DiaJuliano(entrada.getValue()));
    }

    public void addActual(double incremento)
    {
        setActual(new DiaJuliano(getActual().getValue() + incremento));
    }

    @Override
    public void addActual(DiaJuliano incremento)
    {        
        TareaTemporal.this.addActual(incremento.getValue());
    }

    @Override
    public int compara(DiaJuliano objeto, DiaJuliano referencia)
    {
        if (objeto.isBefore(referencia))
        {
            return -1;
        }
        else if (objeto.isAfter(referencia))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    abstract public void terminado();

    @Override
    abstract public void ciclo();

}
