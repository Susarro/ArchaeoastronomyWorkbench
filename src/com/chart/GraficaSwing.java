/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import com.Global;
import com.interfaz.esqueleto.Skeleton;
import com.interfaz.esqueleto.PseudoModalDialog;
import demo.CircleDrawer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.random;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.KeyStroke;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.SeriesException;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class GraficaSwing implements GraficaInterface
{

    List<CategoriaGrafica> categorias;
    Skeleton padre;
    String nombre;

    public JFreeChart chart;
    public ChartPanel chartPanel;
    public List<Serie> listaSerie = new ArrayList<>();
    List<XYSeriesCollection> listaDataset = new ArrayList<>();
    public List<NumberAxis> listaEjes = new ArrayList<>();

    public ValueAxis ejeAbcisas;
    XYPlot plot;
    public List<MenuItem> listaMenuContextual = new ArrayList<>();
    VBox marcoGrafica;
    VBox leyenda = new VBox(5);
    ContextMenu menu;

    public String strColorFondo = "#343434";
    public String strColorFondoGrafica = "#226262";
    public String strColorTick = "white";
    public String strColorRejilla = "white";
    public float tamañoFuente = 11;

    protected Format formatoAbcisa;
    protected final Format formatoOrdenada;

    protected ChartMouseListener cml;

    public void updateColoresGraficado(String[] cfg)
    {
        strColorFondo = cfg[0];
        for (Node le : leyenda.getChildren())
        {
            if (le instanceof LeyendaEje)
            {
                le.setStyle("-fx-background-color:" + strColorFondo);
                ((LeyendaEje) le).seleccionado = false;
            }
        }
        chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)))));
        chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));

        leyenda.setStyle("marco: " + strColorFondo + ";-fx-background-color: marco;");

        strColorFondoGrafica = cfg[1];;
        plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondoGrafica)));

        for (Node le : leyenda.getChildren())
        {
            if (le instanceof LeyendaEje)
            {
                le.setStyle("-fx-background-color:" + strColorFondo);
                ((LeyendaEje) le).seleccionado = false;
                for (Node nn : ((LeyendaEje) le).getChildren())
                {
                    if (nn instanceof Label)
                    {
                        ((Label) nn).setStyle("fondo: " + strColorFondoGrafica + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(tamañoFuente) + "px");
                    }
                }
            }
        }

        strColorRejilla = cfg[2];;
        plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));
        plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));

        strColorTick = cfg[3];;
        ejeAbcisas.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        ejeAbcisas.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        for (NumberAxis ejeOrdenada : listaEjes)
        {
            ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
            ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        }
    }

    public void setMaxItemCount(int max)
    {
        for (Serie serie : listaSerie)
        {
            serie.setMaximumItemCount(max);
        }
    }

    public void setFormatoAbcisa(Format formatoAbcisa)
    {
        this.formatoAbcisa = formatoAbcisa;
    }

    public class Serie extends XYSeries
    {

        private String estilo;
        private final int indiceCategoria;
        private final int indiceSerie;

        public Serie(Comparable key, String estilo, int indiceCategoria, int indiceSerie)
        {
            super(key);
            this.estilo = estilo;
            this.indiceCategoria = indiceCategoria;
            this.indiceSerie = indiceSerie;
        }

        /**
         * @return the estilo
         */
        public String getEstilo()
        {
            return estilo;
        }

        /**
         * @param estilo the estilo to set
         */
        public void setEstilo(String estilo)
        {
            this.estilo = estilo;
        }

        /**
         * @return the indiceCategoria
         */
        public int getIndiceCategoria()
        {
            return indiceCategoria;
        }

        /**
         * @return the indiceSerie
         */
        public int getIndiceSerie()
        {
            return indiceSerie;
        }

    }

    public javafx.scene.paint.Color getColor(int i)
    {
        switch (i)
        {
            case 0:
                return javafx.scene.paint.Color.AQUA;
            case 1:
                return javafx.scene.paint.Color.BLUE;
            case 2:
                return javafx.scene.paint.Color.DARKORCHID;
            case 3:
                return javafx.scene.paint.Color.BROWN;
            case 4:
                return javafx.scene.paint.Color.CADETBLUE;
            case 5:
                return javafx.scene.paint.Color.CHARTREUSE;
            case 6:
                return javafx.scene.paint.Color.CORAL;
            case 7:
                return javafx.scene.paint.Color.CRIMSON;
            case 8:
                return javafx.scene.paint.Color.DARKBLUE;
            case 9:
                return javafx.scene.paint.Color.DARKGREEN;
            case 10:
                return javafx.scene.paint.Color.DARKORANGE;
            case 11:
                return javafx.scene.paint.Color.DEEPSKYBLUE;
            case 12:
                return javafx.scene.paint.Color.FORESTGREEN;
            case 13:
                return javafx.scene.paint.Color.FUCHSIA;
            case 14:
                return javafx.scene.paint.Color.GOLD;
            case 15:
                return javafx.scene.paint.Color.LAWNGREEN;
            default:
                return new javafx.scene.paint.Color(random(), random(), random(), 1);
        }
    }

    final java.awt.Color scene2awtColor(javafx.scene.paint.Color color)
    {
        return new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
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
                    try
                    {
                        exportacion[e][n] = formatoOrdenada.format(s.getDataItem(e).getYValue());
                    }
                    catch (Exception ex)
                    {
                        exportacion[e][n] = "";
                    }
                    if (n == 1)
                    {

                        exportacion[e][0] = formatoAbcisa.format(s.getDataItem(e).getXValue());

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

    public final void copyClipboard(final Node node)
    {

        WritableImage captura = node.snapshot(new SnapshotParameters(), null);

        ImageView iv = new ImageView(captura);

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.put(DataFormat.IMAGE, captura);
        clipboard.setContent(content);

    }

    public GraficaSwing(String nombre, final Skeleton padre, List<CategoriaGrafica> categorias, String nombreAbcisa)
    {
        this.padre = padre;
        this.categorias = categorias;
        this.nombre = nombre;

        this.formatoAbcisa = NumberFormat.getInstance(Locale.getDefault());
        this.formatoOrdenada = NumberFormat.getInstance(Locale.getDefault());

        plot = new XYPlot();
        plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondoGrafica)));
        plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));
        plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

        ejeAbcisas = new NumberAxis(nombreAbcisa);
        ((NumberAxis) ejeAbcisas).setAutoRangeIncludesZero(false);
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

        plot.setDomainAxis(ejeAbcisas);

        for (int i = 0; i < categorias.size(); i++)
        {
            CategoriaGrafica categoria = categorias.get(i);
            addCategoria(categoria.getNombre());

            for (int j = 0; j < categoria.listaConfigSerie.size(); j++)
            {
                SimpleConfigSerie cs = categoria.listaConfigSerie.get(j);
                addSerie(categoria.getNombre(), cs);
            }
        }
        chart = new JFreeChart(
                "",
                new Font("SansSerif", Font.BOLD, 16),
                plot,
                false);

        chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)))));

        chartPanel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        chartPanel.getActionMap().put("escape", new AbstractAction()
        {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                for (int i = 0; i < plot.getDatasetCount(); i++)
                {
                    XYDataset test = plot.getDataset(i);
                    XYItemRenderer r = plot.getRenderer(i);
                    r.removeAnnotations();
                }
            }
        });

        chartPanel.addChartMouseListener(cml = new ChartMouseListener()
        {
            @Override
            public void chartMouseClicked(ChartMouseEvent event)
            {
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event)
            {
                try
                {
                    XYItemEntity xyitem = (XYItemEntity) event.getEntity(); // get clicked entity
                    XYDataset dataset = (XYDataset) xyitem.getDataset(); // get data set    
                    double x = dataset.getXValue(xyitem.getSeriesIndex(), xyitem.getItem());
                    double y = dataset.getYValue(xyitem.getSeriesIndex(), xyitem.getItem());

                    final XYPlot plot = chart.getXYPlot();
                    for (int i = 0; i < plot.getDatasetCount(); i++)
                    {
                        XYDataset test = plot.getDataset(i);
                        XYItemRenderer r = plot.getRenderer(i);
                        r.removeAnnotations();
                        if (test == dataset)
                        {
                            NumberAxis ejeOrdenada = listaEjes.get(i);
                            double y_max = ejeOrdenada.getUpperBound();
                            double y_min = ejeOrdenada.getLowerBound();
                            double x_max = ejeAbcisas.getUpperBound();
                            double x_min = ejeAbcisas.getLowerBound();
                            double angulo;
                            if (y > (y_max + y_min) / 2 && x > (x_max + x_min) / 2)
                            {
                                angulo = 3.0 * Math.PI / 4.0;
                            }
                            else if (y > (y_max + y_min) / 2 && x < (x_max + x_min) / 2)
                            {
                                angulo = 1.0 * Math.PI / 4.0;
                            }
                            else if (y < (y_max + y_min) / 2 && x < (x_max + x_min) / 2)
                            {
                                angulo = 7.0 * Math.PI / 4.0;
                            }
                            else
                            {
                                angulo = 5.0 * Math.PI / 4.0;
                            }

                            CircleDrawer cd = new CircleDrawer((Color) r.getSeriesPaint(xyitem.getSeriesIndex()), new BasicStroke(2.0f), null);
                            //XYAnnotation bestBid = new XYDrawableAnnotation(dataset.getXValue(xyitem.getSeriesIndex(), xyitem.getItem()), dataset.getYValue(xyitem.getSeriesIndex(), xyitem.getItem()), 11, 11, cd);
                            String txt = "X:" + formatoAbcisa.format(x) + ", Y:" + formatoOrdenada.format(y);
                            XYPointerAnnotation anotacion = new XYPointerAnnotation(txt, dataset.getXValue(xyitem.getSeriesIndex(), xyitem.getItem()), dataset.getYValue(xyitem.getSeriesIndex(), xyitem.getItem()), angulo);
                            anotacion.setTipRadius(10.0);
                            anotacion.setBaseRadius(35.0);
                            anotacion.setFont(new Font("SansSerif", Font.PLAIN, 10));

                            if (Long.parseLong((strColorFondoGrafica.replace("#", "")), 16) > 0xffffff / 2)
                            {
                                anotacion.setPaint(Color.black);
                                anotacion.setArrowPaint(Color.black);
                            }
                            else
                            {
                                anotacion.setPaint(Color.white);
                                anotacion.setArrowPaint(Color.white);
                            }

                            //bestBid.setPaint((Color) r.getSeriesPaint(xyitem.getSeriesIndex()));
                            r.addAnnotation(anotacion);
                        }
                    }

                    //LabelValorVariable.setSize(LabelValorVariable.getPreferredSize());
                }
                catch (NullPointerException | ClassCastException ex)
                {

                }
            }
        });

        chartPanel.setPopupMenu(null);
        chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));

        SwingNode sn = new SwingNode();
        sn.setContent(chartPanel);
        marcoGrafica = new VBox();
        marcoGrafica.getChildren().addAll(sn, leyenda);
        VBox.setVgrow(sn, Priority.ALWAYS);
        VBox.setVgrow(leyenda, Priority.NEVER);

        marcoGrafica.getStylesheets().addAll(Grafica.class
                .getResource("overlay-chart.css").toExternalForm());

        leyenda.setStyle(
                "marco: " + strColorFondo + ";-fx-background-color: marco;");

        MenuItem mi;
        mi = new MenuItem("Imprimir");
        mi.setOnAction((ActionEvent t) ->
        {
            print(marcoGrafica);
        });
        listaMenuContextual.add(mi);

        sn.setOnMouseClicked((MouseEvent t) ->
        {
            if (menu != null)
            {
                menu.hide();
            }
            if (t.getClickCount() == 2)
            {
                editarFondo();
            }
        });

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

        marcoGrafica.setOnContextMenuRequested((ContextMenuEvent t) ->
        {
            if (menu != null)
            {
                menu.hide();
            }
            menu = new ContextMenu();
            menu.getItems().addAll(listaMenuContextual);
            menu.show(marcoGrafica, t.getScreenX(), t.getScreenY());
        });

    }

    public final void print(final Node node)
    {
        LegendTitle legend = new LegendTitle(chart.getPlot());
        legend.setItemFont(legend.getItemFont().deriveFont(tamañoFuente));
        chart.addLegend(legend);
        legend.setPosition(RectangleEdge.BOTTOM);
        chart.setTitle(nombre);
        chartPanel.createChartPrintJob();
        chart.removeLegend();
        chart.setTitle("");
    }

    void editarSerie(final Serie serie)
    {
        String[] estilo = serie.getEstilo().split(";");
        String strColor = "black";
        final TextField editGrosor = new TextField();

        String tempS = "nulo";
        for (String e : estilo)
        {
            if (e.contains("color: "))
            {
                strColor = e.replace("color: ", "");
            }
            else if (e.contains("grosor: "))
            {
                editGrosor.setText(e.replace("grosor: ", ""));
            }
            else if (e.contains("símbolo: "))
            {
                tempS = e.replace("símbolo: ", "");
            }
        }
        final String simbolo = tempS;

        final List<Simbolo> listaSimbolos = new ArrayList<>();
        final ObservableList<Simbolo> modeloListaSimbolos;
        final ListView<Simbolo> comboSimbolos = new ListView();
        listaSimbolos.add(new Simbolo("nulo", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("rectángulo", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("círculo", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("triángulo", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("cruz", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("diamante", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("rectángulo hueco", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("círculo hueco", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("triángulo hueco", javafx.scene.paint.Color.web(strColor)));
        listaSimbolos.add(new Simbolo("diamante hueco", javafx.scene.paint.Color.web(strColor)));

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

        final ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(strColor));

        colorPicker.setOnAction((ActionEvent t) ->
        {
            String sc = colorPicker.getValue().toString();
            modeloListaSimbolos.clear();
            modeloListaSimbolos.add(new Simbolo("nulo", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("rectángulo", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("círculo", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("triángulo", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("cruz", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("diamante", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("rectángulo hueco", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("círculo hueco", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("triángulo hueco", javafx.scene.paint.Color.web(sc)));
            modeloListaSimbolos.add(new Simbolo("diamante hueco", javafx.scene.paint.Color.web(sc)));

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
        grid.add(new Label(serie.getKey().toString()), 1, 0);
        grid.add(new Label("Color"), 0, 1);
        grid.add(colorPicker, 1, 1);
        grid.add(new Label("Grosor"), 0, 2);
        grid.add(editGrosor, 1, 2);
        grid.add(new Label("Símbolo"), 0, 3);
        grid.add(comboSimbolos, 1, 3);

        new PseudoModalDialog(padre, grid, true)
        {
            @Override
            public boolean Validacion()
            {
                String strColor = colorPicker.getValue().toString();
                String strGrosor = editGrosor.getText();
                double iGrosor = Double.valueOf(strGrosor);
                String strSimbolo = "nulo";
                Simbolo simb = new Simbolo(comboSimbolos.getSelectionModel().getSelectedItem().toString(), javafx.scene.paint.Color.web(strColor));

                XYItemRenderer renderer = (XYItemRenderer) plot.getRenderer(serie.getIndiceCategoria());

                renderer.setSeriesPaint(serie.getIndiceSerie(), scene2awtColor(colorPicker.getValue()));

                try
                {
                    if (Double.valueOf(strGrosor) > 0)
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(serie.getIndiceSerie(), true);
                        renderer.setSeriesStroke(serie.getIndiceSerie(), new BasicStroke(Integer.valueOf(strGrosor)));
                    }
                    else
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(serie.getIndiceSerie(), false);
                    }
                }
                catch (NumberFormatException ex)
                {

                }

                if (simb.getNombre().contains("nulo"))
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(serie.getIndiceSerie(), false);
                    renderer.setSeriesShape(serie.getIndiceSerie(), null);
                }
                else
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(serie.getIndiceSerie(), true);
                    renderer.setSeriesShape(serie.getIndiceSerie(), simb.getShapeAWT());
                    if (simb.getNombre().contains("hueco"))
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(serie.getIndiceSerie(), false);
                    }
                    else
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(serie.getIndiceSerie(), true);
                    }

                }

                serie.setEstilo("color: " + strColor + ";grosor: " + editGrosor.getText() + ";símbolo: " + strSimbolo + ";");

                for (Node le : leyenda.getChildren())
                {
                    if (le instanceof LeyendaEje)
                    {
                        for (Node nn : ((LeyendaEje) le).getChildren())
                        {
                            if (nn instanceof Label)
                            {
                                if (((Label) nn).getText().equals(serie.getKey().toString()))
                                {
                                    ((Label) nn).setGraphic(simb.getRepresentacion());
                                }
                            }
                        }
                    }
                }

                /*for (Chart chart : charts)
                 {
                 LeyendaEje le = getLeyendaEje(chart.getId());
                 Label l = le.getLabel(serie.getName());
                 if (l != null && comboSimbolos.getSelectionModel().getSelectedItem() != null)
                 {
                 Simbolo simb = new Simbolo(comboSimbolos.getSelectionModel().getSelectedItem().toString(), Color.web(strColor));
                 l.setGraphic(simb.getRepresentacion());
                 }
                 }*/
                return true;
            }
        }.Show();

    }

    final public void editarFondo()
    {
        final ColorPicker colorPickerFondoGrafica = new ColorPicker(javafx.scene.paint.Color.web(strColorFondoGrafica));
        colorPickerFondoGrafica.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerRejilla = new ColorPicker(javafx.scene.paint.Color.web(strColorRejilla));
        colorPickerRejilla.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerFondo = new ColorPicker(javafx.scene.paint.Color.web(strColorFondo));
        colorPickerFondo.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerEjes = new ColorPicker(javafx.scene.paint.Color.web(strColorTick));
        colorPickerEjes.setMaxWidth(Double.MAX_VALUE);
        final TextField tfTamañoFuente = new TextField();
        tfTamañoFuente.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Color de fondo"), 0, 0);
        grid.add(colorPickerFondoGrafica, 1, 0);
        grid.add(new Label("Color de rejilla"), 0, 1);
        grid.add(colorPickerRejilla, 1, 1);
        grid.add(new Label("Color de marco"), 0, 2);
        grid.add(colorPickerFondo, 1, 2);
        grid.add(new Label("Color de ejes"), 0, 3);
        grid.add(colorPickerEjes, 1, 3);
        grid.add(new Label("Tamaño fuente"), 0, 4);
        grid.add(tfTamañoFuente, 1, 4);
        tfTamañoFuente.setText(String.valueOf(tamañoFuente));

        new PseudoModalDialog(padre, grid, true)
        {

            @Override
            public boolean Validacion()
            {
                tamañoFuente = Float.valueOf(tfTamañoFuente.getText().replace(",", "."));
                strColorFondo = colorPickerFondo.getValue().toString().replace("0x", "#");
                for (Node le : leyenda.getChildren())
                {
                    if (le instanceof LeyendaEje)
                    {
                        le.setStyle("-fx-background-color:" + strColorFondo);
                        ((LeyendaEje) le).seleccionado = false;
                    }
                }
                chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));
                chartPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(4, 4, 4, 4),
                        BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)))));
                chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strColorFondo)));

                leyenda.setStyle("marco: " + colorPickerFondo.getValue().toString().replace("0x", "#") + ";-fx-background-color: marco;");

                strColorFondoGrafica = colorPickerFondoGrafica.getValue().toString().replace("0x", "#");
                plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorFondoGrafica)));

                for (Node le : leyenda.getChildren())
                {
                    if (le instanceof LeyendaEje)
                    {
                        le.setStyle("-fx-background-color:" + strColorFondo);
                        ((LeyendaEje) le).seleccionado = false;
                        for (Node nn : ((LeyendaEje) le).getChildren())
                        {
                            if (nn instanceof Label)
                            {
                                ((Label) nn).setStyle("fondo: " + colorPickerFondoGrafica.getValue().toString().replace("0x", "#") + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(tamañoFuente) + "px");
                            }
                        }
                    }
                }

                strColorRejilla = colorPickerRejilla.getValue().toString().replace("0x", "#");
                plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));
                plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strColorRejilla)));

                strColorTick = colorPickerEjes.getValue().toString().replace("0x", "#");
                ejeAbcisas.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
                ejeAbcisas.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
                ejeAbcisas.setLabelFont(ejeAbcisas.getLabelFont().deriveFont(tamañoFuente));
                ejeAbcisas.setTickLabelFont(ejeAbcisas.getLabelFont().deriveFont(tamañoFuente));

                for (NumberAxis ejeOrdenada : listaEjes)
                {
                    ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
                    ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
                    ejeOrdenada.setLabelFont(ejeOrdenada.getLabelFont().deriveFont(tamañoFuente));
                    ejeOrdenada.setTickLabelFont(ejeOrdenada.getLabelFont().deriveFont(tamañoFuente));
                }
                return true;
            }
        }.Show();

    }

    void ajustarRangoOrdenada(final NumberAxis eje)
    {
        eje.setAutoRange(false);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        final TextField tfMax;
        final TextField tfMin;
        final TextField tfTick;
        final TextField tfFuente;
        grid.add(new Label("Eje"), 0, 0);
        grid.add(new Label(eje.getLabel()), 1, 0);
        grid.add(new Label("Inferior"), 0, 1);
        grid.add(tfMin = new TextField(), 1, 1);
        grid.add(new Label("Superior"), 0, 2);
        grid.add(tfMax = new TextField(), 1, 2);
        grid.add(new Label("Espacio"), 0, 3);
        grid.add(tfTick = new TextField(), 1, 3);

        tfMin.setText(String.valueOf(eje.getLowerBound()));
        tfMax.setText(String.valueOf(eje.getUpperBound()));
        tfTick.setText(String.valueOf(eje.getTickUnit().getSize()));

        new PseudoModalDialog(padre, grid, true)
        {
            @Override
            public boolean Validacion()
            {
                eje.setLowerBound(Double.valueOf(tfMin.getText()));
                eje.setUpperBound(Double.valueOf(tfMax.getText()));
                eje.setTickUnit(new NumberTickUnit(Double.valueOf(tfTick.getText())));
                return true;
            }
        }.Show();

    }

    public void ajustarRangoOrdenada(int i, double desde, double hasta, double tick)
    {
        NumberAxis e = listaEjes.get(i);
        e.setAutoRange(false);
        e.setLowerBound(desde);
        e.setUpperBound(hasta);
        e.setTickUnit(new NumberTickUnit(tick));

    }

    @Override
    public final void addCategoria(String nombre)
    {
        boolean encontrado = false;
        for (CategoriaGrafica categoria : categorias)
        {
            if (categoria.getNombre().equals(nombre))
            {
                encontrado = true;
                break;
            }
        }
        if (!encontrado)
        {
            categorias.add(new CategoriaGrafica((nombre)));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        NumberAxis ejeOrdenada = new NumberAxis(nombre);

        ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        ejeOrdenada.setLabelFont(ejeOrdenada.getLabelFont().deriveFont(tamañoFuente));
        ejeOrdenada.setTickLabelFont(ejeOrdenada.getLabelFont().deriveFont(tamañoFuente));

        int i = listaDataset.size();

        listaDataset.add(dataset);
        listaEjes.add(ejeOrdenada);
        plot.setDataset(i, dataset);
        plot.setRangeAxis(i, ejeOrdenada);
        plot.setRangeAxisLocation(i, AxisLocation.BOTTOM_OR_LEFT);
        plot.mapDatasetToRangeAxis(i, i);
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, true);
        if (i == 0)
        {
            plot.setRenderer(renderer);
        }
        else
        {
            plot.setRenderer(i, renderer);
        }

        final LeyendaEje le;
        final int indiceLeyenda = leyenda.getChildren().size();

        leyenda.getChildren().add(le = new LeyendaEje(nombre));

        le.setOnMouseClicked((MouseEvent t) ->
        {
            if (le.seleccionado && t.getClickCount() == 2)
            {
                ajustarRangoOrdenada(listaEjes.get(indiceLeyenda));
            }
        });

        le.setOnMouseEntered((MouseEvent t) ->
        {
            le.setStyle("-fx-background-color:blue");
            le.seleccionado = true;
            listaEjes.get(indiceLeyenda).setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web("blue")));
            listaEjes.get(indiceLeyenda).setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web("blue")));
        });

        le.setOnMouseExited((MouseEvent t) ->
        {
            le.setStyle("-fx-background-color:" + strColorFondo);
            le.seleccionado = false;
            listaEjes.get(indiceLeyenda).setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
            listaEjes.get(indiceLeyenda).setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strColorTick)));
        });
    }

    @Override
    public final void addSerie(String categoria, SimpleConfigSerie cs)
    {
        for (int i = 0; i < categorias.size(); i++)
        {
            if (categorias.get(i).getNombre().equals(categoria))
            {
                String strColor;
                javafx.scene.paint.Color color;
                int indice = listaSerie.size();
                if (cs.getColor() == null)
                {
                    color = getColor(indice);
                }
                else
                {
                    color = cs.getColor();
                }
                strColor = color.toString();

                XYSeriesCollection dataset = listaDataset.get(i);
                Serie serie = new Serie(cs.getNombre(), "color: " + strColor + ";grosor: " + String.valueOf(cs.getGrosor()) + ";símbolo: " + cs.getSimbolo() + ";", i, dataset.getSeriesCount());
                dataset.addSeries(serie);

                XYItemRenderer renderer = plot.getRenderer(i);
                renderer.setSeriesPaint(dataset.getSeriesCount() - 1, scene2awtColor(color));

                Simbolo simb = new Simbolo(cs.getSimbolo(), javafx.scene.paint.Color.web(strColor.replace("#", "0x")));

                if (cs.getGrosor() > 0)
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(dataset.getSeriesCount() - 1, true);
                    renderer.setSeriesStroke(dataset.getSeriesCount() - 1, new BasicStroke(cs.getGrosor()));
                }
                else
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(dataset.getSeriesCount() - 1, false);
                }

                if (cs.getSimbolo().equals("nulo"))
                {
                    renderer.setSeriesShape(dataset.getSeriesCount() - 1, null);
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(dataset.getSeriesCount() - 1, false);
                }
                else
                {
                    renderer.setSeriesShape(dataset.getSeriesCount() - 1, simb.getShapeAWT());
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(dataset.getSeriesCount() - 1, true);
                    if (cs.getSimbolo().contains("hueco"))
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(dataset.getSeriesCount() - 1, false);
                    }
                    else
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(dataset.getSeriesCount() - 1, true);
                    }
                }

                if (i == 0)
                {
                    plot.setRenderer(renderer);
                }
                else
                {
                    plot.setRenderer(i, renderer);
                }

                listaSerie.add(serie);

                final LeyendaEje le = getLeyendaEje(categoria);
                final Label label = new Label(cs.toString());
                Platform.runLater(() ->
                {
                    label.setStyle("fondo: " + strColorFondoGrafica + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(tamañoFuente) + "px");
                });
                
                label.setOnMouseClicked((MouseEvent t) ->
                {
                    if (t.getClickCount() == 2)
                    {
                        for (int i1 = 0; i1 < listaSerie.size(); i1++)
                        {
                            if (listaSerie.get(i1).getKey().toString().equals(label.getText()))
                            {
                                editarSerie(listaSerie.get(i1));
                                break;
                            }
                        }
                    }
                });
                label.setOnMouseExited((MouseEvent t) ->
                {
                    label.setStyle(label.getStyle().replace("-fx-background-color: blue", "-fx-background-color: fondo"));
                });
                label.setOnMouseEntered((MouseEvent t) ->
                {
                    label.setStyle(label.getStyle().replace("-fx-background-color: fondo", "-fx-background-color: blue"));
                    for (Node le1 : leyenda.getChildren())
                    {
                        if (le1 instanceof LeyendaEje)
                        {
                            le1.setStyle("-fx-background-color:" + strColorFondo);
                            ((LeyendaEje) le1).seleccionado = false;
                        }
                    }
                });
                label.setStyle("fondo: " + strColorFondoGrafica + ";-fx-text-fill: white;-fx-background-color: fondo;-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(tamañoFuente) + "px");

                le.getChildren().add(label);
                label.setGraphic(simb.getRepresentacion());

                break;
            }
        }
    }

    @Override
    public void addMuestra(double x, double[] valor)
    {
        try
        {
            for (int i = 0; i < valor.length; i++)
            {
                listaSerie.get(i).add(x, valor[i]);
            }
        }
        catch (SeriesException e)
        {

        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void addMuestra(double x, double y, String nombreSerie)
    {
        for (XYSeries serie : listaSerie)
        {
            if (serie.getKey().toString().equals(nombreSerie))
            {
                serie.add(x, y);
                break;
            }
        }
    }

    public void BorraMuestras(String nombreSerie)
    {
        for (XYSeries serie : listaSerie)
        {
            if (serie.getKey().toString().equals(nombreSerie))
            {
                serie.clear();
                break;
            }
        }
    }

    public void BorraMuestras()
    {
        for (XYSeries serie : listaSerie)
        {
            serie.clear();
        }
    }

    @Override
    public Pane getGrafica()
    {

        return marcoGrafica;
    }

    @Override
    public void fijaAbcisa(double dDesde, double dHasta)
    {
        ejeAbcisas.setAutoRange(false);
        ejeAbcisas.setUpperBound(dHasta);
        ejeAbcisas.setLowerBound(dDesde);
    }

}
