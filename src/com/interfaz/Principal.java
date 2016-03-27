/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz;

import com.interfaz.esqueleto.ModalDialog;
import com.interfaz.esqueleto.MessageDialog;
import com.interfaz.esqueleto.Skeleton;
import com.CancelExcepcion;
import com.ProcessException;
import com.components.BordererLabel;
import com.components.GridPane10;
import com.Global;
import com.TemporalTask;
import com.astronomy.Catalogue;
import com.astronomy.JulianDay;
import com.astronomy.Star;
import com.astronomy.InfoMoon;
import com.astronomy.Moon;
import com.astronomy.Planet;
import com.astronomy.Sun;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.Horizontal;
import com.astronomy.coordinate_system.ApparentHorizontal;
import com.astronomy.estudio.Estudio;
import com.calendario.FiestasMediaEstacion;
import com.PlanetEnum;
import com.chart.CategoriaGrafica;
import com.chart.GraficaJuliano;
import com.chart.SimpleConfigSerie;
import com.CalculusType;
import com.units.SexagesimalDegree;
import com.units.Radian;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.log10;
import static java.lang.Math.PI;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import org.jdom2.JDOMException;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Principal extends Application
{

    public static Skeleton skeleton;
    VBox raiz;
    TabPane tp;
    MenuBar menu;

    public SexagesimalDegree latitud;
    public SexagesimalDegree longitud;

    JulianDay desde;
    JulianDay hasta;
    double incremento = 1;
    Stage st;

    static List<Estudio> estudios = new CopyOnWriteArrayList<>();
    private MenuItem miCerrarProyecto;
    private MenuItem miGuardarProyecto;
    private HBox hDiaJuliano = new HBox(10);
    
    public String inputOpcion(String[] opcion) throws ProcessException, CancelExcepcion 
    { 
        
        GridPane gridPane = new GridPane(); 
        gridPane.setHgap(10); 
        gridPane.setVgap(10); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.add(new Label("Opción"), 0, 0); 
        ComboBox lv; 
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(opcion)), 1, 0); 
        lv.getSelectionModel().select(0); 

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true); 
        if (dialogo.ShowModal()) 
        { 
            return lv.getSelectionModel().getSelectedItem().toString(); 
        } 
        else 
        { 
            throw new CancelExcepcion(""); 
        } 
    } 


    public final void inputCoordenadasLocales() throws ProcessException, CancelExcepcion
    {
        TextField tfLatitud;
        TextField tfLongitud;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Latitud"), 0, 0);
        gridPane.add(tfLatitud = new TextField(latitud.toString()), 1, 0);
        gridPane.add(new Label("Longitud"), 0, 1);
        gridPane.add(tfLongitud = new TextField(longitud.toString()), 1, 1);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.ShowModal())
        {
            latitud = SexagesimalDegree.valueOf(tfLatitud.getText());
            longitud = SexagesimalDegree.valueOf(tfLongitud.getText());
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    public final void inputTiempo() throws ProcessException, CancelExcepcion 
    { 
        inputTiempo(true); 
    }         

    public final void inputTiempo(boolean incr) throws ProcessException, CancelExcepcion 
    { 
        TextField tfDesde; 
        TextField tfHasta; 
        TextField tfIncremento=null; 
        GridPane gridPane = new GridPane(); 
        gridPane.setHgap(10); 
        gridPane.setVgap(10); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.add(new Label("Desde"), 0, 0); 
        gridPane.add(tfDesde = new TextField(desde.toString()), 1, 0); 
        gridPane.add(new Label("Hasta"), 0, 1); 
        gridPane.add(tfHasta = new TextField(hasta.toString()), 1, 1); 
        if (incr) 
        { 
            gridPane.add(new Label("Incremento (días)"), 0, 2); 
            gridPane.add(tfIncremento = new TextField(String.valueOf(incremento)), 1, 2); 
        } 

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true); 
        if (dialogo.ShowModal()) 
        { 
            desde = JulianDay.valueOf(tfDesde.getText()); 
            hasta = JulianDay.valueOf(tfHasta.getText()); 
            if (incr) 
            { 
                incremento = Double.valueOf(tfIncremento.getText()); 
            } 
            else 
            { 
                incremento=1; 
            }     
        } 
        else 
        { 
            throw new CancelExcepcion(""); 
        } 
    } 
    
    public Planet inputPlaneta() throws ProcessException, CancelExcepcion 
    { 
        TextField tfLatitud; 
        TextField tfLongitud; 
        GridPane gridPane = new GridPane(); 
        gridPane.setHgap(10); 
        gridPane.setVgap(10); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.add(new Label("Planeta"), 0, 0); 
        ComboBox lv; 
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(new String[] 
        { 
            "Mercurio", "Venus", "Marte", "Júpiter", "Saturno" 
        })), 1, 0); 
        lv.getSelectionModel().select(0); 

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true); 
        if (dialogo.ShowModal()) 
        {             
            return new Planet(Catalogue.getPlanetEnum(lv.getSelectionModel().getSelectedItem().toString())); 
        } 
        else 
        { 
            throw new CancelExcepcion(""); 
        } 
    }

    public Star inputEstrella() throws ProcessException, CancelExcepcion
    {
        TextField tfLatitud;
        TextField tfLongitud;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Estrella"), 0, 0);
        ComboBox lv;
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(Catalogue.getStarStrings())), 1, 0);
        lv.getSelectionModel().select(0);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.ShowModal())
        {
            return Catalogue.getStar(Catalogue.getEnumEstrella(lv.getSelectionModel().getSelectedItem().toString()));
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    public void updateListaVariables(ObservableList lista, String filtro)
    {
        lista.clear();
        String[] pp = new String[]
        {
            "Sol", "Luna", "Mercurio", "Venus", "Marte", "Júpiter", "Saturno"
        };
        for (String p : pp)
        {

            String[] tt = new String[]
            {
                p + "," + "DE", p + "," + "AR", p + "," + "A", p + "," + "h"
            };
            for (String t : tt)
            {
                if (filtro.isEmpty() || t.contains(filtro))
                {
                    lista.add(t);
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
                if (filtro.isEmpty() || t.contains(filtro))
                {
                    lista.add(t);
                }
            }
        }
    }

    public String[] inputVariables() throws ProcessException, CancelExcepcion
    {
        ObservableList lista1 = FXCollections.observableArrayList();
        ObservableList lista2 = FXCollections.observableArrayList();
        TextField tfFiltro = new TextField();
        HBox.setHgrow(tfFiltro, Priority.ALWAYS);

        //TextField tfLatitud;
        //TextField tfLongitud;
        HBox h = new HBox(10);
        VBox v1 = new VBox(10);
        VBox v2 = new VBox(10);
        HBox h1 = new HBox(10);

        ListView lv1, lv2;
        updateListaVariables(lista1, "");
        lv1 = new ListView(lista1);
        lv2 = new ListView(lista2);

        h1.getChildren().addAll(new Label("Filtro"), tfFiltro);
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

        btn1.setOnAction((ActionEvent t) ->
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

        btn2.setOnAction((ActionEvent t) ->
        {
            ObservableList seleccionado = FXCollections.observableArrayList(lv2.getSelectionModel().getSelectedItems());
            for (int i = 0; i < seleccionado.size(); i++)
            {
                String str2 = seleccionado.get(i).toString();
                lista2.remove(str2);
            }
        });

        btn3.setOnAction((ActionEvent t) ->
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

        tfFiltro.textProperty().addListener((ObservableValue<? extends String> ov, String t, String filtro) ->
        {
            updateListaVariables(lista1, filtro);
        });
        lv1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lv2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ModalDialog dialogo = new ModalDialog(skeleton, h, true);
        if (dialogo.ShowModal())
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

    public String inputDialog(String text, String valor)
    {
        HBox p = new HBox(10);
        p.setAlignment(Pos.CENTER);
        TextField tf;
        p.getChildren().addAll(new Label(text), tf = new TextField(valor));
        ModalDialog dialogo = new ModalDialog(skeleton, p, true);
        if (dialogo.ShowModal())
        {
            return tf.getText();
        }
        else
        {
            return "";
        }
    }

    public int inputOpciones(String msg, String[] opciones, String defecto) throws ProcessException
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label(msg), 0, 0);
        ComboBox lv;

        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(opciones)), 1, 0);
        lv.getSelectionModel().select(defecto);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.ShowModal())
        {
            return lv.getSelectionModel().getSelectedIndex();
        }
        else
        {
            return -1;
        }
    }

    public Principal() throws ProcessException
    {
        latitud = SexagesimalDegree.valueOf("42º34'28''");
        longitud = SexagesimalDegree.valueOf("6º20'01''");
        desde = new JulianDay(1, 1, 2015);
        hasta = new JulianDay(1, 1, 2016);
    }

    public Tab NuevoTab(String nombre)
    {
        Tab tab;
        tp.getTabs().add(tab = new Tab(nombre));
        tp.getSelectionModel().select(tab);
        return tab;
    }

    private void FenomenoEstelar(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;
            inputCoordenadasLocales();
            String strAño = inputDialog("Año", "");
            String strAcimut = inputDialog("Acimut", "");
            Star estrella = inputEstrella();
            tab = NuevoTab(estrella.toString() + " " + strAño);
            tab.setContent(FenomenoEstelar(Integer.valueOf(strAño), estrella, Double.valueOf(strAcimut), latitud, longitud));
        }
        catch (NullPointerException ex)
        {
            Global.info.Log(ex);
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    private void SimulacionAstro(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;
            inputCoordenadasLocales();
            String[] var = inputVariables();
            inputTiempo();
            tab = NuevoTab("Simulación astros");
            tab.setContent(Astro(var, desde, hasta, incremento));
        }
        catch (NullPointerException ex)
        {
            Global.info.Log(ex);
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    public int Signo(double valor)
    {
        if (valor > 0)
        {
            return 1;
        }
        else if (valor < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    class TareaCoincidenciaElevacion extends TemporalTask 
    { 

        String momento; 
        List<CategoriaGrafica> categorias; 
        CategoriaGrafica categoria; 
        GraficaJuliano grafica; 
        Double h_temp = null; 
        Double temp = null; 
        Double temp2 = null; 

        int estado = 0; 
        //estado = 1 disco iluminado >=0.1 
        //estad 
        boolean orto = false; 

        Planet planeta; 
        Star estrella; 

        public TareaCoincidenciaElevacion(String nombre, Star estrella, Planet planeta, JulianDay desde, JulianDay hasta, String momento) throws ProcessException 
        { 
            super(nombre, desde, hasta, 0.005); 
            this.momento = momento; 
            this.estrella = estrella; 
            this.planeta = planeta; 
            categorias = new ArrayList<>(); 

            categorias.add(categoria = new CategoriaGrafica("Declinación")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("DE " + planeta.toString(), Color.ORANGE, 4, "rectángulo")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("DE " + estrella.toString(), Color.ORANGE, 2, "cruz")); 
            categorias.add(categoria = new CategoriaGrafica("Acimut")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A " + planeta.toString(), Color.RED, 4, "nulo")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A " + estrella.toString(), Color.RED, 2, "nulo")); 
            categorias.add(categoria = new CategoriaGrafica("Elevación")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h " + planeta.toString(), Color.WHITE, 4, "nulo")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h " + estrella.toString(), Color.WHITE, 2, "nulo")); 
            categoria.listaConfigSerie.add(new SimpleConfigSerie("planeta-estrella", Color.CYAN, 2, "nulo")); 
            grafica = new GraficaJuliano("", skeleton, categorias, "DJ"); 
            if (momento.equals("Orto")) 
            { 
                orto = true; 
            } 
        } 

        @Override 
        public void cycle() 
        { 
            try 
            { 
                Equatorial eS = planeta.getApparentPosition(getCurrent()); 
                Equatorial eT = estrella.getApparentPosition(getCurrent(), CalculusType.PRECISE); 
                ApparentHorizontal hS = eS.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SATURN, getCurrent()); 
                ApparentHorizontal hT = eT.toHorizontal(latitud, longitud, getCurrent()).toApparent(); 

                double difH = hS.getAltitude().getSignedValue() - hT.getAltitude().getSignedValue(); 

                if (temp != null) 
                { 
                    if (estado == 0) 
                    { 
                        if ((!orto & hS.getAltitude().getSignedValue() < temp) 
                                || (orto & hS.getAltitude().getSignedValue() > temp)) 
                        { 
                            if ((!orto && hS.getAltitude().getSignedValue() <= 0 && temp > 0) 
                                    || (orto && hS.getAltitude().getSignedValue() >= 0 && temp < 0)) 
                            { 

                                /*grafica.addMuestra(getActual().getValue(), eS.getDeclinacion().getSignedValue(), "DE " + planeta.toString()); 
                                grafica.addMuestra(getActual().getValue(), eT.getDeclinacion().getSignedValue(), "DE " + estrella.toString()); 
                                grafica.addMuestra(getActual().getValue(), hS.getAcimut().getSignedValue(), "A " + planeta.toString());
                                grafica.addMuestra(getActual().getValue(), hT.getAcimut().getSignedValue(), "A " + estrella.toString()); 
                                grafica.addMuestra(getActual().getValue(), hS.getElevacion().getSignedValue(), "h " + planeta.toString()); 
                                grafica.addMuestra(getActual().getValue(), hT.getElevacion().getSignedValue(), "h " + estrella.toString()); 
                                grafica.addMuestra(getActual().getValue(), difH, "planeta-estrella"); 
                                if (true) 
                                { 
                                    temp = hS.getElevacion().getSignedValue(); 
                                    setIncremento(new DiaJuliano(0.1)); 
                                    return; 
                                }*/
                                
                                if (temp2 != null) 
                                { 
                                    if ((!orto && difH >= 0 && temp2 < 0) 
                                            || (orto && difH <= 0 && temp2 > 0)) 
                                    { 
                                        if (Signo(hS.getAzimuth().getSignedValue()) != Signo(hT.getAzimuth().getSignedValue())) 
                                        { 
                                            switch (planeta.toString()) 
                                            { 
                                                case "Saturno": 
                                                    addToCurrent(0.8); 
                                                    break; 
                                                case "Marte": 
                                                    addToCurrent(0.8); 
                                                    break; 
                                                case "Júpiter": 
                                                    addToCurrent(0.8); 
                                                    break; 
                                                case "Venus": 
                                                    addToCurrent(0.8); 
                                                    break; 
                                                case "Mercurio": 
                                                    addToCurrent(0.8); 
                                                    break;     
                                            } 
                                            temp = null; 
                                            temp2 = null; 
                                            return; 
                                        } 
                                        else 
                                        { 
                                            grafica.addMuestra(getCurrent().getValue(), eS.getDeclination().getSignedValue(), "DE " + planeta.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), eT.getDeclination().getSignedValue(), "DE " + estrella.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), hS.getAzimuth().getSignedValue(), "A " + planeta.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), hT.getAzimuth().getSignedValue(), "A " + estrella.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), hS.getAltitude().getSignedValue(), "h " + planeta.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), hT.getAltitude().getSignedValue(), "h " + estrella.toString()); 
                                            grafica.addMuestra(getCurrent().getValue(), difH, "planeta-estrella"); 
                                            switch (planeta.toString()) 
                                            { 
                                                case "Saturno": 
                                                    addToCurrent(365 * 29); 
                                                    break; 
                                                case "Marte": 
                                                    addToCurrent(0.8); 
                                                    break; 
                                                case "Júpiter": 
                                                    addToCurrent(365 * 10); 
                                                    break; 
                                                case "Venus": 
                                                    addToCurrent(0.8); 
                                                    break;     
                                                case "Mercurio": 
                                                    addToCurrent(0.8); 
                                                    break;     
                                            } 
                                            temp = null; 
                                            temp2 = null; 
                                            return; 
                                        } 

                                    } 
                                    else 
                                    { 
                                        addToCurrent(0.95); 
                                        temp = null; 
                                        temp2 = difH; 
                                        return; 
                                    } 
                                } 
                                temp2 = difH; 
                            } 
                        } 
                    } 
                    else if (estado == 1) 
                    { 
                        grafica.addMuestra(getCurrent().getValue(), eS.getDeclination().getSignedValue(), "DE Saturno"); 
                        grafica.addMuestra(getCurrent().getValue(), eT.getDeclination().getSignedValue(), "DE Tauro"); 
                        grafica.addMuestra(getCurrent().getValue(), hS.getAzimuth().getSignedValue(), "A Saturno"); 
                        grafica.addMuestra(getCurrent().getValue(), hT.getAzimuth().getSignedValue(), "A Tauro"); 
                        grafica.addMuestra(getCurrent().getValue(), hS.getAltitude().getSignedValue(), "h Saturno"); 
                        grafica.addMuestra(getCurrent().getValue(), hT.getAltitude().getSignedValue(), "h Tauro"); 
                        grafica.addMuestra(getCurrent().getValue(), difH, "diferencia"); 

                    } 
                } 
                temp = hS.getAltitude().getSignedValue(); 

            } 
            catch (ProcessException ex) 
            { 
                Global.info.Log(ex); 
            } 
        } 

        private Node getGrafica() 
        { 
            return grafica.getGrafica(); 
        } 

        @Override 
        public void taskEnd() 
        { 
        } 

        

    } 


   private void CoincidenciaElevacion(TabPane tp) throws ProcessException 
    { 
        try 
        { 
            Tab tab; 

            inputCoordenadasLocales(); 

            inputTiempo(false); 

            Star estrella = inputEstrella(); 
            Planet planeta = inputPlaneta(); 
            tab = NuevoTab(planeta.toString() + " & " + estrella.toString()); 

            TareaCoincidenciaElevacion conjuncion = new TareaCoincidenciaElevacion(planeta.toString() + " & " + estrella.toString(), estrella, planeta, desde, hasta, inputOpcion(new String[] 
            { 
                "Orto", "Ocaso" 
            })); 

            conjuncion.taskStart(); 
            tab.setContent(conjuncion.getGrafica()); 

        } 
        catch (CancelExcepcion ex) 
        { 

        } 

    } 


    private void LunaLlenaPrimavera(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputCoordenadasLocales();
            String strAño = inputDialog("Año inicial", "-2000");
            String strNumero = inputDialog("Nº años", "10");

            List<CategoriaGrafica> categorias = new ArrayList<>();
            CategoriaGrafica categoria;

            categorias.add(categoria = new CategoriaGrafica("Fase Luna"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("Fase", Color.ORANGE, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Acimut"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Luna", Color.RED, 2, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Sol", Color.RED, 1, "nulo"));

            GraficaJuliano grafica = new GraficaJuliano("", skeleton, categorias, "DJ");
            final int año = Integer.valueOf(strAño);
            final int n_año = Integer.valueOf(strNumero);

            tab = NuevoTab("Luna Primavera");

            (new TemporalTask("Luna", new JulianDay(1, 1, año), new JulianDay(1, 1, año + n_año), 1.0) //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay puestaSol;
                int año_ = año;
                Double temp = null;

                //estado = 1 disco iluminado >=0.1
                //estad
                @Override
                public void taskEnd()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il_antes = Moon.getInfoLuna(getCurrent().minus(new JulianDay(1)));
                        InfoMoon il = Moon.getInfoLuna(getCurrent());
                        InfoMoon il_despues = Moon.getInfoLuna(getCurrent().plus(new JulianDay(1)));
                        boolean marca = false;

                        

                        if (il_antes.fraction < il.fraction && il_despues.fraction < il.fraction)
                        {

                            Equatorial el = Moon.getPosicionAparente(getCurrent());
                            Equatorial es = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            SexagesimalDegree Al = SexagesimalDegree.acos(sine(el.getDeclination()) / cosine(latitud));
                            SexagesimalDegree As = SexagesimalDegree.acos(sine(es.getDeclination()) / cosine(latitud));
                            if (temp != null)
                            {
                                if (Al.minus(As).getSignedValue() > 0 && temp < 0)
                                {
                                    grafica.addMuestra(getCurrent().getValue(), il.fraction, "Fase");
                                    grafica.addMuestra(getCurrent().getValue(), Al.getSignedValue(), "A Luna");
                                    grafica.addMuestra(getCurrent().getValue(), As.getSignedValue(), "A Sol");

                                }
                            }
                            temp = Al.minus(As).getSignedValue();
                            addToCurrent(29);
                        }
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.Log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    private void PrimeraLuna(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputCoordenadasLocales();
            String strAño = inputDialog("Año inicial", "-2000");
            String strNumero = inputDialog("Nº años", "10");

            List<CategoriaGrafica> categorias = new ArrayList<>();
            CategoriaGrafica categoria;

            categorias.add(categoria = new CategoriaGrafica("Fase Luna"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("Fase", Color.ORANGE, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Acimut"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Luna", Color.RED, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Elevación"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Luna", Color.WHITE, 4, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Sol", Color.WHITE, 2, "nulo"));

            GraficaJuliano grafica = new GraficaJuliano("", skeleton, categorias, "DJ");
            final int año = Integer.valueOf(strAño);
            final int n_año = Integer.valueOf(strNumero);

            String[] oo;

            int opcion = inputOpciones("Referencia", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Solsticio de verano", "Solsticio de invierno", "Equinoccio primavera", "Equinoccio otoño"
            }, "Solsticio de invierno");
            JulianDay referencia;

            tab = NuevoTab("Primera Luna " + oo[opcion]);

            FiestasMediaEstacion fme = new FiestasMediaEstacion(año);
            switch (opcion)
            {
                case 0:
                    referencia = fme.imbolc;
                    break;
                case 1:
                    referencia = fme.beltaine;
                    break;
                case 2:
                    referencia = fme.lugnasad;
                    break;
                case 3:
                    referencia = fme.samain;
                    break;
                case 4:
                    referencia = fme.solsticioVerano;
                    break;
                case 5:
                    referencia = fme.solsticioInvierno;
                    break;
                case 6:
                    referencia = fme.equinoccioPrimavera;
                    break;
                case 7:
                    referencia = fme.equinoccioOtoño;
                    break;
                default:
                    return;
            }

            (new TemporalTask("Luna", referencia, new JulianDay(1, 1, año + n_año), 1.0 / (24.0 * 60)) //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay puestaSol;
                int año_ = año;
                int estado = 0;
                //estado = 1 disco iluminado >=0.1
                //estad

                @Override
                public void taskEnd()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il = Moon.getInfoLuna(getCurrent());
                        boolean marca = false;

                        if (estado == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.fraction > d_temp)
                                {
                                    if (il.fraction < 0.1 & il.fraction > 0 && d_temp <= il.fraction)
                                    {
                                        estado = 1;
                                    }
                                }
                            }
                            d_temp = il.fraction;
                        }

                        if (estado == 2)
                        {
                            Equatorial e = Moon.getPosicionAparente(getCurrent());
                            Equatorial ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs = ns.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());

                            double hPrueba = h.getAltitude().getSignedValue();
                            double arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                            SexagesimalDegree elong = Equatorial.getAngularSeparation(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        JulianDay puestaLuna = new JulianDay(getCurrent());
                                        setCurrent(new JulianDay(puestaSol).plus(new JulianDay(4 * (puestaLuna.getValue() - puestaSol.getValue()) / 9)));

                                        e = Moon.getPosicionAparente(getCurrent());
                                        ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                        h = e.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                                        hs = ns.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());
                                        arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                                        elong = Equatorial.getAngularSeparation(e, ns);
                                        il = Moon.getInfoLuna(getCurrent());
                                        double w = il.fraction * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            grafica.addMuestra(getCurrent().getValue(), il.fraction, "Fase");
                                            grafica.addMuestra(getCurrent().getValue(), h.getAzimuth().getSignedValue(), "A Luna");
                                            grafica.addMuestra(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Luna");
                                            grafica.addMuestra(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sol");

                                            año_++;
                                            FiestasMediaEstacion fme = new FiestasMediaEstacion(año_);
                                            JulianDay referencia;
                                            switch (opcion)
                                            {
                                                case 0:
                                                    referencia = fme.imbolc;
                                                    break;
                                                case 1:
                                                    referencia = fme.beltaine;
                                                    break;
                                                case 2:
                                                    referencia = fme.lugnasad;
                                                    break;
                                                case 3:
                                                    referencia = fme.samain;
                                                    break;
                                                case 4:
                                                    referencia = fme.solsticioVerano;
                                                    break;
                                                case 5:
                                                    referencia = fme.solsticioInvierno;
                                                    break;
                                                case 6:
                                                    referencia = fme.equinoccioPrimavera;
                                                    break;
                                                case 7:
                                                    referencia = fme.equinoccioOtoño;
                                                    break;
                                                default:
                                                    return;
                                            }
                                            setCurrent(referencia);
                                            estado = 0;
                                            d_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            estado = 1;
                                            setCurrent(new JulianDay(puestaSol.getValue() + 0.9));

                                            h_temp = null;
                                            return;
                                        }
                                    }
                                }
                            }
                            h_temp = hPrueba;
                        }

                        if (estado == 1)
                        {
                            Equatorial e = Moon.getPosicionAparente(getCurrent());
                            Equatorial ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs = ns.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());

                            double hPrueba = hs.getAltitude().getSignedValue();
                            double arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                            SexagesimalDegree elong = Equatorial.getAngularSeparation(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        puestaSol = new JulianDay(getCurrent());
                                        estado = 2;
                                        h_temp = null;
                                        return;
                                    }
                                }
                            }
                            h_temp = hPrueba;

                        }
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.Log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    private void UltimaLuna(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputCoordenadasLocales();
            String strAño = inputDialog("Año inicial", "-2000");
            String strNumero = inputDialog("Nº años", "10");

            List<CategoriaGrafica> categorias = new ArrayList<>();
            CategoriaGrafica categoria;

            categorias.add(categoria = new CategoriaGrafica("Fase Luna"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("Fase", Color.ORANGE, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Acimut"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Luna", Color.RED, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Elevación"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Luna", Color.WHITE, 4, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Sol", Color.WHITE, 2, "nulo"));

            GraficaJuliano grafica = new GraficaJuliano("", skeleton, categorias, "DJ");
            final int año = Integer.valueOf(strAño);
            final int n_año = Integer.valueOf(strNumero);

            String[] oo;

            int opcion = inputOpciones("Referencia", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Solsticio de verano", "Solsticio de invierno", "Equinoccio primavera", "Equinoccio otoño"
            }, "Solsticio de invierno");
            JulianDay referencia;

            tab = NuevoTab("Última Luna " + oo[opcion]);

            FiestasMediaEstacion fme = new FiestasMediaEstacion(año);
            switch (opcion)
            {
                case 0:
                    referencia = fme.imbolc;
                    break;
                case 1:
                    referencia = fme.beltaine;
                    break;
                case 2:
                    referencia = fme.lugnasad;
                    break;
                case 3:
                    referencia = fme.samain;
                    break;
                case 4:
                    referencia = fme.solsticioVerano;
                    break;
                case 5:
                    referencia = fme.solsticioInvierno;
                    break;
                case 6:
                    referencia = fme.equinoccioPrimavera;
                    break;
                case 7:
                    referencia = fme.equinoccioOtoño;
                    break;
                default:
                    return;
            }

            (new TemporalTask("Luna", referencia, new JulianDay(1, 1, año + n_año), 1.0 / (24.0 * 60)) //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay puestaLunaTemp;
                int año_ = año;
                int estado = 0;
                //estado = 1 disco iluminado >=0.1
                //estad

                @Override
                public void taskEnd()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il = Moon.getInfoLuna(getCurrent());
                        boolean marca = false;

                        if (estado == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.fraction > d_temp)
                                {
                                    if (il.fraction < 0.1 & il.fraction > 0 && d_temp <= il.fraction)
                                    {
                                        estado = 1;
                                        addToCurrent(-10);
                                    }
                                }
                            }
                            d_temp = il.fraction;
                        }

                        if (estado == 1)
                        {
                            Equatorial e = Moon.getPosicionAparente(getCurrent());
                            Equatorial ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs = ns.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());

                            double hPrueba = h.getAltitude().getSignedValue();
                            double arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                            SexagesimalDegree elong = Equatorial.getAngularSeparation(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba > h_temp)
                                {
                                    if (hPrueba > 1 && h_temp <= 1)
                                    {
                                        JulianDay puestaLuna = new JulianDay(getCurrent());
                                        il = Moon.getInfoLuna(getCurrent());
                                        double w = il.fraction * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            puestaLunaTemp = new JulianDay(getCurrent());
                                            addToCurrent(0.9);
                                            h_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            setCurrent(puestaLunaTemp);
                                            e = Moon.getPosicionAparente(getCurrent());
                                            ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                            h = e.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                                            hs = ns.toHorizontal(latitud, longitud, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());
                                            arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                                            elong = Equatorial.getAngularSeparation(e, ns);
                                            il = Moon.getInfoLuna(getCurrent());

                                            grafica.addMuestra(getCurrent().getValue(), il.fraction, "Fase");
                                            grafica.addMuestra(getCurrent().getValue(), h.getAzimuth().getSignedValue(), "A Luna");
                                            grafica.addMuestra(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Luna");
                                            grafica.addMuestra(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sol");

                                            año_++;
                                            FiestasMediaEstacion fme = new FiestasMediaEstacion(año_);
                                            JulianDay referencia;
                                            switch (opcion)
                                            {
                                                case 0:
                                                    referencia = fme.imbolc;
                                                    break;
                                                case 1:
                                                    referencia = fme.beltaine;
                                                    break;
                                                case 2:
                                                    referencia = fme.lugnasad;
                                                    break;
                                                case 3:
                                                    referencia = fme.samain;
                                                    break;
                                                case 4:
                                                    referencia = fme.solsticioVerano;
                                                    break;
                                                case 5:
                                                    referencia = fme.solsticioInvierno;
                                                    break;
                                                case 6:
                                                    referencia = fme.equinoccioPrimavera;
                                                    break;
                                                case 7:
                                                    referencia = fme.equinoccioOtoño;
                                                    break;
                                                default:
                                                    return;
                                            }
                                            setCurrent(referencia);
                                            estado = 0;
                                            d_temp = null;
                                            return;
                                        }
                                    }
                                }
                            }
                            h_temp = hPrueba;
                        }

                    }
                    catch (ProcessException ex)
                    {
                        Global.info.Log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    private boolean NuevoProyecto(TabPane tp) throws IOException
    {
        String nombre = inputDialog("Nombre estudio", "prueba");
        if (nombre.isEmpty())
        {
            return false;
        }
        Estudio estudio;
        estudios.add(estudio = new Estudio(nombre));
        estudio.Guardar();
        Tab tab = NuevoTab("Estudio " + nombre);
        tab.setContent(estudio.getVisualizacion(Principal.this));
        tab.setClosable(true);
        tab.setOnClosed((Event t) ->
        {
            Estudio activo = (Estudio) tab.getContent();

            if (activo.isCambio())
            {
                if (new MessageDialog(skeleton, "¿Guardar estudio " + nombre + "?", MessageDialog.TipoMensaje.AVISO).Show())
                {
                    try
                    {
                        activo.Salva();
                    }
                    catch (IOException ex)
                    {

                    }
                }
            }
            for (Estudio e : estudios)
            {
                if (e.equals(tab.getContent()))
                {
                    estudios.remove(e);
                }
            }
        });

        return true;

    }

    private boolean AbrirProyecto(TabPane tp)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir proyecto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archaeoastronomy Work Benche files", "*.awb"));
        File fichero = fileChooser.showOpenDialog(st);

        if (fichero == null)
        {
            return false;
        }
        Estudio estudio;
        estudios.add(estudio = new Estudio(fichero.getName().replace(".awb", ""), fichero.getParentFile().getAbsolutePath()));
        try
        {
            estudio.Carga();

            Tab tab = NuevoTab("Estudio " + estudio.getNombre());
            tab.setContent(estudio.getVisualizacion(Principal.this));
            tab.setClosable(true);
            tab.setOnClosed((Event t) ->
            {
                for (Estudio e : estudios)
                {
                    if (e.equals(tab.getContent()))
                    {
                        estudios.remove(e);
                    }
                }
            });

            return true;
        }
        catch (JDOMException ex)
        {
            new MessageDialog(skeleton, "No comprendo el plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        catch (IOException ex)
        {
            new MessageDialog(skeleton, "No encuentro el plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        catch (ProcessException ex)
        {
            new MessageDialog(skeleton, "Error en el formato de los campos del plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        return false;
    }

    class TareaAstro extends TemporalTask
    {

        String[] variables;
        List<CategoriaGrafica> categorias = new ArrayList<>();
        GraficaJuliano grafica;

        public TareaAstro(String[] variables, JulianDay desde, JulianDay hasta, double incremento)
        {
            super("", desde, hasta, incremento);
            this.variables = variables;
            for (String var : variables)
            {
                String[] v = var.split(",");
                boolean encontrado = false;
                CategoriaGrafica categoria = null;
                for (CategoriaGrafica c : categorias)
                {
                    if (c.getNombre().equals(v[1]))
                    {
                        encontrado = true;
                        categoria = c;
                    }
                }
                if (!encontrado)
                {
                    categorias.add(categoria = new CategoriaGrafica(v[1]));
                }
                categoria.listaConfigSerie.add(new SimpleConfigSerie(var));
            }
            grafica = new GraficaJuliano("", skeleton, categorias, "DJ");

        }

        @Override
        public void taskEnd()
        {

        }

        @Override
        public void cycle()
        {
            Equatorial e;
            Horizontal h;

            for (String var : variables)
            {
                try
                {
                    String[] v = var.split(",");
                    double valor = 0;
                    if (v[0].equals("Sol"))
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                valor = e.getDeclination().getSignedValue();
                                break;
                            case "AR":
                                e = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                valor = e.getRightAscension().getSignedValue();
                                break;
                            case "A":
                                e = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAzimuth().getValue();
                                break;
                            case "h":
                                e = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAltitude().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else if (v[0].equals("Luna"))
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = Moon.getPosicionAparente(getCurrent());
                                valor = e.getDeclination().getSignedValue();
                                break;
                            case "AR":
                                e = Moon.getPosicionAparente(getCurrent());
                                valor = e.getRightAscension().getSignedValue();
                                break;
                            case "A":
                                e = Moon.getPosicionAparente(getCurrent());
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAzimuth().getValue();
                                break;
                            case "h":
                                e = Moon.getPosicionAparente(getCurrent());
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAltitude().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else if ("Mercurio,Venus,Marte,Júpiter,Saturno".contains(v[0]))
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                                valor = e.getDeclination().getSignedValue();
                                break;
                            case "AR":
                                e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                                valor = e.getRightAscension().getSignedValue();
                                break;
                            case "A":
                                e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAzimuth().getValue();
                                break;
                            case "h":
                                e = new Planet(Catalogue.getPlanetEnum(v[0])).getApparentPosition(getCurrent());
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAltitude().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                valor = e.getDeclination().getSignedValue();
                                break;
                            case "AR":
                                e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                valor = e.getRightAscension().getSignedValue();
                                break;
                            case "A":
                                e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAzimuth().getValue();
                                break;
                            case "h":
                                e = Catalogue.getStar(Catalogue.getEnumEstrella(v[0])).getApparentPosition(getCurrent(), CalculusType.PRECISE);
                                h = e.toHorizontal(latitud, longitud, getCurrent());
                                valor = h.getAltitude().getSignedValue();
                                break;
                            default:
                        }
                    }
                    grafica.addMuestra(getCurrent().getValue(), valor, var);
                }
                catch (ProcessException ex)
                {
                    Global.info.Log(ex);
                }
            }
        }
    }

    public Node FenomenoEstelar(int año, Star estrella, double acimutUmbral, SexagesimalDegree latitud, SexagesimalDegree longitud) throws ProcessException
    {
        List<CategoriaGrafica> categorias = new ArrayList<>();
        CategoriaGrafica categoria;
        categorias.add(categoria = new CategoriaGrafica("Elevación"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("h Estrella", Color.RED, 2, "nulo"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("h Sol", Color.RED, 4, "nulo"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("horizonte", Color.WHITE, 2, "nulo"));
        categorias.add(categoria = new CategoriaGrafica("Acimut"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("A Estrella", Color.LIGHTGREEN, 2, "nulo"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("A Sol", Color.LIGHTGREEN, 4, "nulo"));
        categorias.add(categoria = new CategoriaGrafica("Declinación"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("DE Estrella", Color.ORANGE, 2, "nulo"));
        categorias.add(categoria = new CategoriaGrafica("Magnitud visual"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("m crítica", Color.CYAN, 4, "nulo"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("m Estrella", Color.CYAN, 2, "nulo"));
        categorias.add(categoria = new CategoriaGrafica("Calendario"));
        categoria.listaConfigSerie.add(new SimpleConfigSerie("fiestas", Color.WHITE, 4, "nulo"));
        /*categorias.add(categoria = new CategoriaGrafica("Brillo cielo"));
         categoria.listaConfigSerie.add(new SimpleConfigSerie("estándar", Color.GOLD, 2, "nulo"));
         categoria.listaConfigSerie.add(new SimpleConfigSerie("no estándar", Color.GOLD, 4, "nulo"));*/

        GraficaJuliano grafica = new GraficaJuliano("", skeleton, categorias, "DJ");
        grafica.listaEjes.get(grafica.listaEjes.size() - 1).setVisible(false);

        (new TemporalTask("Elevacion Sol", new JulianDay(1, 1, año), new JulianDay(1, 1, año + 1), 1.0 / (24 * 60))
        {
            Double a_temp = null;

            @Override
            public void taskEnd()
            {
                try
                {
                    FiestasMediaEstacion fme = new FiestasMediaEstacion(año);

                    grafica.addMuestra(fme.solsticioInvierno.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.solsticioInvierno.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.solsticioInvierno.getValue(), Double.NaN, "fiestas");
                    grafica.addMuestra(fme.solsticioVerano.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.solsticioVerano.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.solsticioVerano.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.imbolc.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.imbolc.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.imbolc.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.beltaine.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.beltaine.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.beltaine.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.lugnasad.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.lugnasad.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.lugnasad.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.samain.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.samain.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.samain.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.equinoccioPrimavera.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.equinoccioPrimavera.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.equinoccioPrimavera.getValue(), Double.NaN, "fiestas");

                    grafica.addMuestra(fme.equinoccioOtoño.getValue(), 0, "fiestas");
                    grafica.addMuestra(fme.equinoccioOtoño.getValue(), 1, "fiestas");
                    grafica.addMuestra(fme.equinoccioOtoño.getValue(), Double.NaN, "fiestas");

                }
                catch (ProcessException ex)
                {
                    Global.info.Log(ex);
                }
            }

            @Override
            public void cycle()
            {
                try
                {
                    //Ecuatorial mp=new Ecuatorial(new Grados(0),new Hora(0));
                    Equatorial ne = estrella.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                    Equatorial ns = Sun.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                    Horizontal h = ne.toHorizontal(latitud, longitud, getCurrent());
                    Horizontal hs = ns.toHorizontal(latitud, longitud, getCurrent());
                    double hEstrella = h.getAltitude().getSignedValue();
                    double AEstrella = h.getAzimuth().getValue();

                    boolean marca = false;

                    if (a_temp != null && abs(AEstrella - a_temp) < 10)
                    {
                        if (AEstrella > a_temp)
                        {
                            if (AEstrella >= acimutUmbral && a_temp < acimutUmbral)
                            {
                                marca = true;
                            }
                        }
                    }
                    if (marca)
                    {
                        grafica.addMuestra(getCurrent().getValue(), h.getAzimuth().getValue(), "A Estrella");
                        grafica.addMuestra(getCurrent().getValue(), hs.getAzimuth().getValue(), "A Sol");
                        grafica.addMuestra(getCurrent().getValue(), 0, "horizonte");
                        grafica.addMuestra(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Estrella");
                        grafica.addMuestra(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sol");
                        grafica.addMuestra(getCurrent().getValue(), ne.getDeclination().getSignedValue(), "DE Estrella");

                        grafica.addMuestra(getCurrent().getValue(), estrella.apparentMagnitude, "m Estrella");
                        addToCurrent(0.9);

                        double distanciaAngular = abs(h.getAzimuth().minus(hs.getAzimuth()).getSignedValue()) * PI / 180;
                        double Z = Radian.valueOf(new SexagesimalDegree(90).minus(h.getAltitude())).getValue();
                        double h0 = hs.getAltitude().getSignedValue() * PI / 180;

                        double log10_bst = 4.75 - distanciaAngular * Z / 3 + h0 * (12 + 8.21 * Z) + 2.86 * Z;
                        double bst = pow(10.0, log10_bst);
                        double mz = 6;
                        double kv = 0.2;

                        double log10_C = -9.8;
                        double K = pow(10, -1.9);

                        double Y1 = -0.2 * (mz + 16.57 + kv + 2.5 * log10_C);
                        double A1 = pow(-1 + pow(10, Y1), 2) / K;
                        double bst_umbral = (pow(10, 3.17) - A1) / (5 * kv) + 118;

                        if (bst >= bst_umbral)
                        {
                            log10_C = -8.35;
                            K = pow(10, -5.9);
                        }

                        double Y = -0.2 * (mz + 16.57 + kv + 2.5 * log10_C);
                        double b = pow(pow(10, Y) - 1, 2) / K + kv / 0.2 * (bst - 118);

                        double C = pow(10.0, log10_C);
                        double i = C * pow(1 + sqrt(K * b), 2);
                        double X = 1 / (cos(Z) + 0.025 * exp(-11 * cos(Z)));
                        double m = -16.57 - 2.5 * log10(i) - kv * X;
                        double logb = log10(b);
                        grafica.addMuestra(getCurrent().getValue(), log10_bst, "estándar");
                        grafica.addMuestra(getCurrent().getValue(), logb, "no estándar");

                        if (m > -1)
                        {
                            grafica.addMuestra(getCurrent().getValue(), m, "m crítica");
                        }
                        else
                        {
                            grafica.addMuestra(getCurrent().getValue(), Double.NaN, "m crítica");
                        }
                    }
                    a_temp = AEstrella;

                }
                catch (ProcessException ex)
                {
                    Global.info.Log(ex);
                }

            }
        }).taskStart();

        return grafica.getGrafica();
    }

    public Node Astro(String[] variables, JulianDay desde, JulianDay hasta, double incremento) throws ProcessException
    {
        TareaAstro astro;
        (astro = new TareaAstro(variables, desde, hasta, incremento)
        {

        }).taskStart();

        return astro.grafica.getGrafica();
    }

    @Override
    public void start(Stage primaryStage) throws ProcessException
    {
        st = primaryStage;
        st.getIcons().add(new Image(getClass().getResourceAsStream("/com/resources/Icon.png")));
        skeleton = new Skeleton(System.getProperty("user.home") + System.getProperty("file.separator") + "AWB", "awb", primaryStage, null);
        raiz = new VBox();
        Scene scene = new Scene(raiz);
        scene.getStylesheets().add(Skeleton.class.getResource("general.css").toExternalForm());
        skeleton.setScene(scene);
        primaryStage.setTitle("Archaeoastronomy Workbench");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        raiz.getStyleClass().add("fondo");
        primaryStage.show();

        HBox h = new HBox(10);
        TextField tf = new TextField();
        Label DiaJulianoLabel = new Label();
        tf.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) ->
        {
            try
            {
                double f = Double.valueOf(tf.getText().replace(",", "."));
                DiaJulianoLabel.setText(new JulianDay(f).toString());
            }
            catch (NumberFormatException ex)
            {

            }
        });
        hDiaJuliano.getChildren().addAll(new Label("Día Juliano"), tf, DiaJulianoLabel);

        raiz.getChildren().add(menu = new MenuBar());
        raiz.getChildren().add(tp = new TabPane());

        VBox.setVgrow(tp, Priority.ALWAYS);

        Menu m;
        MenuItem mi;
        menu.getMenus().add(m = new Menu("Estudio"));

        mi = new MenuItem("Nuevo proyecto");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent event) ->
        {
            try
            {
                NuevoProyecto(tp);
            }
            catch (IOException ex)
            {
                Global.info.Log(ex);
            }
        });
        mi = new MenuItem("Abrir proyecto");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent event) ->
        {
            AbrirProyecto(tp);
        });

        menu.getMenus().add(m = new Menu("Simulación"));
        mi = new MenuItem("Fenómeno estelar");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                FenomenoEstelar(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });
        
        
        mi = new MenuItem("Evolución temporal variables astros");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                SimulacionAstro(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });
        
        Menu mLuna = new Menu("Luna");
        m.getItems().add(mLuna);

        mi = new MenuItem("Primera Luna");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                PrimeraLuna(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });

        mi = new MenuItem("Última Luna");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                UltimaLuna(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });

        mi = new MenuItem("Luna Llena Primavera");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                LunaLlenaPrimavera(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });

        mi = new MenuItem("Coincidencia en elevación");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                CoincidenciaElevacion(tp);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });

        m.getItems().add(new SeparatorMenuItem());
        mi = new MenuItem("Calendario");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            String strAño = inputDialog("Año", "");
            try
            {
                FiestasMediaEstacion fme = new FiestasMediaEstacion(Integer.valueOf(strAño));
                GridPane10 rejilla = new GridPane10();
                rejilla.add(new Label("Fiesta media estación fin de invierno (Imbolc)"), 0, 0);
                rejilla.add(new Label("Equinoccio aparente de primavera"), 0, 1);
                rejilla.add(new Label("Fiesta media estación comienzo de verano (Beltaine)"), 0, 2);
                rejilla.add(new Label("Solsticio de verano"), 0, 3);
                rejilla.add(new Label("Fiesta media estación fin de verano (Lugnasad)"), 0, 4);
                rejilla.add(new Label("Equinoccio aparente de otoño"), 0, 5);
                rejilla.add(new Label("Fiesta media estación comienzo de invierno (Samain)"), 0, 6);
                rejilla.add(new Label("Solsticio de invierno"), 0, 7);

                rejilla.add(new BordererLabel(fme.imbolc.getSimpleDate().toString()), 1, 0);
                rejilla.add(new BordererLabel(fme.equinoccioPrimavera.getSimpleDate().toString()), 1, 1);
                rejilla.add(new BordererLabel(fme.beltaine.getSimpleDate().toString()), 1, 2);
                rejilla.add(new BordererLabel(fme.solsticioVerano.getSimpleDate().toString()), 1, 3);
                rejilla.add(new BordererLabel(fme.lugnasad.getSimpleDate().toString()), 1, 4);
                rejilla.add(new BordererLabel(fme.equinoccioOtoño.getSimpleDate().toString()), 1, 5);
                rejilla.add(new BordererLabel(fme.samain.getSimpleDate().toString()), 1, 6);
                rejilla.add(new BordererLabel(fme.solsticioInvierno.getSimpleDate().toString()), 1, 7);

                NuevoTab("Calendario " + strAño).setContent(rejilla);
            }
            catch (ProcessException ex)
            {
                Global.info.Log(ex);
            }
        });

        menu.getMenus().add(m = new Menu("Herramientas"));
        CheckMenuItem cmi = new CheckMenuItem("Día Juliano");
        m.getItems().add(cmi);
        cmi.setOnAction((ActionEvent t) ->
        {

            if (cmi.isSelected())
            {
                raiz.getChildren().add(hDiaJuliano);
            }
            else
            {
                raiz.getChildren().remove(hDiaJuliano);
            }

        });

        menu.getMenus().add(m = new Menu("Aplicación"));
        mi = new MenuItem("Acerca de...");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            new MessageDialog(skeleton, "Archaeoastronomy Work Bench 2015", MessageDialog.TipoMensaje.INFO).Show();
        });

        mi = new MenuItem("Cerrar");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            st.close();
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
