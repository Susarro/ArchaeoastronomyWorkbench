/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grafica;


import com.Global;
import com.interfaz.esqueleto.Esqueleto;
import com.interfaz.esqueleto.PseudoModalDialog;
import com.sun.javafx.charts.Legend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 *
 * @author MIGUEL_ANGEL
 */
public abstract class Grafica implements GraficaInterface
{

    VBox marcoGrafica;
    VBox leyenda;
    StackPane grafica;
    List<CategoriaGrafica> categorias;
    final int anchoEje = 50;
    List<Chart> charts = new ArrayList<>();
    ContextMenu menu;

    int numeroSeries = 0;
    Esqueleto padre;
    Rectangle fondo;

    public List<MenuItem> listaMenuContextual = new ArrayList<>();

    public ContextMenu getContextMenu()
    {
        return menu;
    }

   
   

    LeyendaEje getLeyendaEje(String nombre)
    {
        for (Node n : leyenda.getChildren())
        {
            if (n instanceof LeyendaEje)
            {
                if (((LeyendaEje) n).getNombre().equals(nombre))
                {
                    return (LeyendaEje) n;
                }
            }
        }
        return null;
    }

    public void AddAnotacion(double x, double y, String texto, String categoria)
    {
        for (int i = 0; i < charts.size(); i++)
        {
            if (charts.get(i).getId().equals(categoria))
            {
                panelAnotacion.addAnnotation(x, y, texto, (LineChart) charts.get(i));
                return;
            }
        }
    }

    protected PanelAnotacion panelAnotacion;

    public LineChart.Series getSerie(String nombre)
    {
        for (Chart chart : charts)
        {
            for (int i = 0; i < ((LineChart) chart).getData().size(); i++)
            {
                LineChart.Series serie = (LineChart.Series) ((LineChart) chart).getData().get(i);
                if (serie.getName().equals(nombre))
                {
                    return serie;
                }
            }
        }
        return null;
    }

    void editarSerie(final LineChart.Series serie)
    {
        String[] estilo = serie.getNode().getStyle().split(";");
        String strColor = "black";
        final TextField editGrosor = new TextField();

        String tempS = "nulo";
        for (String e : estilo)
        {
            if (e.contains("-fx-stroke:"))
            {
                strColor = e.replace("-fx-stroke: #", "0x");
            }
            else if (e.contains("-fx-stroke-width:"))
            {
                editGrosor.setText(e.replace("-fx-stroke-width: ", ""));
            }
            else if (e.contains("simbolo:"))
            {
                tempS = e.replace("simbolo: ", "");
            }
        }
        final String simbolo = tempS;

        final List<Simbolo> listaSimbolos = new ArrayList<>();
        final ObservableList<Simbolo> modeloListaSimbolos;
        final ListView<Simbolo> comboSimbolos = new ListView();
        listaSimbolos.add(new Simbolo("nulo", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("rectángulo", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("círculo", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("triángulo", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("cruz", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("diamante", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("rectángulo hueco", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("círculo hueco", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("triángulo hueco", Color.web(strColor)));
        listaSimbolos.add(new Simbolo("diamante hueco", Color.web(strColor)));

        modeloListaSimbolos = FXCollections.observableList(listaSimbolos);
        comboSimbolos.setItems(modeloListaSimbolos);
        comboSimbolos.setCellFactory(new Callback<ListView<Simbolo>, ListCell<Simbolo>>()
        {

            @Override
            public ListCell<Simbolo> call(ListView<Simbolo> p)
            {

                ListCell<Simbolo> cell = new ListCell<Simbolo>()
                {

                    @Override
                    protected void updateItem(Simbolo t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            setText("");
                            setGraphic(t.getRepresentacion());
                        }
                    }
                };

                return cell;
            }
        });
        for (Simbolo smb : modeloListaSimbolos)
        {
            if (smb.getNombre().equals(simbolo))
            {
                comboSimbolos.getSelectionModel().select(smb);
            }
        }

        final ColorPicker colorPicker = new ColorPicker(Color.web(strColor));

        colorPicker.setOnAction((ActionEvent t) ->
        {
            String sc = colorPicker.getValue().toString();
            modeloListaSimbolos.clear();
            modeloListaSimbolos.add(new Simbolo("nulo", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("rectángulo", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("círculo", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("triángulo", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("cruz", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("diamante", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("rectángulo hueco", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("círculo hueco", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("triángulo hueco", Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("diamante hueco", Color.web(sc)));
            
            comboSimbolos.setItems(modeloListaSimbolos);
            for (Simbolo smb : modeloListaSimbolos)
            {
                if (smb.getNombre().equals(simbolo))
                {
                    comboSimbolos.getSelectionModel().select(smb);
                }
            }
        });

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Serie"), 0, 0);
        grid.add(new Label(serie.getName()), 1, 0);
        grid.add(new Label("Color"), 0, 1);
        grid.add(colorPicker, 1, 1);
        grid.add(new Label("Grosor"), 0, 2);
        grid.add(editGrosor, 1, 2);
        grid.add(new Label("Símbolo"), 0, 3);
        grid.add(comboSimbolos, 1, 3);

        new PseudoModalDialog(padre, grid,true)
        {

            @Override
            public boolean Validacion()
            {
                String strColor = colorPicker.getValue().toString();
                serie.getNode().setStyle("-fx-stroke: " + strColor.replace("0x", "#") + ";-fx-stroke-width: " + editGrosor.getText() + ";");
                panelAnotacion.setColorPosicionCuersor(Color.web(strColor), serie.getName());
                for (int i = 0; i < serie.getData().size(); i++)
                {
                    LineChart.Data dato = (LineChart.Data) serie.getData().get(i);
                    if (comboSimbolos.getSelectionModel().getSelectedItem() != null)
                    {
                        ((Label) dato.getNode()).setGraphic(new Simbolo(comboSimbolos.getSelectionModel().getSelectedItem().toString(), Color.web(strColor)).getNodo());
                    }
                }
                for (Chart chart : charts)
                {
                    LeyendaEje le = getLeyendaEje(chart.getId());
                    Label l = le.getLabel(serie.getName());
                    if (l != null && comboSimbolos.getSelectionModel().getSelectedItem() != null)
                    {
                        Simbolo simb = new Simbolo(comboSimbolos.getSelectionModel().getSelectedItem().toString(), Color.web(strColor));
                        l.setGraphic(simb.getRepresentacion());
                    }
                }
                return true;
            }
        }.Show();

    }

    void editarFondo()
    {
        String strColorFondo = fondo.getStyle().split(";")[0].replace("fondo: #", "0x");

        final ColorPicker colorPickerFondo = new ColorPicker(Color.web(strColorFondo));

        Node verticalGridLines = charts.get(0).lookup(".chart-vertical-grid-lines");
        Node horizontalGridLines = charts.get(0).lookup(".chart-horizontal-grid-lines");
        String strColorRejilla = verticalGridLines.getStyle().replace("-fx-stroke: ", "").replace("#", "0x").replace(";", "");
        final ColorPicker colorPickerRejilla = new ColorPicker(Color.web(strColorRejilla));

        String strColorMarco = grafica.getStyle().split(";")[0].replace("marco: #", "0x");
        final ColorPicker colorPickerMarco = new ColorPicker(Color.web(strColorMarco));

        Node axisLabel = charts.get(0).lookup(".axis-label");
        Node axis = charts.get(0).lookup(".axis");
        Node axisTickMark = charts.get(0).lookup(".axis-tick-mark");
        String strColorEje = axisLabel.getStyle().replace("-fx-text-fill: ", "").replace("#", "0x").replace(";", "");
        final ColorPicker colorPickerEjes = new ColorPicker(Color.web(strColorEje));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Color de fondo"), 0, 0);
        grid.add(colorPickerFondo, 1, 0);
        grid.add(new Label("Color de rejilla"), 0, 1);
        grid.add(colorPickerRejilla, 1, 1);
        grid.add(new Label("Color de marco"), 0, 2);
        grid.add(colorPickerMarco, 1, 2);
        grid.add(new Label("Color de ejes"), 0, 3);
        grid.add(colorPickerEjes, 1, 3);

        new PseudoModalDialog(padre, grid,true)
        {

            @Override
            public boolean Validacion()
            {
                fondo.setStyle("fondo: " + colorPickerFondo.getValue().toString().replace("0x", "#") + ";-fx-fill: linear-gradient(to bottom, derive(fondo, 40%), fondo);");
                marcoGrafica.setStyle("fondo: " + colorPickerFondo.getValue().toString().replace("0x", "#") + ";-fx-fill: linear-gradient(to bottom, derive(fondo, 40%), fondo);");
                grafica.setStyle("marco: " + colorPickerMarco.getValue().toString().replace("0x", "#") + ";-fx-background-color: linear-gradient(to bottom, derive(marco, 40%), marco);");
                leyenda.setStyle("marco: " + colorPickerMarco.getValue().toString().replace("0x", "#") + ";-fx-background-color: marco;");

                for (Node n : leyenda.getChildren())
                {
                    if (n instanceof LeyendaEje)
                    {
                        LeyendaEje le = (LeyendaEje) n;
                        for (Node nn : le.getChildren())
                        {
                            if (nn instanceof Label)
                            {
                                ((Label) nn).setStyle("fondo: " + colorPickerFondo.getValue().toString().replace("0x", "#") + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;");
                            }
                        }
                    }
                }

                for (Chart chart : charts)
                {
                    Node verticalGridLines = chart.lookup(".chart-vertical-grid-lines");
                    Node horizontalGridLines = chart.lookup(".chart-horizontal-grid-lines");
                    verticalGridLines.setStyle("-fx-stroke: " + colorPickerRejilla.getValue().toString().replace("0x", "#") + ";");
                    horizontalGridLines.setStyle("-fx-stroke: " + colorPickerRejilla.getValue().toString().replace("0x", "#") + ";");

                    for (Node n : chart.lookupAll(".axis-label"))
                    {
                        n.setStyle("-fx-text-fill: " + colorPickerEjes.getValue().toString().replace("0x", "#") + ";");
                    }
                    for (Node n : chart.lookupAll(".axis"))
                    {

                        String estilo = "eje: " + colorPickerEjes.getValue().toString().replace("0x", "#") + ";"
                                + "-fx-tick-label-fill: eje;"
                                + "-fx-stroke-width: 2;";

                        if (((Axis) n).getSide() == Side.LEFT)
                        {
                            estilo += "-fx-border-color: transparent eje transparent transparent;-fx-border-width:2;";
                        }
                        else if (((Axis) n).getSide() == Side.BOTTOM)
                        {
                            estilo += "-fx-border-color: eje transparent transparent transparent;-fx-border-width:2;";
                        }
                        n.setStyle(estilo);
                    }
                    for (Node n : chart.lookupAll(".axis-tick-mark"))
                    {
                        n.setStyle("-fx-fill: null; -fx-stroke: " + colorPickerEjes.getValue().toString().replace("0x", "#") + ";");
                    }
                    for (Node n : chart.lookupAll(".axis-minor-tick-mark"))
                    {
                        n.setStyle("-fx-fill: null; -fx-stroke: " + colorPickerEjes.getValue().toString().replace("0x", "#") + ";");
                    }
                }
                return true;
            }
        }.Show();

    }

    void ajustarRangoOrdenada(final NumberAxis eje, String nombre)
    {
        eje.autoRangingProperty().set(false);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        final TextField tfMax;
        final TextField tfMin;
        final TextField tfTick;
        grid.add(new Label("Eje"), 0, 0);
        grid.add(new Label(nombre), 1, 0);
        grid.add(new Label("Inferior"), 0, 1);
        grid.add(tfMin = new TextField(), 1, 1);
        grid.add(new Label("Superior"), 0, 2);
        grid.add(tfMax = new TextField(), 1, 2);
        grid.add(new Label("Espacio"), 0, 3);
        grid.add(tfTick = new TextField(), 1, 3);

        tfMin.setText(String.valueOf(eje.getLowerBound()));
        tfMax.setText(String.valueOf(eje.getUpperBound()));
        tfTick.setText(String.valueOf(eje.getTickUnit()));

        new PseudoModalDialog(padre, grid,true)
        {

            @Override
            public boolean Validacion()
            {
                eje.setLowerBound(Double.valueOf(tfMin.getText()));
                eje.setUpperBound(Double.valueOf(tfMax.getText()));
                eje.setTickUnit(Double.valueOf(tfTick.getText()));
                return true;
            }
        }.Show();

    }

    private NumberAxis createYaxis()
    {
        final NumberAxis axis = new NumberAxis(0, 1, 1);

        axis.setPrefWidth(anchoEje);
        axis.setMinorTickCount(10);

        axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis)
        {
            @Override
            public String toString(Number object)
            {
                return String.format("%7.2f", object.floatValue());
            }
        });

        return axis;
    }

    private void setDefaultChartProperties(final LineChart<Number, Number> chart)
    {
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        //chart.setCreateSymbols(true);
        //chart.setMaxWidth(Control.USE_PREF_SIZE);
    }

    private void configureOverlayChart(final LineChart<Number, Number> chart)
    {
        chart.setAlternativeRowFillVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setHorizontalGridLinesVisible(true);
        chart.setVerticalGridLinesVisible(true);
        chart.getXAxis().setVisible(false);
        chart.getYAxis().setVisible(false);

        chart.getStylesheets().addAll(Grafica.class
                .getResource("overlay-chart.css").toExternalForm());
    }

    public final void exportar(File file)
    {
        class ValorY
        {

            private int posicion;
            private double valor;

            public ValorY(double valor, int posicion)
            {
                this.valor = valor;
                this.posicion = posicion;
            }

            /**
             * @return the posicion
             */
            public int getPosicion()
            {
                return posicion;
            }

            /**
             * @param posicion the posicion to set
             */
            public void setPosicion(int posicion)
            {
                this.posicion = posicion;
            }

            /**
             * @return the valor
             */
            public double getValor()
            {
                return valor;
            }

            /**
             * @param valor the valor to set
             */
            public void setValor(double valor)
            {
                this.valor = valor;
            }
        }

        class Muestra
        {

            private double x;
            List<ValorY> y;

            public Muestra(double x)
            {
                this.x = x;
                y = new ArrayList<>();
            }

            public double getValor(int posicion)
            {
                for (int i = 0; i < y.size(); i++)
                {
                    if (y.get(i).getPosicion() == posicion)
                    {
                        return y.get(i).getValor();
                    }
                }
                return 0;
            }

            public void setValor(int posicion, double valor)
            {
                for (int i = 0; i < y.size(); i++)
                {
                    if (y.get(i).getPosicion() == posicion)
                    {
                        y.get(i).setValor(valor);
                        return;
                    }
                }
                y.add(new ValorY(valor, posicion));
            }

            /**
             * @return the x
             */
            public double getX()
            {
                return x;
            }

            /**
             * @param x the x to set
             */
            public void setX(double x)
            {
                this.x = x;
            }

        }

        class ListaMuestras
        {

            List<Muestra> muestras = new ArrayList<>();
            int posiciones = 0;

            public void setMuestra(double x, double y, int posicion)
            {
                if (posiciones < posicion + 1)
                {
                    posiciones = posicion + 1;
                }
                for (Muestra muestra : muestras)
                {
                    if (muestra.getX() == x)
                    {
                        muestra.setValor(posicion, y);
                        return;
                    }
                }
                Muestra m = new Muestra(x);
                m.setValor(posicion, y);
                if (muestras.isEmpty())
                {
                    muestras.add(m);
                }
                else
                {
                    for (int i = 0; i < muestras.size(); i++)
                    {
                        if (((Muestra) muestras.get(i)).getX() > x)
                        {
                            muestras.add(i, m);
                            return;
                        }
                    }
                    muestras.add(m);
                }
            }

            public int getSize()
            {
                return muestras.size();
            }

            public int getPosiciones()
            {
                return posiciones;
            }

            public double[] getMuestra(double x)
            {
                for (int i = 0; i < muestras.size(); i++)
                {
                    if (((Muestra) muestras.get(i)).getX() == x)
                    {
                        return getMuestra(i);
                    }
                }
                return null;
            }

            public double[] getMuestra(int i)
            {
                double[] m = new double[posiciones + 1];

                Muestra muestra = muestras.get(i);
                m[0] = muestra.getX();
                for (int j = 0; j < posiciones; j++)
                {
                    m[j + 1] = muestra.getValor(j);
                }

                return m;
            }

        }

        int posSerie = 0;
        ListaMuestras muestras = new ListaMuestras();

        String cabecera = "";
        for (int i = 0; i < charts.size(); i++)
        {
            LineChart chart = (LineChart) charts.get(i);
            for (int j = 0; j < chart.getData().size(); j++)
            {
                LineChart.Series serie = (LineChart.Series) chart.getData().get(j);
                cabecera += serie.getName() + ";";
                for (int k = 0; k < serie.getData().size(); k++)
                {
                    LineChart.Data dato = (LineChart.Data) serie.getData().get(k);
                    muestras.setMuestra(((Number) dato.getXValue()).doubleValue(), ((Number) dato.getYValue()).doubleValue(), posSerie);
                }
                posSerie++;
            }
        }
        try
        {
            try
            (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO8859_1")))) {
                pw.println(cabecera);
                for (int i = 0; i < muestras.getSize(); i++)
                {
                    String linea = "";
                    double[] ms = muestras.getMuestra(i);
                    for (double m : ms)
                    {
                        linea += String.format("%7.2f;", m).replace(".", ",");
                    }
                    pw.println(linea);
                }
            }
        }
        catch (UnsupportedEncodingException | FileNotFoundException ex)
        {
            Global.info.Registra(ex);
        }

    }

    abstract public double getAltoAbcisa();

    abstract public String FormatoAbcisa(double v);

    abstract public double FormatoAbcisa(String str);
    
    abstract public double getTickUnit(String text);
    
    abstract public String getTickUnit(double tu);            

    @Override
    public final void addCategoria(String nombre)
    {
        for (Chart c : charts)
        {
            if (c.getId().equals(nombre))
            {
                return;
            }
        }

        NumberAxis ordenada = createYaxis();
        ordenada.translateXProperty().set(-charts.size() * anchoEje);
        ordenada.boundsInLocalProperty().addListener(panelAnotacion);
        ordenada.autoRangingProperty().set(true);
        ordenada.forceZeroInRangeProperty().set(false);

        NumberAxis abcisa = new NumberAxis();
        abcisa.tickLabelRotationProperty().set(270);
        abcisa.autoRangingProperty().set(true);
        abcisa.forceZeroInRangeProperty().set(false);
        double altoAbcisa = getAltoAbcisa();
        if (altoAbcisa > 0)
        {
            abcisa.setPrefHeight(altoAbcisa);
            abcisa.setMaxHeight(altoAbcisa);
            abcisa.setMinHeight(altoAbcisa);
        }

        abcisa.setTickLabelFormatter(new NumberAxis.DefaultFormatter(abcisa)
        {
            @Override
            public String toString(Number object)
            {
                return FormatoAbcisa(object.floatValue());
            }
        });

        final LineChart<Number, Number> chart = new LineChart(abcisa, ordenada);
        chart.setId(nombre);
        setDefaultChartProperties(chart);
        chart.setCreateSymbols(false);
        charts.add(chart);
        configureOverlayChart(chart);
        chart.needsLayoutProperty().addListener(panelAnotacion);

        leyenda.getChildren().add(new LeyendaEje(nombre));
        Node verticalGridLines = chart.lookup(".chart-vertical-grid-lines");
        Node horizontalGridLines = chart.lookup(".chart-horizontal-grid-lines");
        verticalGridLines.setStyle("-fx-stroke: white;");
        horizontalGridLines.setStyle("-fx-stroke: white;");

        for (Node n : chart.lookupAll(".axis-label"))
        {
            n.setStyle("-fx-text-fill: white;");
        }
        for (Node n : chart.lookupAll(".axis"))
        {
            String estilo = "eje: white;-fx-tick-label-fill: eje;-fx-stroke-width: 2;";

            if (((Axis) n).getSide() == Side.LEFT)
            {
                estilo += "-fx-border-color: transparent eje transparent transparent;-fx-border-width:2;";
            }
            else if (((Axis) n).getSide() == Side.BOTTOM)
            {
                estilo += "-fx-border-color: eje transparent transparent transparent;-fx-border-width:2;";
            }
            n.setStyle(estilo);
        }
        for (Node n : chart.lookupAll(".axis-tick-mark"))
        {
            n.setStyle("-fx-fill: null; -fx-stroke: white;");
        }
        for (Node n : chart.lookupAll(".axis-minor-tick-mark"))
        {
            n.setStyle("-fx-fill: null; -fx-stroke: white;");
        }

        final Node chartPlotArea = chart.lookup(".chart-plot-background");

        chartPlotArea.boundsInParentProperty().addListener((ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) ->
        {
            Bounds chartAreaBounds = chart.localToParent(t1);
            
            double yShift = chartAreaBounds.getMinY();
            double xShift = chartAreaBounds.getMinX();
            Point2D p = grafica.localToParent(xShift, yShift);
            
            //fondo.setLayoutX((int) (chartAreaBounds.getMinX() + 0.5) + chart.getPadding().getLeft());
            //fondo.setLayoutY((int) (chartAreaBounds.getMinY() + 0.5) + chart.getPadding().getTop());
            fondo.setTranslateX((int) (chartAreaBounds.getMinX() + 0.5) + chart.getPadding().getLeft());
            fondo.setTranslateY((int) (chartAreaBounds.getMinY() + 0.5) + chart.getPadding().getTop());
            
            fondo.setWidth(chartAreaBounds.getWidth());
            fondo.setHeight(chartAreaBounds.getHeight());
            
            panelAnotacion.update();
        });

        grafica.getChildren().add(chart);
        grafica.getChildren().remove(panelAnotacion);
        grafica.getChildren().add(panelAnotacion);     
    }

    @SuppressWarnings("empty-statement")
    @Override
    public final void addSerie(String categoria, SimpleConfigSerie serie)
    {
        for (int i = 0; i < charts.size(); i++)
        {
            if (charts.get(i).getId().equals(categoria))
            {
                if (getSerie(serie.getNombre()) == null)
                {
                    final LineChart.Series s = new LineChart.Series();
                    s.setName(serie.toString());
                    s.setData(FXCollections.observableArrayList());
                    ((LineChart) charts.get(i)).getData().add(s);
                    String strColor;
                    if (serie.getColor() == null)
                    {
                        strColor = getColor(numeroSeries).toString().replace("0x", "#");
                    }
                    else
                    {
                        strColor = serie.getColor().toString();
                    }
                    s.getNode().setStyle("-fx-stroke: " + strColor
                            + ";-fx-stroke-width: " + serie.getGrosor() + ";"
                            + "simbolo: " + serie.getSimbolo() + ";");
                    panelAnotacion.addPosicionCursor(0, 0, (LineChart) charts.get(i), s);
                    LeyendaEje le = getLeyendaEje(categoria);
                    final Label label = new Label(serie.toString());
                    
                    label.setOnMouseClicked((MouseEvent t) ->
                    {
                        editarSerie(getSerie(label.getText()));
                    });
                    label.setOnMouseExited((MouseEvent t) ->
                    {
                        label.setStyle(label.getStyle().replace("-fx-background-color: blue", "-fx-background-color: fondo"));
                    });
                    label.setOnMouseEntered((MouseEvent t) ->
                    {
                        label.setStyle(label.getStyle().replace("-fx-background-color: fondo", "-fx-background-color: blue"));
                    });
                    label.setStyle("fondo: #226262;-fx-text-fill: white;-fx-background-color: fondo;-fx-padding:5px;-fx-background-radius: 5;");
                    le.getChildren().add(label);
                    Simbolo simb = new Simbolo(serie.getSimbolo(), Color.web(strColor.replace("#", "0x")));
                    label.setGraphic(simb.getRepresentacion());

                    numeroSeries++;
                }

            }
        }
    }

    @Override
    public void addMuestra(double x, double[] y)
    {
        if (y.length == numeroSeries)
        {
            int indice = 0;
            for (int i = 0; i < charts.size(); i++)
            {
                for (int j = 0; j < ((LineChart) charts.get(i)).getData().size(); j++)
                {
                    String[] estilo = ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getNode().getStyle().split(";");
                    String strColor = "black";
                    String strSimbolo = "nulo";
                    for (String e : estilo)
                    {
                        if (e.contains("-fx-stroke:"))
                        {
                            strColor = e.replace("-fx-stroke: #", "0x");
                        }
                        else if (e.contains("simbolo:"))
                        {
                            strSimbolo = e.replace("simbolo: ", "");
                        }
                    }

                    LineChart.Data data;
                    data = new LineChart.Data(x, y[indice]);
                    Label label = new Label();
                    label.setPadding(Insets.EMPTY);
                    label.setBorder(Border.EMPTY);
                    label.setStyle("-fx-background-color: transparent;");
                    //label.setGraphic(new Simbolo(strSimbolo, Color.web(strColor)).getNodo());                    
                    data.setNode(label);
                    ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().add(data);

                    indice++;
                }
            }
        }

    }

    @Override
    public void addMuestra(double x, double y, String nombreSerie)
    {
        LineChart.Series serie = getSerie(nombreSerie);
        if (serie != null)
        {
            String[] estilo = serie.getNode().getStyle().split(";");
            String strColor = "black";
            String strSimbolo = "nulo";
            for (String e : estilo)
            {
                if (e.contains("-fx-stroke:"))
                {
                    strColor = e.replace("-fx-stroke: #", "0x");
                }
                else if (e.contains("simbolo:"))
                {
                    strSimbolo = e.replace("simbolo: ", "");
                }
            }

            LineChart.Data data;
            data = new LineChart.Data(x, y);
            Label label = new Label();
            label.setPadding(Insets.EMPTY);
            label.setBorder(Border.EMPTY);
            label.setStyle("-fx-background-color: transparent;");
            //label.setGraphic(new Simbolo(strSimbolo, Color.web(strColor)).getNodo());      
            data.setNode(label);
            serie.getData().add(data);
        }
    }

    public Color getColor(int i)
    {
        switch (i)
        {
            case 0:
                return Color.AQUA;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.DARKORCHID;
            case 3:
                return Color.BROWN;
            case 4:
                return Color.CADETBLUE;
            case 5:
                return Color.CHARTREUSE;
            case 6:
                return Color.CORAL;
            case 7:
                return Color.CRIMSON;
            case 8:
                return Color.DARKBLUE;
            case 9:
                return Color.DARKGREEN;
            case 10:
                return Color.DARKORANGE;
            case 11:
                return Color.DEEPSKYBLUE;
            case 12:
                return Color.FORESTGREEN;
            case 13:
                return Color.FUCHSIA;
            case 14:
                return Color.GOLD;
            case 15:
                return Color.LAWNGREEN;
            default:
                return new Color(random(), random(), random(), 1);
        }
    }

    final Point2D getValueForDisplay(Point2D p, LineChart chart)
    {
        Point2D pC = chart.parentToLocal(p.getX(), p.getY());
        Point2D pCX = chart.getXAxis().parentToLocal(pC.getX(), 0);
        Point2D pCY = chart.getYAxis().parentToLocal(0, pC.getY());

        double x = pCX.getX();
        double y = pCY.getY();

        return new Point2D(Double.valueOf(chart.getXAxis().getValueForDisplay(x).toString()), Double.valueOf(chart.getYAxis().getValueForDisplay(y).toString()));
    }

    public final void print(final Node node)
    {
        WritableImage captura = node.snapshot(new SnapshotParameters(), null);
        /*File file = new File("chart.png");
         try
         {
         ImageIO.write(SwingFXUtils.fromFXImage(captura, null), "png", file);
         }
         catch (IOException e)
         {
         // TODO: handle exception here
         }*/

        ImageView iv = new ImageView(captura);
        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob();
        Window w = null;
        try
        {
            w = padre.getScene().getWindow();
        }
        catch (NullPointerException ex)
        {

        }
        if (job != null && job.showPageSetupDialog(w))
        {
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = pageLayout.getPrintableWidth() / iv.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / iv.getBoundsInParent().getHeight();
            iv.getTransforms().add(new Scale(scaleX, scaleY));
            if (job.showPrintDialog(w))
            {
                boolean success = job.printPage(iv);
                if (success)
                {
                    job.endJob();
                }
            }
        }

    }

    public final void copyClipboard(final Node node)
    {
        WritableImage captura = node.snapshot(new SnapshotParameters(), null);

        ImageView iv = new ImageView(captura);

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.put(DataFormat.IMAGE, captura);
        clipboard.setContent(content);

    }

    @Override
    public void fijaAbcisa(double desde, double hasta)
    {
        for (int j = 0; j < charts.size(); j++)
        {
            ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).autoRangingProperty().set(false);
            ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setLowerBound(desde);
            ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setUpperBound(hasta);
        }
    }

    public Grafica(final Esqueleto padre, List<CategoriaGrafica> categorias)
    {
        this.categorias = categorias;
        this.padre = padre;
        marcoGrafica = new VBox(0);

        MenuItem mi;
        mi = new MenuItem("Imprimir");
        mi.setOnAction((ActionEvent t) ->
        {
            print(marcoGrafica);
        });
        listaMenuContextual.add(mi);

        mi = new MenuItem("Copiar al portapapeles");
        mi.setOnAction((ActionEvent t) ->
        {
            copyClipboard(marcoGrafica);
        });
        listaMenuContextual.add(mi);

        mi = new MenuItem("Exportar valores");
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
        listaMenuContextual.add(mi);

        CheckMenuItem cmi = new CheckMenuItem("Valores visibles");
        cmi.setSelected(panelAnotacion.getVer());
        cmi.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue ov,
                    Boolean old_val, Boolean new_val)
            {
                panelAnotacion.setVer(new_val);
            }
        });
        listaMenuContextual.add(cmi);

        marcoGrafica.setOnContextMenuRequested((ContextMenuEvent t) ->
        {
            menu = new ContextMenu();
            menu.getItems().addAll(listaMenuContextual);
            menu.show(marcoGrafica, t.getScreenX(), t.getScreenY());
        });
        leyenda = new VBox(5);
        Label l;
        grafica = new StackPane();
        marcoGrafica.getChildren().addAll(grafica, leyenda);
        VBox.setVgrow(grafica, Priority.ALWAYS);
        VBox.setVgrow(leyenda, Priority.NEVER);

        panelAnotacion = new PanelAnotacion()
        {

            @Override
            String FormatoAnotacion(double x, double y)
            {
                return "Y:" + String.format("%7.2f", y) + "\n" + "X:" + String.format("%7.2f", x);
            }
        };
        fondo = new Rectangle();

        grafica.getChildren().addAll(fondo, panelAnotacion);
        marcoGrafica.visibleProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) ->
        {
            if (!t1)
            {
                for (int i = 0; i < charts.size(); i++)
                {
                    LineChart chart = (LineChart) charts.get(i);
                    for (int j = 0; j < chart.getData().size(); j++)
                    {
                        LineChart.Series serie = (LineChart.Series) chart.getData().get(j);
                        serie.getData().clear();
                    }
                    chart.getData().clear();
                }
            }
        });

        marcoGrafica.getStylesheets().addAll(Grafica.class
                .getResource("overlay-chart.css").toExternalForm());
        grafica.setStyle(
                "marco: #343434;-fx-background-color: linear-gradient(to bottom, derive(marco, 40%), marco);");
        leyenda.setStyle(
                "marco: #343434;-fx-background-color: marco;");
        marcoGrafica.setStyle(
                "marco: #343434;-fx-background-color: marco;");
        fondo.setStyle(
                "fondo: #226262;-fx-fill: linear-gradient(to bottom, derive(fondo, 40%), fondo);");
        grafica.setAlignment(Pos.TOP_LEFT);

        grafica.layoutBoundsProperty().addListener((ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) ->
        {
            for (Chart chart : charts)
            {
                for (int i = 0; i < ((LineChart) chart).getData().size(); i++)
                {
                    LineChart.Series serie = (LineChart.Series) ((LineChart) chart).getData().get(i);
                    String[] estilo = serie.getNode().getStyle().split(";");
                    
                    String strSimbolo = "nulo";
                    String strColor = "black";
                    for (String e : estilo)
                    {
                        if (e.contains("-fx-stroke:"))
                        {
                            strColor = e.replace("-fx-stroke: #", "0x");
                        }
                        else if (e.contains("simbolo:"))
                        {
                            strSimbolo = e.replace("simbolo: ", "");
                        }
                    }
                    
                    for (int j = 0; j < serie.getData().size(); j++)
                    {
                        LineChart.Data dato = (LineChart.Data) serie.getData().get(j);
                        
                        ((Label) dato.getNode()).setGraphic(new Simbolo(strSimbolo, Color.web(strColor)).getNodo());
                    }
                }
            }
        });

        grafica.widthProperty()
                .addListener((ObservableValue<? extends Number> ov, Number t, Number t1) ->
        {
            double a = 10;
            for (int i = 0; i < grafica.getChildren().size(); i++)
            {
                if (grafica.getChildren().get(i) instanceof Chart)
                {
                    ((Chart) grafica.getChildren().get(i)).prefWidthProperty().set(t1.doubleValue() - (charts.size() - 1) * anchoEje);
                    ((Chart) grafica.getChildren().get(i)).maxWidthProperty().set(t1.doubleValue() - (charts.size() - 1) * anchoEje);
                    ((Chart) grafica.getChildren().get(i)).minWidthProperty().set(t1.doubleValue() - (charts.size() - 1) * anchoEje);
                }
            }
        });

        for (int i = 0; i < categorias.size(); i++)
        {
            addCategoria(categorias.get(i).getNombre());
            for (int j = 0; j < categorias.get(i).listaConfigSerie.size(); j++)
            {
                addSerie(categorias.get(i).getNombre(), categorias.get(i).listaConfigSerie.get(j));
            }
        }

        grafica.setOnMouseClicked(
                new javafx.event.EventHandler<MouseEvent>()
                {

                    @Override
                    public void handle(MouseEvent t
                    )
                    {
                        if (t.getButton() == MouseButton.PRIMARY)
                        {
                            boolean editarFondo = false;
                            for (int i = 0; i < charts.size(); i++)
                            {
                                NumberAxis ordenada = (NumberAxis) ((LineChart) charts.get(i)).getYAxis();

                                final NumberAxis abcisa = (NumberAxis) ((LineChart) charts.get(i)).getXAxis();

                                double sx = t.getSceneX();
                                double sy = t.getSceneY();

                                Point2D valorPunteroRaton = getValueForDisplay(new Point2D(t.getSceneX(), t.getSceneY()), (LineChart) charts.get(i));

                                final Node chartPlotArea = charts.get(i).lookup(".chart-plot-background");
                                if (chartPlotArea.localToScene(chartPlotArea.getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                                {
                                    editarFondo = true;
                                    for (int j = 0; j < ((LineChart) charts.get(i)).getData().size(); j++)
                                    {
                                        double superior = ((NumberAxis) ((LineChart) charts.get(i)).getYAxis()).getUpperBound();
                                        double inferior = ((NumberAxis) ((LineChart) charts.get(i)).getYAxis()).getLowerBound();
                                        for (int k = 1; k < ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().size(); k++)
                                        {

                                            LineChart.Data n1 = (LineChart.Data) ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().get(k - 1);
                                            LineChart.Data n2 = (LineChart.Data) ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().get(k);

                                            if (((Number) n1.getXValue()).doubleValue() <= valorPunteroRaton.getX()
                                            && ((Number) n2.getXValue()).doubleValue() >= valorPunteroRaton.getX())
                                            {
                                                double px = valorPunteroRaton.getX();
                                                double py = -(((Number) n2.getYValue()).doubleValue() - ((Number) n1.getYValue()).doubleValue()) * (((Number) n2.getXValue()).doubleValue() - valorPunteroRaton.getX()) / (((Number) n2.getXValue()).doubleValue() - ((Number) n1.getXValue()).doubleValue()) + ((Number) n2.getYValue()).doubleValue();

                                                if (java.lang.Math.abs((py - valorPunteroRaton.getY()) / (superior - inferior)) < 0.05)
                                                {
                                                    //Editar serie
                                                    LineChart.Series serie = (LineChart.Series) ((LineChart) charts.get(i)).getData().get(j);
                                                    editarSerie(serie);
                                                    return;
                                                }
                                            }
                                        }
                                    }

                                }

                                if (ordenada.localToScene(ordenada.getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                                {
                                    ajustarRangoOrdenada(ordenada, charts.get(i).getId());
                                }
                                if (abcisa.localToScene(abcisa.getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                                {
                                    GridPane grid = new GridPane();
                                    grid.setHgap(10);
                                    grid.setVgap(10);
                                    grid.setPadding(new Insets(0, 10, 0, 10));
                                    final TextField tfMax;
                                    final TextField tfMin;
                                    final TextField tfTick;
                                    final TextField tfFormatoTiempo = new TextField();
                                    grid.add(new Label("Eje"), 0, 0);
                                    grid.add(new Label("Abcisa"), 1, 0);
                                    grid.add(new Label("Inferior"), 0, 1);
                                    grid.add(tfMin = new TextField(), 1, 1);
                                    grid.add(new Label("Superior"), 0, 2);
                                    grid.add(tfMax = new TextField(), 1, 2);
                                    grid.add(new Label("Espacio"), 0, 3);
                                    grid.add(tfTick = new TextField(), 1, 3);

                                    tfMin.setText(String.valueOf(FormatoAbcisa(abcisa.getLowerBound())));
                                    tfMax.setText(String.valueOf(FormatoAbcisa(abcisa.getUpperBound())));
                                    tfTick.setText(getTickUnit(abcisa.getTickUnit()));
                                    
                                    new PseudoModalDialog(padre, grid,true)
                                    {

                                        @Override
                                        public boolean Validacion()
                                        {
                                            double altoAbcisa = getAltoAbcisa();

                                            for (int j = 0; j < charts.size(); j++)
                                            {
                                                if (altoAbcisa > 0)
                                                {
                                                    ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setPrefHeight(altoAbcisa);
                                                    ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setMaxHeight(altoAbcisa);
                                                    ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setMinHeight(altoAbcisa);
                                                }

                                                ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).autoRangingProperty().set(false);
                                                ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setTickLabelFormatter(new NumberAxis.DefaultFormatter(abcisa)
                                                        {
                                                            @Override
                                                            public String toString(Number object)
                                                            {
                                                                return FormatoAbcisa(object.floatValue());
                                                            }
                                                });

                                                ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setLowerBound(FormatoAbcisa(tfMin.getText()));
                                                ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setUpperBound(FormatoAbcisa(tfMax.getText()));
                                                ((NumberAxis) ((LineChart) charts.get(j)).getXAxis()).setTickUnit(getTickUnit(tfTick.getText()));
                                               
                                            }
                                            return true;
                                        }

                                        
                                        
                                    }.Show();

                                    return;
                                }

                            }
                            if (editarFondo)
                            {
                                editarFondo();
                            }
                        }
                    }
            
                }
        );

        grafica.setOnMouseMoved(
                (MouseEvent t) ->
        {
            for (int i = 0; i < charts.size(); i++)
            {
                final Node chartPlotArea = charts.get(0).lookup(".chart-plot-background");
                if (chartPlotArea.localToScene(chartPlotArea.getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                {
                    
                    //Point2D valorPunteroRaton = getValueForDisplay(new Point2D(t.getSceneX(), t.getSceneY()), (LineChart) charts.get(i));
                    Point2D valorPunteroRaton = getValueForDisplay(new Point2D(t.getX(), t.getY()), (LineChart) charts.get(i));
                    
                    for (int j = 0; j < ((LineChart) charts.get(i)).getData().size(); j++)
                    {
                        
                        for (int k = 1; k < ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().size(); k++)
                        {
                            
                            LineChart.Data n1 = (LineChart.Data) ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().get(k - 1);
                            LineChart.Data n2 = (LineChart.Data) ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getData().get(k);
                            
                            if (((Number) n1.getXValue()).doubleValue() <= valorPunteroRaton.getX()
                                    && ((Number) n2.getXValue()).doubleValue() >= valorPunteroRaton.getX())
                            {
                                double px = valorPunteroRaton.getX();
                                double py = -(((Number) n2.getYValue()).doubleValue() - ((Number) n1.getYValue()).doubleValue()) * (((Number) n2.getXValue()).doubleValue() - valorPunteroRaton.getX()) / (((Number) n2.getXValue()).doubleValue() - ((Number) n1.getXValue()).doubleValue()) + ((Number) n2.getYValue()).doubleValue();
                                //System.out.println(((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getName() + ":X=" + valorPunteroRaton.getX() + " Y=" + String.valueOf(py) + "\n");
                                
                                panelAnotacion.movePosicionCursor(px, py, ((LineChart.Series) ((LineChart) charts.get(i)).getData().get(j)).getName());
                                break;
                            }
                        }
                    }
                }
                
                for (Node n : charts.get(i).lookupAll(".axis"))
                {
                    if (((Axis) n).getSide() == Side.LEFT)
                    {
                        if (((NumberAxis) ((LineChart) charts.get(i)).getYAxis()).localToScene(((NumberAxis) ((LineChart) charts.get(i)).getYAxis()).getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                        {
                            n.setStyle(n.getStyle().replace("-fx-border-color: transparent eje transparent transparent", "-fx-border-color: blue"));
                        }
                        else
                        {
                            n.setStyle(n.getStyle().replace("-fx-border-color: blue", "-fx-border-color: transparent eje transparent transparent"));
                        }
                    }
                    else if (((Axis) n).getSide() == Side.BOTTOM)
                    {
                        if (((NumberAxis) ((LineChart) charts.get(i)).getXAxis()).localToScene(((NumberAxis) ((LineChart) charts.get(i)).getXAxis()).getBoundsInLocal()).contains(t.getSceneX(), t.getSceneY()))
                        {
                            n.setStyle(n.getStyle().replace("-fx-border-color: eje transparent transparent transparent", "-fx-border-color: blue"));
                        }
                        else
                        {
                            n.setStyle(n.getStyle().replace("-fx-border-color: blue", "-fx-border-color: eje transparent transparent transparent"));
                        }
                    }
                }
                
            }
        });
    }

    public Legend getLeyenda(Chart chart)
    {
        for (Node n : chart.getChildrenUnmodifiable())
        {
            if (n instanceof Legend)
            {
                return (Legend) n;

            }
        }
        return null;
    }

    @Override
    public Pane getGrafica()
    {
        double altura = 0;
        for (int i = 0; i < charts.size(); i++)
        {
            charts.get(i).translateXProperty().set((charts.size() - 1) * anchoEje);
            Legend l = getLeyenda((Chart) charts.get(i));
            l.setTranslateX(-(charts.size() - 1) * anchoEje);
        }
        return marcoGrafica;
    }
}
