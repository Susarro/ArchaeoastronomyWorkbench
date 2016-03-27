/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.esqueleto;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ProgressDialog extends ModalDialog
{

    public ProgressBar progressBar;
    public Label title;
    public Label message;
    public Label running;
    public Label state;
    public Label totalWork;
    public Label workDone;
    public Label progress;
    public Label value;
    public Label exception;
    Task tarea;

    public void setTarea(final Task tarea)
    {
        this.tarea = tarea;
        
        btnCancelar.setOnAction((ActionEvent event) ->
        {
            tarea.cancel();
            Cancelar();
        });

        final ReadOnlyObjectProperty<Worker.State> stateProperty
                = tarea.stateProperty();

        progressBar.progressProperty().unbind();                
        progressBar.progressProperty().bind(tarea.progressProperty());
        
        title.textProperty().unbind();
        title.textProperty().bind(tarea.titleProperty());
        
        message.textProperty().unbind();
        message.textProperty().bind(tarea.messageProperty());
        
        running.textProperty().unbind();
        running.textProperty().bind(Bindings.format("%s", tarea.runningProperty()));
        
        state.textProperty().unbind();
        state.textProperty().bind(Bindings.format("%s", stateProperty));
        
        totalWork.textProperty().unbind();
        totalWork.textProperty().bind(tarea.totalWorkProperty().asString());
        
        workDone.textProperty().unbind();
        workDone.textProperty().bind(tarea.workDoneProperty().asString());
        
        progress.textProperty().unbind();
        progress.textProperty().bind(Bindings.format("%5.2f%%", tarea.progressProperty().multiply(100)));
        
        value.textProperty().unbind();
        value.textProperty().bind(tarea.valueProperty());

        exception.textProperty().unbind();
        exception.textProperty().bind(new StringBinding()
        {            
            {
                super.bind(tarea.exceptionProperty());
            }

            @Override
            protected String computeValue()
            {
                final Throwable exception = tarea.getException();
                if (exception == null)
                {
                    return "";
                }
                return exception.getMessage();
            }

        });
    }

    public ProgressDialog(Skeleton padre)
    {
        super(padre, new VBox(),true);
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(500);
        title = new Label();
        message = new Label();
        running = new Label();
        state = new Label();
        totalWork = new Label();
        workDone = new Label();
        progress = new Label();
        value = new Label();
        exception = new Label();
        panel.getChildren().addAll(progressBar, title, message);

        btnAceptar.setVisible(false);

    }

    public ProgressDialog(Skeleton padre, final Task tarea)
    {
        super(padre, new VBox(),true);
        this.tarea = tarea;
        progressBar = new ProgressBar();
        progressBar.setPrefWidth(500);
        title = new Label();
        message = new Label();
        running = new Label();
        state = new Label();
        totalWork = new Label();
        workDone = new Label();
        progress = new Label();
        value = new Label();
        exception = new Label();
        panel.getChildren().addAll(progressBar, title, message);

        btnAceptar.setVisible(false);

        btnCancelar.setOnAction((ActionEvent event) ->
        {
            tarea.cancel();
            Cancelar();
        });

        final ReadOnlyObjectProperty<Worker.State> stateProperty
                = tarea.stateProperty();

        progressBar.progressProperty().bind(tarea.progressProperty());
        title.textProperty().bind(
                tarea.titleProperty());
        message.textProperty().bind(
                tarea.messageProperty());
        running.textProperty().bind(
                Bindings.format("%s", tarea.runningProperty()));
        state.textProperty().bind(
                Bindings.format("%s", stateProperty));
        totalWork.textProperty().bind(
                tarea.totalWorkProperty().asString());
        workDone.textProperty().bind(
                tarea.workDoneProperty().asString());
        progress.textProperty().bind(
                Bindings.format("%5.2f%%", tarea.progressProperty().multiply(100)));
        value.textProperty().bind(tarea.valueProperty());

        exception.textProperty().bind(new StringBinding()
        {
            
            {
                super.bind(tarea.exceptionProperty());
            }

            @Override
            protected String computeValue()
            {
                final Throwable exception = tarea.getException();
                if (exception == null)
                {
                    return "";
                }
                return exception.getMessage();
            }

        });
    }
}
