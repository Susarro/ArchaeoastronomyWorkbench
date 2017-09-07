/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import com.astronomy.JulianDay;
import com.main.Main;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import org.jfree.chart.axis.NumberAxis;

/**
 * Swing chart with temporal abcissa based on julian days
 * @author MIGUEL_ANGEL
 */
public class JulianChart extends SwingChart
{
    /**
     * 
     * @param name Chart name
     * @param axes Configuration of axes
     * @param abcissaName Abcissa name
     */
    public JulianChart(String name, List<AxisChart> axes, String abcissaName)
    {
        super(name, Main.skeleton, axes, abcissaName);
        NumberAxis naAbcisa = (NumberAxis) abcissaAxis;
        
        NumberFormat nf = new NumberFormat()
        {

            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
            {
                return new StringBuffer((new JulianDay(number)).toString());
            }

            @Override
            public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)
            {
                return new StringBuffer((new JulianDay(number)).toString());
            }

            @Override
            public Number parse(String source, ParsePosition parsePosition)
            {
                return null;
            }
        };

        naAbcisa.setNumberFormatOverride(nf);
        abcissaFormat = nf;

    }
}
