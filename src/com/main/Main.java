/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.interfaz.skeleton.MessageDialog;
import com.interfaz.skeleton.Skeleton;
import com.CancelExcepcion;
import com.ProcessException;
import com.components.BordererLabel;
import com.components.GridPane10;
import com.Global;
import com.TemporalTask;
import com.astronomy.JulianDay;
import com.astronomy.Star;
import com.astronomy.InfoMoon;
import com.astronomy.Moon;
import com.astronomy.Planet;
import com.astronomy.Sun;
import com.astronomy.coordinate_system.Equatorial;
import com.astronomy.coordinate_system.Horizontal;
import com.astronomy.coordinate_system.ApparentHorizontal;
import com.astronomy.project.Project;
import com.calendar.CrossQuarterFestivals;
import com.PlanetEnum;
import com.chart.AxisChart;
import com.chart.JulianChart;
import com.chart.SimpleSeriesConfiguration;
import com.CalculusType;
import com.HorizontalTime;
import com.TemporalTaskTemplate;
import com.astronomy.project.Alignment;
import com.astronomy.project.AlignmentDialogInput;
import com.astronomy.project.FortuitousProbabilityOption;
import com.astronomy.project.GaussianKernelDensityEstimationParameters;
import com.astronomy.project.ProbabilityDensityPair;
import static com.astronomy.project.Project.inputGaussianKernelDensityEstimationParameters;
import com.chart.SwingChart;
import static com.interfaz.skeleton.MessageDialog.MessageType.ERROR;
import static com.interfaz.skeleton.MessageDialog.MessageType.INFO;
import static com.interfaz.skeleton.MessageDialog.MessageType.WARNING;
import com.interfaz.skeleton.ModalDialog;
import static com.main.Dialogs.inputDialog;
import static com.main.Dialogs.inputLocalCoordinates;
import static com.main.Dialogs.inputOption;
import static com.main.Dialogs.inputPlanet;
import static com.main.Dialogs.inputStar;
import static com.main.Dialogs.inputVariables;
import static com.main.Dialogs.selectOption;
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
import static java.lang.Math.sqrt;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.log10;
import static java.lang.Math.PI;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import org.jdom2.JDOMException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static com.main.Dialogs.inputTimeInterval;
import static java.lang.Math.abs;
import static com.units.Tools.sine;
import static com.units.Tools.cosine;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import java.util.Arrays;
import java.util.Collections;
import javafx.scene.control.SelectionMode;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Main extends Application
{

    /**
     * main skeleton
     */
    public static Skeleton skeleton;
    /**
     * Main pane
     */
    VBox root;
    /**
     * Current tab pane
     */
    TabPane tp;
    /**
     * Menu bar
     */
    MenuBar menu;

    /**
     * Current latitude
     */
    public static SexagesimalDegree latitude;
    /**
     * Current longitude
     */
    public static SexagesimalDegree longitude;

    Interval interval;

    /**
     * Main stage
     */
    Stage mainStage;

    /**
     * List of projects
     */
    static List<Project> projects = new CopyOnWriteArrayList<>();

    /**
     * Menu item close project
     */
    private MenuItem miCloseProject;
    /**
     * Menu item save project
     */
    private MenuItem miGuardarProyecto;

    /**
     * Tool for converting from Julian day to calendar date
     */
    private final HBox hJulianDay = new HBox(10);

    /**
     * Main
     *
     * @throws ProcessException Format error
     */
    public Main() throws ProcessException
    {
        latitude = SexagesimalDegree.valueOf("42º25'27''");
        longitude = SexagesimalDegree.valueOf("-6º3'00''");
        LocalDateTime ldts = LocalDateTime.parse("01/01/" + String.valueOf(LocalDateTime.now().getYear()) + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        LocalDateTime ldte = ldts.plusYears(1);
        interval = new Interval(new JulianDay(ldts.getDayOfMonth(), ldts.getMonthValue(), ldts.getYear()),
                new JulianDay(ldte.getDayOfMonth(), ldte.getMonthValue(), ldte.getYear()), 1);

    }

    /**
     *
     * @param tabName Tab pane
     * @return new tab
     */
    public Tab newTab(String tabName)
    {
        Tab tab;
        tp.getTabs().add(tab = new Tab(tabName));
        tp.getSelectionModel().select(tab);
        return tab;
    }

    /**
     * Dates of heliacal and acronic rising and setting. It asks for a year, the
     * setting or rising azimuth and the star
     *
     * @param tp Tab pane
     * @throws ProcessException Format error
     */
    private void stellarVisibilityPhenomena(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;
            inputLocalCoordinates();
            String strYear = inputDialog("Year", "");
            String strAcimut = inputDialog("Azimuth of setting or rising star", "");
            Star star = inputStar();
            tab = newTab(star.toString() + " " + strYear);
            tab.setContent(stellarVisibilityPhenomena(Integer.valueOf(strYear), star, Double.valueOf(strAcimut), latitude, longitude));
        }
        catch (NullPointerException ex)
        {
            Global.info.log(ex);
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    /**
     * Chart of equatorial and horizontal coordinates of celestial bodies
     * visible to the naked-eye. It asks for the local geographic coordinates of
     * the observatory site , the variables to plot and the time interval
     *
     * @param tp Tab pane.
     * @throws ProcessException Format error.
     */
    private void celestialCoordinatesChart(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;
            inputLocalCoordinates();
            String[] var = inputVariables();
            interval = inputTimeInterval(interval);
            tab = newTab("Celestial coordinates chart");
            tab.setContent(getCelestialCoordinatesChart(var, interval.getStart(), interval.getEnd(), interval.getIncrement()));
        }
        catch (NullPointerException ex)
        {
            Global.info.log(ex);
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    /**
     * Chart of star and planet coincidence in altitude
     *
     * @param tp Tab pane
     * @throws ProcessException Format error
     */
    private void coincidenceInAltitude(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;
            inputLocalCoordinates();
            interval = inputTimeInterval(false, interval);
            Star star = inputStar();
            Planet planet = inputPlanet();
            tab = newTab(planet.toString() + " & " + star.toString());

            CoincidenceInAltitude conjuncion = new CoincidenceInAltitude(planet.toString() + " & " + star.toString(), star, planet, interval.getStart(), interval.getEnd(), HorizontalTime.valueOf(inputOption((Stream.of(HorizontalTime.values())
                    .map(Enum::name)
                    .collect(Collectors.toList())).toArray(new String[HorizontalTime.values().length]))));

            conjuncion.taskStart();
            tab.setContent(conjuncion.getChart());
        }
        catch (CancelExcepcion ex)
        {

        }
    }

    /**
     * Spring Full Moon calculation
     *
     * @param tp Tab Pane
     * @see MARCIANO DA SILVA, C, The Spring Full Moon, Journal for History of
     * Astronomy, v. 3, 4, pp. 475-478
     * @throws ProcessException
     */
    private void springFullMoon(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputLocalCoordinates();
            String strYear = inputDialog("First year", "-2000");
            String strNumber = inputDialog("Number of years", "10");

            List<AxisChart> axes = new ArrayList<>();
            AxisChart axis;

            axes.add(axis = new AxisChart("Moon phase"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("Phase", Color.ORANGE, 2, "null"));
            axes.add(axis = new AxisChart("Azimuth"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("A Moon", Color.RED, 2, "null"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("A Sun", Color.RED, 1, "null"));

            JulianChart chart = new JulianChart("", axes, "JD");
            final int year = Integer.valueOf(strYear);
            final int n_years = Integer.valueOf(strNumber);

            tab = newTab("Spring Full Moon");

            (new TemporalTask("Moon", new JulianDay(1, 1, year), new JulianDay(1, 1, year + n_years), 1.0)
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay sunSetting;
                int year_ = year;
                Double temp = null;

                //state = 1 illumitated disk >=0.1
                @Override
                public void taskEnd()
                {
                    tab.setContent(chart.getChart());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il_before = Moon.getInfoMoon(getCurrent().minus(new JulianDay(1)));
                        InfoMoon il = Moon.getInfoMoon(getCurrent());
                        InfoMoon il_after = Moon.getInfoMoon(getCurrent().plus(new JulianDay(1)));
                        boolean mark = false;

                        if (il_before.fraction < il.fraction && il_after.fraction < il.fraction)
                        {

                            Equatorial el = Moon.getApparentPosition(getCurrent());
                            Equatorial es = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            SexagesimalDegree Al = SexagesimalDegree.acos(sine(el.getDeclination()) / cosine(latitude));
                            SexagesimalDegree As = SexagesimalDegree.acos(sine(es.getDeclination()) / cosine(latitude));
                            if (temp != null)
                            {
                                if (Al.minus(As).getSignedValue() > 0 && temp < 0)
                                {
                                    chart.addSample(getCurrent().getValue(), il.fraction, "Phase");
                                    chart.addSample(getCurrent().getValue(), Al.getSignedValue(), "A Moon");
                                    chart.addSample(getCurrent().getValue(), As.getSignedValue(), "A Sun");

                                }
                            }
                            temp = Al.minus(As).getSignedValue();
                            addToCurrent(29);
                        }
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    /**
     * First new Moon after solstices, equinoxes or cross-quarter festivals
     *
     * @param tp Tab pane
     * @throws ProcessException Format error
     */
    private void firstNewMoon(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputLocalCoordinates();
            String strYear = inputDialog("First year", "-2000");
            String strNumber = inputDialog("Number of years", "10");

            List<AxisChart> axes = new ArrayList<>();
            AxisChart axis;

            axes.add(axis = new AxisChart("Moon phase"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("Phase", Color.ORANGE, 2, "null"));
            axes.add(axis = new AxisChart("Azimuth"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("A Moon", Color.RED, 2, "null"));
            axes.add(axis = new AxisChart("Altitude"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("h Moon", Color.WHITE, 4, "null"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("h Sun", Color.WHITE, 2, "null"));

            JulianChart chart = new JulianChart("", axes, "DJ");
            final int year = Integer.valueOf(strYear);
            final int n_years = Integer.valueOf(strNumber);

            String[] oo;

            int opcion = selectOption("Reference", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Summer solstice", "Winter solstice", "Spring equinox", "Autumn equinox"
            }, "Winter solstice");
            JulianDay referencia;

            tab = newTab("First Moon " + oo[opcion]);

            CrossQuarterFestivals fme = new CrossQuarterFestivals(year);
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
                    referencia = fme.summerSolstice;
                    break;
                case 5:
                    referencia = fme.winterSolstice;
                    break;
                case 6:
                    referencia = fme.apparentSpringEquinox;
                    break;
                case 7:
                    referencia = fme.apparentAutumnEquinox;
                    break;
                default:
                    return;
            }

            (new TemporalTask("Moon", referencia, new JulianDay(1, 1, year + n_years), 1.0 / (24.0 * 60)) //(new Tarea("Luna", fme.solsticioInvierno, new DiaJuliano(15, 3, año), 1.0 / (24.0 * 10))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay SunSetting;
                int year_ = year;
                int state = 0;

                //state = 1 illumitated disk >=0.1
                @Override
                public void taskEnd()
                {
                    tab.setContent(chart.getChart());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il = Moon.getInfoMoon(getCurrent());
                        boolean marca = false;

                        if (state == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.fraction > d_temp)
                                {
                                    if (il.fraction < 0.1 & il.fraction > 0 && d_temp <= il.fraction)
                                    {
                                        state = 1;
                                    }
                                }
                            }
                            d_temp = il.fraction;
                        }

                        if (state == 2)
                        {
                            Equatorial e = Moon.getApparentPosition(getCurrent());
                            Equatorial ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs;

                            double hPrueba = h.getAltitude().getSignedValue();
                            double arco;
                            SexagesimalDegree elong = Equatorial.getAngularSeparation(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        JulianDay puestaLuna = new JulianDay(getCurrent());
                                        setCurrent(new JulianDay(SunSetting).plus(new JulianDay(4 * (puestaLuna.getValue() - SunSetting.getValue()) / 9)));

                                        e = Moon.getApparentPosition(getCurrent());
                                        ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                                        h = e.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                                        hs = ns.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());
                                        arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                                        elong = Equatorial.getAngularSeparation(e, ns);
                                        il = Moon.getInfoMoon(getCurrent());
                                        double w = il.fraction * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            chart.addSample(getCurrent().getValue(), il.fraction, "Phase");
                                            chart.addSample(getCurrent().getValue(), h.getAzimuth().getSignedValue(), "A Moon");
                                            chart.addSample(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Moon");
                                            chart.addSample(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sun");

                                            year_++;
                                            CrossQuarterFestivals fme = new CrossQuarterFestivals(year_);
                                            JulianDay reference;
                                            switch (opcion)
                                            {
                                                case 0:
                                                    reference = fme.imbolc;
                                                    break;
                                                case 1:
                                                    reference = fme.beltaine;
                                                    break;
                                                case 2:
                                                    reference = fme.lugnasad;
                                                    break;
                                                case 3:
                                                    reference = fme.samain;
                                                    break;
                                                case 4:
                                                    reference = fme.summerSolstice;
                                                    break;
                                                case 5:
                                                    reference = fme.winterSolstice;
                                                    break;
                                                case 6:
                                                    reference = fme.apparentSpringEquinox;
                                                    break;
                                                case 7:
                                                    reference = fme.apparentAutumnEquinox;
                                                    break;
                                                default:
                                                    return;
                                            }
                                            setCurrent(reference);
                                            state = 0;
                                            d_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            state = 1;
                                            setCurrent(new JulianDay(SunSetting.getValue() + 0.9));

                                            h_temp = null;
                                            return;
                                        }
                                    }
                                }
                            }
                            h_temp = hPrueba;
                        }

                        if (state == 1)
                        {
                            Equatorial e = Moon.getApparentPosition(getCurrent());
                            Equatorial ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs = ns.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());

                            double hPrueba = hs.getAltitude().getSignedValue();
                            double arco = h.getAltitude().getSignedValue() - hs.getAltitude().getSignedValue();
                            SexagesimalDegree elong = Equatorial.getAngularSeparation(e, ns);

                            if (h_temp != null)
                            {
                                if (hPrueba < h_temp)
                                {
                                    if (hPrueba < 0 && h_temp >= 0)
                                    {
                                        SunSetting = new JulianDay(getCurrent());
                                        state = 2;
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
                        Global.info.log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    /**
     * Last Moon after solstices, equinoxes or cross-quarter festivals
     *
     * @param tp tab pane
     * @throws ProcessException Format error
     */
    private void lastMoon(TabPane tp) throws ProcessException
    {
        try
        {
            Tab tab;

            inputLocalCoordinates();
            String strYear = inputDialog("First year", "-2000");
            String strNumber = inputDialog("Number of years", "10");

            List<AxisChart> axes = new ArrayList<>();
            AxisChart axis;

            axes.add(axis = new AxisChart("Moon phase"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("Phase", Color.ORANGE, 2, "null"));
            axes.add(axis = new AxisChart("Azimuth"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("A Moon", Color.RED, 2, "null"));
            axes.add(axis = new AxisChart("Altitude"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("h Moon", Color.WHITE, 4, "null"));
            axis.configSerieList.add(new SimpleSeriesConfiguration("h Sun", Color.WHITE, 2, "null"));

            JulianChart chart = new JulianChart("", axes, "JD");
            final int year = Integer.valueOf(strYear);
            final int n_years = Integer.valueOf(strNumber);

            String[] oo;

            int opcion = selectOption("Reference", oo = new String[]
            {
                "Imbolc", "Beltaine", "Lugnasad", "Samain", "Summer solstice", "Winter solstice", "Spring equinox", "Autumn equinox"
            }, "Winter solstice");
            JulianDay reference;

            tab = newTab("Last Moon " + oo[opcion]);

            CrossQuarterFestivals fme = new CrossQuarterFestivals(year);
            switch (opcion)
            {
                case 0:
                    reference = fme.imbolc;
                    break;
                case 1:
                    reference = fme.beltaine;
                    break;
                case 2:
                    reference = fme.lugnasad;
                    break;
                case 3:
                    reference = fme.samain;
                    break;
                case 4:
                    reference = fme.summerSolstice;
                    break;
                case 5:
                    reference = fme.winterSolstice;
                    break;
                case 6:
                    reference = fme.apparentSpringEquinox;
                    break;
                case 7:
                    reference = fme.apparentAutumnEquinox;
                    break;
                default:
                    return;
            }

            (new TemporalTask("Moon", reference, new JulianDay(1, 1, year + n_years), 1.0 / (24.0 * 60))
            {
                Double h_temp = null;
                Double h_temp2 = null;
                Double d_temp = null;
                JulianDay MoonSettingTemp;
                int year_ = year;
                int state = 0;

                @Override
                public void taskEnd()
                {
                    tab.setContent(chart.getChart());
                }

                @Override
                public void cycle()
                {
                    try
                    {
                        InfoMoon il = Moon.getInfoMoon(getCurrent());
                        boolean marca = false;

                        if (state == 0)
                        {
                            if (d_temp != null)
                            {
                                if (il.fraction > d_temp)
                                {
                                    if (il.fraction < 0.1 & il.fraction > 0 && d_temp <= il.fraction)
                                    {
                                        state = 1;
                                        addToCurrent(-10);
                                    }
                                }
                            }
                            d_temp = il.fraction;
                        }

                        if (state == 1)
                        {
                            Equatorial e = Moon.getApparentPosition(getCurrent());
                            Equatorial ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                            ApparentHorizontal h = e.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                            ApparentHorizontal hs = ns.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());

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
                                        il = Moon.getInfoMoon(getCurrent());
                                        double w = il.fraction * 15;
                                        double v = arco - (-0.1018 * pow(w, 3) + 0.7319 * pow(w, 2) - 6.3226 * w + 7.1651);

                                        if (v > 5.65)
                                        {
                                            MoonSettingTemp = new JulianDay(getCurrent());
                                            addToCurrent(0.9);
                                            h_temp = null;
                                            return;
                                        }
                                        else
                                        {
                                            setCurrent(MoonSettingTemp);
                                            e = Moon.getApparentPosition(getCurrent());
                                            ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                                            h = e.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.MOON, getCurrent());
                                            hs = ns.toHorizontal(latitude, longitude, getCurrent()).toApparent(PlanetEnum.SUN, getCurrent());
                                            elong = Equatorial.getAngularSeparation(e, ns);
                                            il = Moon.getInfoMoon(getCurrent());

                                            chart.addSample(getCurrent().getValue(), il.fraction, "Phase");
                                            chart.addSample(getCurrent().getValue(), h.getAzimuth().getSignedValue(), "A Moon");
                                            chart.addSample(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Moon");
                                            chart.addSample(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sun");

                                            year_++;
                                            CrossQuarterFestivals fme = new CrossQuarterFestivals(year_);
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
                                                    referencia = fme.summerSolstice;
                                                    break;
                                                case 5:
                                                    referencia = fme.winterSolstice;
                                                    break;
                                                case 6:
                                                    referencia = fme.apparentSpringEquinox;
                                                    break;
                                                case 7:
                                                    referencia = fme.apparentAutumnEquinox;
                                                    break;
                                                default:
                                                    return;
                                            }
                                            setCurrent(referencia);
                                            state = 0;
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
                        Global.info.log(ex);
                    }

                }
            }).taskStart();
        }
        catch (CancelExcepcion ex)
        {

        }

    }

    /**
     * New project
     *
     * @param tp Tab pane
     * @return True if success
     * @throws IOException IO error
     */
    private boolean newProject(TabPane tp) throws IOException
    {
        String name = inputDialog("Project name", "example");
        if (name.isEmpty())
        {
            return false;
        }
        Project project;
        projects.add(project = new Project(name));
        project.saveProject();
        Tab tab = newTab("Project " + name);
        tab.setContent(project.getInterface(Main.this));
        tab.setClosable(true);
        tab.setOnClosed((Event t)
                -> 
                {
                    Project active = (Project) tab.getContent();

                    if (active.isChange())
                    {
                        if (new MessageDialog(skeleton, "Do yo want to save project " + name + "?", WARNING).show())
                        {
                            try
                            {
                                active.save();
                            }
                            catch (IOException ex)
                            {

                            }
                        }
                    }
                    for (Project e : projects)
                    {
                        if (e.equals(tab.getContent()))
                        {
                            projects.remove(e);
                        }
                    }
        });

        return true;

    }

    /**
     * Open project
     *
     * @param tp Tab pane
     * @return True if success
     */
    private boolean openProject(TabPane tp)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archaeoastronomy Work Benche files", "*.awb"));
        File file = fileChooser.showOpenDialog(mainStage);

        if (file == null)
        {
            return false;
        }
        Project project;
        projects.add(project = new Project(file.getName().replace(".awb", ""), file.getParentFile().getAbsolutePath()));
        try
        {
            project.open();

            Tab tab = newTab("Project " + project.getName());
            tab.setContent(project.getInterface(Main.this));
            tab.setClosable(true);
            tab.setOnClosed((Event t)
                    -> 
                    {
                        for (Project e : projects)
                        {
                            if (e.equals(tab.getContent()))
                            {
                                if (e.isChange())
                                {
                                    if (new MessageDialog(skeleton, "Do yo want to save project " + e.getName() + "?", WARNING).show())
                                    {
                                        try
                                        {
                                            e.save();
                                        }
                                        catch (IOException ex)
                                        {

                                        }
                                    }
                                }

                                projects.remove(e);
                            }
                        }
            });

            return true;
        }
        catch (JDOMException ex)
        {
            new MessageDialog(skeleton, "I can´t understand this XML format", ERROR).show();
        }
        catch (IOException ex)
        {
            new MessageDialog(skeleton, "There is a problem with this project file", ERROR).show();
        }
        catch (ProcessException ex)
        {
            new MessageDialog(skeleton, "There is an error with the XML format of this project file", ERROR).show();
        }
        return false;
    }

    /**
     *
     * @param year Year
     * @param star Star
     * @param azimuth Azimuth
     * @param latitude Geographical latitude of observatory
     * @param longitude Geographical longitude of observatory
     * @return
     * @throws ProcessException
     */
    public Node stellarVisibilityPhenomena(int year, Star star, double azimuth, SexagesimalDegree latitude, SexagesimalDegree longitude) throws ProcessException
    {
        List<AxisChart> axes = new ArrayList<>();
        AxisChart axis;
        /* axes.add(axis = new AxisChart("Brightness"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("Schaefer standard", Color.YELLOW, 4, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("Schaefer non-standard", Color.BEIGE, 4, "null"));*/

        axes.add(axis = new AxisChart("Altitude"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("h Star", Color.RED, 2, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("h Sun", Color.RED, 4, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("horizon", Color.WHITE, 2, "null"));
        axes.add(axis = new AxisChart("Azimuth"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("A Star", Color.LIGHTGREEN, 2, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("A Sun", Color.LIGHTGREEN, 4, "null"));
        axes.add(axis = new AxisChart("Declination"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("DE Star", Color.ORANGE, 2, "null"));
        axes.add(axis = new AxisChart("Apparent magnitude"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("m Critical", Color.CYAN, 4, "null"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("m Star", Color.CYAN, 2, "null"));
        axes.add(axis = new AxisChart("Calendar"));
        axis.configSerieList.add(new SimpleSeriesConfiguration("festivals", Color.WHITE, 4, "null"));

        JulianChart chart = new JulianChart("", axes, "JD");
        chart.AxesList.get(chart.AxesList.size() - 1).setVisible(false);

        (new TemporalTask("Stellar visibility phenomena", new JulianDay(1, 1, year), new JulianDay(1, 1, year + 1), 1.0 / (24 * 60))
        {
            Double a_temp = null;

            @Override
            public void taskEnd()
            {
                try
                {
                    CrossQuarterFestivals fme = new CrossQuarterFestivals(year);

                    chart.addSample(fme.winterSolstice.getValue(), 0, "festivals");
                    chart.addSample(fme.winterSolstice.getValue(), 1, "festivals");
                    chart.addSample(fme.winterSolstice.getValue(), Double.NaN, "festivals");
                    chart.addSample(fme.summerSolstice.getValue(), 0, "festivals");
                    chart.addSample(fme.summerSolstice.getValue(), 1, "festivals");
                    chart.addSample(fme.summerSolstice.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.imbolc.getValue(), 0, "festivals");
                    chart.addSample(fme.imbolc.getValue(), 1, "festivals");
                    chart.addSample(fme.imbolc.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.beltaine.getValue(), 0, "festivals");
                    chart.addSample(fme.beltaine.getValue(), 1, "festivals");
                    chart.addSample(fme.beltaine.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.lugnasad.getValue(), 0, "festivals");
                    chart.addSample(fme.lugnasad.getValue(), 1, "festivals");
                    chart.addSample(fme.lugnasad.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.samain.getValue(), 0, "festivals");
                    chart.addSample(fme.samain.getValue(), 1, "festivals");
                    chart.addSample(fme.samain.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.apparentSpringEquinox.getValue(), 0, "festivals");
                    chart.addSample(fme.apparentSpringEquinox.getValue(), 1, "festivals");
                    chart.addSample(fme.apparentSpringEquinox.getValue(), Double.NaN, "festivals");

                    chart.addSample(fme.apparentAutumnEquinox.getValue(), 0, "festivals");
                    chart.addSample(fme.apparentAutumnEquinox.getValue(), 1, "festivals");
                    chart.addSample(fme.apparentAutumnEquinox.getValue(), Double.NaN, "festivals");
                }
                catch (ProcessException ex)
                {
                    Global.info.log(ex);
                }
            }

            @Override
            public void cycle()
            {
                try
                {
                    Equatorial ne = star.getApparentPosition(getCurrent(), CalculusType.PRECISE);
                    Equatorial ns = Sun.getApparentEquatorialPosition(getCurrent(), CalculusType.PRECISE);
                    Horizontal h = ne.toHorizontal(latitude, longitude, getCurrent());
                    Horizontal hs = ns.toHorizontal(latitude, longitude, getCurrent());
                    double hEstrella = h.getAltitude().getSignedValue();
                    double AEstrella = h.getAzimuth().getValue();

                    boolean marca = false;

                    if (a_temp != null && abs(AEstrella - a_temp) < 10)
                    {
                        if (AEstrella > a_temp)
                        {
                            if (AEstrella >= azimuth && a_temp < azimuth)
                            {
                                marca = true;
                            }
                        }
                    }
                    if (marca)
                    {

                        chart.addSample(getCurrent().getValue(), h.getAzimuth().getValue(), "A Star");
                        chart.addSample(getCurrent().getValue(), hs.getAzimuth().getValue(), "A Sun");
                        chart.addSample(getCurrent().getValue(), 0, "horizon");
                        chart.addSample(getCurrent().getValue(), h.getAltitude().getSignedValue(), "h Star");
                        chart.addSample(getCurrent().getValue(), hs.getAltitude().getSignedValue(), "h Sun");
                        chart.addSample(getCurrent().getValue(), ne.getDeclination().getSignedValue(), "DE Star");

                        chart.addSample(getCurrent().getValue(), star.apparentMagnitude, "m Star");
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

                        /*if (bst >= bst_umbral)
                        {
                            log10_C = -8.35;
                            K = pow(10, -5.9);
                        }*/
                        double Y = -0.2 * (mz + 16.57 + kv + 2.5 * log10_C);
                        double b = pow(pow(10, Y) - 1, 2) / K + kv / 0.2 * (bst - 118);

                        double C = pow(10.0, log10_C);
                        double i;
                        i = C * pow(1 + sqrt(K * b), 2);

                        double X = 1 / (cos(Z) + 0.025 * exp(-11 * cos(Z)));
                        double m = -16.57 - 2.5 * log10(i) - kv * X;
                        double logb = log10(b);

                        //chart.addSample(getCurrent().getValue(), log10_bst, "Schaefer standard");
                        //chart.addSample(getCurrent().getValue(), logb, "Schaefer non-standard");
                        chart.addSample(getCurrent().getValue(), m, "m Critical");

                        /*if (m > -5)
                        {
                           
                        }
                        else
                        {
                            chart.addSample(getCurrent().getValue(), Double.NaN, "m Critical");
                        }*/
                    }
                    a_temp = AEstrella;

                }
                catch (ProcessException ex)
                {
                    Global.info.log(ex);
                }

            }
        }).taskStart();

        return chart.getChart();
    }

    /**
     * Chart of equatorial and horizontal coordinates of celestial bodies
     * visible to the naked-eye. It asks for the local geographic coordinates of
     * the observatory site , the variables to plot and the time interval
     *
     * @param variables Equatorial and horizontal coordinates of celestial
     * bodies
     * @param start Interval start
     * @param end Interval end
     * @param increment Increment
     * @return Chart
     * @throws ProcessException Format error
     */
    public Node getCelestialCoordinatesChart(String[] variables, JulianDay start, JulianDay end, double increment) throws ProcessException
    {
        CelestialCoordinatesChartTask celestialBody;
        (celestialBody = new CelestialCoordinatesChartTask(variables, start, end, increment)
        {

        }).taskStart();

        return celestialBody.chart.getChart();
    }

    @Override
    public void start(Stage primaryStage) throws ProcessException
    {
        mainStage = primaryStage;
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/resources/Icon.png")));
        skeleton = new Skeleton(System.getProperty("user.home") + System.getProperty("file.separator") + "AWB", "awb", primaryStage, null);
        root = new VBox();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Skeleton.class.getResource("general.css").toExternalForm());
        skeleton.setScene(scene);
        primaryStage.setTitle("Archaeoastronomy Workbench");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        root.getStyleClass().add("fondo");
        primaryStage.show();

        HBox h = new HBox(10);
        TextField tf = new TextField();
        Label DiaJulianoLabel = new Label();
        tf.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1)
                -> 
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
        hJulianDay.getChildren().addAll(new Label("Julian day"), tf, DiaJulianoLabel);

        root.getChildren().add(menu = new MenuBar());
        root.getChildren().add(tp = new TabPane());

        VBox.setVgrow(tp, Priority.ALWAYS);

        Menu m;
        MenuItem mi;
        menu.getMenus().add(m = new Menu("Project"));

        mi = new MenuItem("New project");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    try
                    {
                        newProject(tp);
                    }
                    catch (IOException ex)
                    {
                        Global.info.log(ex);
                    }
        });
        mi = new MenuItem("Open project");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent event)
                -> 
                {
                    openProject(tp);
        });

        menu.getMenus().add(m = new Menu("Simulation"));
        mi = new MenuItem("Stellar visivility phenomena");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        stellarVisibilityPhenomena(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        mi = new MenuItem("Celestial coordinates chart");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        celestialCoordinatesChart(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        Menu mLuna = new Menu("Moon");
        m.getItems().add(mLuna);

        mi = new MenuItem("First Moon");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        firstNewMoon(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        mi = new MenuItem("Last Moon");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        lastMoon(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        mi = new MenuItem("Spring full Moon");
        mLuna.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        springFullMoon(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        mi = new MenuItem("Coincidence in altitude planet-star");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    try
                    {
                        coincidenceInAltitude(tp);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        menu.getMenus().add(m = new Menu("Tools"));
        CheckMenuItem cmi = new CheckMenuItem("Julian day");
        m.getItems().add(cmi);
        cmi.setOnAction((ActionEvent t)
                -> 
                {

                    if (cmi.isSelected())
                    {
                        root.getChildren().add(hJulianDay);
                    }
                    else
                    {
                        root.getChildren().remove(hJulianDay);
                    }

        });

        mi = new MenuItem("Calendar");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    String strYear = inputDialog("Year", "");
                    try
                    {
                        CrossQuarterFestivals fme = new CrossQuarterFestivals(Integer.valueOf(strYear));
                        GridPane10 grid = new GridPane10();
                        grid.add(new Label("Cross-quarter gestival which marks the end of winter (Imbolc)"), 0, 0);
                        grid.add(new Label("Spring Apparent equinox"), 0, 1);
                        grid.add(new Label("Cross-quarter gestival which marks the beginning of summer (Beltaine)"), 0, 2);
                        grid.add(new Label("Summer solstice"), 0, 3);
                        grid.add(new Label("Cross-quarter gestival which marks the end of summer (Lugnasad)"), 0, 4);
                        grid.add(new Label("Autumn Apparent equinox"), 0, 5);
                        grid.add(new Label("Cross-quarter gestival which marks the beginning of winter (Samain)"), 0, 6);
                        grid.add(new Label("Winter solstice"), 0, 7);

                        grid.add(new BordererLabel(fme.imbolc.getSimpleDate().toString()), 1, 0);
                        grid.add(new BordererLabel(fme.apparentSpringEquinox.getSimpleDate().toString()), 1, 1);
                        grid.add(new BordererLabel(fme.beltaine.getSimpleDate().toString()), 1, 2);
                        grid.add(new BordererLabel(fme.summerSolstice.getSimpleDate().toString()), 1, 3);
                        grid.add(new BordererLabel(fme.lugnasad.getSimpleDate().toString()), 1, 4);
                        grid.add(new BordererLabel(fme.apparentAutumnEquinox.getSimpleDate().toString()), 1, 5);
                        grid.add(new BordererLabel(fme.samain.getSimpleDate().toString()), 1, 6);
                        grid.add(new BordererLabel(fme.winterSolstice.getSimpleDate().toString()), 1, 7);

                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.imbolc, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 0);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.apparentSpringEquinox, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 1);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.beltaine, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 2);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.summerSolstice, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 3);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.lugnasad, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 4);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.apparentAutumnEquinox, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 5);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.samain, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 6);
                        grid.add(new BordererLabel(String.format("%.1f", Sun.getApparentEquatorialPosition(fme.winterSolstice, CalculusType.PRECISE).getDeclination().getSignedValue())), 2, 7);

                        newTab("Calendar " + strYear).setContent(grid);
                    }
                    catch (ProcessException ex)
                    {
                        Global.info.log(ex);
                    }
        });

        mi = new MenuItem("Alignment");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    AlignmentDialogInput dlg = new AlignmentDialogInput(null);
                    dlg.setForesightName("Foresight");
                    dlg.setObservatoryName("Observatory");
                    dlg.boxButtons.getChildren().remove(dlg.btnOK);
                    dlg.btnCancel.setText("Close");
                    dlg.showModal();

        });

        mi = new MenuItem("Probability of fortuitous astronomical coincidences from a number of alignments");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    TextField tfMeaningful = new TextField("18");
                    TextField tfCoincidences = new TextField("0");
                    TextField tfTotal = new TextField("0");
                    TextField tfError = new TextField("1");
                    Label lProbability = new Label();
                    ComboBox cbOptions = new ComboBox(FXCollections.observableArrayList(FortuitousProbabilityOption.values()));
                    cbOptions.getSelectionModel().select(0);

                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(10, 10, 10, 10));
                    gridPane.add(new Label("Number of astronomically meaningful directions"), 0, 0);
                    gridPane.add(tfMeaningful, 1, 0);
                    gridPane.add(new Label("Number of coincidences to within +- error of azimuth"), 0, 1);
                    gridPane.add(tfCoincidences, 1, 1);
                    gridPane.add(new Label("Number of horizon aligments"), 0, 2);
                    gridPane.add(tfTotal, 1, 2);
                    gridPane.add(new Label("Azimuth error in sexagesimal degrees"), 0, 3);
                    gridPane.add(tfError, 1, 3);
                    gridPane.add(new Label("Option"), 0, 4);
                    gridPane.add(cbOptions, 1, 4);
                    gridPane.add(new Label("Probability of fortuitous astronomical coincidences"), 0, 5);
                    gridPane.add(lProbability, 1, 5);

                    ChangeListener cll = (ChangeListener) (ObservableValue observable, Object oldValue, Object newValue)
                            -> 
                            {
                                try
                                {
                                    int meaningful = Integer.valueOf(tfMeaningful.getText());
                                    int coincidences = Integer.valueOf(tfCoincidences.getText());
                                    int total = Integer.valueOf(tfTotal.getText());
                                    double error = Double.valueOf(tfError.getText().replace(",", "."));
                                    Double probability = Double.NaN;
                                    switch ((FortuitousProbabilityOption) cbOptions.getSelectionModel().getSelectedItem())
                                    {
                                        case BERNOULLI:
                                            probability = Project.Bernoulli(meaningful, coincidences, total, error);
                                            lProbability.setText(String.format("%.1f", probability * 100) + "%");
                                            break;
                                        case ROSENFELDT:

                                            probability = Project.Rosenfeldt(meaningful, coincidences, total, error);
                                            lProbability.setText(String.format("%.1f", probability * 100) + "%");
                                            break;
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    lProbability.setText("Error");
                                }
                    };

                    tfMeaningful.textProperty().addListener(cll);
                    tfCoincidences.textProperty().addListener(cll);
                    tfTotal.textProperty().addListener(cll);
                    tfError.textProperty().addListener(cll);
                    cbOptions.valueProperty().addListener(cll);

                    ModalDialog dialogo = new ModalDialog(skeleton, gridPane, true);
                    dialogo.boxButtons.getChildren().remove(dialogo.btnOK);
                    dialogo.btnCancel.setText("Close");
                    dialogo.showModal();

        });

        mi = new MenuItem("Probability density estimation");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    ObservableList<Double> values = FXCollections.observableArrayList();
                    ListView<Double> valueList = new ListView<>(values);
                    valueList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    Button bAdd = new Button("Add value");
                    Button bRemove = new Button("Remove value");
                    bAdd.setOnAction((ActionEvent event)
                            -> 
                            {
                                try
                                {
                                    Arrays.asList(inputDialog("Comma separated values", "").split(";")).stream().map(s -> Double.valueOf(s)).forEach(v -> values.add(v));                                    
                                }
                                catch (NumberFormatException ex)
                                {

                                }
                    });
                    bRemove.setOnAction((ActionEvent event)
                            -> 
                            {
                                valueList.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList()).forEach(v -> values.remove(v));                                    
                                
                    });

                    ModalDialog dialogo = new ModalDialog(skeleton, new VBox(valueList), true);

                    dialogo.boxButtons.getChildren().add(0, bRemove);
                    dialogo.boxButtons.getChildren().add(0, bAdd);
                    dialogo.btnCancel.setText("Close");
                    if (dialogo.showModal())
                    {
                        probabilityDensityEstimation("", values);
                    }

        });

        menu.getMenus().add(m = new Menu("Help"));
        mi = new MenuItem("Help");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    WebView webView = new WebView();
                    WebEngine webEngine = webView.getEngine();
                    String urlHelp = getClass().getResource("/com/resources/help.html").toExternalForm();
                    webEngine.load(urlHelp);
                    Tab tab = newTab("Help");
                    tab.setContent(webView);

                    webEngine.setCreatePopupHandler((PopupFeatures config)
                            -> 
                            {
                                WebView wv = new WebView();
                                Tab tab2 = newTab("Help");
                                tab2.setContent(wv);
                                return wv.getEngine();
                    });
        });

        mi = new MenuItem("About");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    new MessageDialog(skeleton, "Archaeoastronomy Work Bench 2016", INFO).show();
        });

        mi = new MenuItem("Full screen");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    mainStage.setFullScreen(true);
        });

        mi = new MenuItem("Close");
        m.getItems().add(mi);
        mi.setOnAction((ActionEvent t)
                -> 
                {
                    mainStage.close();
        });

        mainStage.setFullScreen(true);
    }

    /**
     * // * Proobability density estimation using a Gaussian kernel
     *
     * @param name Name
     * @param values List of observation values
     */
    public void probabilityDensityEstimation(String name, List<Double> values)
    {

        try
        {
            List<AxisChart> axes = new ArrayList<>();
            AxisChart categoria;
            axes.add(categoria = new AxisChart("Density"));
            categoria.configSerieList.add(new SimpleSeriesConfiguration("Density", Color.LIME, 4, "null"));
            SwingChart chart = new SwingChart("", skeleton, axes, "DE");

            GaussianKernelDensityEstimationParameters ifd = inputGaussianKernelDensityEstimationParameters(1,ceil(Collections.min(values)-0.2*(Collections.max(values)-Collections.min(values))), floor(Collections.max(values)-0.2*(Collections.max(values)-Collections.min(values))),1);

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
                    Tab tab = newTab("Probability density " + name);
                    tab.setContent(chart.getChart());

                }

                @Override
                public void cycle()
                {
                    ProbabilityDensityPair pair = new ProbabilityDensityPair(getCurrent());

                    for (int i = 0; i < values.size(); i++)
                    {
                        pair.updateDensity(values.get(i),
                                ifd.getStandardDeviation(),
                                values.size());
                    }

                    chart.addSample(pair.getValue(), pair.getProbabilityDensity(), "Density");
                }
            }).taskStart();

        }
        catch (ProcessException | CancelExcepcion ex)
        {

        }
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
