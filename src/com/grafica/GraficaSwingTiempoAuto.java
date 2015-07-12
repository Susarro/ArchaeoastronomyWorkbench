/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import com.interfaz.esqueleto.Esqueleto;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author MIGUEL_ANGEL
 */
public abstract class GraficaSwingTiempoAuto extends GraficaSwingTiempo
{

    Timeline temporizador;
    private int maximoMuestras = 0;
    
    public void Cerrar()
    {
       temporizador.stop(); 
    }
    
    
    public GraficaSwingTiempoAuto(String nombre, Esqueleto padre, List<CategoriaGrafica> categorias, String nombreAbcisas, Duration duracion)
    {
        this(nombre, padre, categorias,nombreAbcisas,"dd/MM/yy HH:mm",duracion);
    } 

    public GraficaSwingTiempoAuto(String nombre, Esqueleto padre, List<CategoriaGrafica> categorias, String nombreAbcisas, String formatoFecha, Duration duracion)
    {
        super(nombre, padre, categorias,nombreAbcisas,formatoFecha);
        temporizador = new Timeline(new KeyFrame(duracion, (ActionEvent t) ->
        {
            addMuestra();
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
        temporizador.play();
        
       
    }

    public abstract void addMuestra();

    /**
     * @return the maximoMuestras
     */
    public int getMaximoMuestras()
    {
        return maximoMuestras;
    }

    /**
     * @param maximoMuestras the maximoMuestras to set
     */
    public void setMaximoMuestras(int maximoMuestras)
    {
        this.maximoMuestras = maximoMuestras;
    }

}
