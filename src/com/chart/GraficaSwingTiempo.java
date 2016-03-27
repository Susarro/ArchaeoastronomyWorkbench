/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import com.Global;
import com.interfaz.esqueleto.Skeleton;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jfree.chart.axis.DateAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class GraficaSwingTiempo extends GraficaSwing
{

    SimpleDateFormat formatoFecha;
    long maxAge = 0;

    //LocalDateTime fechaReferencia = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0);
    //LocalDateTime fechaReferencia = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.of("Europe/Madrid"));
    ZonedDateTime fechaReferencia = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0), ZoneId.of("UTC"));

    public LocalDateTime getLocalDateTime(long x) //X en milisegundos
    {
        return fechaReferencia.plusNanos(x * 1000000).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setMaxItemAge(long maxAge)
    {
        this.maxAge = maxAge;
    }

    public GraficaSwingTiempo(String nombre, Skeleton padre, List<CategoriaGrafica> categorias, String nombreAbcisa, String strFormatoFecha)
    {
        super(nombre, padre, categorias, nombreAbcisa);

        this.formatoFecha = new SimpleDateFormat(strFormatoFecha);
        setFormatoAbcisa(formatoFecha);
        ejeAbcisas = new DateAxis(nombreAbcisa);
        ejeAbcisas.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        ejeAbcisas.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        ejeAbcisas.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        ejeAbcisas.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        ejeAbcisas.setAutoRange(true);
        ejeAbcisas.setLowerMargin(0.0);
        ejeAbcisas.setUpperMargin(0.0);
        ejeAbcisas.setTickLabelsVisible(true);
        ejeAbcisas.setLabelFont(ejeAbcisas.getLabelFont().deriveFont(tamañoFuente));
        ejeAbcisas.setTickLabelFont(ejeAbcisas.getLabelFont().deriveFont(tamañoFuente));

        ((DateAxis) ejeAbcisas).setDateFormatOverride(formatoFecha);
        plot.setDomainAxis(ejeAbcisas);

        for (MenuItem mi : listaMenuContextual)
        {
            if (mi.getText().equals("Exportar valores"))
            {
                mi.setOnAction((ActionEvent t) ->
                {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Exportar a fichero");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Comma Separated Values", "*.csv"));

                    Window w = null;
                    try
                    {
                        w = padre.getScene().getWindow();
                    }
                    catch (NullPointerException e)
                    {

                    }
                    File file = fileChooser.showSaveDialog(w);
                    if (file != null)
                    {
                        exportar(file);
                    }
                });
            }
        }
    }

    public GraficaSwingTiempo(String nombre, Skeleton padre, List<CategoriaGrafica> categorias, String nombreAbcisa)
    {
        this(nombre, padre, categorias, nombreAbcisa, "dd/MM/yy HH:mm");
    }

    public void addMuestra(LocalDateTime tiempo, double y, String nombreSerie)
    {
        ZonedDateTime zdt = ZonedDateTime.of(tiempo, ZoneId.systemDefault());

        for (XYSeries serie : listaSerie)
        {
            if (serie.getKey().toString().equals(nombreSerie))
            {
                serie.add((long) ChronoUnit.MILLIS.between(fechaReferencia, zdt), y);

                if (maxAge > 0)
                {
                    if (ChronoUnit.MILLIS.between(fechaReferencia.plusNanos(serie.getX(serie.getItemCount() - 1).longValue() * 1000000), zdt) > maxAge)
                    {
                        serie.remove(serie.getItemCount() - 1);
                    }
                }

                break;
            }
        }
    }

    public void addMuestra(LocalDateTime tiempo, double[] valores)
    {
        ZonedDateTime zdt = ZonedDateTime.of(tiempo, ZoneId.systemDefault());
        double x = ChronoUnit.MILLIS.between(fechaReferencia, zdt);
        addMuestra((long) x, valores);
        if (maxAge > 0)
        {
            for (XYSeries serie : listaSerie)
            {
                if (ChronoUnit.MILLIS.between(fechaReferencia.plusNanos(serie.getX(0).longValue() * 1000000), zdt) > maxAge)
                {
                    serie.remove(0);
                }
            }
        }
    }

    public void fijaAbcisa(LocalDateTime dDesde, LocalDateTime dHasta)
    {
        ejeAbcisas.setAutoRange(false);
        ejeAbcisas.setUpperBound(ChronoUnit.MILLIS.between(fechaReferencia, ZonedDateTime.of(dHasta, ZoneId.systemDefault())));
        ejeAbcisas.setLowerBound(ChronoUnit.MILLIS.between(fechaReferencia, ZonedDateTime.of(dDesde, ZoneId.systemDefault())));
    }

    public LocalDateTime[] getAbcisas(String nombreSerie)
    {
        List<LocalDateTime> lista = new ArrayList<>();
        for (Serie serie : listaSerie)
        {
            if (serie.getKey().toString().equals(nombreSerie))
            {
                for (int i = 0; i < serie.getItemCount(); i++)
                {
                    LocalDateTime hora = getLocalDateTime(serie.getX(i).longValue());
                    lista.add(hora);
                }
                return lista.toArray(new LocalDateTime[lista.size()]);
            }
        }
        return null;
    }

    private void exportar(File file)
    {

        String registro;
        int n = 0;
        int ne = 0;
        String cabecera = "Tiempo;";
        for (int i = 0; i < plot.getDatasetCount(); i++)
        {
            XYSeriesCollection ds = (XYSeriesCollection) plot.getDataset(i);
            for (int j = 0; j < ds.getSeriesCount(); j++)
            {
                n++;
                XYSeries s = (XYSeries) ds.getSeries(j);
                cabecera += s.getKey().toString() + ";";
                if (n == 1)
                {
                    ne = s.getItemCount();
                }
            }
        }
        String[][] exportacion = new String[ne][n + 1];
        n = 0;
        for (int i = 0; i < plot.getDatasetCount(); i++)
        {
            XYSeriesCollection ds = (XYSeriesCollection) plot.getDataset(i);
            for (int j = 0; j < ds.getSeriesCount(); j++)
            {
                XYSeries s = (XYSeries) ds.getSeries(j);
                n++;
                for (int e = 0; e < ne; e++)
                {
                    exportacion[e][n] = formatoOrdenada.format(s.getDataItem(e).getYValue());
                    if (n == 1)
                    {
                        exportacion[e][0] = formatoFecha.format(s.getDataItem(e).getXValue());

                    }
                }
            }
        }
        FileWriter fichero;

        PrintWriter pw;

        try
        {
            fichero = new FileWriter(file.getAbsolutePath(), false);
            pw = new PrintWriter(fichero);
            pw.println(cabecera);
            for (String[] exp : exportacion)
            {
                String strRegistro = "";
                for (String e : exp)
                {
                    strRegistro += e + ";";
                }
                pw.println(strRegistro);
            }

            pw.close();
        }
        catch (IOException ex)
        {
            Global.info.Log(ex);
        }
    }
}
