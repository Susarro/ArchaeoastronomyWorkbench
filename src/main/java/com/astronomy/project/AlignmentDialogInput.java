/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.ProcessException;
import com.components.FixedWidthTextField;
import com.components.TextFieldInDegrees;
import com.components.GridPane10;
import com.astronomy.coordinate_system.Geographic;
import com.interfaz.skeleton.BorderedTitledPane;
import com.interfaz.skeleton.ModalDialog;
import com.main.Main;
import com.units.SexagesimalDegree;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class AlignmentDialogInput extends ModalDialog
{

    /**
     * Text field for observatory name
     */
    TextField observatoryName;
    /**
     * Text field for observatory latitude
     */
    FixedWidthTextField observatoryLatitude;
    /**
     * Text field for observatory longitude
     */
    FixedWidthTextField observatoryLongitude;
    /**
     * Text field for observatory elevation
     */
    FixedWidthTextField observatoryElevation;
    
    /**
     * Text field for foresight name
     */
    TextField foresightName;
    /**
     * Text field for foresight latitude
     */
    FixedWidthTextField foresightLatitude;
    /**
     * Text field for foresight longitude
     */
    FixedWidthTextField foresightLongitude;
    /**
     * Text field for foresight elevation
     */
    FixedWidthTextField foresightElevation;
    /**
     * Azimuth
     */
    FixedWidthTextField azimuth;
    /**
     * Altitude
     */
    FixedWidthTextField altitude;
    /**
     * Declination
     */
    FixedWidthTextField declination;
    /**
     * Alignment
     */
    Alignment alignment;
    /**
     * Checkbox to enter coordinates
     */
    private final CheckBox cbCoordinates;

    /**
     * 
     * @param name Observatory name to set
     */
    public void setObservatoryName(String name)
    {
        observatoryName.setText(name);
    } 
    
     /**
     * 
     * @param name Foresight Name to set
     */
    public void setForesightName(String name)
    {
        foresightName.setText(name);
    } 
    
    /**
     * Update
     */
    private void updateAlignment()
    {
        if (cbCoordinates.isSelected())
        {
            try
            {
                alignment = new Alignment(
                        new ReferencePoint(observatoryName.getText(),
                                new Geographic(SexagesimalDegree.valueOf(observatoryLatitude.getText()), SexagesimalDegree.valueOf(observatoryLongitude.getText()),
                                        Double.valueOf(observatoryElevation.getText().replace(",", "."))), "origen"),
                        new ReferencePoint(foresightName.getText(),
                                new Geographic(SexagesimalDegree.valueOf(foresightLatitude.getText()), SexagesimalDegree.valueOf(foresightLongitude.getText()),
                                        Double.valueOf(foresightElevation.getText().replace(",", "."))), "referencia"));
                azimuth.setText(String.format("%.1f", alignment.getOrientation().getAzimuth().getSignedValue()));
                altitude.setText(String.format("%.1f", alignment.getOrientation().getAltitude().getSignedValue()));
                declination.setText(String.format("%.1f", alignment.getDeclination().getSignedValue()));
                if (observatoryName.getText().isEmpty() || foresightName.getText().isEmpty())
                {
                    btnOK.setDisable(true);
                }
                else
                {
                    btnOK.setDisable(false);
                }
            }
            catch (ProcessException ex)
            {
                alignment = null;
                azimuth.setText("");
                altitude.setText("");
                declination.setText("");
                btnOK.setDisable(true);
            }

        }
        else
        {
            try
            {
                alignment = new Alignment(
                        new ReferencePoint(observatoryName.getText(), new Geographic(SexagesimalDegree.valueOf(observatoryLatitude.getText()),
                                        SexagesimalDegree.valueOf(observatoryLongitude.getText()),
                                        Double.valueOf(observatoryElevation.getText().replace(",", "."))), "origen"),
                        foresightName.getText(),
                        new Orientation(SexagesimalDegree.valueOf(azimuth.getText()), SexagesimalDegree.valueOf(altitude.getText())));
                declination.setText(String.format("%.1f", alignment.getDeclination().getSignedValue()));
                if (observatoryName.getText().isEmpty() || foresightName.getText().isEmpty())
                {
                    btnOK.setDisable(true);
                }
                else
                {
                    btnOK.setDisable(false);
                }
            }
            catch (ProcessException ex)
            {
                alignment = null;
                declination.setText("");
                btnOK.setDisable(true);
            }
        }

    }

    /**
     * Check text field. If empty set background color to red
     * @param tf Text field
     */
    private void checkTextField(TextField tf)
    {
        if (tf.getText().trim().isEmpty())
        {
            tf.setStyle("-fx-background-color: red");
        }
        else
        {
            tf.setStyle("");
        }
        updateAlignment();
    }

    
    /**
     * 
     * @param initial Initial algnment
     */
    public AlignmentDialogInput(Alignment initial)
    {
        super(Main.skeleton, new VBox(10), true);
        observatoryName = new TextField(initial == null ? "" : initial.getObservatory().getName());
        observatoryLatitude = new TextFieldInDegrees(initial == null || initial.getObservatory().getCoordinates() == null ? Main.latitude : initial.getObservatory().getCoordinates().getLatitude());
        observatoryLongitude = new TextFieldInDegrees(initial == null || initial.getObservatory().getCoordinates() == null ? Main.longitude : initial.getObservatory().getCoordinates().getLongitude());
        observatoryElevation = new FixedWidthTextField(initial == null || initial.getObservatory().getCoordinates() == null ? "0.0" : String.format("%.1f", initial.getObservatory().getCoordinates().getElevation()));
        foresightName = new TextField(initial == null ? "" : initial.getForesight().getName());
        foresightLatitude = new TextFieldInDegrees(initial == null || initial.getForesight().getCoordinates() == null ? Main.latitude.plus(new SexagesimalDegree(1)) : initial.getForesight().getCoordinates().getLatitude());
        foresightLongitude = new TextFieldInDegrees(initial == null || initial.getForesight().getCoordinates() == null ? Main.longitude : initial.getForesight().getCoordinates().getLongitude());
        foresightElevation = new FixedWidthTextField(initial == null || initial.getForesight().getCoordinates() == null ? "0.0" : String.format("%.1f", initial.getForesight().getCoordinates().getElevation()));
        azimuth = new TextFieldInDegrees(initial == null ? new SexagesimalDegree(0) : initial.getOrientation().getAzimuth());
        altitude = new TextFieldInDegrees(initial == null ? new SexagesimalDegree(0) : initial.getOrientation().getAltitude());
        declination = new TextFieldInDegrees(initial == null ? new SexagesimalDegree(0) : initial.getDeclination());
        cbCoordinates = new CheckBox("Coordenadas");

        checkTextField(observatoryName);
        checkTextField(foresightName);
        
        observatoryName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            checkTextField(observatoryName);
        });
        
        foresightName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            checkTextField(foresightName);
        });
        
        observatoryLatitude.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        observatoryLongitude.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        observatoryElevation.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        foresightLatitude.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        foresightLongitude.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        foresightElevation.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlignment();
        });
        
        azimuth.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (!cbCoordinates.isSelected())
            {
                updateAlignment();
            }
        });
        
        altitude.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (!cbCoordinates.isSelected())
            {
                updateAlignment();
            }
        });        

        HBox hOrigen = new HBox(10);
        GridPane10[] gpOrigen = new GridPane10[2];
        for (int i = 0; i < gpOrigen.length; i++)
        {
            gpOrigen[i] = new GridPane10();
        }
        hOrigen.getChildren().addAll(gpOrigen[0], gpOrigen[1]);

        BorderedTitledPane btpOrigen = new BorderedTitledPane("Origen", hOrigen);

        gpOrigen[0].add(new Label("Nombre"), 0, 0);
        gpOrigen[0].add(observatoryName, 1, 0);
        gpOrigen[1].add(new Label("Latitud"), 0, 0);
        gpOrigen[1].add(observatoryLatitude, 1, 0);
        gpOrigen[1].add(new Label("Longitud"), 0, 1);
        gpOrigen[1].add(observatoryLongitude, 1, 1);
        gpOrigen[1].add(new Label("Altitud"), 0, 2);
        gpOrigen[1].add(observatoryElevation, 1, 2);

        HBox hDestino = new HBox(10);
        GridPane10[] gpDestino = new GridPane10[3];
        for (int i = 0; i < gpDestino.length; i++)
        {
            gpDestino[i] = new GridPane10();
        }
        hDestino.getChildren().addAll(gpDestino[0], gpDestino[1], gpDestino[2]);

        BorderedTitledPane btpDestino = new BorderedTitledPane("Alineamiento", hDestino);
        gpDestino[0].add(new Label("Referencia"), 0, 0);
        gpDestino[0].add(foresightName, 1, 0);
        gpDestino[1].add(new Label("Latitud"), 0, 0);
        gpDestino[1].add(foresightLatitude, 1, 0);
        gpDestino[1].add(new Label("Longitud"), 0, 1);
        gpDestino[1].add(foresightLongitude, 1, 1);
        gpDestino[1].add(new Label("Altitud"), 0, 2);
        gpDestino[1].add(foresightElevation, 1, 2);
        gpDestino[2].add(new Label("Acimut"), 0, 1);
        gpDestino[2].add(azimuth, 1, 1);
        gpDestino[2].add(new Label("Elevación"), 0, 2);
        gpDestino[2].add(altitude, 1, 2);

        gpDestino[0].add(cbCoordinates, 1, 1);
        declination.setDisable(true);

        HBox hDeclinación = new HBox(10);
        hDeclinación.getChildren().addAll(new Label("Declinación"), declination);
        hDeclinación.setAlignment(Pos.CENTER_RIGHT);

        userPane.getChildren().addAll(btpOrigen, btpDestino, hDeclinación);

        azimuth.disableProperty().bind(cbCoordinates.selectedProperty());
        altitude.disableProperty().bind(cbCoordinates.selectedProperty());
        cbCoordinates.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
        {
            if (newValue)
            {
                if (!hDestino.getChildren().contains(gpDestino[1]))
                {
                    hDestino.getChildren().add(1, gpDestino[1]);
                }
            }
            else
            {
                hDestino.getChildren().remove(gpDestino[1]);
            }
            updateAlignment();
            stage.sizeToScene();

        });
        cbCoordinates.setSelected(initial != null && initial.getForesight().getCoordinates() != null);
        if (cbCoordinates.isSelected())
        {
            if (!hDestino.getChildren().contains(gpDestino[1]))
            {
                hDestino.getChildren().add(1, gpDestino[1]);
            }
        }
        else
        {
            hDestino.getChildren().remove(gpDestino[1]);
        }
        updateAlignment();

    }

}
