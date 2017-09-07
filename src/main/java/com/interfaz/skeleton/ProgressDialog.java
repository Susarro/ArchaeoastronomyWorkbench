/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

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
 * Preogress dialog
 *
 * @author MIGUEL_ANGEL
 */
public class ProgressDialog extends ModalDialog
{

    /**
     * Progress bar
     */
    public ProgressBar progressBar;
    /**
     * Title
     */
    public Label title;
    /**
     * Message
     */
    public Label message;
    /**
     * Running
     */
    public Label running;
    /**
     * State
     */
    public Label state;
    /**
     * Total work
     */
    public Label totalWork;
    /**
     * Work done
     */
    public Label workDone;
    /**
     * Progress
     */
    public Label progress;
    /**
     * Value
     */
    public Label value;
    /**
     * Exception
     */
    public Label exception;
    Task tarea;

    /**
     * Task to which progress, title, message, running, state, totalWork,
     * workDone, value and exception properties are bound
     *
     * @param task Task
     */
    public void setTask(final Task task)
    {
        this.tarea = task;

        btnCancel.setOnAction((ActionEvent event) ->
        {
            task.cancel();
            cancel();
        });

        final ReadOnlyObjectProperty<Worker.State> stateProperty
                = task.stateProperty();

        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());

        title.textProperty().unbind();
        title.textProperty().bind(task.titleProperty());

        message.textProperty().unbind();
        message.textProperty().bind(task.messageProperty());

        running.textProperty().unbind();
        running.textProperty().bind(Bindings.format("%s", task.runningProperty()));

        state.textProperty().unbind();
        state.textProperty().bind(Bindings.format("%s", stateProperty));

        totalWork.textProperty().unbind();
        totalWork.textProperty().bind(task.totalWorkProperty().asString());

        workDone.textProperty().unbind();
        workDone.textProperty().bind(task.workDoneProperty().asString());

        progress.textProperty().unbind();
        progress.textProperty().bind(Bindings.format("%5.2f%%", task.progressProperty().multiply(100)));

        value.textProperty().unbind();
        value.textProperty().bind(task.valueProperty());

        exception.textProperty().unbind();
        exception.textProperty().bind(new StringBinding()
        {
            {
                super.bind(task.exceptionProperty());
            }

            @Override
            protected String computeValue()
            {
                final Throwable exception = task.getException();
                if (exception == null)
                {
                    return "";
                }
                return exception.getMessage();
            }

        });
    }

    /**
     *
     * @param parent Skeleton parent
     */
    public ProgressDialog(Skeleton parent)
    {
        super(parent, new VBox(), true);
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
        userPane.getChildren().addAll(progressBar, title, message);

        btnOK.setVisible(false);

    }

    /**
     *
     * @param parent Skeleton parent
     * @param task Task to which progress, title, message, running, state,
     * totalWork, workDone, value and exception properties are bound
     */
    public ProgressDialog(Skeleton parent, final Task task)
    {
        super(parent, new VBox(), true);
        this.tarea = task;
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
        userPane.getChildren().addAll(progressBar, title, message);

        btnOK.setVisible(false);

        btnCancel.setOnAction((ActionEvent event) ->
        {
            task.cancel();
            cancel();
        });

        final ReadOnlyObjectProperty<Worker.State> stateProperty
                = task.stateProperty();

        progressBar.progressProperty().bind(task.progressProperty());
        title.textProperty().bind(
                task.titleProperty());
        message.textProperty().bind(
                task.messageProperty());
        running.textProperty().bind(
                Bindings.format("%s", task.runningProperty()));
        state.textProperty().bind(
                Bindings.format("%s", stateProperty));
        totalWork.textProperty().bind(
                task.totalWorkProperty().asString());
        workDone.textProperty().bind(
                task.workDoneProperty().asString());
        progress.textProperty().bind(
                Bindings.format("%5.2f%%", task.progressProperty().multiply(100)));
        value.textProperty().bind(task.valueProperty());

        exception.textProperty().bind(new StringBinding()
        {

            {
                super.bind(task.exceptionProperty());
            }

            @Override
            protected String computeValue()
            {
                final Throwable exception = task.getException();
                if (exception == null)
                {
                    return "";
                }
                return exception.getMessage();
            }

        });
    }
}
