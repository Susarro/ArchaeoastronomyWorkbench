/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.Global;
import com.interfaz.esqueleto.Skeleton;
import com.interfaz.esqueleto.ModalDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ImageManager extends ModalDialog
{

    private final String path;
    String tempDir;
    private final ListView lista;
    boolean ok = false;

    public String inputDialog(String text, String valor)
    {
        HBox p = new HBox(10);
        p.setAlignment(Pos.CENTER);
        TextField tf;
        p.getChildren().addAll(new Label(text), tf = new TextField(valor));

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            if (new File(this.path + System.getProperty("file.separator") + newValue + ".jpg").exists())
            {
                tf.setStyle("-fx-background-color: red");
                ok = false;
            }
            else
            {
                tf.setStyle("");
                ok = true;
            }
        });

        ModalDialog dialogo = new ModalDialog(null, p, true);
        if (dialogo.ShowModal() && ok)
        {
            return tf.getText();
        }
        else
        {
            return "";
        }
    }

    public class MuestraImagen
    {

        private File fichero;
        public ImageView thumbsnail;

        public MuestraImagen(File f) throws FileNotFoundException
        {
            this.fichero = f;
            thumbsnail = new ImageView(new Image(new FileInputStream(f), 300, 0, true, true));

        }

        /**
         * @return the fichero
         */
        public File getFichero()
        {
            return fichero;
        }

    }

    ObservableList<MuestraImagen> items = FXCollections.observableArrayList();

    public File getImagen()
    {
        try
        {
            return ((MuestraImagen) lista.getSelectionModel().getSelectedItem()).getFichero();
        }
        catch (NullPointerException ex)
        {
            return null;
        }
    }

    public void update()
    {
        items.clear();
        File directorio = new File(path);
        if (directorio.exists())
        {
            File[] files = directorio.listFiles((File dir, String name) -> name.toLowerCase().endsWith("jpg"));
            for (File f : files)
            {
                try
                {
                    items.add(new MuestraImagen(f));
                }
                catch (FileNotFoundException ex)
                {
                    Global.info.Log(ex);
                }
                //Global.info.Info(f.getName());
            }
        }
    }

    class MuestraImagenCell extends ListCell<MuestraImagen>
    {

        VBox v = new VBox(10);
        Label label = new Label("Hola");
        ImageView imagen = new ImageView();

        public MuestraImagenCell()
        {
            v.getChildren().addAll(label, imagen);
        }

        @Override
        protected void updateItem(MuestraImagen t, boolean bln)
        {
            if (t != null)
            {
                imagen.setImage(t.thumbsnail.getImage());
                setGraphic(v);
            }
        }
    }

    public ImageManager(Skeleton padre, String p)
    {
        super(padre, new VBox(10), true);
        this.path = p + System.getProperty("file.separator") + "imagenes";
        new File(this.path).mkdirs();
        tempDir = "";
        lista = new ListView(items);
        panel.getChildren().add(lista);
        update();

        lista.setCellFactory((new Callback<ListView<MuestraImagen>, ListCell<MuestraImagen>>()
        {

            @Override
            public ListCell<MuestraImagen> call(ListView<MuestraImagen> p)
            {

                ListCell<MuestraImagen> cell = new ListCell<MuestraImagen>()
                {
                    @Override
                    protected void updateItem(MuestraImagen t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            VBox v = new VBox(5);
                            Label l;
                            v.getChildren().addAll(l = new Label(t.getFichero().getName().split("\\.")[0]), t.thumbsnail);
                            l.getStyleClass().clear();
                            l.getStyleClass().add("label_negro");
                            setGraphic(v);
                        }
                    }
                };

                return cell;
            }
        }));

        lista.setItems(items);

        Button btnImportar = new Button("Importar");
        boxButtons.getChildren().add(0, btnImportar);
        btnImportar.setOnAction((ActionEvent event) ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG"));
            if (!tempDir.isEmpty())
            {
                fileChooser.setInitialDirectory(new File(tempDir));
            }

            File file = fileChooser.showOpenDialog(util);

            if (file != null)
            {
                try
                {
                    String nombre = inputDialog("Nombre imagen", "");
                    if (!nombre.isEmpty())
                    {
                        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(new File(this.path + System.getProperty("file.separator") + nombre +".jpg").getAbsolutePath()));
                        tempDir = file.getAbsolutePath().replace(file.getName(), "");
                    }
                }
                catch (IOException ex)
                {
                    Global.info.Log(ex);
                }
                lista.setVisible(false);
                Platform.runLater(() ->
                {
                    update();
                    lista.setVisible(true);

                });

            }
        });

    }

}
