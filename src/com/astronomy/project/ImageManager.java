/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.project;

import com.Global;
import com.interfaz.skeleton.Skeleton;
import com.interfaz.skeleton.ModalDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * Image manager
 *
 * @author MIGUEL_ANGEL
 */
public class ImageManager extends ModalDialog
{

    /**
     * Image path
     */
    private final String path;
    /**
     * temporal directory
     */
    static String tempDir;
    /**
     * Image list view
     */
    private final ListView imageList;

    /**
     * If true image file selected and found
     */
    boolean ok = false;

    /**
     * maximum inner list view cell width
     */
    double maxWidth = 0;

    /**
     * Enter image name. If image file is already loaded, background color
     * changes to red and process is aborted
     *
     * @param text Message to user
     * @param value default text value
     * @return user input
     */
    public String inputDialog(String text, String value)
    {
        HBox p = new HBox(10);
        p.setAlignment(Pos.CENTER);
        TextField tf;
        p.getChildren().addAll(new Label(text), tf = new TextField(value));

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                -> 
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

        ModalDialog dialog = new ModalDialog(null, p, true);
        if (dialog.showModal() && ok)
        {
            return tf.getText();
        }
        else
        {
            return "";
        }
    }

    /**
     * Obtain a thumbsnail from an image file
     */
    public class Thumbsnail
    {

        /**
         * File
         */
        private final File file;
        /**
         * Image view
         */
        public ImageView thumbsnail;

        /**
         *
         * @param f Image file
         * @throws FileNotFoundException File not found error
         */
        public Thumbsnail(File f) throws FileNotFoundException, IOException
        {
            this.file = f;
            FileInputStream fis;
            thumbsnail = new ImageView(new Image(fis=new FileInputStream(f), 300, 0, true, true));
            fis.close();
        }

        /**
         * @return file
         */
        public File getFile()
        {
            return file;
        }

    }

    /**
     * Observable list of thumbsnails
     */
    ObservableList<Thumbsnail> items = FXCollections.observableArrayList();

    /**
     *
     * @return selected image file
     */
    public File getImageFile()
    {
        try
        {
            return ((Thumbsnail) imageList.getSelectionModel().getSelectedItem()).getFile();
        }
        catch (NullPointerException ex)
        {
            return null;
        }
    }

    /**
     * Update list view
     */
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
                    items.add(new Thumbsnail(f));
                }
                catch (IOException ex)
                {
                    Global.info.log(ex);
                }
                
                //Global.info.Info(f.getName());
            }
        }
    }

    /**
     * Thumbsnail cell
     */
    class ThumbsnailCell extends ListCell<Thumbsnail>
    {

        VBox v = new VBox(10);
        Label label = new Label("");
        ImageView imagen = new ImageView();

        public ThumbsnailCell()
        {
            v.getChildren().addAll(label, imagen);
        }

        @Override
        protected void updateItem(Thumbsnail t, boolean bln)
        {
            if (t != null)
            {
                imagen.setImage(t.thumbsnail.getImage());
                setGraphic(v);
            }
        }
    }

    /**
     *
     * @return vertical scroll bar of listview
     */
    private ScrollBar getVerticalScrollbar()
    {
        ScrollBar scrollBar = null;
        for (Node n : imageList.lookupAll(".scroll-bar"))
        {
            if (n instanceof ScrollBar)
            {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation() == javafx.geometry.Orientation.VERTICAL)
                {
                    scrollBar = bar;
                }
            }
        }
        return scrollBar;
    }

    /**
     *
     * @param parent Skeleton parent
     * @param workDir Work dir
     */
    public ImageManager(Skeleton parent, String workDir)
    {
        super(parent, new VBox(10), true);
        this.path = workDir + System.getProperty("file.separator") + "imagenes";
        new File(this.path).mkdirs();
        imageList = new ListView(items);
        HBox.setHgrow(imageList, Priority.ALWAYS);
        HBox.setHgrow(userPane, Priority.ALWAYS);

        userPane.getChildren().add(imageList);
        update();

        imageList.setCellFactory((new Callback<ListView<Thumbsnail>, ListCell<Thumbsnail>>()
        {
            @Override
            public ListCell<Thumbsnail> call(ListView<Thumbsnail> p)
            {
                ListCell<Thumbsnail> cell = new ListCell<Thumbsnail>()
                {
                    @Override
                    protected void updateItem(Thumbsnail t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            VBox v = new VBox(5);
                            Label l;
                            v.getChildren().addAll(l = new Label(t.getFile().getName().split("\\.")[0]), t.thumbsnail);
                            l.getStyleClass().clear();
                            l.getStyleClass().add("label_negro");
                            setGraphic(v);
                            widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                                    -> 
                                    {
                                        double width = imageList.getInsets().getLeft() + imageList.getInsets().getRight() + getWidth();
                                        width += getVerticalScrollbar().getWidth();
                                        if (width > maxWidth)
                                        {
                                            maxWidth = width;
                                        }
                                        imageList.setPrefWidth(maxWidth);
                                        imageList.setMaxWidth(maxWidth);
                                        imageList.setMinWidth(maxWidth);
                                        stage.setWidth(maxWidth + userPane.getLayoutX() * 2);

                            });
                        }
                    }
                };

                return cell;
            }
        }));

        imageList.setItems(items);

        Button btnImportar = new Button("Import");
        Button btnDelete = new Button("Delete");
        boxButtons.getChildren().add(0, btnImportar);
        boxButtons.getChildren().add(btnDelete);
        btnDelete.setOnAction((ActionEvent event)
                -> 
                {
                    Thumbsnail t = (Thumbsnail) imageList.getSelectionModel().getSelectedItem();
                    try
                    {
                        t.getFile().delete();
                        imageList.setVisible(false);
                        Platform.runLater(()
                                -> 
                                {
                                    update();
                                    imageList.setVisible(true);

                        });
                    }
                    catch (Exception ex)
                    {
                        
                    }
        });

        btnImportar.setOnAction((ActionEvent event)
                -> 
                {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG"));
                    if (tempDir!=null)
                    {
                        fileChooser.setInitialDirectory(new File(tempDir));
                    }

                    File file = fileChooser.showOpenDialog(stage);

                    if (file != null)
                    {
                        try
                        {
                            String nombre = inputDialog("Imagen name", "");
                            if (!nombre.isEmpty())
                            {
                                Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(new File(this.path + System.getProperty("file.separator") + nombre + ".jpg").getAbsolutePath()));
                                tempDir = file.getAbsolutePath().replace(file.getName(), "");
                            }
                        }
                        catch (IOException ex)
                        {
                            Global.info.log(ex);
                        }
                        imageList.setVisible(false);
                        Platform.runLater(()
                                -> 
                                {
                                    update();
                                    imageList.setVisible(true);

                        });
                    }
        });
    }
}
