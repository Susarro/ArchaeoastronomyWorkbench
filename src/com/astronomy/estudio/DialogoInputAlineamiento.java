/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.ProcessException;
import com.components.FixedWidthTextField;
import com.components.TextFieldInDegrees;
import com.components.GridPane10;
import com.astronomy.coordinate_system.Geographic;
import com.interfaz.esqueleto.BorderedTitledPane;
import com.interfaz.esqueleto.ModalDialog;
import com.interfaz.Principal;
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
public class DialogoInputAlineamiento extends ModalDialog
{

    TextField nombreOrigen;
    FixedWidthTextField latitudOrigen;
    FixedWidthTextField longitudOrigen;
    FixedWidthTextField altitudOrigen;
    TextField nombreReferencia;
    FixedWidthTextField latitudDestino;
    FixedWidthTextField longitudDestino;
    FixedWidthTextField altitudDestino;
    FixedWidthTextField acimut;
    FixedWidthTextField elevacion;
    FixedWidthTextField declinacion;
    Alineamiento alineamiento;
    private final CheckBox cbCoordenadas;

    private void updateAlineamiento()
    {
        if (cbCoordenadas.isSelected())
        {
            try
            {
                alineamiento = new Alineamiento(
                        new Lugar(nombreOrigen.getText(),
                                new Geographic(SexagesimalDegree.valueOf(latitudOrigen.getText()), SexagesimalDegree.valueOf(longitudOrigen.getText()),
                                        Double.valueOf(altitudOrigen.getText().replace(",", "."))), "origen"),
                        new Lugar(nombreReferencia.getText(),
                                new Geographic(SexagesimalDegree.valueOf(latitudDestino.getText()), SexagesimalDegree.valueOf(longitudDestino.getText()),
                                        Double.valueOf(altitudDestino.getText().replace(",", "."))), "referencia"));
                acimut.setText(String.format("%.1f", alineamiento.getDireccion().getAzimuth().getSignedValue()));
                elevacion.setText(String.format("%.1f", alineamiento.getDireccion().getAltitude().getSignedValue()));
                declinacion.setText(String.format("%.1f", alineamiento.getDeclinacion().getSignedValue()));
                if (nombreOrigen.getText().isEmpty() || nombreReferencia.getText().isEmpty())
                {
                    btnAceptar.setDisable(true);
                }
                else
                {
                    btnAceptar.setDisable(false);
                }
            }
            catch (ProcessException ex)
            {
                alineamiento = null;
                acimut.setText("");
                elevacion.setText("");
                declinacion.setText("");
                btnAceptar.setDisable(true);
            }

        }
        else
        {
            try
            {
                alineamiento = new Alineamiento(
                        new Lugar(nombreOrigen.getText(), new Geographic(SexagesimalDegree.valueOf(latitudOrigen.getText()),
                                        SexagesimalDegree.valueOf(longitudOrigen.getText()),
                                        Double.valueOf(altitudOrigen.getText().replace(",", "."))), "origen"),
                        nombreReferencia.getText(),
                        new Direccion(SexagesimalDegree.valueOf(acimut.getText()), SexagesimalDegree.valueOf(elevacion.getText())));
                declinacion.setText(String.format("%.1f", alineamiento.getDeclinacion().getSignedValue()));
                if (nombreOrigen.getText().isEmpty() || nombreReferencia.getText().isEmpty())
                {
                    btnAceptar.setDisable(true);
                }
                else
                {
                    btnAceptar.setDisable(false);
                }
            }
            catch (ProcessException ex)
            {
                alineamiento = null;
                declinacion.setText("");
                btnAceptar.setDisable(true);
            }
        }

    }

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
        updateAlineamiento();
    }

    public DialogoInputAlineamiento(Principal padre, Alineamiento entrada)
    {
        super(padre.skeleton, new VBox(10), true);
        nombreOrigen = new TextField(entrada == null ? "" : entrada.getOrigen().getNombre());
        latitudOrigen = new TextFieldInDegrees(entrada == null || entrada.getOrigen().getCoordenadas() == null ? padre.latitud : entrada.getOrigen().getCoordenadas().getLatitude());
        longitudOrigen = new TextFieldInDegrees(entrada == null || entrada.getOrigen().getCoordenadas() == null ? padre.longitud : entrada.getOrigen().getCoordenadas().getLongitude());
        altitudOrigen = new FixedWidthTextField(entrada == null || entrada.getOrigen().getCoordenadas() == null ? "0.0" : String.format("%.1f", entrada.getOrigen().getCoordenadas().getAltitud()));
        nombreReferencia = new TextField(entrada == null ? "" : entrada.getReferencia().getNombre());
        latitudDestino = new TextFieldInDegrees(entrada == null || entrada.getReferencia().getCoordenadas() == null ? padre.latitud.plus(new SexagesimalDegree(1)) : entrada.getReferencia().getCoordenadas().getLatitude());
        longitudDestino = new TextFieldInDegrees(entrada == null || entrada.getReferencia().getCoordenadas() == null ? padre.longitud : entrada.getReferencia().getCoordenadas().getLongitude());
        altitudDestino = new FixedWidthTextField(entrada == null || entrada.getReferencia().getCoordenadas() == null ? "0.0" : String.format("%.1f", entrada.getReferencia().getCoordenadas().getAltitud()));
        acimut = new TextFieldInDegrees(entrada == null ? new SexagesimalDegree(0) : entrada.getDireccion().getAzimuth());
        elevacion = new TextFieldInDegrees(entrada == null ? new SexagesimalDegree(0) : entrada.getDireccion().getAltitude());
        declinacion = new TextFieldInDegrees(entrada == null ? new SexagesimalDegree(0) : entrada.getDeclinacion());
        cbCoordenadas = new CheckBox("Coordenadas");

        checkTextField(nombreOrigen);
        checkTextField(nombreReferencia);

        
        
        nombreOrigen.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            checkTextField(nombreOrigen);
        });
        
        nombreReferencia.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            checkTextField(nombreReferencia);
        });
        
        latitudOrigen.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        longitudOrigen.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        altitudOrigen.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        latitudDestino.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        longitudDestino.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        altitudDestino.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            updateAlineamiento();
        });
        
        acimut.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (!cbCoordenadas.isSelected())
            {
                updateAlineamiento();
            }
        });
        
        elevacion.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (!cbCoordenadas.isSelected())
            {
                updateAlineamiento();
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
        gpOrigen[0].add(nombreOrigen, 1, 0);
        gpOrigen[1].add(new Label("Latitud"), 0, 0);
        gpOrigen[1].add(latitudOrigen, 1, 0);
        gpOrigen[1].add(new Label("Longitud"), 0, 1);
        gpOrigen[1].add(longitudOrigen, 1, 1);
        gpOrigen[1].add(new Label("Altitud"), 0, 2);
        gpOrigen[1].add(altitudOrigen, 1, 2);

        HBox hDestino = new HBox(10);
        GridPane10[] gpDestino = new GridPane10[3];
        for (int i = 0; i < gpDestino.length; i++)
        {
            gpDestino[i] = new GridPane10();
        }
        hDestino.getChildren().addAll(gpDestino[0], gpDestino[1], gpDestino[2]);

        BorderedTitledPane btpDestino = new BorderedTitledPane("Alineamiento", hDestino);
        gpDestino[0].add(new Label("Referencia"), 0, 0);
        gpDestino[0].add(nombreReferencia, 1, 0);
        gpDestino[1].add(new Label("Latitud"), 0, 0);
        gpDestino[1].add(latitudDestino, 1, 0);
        gpDestino[1].add(new Label("Longitud"), 0, 1);
        gpDestino[1].add(longitudDestino, 1, 1);
        gpDestino[1].add(new Label("Altitud"), 0, 2);
        gpDestino[1].add(altitudDestino, 1, 2);
        gpDestino[2].add(new Label("Acimut"), 0, 1);
        gpDestino[2].add(acimut, 1, 1);
        gpDestino[2].add(new Label("Elevación"), 0, 2);
        gpDestino[2].add(elevacion, 1, 2);

        gpDestino[0].add(cbCoordenadas, 1, 1);
        declinacion.setDisable(true);

        HBox hDeclinación = new HBox(10);
        hDeclinación.getChildren().addAll(new Label("Declinación"), declinacion);
        hDeclinación.setAlignment(Pos.CENTER_RIGHT);

        panel.getChildren().addAll(btpOrigen, btpDestino, hDeclinación);

        acimut.disableProperty().bind(cbCoordenadas.selectedProperty());
        elevacion.disableProperty().bind(cbCoordenadas.selectedProperty());
        cbCoordenadas.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
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
            updateAlineamiento();
            util.sizeToScene();

        });
        cbCoordenadas.setSelected(entrada != null && entrada.getReferencia().getCoordenadas() != null);
        if (cbCoordenadas.isSelected())
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
        updateAlineamiento();

    }

}
