/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chart;

import com.Global;
import com.interfaz.skeleton.Skeleton;
import com.interfaz.skeleton.PseudoModalDialog;
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
 * @see JFreeChart
 */
public class SwingChart implements ChartInterface
{
    /**
     * List of axes
     */
    List<AxisChart> axes;
    /**
     * Parent
     */
    Skeleton skeleton;
    
    /**
     * Chart name
     */
    String name;

    /**
     * JFreeChart
     */
    public JFreeChart chart;
    /**
     * JFreeChart chart panel
     */
    public ChartPanel chartPanel;
    
    /**
     * List of series
     */
    public List<Series> seriesList = new ArrayList<>();
    
    /**
     * List of datasets
     */
    List<XYSeriesCollection> datasetList = new ArrayList<>();
    
    /**
     * List of axes
     */
    public List<NumberAxis> AxesList = new ArrayList<>();

    /**
     * Abcissa axis
     */
    public ValueAxis abcissaAxis;
    
    /**
     * Plot
     */
    XYPlot plot;
    
    /**
     * Context menu item list
     */
    public List<MenuItem> contextMenuList = new ArrayList<>();
    
    /**
     * Chart frame
     */
    VBox chartFrame;
    
    /**
     * Legend frame
     */
    VBox legendFrame = new VBox(5);
    
    /**
     * Context menu
     */
    ContextMenu menu;

    /**
     * Background color as string
     */
    public String strBackgroundColor = "#343434";
    /**
     * background color of chart as string
     */
    public String strChartBackgroundColor = "#226262";
    /**
     * Tick color
     */
    public String strTickColor = "white";
    /**
     * Gridline color
     */
    public String strGridlineColor = "white";
    
    /**
     * Font size
     */
    public float fontSize = 11;

    /**
     * Abcissa format
     */
    protected Format abcissaFormat;
    /**
     * Ordinate format
     */
    protected final Format ordinateFormat;

    /**
     * Chart mouse listener
     */
    protected ChartMouseListener cml;

    /**
     * Update background, tick and gridline colors
     * @param cfg cfg[0] Background, cfg[1] Chart background, cfg[2] y cfg[3] gridline
     */
    public void updateChartColors(String[] cfg)
    {
        strBackgroundColor = cfg[0];
        for (Node le : legendFrame.getChildren())
        {
            if (le instanceof LegendAxis)
            {
                le.setStyle("-fx-background-color:" + strBackgroundColor);
                ((LegendAxis) le).selected = false;
            }
        }
        chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)))));
        chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));

        legendFrame.setStyle("marco: " + strBackgroundColor + ";-fx-background-color: marco;");

        strChartBackgroundColor = cfg[1];;
        plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strChartBackgroundColor)));

        for (Node le : legendFrame.getChildren())
        {
            if (le instanceof LegendAxis)
            {
                le.setStyle("-fx-background-color:" + strBackgroundColor);
                ((LegendAxis) le).selected = false;
                for (Node nn : ((LegendAxis) le).getChildren())
                {
                    if (nn instanceof Label)
                    {
                        ((Label) nn).setStyle("fondo: " + strChartBackgroundColor + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(fontSize) + "px");
                    }
                }
            }
        }

        strGridlineColor = cfg[2];;
        plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));
        plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));

        strTickColor = cfg[3];;
        abcissaAxis.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        abcissaAxis.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        for (NumberAxis ejeOrdenada : AxesList)
        {
            ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
            ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        }
    }

    /**
     * 
     * @param max maximum item count for all series
     */
    public void setMaxItemCount(int max)
    {
        for (Series series : seriesList)
        {
            series.setMaximumItemCount(max);
        }
    }

    /**
     * Abcissa format
     * @param af Abcissa format to set
     */
    public void setAbcissaFormat(Format af)
    {
        this.abcissaFormat = af;
    }

    
 
    /**
     * 
     * @param i Index
     * @return default color by index
     */
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

    /**
     * 
     * @param color JavaFX color
     * @return AWT Color
     */
    final java.awt.Color scene2awtColor(javafx.scene.paint.Color color)
    {
        return new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
    }

    /**
     * 
     * @param name Axis name
     * @return Legend axis by name
     */
    LegendAxis getLegendAxis(String name)
    {
        for (Node n : legendFrame.getChildren())
        {
            if (n instanceof LegendAxis)
            {
                if (((LegendAxis) n).getName().equals(name))
                {
                    return (LegendAxis) n;
                }
            }
        }
        return null;
    }

    /**
     * Export chart values to a csv file
     * @param file CSV file
     */
    private void export(File file)
    {
        String registro;
        int n = 0;
        int ne = 0;
        String cabecera = "Time;";
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
                        exportacion[e][n] = ordinateFormat.format(s.getDataItem(e).getYValue());
                    }
                    catch (Exception ex)
                    {
                        exportacion[e][n] = "";
                    }
                    if (n == 1)
                    {

                        exportacion[e][0] = abcissaFormat.format(s.getDataItem(e).getXValue());

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
            Global.info.log(ex);
        }
    }

    /**
     * Copy to clipboard
     * @param node JavaFX Node to copy
     */
    public final void copyClipboard(final Node node)
    {

        WritableImage captura = node.snapshot(new SnapshotParameters(), null);

        ImageView iv = new ImageView(captura);

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.put(DataFormat.IMAGE, captura);
        clipboard.setContent(content);

    }

    /**
     * 
     * @param name Chart name
     * @param parent Skeleton parent
     * @param axes Configuration of axes
     * @param abcissaName Abcissa name
     */
    public SwingChart(String name, final Skeleton parent, List<AxisChart> axes, String abcissaName)
    {
        this.skeleton = parent;
        this.axes = axes;
        this.name = name;

        this.abcissaFormat = NumberFormat.getInstance(Locale.getDefault());
        this.ordinateFormat = NumberFormat.getInstance(Locale.getDefault());

        plot = new XYPlot();
        plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strChartBackgroundColor)));
        plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));
        plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

        abcissaAxis = new NumberAxis(abcissaName);
        ((NumberAxis) abcissaAxis).setAutoRangeIncludesZero(false);
        abcissaAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        abcissaAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        abcissaAxis.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        abcissaAxis.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        abcissaAxis.setAutoRange(true);
        abcissaAxis.setLowerMargin(0.0);
        abcissaAxis.setUpperMargin(0.0);
        abcissaAxis.setTickLabelsVisible(true);
        abcissaAxis.setLabelFont(abcissaAxis.getLabelFont().deriveFont(fontSize));
        abcissaAxis.setTickLabelFont(abcissaAxis.getLabelFont().deriveFont(fontSize));

        plot.setDomainAxis(abcissaAxis);

        for (int i = 0; i < axes.size(); i++)
        {
            AxisChart categoria = axes.get(i);
            addAxis(categoria.getName());

            for (int j = 0; j < categoria.configSerieList.size(); j++)
            {
                SimpleSeriesConfiguration cs = categoria.configSerieList.get(j);
                addSeries(categoria.getName(), cs);
            }
        }
        chart = new JFreeChart(
                "",
                new Font("SansSerif", Font.BOLD, 16),
                plot,
                false);

        chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)))));

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
                            NumberAxis ejeOrdenada = AxesList.get(i);
                            double y_max = ejeOrdenada.getUpperBound();
                            double y_min = ejeOrdenada.getLowerBound();
                            double x_max = abcissaAxis.getUpperBound();
                            double x_min = abcissaAxis.getLowerBound();
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
                            String txt = "X:" + abcissaFormat.format(x) + ", Y:" + ordinateFormat.format(y);
                            XYPointerAnnotation anotacion = new XYPointerAnnotation(txt, dataset.getXValue(xyitem.getSeriesIndex(), xyitem.getItem()), dataset.getYValue(xyitem.getSeriesIndex(), xyitem.getItem()), angulo);
                            anotacion.setTipRadius(10.0);
                            anotacion.setBaseRadius(35.0);
                            anotacion.setFont(new Font("SansSerif", Font.PLAIN, 10));

                            if (Long.parseLong((strChartBackgroundColor.replace("#", "")), 16) > 0xffffff / 2)
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
        chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));

        SwingNode sn = new SwingNode();
        sn.setContent(chartPanel);
        chartFrame = new VBox();
        chartFrame.getChildren().addAll(sn, legendFrame);
        VBox.setVgrow(sn, Priority.ALWAYS);
        VBox.setVgrow(legendFrame, Priority.NEVER);

        chartFrame.getStylesheets().addAll(SwingChart.class
                .getResource("overlay-chart.css").toExternalForm());

        legendFrame.setStyle(
                "marco: " + strBackgroundColor + ";-fx-background-color: marco;");

        MenuItem mi;
        mi = new MenuItem("Print");
        mi.setOnAction((ActionEvent t) ->
        {
            print(chartFrame);
        });
        contextMenuList.add(mi);

        sn.setOnMouseClicked((MouseEvent t) ->
        {
            if (menu != null)
            {
                menu.hide();
            }
            if (t.getClickCount() == 2)
            {
                backgroundEdition();
            }
        });

        mi = new MenuItem("Copy to clipboard");
        mi.setOnAction((ActionEvent t) ->
        {
            copyClipboard(chartFrame);
        });
        contextMenuList.add(mi);

        mi = new MenuItem("Export values");
        mi.setOnAction((ActionEvent t) ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export to file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Comma Separated Values", "*.csv"));

            Window w = null;
            try
            {
                w = parent.getScene().getWindow();
            }
            catch (NullPointerException e)
            {

            }
            File file = fileChooser.showSaveDialog(w);
            if (file != null)
            {
                export(file);
            }
        });
        contextMenuList.add(mi);

        chartFrame.setOnContextMenuRequested((ContextMenuEvent t) ->
        {
            if (menu != null)
            {
                menu.hide();
            }
            menu = new ContextMenu();
            menu.getItems().addAll(contextMenuList);
            menu.show(chartFrame, t.getScreenX(), t.getScreenY());
        });

    }

    /**
     * Send node to printer
     * @param node JavaFX node
     */
    public final void print(final Node node)
    {
        LegendTitle legend = new LegendTitle(chart.getPlot());
        legend.setItemFont(legend.getItemFont().deriveFont(fontSize));
        chart.addLegend(legend);
        legend.setPosition(RectangleEdge.BOTTOM);
        chart.setTitle(name);
        chartPanel.createChartPrintJob();
        chart.removeLegend();
        chart.setTitle("");
    }

    /**
     * Series edition
     * @param series Series to edit
     */
    void editSeries(final Series series)
    {
        String[] style = series.getStyle().split(";");
        String strColor = "black";
        final TextField editWidth = new TextField();

        String tempS = "null";
        for (String e : style)
        {
            if (e.contains("color: "))
            {
                strColor = e.replace("color: ", "");
            }
            else if (e.contains("width: "))
            {
                editWidth.setText(e.replace("width: ", ""));
            }
            else if (e.contains("shape: "))
            {
                tempS = e.replace("shape: ", "");
            }
        }
        final String symbol = tempS;

        final List<SeriesShape> symbolList = new ArrayList<>();
        final ObservableList<SeriesShape> symbolListModel;
        final ListView<SeriesShape> comboSymbol = new ListView();
        symbolList.add(new SeriesShape("null", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("rectangle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("circle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("triangle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("crux", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("diamond", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("empty rectangle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("empty circle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("empty triangle", javafx.scene.paint.Color.web(strColor)));
        symbolList.add(new SeriesShape("empty diamond", javafx.scene.paint.Color.web(strColor)));

        symbolListModel = FXCollections.observableList(symbolList);
        comboSymbol.setItems(symbolListModel);
        comboSymbol.setCellFactory(new Callback<ListView<SeriesShape>, ListCell<SeriesShape>>()
        {
            @Override
            public ListCell<SeriesShape> call(ListView<SeriesShape> p)
            {
                ListCell<SeriesShape> cell = new ListCell<SeriesShape>()
                {
                    @Override
                    protected void updateItem(SeriesShape t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            setText("");
                            setGraphic(t.getShapeGraphic());
                        }
                    }
                };

                return cell;
            }
        });
        for (SeriesShape smb : symbolListModel)
        {
            if (smb.getName().equals(symbol))
            {
                comboSymbol.getSelectionModel().select(smb);
            }
        }

        final ColorPicker colorPicker = new ColorPicker(javafx.scene.paint.Color.web(strColor));

        colorPicker.setOnAction((ActionEvent t) ->
        {
            String sc = colorPicker.getValue().toString();
            symbolListModel.clear();
            symbolListModel.add(new SeriesShape("null", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("rectangle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("circle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("triangle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("crux", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("diamond", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("empty rectangle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("empty circle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("empty triangle", javafx.scene.paint.Color.web(sc)));
            symbolListModel.add(new SeriesShape("empty diamond", javafx.scene.paint.Color.web(sc)));

            comboSymbol.setItems(symbolListModel);
            for (SeriesShape smb : symbolListModel)
            {
                if (smb.getName().equals(symbol))
                {
                    comboSymbol.getSelectionModel().select(smb);
                }
            }
        });

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Series"), 0, 0);
        grid.add(new Label(series.getKey().toString()), 1, 0);
        grid.add(new Label("Color"), 0, 1);
        grid.add(colorPicker, 1, 1);
        grid.add(new Label("Width"), 0, 2);
        grid.add(editWidth, 1, 2);
        grid.add(new Label("Shape"), 0, 3);
        grid.add(comboSymbol, 1, 3);

        new PseudoModalDialog(skeleton, grid, true)
        {
            @Override
            public boolean validation()
            {
                String strColor = colorPicker.getValue().toString();
                String strWidth = editWidth.getText();
                double dWidth = Double.valueOf(strWidth);
                String strSimbolo = "null";
                SeriesShape simb = new SeriesShape(comboSymbol.getSelectionModel().getSelectedItem().toString(), javafx.scene.paint.Color.web(strColor));

                XYItemRenderer renderer = (XYItemRenderer) plot.getRenderer(series.getAxisIndex());

                renderer.setSeriesPaint(series.getSeriesIndex(), scene2awtColor(colorPicker.getValue()));

                try
                {
                    if (Double.valueOf(strWidth) > 0)
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(series.getSeriesIndex(), true);
                        renderer.setSeriesStroke(series.getSeriesIndex(), new BasicStroke(Integer.valueOf(strWidth)));
                    }
                    else
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(series.getSeriesIndex(), false);
                    }
                }
                catch (NumberFormatException ex)
                {

                }

                if (simb.getName().contains("null"))
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(series.getSeriesIndex(), false);
                    renderer.setSeriesShape(series.getSeriesIndex(), null);
                }
                else
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(series.getSeriesIndex(), true);
                    renderer.setSeriesShape(series.getSeriesIndex(), simb.getShapeAWT());
                    if (simb.getName().contains("empty"))
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(series.getSeriesIndex(), false);
                    }
                    else
                    {
                        ((XYLineAndShapeRenderer) renderer).setSeriesShapesFilled(series.getSeriesIndex(), true);
                    }

                }

                series.setStyle("color: " + strColor + ";width: " + editWidth.getText() + ";shape: " + strSimbolo + ";");

                for (Node le : legendFrame.getChildren())
                {
                    if (le instanceof LegendAxis)
                    {
                        for (Node nn : ((LegendAxis) le).getChildren())
                        {
                            if (nn instanceof Label)
                            {
                                if (((Label) nn).getText().equals(series.getKey().toString()))
                                {
                                    ((Label) nn).setGraphic(simb.getShapeGraphic());
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }.show();

    }

    /**
     * Background edition
     */
    final public void backgroundEdition()
    {
        final ColorPicker colorPickerChartBackground = new ColorPicker(javafx.scene.paint.Color.web(strChartBackgroundColor));
        colorPickerChartBackground.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerGridline = new ColorPicker(javafx.scene.paint.Color.web(strGridlineColor));
        colorPickerGridline.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerBackground = new ColorPicker(javafx.scene.paint.Color.web(strBackgroundColor));
        colorPickerBackground.setMaxWidth(Double.MAX_VALUE);
        final ColorPicker colorPickerTick = new ColorPicker(javafx.scene.paint.Color.web(strTickColor));
        colorPickerTick.setMaxWidth(Double.MAX_VALUE);
        final TextField tfFontSize = new TextField();
        tfFontSize.setMaxWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(new Label("Background color"), 0, 0);
        grid.add(colorPickerChartBackground, 1, 0);
        grid.add(new Label("Gridline color"), 0, 1);
        grid.add(colorPickerGridline, 1, 1);
        grid.add(new Label("Frame color"), 0, 2);
        grid.add(colorPickerBackground, 1, 2);
        grid.add(new Label("Tick color"), 0, 3);
        grid.add(colorPickerTick, 1, 3);
        grid.add(new Label("Font size"), 0, 4);
        grid.add(tfFontSize, 1, 4);
        tfFontSize.setText(String.valueOf(fontSize));

        new PseudoModalDialog(skeleton, grid, true)
        {

            @Override
            public boolean validation()
            {
                fontSize = Float.valueOf(tfFontSize.getText().replace(",", "."));
                strBackgroundColor = colorPickerBackground.getValue().toString().replace("0x", "#");
                for (Node le : legendFrame.getChildren())
                {
                    if (le instanceof LegendAxis)
                    {
                        le.setStyle("-fx-background-color:" + strBackgroundColor);
                        ((LegendAxis) le).selected = false;
                    }
                }
                chart.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));
                chartPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(4, 4, 4, 4),
                        BorderFactory.createLineBorder(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)))));
                chartPanel.setBackground(scene2awtColor(javafx.scene.paint.Color.web(strBackgroundColor)));

                legendFrame.setStyle("marco: " + colorPickerBackground.getValue().toString().replace("0x", "#") + ";-fx-background-color: marco;");

                strChartBackgroundColor = colorPickerChartBackground.getValue().toString().replace("0x", "#");
                plot.setBackgroundPaint(scene2awtColor(javafx.scene.paint.Color.web(strChartBackgroundColor)));

                for (Node le : legendFrame.getChildren())
                {
                    if (le instanceof LegendAxis)
                    {
                        le.setStyle("-fx-background-color:" + strBackgroundColor);
                        ((LegendAxis) le).selected = false;
                        for (Node nn : ((LegendAxis) le).getChildren())
                        {
                            if (nn instanceof Label)
                            {
                                ((Label) nn).setStyle("fondo: " + colorPickerChartBackground.getValue().toString().replace("0x", "#") + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(fontSize) + "px");
                            }
                        }
                    }
                }

                strGridlineColor = colorPickerGridline.getValue().toString().replace("0x", "#");
                plot.setDomainGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));
                plot.setRangeGridlinePaint(scene2awtColor(javafx.scene.paint.Color.web(strGridlineColor)));

                strTickColor = colorPickerTick.getValue().toString().replace("0x", "#");
                abcissaAxis.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
                abcissaAxis.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
                abcissaAxis.setLabelFont(abcissaAxis.getLabelFont().deriveFont(fontSize));
                abcissaAxis.setTickLabelFont(abcissaAxis.getLabelFont().deriveFont(fontSize));

                for (NumberAxis ejeOrdenada : AxesList)
                {
                    ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
                    ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
                    ejeOrdenada.setLabelFont(ejeOrdenada.getLabelFont().deriveFont(fontSize));
                    ejeOrdenada.setTickLabelFont(ejeOrdenada.getLabelFont().deriveFont(fontSize));
                }
                return true;
            }
        }.show();

    }

    /**
     * Set lower and upper limits for an ordinate
     * @param axis Axis to configure
     */
    void setOrdinateRange(final NumberAxis axis)
    {
        axis.setAutoRange(false);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        final TextField tfMax;
        final TextField tfMin;
        final TextField tfTick;
        final TextField tfFuente;
        grid.add(new Label("Axis"), 0, 0);
        grid.add(new Label(axis.getLabel()), 1, 0);
        grid.add(new Label("Lower"), 0, 1);
        grid.add(tfMin = new TextField(), 1, 1);
        grid.add(new Label("Upper"), 0, 2);
        grid.add(tfMax = new TextField(), 1, 2);
        grid.add(new Label("Space"), 0, 3);
        grid.add(tfTick = new TextField(), 1, 3);

        tfMin.setText(String.valueOf(axis.getLowerBound()));
        tfMax.setText(String.valueOf(axis.getUpperBound()));
        tfTick.setText(String.valueOf(axis.getTickUnit().getSize()));

        new PseudoModalDialog(skeleton, grid, true)
        {
            @Override
            public boolean validation()
            {
                axis.setLowerBound(Double.valueOf(tfMin.getText()));
                axis.setUpperBound(Double.valueOf(tfMax.getText()));
                axis.setTickUnit(new NumberTickUnit(Double.valueOf(tfTick.getText())));
                return true;
            }
        }.show();

    }

    /**
     * Set lower and upper limits for an ordinate
     * @param i Axis index
     * @param lower Lower limit
     * @param upper Upper limit
     * @param tick Tick unit
     */
    public void setOrdinateRange(int i, double lower, double upper, double tick)
    {
        NumberAxis e = AxesList.get(i);
        e.setAutoRange(false);
        e.setLowerBound(lower);
        e.setUpperBound(upper);
        e.setTickUnit(new NumberTickUnit(tick));

    }

    /**
     * Add axis
     * @param name new axis name 
     */
    @Override
    public final void addAxis(String name)
    {
        boolean encontrado = false;
        for (AxisChart categoria : axes)
        {
            if (categoria.getName().equals(name))
            {
                encontrado = true;
                break;
            }
        }
        if (!encontrado)
        {
            axes.add(new AxisChart((name)));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        NumberAxis ejeOrdenada = new NumberAxis(name);

        ejeOrdenada.setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        ejeOrdenada.setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        ejeOrdenada.setLabelFont(ejeOrdenada.getLabelFont().deriveFont(fontSize));
        ejeOrdenada.setTickLabelFont(ejeOrdenada.getLabelFont().deriveFont(fontSize));

        int i = datasetList.size();

        datasetList.add(dataset);
        AxesList.add(ejeOrdenada);
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

        final LegendAxis le;
        final int indiceLeyenda = legendFrame.getChildren().size();

        legendFrame.getChildren().add(le = new LegendAxis(name));

        le.setOnMouseClicked((MouseEvent t) ->
        {
            if (le.selected && t.getClickCount() == 2)
            {
                setOrdinateRange(AxesList.get(indiceLeyenda));
            }
        });

        le.setOnMouseEntered((MouseEvent t) ->
        {
            le.setStyle("-fx-background-color:blue");
            le.selected = true;
            AxesList.get(indiceLeyenda).setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web("blue")));
            AxesList.get(indiceLeyenda).setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web("blue")));
        });

        le.setOnMouseExited((MouseEvent t) ->
        {
            le.setStyle("-fx-background-color:" + strBackgroundColor);
            le.selected = false;
            AxesList.get(indiceLeyenda).setLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
            AxesList.get(indiceLeyenda).setTickLabelPaint(scene2awtColor(javafx.scene.paint.Color.web(strTickColor)));
        });
    }

    /**
     * 
     * @param axis Axis name to wich the new series belongs
     * @param cs Series Coinfiguration
     */
    @Override
    public final void addSeries(String axis, SimpleSeriesConfiguration cs)
    {
        for (int i = 0; i < axes.size(); i++)
        {
            if (axes.get(i).getName().equals(axis))
            {
                String strColor;
                javafx.scene.paint.Color color;
                int indice = seriesList.size();
                if (cs.getColor() == null)
                {
                    color = getColor(indice);
                }
                else
                {
                    color = cs.getColor();
                }
                strColor = color.toString();

                XYSeriesCollection dataset = datasetList.get(i);
                Series series = new Series(cs.getName(), "color: " + strColor + ";width: " + String.valueOf(cs.getLineWidth()) + ";shape: " + cs.getShapeName()+ ";", i, dataset.getSeriesCount());
                dataset.addSeries(series);

                XYItemRenderer renderer = plot.getRenderer(i);
                renderer.setSeriesPaint(dataset.getSeriesCount() - 1, scene2awtColor(color));

                SeriesShape simb = new SeriesShape(cs.getShapeName(), javafx.scene.paint.Color.web(strColor.replace("#", "0x")));

                if (cs.getLineWidth()> 0)
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(dataset.getSeriesCount() - 1, true);
                    renderer.setSeriesStroke(dataset.getSeriesCount() - 1, new BasicStroke(cs.getLineWidth()));
                }
                else
                {
                    ((XYLineAndShapeRenderer) renderer).setSeriesLinesVisible(dataset.getSeriesCount() - 1, false);
                }

                if (cs.getShapeName().equals("null"))
                {
                    renderer.setSeriesShape(dataset.getSeriesCount() - 1, null);
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(dataset.getSeriesCount() - 1, false);
                }
                else
                {
                    renderer.setSeriesShape(dataset.getSeriesCount() - 1, simb.getShapeAWT());
                    ((XYLineAndShapeRenderer) renderer).setSeriesShapesVisible(dataset.getSeriesCount() - 1, true);
                    if (cs.getShapeName().contains("empty"))
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

                seriesList.add(series);

                final LegendAxis le = getLegendAxis(axis);
                final Label label = new Label(cs.toString());
                Platform.runLater(() ->
                {
                    label.setStyle("fondo: " + strChartBackgroundColor + ";-fx-background-color: fondo;-fx-text-fill: ladder(fondo, white 49%, black 50%);-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(fontSize) + "px");
                });
                
                label.setOnMouseClicked((MouseEvent t) ->
                {
                    if (t.getClickCount() == 2)
                    {
                        for (int i1 = 0; i1 < seriesList.size(); i1++)
                        {
                            if (seriesList.get(i1).getKey().toString().equals(label.getText()))
                            {
                                editSeries(seriesList.get(i1));
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
                    for (Node le1 : legendFrame.getChildren())
                    {
                        if (le1 instanceof LegendAxis)
                        {
                            le1.setStyle("-fx-background-color:" + strBackgroundColor);
                            ((LegendAxis) le1).selected = false;
                        }
                    }
                });
                label.setStyle("fondo: " + strChartBackgroundColor + ";-fx-text-fill: white;-fx-background-color: fondo;-fx-padding:5px;-fx-background-radius: 5;-fx-font-size: " + String.valueOf(fontSize) + "px");

                le.getChildren().add(label);
                label.setGraphic(simb.getShapeGraphic());

                break;
            }
        }
    }

    /**
     * Add smaples for all series
     * @param x abcissa
     * @param values ordinates or values 
     */
    @Override
    public void addSample(double x, double[] values)
    {
        try
        {
            for (int i = 0; i < values.length; i++)
            {
                seriesList.get(i).add(x, values[i]);
            }
        }
        catch (SeriesException e)
        {

        }
        catch (Exception e)
        {

        }
    }

    /**
     * Add sample to a series
     * @param x Abcissa
     * @param y Ordinate
     * @param seriesName Series name 
     */
    @Override
    public void addSample(double x, double y, String seriesName)
    {
        for (XYSeries serie : seriesList)
        {
            if (serie.getKey().toString().equals(seriesName))
            {
                serie.add(x, y);
                break;
            }
        }
    }

    /**
     * Clear a series
     * @param seriesName Name of series to clear
     */
    public void clear(String seriesName)
    {
        for (XYSeries serie : seriesList)
        {
            if (serie.getKey().toString().equals(seriesName))
            {
                serie.clear();
                break;
            }
        }
    }

    /**
     * Clear all series
     */
    public void clear()
    {
        for (XYSeries serie : seriesList)
        {
            serie.clear();
        }
    }

    /**
     * 
     * @return chart frame
     */
    @Override
    public Pane getChart()
    {

        return chartFrame;
    }

    /**
     * Set abcissa upper and lower limits
     * @param lower Lower limit
     * @param upper Upper limit
     */
    @Override
    public void setAbcissaRange(double lower, double upper)
    {
        abcissaAxis.setAutoRange(false);
        abcissaAxis.setUpperBound(upper);
        abcissaAxis.setLowerBound(lower);
    }

}
