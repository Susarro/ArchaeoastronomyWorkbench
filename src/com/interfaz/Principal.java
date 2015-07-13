/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz;

import com.interfaz.esqueleto.ModalDialog;
import com.interfaz.esqueleto.MessageDialog;
import com.interfaz.esqueleto.Esqueleto;
import com.CancelExcepcion;
import com.Excepcion;
import com.FX.LabelBorde;
import com.FX.Rejilla;
import com.Global;
import com.TareaTemporal;
import com.astronomia.Catalogo;
import com.astronomia.DiaJuliano;
import com.astronomia.Estrella;
import com.astronomia.InfoLuna;
import com.astronomia.Luna;
import com.astronomia.Planeta;
import com.astronomia.Sol;
import com.astronomia.sistema.Ecuatorial;
import com.astronomia.sistema.Horizontal;
import com.astronomia.sistema.HorizontalAparente;
import com.astronomia.estudio.Estudio;
import com.calendario.FiestasMediaEstacion;
import static com.enumEstrella.ALPHA_TAURI;
import com.enumPlaneta;
import static com.enumPlaneta.SATURNO;
import com.grafica.CategoriaGrafica;
import com.grafica.GraficaJuliano;
import com.grafica.SimpleConfigSerie;
import com.tipoCalculo;
import com.unidades.Grados;
import com.unidades.Radianes;
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

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Principal extends Application
{

    public Esqueleto esqueleto;
    VBox raiz;
    TabPane tp;
    MenuBar menu;

    public Grados latitud;
    public Grados longitud;

    DiaJuliano desde;
    DiaJuliano hasta;
    double incremento = 1;
    Stage st;

    static List<Estudio> estudios = new CopyOnWriteArrayList<>();
    private MenuItem miCerrarProyecto;
    private MenuItem miGuardarProyecto;
    private HBox hDiaJuliano = new HBox(10);

    public final void inputCoordenadasLocales() throws Excepcion, CancelExcepcion
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

        ModalDialog dialogo = new ModalDialog(esqueleto, gridPane, true);
        if (dialogo.ShowModal())
        {
            latitud = Grados.valueOf(tfLatitud.getText());
            longitud = Grados.valueOf(tfLongitud.getText());
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    public final void inputTiempo() throws Excepcion, CancelExcepcion
    {
        TextField tfDesde;
        TextField tfHasta;
        TextField tfIncremento;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Desde"), 0, 0);
        gridPane.add(tfDesde = new TextField(desde.toString()), 1, 0);
        gridPane.add(new Label("Hasta"), 0, 1);
        gridPane.add(tfHasta = new TextField(hasta.toString()), 1, 1);
        gridPane.add(new Label("Incremento (días)"), 0, 2);
        gridPane.add(tfIncremento = new TextField(String.valueOf(incremento)), 1, 2);

        ModalDialog dialogo = new ModalDialog(esqueleto, gridPane, true);
        if (dialogo.ShowModal())
        {
            desde = DiaJuliano.valueOf(tfDesde.getText());
            hasta = DiaJuliano.valueOf(tfHasta.getText());
            incremento = Double.valueOf(tfIncremento.getText());
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    public Estrella inputEstrella() throws Excepcion, CancelExcepcion
    {
        TextField tfLatitud;
        TextField tfLongitud;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Estrella"), 0, 0);
        ComboBox lv;
        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(Catalogo.getStrEstrellas())), 1, 0);
        lv.getSelectionModel().select(0);

        ModalDialog dialogo = new ModalDialog(esqueleto, gridPane, true);
        if (dialogo.ShowModal())
        {
            return Catalogo.getEstrella(Catalogo.getEnumEstrella(lv.getSelectionModel().getSelectedItem().toString()));
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
        for (String p : Catalogo.getStrEstrellas())
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

    public String[] inputVariables() throws Excepcion, CancelExcepcion
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

        ModalDialog dialogo = new ModalDialog(esqueleto, h, true);
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
        ModalDialog dialogo = new ModalDialog(esqueleto, p, true);
        if (dialogo.ShowModal())
        {
            return tf.getText();
        }
        else
        {
            return "";
        }
    }

    public int inputOpciones(String msg, String[] opciones, String defecto) throws Excepcion
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label(msg), 0, 0);
        ComboBox lv;

        gridPane.add(lv = new ComboBox(FXCollections.observableArrayList(opciones)), 1, 0);
        lv.getSelectionModel().select(defecto);

        ModalDialog dialogo = new ModalDialog(esqueleto, gridPane, true);
        if (dialogo.ShowModal())
        {
            return lv.getSelectionModel().getSelectedIndex();
        }
        else
        {
            return -1;
        }
    }

    public Principal() throws Excepcion
    {
        latitud = Grados.valueOf("42º34'28''");
        longitud = Grados.valueOf("6º20'01''");
        desde = new DiaJuliano(1, 1, 2015);
        hasta = new DiaJuliano(1, 1, 2016);
    }

    public Tab NuevoTab(String nombre)
    {
        Tab tab;
        tp.getTabs().add(tab = new Tab(nombre));
        tp.getSelectionModel().select(tab);
        return tab;
    }

    private void FenomenoEstelar(TabPane tp) throws Excepcion
    {
        try
        {
            Tab tab;
            inputCoordenadasLocales();
            String strAño = inputDialog("Año", "");
            String strAcimut = inputDialog("Acimut", "");
            Estrella estrella = inputEstrella();
            tab = NuevoTab(estrella.nombre + " " + strAño);
            tab.setContent(FenomenoEstelar(Integer.valueOf(strAño), estrella, Double.valueOf(strAcimut), latitud, longitud));
        }
        catch (NullPointerException ex)
        {
            Global.info.Registra(ex);
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    private void SimulacionAstro(TabPane tp) throws Excepcion
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
            Global.info.Registra(ex);
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

    private void ConjuncionSaturnoAldebaran(TabPane tp) throws Excepcion
    {
        try
        {
            Tab tab;
            inputCoordenadasLocales();
            inputTiempo();
            tab = NuevoTab("Saturno & Aldebarán");
            List<CategoriaGrafica> categorias = new ArrayList<>();
            CategoriaGrafica categoria;

            categorias.add(categoria = new CategoriaGrafica("Declinación"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("DE Saturno", Color.ORANGE, 4, "rectángulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("DE Tauro", Color.ORANGE, 2, "rectángulo"));
            categorias.add(categoria = new CategoriaGrafica("Acimut"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Saturno", Color.RED, 4, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("A Tauro", Color.RED, 2, "nulo"));
            categorias.add(categoria = new CategoriaGrafica("Elevación"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Saturno", Color.WHITE, 4, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("h Tauro", Color.WHITE, 2, "nulo"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("diferencia", Color.CYAN, 2, "nulo"));

            GraficaJuliano grafica = new GraficaJuliano("", esqueleto, categorias, "DJ");

            (new TareaTemporal(esqueleto,"Saturno & Aldebarán", desde, hasta, incremento)
            {
                Double h_temp = null;
                Double temp = null;
                Double temp2 = null;

                int estado = 0;
                //estado = 1 disco iluminado >=0.1
                //estad

                Planeta Saturno = new Planeta(SATURNO);
                Estrella Tauro = Catalogo.getEstrella(ALPHA_TAURI);

                @Override
                public void terminado()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void ciclo()
                {
                    try
                    {
                        Ecuatorial eS = Saturno.getPosicionAparente(getActual());
                        Ecuatorial eT = Tauro.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                        HorizontalAparente hS = eS.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SATURNO, getActual());
                        HorizontalAparente hT = eT.toHorizontal(latitud, longitud, getActual()).toAparente();

                        double difH = hS.getElevacion().getSignedValue() - hT.getElevacion().getSignedValue();

                        if (temp != null)
                        {
                            if (hS.getElevacion().getSignedValue() > temp)
                            {
                                if (hS.getElevacion().getSignedValue() >= 0 && temp < 0)
                                {
                                    if (temp2 != null && difH <= 0 && temp2 > 0)
                                    {
                                        if (Signo(hS.getAcimut().getSignedValue()) != Signo(hT.getAcimut().getSignedValue()))
                                        {                                                                                      
                                            addActual(365.24 * 14);
                                            temp2 = null;
                                        }
                                        else
                                        {
                                            grafica.addMuestra(getActual().getValue(), eS.getDeclinacion().getSignedValue(), "DE Saturno");
                                            grafica.addMuestra(getActual().getValue(), eT.getDeclinacion().getSignedValue(), "DE Tauro");
                                            grafica.addMuestra(getActual().getValue(), hS.getAcimut().getSignedValue(), "A Saturno");
                                            grafica.addMuestra(getActual().getValue(), hT.getAcimut().getSignedValue(), "A Tauro");
                                            grafica.addMuestra(getActual().getValue(), hS.getElevacion().getSignedValue(), "h Saturno");
                                            grafica.addMuestra(getActual().getValue(), hT.getElevacion().getSignedValue(), "h Tauro");
                                            grafica.addMuestra(getActual().getValue(), difH, "diferencia");                                           
                                            addActual(365.24 * 29);
                                            temp2 = null;
                                        }
                                    }
                                    
                                    addActual(0.9);
                                    temp = null;
                                    temp2 = difH;
                                }
                            }
                        }
                        temp = hS.getElevacion().getSignedValue();

                    }
                    catch (Excepcion ex)
                    {
                        Global.info.Registra(ex);
                    }

                }
                
            }).Inicia();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

   

    private void PrimeraLuna(TabPane tp) throws Excepcion
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

            GraficaJuliano grafica = new GraficaJuliano("", esqueleto, categorias, "DJ");
            final int año = Integer.valueOf(strAño);
            final int n_año = Integer.valueOf(strNumero);

            String[] oo;

            int opcion = inputOpciones("Referencia", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Solsticio de verano", "Solsticio de invierno", "Equinoccio primavera", "Equinoccio otoño"
            }, "Solsticio de invierno");
            DiaJuliano referencia;

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

            (new TareaTemporal(esqueleto,"Luna", referencia, new DiaJuliano(1, 1, año + n_año), 1.0 / (24.0 * 60))
            //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                DiaJuliano puestaSol;
                int año_ = año;
                int estado = 0;
                //estado = 1 disco iluminado >=0.1
                //estad

                @Override
                public void terminado()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void ciclo()
                {
                    try
                    {
                        InfoLuna il = Luna.getInfoLuna(getActual());
                        boolean marca = false;

                        if (estado == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.discoIluminado > d_temp)
                                {
                                    if (il.discoIluminado < 0.1 & il.discoIluminado > 0 && d_temp <= il.discoIluminado)
                                    {
                                        estado = 1;
                                    }
                                }
                            }
                            d_temp = il.discoIluminado;
                        }

                        if (estado == 2)
                        {
                            Ecuatorial e = Luna.getPosicionAparente(getActual());
                            Ecuatorial ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                            HorizontalAparente h = e.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.LUNA, getActual());
                            HorizontalAparente hs = ns.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SOL, getActual());

                            double hPrueba = h.getElevacion().getSignedValue();
                            double arco = h.getElevacion().getSignedValue() - hs.getElevacion().getSignedValue();
                            Grados elong = Ecuatorial.getSeparacionAngular(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        DiaJuliano puestaLuna = new DiaJuliano(getActual());
                                        setActual(new DiaJuliano(puestaSol).plus(new DiaJuliano(4 * (puestaLuna.getValue() - puestaSol.getValue()) / 9)));                                        

                                        e = Luna.getPosicionAparente(getActual());
                                        ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                        h = e.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.LUNA, getActual());
                                        hs = ns.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SOL, getActual());
                                        arco = h.getElevacion().getSignedValue() - hs.getElevacion().getSignedValue();
                                        elong = Ecuatorial.getSeparacionAngular(e, ns);
                                        il = Luna.getInfoLuna(getActual());
                                        double w = il.discoIluminado * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            grafica.addMuestra(getActual().getValue(), il.discoIluminado, "Fase");
                                            grafica.addMuestra(getActual().getValue(), h.getAcimut().getSignedValue(), "A Luna");
                                            grafica.addMuestra(getActual().getValue(), h.getElevacion().getSignedValue(), "h Luna");
                                            grafica.addMuestra(getActual().getValue(), hs.getElevacion().getSignedValue(), "h Sol");

                                            año_++;
                                            FiestasMediaEstacion fme = new FiestasMediaEstacion(año_);
                                            DiaJuliano referencia;
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
                                            setActual(referencia);
                                            estado = 0;
                                            d_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            estado = 1;
                                            setActual(new DiaJuliano(puestaSol.getValue() + 0.9));
                                            
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
                            Ecuatorial e = Luna.getPosicionAparente(getActual());
                            Ecuatorial ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                            HorizontalAparente h = e.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.LUNA, getActual());
                            HorizontalAparente hs = ns.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SOL, getActual());

                            double hPrueba = hs.getElevacion().getSignedValue();
                            double arco = h.getElevacion().getSignedValue() - hs.getElevacion().getSignedValue();
                            Grados elong = Ecuatorial.getSeparacionAngular(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        puestaSol = new DiaJuliano(getActual());
                                        estado = 2;
                                        h_temp = null;
                                        return;
                                    }
                                }
                            }
                            h_temp = hPrueba;

                        }
                    }
                    catch (Excepcion ex)
                    {
                        Global.info.Registra(ex);
                    }

                }
            }).Inicia();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    private void UltimaLuna(TabPane tp) throws Excepcion
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

            GraficaJuliano grafica = new GraficaJuliano("", esqueleto, categorias, "DJ");
            final int año = Integer.valueOf(strAño);
            final int n_año = Integer.valueOf(strNumero);

            String[] oo;

            int opcion = inputOpciones("Referencia", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Solsticio de verano", "Solsticio de invierno", "Equinoccio primavera", "Equinoccio otoño"
            }, "Solsticio de invierno");
            DiaJuliano referencia;

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

            (new TareaTemporal(esqueleto,"Luna", referencia, new DiaJuliano(1, 1, año + n_año), 1.0 / (24.0 * 60))
            //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                DiaJuliano puestaLunaTemp;
                int año_ = año;
                int estado = 0;
                //estado = 1 disco iluminado >=0.1
                //estad

                @Override
                public void terminado()
                {
                    tab.setContent(grafica.getGrafica());
                }

                @Override
                public void ciclo()
                {
                    try
                    {
                        InfoLuna il = Luna.getInfoLuna(getActual());
                        boolean marca = false;

                        if (estado == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.discoIluminado > d_temp)
                                {
                                    if (il.discoIluminado < 0.1 & il.discoIluminado > 0 && d_temp <= il.discoIluminado)
                                    {
                                        estado = 1;                                        
                                        addActual(-10);
                                    }
                                }
                            }
                            d_temp = il.discoIluminado;
                        }

                        if (estado == 1)
                        {
                            Ecuatorial e = Luna.getPosicionAparente(getActual());
                            Ecuatorial ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                            HorizontalAparente h = e.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.LUNA, getActual());
                            HorizontalAparente hs = ns.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SOL, getActual());

                            double hPrueba = h.getElevacion().getSignedValue();
                            double arco = h.getElevacion().getSignedValue() - hs.getElevacion().getSignedValue();
                            Grados elong = Ecuatorial.getSeparacionAngular(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba > h_temp)
                                {
                                    if (hPrueba > 1 && h_temp <= 1)
                                    {
                                        DiaJuliano puestaLuna = new DiaJuliano(getActual());
                                        il = Luna.getInfoLuna(getActual());
                                        double w = il.discoIluminado * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            puestaLunaTemp = new DiaJuliano(getActual());                                            
                                            addActual(0.9);
                                            h_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            setActual(puestaLunaTemp);
                                            e = Luna.getPosicionAparente(getActual());
                                            ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                            h = e.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.LUNA, getActual());
                                            hs = ns.toHorizontal(latitud, longitud, getActual()).toAparente(enumPlaneta.SOL, getActual());
                                            arco = h.getElevacion().getSignedValue() - hs.getElevacion().getSignedValue();
                                            elong = Ecuatorial.getSeparacionAngular(e, ns);
                                            il = Luna.getInfoLuna(getActual());

                                            grafica.addMuestra(getActual().getValue(), il.discoIluminado, "Fase");
                                            grafica.addMuestra(getActual().getValue(), h.getAcimut().getSignedValue(), "A Luna");
                                            grafica.addMuestra(getActual().getValue(), h.getElevacion().getSignedValue(), "h Luna");
                                            grafica.addMuestra(getActual().getValue(), hs.getElevacion().getSignedValue(), "h Sol");

                                            año_++;
                                            FiestasMediaEstacion fme = new FiestasMediaEstacion(año_);
                                            DiaJuliano referencia;
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
                                            setActual(referencia);
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
                    catch (Excepcion ex)
                    {
                        Global.info.Registra(ex);
                    }

                }
            }).Inicia();
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
                if (new MessageDialog(esqueleto, "¿Guardar estudio " + nombre + "?", MessageDialog.TipoMensaje.AVISO).Show())
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
            new MessageDialog(esqueleto, "No comprendo el plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        catch (IOException ex)
        {
            new MessageDialog(esqueleto, "No encuentro el plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        catch (Excepcion ex)
        {
            new MessageDialog(esqueleto, "Error en el formato de los campos del plan", MessageDialog.TipoMensaje.ERROR).Show();
        }
        return false;
    }

    

    class TareaAstro extends TareaTemporal
    {

        String[] variables;
        List<CategoriaGrafica> categorias = new ArrayList<>();
        GraficaJuliano grafica;

        public TareaAstro(String[] variables, DiaJuliano desde, DiaJuliano hasta, double incremento)
        {
            super(esqueleto,"", desde, hasta, incremento);
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
            grafica = new GraficaJuliano("", esqueleto, categorias, "DJ");

        }

        @Override
        public void terminado()
        {

        }

        @Override
        public void ciclo()
        {
            Ecuatorial e;
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
                                e = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                valor = e.getDeclinacion().getSignedValue();
                                break;
                            case "AR":
                                e = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                valor = e.getAscensionRecta().getSignedValue();
                                break;
                            case "A":
                                e = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getAcimut().getValor();
                                break;
                            case "h":
                                e = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getElevacion().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else if (v[0].equals("Luna"))
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = Luna.getPosicionAparente(getActual());
                                valor = e.getDeclinacion().getSignedValue();
                                break;
                            case "AR":
                                e = Luna.getPosicionAparente(getActual());
                                valor = e.getAscensionRecta().getSignedValue();
                                break;
                            case "A":
                                e = Luna.getPosicionAparente(getActual());
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getAcimut().getValor();
                                break;
                            case "h":
                                e = Luna.getPosicionAparente(getActual());
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getElevacion().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else if ("Mercurio,Venus,Marte,Júpiter,Saturno".contains(v[0]))
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = new Planeta(Catalogo.getEnumPlaneta(v[0])).getPosicionAparente(getActual());
                                valor = e.getDeclinacion().getSignedValue();
                                break;
                            case "AR":
                                e = new Planeta(Catalogo.getEnumPlaneta(v[0])).getPosicionAparente(getActual());
                                valor = e.getAscensionRecta().getSignedValue();
                                break;
                            case "A":
                                e = new Planeta(Catalogo.getEnumPlaneta(v[0])).getPosicionAparente(getActual());
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getAcimut().getValor();
                                break;
                            case "h":
                                e = new Planeta(Catalogo.getEnumPlaneta(v[0])).getPosicionAparente(getActual());
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getElevacion().getSignedValue();
                                break;
                            default:
                        }
                    }
                    else
                    {
                        switch (v[1])
                        {
                            case "DE":
                                e = Catalogo.getEstrella(Catalogo.getEnumEstrella(v[0])).getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                valor = e.getDeclinacion().getSignedValue();
                                break;
                            case "AR":
                                e = Catalogo.getEstrella(Catalogo.getEnumEstrella(v[0])).getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                valor = e.getAscensionRecta().getSignedValue();
                                break;
                            case "A":
                                e = Catalogo.getEstrella(Catalogo.getEnumEstrella(v[0])).getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getAcimut().getValor();
                                break;
                            case "h":
                                e = Catalogo.getEstrella(Catalogo.getEnumEstrella(v[0])).getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                                h = e.toHorizontal(latitud, longitud, getActual());
                                valor = h.getElevacion().getSignedValue();
                                break;
                            default:
                        }
                    }
                    grafica.addMuestra(getActual().getValue(), valor, var);
                }
                catch (Excepcion ex)
                {
                    Global.info.Registra(ex);
                }
            }
        }
    }

    public Node FenomenoEstelar(int año, Estrella estrella, double acimutUmbral, Grados latitud, Grados longitud) throws Excepcion
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

        GraficaJuliano grafica = new GraficaJuliano("", esqueleto, categorias, "DJ");
        grafica.listaEjes.get(grafica.listaEjes.size() - 1).setVisible(false);

        (new TareaTemporal(esqueleto,"Elevacion Sol", new DiaJuliano(1, 1, año), new DiaJuliano(1, 1, año + 1), 1.0 / (24 * 60))
        {
            Double a_temp = null;

            @Override
            public void terminado()
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
                catch (Excepcion ex)
                {
                    Global.info.Registra(ex);
                }
            }

            @Override
            public void ciclo()
            {
                try
                {
                    //Ecuatorial mp=new Ecuatorial(new Grados(0),new Hora(0));
                    Ecuatorial ne = estrella.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                    Ecuatorial ns = Sol.getPosicionAparente(getActual(), tipoCalculo.PRECISO);
                    Horizontal h = ne.toHorizontal(latitud, longitud, getActual());
                    Horizontal hs = ns.toHorizontal(latitud, longitud, getActual());
                    double hEstrella = h.getElevacion().getSignedValue();
                    double AEstrella = h.getAcimut().getValor();

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
                        grafica.addMuestra(getActual().getValue(), h.getAcimut().getValor(), "A Estrella");
                        grafica.addMuestra(getActual().getValue(), hs.getAcimut().getValor(), "A Sol");
                        grafica.addMuestra(getActual().getValue(), 0, "horizonte");
                        grafica.addMuestra(getActual().getValue(), h.getElevacion().getSignedValue(), "h Estrella");
                        grafica.addMuestra(getActual().getValue(), hs.getElevacion().getSignedValue(), "h Sol");
                        grafica.addMuestra(getActual().getValue(), ne.getDeclinacion().getSignedValue(), "DE Estrella");

                        grafica.addMuestra(getActual().getValue(), estrella.magnitudVisual, "m Estrella");
                        addActual(0.9);                        

                        double distanciaAngular = abs(h.getAcimut().minus(hs.getAcimut()).getSignedValue()) * PI / 180;
                        double Z = Radianes.valueOf(new Grados(90).minus(h.getElevacion())).getValor();
                        double h0 = hs.getElevacion().getSignedValue() * PI / 180;

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
                        grafica.addMuestra(getActual().getValue(), log10_bst, "estándar");
                        grafica.addMuestra(getActual().getValue(), logb, "no estándar");

                        if (m > -1)
                        {
                            grafica.addMuestra(getActual().getValue(), m, "m crítica");
                        }
                        else
                        {
                            grafica.addMuestra(getActual().getValue(), Double.NaN, "m crítica");
                        }
                    }
                    a_temp = AEstrella;

                }
                catch (Excepcion ex)
                {
                    Global.info.Registra(ex);
                }

            }
        }).Inicia();

        return grafica.getGrafica();
    }

    public Node Astro(String[] variables, DiaJuliano desde, DiaJuliano hasta, double incremento) throws Excepcion
    {
        TareaAstro astro;
        (astro = new TareaAstro(variables, desde, hasta, incremento)
        {

        }).Inicia();

        return astro.grafica.getGrafica();
    }

    @Override
    public void start(Stage primaryStage) throws Excepcion
    {
        st = primaryStage;
        st.getIcons().add(new Image(getClass().getResourceAsStream("/com/resources/Icon.png")));
        esqueleto = new Esqueleto(System.getProperty("user.home") + System.getProperty("file.separator") + "AWB", "awb", primaryStage, null);
        raiz = new VBox();
        Scene scene = new Scene(raiz);
        scene.getStylesheets().add(Esqueleto.class.getResource("general.css").toExternalForm());
        esqueleto.scene = scene;
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
                DiaJulianoLabel.setText(new DiaJuliano(f).toString());
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
                Global.info.Registra(ex);
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
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
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
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
            }
        });

        mi = new MenuItem("Primera Luna");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                PrimeraLuna(tp);
            }
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
            }
        });

        mi = new MenuItem("Última Luna");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                UltimaLuna(tp);
            }
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
            }
        });

        mi = new MenuItem("Conjunción Saturno-Aldebarán");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t) ->
        {
            try
            {
                ConjuncionSaturnoAldebaran(tp);
            }
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
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
                Rejilla rejilla=new Rejilla();
                rejilla.add(new Label("Fiesta media estación fin de invierno (Imbolc)"),0,0);
                rejilla.add(new Label("Equinoccio aparente de primavera"),0,1);
                rejilla.add(new Label("Fiesta media estación comienzo de verano (Beltaine)"),0,2);
                rejilla.add(new Label("Solsticio de verano"),0,3);
                rejilla.add(new Label("Fiesta media estación fin de verano (Lugnasad)"),0,4);
                rejilla.add(new Label("Equinoccio aparente de otoño"),0,5);
                rejilla.add(new Label("Fiesta media estación comienzo de invierno (Samain)"),0,6);
                rejilla.add(new Label("Solsticio de invierno"),0,7);
                
                rejilla.add(new LabelBorde(fme.imbolc.getFecha().toString()),1,0);
                rejilla.add(new LabelBorde(fme.equinoccioPrimavera.getFecha().toString()),1,1);
                rejilla.add(new LabelBorde(fme.beltaine.getFecha().toString()),1,2);
                rejilla.add(new LabelBorde(fme.solsticioVerano.getFecha().toString()),1,3);
                rejilla.add(new LabelBorde(fme.lugnasad.getFecha().toString()),1,4);
                rejilla.add(new LabelBorde(fme.equinoccioOtoño.getFecha().toString()),1,5);
                rejilla.add(new LabelBorde(fme.samain.getFecha().toString()),1,6);
                rejilla.add(new LabelBorde(fme.solsticioInvierno.getFecha().toString()),1,7);
                
                NuevoTab("Calendario "+strAño).setContent(rejilla);                
            }
            catch (Excepcion ex)
            {
                Global.info.Registra(ex);
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
            new MessageDialog(esqueleto, "Archaeoastronomy Work Bench 2015", MessageDialog.TipoMensaje.INFO).Show();
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
