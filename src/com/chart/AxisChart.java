/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Axis configuration
 * 
 * @author MIGUEL_ANGEL
 */
public class AxisChart
{
    /**
     * Name
     */
    private String name;
    /**
     * List of series for this axis
     */
    public List<SimpleSeriesConfiguration> configSerieList;
    /**
     * List view
     */
    ListView<SimpleSeriesConfiguration> listView;
    /**
     * Observable list of series
     */
    private final ObservableList<SimpleSeriesConfiguration> listModel;

    /**
     * 
     * @param name Axis' name
     */
    public AxisChart(String name)
    {
        this.name = name;
        configSerieList = new ArrayList<>();
        listView = new ListView();
        listModel = FXCollections.observableList(configSerieList);
        listView.setItems(listModel);        
        listView.setCellFactory(new Callback<ListView<SimpleSeriesConfiguration>, ListCell<SimpleSeriesConfiguration>>()
        {

            @Override
            public ListCell<SimpleSeriesConfiguration> call(ListView<SimpleSeriesConfiguration> p)
            {

                ListCell<SimpleSeriesConfiguration> cell = new ListCell<SimpleSeriesConfiguration>()
                {

                    @Override
                    protected void updateItem(SimpleSeriesConfiguration t, boolean bln)
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

    public void update()
    {
        listView.setItems(listModel);
    }

    /**
     * 
     * @return Axis' name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @return List view
     */
    public ListView getList()
    {
        return listView;
    }

    /**
     *
     * @param serie Serie to add
     */
    public void addSerie(SimpleSeriesConfiguration serie)
    {
        listModel.add(serie);        
    }
    
    /**
     * 
     * @param name Serie's name to remove 
     */
    public void removeSerie(String name)
    {
        for(int i=0;i<listModel.size();i++)
        {
            if(i<0) i=0;
            if(listModel.get(i).getName().equals(name))
            {
                listModel.remove(i--);
            }    
        } 
        
    }

    /**
     * 
     * @param n Name to set
     */
    void setName(String n)
    {
        name = n;
    }

}
