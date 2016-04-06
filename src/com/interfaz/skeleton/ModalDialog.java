/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

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
 * Modal dialog
 * 
 * @author MIGUEL_ANGEL
 */
public class ModalDialog
{

    /**
     * OK button
     */
    public Button btnOK;
    /**
     * Cancel button
     */
    public Button btnCancel;
    /**
     * True if OK. Otherwise false
     */
    public boolean result = false;
    
    /**
     * Stage
     */
    public final Stage stage;
    /**
     * Parent
     */
    public Skeleton parent;
    
    /**
     * User pane
     */
    public Pane userPane;
    
    /**
     * Contains boxButtons and userPane
     */
    public VBox box;
    /**
     * Horizontal box for buttons
     */
    public HBox boxButtons;
    /**
     * Frame. Contains box
     */
    public StackPane frame;
    
    /**
     * 
     * @param t Title to set
     */
    public void setTitle(String t)
    {
        stage.setTitle(t);
    }        

    /**
     * OK selected
     */
    public void OK()
    {
        if (parent != null)
        {
            parent.getScene().getRoot().setEffect(null);
        }
        result = true;
        stage.close();
    }

    /**
     * Cancel selected
     */
    public void cancel()
    {
        if (parent != null)
        {
            parent.getScene().getRoot().setEffect(null);
        }
        result = false;
        stage.close();

    }
    
    /**
     * Close. It doesn't set result
     */
    public void close()
    {
        if (parent != null)
        {
            parent.getScene().getRoot().setEffect(null);
        }        
        stage.close();

    }
    
    
/**
 * 
 * @param parent Skeleton parent
 * @param userPane User pane
 * @param keyEnter If true key ENTER causes OK
 */
    public ModalDialog(final Skeleton parent, Pane userPane, boolean keyEnter)
    {
        this.parent = parent;
        this.userPane = userPane;
        stage = new Stage(StageStyle.TRANSPARENT);        
        stage.initModality(Modality.APPLICATION_MODAL);
        frame = new StackPane();
        frame.getStyleClass().add("modal-dialog-glass");
        box = new VBox();
        box.getStyleClass().add("modal-dialog-content");
        frame.getChildren().add(box);
        box.getChildren().add(userPane);
        boxButtons = new HBox();
        box.getChildren().add(boxButtons);
        boxButtons.setAlignment(Pos.TOP_CENTER);
        boxButtons.setSpacing(10);

        if (keyEnter)
        {
            frame.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent keyEvent) ->
            {
                if (keyEvent.getCode() == KeyCode.ENTER)
                {
                    OK();
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.ESCAPE)
                {
                    cancel();
                    keyEvent.consume();
                }
            });
        }

        btnOK = new Button();
        btnOK.setText("Aceptar");

        btnOK.setOnAction((ActionEvent event) ->
        {
            OK();
        });
        boxButtons.getChildren().add(btnOK);

        btnCancel = new Button();
        btnCancel.setText("Cancelar");

        btnCancel.setOnAction((ActionEvent event) ->
        {
            cancel();
        });
        boxButtons.getChildren().add(btnCancel);

        Scene scene = new Scene(frame, Color.TRANSPARENT);
        stage.setScene(scene);

        stage.getScene().getStylesheets().add(ModalDialog.class.getResource("modal-dialog.css").toExternalForm());

        stage.widthProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) ->
        {
            try
            {
                stage.setX(parent.getStage().getX() + parent.getStage().getWidth() / 2 - stage.getWidth() / 2);
            }
            catch (NullPointerException ex)
            {

            }
        });

        stage.heightProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) ->
        {
            try
            {
                stage.setY(parent.getStage().getY() + parent.getStage().getHeight() / 2 - stage.getHeight() / 2);
            }
            catch (NullPointerException ex)
            {

            }
        });

    }

    /**
     * Show modal
     * @return result
     */
    public boolean showModal()
    {
        stage.showAndWait();
        stage.toFront();

        return result;
    }

    /**
     * Show
     * @return result
     */
    public boolean show()
    {
        stage.show();
        stage.toFront();
        return result;
    }
}
