/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class CategoriaGrafica
{

    private String nombre;
    public List<SimpleConfigSerie> listaConfigSerie;
    ListView<SimpleConfigSerie> lista;
    private final ObservableList<SimpleConfigSerie> modeloLista;

    public CategoriaGrafica(String nombre)
    {
        this.nombre = nombre;
        listaConfigSerie = new ArrayList<>();
        lista = new ListView();
        modeloLista = FXCollections.observableList(listaConfigSerie);
        lista.setItems(modeloLista);        
        lista.setCellFactory(new Callback<ListView<SimpleConfigSerie>, ListCell<SimpleConfigSerie>>()
        {

            @Override
            public ListCell<SimpleConfigSerie> call(ListView<SimpleConfigSerie> p)
            {

                ListCell<SimpleConfigSerie> cell = new ListCell<SimpleConfigSerie>()
                {

                    @Override
                    protected void updateItem(SimpleConfigSerie t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            setText(t.toString());
                        }
                        else
                        {
                            setText("");
                        }    
                    }

                };

                return cell;
            }
        });
    }

    public void Actualiza()
    {
        lista.setItems(modeloLista);
    }

    public String getNombre()
    {
        return nombre;
    }

    public ListView getLista()
    {
        return lista;
    }

    public void addSerie(SimpleConfigSerie serie)
    {
        modeloLista.add(serie);        
    }
    
    public void removeSerie(String nombre)
    {
        for(int i=0;i<modeloLista.size();i++)
        {
            if(i<0) i=0;
            if(modeloLista.get(i).getNombre().equals(nombre))
            {
                modeloLista.remove(i--);
            }    
        } 
        
    }

    void setNombre(String n)
    {
        nombre = n;
    }

}
