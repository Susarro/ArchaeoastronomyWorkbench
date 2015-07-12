/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author MIGUEL_ANGEL
 */
abstract public class PanelAnotacion extends Pane implements InvalidationListener
{

    private final ObservableList<Anotacion> anotaciones;
    private boolean visible = false;

    public PanelAnotacion()
    {
        anotaciones = FXCollections.observableArrayList();
        anotaciones.addListener(this);
    }

    public void addAnnotation(final double x, final double y, String texto, LineChart chart)
    {
        Label l;
        anotaciones.add(new Anotacion(l = new Label(texto), x, y, chart));
        l.getStyleClass().add("anotacion");
    }

    public void addPosicionCursor(final double x, final double y, LineChart chart, LineChart.Series serie)
    {
        Label l;
        Anotacion a;
        anotaciones.add(a = new Anotacion(l = new Label("Y:" + String.format("%7.2f", y) + "\n" + "X:" + String.format("%7.2f", x)), x, y, chart));
        a.setNombre(serie.getName());
        l.setStyle(serie.getNode().getStyle().replace("-fx-stroke:", "fondo:") + "-fx-background-color:black,white,fondo;-fx-background-insets: 0,1,2;-fx-background-radius: 5,5,5;-fx-padding:5;-fx-text-fill: ladder(fondo, white 49%, black 50%);");
    }

    public void setColorPosicionCuersor(Color color, String nombre)
    {
        for (Anotacion a : anotaciones)
        {
            if (a.getNombre().equals(nombre))
            {
                a.getNode().setStyle("fondo:"
                        + color.toString().replace("0x", "#") + ";"
                        + "-fx-background-color:black,white,fondo;"
                        + "-fx-background-insets: 0,1,2;"
                        + "-fx-background-radius: 5,5,5;"
                        + "-fx-padding:5;"
                        + "-fx-text-fill: ladder(fondo, white 49%, black 50%);");
                update();
                return;
            }
        }
    }

    public void movePosicionCursor(final double x, final double y, String nombre)
    {
        for (Anotacion a : anotaciones)
        {
            if (a.getNombre().equals(nombre))
            {
                a.setX(x);
                a.setY(y);
                ((Label) a.getNode()).setText(FormatoAnotacion(x, y));

                update();
                return;
            }
        }
    }

    abstract String FormatoAnotacion(double x, double y);

    @Override
    public void invalidated(Observable o)
    {
        update();

    }

    public void update()
    {
        getChildren().clear();
        for (Anotacion anotacion : anotaciones)
        {
            if (anotacion.getNombre().equals("")
                    || (!anotacion.getNombre().equals("") && visible))
            {
                final Axis<Number> xAxis = anotacion.getChart().getXAxis();
                final Axis<Number> yAxis = anotacion.getChart().getYAxis();

                double dpx = xAxis.getDisplayPosition(anotacion.getX());
                double dpy = yAxis.getDisplayPosition(anotacion.getY());

                double px = xAxis.localToParent(dpx, 0).getX();
                double py = yAxis.localToParent(0, dpy).getY();
                Point2D p = anotacion.getChart().localToParent(px, py);

                final double x = p.getX();
                final double y = p.getY();

                final Circle indicator = new Circle(5);
                indicator.getStyleClass().add("anotacion-puntero");
                indicator.setCenterX(x);
                indicator.setCenterY(y);

                getChildren().add(indicator);

                final Node node = anotacion.getNode();
                getChildren().add(node);
                node.relocate(x + 10, y - node.prefHeight(Integer.MAX_VALUE) / 2 + 10);
                node.autosize();
            }

        }
    }

    /**
     * @param visible the visible to set
     */
    public void setVer(boolean visible)
    {
        this.visible = visible;
    }
    
    public boolean getVer()
    {
        return visible;
    }
}
