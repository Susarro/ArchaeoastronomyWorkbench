/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Global;
import com.interfaz.esqueleto.Esqueleto;
import com.interfaz.esqueleto.ModalDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class ImageManager extends ModalDialog
{

    private final String path;
    String tempDir;
    private final ListView lista;

    public class MuestraImagen
    {

        private File fichero;

        public MuestraImagen(File f)
        {
            this.fichero = f;
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
                items.add(new MuestraImagen(f));
            }
        }
    }

    public ImageManager(Esqueleto padre, String p)
    {
        super(padre, new VBox(10), true);
        this.path = p + System.getProperty("file.separator") + "imagenes";
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
                    ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(MuestraImagen t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        if (t != null)
                        {
                            BufferedImage bufferedImage;
                            try
                            {
                                imageView.setFitHeight(300 / 1.8);
                                imageView.setFitWidth(300);
                                bufferedImage = ImageIO.read(t.getFichero());
                                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                imageView.setImage(image);
                                setGraphic(imageView);
                            }
                            catch (IOException ex)
                            {

                            }

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
                    Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(new File(this.path + System.getProperty("file.separator") + file.getName()).getAbsolutePath()));
                    tempDir = file.getAbsolutePath().replace(file.getName(), "");
                }
                catch (IOException ex)
                {
                    Global.info.Registra(ex);
                }
                update();
            }
        });

    }

}
