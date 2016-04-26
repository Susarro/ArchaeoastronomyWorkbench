/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.Global;
import com.main.Main;
import com.components.ImageViewPane;
import com.components.BordererLabel;
import com.components.Led;
import com.astronomy.JulianDay;
import com.astronomy.Disk;
import com.astronomy.Sun;
import com.calendar.CrossQuarterFestivals;
import static com.PlanetEnum.MOON;
import static com.PlanetEnum.SUN;
import com.CalculusType;
import static com.CalculusType.PRECISE;
import static com.main.Main.skeleton;
import com.units.SexagesimalDegree;
import static com.units.SexagesimalDegree.cos;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import static java.lang.Math.abs;

/**
 * Alignment tab pane
 *
 * @author MIGUEL_ANGEL
 */
public class AlignmentPane extends TabPane
{

    /**
     * Current project
     */
    private final Project currentProject;

    /**
     * Main
     */
    private final Main main;

    /**
     * Alignment image view
     */
    ImageView imageView = null;
    /**
     * Alignment
     */
    private Alignment alignment;

    /**
     * Julian day
     */
    JulianDay JulianDay;
    /**
     * Text field for current year
     */
    private TextField tfYear;
    /**
     * Combo box
     */
    private ComboBox comboBox;

    /**
     * Details tab
     */
    Tab tabDetails = new Tab();
    /**
     * Validation tab
     */
    Tab tabValidation = new Tab();
    /**
     * Label for observatory name
     */
    BordererLabel labelObservatoryName = new BordererLabel();
    /**
     * Label for observatory coordinates
     */
    BordererLabel labelObservatoryCoordinates = new BordererLabel();
    /**
     * Label for foresight name
     */
    BordererLabel labelForesightName = new BordererLabel();
    /**
     * Label for foresight coordinates
     */
    BordererLabel labelForesightCoordinates = new BordererLabel();
    /**
     * Label for azimuth
     */
    BordererLabel labelAzimuth = new BordererLabel();
    /**
     * Label for azimuth
     */
    BordererLabel labelAltitude = new BordererLabel();
    /**
     * Label for declination
     */
    BordererLabel labelDeclination = new BordererLabel();

    /**
     *
     * @param alignment Algnment to set
     */
    public void setData(Alignment alignment)
    {
        this.alignment = alignment;
        labelObservatoryName.setText(alignment.getObservatory().getName());
        labelForesightName.setText(alignment.getForesight().getName());
        if (alignment.getObservatory().getCoordinates() != null)
        {
            labelObservatoryCoordinates.setText(alignment.getObservatory().getCoordinates().toString());
        }
        else
        {
            labelObservatoryCoordinates.setText("");
        }
        if (alignment.getForesight().getCoordinates() != null)
        {
            labelForesightCoordinates.setText(alignment.getForesight().getCoordinates().toString());
        }
        else
        {
            labelForesightCoordinates.setText("");
        }
        labelAzimuth.setText(alignment.getOrientation().getAzimuth().toString());
        labelAltitude.setText(String.format("%.1fº", alignment.getOrientation().getAltitude().getSignedValue()).replace(",", "."));
        labelDeclination.setText(alignment.getDeclination().toString());
        if (alignment.getImagenPath().isEmpty())
        {
            imageView.setImage(null);
        }
        else
        {
            try
            {
                File f = new File(currentProject.getWorkDir()
                        + System.getProperty("file.separator")
                        + "imagenes"
                        + System.getProperty("file.separator")
                        + alignment.getImagenPath());
                BufferedInputStream bis;
                Image image = new Image(bis = new BufferedInputStream(new FileInputStream(currentProject.getWorkDir() + "imagenes" + System.getProperty("file.separator") + f.getName())));
                this.imageView.setImage(image);
                bis.close();
            }
            catch (IOException ex)
            {
                Global.info.log(ex);
            }
        }

        comboBox.getSelectionModel().clearSelection();

        DatedAstronomicalEvent f = Project.checkAstronomicalEvent(alignment.getComments());
        if (f != null)
        {
            tfYear.setText(String.valueOf(f.getYear()));
            if (f.getAstronomicalEvent() != null)
            {
                comboBox.getSelectionModel().select(f.getAstronomicalEvent());
            }
        }
    }

    /**
     * Calculates declinations for solstices, equinoxes, cross-quarter festivasl
     * and lunar standstills for the given year
     *
     * @param year Year to update
     */
    public void updateYear(int year)
    {
        try
        {
            CrossQuarterFestivals fme = new CrossQuarterFestivals(year);
            JulianDay = fme.summerSolstice;
            AstronomicalEvent.BELTAINE_LUGNASAD.setDeclination(new SexagesimalDegree((Sun.getApparentEquatorialPosition(fme.beltaine, CalculusType.PRECISE).getDeclination().getSignedValue()
                    + Sun.getApparentEquatorialPosition(fme.lugnasad, CalculusType.PRECISE).getDeclination().getSignedValue()) / 2));
            AstronomicalEvent.IMBOLC_SAMAIN.setDeclination(new SexagesimalDegree((Sun.getApparentEquatorialPosition(fme.imbolc, CalculusType.PRECISE).getDeclination().getSignedValue()
                    + Sun.getApparentEquatorialPosition(fme.samain, CalculusType.PRECISE).getDeclination().getSignedValue()) / 2));
            AstronomicalEvent.WINTER_SOLSTICE.setDeclination(new SexagesimalDegree(Sun.getApparentEquatorialPosition(fme.winterSolstice, CalculusType.PRECISE).getDeclination().getSignedValue()));
            AstronomicalEvent.SUMMER_SOLSTICE.setDeclination(new SexagesimalDegree(Sun.getApparentEquatorialPosition(fme.summerSolstice, CalculusType.PRECISE).getDeclination().getSignedValue()));
            AstronomicalEvent.NORTHERN_LUNAR_MAJOR_STANDSTILL.setDeclination(JulianDay.getTrueObliquityEcliptic(PRECISE).plus(new SexagesimalDegree(5.145)));
            AstronomicalEvent.SOUTHERN_LUNAR_MAJOR_STANDSTILL.setDeclination(SexagesimalDegree.negative(JulianDay.getTrueObliquityEcliptic(PRECISE)).minus(new SexagesimalDegree(5.145)));
            AstronomicalEvent.NORTHERN_LUNAR_MINOR_STANDSTILL.setDeclination(JulianDay.getTrueObliquityEcliptic(PRECISE).minus(new SexagesimalDegree(5.145)));
            AstronomicalEvent.SOUTHERN_LUNAR_MINOR_STANDSTILL.setDeclination(SexagesimalDegree.negative(JulianDay.getTrueObliquityEcliptic(PRECISE)).plus(new SexagesimalDegree(5.145)));

        }
        catch (ProcessException ex)
        {

        }
    }

    /**
     * Interface for validation. Checks if an alignmente corresponds to an
     * astronomical event (solstices, equinoxes,...) within an error margin
     */
    private void validationInterface()
    {
        VBox vbox = new VBox(10);
        HBox hbox = new HBox(10);
        Label lError = new Label("");
        lError.setAlignment(Pos.CENTER);
        lError.setMaxWidth(Double.MAX_VALUE);
        GridPane rejilla = new GridPane();
        rejilla.setHgap(10);
        rejilla.setVgap(10);
        rejilla.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(10);

        rejilla.getColumnConstraints().addAll(col1, col2, col3);

        comboBox = new ComboBox(FXCollections.observableArrayList(
                AstronomicalEvent.values()));
        tfYear = new TextField("-2000");
        updateYear(Integer.valueOf(tfYear.getText()));
        comboBox.setMaxWidth(Double.MAX_VALUE);
        tfYear.setMaxWidth(Double.MAX_VALUE);

        Led ledValidation = new Led();
        ledValidation.setPrefWidth(30);
        ledValidation.setPrefHeight(30);

        rejilla.add(new Label("Astronomical event"), 0, 0);
        rejilla.add(comboBox, 0, 1);
        rejilla.add(new Label("Year"), 1, 0);
        rejilla.add(tfYear, 1, 1);
        rejilla.add(ledValidation, 2, 1);
        rejilla.add(lError, 2, 2);

        vbox.getChildren().addAll(rejilla);
        tabValidation.setContent(vbox);

        comboBox.setOnAction((Event event)
                -> 
                {
                    SexagesimalDegree variacionAcimut;
                    try
                    {
                        AstronomicalEvent astEvent = (AstronomicalEvent) comboBox.getSelectionModel().getSelectedItem();
                        if (astEvent == null)
                        {
                            lError.setText("");
                            ledValidation.setOn(false);
                            return;
                        }
                        SexagesimalDegree astronomicalDeclination = null;
                        switch (astEvent)
                        {
                            case NONE:
                                lError.setText("");
                                ledValidation.setOn(false);
                                alignment.setComments("");
                                currentProject.setChange(true);
                                return;
                            case BELTAINE_LUGNASAD:
                            case IMBOLC_SAMAIN:
                            case WINTER_SOLSTICE:
                            case SUMMER_SOLSTICE:
                                astronomicalDeclination = alignment
                                        .getOrientation().toTrue(SUN, Disk.UPPER_LIMB)
                                        .toEquatorial(alignment.getObservatory().getCoordinates().getLatitude(),
                                                alignment.getObservatory().getCoordinates().getLongitude(),
                                                JulianDay)
                                        .getDeclination().reduction();

                                break;
                            case NORTHERN_LUNAR_MAJOR_STANDSTILL:
                            case SOUTHERN_LUNAR_MAJOR_STANDSTILL:
                            case SOUTHERN_LUNAR_MINOR_STANDSTILL:
                            case NORTHERN_LUNAR_MINOR_STANDSTILL:
                                astronomicalDeclination = alignment
                                        .getOrientation().toTrue(MOON, Disk.UPPER_LIMB)
                                        .toEquatorial(alignment.getObservatory().getCoordinates().getLatitude(),
                                                alignment.getObservatory().getCoordinates().getLongitude(),
                                                JulianDay)
                                        .getDeclination();

                                break;
                        }

                        SexagesimalDegree L = alignment.getObservatory().getCoordinates().getLatitude();
                        SexagesimalDegree l = alignment.getObservatory().getCoordinates().getLongitude();
                        SexagesimalDegree A = alignment.getOrientation().getAzimuth();
                        SexagesimalDegree h = alignment.getOrientation().getAltitude();
                        SexagesimalDegree delinationDifference = astEvent.getDeclination().minus(astronomicalDeclination).reduction();
                        variacionAcimut = new SexagesimalDegree(abs(cosine(astronomicalDeclination) * delinationDifference.getSignedValue() / (sine(A) * cos(L) * cosine(h))));
                        lError.setText(String.format("%dº%02d'", variacionAcimut.getDegrees(), variacionAcimut.getMinutes()));
                        SexagesimalDegree error = SexagesimalDegree.valueOf(lError.getText());
                        if (error.getSignedValue() <= 1)
                        {
                            ledValidation.setLedColor(Color.LIME);
                            alignment.setComments(astEvent.toString() + "," + tfYear.getText());
                            currentProject.setChange(true);
                        }
                        else
                        {
                            ledValidation.setLedColor(Color.RED);
                        }

                        ledValidation.setOn(true);

                    }
                    catch (ProcessException ex)
                    {

                    }
        });

        tfYear.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                -> 
                {
                    try
                    {
                        updateYear(Integer.valueOf(tfYear.getText()));
                    }
                    catch (NumberFormatException ex)
                    {

                    }
        });

    }

    /**
     * Details of alignment
     */
    public final void detailsInterface()
    {
        VBox panel = new VBox();
        tabDetails.setContent(panel);
        HBox.setHgrow(panel, Priority.ALWAYS);
        Text t;
        panel.setPadding(new Insets(10));
        panel.getChildren().add(t = new Text("Observatory"));
        t.setFont(Font.font("Verdana", 16));
        t.setFill(Color.ORANGE);
        GridPane gridPane = new GridPane();
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        panel.getChildren().add(gridPane);
        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(new Label("Coordinates"), 0, 1);
        gridPane.add(labelObservatoryName, 1, 0);
        gridPane.add(labelObservatoryCoordinates, 1, 1);
        panel.getChildren().add(t = new Text("Foresight"));
        t.setFont(Font.font("Verdana", 16));
        t.setFill(Color.ORANGE);
        gridPane = new GridPane();
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(new Label("Coordinates"), 0, 1);
        gridPane.add(labelForesightName, 1, 0);
        gridPane.add(labelForesightCoordinates, 1, 1);
        panel.getChildren().add(gridPane);
        panel.getChildren().add(t = new Text("Alignment"));
        t.setFont(Font.font("Verdana", 16));
        t.setFill(Color.ORANGE);
        gridPane = new GridPane();
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(new Label("Azimuth"), 0, 0);
        gridPane.add(new Label("Altitude"), 0, 1);
        gridPane.add(new Label("Declination"), 0, 2);
        gridPane.add(labelAzimuth, 1, 0);
        gridPane.add(labelAltitude, 1, 1);
        gridPane.add(labelDeclination, 1, 2);
        panel.getChildren().add(gridPane);

        Button btnUpdate;
        Button btnDelete;
        HBox h;
        panel.getChildren().addAll(t = new Text("Image"), new Label(""));
        t.setFont(Font.font("Verdana", 16));
        t.setFill(Color.ORANGE);
        ImageViewPane v;
        imageView = new ImageView();
        imageView.setOnMouseClicked((MouseEvent event)
                -> 
                {
                    if (event.getClickCount() == 2)
                    {
                        Tab tab = main.newTab(alignment.getObservatory().getName() + "-" + alignment.getForesight().getName());
                        ImageViewPane ivp = new ImageViewPane(new ImageView(imageView.getImage()));
                        tab.setContent(ivp);
                    }
        });
        panel.getChildren().add(v = new ImageViewPane(imageView));
        panel.getChildren().addAll(h = new HBox(10, btnDelete = new Button("Delete"), btnUpdate = new Button("Update")), new Label(""));
        h.setAlignment(Pos.BOTTOM_RIGHT);
        HBox.setHgrow(v, Priority.ALWAYS);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        VBox.setVgrow(v, Priority.ALWAYS);
        imageView.fitWidthProperty().bind(v.widthProperty());
        imageView.setPreserveRatio(true);
        HBox.setHgrow(v, Priority.ALWAYS);

        btnDelete.setOnAction((ActionEvent event)
                -> 
                {
                    imageView.setImage(null);
                    alignment.setImagenPath("");
        });

        btnUpdate.setOnAction((ActionEvent event)
                -> 
                {
                    ImageManager im = new ImageManager(skeleton, currentProject.getWorkDir());
                    if (im.showModal())
                    {
                        try
                        {
                            BufferedInputStream bis;
                            imageView.setImage(new Image(bis = new BufferedInputStream(new FileInputStream(currentProject.getWorkDir() + "imagenes" + System.getProperty("file.separator") + im.getImageFile().getName()))));
                            bis.close();
                            //vistaImagen.setCache(true);  
                            currentProject.setChange(true);
                            alignment.setImagenPath(im.getImageFile().getName());
                        }
                        //catch (IOException | NullPointerException ex)
                        catch (Exception ex)
                        {
                            Global.info.log(ex);
                        }

                    }
        });
    }

    /**
     * 
     * @param main Main
     * @param project Project
     */
    public AlignmentPane(Main main, Project project)
    {
        this.currentProject = project;
        this.main = main;
        HBox.setHgrow(this, Priority.ALWAYS);

        tabDetails.setText("Details");
        tabDetails.setClosable(false);
        getTabs().add(tabDetails);
        detailsInterface();

        tabValidation.setText("Validation");
        tabValidation.setClosable(false);
        getTabs().add(tabValidation);
        validationInterface();

    }
}
