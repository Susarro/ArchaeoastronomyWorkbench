/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.esqueleto;

import com.interfaz.esqueleto.Esqueleto;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ModalDialog
{

    public Button btnAceptar;
    public Button btnCancelar;
    public boolean resultado = false;
    public Stage stage;
    public final Stage util;
    public Esqueleto padre;
    public Pane panel;
    public VBox box;
    public HBox boxButtons;
    public StackPane contenedor;
    
    public void setTitle(String t)
    {
        util.setTitle(t);
    }        

    public void Aceptar()
    {
        if (padre != null)
        {
            padre.getScene().getRoot().setEffect(null);
        }
        resultado = true;
        util.close();
    }

    public void Cancelar()
    {
        if (padre != null)
        {
            padre.getScene().getRoot().setEffect(null);
        }
        resultado = false;
        util.close();

    }
    
    public void Cerrar()
    {
        if (padre != null)
        {
            padre.getScene().getRoot().setEffect(null);
        }        
        util.close();

    }
    
    

    public ModalDialog(final Esqueleto padre, Pane pane, boolean keyEnter)
    {
        this.padre = padre;
        panel = pane;
        util = new Stage(StageStyle.TRANSPARENT);        
        util.initModality(Modality.APPLICATION_MODAL);
        contenedor = new StackPane();
        contenedor.getStyleClass().add("modal-dialog-glass");
        box = new VBox();
        box.getStyleClass().add("modal-dialog-content");
        contenedor.getChildren().add(box);
        box.getChildren().add(pane);
        boxButtons = new HBox();
        box.getChildren().add(boxButtons);
        boxButtons.setAlignment(Pos.TOP_CENTER);
        boxButtons.setSpacing(10);

        if (keyEnter)
        {
            contenedor.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent keyEvent) ->
            {
                if (keyEvent.getCode() == KeyCode.ENTER)
                {
                    Aceptar();
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                {
                    Cancelar();
                    keyEvent.consume();
                }
            });
        }

        btnAceptar = new Button();
        btnAceptar.setText("Aceptar");

        btnAceptar.setOnAction((ActionEvent event) ->
        {
            Aceptar();
        });
        boxButtons.getChildren().add(btnAceptar);

        btnCancelar = new Button();
        btnCancelar.setText("Cancelar");

        btnCancelar.setOnAction((ActionEvent event) ->
        {
            Cancelar();
        });
        boxButtons.getChildren().add(btnCancelar);

        Scene scene = new Scene(contenedor, Color.TRANSPARENT);
        util.setScene(scene);

        util.getScene().getStylesheets().add(ModalDialog.class.getResource("modal-dialog.css").toExternalForm());

        util.widthProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) ->
        {
            try
            {
                util.setX(padre.getStage().getX() + padre.getStage().getWidth() / 2 - util.getWidth() / 2);
            }
            catch (NullPointerException ex)
            {

            }
        });

        util.heightProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) ->
        {
            try
            {
                util.setY(padre.getStage().getY() + padre.getStage().getHeight() / 2 - util.getHeight() / 2);
            }
            catch (NullPointerException ex)
            {

            }
        });

    }

    public boolean ShowModal()
    {
        //util.showAndWait();
        if (padre != null)
        {
            //padre.getScene().getRoot().setEffect(new BoxBlur());
        }
        //util.setX(padre.getStage().getX()+padre.getStage().getWidth()/2-util.getWidth()/2);
        //util.setY(padre.getStage().getY()+padre.getStage().getHeight()/2-util.getHeight()/2);
        //util.centerOnScreen();
        util.showAndWait();
        util.toFront();

        return resultado;
    }

    public boolean Show()
    {
        //util.showAndWait();
        if (padre != null)
        {
            //padre.getScene().getRoot().setEffect(new BoxBlur());
        }

        //util.setX(padre.getStage().getX()+padre.getStage().getWidth()/2-util.getWidth()/2);
        //util.setY(padre.getStage().getY()+padre.getStage().getHeight()/2-util.getHeight()/2);
        //util.centerOnScreen(Screen.getMainScreen());
        util.show();
        util.toFront();

        return resultado;
    }
}
