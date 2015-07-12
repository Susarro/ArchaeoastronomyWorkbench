/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import com.astronomia.DiaJuliano;
import com.interfaz.esqueleto.Esqueleto;
import com.interfaz.Principal;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import org.jfree.chart.axis.NumberAxis;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class GraficaJuliano extends GraficaSwing
{

    Principal principal;

    public GraficaJuliano(String nombre, Esqueleto esqueleto, List<CategoriaGrafica> categorias, String nombreAbcisa)
    {
        super(nombre, esqueleto, categorias, nombreAbcisa);
        this.principal = principal;
        NumberAxis naAbcisa = (NumberAxis) ejeAbcisas;
        
        NumberFormat nf = new NumberFormat()
        {

            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
            {
                return new StringBuffer((new DiaJuliano(number)).toString());
            }

            @Override
            public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)
            {
                return new StringBuffer((new DiaJuliano(number)).toString());
            }

            @Override
            public Number parse(String source, ParsePosition parsePosition)
            {
                return null;
            }
        };

        naAbcisa.setNumberFormatOverride(nf);
        formatoAbcisa = nf;

    }
}
