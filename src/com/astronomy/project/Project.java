/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.CancelExcepcion;
import com.ProcessException;
import com.Global;
import com.TemporalTaskTemplate;
import static com.astronomy.project.FortuitousProbabilityOption.BERNOULLI;
import static com.astronomy.project.FortuitousProbabilityOption.ROSENFELDT;
import com.chart.AxisChart;
import com.chart.SwingChart;
import com.chart.SimpleSeriesConfiguration;
import com.interfaz.skeleton.MessageDialog;
import com.interfaz.skeleton.ModalDialog;
import com.main.Main;
import static com.main.Main.skeleton;
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
 * Archaeoastronomy project
 *
 * @author MIGUEL_ANGEL
 */
public class Project extends VBox
{

    /**
     * Project name
     */
    private String name;
    /**
     * Work dir
     */
    private String workDir = "";
    /**
     * Observable list of Alignment
     */
    ObservableList data = FXCollections.observableArrayList(new ArrayList<>());
    /**
     * Table view for alignments
     */
    TableView<Alignment> table = new TableView<>(data);
    /**
     * Main parent
     */
    Main parent = null;
    /**
     * Main slipt pane.
     */
    private SplitPane splitPane;
    /**
     * Pane for details of an alignment
     */
    AlignmentPane alignmentPane;

    /**
     * If true a change has been mada and project needs to be saved before
     * closing
     */
    private boolean change = false;
    MenuBar menuBar;

    /**
     * Dialog for creating a new alignment
     *
     * @return Alignment
     */
    public static Alignment inputAlignment()
    {
        AlignmentDialogInput dlg = new AlignmentDialogInput(null);
        if (dlg.showModal())
        {
            return dlg.alignment;
        }
        else
        {
            return null;
        }
    }

    /**
     * Dialog for selecting the Gaussian kernel density estimation parameters
     *
     * @return Gaussian kernel density estimation parameters
     * @throws ProcessException Format error
     * @throws CancelExcepcion cancelation by user
     */
    public final GaussianKernelDensityEstimationParameters inputGaussianKernelDensityEstimationParameters() throws ProcessException, CancelExcepcion
    {
        TextField tfDesviacionTipica = new TextField("1.0");
        TextField tfDeclinacionDesde = new TextField("-50");
        TextField tfDeclinacionHasta = new TextField("50");
        TextField tfDeclinacionPaso = new TextField("1.0");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(new Label("Standard deviation"), 0, 0);
        gridPane.add(tfDesviacionTipica, 1, 0);
        gridPane.add(new Label("Minimum value of declination"), 0, 1);
        gridPane.add(tfDeclinacionDesde, 1, 1);
        gridPane.add(new Label("Maximum value of declination"), 0, 2);
        gridPane.add(tfDeclinacionHasta, 1, 2);
        gridPane.add(new Label("Declination increment"), 0, 3);
        gridPane.add(tfDeclinacionPaso, 1, 3);

        ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
        if (dialogo.showModal())
        {
            try
            {
                return new GaussianKernelDensityEstimationParameters(
                        Double.valueOf(tfDesviacionTipica.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionDesde.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionHasta.getText().replace(",", ".")),
                        Double.valueOf(tfDeclinacionPaso.getText().replace(",", ".")));
            }
            catch (NumberFormatException ex)
            {
                new MessageDialog(skeleton, "Formato de parámetros incorrecto", MessageDialog.MessageType.ERROR).show();
                return null;
            }
        }
        else
        {
            throw new CancelExcepcion("");
        }
    }

    /**
     * Save project
     *
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations
     */
    public void saveProject() throws IOException
    {
        if (!exists())
        {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            Project.this.save(selectedDirectory.getAbsolutePath());
        }
        else
        {
            save();
        }
    }

    /**
     * Export project
     */
    public void export()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export a CSV file");
        fileChooser.setInitialDirectory(new File(getWorkDir()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Comma Separated Values", "*.csv"));

        Window w = null;
        try
        {
            w = skeleton.getScene().getWindow();
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

        FileWriter fichero;
        PrintWriter pw;
        try
        {
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO8859_1")));
            pw.println("Observatory;Foresight;Azimuth;Altitude;Declination;");
            for (Object dato : data)
            {
                String strRegistro = "";
                Alignment al = (Alignment) dato;
                strRegistro += al.pObservatory.getValue() + ";";
                strRegistro += al.pForesight.getValue() + ";";
                strRegistro += al.pAzimuth.getValue() + ";";
                strRegistro += al.pAltitude.getValue() + ";";
                strRegistro += al.pDeclination.getValue() + ";";
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
     * Get project interface
     *
     * @param parent Main parent
     * @return Project as VBox
     */
    public Project getInterface(Main parent)
    {
        this.parent = parent;
        alignmentPane = new AlignmentPane(parent, this);

        menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.getMenus().add(menu);
        MenuItem mi = new MenuItem("New alignment");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    newAlignment();
        });
        menu.getItems().add(mi);
        mi = new MenuItem("Save");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    try
                    {
                        saveProject();
                    }
                    catch (IOException ex)
                    {
                        Global.info.log(ex);
                    }
        });
        menu.getItems().add(mi);
        mi = new MenuItem("Export");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    export();
        });
        menu.getItems().add(mi);
        menu = new Menu("Data analysis");
        menuBar.getMenus().add(menu);
        Menu submenu = new Menu("Probability of fortuitous astronomical coincidences from a number of alignments");
        menu.getItems().add(submenu);
        mi = new MenuItem("Rosenfeldt");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    fortuitousProbality(ROSENFELDT);
        });
        submenu.getItems().add(mi);
        mi = new MenuItem("Bernoulli");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    fortuitousProbality(BERNOULLI);
        });
        submenu.getItems().add(mi);

        mi = new MenuItem("Probability density estimation using a Gaussian kernel");
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    probabilityDensityEstimation();
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
        HBox.setHgrow(alignmentPane, Priority.ALWAYS);

        VBox.setVgrow(hpanel, Priority.ALWAYS);
        vTabla.getChildren().addAll(table);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn observatory = new TableColumn("Observatory");
        TableColumn foresight = new TableColumn("Foresight");
        TableColumn azimuth = new TableColumn("Azimuth");
        TableColumn altitude = new TableColumn("Altitude");
        TableColumn declination = new TableColumn("Declination");
        TableColumn comments = new TableColumn("Comments");
        observatory.setCellValueFactory(new PropertyValueFactory("observatory"));
        foresight.setCellValueFactory(new PropertyValueFactory("foresight"));
        azimuth.setCellValueFactory(new PropertyValueFactory("azimuth"));
        altitude.setCellValueFactory(new PropertyValueFactory("altitude"));
        declination.setCellValueFactory(new PropertyValueFactory("declination"));
        comments.setCellValueFactory(new PropertyValueFactory("comments"));
        comments.setCellFactory(TextFieldTableCell.forTableColumn());
        comments.setOnEditCommit(new EventHandler<CellEditEvent<Alignment, String>>()
        {
            @Override
            public void handle(CellEditEvent<Alignment, String> event)
            {
                ((Alignment) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setComments(event.getNewValue());
            }

        });
        table.getColumns().addAll(observatory, foresight, azimuth, altitude, declination, comments);
        table.setItems(data);
        table.setEditable(true);

        table.setRowFactory(tv
                -> 
                {
                    TableRow<Alignment> row = new TableRow<>();
                    row.setOnMouseClicked((MouseEvent event)
                            -> 
                            {
                                if (!row.isEmpty())
                                {
                                    if (event.isPopupTrigger())
                                    {
                                        Alignment rowData = row.getItem();
                                        ContextMenu cm = new ContextMenu();
                                        MenuItem mmi = new MenuItem("Delete");
                                        mmi.setOnAction((ActionEvent event1)
                                                -> 
                                                {
                                                    data.remove(rowData);
                                                    change = true;
                                        });
                                        cm.getItems().add(mmi);
                                        mmi = new MenuItem("Edit");
                                        mmi.setOnAction((ActionEvent event1)
                                                -> 
                                                {
                                                    AlignmentDialogInput dlg = new AlignmentDialogInput(rowData);
                                                    if (dlg.showModal())
                                                    {
                                                        String img = rowData.getImagenPath();
                                                        String obs = rowData.getComments();
                                                        rowData.set(dlg.alignment);
                                                        rowData.setImagenPath(img);
                                                        rowData.setComments(obs);
                                                        setChange(true);
                                                    }
                                        });
                                        cm.getItems().add(mmi);
                                        mmi = new MenuItem("Copy");
                                        mmi.setOnAction((ActionEvent event1)
                                                -> 
                                                {
                                                    AlignmentDialogInput dlg = new AlignmentDialogInput(rowData);
                                                    if (dlg.showModal())
                                                    {

                                                        data.add(dlg.alignment);
                                                    }
                                        });
                                        cm.getItems().add(mmi);
                                        cm.show(table, event.getScreenX(), event.getScreenY());
                                    }
                                    else if (event.getClickCount() == 1)
                                    {
                                        if (!splitPane.getItems().contains(alignmentPane))
                                        {
                                            splitPane.getItems().add(alignmentPane);
                                        }
                                        Alignment rowData = row.getItem();
                                        alignmentPane.setData(rowData);
                                    }

                                }

                    });
                    return row;
        });

        return this;
    }

    /**
     * 
     * @return True if project exists
     */
    public boolean exists()
    {
        return !workDir.isEmpty();
    }

    /**
     * 
     * @param name Project name
     */
    public Project(String name)
    {
        super(10);
        this.name = name;
        change = false;
    }

    /**
     * 
     * @param name Project name
     * @param workDir Work dir
     */
    public Project(String name, String workDir)
    {
        super(10);
        this.name = name;
        if (!workDir.endsWith(System.getProperty("file.separator")))
        {
            workDir += System.getProperty("file.separator");
        }
        this.workDir = workDir;
        change = false;
    }

    public Project()
    {
        super(10);
        this.name = "";
        change = false;
    }

   

    /**
     * Open project from saved project file 
     * @throws JDOMException XML error
     * @throws IOException IO error
     * @throws ProcessException Format error
     */
    public void open() throws JDOMException, IOException, ProcessException
    {
        parseXML(new File(getWorkDir() + name + ".awb"));
        change = false;
    }


    /**
     * Save project 
     * @param workDir Work directory
     * @throws IOException IO error
     */
    public void save(String workDir) throws IOException
    {
        if (!workDir.endsWith(System.getProperty("file.separator")))
        {
            workDir += System.getProperty("file.separator");
        }
        this.workDir = workDir;
        toXML(new File(workDir + name + ".awb"));
        change = false;
    }

    /**
     * Save project 
     * @throws IOException IO error
     */
    public void save() throws IOException
    {
        if (!workDir.isEmpty())
        {
            if (!workDir.endsWith(System.getProperty("file.separator")))
            {
                workDir += System.getProperty("file.separator");
            }
            toXML(new File(getWorkDir() + name + ".awb"));
            change = false;
        }
    }

    /**
     * 
     * @return Project name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 
     * @param name Project name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Parse XML project
     * @param file XML file
     * @throws JDOMException XML error
     * @throws IOException IO error
     * @throws ProcessException Format error
     */
    public void parseXML(File file) throws JDOMException, IOException, ProcessException
    {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element raiz = document.getRootElement();
        name = raiz.getAttributeValue("nombre");
        List<Element> aa = raiz.getChild("alineamientos").getChildren();
        data.clear();

        for (Element e : aa)
        {
            data.add(new Alignment(e));
        }
    }

    /**
     * Save project as XML file
     * @param file XML file
     * @throws FileNotFoundException File not found error
     * @throws IOException IO error
     * @throws NullPointerException Null pointer error 
     */
    public void toXML(File file) throws FileNotFoundException, IOException, NullPointerException
    {
        Element estudioElemento = new Element("estudio");
        Document doc = new Document(estudioElemento);
        estudioElemento.setAttribute("nombre", name);
        Element aa = new Element("alineamientos");
        estudioElemento.addContent(aa);
        for (int i = 0; i < data.size(); i++)
        {
            Alignment ali = (Alignment) data.get(i);
            aa.addContent(ali.getXMLElement());
        }

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        FileOutputStream out = null;
        try
        {
            xmlOutput.output(doc, out = new FileOutputStream(file));
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
            Project estudio = new Project("prueba");
            estudio.parseXML(new File("estudio.xml"));

        }
        catch (ProcessException | IOException | NullPointerException | JDOMException ex)
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * New alignment
     */
    private void newAlignment()
    {
        Alignment alignment = inputAlignment();
        boolean found = false;
        for (Object d : data)
        {
            Alignment a = (Alignment) d;
            if (alignment.equals(a))
            {
                found = true;
                break;
            }
        }
        if (!found)
        {
            data.add(alignment);
            change = true;                        
        }
    }

    /**
     * @return work directory
     */
    public String getWorkDir()
    {
        return workDir;
    }

    /**
     * @return If true a change has been mada and project needs to be saved before
     * closing
     */
    public boolean isChange()
    {
        return change;
    }

    /**
     * @param change change to set
     */
    public void setChange(boolean change)
    {
        this.change = change;
    }

    /**
     * 
     * @param number
     * @return Factorial of a number
     */
    static private BigDecimal factorial(BigDecimal number)
    {
        if (number.compareTo(BigDecimal.ZERO) == 1)
        {
            BigDecimal m = new BigDecimal(number.longValue()).subtract(BigDecimal.ONE);
            BigDecimal r = new BigDecimal(number.longValue());

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

    /**
     * Fortuitously probality calculation. Number od astronomically meaningful directions = 18
     * @param meaningful number of astronomically meaningful directions
     * @param coincidences Number of coincidences to within +- error of azimuth
     * @param total Number of horizon aligments
     * @param error Azimuth error
     * @return Fortuitously probality of a number of coincidences or more per a total of alignments
     */
    public static double Rosenfeldt(int meaningful,int coincidences, int total, double error)
    {
        BigDecimal P = new BigDecimal(meaningful); 

        BigDecimal N = new BigDecimal(360).divide(new BigDecimal(2).multiply(new BigDecimal(error))); //margen de error
        BigDecimal S = new BigDecimal(total); 

        BigDecimal resultado = BigDecimal.ZERO;

        for (int a = coincidences; a <= total; a++)
        {
            BigDecimal M = new BigDecimal(a);

            BigDecimal num = Project.factorial(P).multiply(Project.factorial(N.subtract(P))).multiply(Project.factorial(S)).multiply(Project.factorial(N.subtract(S)));
            BigDecimal den = Project.factorial(M).multiply(Project.factorial(P.subtract(M))).multiply(Project.factorial(S.subtract(M))).multiply(Project.factorial(N.add(M).subtract(S).subtract(P))).multiply(Project.factorial(N));
            BigDecimal res = num.divide(den, 5, RoundingMode.HALF_UP);
            resultado = resultado.add(res);
        }

        return resultado.doubleValue();
    }

    /**
     * Fortuitously probality calculation by Bernoulli process. 
      * @param meaningful number of astronomically meaningful directions
     * @param coincidences Number of coincidences to within +- error of azimuth
     * @param total Number of horizon aligments
     * @param error Azimuth error
     * @return Fortuitously probality of a number of coincidences or more per a total of alignments
     */
    public static double Bernoulli(int meaningful,int coincidences, int total, double error)
    {
        BigDecimal P = new BigDecimal(meaningful); 
        BigDecimal N = new BigDecimal(360).divide(new BigDecimal(2).multiply(new BigDecimal(error))); //margen de error
        BigDecimal S = new BigDecimal(total); 
        BigDecimal probabilidad = P.divide(N);

        BigDecimal resultado = BigDecimal.ZERO;

        for (int a = coincidences; a <= total; a++)
        {
            BigDecimal M = new BigDecimal(a);

            BigDecimal num = Project.factorial(new BigDecimal(total)).multiply(probabilidad.pow(a)).multiply((BigDecimal.ONE.subtract(probabilidad)).pow(total - a));
            BigDecimal den = Project.factorial(M).multiply(Project.factorial(S.subtract(M)));
            BigDecimal res = num.divide(den, 5, RoundingMode.HALF_UP);
            resultado = resultado.add(res);
        }

        return resultado.doubleValue();
    }

    /**
     * Fortuitously probality calculation
     * @params pc Option Rosenfeldt or Bernoulli
     * @param pc 
     */
    private void fortuitousProbality(FortuitousProbabilityOption pc)
    {
        int total = 0;
        int coincidences = 0;
        for (int i = 0; i < data.size(); i++)
        {
            Alignment al = (Alignment) data.get(i);
            total++;
            DatedAstronomicalEvent fd = checkAstronomicalEvent(al.getComments());
            if (fd != null && fd.getAstronomicalEvent() != null)
            {
                coincidences++;
            }
        }
        double v = 0;
        switch (pc)
        {
            case BERNOULLI:
                v = Bernoulli(18, coincidences, total, 1);
                break;
            case ROSENFELDT:
                v = Rosenfeldt(18, coincidences, total, 1);
                break;
        }

        new MessageDialog(skeleton, String.format("Fortuitously probality %.2f%%", v * 100), MessageDialog.MessageType.INFO).show();

    }

    /**
     * Proobability density estimation using a Gaussian kernel
     */
    private void probabilityDensityEstimation()
    {
        class ProbabilityDensity_Declination
        { 
 
            double probabilityDensity;
            double declination;

            public ProbabilityDensity_Declination(double declination)
            {
                this.declination = declination;
                this.probabilityDensity = 0;
            }

            public void addProbabilityDensity(double f)
            {
                this.probabilityDensity += f;
            }

            public double ProbabilityDensity(double originDeclination, double standardDeviation, int total)
            {
                double d = declination;
                double d0 = originDeclination;
                double s = standardDeviation;
                int n = total;
                return (exp(-pow(d - d0, 2) / (2 * pow(s, 2))) / sqrt(2 * PI)) / (n * s);
            }

        }

        try
        {
            List<AxisChart> axes = new ArrayList<>();
            AxisChart categoria;
            axes.add(categoria = new AxisChart("Density"));
            categoria.configSerieList.add(new SimpleSeriesConfiguration("Density", Color.LIME, 4, "null"));
            SwingChart chart = new SwingChart("", skeleton, axes, "DE");

            GaussianKernelDensityEstimationParameters ifd = inputGaussianKernelDensityEstimationParameters();

            (new TemporalTaskTemplate<Double>("", ifd.getMinimumValue(), ifd.getMaximumValue(), ifd.getStepValue())
            {

                @Override
                public void cloneCurrent(Double input)
                {
                    setCurrent(input);
                }

                @Override
                public double doubleValue(Double input)
                {
                    return input;
                }

                @Override
                public String toString(Double input)
                {
                    return String.format("%.1f", input).replace(",", ".");
                }

                @Override
                public void addToCurrent(Double increment)
                {
                    setCurrent(getCurrent() + increment);
                }

                @Override
                public int compare(Double object, Double reference)
                {
                    if (object < reference)
                    {
                        return -1;
                    }
                    else if (object > reference)
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
                    Tab tab = parent.newTab("Probability density " + name);
                    tab.setContent(chart.getChart());

                }

                @Override
                public void cycle()
                {
                    ProbabilityDensity_Declination couple = new ProbabilityDensity_Declination(getCurrent());

                    for (int i = 0; i < data.size(); i++)
                    {
                        Alignment al = (Alignment) data.get(i);
                        couple.addProbabilityDensity(couple.ProbabilityDensity(
                                al.getDeclination().getSignedValue(),
                                ifd.getStandardDeviation(),
                                data.size()));
                    }

                    chart.addSample(couple.declination, couple.probabilityDensity, "Density");
                }
            }).taskStart();

        }
        catch (ProcessException | CancelExcepcion ex)
        {

        }
    }

    /**
     * Check astronomical event
     * @param description
     * @return 
     */
    static DatedAstronomicalEvent checkAstronomicalEvent(String description)
    {
        AstronomicalEvent f;
        int year;
        if (!description.isEmpty())
        {
            String[] tDes = description.split(",");
            if (tDes.length == 2)
            {
                try
                {
                    year = Integer.valueOf(tDes[1]);
                    f = AstronomicalEvent.fromString(tDes[0]);
                    return new DatedAstronomicalEvent(f, year);
                }
                catch (NumberFormatException ex)
                {

                }
            }
        }
        return null;
    }

}
