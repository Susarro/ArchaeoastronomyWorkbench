/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.CancelExcepcion;
import com.ProcessException;
import com.astronomy.Catalogue;
import com.astronomy.JulianDay;
import com.astronomy.Planet;
import com.astronomy.Star;
import com.interfaz.skeleton.ModalDialog;
import static com.main.Main.latitude;
import static com.main.Main.longitude;
import static com.main.Main.skeleton;
import com.units.SexagesimalDegree;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Miguel Ángel
 */
public class Dialogs
{

    /**
     * Dialog for selecting an option
     *
     * @param options Array of options
     * @return Option selected
     * @throws ProcessException Format error
     * @throws CancelExcepcion CCancelation by user
     */
    static public String inputOption(String[] options) throws ProcessException, CancelExcepcion
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Option"), 0, 0);
        ComboBox lv;
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(options)), 1, 0);
        lv.getSelectionModel().select(0);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            return lv.getSelectionModel().getSelectedItem().toString();
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Dialog for entering local geographic coordinates
     *
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public final void inputLocalCoordinates() throws ProcessException, CancelExcepcion
    {
        TextField tfLatitud;
        TextField tfLongitud;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Latitude"), 0, 0);
        gridPane.add(tfLatitud = new TextField(latitude.toString()), 1, 0);
        gridPane.add(new Label("Longitude"), 0, 1);
        gridPane.add(tfLongitud = new TextField(longitude.toString()), 1, 1);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            latitude = SexagesimalDegree.valueOf(tfLatitud.getText());
            longitude = SexagesimalDegree.valueOf(tfLongitud.getText());
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Dialog for entering an interval of time and an increment
     *
     * @param interval Input interval
     * @return output interval
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public final Interval inputTimeInterval(Interval interval) throws ProcessException, CancelExcepcion
    {
        return inputTimeInterval(true,interval);
    }

    /**
     * Dialog for entering an interval of time
     *
     * @param incr If true, increment is also required
     * @param interval Input interval
     * @return output interval
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public final Interval inputTimeInterval(boolean incr, Interval interval) throws ProcessException, CancelExcepcion
    {
        TextField tfStart;
        TextField tfEnd;
        TextField tfIncremento = null;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Interval start"), 0, 0);
        gridPane.add(tfStart = new TextField(interval.getStart().toString()), 1, 0);
        gridPane.add(new Label("Interval end"), 0, 1);
        gridPane.add(tfEnd = new TextField(interval.getEnd().toString()), 1, 1);
        if (incr)
        {
            gridPane.add(new Label("Increment (days)"), 0, 2);
            gridPane.add(tfIncremento = new TextField(String.valueOf(interval.getIncrement())), 1, 2);
        }

        JulianDay s;
        JulianDay e;
        double i;

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            s = JulianDay.valueOf(tfStart.getText());
            e = JulianDay.valueOf(tfEnd.getText());
            if (incr)
            {
                i = Double.valueOf(tfIncremento.getText());
            }
            else
            {
                i = 1;
            }
            return new Interval(s, e, i);
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Dialog for selecting a planet: Mercury, Venus, Mars, Jupiter, Saturn
     *
     * @return Planet selected
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public Planet inputPlanet() throws ProcessException, CancelExcepcion
    {
        TextField tfLatitud;
        TextField tfLongitud;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Planet"), 0, 0);
        ComboBox lv;
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(new String[]
        {
            "Mercury", "Venus", "Mars", "Jupiter", "Saturn"
        })), 1, 0);
        lv.getSelectionModel().select(0);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            return new Planet(Catalogue.getPlanetEnum(lv.getSelectionModel().getSelectedItem().toString()));
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Dialog for selecting a star
     *
     * @return Star selected
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public Star inputStar() throws ProcessException, CancelExcepcion
    {
        TextField tfLatitude;
        TextField tfLongitude;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Star"), 0, 0);
        ComboBox lv;
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(Catalogue.getStarStrings())), 1, 0);
        lv.getSelectionModel().select(0);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            return Catalogue.getStar(Catalogue.getEnumEstrella(lv.getSelectionModel().getSelectedItem().toString()));
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Update a list of equatorial and horizontal coordinates
     *
     * @param list Final variable list
     * @param strFilter Each variable name which contains strFilter is accepted
     */
    static public void updateCoordinateList(ObservableList list, String strFilter)
    {
        list.clear();
        String[] pp = new String[]
        {
            "Sun", "Moon", "Mercury", "Venus", "Mars", "Jupiter", "Saturn"
        };
        for (String p : pp)
        {

            String[] tt = new String[]
            {
                p + "," + "DE", p + "," + "AR", p + "," + "A", p + "," + "h"
            };
            for (String t : tt)
            {
                if (strFilter.isEmpty() || t.contains(strFilter))
                {
                    list.add(t);
                }
            }

        }
        for (String p : Catalogue.getStarStrings())
        {
            String[] tt = new String[]
            {
                p + "," + "DE", p + "," + "AR", p + "," + "A", p + "," + "h"
            };
            for (String t : tt)
            {
                if (strFilter.isEmpty() || t.contains(strFilter))
                {
                    list.add(t);
                }
            }
        }
    }

    /**
     * A doialog for selecting a group of variables (equatorial and horizontal
     * coordinates)
     *
     * @return Variables selected
     * @throws ProcessException Format error
     * @throws CancelExcepcion Cancelation by user
     */
    static public String[] inputVariables() throws ProcessException, CancelExcepcion
    {
        ObservableList lista1 = FXCollections.observableArrayList();
        ObservableList lista2 = FXCollections.observableArrayList();
        TextField tfFiltro = new TextField();
        HBox.setHgrow(tfFiltro, Priority.ALWAYS);

        HBox h = new HBox(10);
        VBox v1 = new VBox(10);
        VBox v2 = new VBox(10);
        HBox h1 = new HBox(10);

        ListView lv1, lv2;
        updateCoordinateList(lista1, "");
        lv1 = new ListView(lista1);
        lv2 = new ListView(lista2);

        h1.getChildren().addAll(new Label("Filter"), tfFiltro);
        v1.getChildren().addAll(h1, lv1);
        Button btn1 = new Button(">");
        Button btn2 = new Button("<");
        Button btn3 = new Button(">>");
        v2.getChildren().addAll(btn1, btn2, btn3);
        v2.setAlignment(Pos.CENTER);
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        h.getChildren().addAll(v1, v2, lv2);

        btn1.setOnAction((ActionEvent t)
                -> 
                {
                    for (int i = 0; i < lv1.getSelectionModel().getSelectedItems().size(); i++)
                    {
                        String str1 = lv1.getSelectionModel().getSelectedItems().get(i).toString();
                        if (!lista2.contains(str1))
                        {
                            lista2.add(str1);
                        }
                    }
        });

        btn2.setOnAction((ActionEvent t)
                -> 
                {
                    ObservableList seleccionado = FXCollections.observableArrayList(lv2.getSelectionModel().getSelectedItems());
                    for (int i = 0; i < seleccionado.size(); i++)
                    {
                        String str2 = seleccionado.get(i).toString();
                        lista2.remove(str2);
                    }
        });

        btn3.setOnAction((ActionEvent t)
                -> 
                {
                    for (int i = 0; i < lista1.size(); i++)
                    {
                        String str1 = lista1.get(i).toString();
                        if (!lista2.contains(str1))
                        {
                            lista2.add(str1);
                        }
                    }
        });

        tfFiltro.textProperty().addListener((ObservableValue<? extends String> ov, String t, String filtro)
                -> 
                {
                    updateCoordinateList(lista1, filtro);
        });
        lv1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ModalDialog dialogo = new ModalDialog(skeleton, h, true);
        if (dialogo.showModal())
        {
            List<String> l = new ArrayList<>();
            for (Object o : lista2)
            {
                l.add(o.toString());
            }
            return l.toArray(new String[l.size()]);
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * A dialog for entering a string
     *
     * @param text Text shown to user
     * @param value Default value
     * @return Entered value
     */
    static public String inputDialog(String text, String value)
    {
        HBox p = new HBox(10);
        p.setAlignment(Pos.CENTER);
        TextField tf;
        p.getChildren().addAll(new Label(text), tf = new TextField(value));
        ModalDialog dialogo = new ModalDialog(skeleton, p, true);
        if (dialogo.showModal())
        {
            return tf.getText();
        }
        else
        {
            return "";
        }
    }

    /**
     * Dialog for selecting and option
     *
     * @param msg Message to user
     * @param options Array of options
     * @param defaultOption default option
     * @return index opf selected option
     * @throws ProcessException Format error
     */
    static public int selectOption(String msg, String[] options, String defaultOption) throws ProcessException
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label(msg), 0, 0);
        ComboBox lv;

        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(options)), 1, 0);
        lv.getSelectionModel().select(defaultOption);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            return lv.getSelectionModel().getSelectedIndex();
        }
        else
        {
            return -1;
        }
    }
}
