/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.CancelExcepcion;
import com.ProcessException;
import com.Global;
import com.TemporalTaskTemplate;
import static com.astronomy.estudio.ProbabilidadCasualidad.BERNOULLI;
import static com.astronomy.estudio.ProbabilidadCasualidad.ROSENFELDT;
import com.chart.CategoriaGrafica;
import com.chart.GraficaSwing;
import com.chart.SimpleConfigSerie;
import com.interfaz.esqueleto.MessageDialog;
import com.interfaz.esqueleto.ModalDialog;
import com.interfaz.Principal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Estudio extends VBox
{

    private String nombre;
    private String dirTrabajo = "";
    ObservableList data = FXCollections.observableArrayList(new ArrayList<>());
    TableView<Alineamiento> tabla = new TableView<>(data);
    Principal padre = null;
    private SplitPane splitPane;
    PanelAlineamiento panelAlineamiento;
    private boolean cambio = false;
    MenuBar menuBar;

    public Alineamiento inputAlineamiento()
    {
        DialogoInputAlineamiento dlg = new DialogoInputAlineamiento(padre, null);
        if (dlg.ShowModal())
        {
            return dlg.alineamiento;
        }
        else
        {
            return null;
        }
    }

    public final InfoFuncionDensidad inputInfoFuncionDensidad() throws ProcessException, CancelExcepcion
    {
        TextField tfDesviacionTipica = new TextField("1.0");
        TextField tfDeclinacionDesde = new TextField("-50");
        TextField tfDeclinacionHasta = new TextField("50");
        TextField tfDeclinacionPaso = new TextField("1.0");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Desviación típica"), 0, 0);
        gridPane.add(tfDesviacionTipica, 1, 0);
        gridPane.add(new Label("Declinación inicial"), 0, 1);
        gridPane.add(tfDeclinacionDesde, 1, 1);
        gridPane.add(new Label("Declinación final"), 0, 2);
        gridPane.add(tfDeclinacionHasta, 1, 2);
        gridPane.add(new Label("Paso declinación"), 0, 3);
        gridPane.add(tfDeclinacionPaso, 1, 3);

        ModalDialog dialogo = new ModalDialog(padre.skeleton, gridPane, true);
        if (dialogo.ShowModal())
        {
            try
            {
                return new InfoFuncionDensidad(
                        Double.valueOf(tfDesviacionTipica.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionDesde.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionHasta.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionPaso.getText().replace(",", ".")));
            }
            catch (NumberFormatException ex)
            {
                new MessageDialog(padre.skeleton, "Formato de parámetros incorrecto", MessageDialog.TipoMensaje.ERROR).Show();
                return null;
            }
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    public void Guardar() throws IOException
    {
        if (!isSaved())
        {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            Salva(selectedDirectory.getAbsolutePath());
        }
        else
        {
            Salva();
        }
    }

    public void Exportar()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar a fichero");
        fileChooser.setInitialDirectory(new File(getDirTrabajo()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma Separated Values", "*.csv"));

        Window w = null;
        try
        {
            w = padre.skeleton.getScene().getWindow();
        }
        catch (NullPointerException e)
        {

        }
        File file = fileChooser.showSaveDialog(w);
        String path = file.getAbsolutePath();

        if (!path.endsWith(".csv"))
        {
            file = new File(path + ".csv");
        }

        //Definir variables a exportar y orden
        FileWriter fichero;
        PrintWriter pw;
        try
        {
            //fichero = new FileWriter(file.getAbsolutePath(), false);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO8859_1")));
            //pw = new PrintWriter(fichero);

            pw.println("Origen;Referencia;Acimut;Elevación;Declinación;");
            for (Object dato : data)
            {
                String strRegistro = "";
                Alineamiento al = (Alineamiento) dato;
                strRegistro += al.pOrigen.getValue() + ";";
                strRegistro += al.pDestino.getValue() + ";";
                strRegistro += al.pAcimut.getValue() + ";";
                strRegistro += al.pElevacion.getValue() + ";";
                strRegistro += al.pDeclinacion.getValue() + ";";
                pw.println(strRegistro);
            }

            pw.close();
        }
        catch (IOException ex)
        {
            Global.info.Log(ex);
        }
    }

    public Estudio getVisualizacion(Principal padre)
    {
        this.padre = padre;
        panelAlineamiento = new PanelAlineamiento(padre, this);

        menuBar = new MenuBar();
        Menu menu = new Menu("Archivo");
        menuBar.getMenus().add(menu);
        MenuItem mi = new MenuItem("Nuevo alineamiento");
        mi.setOnAction((ActionEvent event) ->
        {
            NuevoAlineamiento();
        });
        menu.getItems().add(mi);
        mi = new MenuItem("Guardar");
        mi.setOnAction((ActionEvent event) ->
        {
            try
            {
                Guardar();
            }
            catch (IOException ex)
            {
                Global.info.Log(ex);
            }
        });
        menu.getItems().add(mi);
        mi = new MenuItem("Exportar");
        mi.setOnAction((ActionEvent event) ->
        {
            Exportar();
        });
        menu.getItems().add(mi);
        menu = new Menu("Análisis");
        menuBar.getMenus().add(menu);
        Menu submenu = new Menu("Estudio estadístico de probabilidad de alineamientos casuales");
        menu.getItems().add(submenu);
        mi = new MenuItem("Rosenfeldt");
        mi.setOnAction((ActionEvent event) ->
        {
            ProbabilidadAlineamientosCasuales(ROSENFELDT);
        });
        submenu.getItems().add(mi);
        mi = new MenuItem("Bernoulli");
        mi.setOnAction((ActionEvent event) ->
        {
            ProbabilidadAlineamientosCasuales(BERNOULLI);
        });
        submenu.getItems().add(mi);

        mi = new MenuItem("Función de densidad de probabilidad");
        mi.setOnAction((ActionEvent event) ->
        {
            FuncionDensidadProbabilidad();
        });
        menu.getItems().add(mi);

        VBox vTabla = new VBox(10);
        HBox hpanel = new HBox(10);
        Button b;

        getChildren().addAll(menuBar, hpanel);
        splitPane = new SplitPane();
        splitPane.getItems().addAll(vTabla);
        hpanel.getChildren().add(splitPane);

        HBox.setHgrow(splitPane, Priority.ALWAYS);
        HBox.setHgrow(panelAlineamiento, Priority.ALWAYS);

        VBox.setVgrow(hpanel, Priority.ALWAYS);
        vTabla.getChildren().addAll(tabla);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn origen = new TableColumn("Origen");
        TableColumn destino = new TableColumn("Referencia");
        TableColumn acimut = new TableColumn("Acimut");
        TableColumn elevacion = new TableColumn("Elevación");
        TableColumn declinacion = new TableColumn("Declinación");
        TableColumn observaciones = new TableColumn("Observaciones");
        origen.setCellValueFactory(new PropertyValueFactory("origen"));
        destino.setCellValueFactory(new PropertyValueFactory("destino"));
        acimut.setCellValueFactory(new PropertyValueFactory("acimut"));
        elevacion.setCellValueFactory(new PropertyValueFactory("elevacion"));
        declinacion.setCellValueFactory(new PropertyValueFactory("declinacion"));
        observaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));
        observaciones.setCellFactory(TextFieldTableCell.forTableColumn());
        observaciones.setOnEditCommit(new EventHandler<CellEditEvent<Alineamiento, String>>()
        {
            @Override
            public void handle(CellEditEvent<Alineamiento, String> event)
            {
                ((Alineamiento) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setDescripcion(event.getNewValue());
            }

        });
        tabla.getColumns().addAll(origen, destino, acimut, elevacion, declinacion, observaciones);
        tabla.setItems(data);
        tabla.setEditable(true);

        tabla.setRowFactory(tv ->
        {
            TableRow<Alineamiento> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) ->
            {
                if (!row.isEmpty())
                {
                    if (event.isPopupTrigger())
                    {
                        Alineamiento rowData = row.getItem();
                        ContextMenu cm = new ContextMenu();
                        MenuItem mmi = new MenuItem("Eliminar");
                        mmi.setOnAction((ActionEvent event1) ->
                        {
                            data.remove(rowData);
                            cambio = true;
                        });
                        cm.getItems().add(mmi);
                        mmi = new MenuItem("Editar");
                        mmi.setOnAction((ActionEvent event1) ->
                        {
                            DialogoInputAlineamiento dlg = new DialogoInputAlineamiento(padre, rowData);
                            if (dlg.ShowModal())
                            {
                                String img=rowData.getImagenPath();
                                String obs=rowData.getDescripcion();
                                rowData.set(dlg.alineamiento);
                                rowData.setImagenPath(img);
                                rowData.setDescripcion(obs);
                                setCambio(true);
                            }
                        });
                        cm.getItems().add(mmi);
                        mmi = new MenuItem("Copiar");
                        mmi.setOnAction((ActionEvent event1) ->
                        {
                            DialogoInputAlineamiento dlg = new DialogoInputAlineamiento(padre, rowData);
                            if (dlg.ShowModal())
                            {
                                
                                data.add(dlg.alineamiento);                                
                            }
                        });
                        cm.getItems().add(mmi);
                        cm.show(tabla, event.getScreenX(), event.getScreenY());
                    }
                    else if (event.getClickCount() == 1)
                    {
                        if (!splitPane.getItems().contains(panelAlineamiento))
                        {
                            splitPane.getItems().add(panelAlineamiento);
                        }
                        Alineamiento rowData = row.getItem();
                        panelAlineamiento.setData(rowData);
                    }

                }

            });
            return row;
        });

        return this;
    }

    public boolean isSaved()
    {
        return !dirTrabajo.isEmpty();
    }

    public Estudio(String nombre)
    {
        super(10);
        this.nombre = nombre;
        cambio = false;
    }

    public Estudio(String nombre, String dirTrabajo)
    {
        super(10);
        this.nombre = nombre;
        if (!dirTrabajo.endsWith(System.getProperty("file.separator")))
        {
            dirTrabajo += System.getProperty("file.separator");
        }
        this.dirTrabajo = dirTrabajo;
        cambio = false;
    }

    public Estudio()
    {
        super(10);
        this.nombre = "";
        cambio = false;
    }

    public void Carga(String dirTrabajo) throws JDOMException, IOException, ProcessException
    {
        parseXML(new File(dirTrabajo + nombre + ".awb"));
        cambio = false;
    }

    public void Carga() throws JDOMException, IOException, ProcessException
    {
        parseXML(new File(getDirTrabajo() + nombre + ".awb"));
        cambio = false;
    }

    public void Salva(String dirTrabajo, String nombre) throws IOException
    {
        if (!dirTrabajo.endsWith(System.getProperty("file.separator")))
        {
            dirTrabajo += System.getProperty("file.separator");
        }
        this.dirTrabajo = dirTrabajo;
        this.nombre = nombre;
        toXML(new File(dirTrabajo + nombre + ".awb"));
        cambio = false;
    }

    public void Salva(String dirTrabajo) throws IOException
    {
        if (!dirTrabajo.endsWith(System.getProperty("file.separator")))
        {
            dirTrabajo += System.getProperty("file.separator");
        }
        this.dirTrabajo = dirTrabajo;
        toXML(new File(dirTrabajo + nombre + ".awb"));
        cambio = false;
    }

    public void Salva() throws IOException
    {
        if (!dirTrabajo.isEmpty())
        {
            if (!dirTrabajo.endsWith(System.getProperty("file.separator")))
            {
                dirTrabajo += System.getProperty("file.separator");
            }
            toXML(new File(getDirTrabajo() + nombre + ".awb"));
            cambio = false;
        }
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void parseXML(File fichero) throws JDOMException, IOException, ProcessException
    {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(fichero);
        Element raiz = document.getRootElement();
        nombre = raiz.getAttributeValue("nombre");
        List<Element> aa = raiz.getChild("alineamientos").getChildren();
        data.clear();

        for (Element e : aa)
        {
            data.add(new Alineamiento(e));
        }
    }

    public void toXML(File fichero) throws FileNotFoundException, IOException, NullPointerException
    {
        Element estudioElemento = new Element("estudio");
        Document doc = new Document(estudioElemento);
        estudioElemento.setAttribute("nombre", nombre);
        Element aa = new Element("alineamientos");
        estudioElemento.addContent(aa);
        for (int i = 0; i < data.size(); i++)
        {
            Alineamiento ali = (Alineamiento) data.get(i);
            aa.addContent(ali.getElementoXML());
        }

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        FileOutputStream out = null;
        try
        {
            xmlOutput.output(doc, out = new FileOutputStream(fichero));
        }
        finally
        {
            out.close();
        }
    }

    public static void main(String args[])
    {
        File f;
        try
        {
            Estudio estudio = new Estudio("prueba");
            estudio.parseXML(new File("estudio.xml"));

        }
        catch (ProcessException | IOException | NullPointerException | JDOMException ex)
        {
            Logger.getLogger(Estudio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void NuevoAlineamiento()
    {
        Alineamiento alieneamiento = inputAlineamiento();
        boolean encontrado = false;
        for (Object data1 : data)
        {
            Alineamiento a = (Alineamiento) data1;
            if (alieneamiento.equals(a))
            {
                encontrado = true;
                break;
            }
        }
        if (!encontrado)
        {
            data.add(alieneamiento);
            cambio = true;
        }
    }

    /**
     * @return the dirTrabajo
     */
    public String getDirTrabajo()
    {
        return dirTrabajo;
    }

    /**
     * @return the cambio
     */
    public boolean isCambio()
    {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(boolean cambio)
    {
        this.cambio = cambio;
    }

    static private BigDecimal factorial(BigDecimal a)
    {
        if (a.compareTo(BigDecimal.ZERO) == 1)
        {
            BigDecimal m = new BigDecimal(a.longValue()).subtract(BigDecimal.ONE);
            BigDecimal r = new BigDecimal(a.longValue());

            while (m.compareTo(BigDecimal.ONE) == 1)
            {
                r = r.multiply(m);
                m = m.subtract(BigDecimal.ONE);
            }
            return r;
        }
        else
        {
            return BigDecimal.ONE;
        }

    }

    private double Rosentfeldt(int direcciones, int aciertos, int intentos, int error)
    {
        BigDecimal P = new BigDecimal(18); //direcciones significativas        
        //BigDecimal M = new BigDecimal(aciertos); // aciertos
        BigDecimal N = new BigDecimal(360).divide(new BigDecimal(2).multiply(new BigDecimal(error))); //margen de error
        BigDecimal S = new BigDecimal(intentos); //número marcas

        BigDecimal resultado = BigDecimal.ZERO;

        for (int a = aciertos; a <= intentos; a++)
        {
            BigDecimal M = new BigDecimal(a);

            BigDecimal num = Estudio.factorial(P).multiply(Estudio.factorial(N.subtract(P))).multiply(Estudio.factorial(S)).multiply(Estudio.factorial(N.subtract(S)));
            BigDecimal den = Estudio.factorial(M).multiply(Estudio.factorial(P.subtract(M))).multiply(Estudio.factorial(S.subtract(M))).multiply(Estudio.factorial(N.add(M).subtract(S).subtract(P))).multiply(Estudio.factorial(N));
            BigDecimal res = num.divide(den, 5, RoundingMode.HALF_UP);
            resultado = resultado.add(res);
        }

        return resultado.doubleValue();
    }

    private double Bernoulli(int direcciones, int aciertos, int intentos, int error)
    {
        BigDecimal P = new BigDecimal(18); //direcciones significativas
        BigDecimal N = new BigDecimal(360).divide(new BigDecimal(2).multiply(new BigDecimal(error))); //margen de error
        BigDecimal S = new BigDecimal(intentos); //número marcas
        BigDecimal probabilidad = P.divide(N);

        BigDecimal resultado = BigDecimal.ZERO;

        for (int a = aciertos; a <= intentos; a++)
        {
            BigDecimal M = new BigDecimal(a);

            BigDecimal num = Estudio.factorial(new BigDecimal(intentos)).multiply(probabilidad.pow(a)).multiply((BigDecimal.ONE.subtract(probabilidad)).pow(intentos - a));
            BigDecimal den = Estudio.factorial(M).multiply(Estudio.factorial(S.subtract(M)));
            BigDecimal res = num.divide(den, 5, RoundingMode.HALF_UP);
            resultado = resultado.add(res);
        }

        return resultado.doubleValue();
    }

    private void ProbabilidadAlineamientosCasuales(ProbabilidadCasualidad pc)
    {
        int intentos = 0;
        int aciertos = 0;
        for (int i = 0; i < data.size(); i++)
        {
            Alineamiento al = (Alineamiento) data.get(i);
            intentos++;
            FenomenoDatado fd = checkFenomeno(al.getDescripcion());
            if (fd != null && fd.getFenomeno() != null)
            {
                aciertos++;
            }
        }
        double v = 0;
        switch (pc)
        {
            case BERNOULLI:
                v = Bernoulli(18, aciertos, intentos, 1);
                break;
            case ROSENFELDT:
                v = Rosentfeldt(18, aciertos, intentos, 1);
                break;
        }

        new MessageDialog(padre.skeleton, String.format("Probabilidad de alineamientos astronómicos casuales %.2f%%", v * 100), MessageDialog.TipoMensaje.INFO).Show();

    }

    private void FuncionDensidadProbabilidad()
    {
        class ParFncDensidadDeclinacion
        {

            double fncDensidad;
            double declinacion;

            public ParFncDensidadDeclinacion(double declinacion)
            {
                this.declinacion = declinacion;
                this.fncDensidad = 0;
            }

            public void addFncDensidad(double f)
            {
                this.fncDensidad += f;
            }

            public double funcionDensidad(double declinacionReferencia, double desviacionTipica, int total)
            {
                double d = declinacion;
                double d0 = declinacionReferencia;
                double s = desviacionTipica;
                int n = total;
                return (exp(-pow(d - d0, 2) / (2 * pow(s, 2))) / sqrt(2 * PI)) / (n * s);
            }

        }

        try
        {
            List<CategoriaGrafica> categorias = new ArrayList<>();
            CategoriaGrafica categoria;
            categorias.add(categoria = new CategoriaGrafica("Densidad"));
            categoria.listaConfigSerie.add(new SimpleConfigSerie("Densidad", Color.LIME, 4, "nulo"));
            GraficaSwing grafica = new GraficaSwing("", padre.skeleton, categorias, "DE");

            InfoFuncionDensidad ifd = inputInfoFuncionDensidad();

            (new TemporalTaskTemplate<Double>("", ifd.getDeclinacionMinima(), ifd.getDeclinacionMaxima(), ifd.getPasoDeclinacion())
            {

                @Override
                public void cloneCurrent(Double entrada)
                {
                    setCurrent(new Double(entrada));
                }

                @Override
                public double doubleValue(Double entrada)
                {
                    return entrada;
                }

                @Override
                public String toString(Double entrada)
                {
                    return String.format("%.1f", entrada).replace(",", ".");
                }

                @Override
                public void addToCurrent(Double incremento)
                {
                    setCurrent(getCurrent() + incremento);
                }

                @Override
                public int compare(Double objeto, Double referencia)
                {
                    if (objeto < referencia)
                    {
                        return -1;
                    }
                    else if (objeto > referencia)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }

                @Override
                public void taskEnd()
                {
                    Tab tab = padre.NuevoTab("Función de densidad " + nombre);
                    tab.setContent(grafica.getGrafica());

                }

                @Override
                public void cycle()
                {
                    ParFncDensidadDeclinacion par = new ParFncDensidadDeclinacion(getCurrent());

                    for (int i = 0; i < data.size(); i++)
                    {
                        Alineamiento al = (Alineamiento) data.get(i);
                        par.addFncDensidad(par.funcionDensidad(
                                al.getDeclinacion().getSignedValue(),
                                ifd.getDesviacionTipica(),
                                data.size()));
                    }

                    grafica.addMuestra(par.declinacion, par.fncDensidad, "Densidad");
                }
            }).taskStart();

        }
        catch (ProcessException | CancelExcepcion ex)
        {

        }
    }

    static FenomenoDatado checkFenomeno(String descripcion)
    {
        Fenomeno f;
        int anno;
        if (!descripcion.isEmpty())
        {
            String[] tDes = descripcion.split(",");
            if (tDes.length == 2)
            {
                try
                {
                    anno = Integer.valueOf(tDes[1]);
                    f = Fenomeno.fromString(tDes[0]);
                    return new FenomenoDatado(f, anno);
                }
                catch (NumberFormatException ex)
                {

                }
            }
        }
        return null;
    }

}
