/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import javafx.scene.layout.Pane;

/**
 *
 * @author MIGUEL_ANGEL
 */
public interface GraficaInterface
{
    public void addCategoria(String nombre);

    public void addSerie(String nombre, SimpleConfigSerie cs);

    public void addMuestra(double x, double[] y);

    public void addMuestra(double x, double y, String nombreSerie);

    public Pane getGrafica();

    public void fijaAbcisa(double dDesde, double dHasta);
}
