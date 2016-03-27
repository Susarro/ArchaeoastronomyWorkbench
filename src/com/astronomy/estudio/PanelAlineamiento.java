/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomy.estudio;

import com.ProcessException;
import com.Global;
import com.interfaz.Principal;
import com.components.ImageViewPane;
import com.components.BordererLabel;
import com.components.Led;
import com.astronomy.JulianDay;
import com.astronomy.Disk;
import com.astronomy.Sun;
import com.calendario.FiestasMediaEstacion;
import static com.PlanetEnum.MOON;
import static com.PlanetEnum.SUN;
import com.CalculusType;
import static com.CalculusType.PRECISE;
import com.units.SexagesimalDegree;
import static com.units.SexagesimalDegree.cos;
import static com.units.Tools.cosine;
import static com.units.Tools.sine;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.Math.abs;
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

/**
 *
 * @author MIGUEL_ANGEL
 */
public class PanelAlineamiento extends TabPane
{

    private final Estudio estudio;
    private final Principal principal;
    ImageView vistaImagen = null;
    private Alineamiento alineamiento;
    SexagesimalDegree DESolsticioInvierno;
    SexagesimalDegree DESolsticioVerano;
    SexagesimalDegree DEImbolcSamain;
    SexagesimalDegree DEBeltaineLugnasad;
    SexagesimalDegree DEEquinoccios;
    SexagesimalDegree DELunasticioMayorNorte;
    SexagesimalDegree DELunasticioMayorSur;
    SexagesimalDegree DELunasticioMenorNorte;
    SexagesimalDegree DELunasticioMenorSur;
    JulianDay dia;
    private TextField tfAnno;
    private ComboBox lista;

    Tab tabDetalles = new Tab();
    Tab tabValidacion = new Tab();
    BordererLabel labelNombreOrigen = new BordererLabel();
    BordererLabel labelCoordenadasOrigen = new BordererLabel();
    BordererLabel labelNombreReferencia = new BordererLabel();
    BordererLabel labelCoordenadasReferencia = new BordererLabel();
    BordererLabel labelAcimut = new BordererLabel();
    BordererLabel labelElevacion = new BordererLabel();
    BordererLabel labelDeclinacion = new BordererLabel();

    public void setData(Alineamiento alinemiento)
    {
        this.alineamiento = alinemiento;
        labelNombreOrigen.setText(alinemiento.getOrigen().getNombre());
        labelNombreReferencia.setText(alinemiento.getReferencia().getNombre());
        if (alinemiento.getOrigen().getCoordenadas() != null)
        {
            labelCoordenadasOrigen.setText(alinemiento.getOrigen().getCoordenadas().toString());
        }
        else
        {
            labelCoordenadasOrigen.setText("");
        }
        if (alinemiento.getReferencia().getCoordenadas() != null)
        {
            labelCoordenadasReferencia.setText(alinemiento.getReferencia().getCoordenadas().toString());
        }
        else
        {
            labelCoordenadasReferencia.setText("");
        }
        labelAcimut.setText(alinemiento.getDireccion().getAzimuth().toString());
        labelElevacion.setText(String.format("%.1fº", alinemiento.getDireccion().getAltitude().getSignedValue()).replace(",", "."));
        labelDeclinacion.setText(alinemiento.getDeclinacion().toString());
        if (alineamiento.getImagenPath().isEmpty())
        {
            vistaImagen.setImage(null);
        }
        else
        {
            try
            {
                File f = new File(estudio.getDirTrabajo()
                        + System.getProperty("file.separator")
                        + "imagenes"
                        + System.getProperty("file.separator")
                        + alineamiento.getImagenPath());
                BufferedInputStream bis;
                Image image = new Image(bis=new BufferedInputStream(new FileInputStream(estudio.getDirTrabajo()+"imagenes"+System.getProperty("file.separator")+f.getName())));                                                
                vistaImagen.setImage(image);
                bis.close();
                //vistaImagen.setCache(true);
            }
            catch (IOException ex)
            {
                Global.info.Log(ex);
            }
        }

        lista.getSelectionModel().clearSelection();

        FenomenoDatado f = Estudio.checkFenomeno(alinemiento.getDescripcion());
        if (f != null)
        {
            tfAnno.setText(String.valueOf(f.getAnno()));
            if (f.getFenomeno() != null)
            {
                lista.getSelectionModel().select(f.getFenomeno());
            }
        }
    }

    public void updateAnno(int anno)
    {
        try
        {
            FiestasMediaEstacion fme = new FiestasMediaEstacion(anno);
            dia = fme.solsticioVerano;
            Fenomeno.BELTAINE_LUGNASAD.setDeclinacion(new SexagesimalDegree((Sun.getApparentPosition(fme.beltaine, CalculusType.PRECISE).getDeclination().getSignedValue()
                    + Sun.getApparentPosition(fme.lugnasad, CalculusType.PRECISE).getDeclination().getSignedValue()) / 2));
            Fenomeno.IMBOLC_SAMAIN.setDeclinacion(new SexagesimalDegree((Sun.getApparentPosition(fme.imbolc, CalculusType.PRECISE).getDeclination().getSignedValue()
                    + Sun.getApparentPosition(fme.samain, CalculusType.PRECISE).getDeclination().getSignedValue()) / 2));
            Fenomeno.SOLSTICIO_INVIERNO.setDeclinacion(new SexagesimalDegree(Sun.getApparentPosition(fme.solsticioInvierno, CalculusType.PRECISE).getDeclination().getSignedValue()));
            Fenomeno.SOLSTICIO_VERANO.setDeclinacion(new SexagesimalDegree(Sun.getApparentPosition(fme.solsticioVerano, CalculusType.PRECISE).getDeclination().getSignedValue()));
            Fenomeno.LUNASTICIO_MAYOR_NORTE.setDeclinacion(dia.getTrueObliquityEcliptic(PRECISE).plus(new SexagesimalDegree(5.145)));
            Fenomeno.LUNASTICIO_MAYOR_SUR.setDeclinacion(SexagesimalDegree.negative(dia.getTrueObliquityEcliptic(PRECISE)).minus(new SexagesimalDegree(5.145)));
            Fenomeno.LUNASTICIO_MENOR_NORTE.setDeclinacion(dia.getTrueObliquityEcliptic(PRECISE).minus(new SexagesimalDegree(5.145)));
            Fenomeno.LUNASTICIO_MENOR_SUR.setDeclinacion(SexagesimalDegree.negative(dia.getTrueObliquityEcliptic(PRECISE)).plus(new SexagesimalDegree(5.145)));

        }
        catch (ProcessException ex)
        {

        }
    }

    private void interfazValidacion()
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

        lista = new ComboBox(FXCollections.observableArrayList(
                Fenomeno.values()));
        tfAnno = new TextField("-2000");
        updateAnno(Integer.valueOf(tfAnno.getText()));
        lista.setMaxWidth(Double.MAX_VALUE);
        tfAnno.setMaxWidth(Double.MAX_VALUE);

        Led ledValidacion = new Led();
        ledValidacion.setPrefWidth(30);
        ledValidacion.setPrefHeight(30);

        rejilla.add(new Label("Fenómeno"), 0, 0);
        rejilla.add(lista, 0, 1);
        rejilla.add(new Label("Año"), 1, 0);
        rejilla.add(tfAnno, 1, 1);
        rejilla.add(ledValidacion, 2, 1);
        rejilla.add(lError, 2, 2);

        vbox.getChildren().addAll(rejilla);
        tabValidacion.setContent(vbox);

        lista.setOnAction((Event event) ->
        {
            SexagesimalDegree variacionAcimut;
            try
            {
                Fenomeno fenomeno = (Fenomeno) lista.getSelectionModel().getSelectedItem();
                if (fenomeno == null)
                {
                    lError.setText("");
                    ledValidacion.setOn(false);
                    return;
                }
                SexagesimalDegree declinacionAstronomica = null;
                switch (fenomeno)
                {
                    case NINGUNO:
                        lError.setText("");
                        ledValidacion.setOn(false);
                        alineamiento.setDescripcion("");
                        estudio.setCambio(true);
                        return;
                    case BELTAINE_LUGNASAD:
                    case IMBOLC_SAMAIN:
                    case SOLSTICIO_INVIERNO:
                    case SOLSTICIO_VERANO:
                        declinacionAstronomica = alineamiento
                                .getDireccion().toTrue(SUN, Disk.UPPER_LIMB)
                                .toEquatorial(
                                        alineamiento.getOrigen().getCoordenadas().getLatitude(),
                                        alineamiento.getOrigen().getCoordenadas().getLongitude(),
                                        dia)
                                .getDeclination().Reduction();

                        break;
                    case LUNASTICIO_MAYOR_NORTE:
                    case LUNASTICIO_MAYOR_SUR:
                    case LUNASTICIO_MENOR_SUR:
                    case LUNASTICIO_MENOR_NORTE:
                        declinacionAstronomica = alineamiento
                                .getDireccion().toTrue(MOON, Disk.UPPER_LIMB)
                                .toEquatorial(
                                        alineamiento.getOrigen().getCoordenadas().getLatitude(),
                                        alineamiento.getOrigen().getCoordenadas().getLongitude(),
                                        dia)
                                .getDeclination();

                        break;
                }

                SexagesimalDegree L = alineamiento.getOrigen().getCoordenadas().getLatitude();
                SexagesimalDegree l = alineamiento.getOrigen().getCoordenadas().getLongitude();
                SexagesimalDegree A = alineamiento.getDireccion().getAzimuth();
                SexagesimalDegree h = alineamiento.getDireccion().getAltitude();
                SexagesimalDegree variacionDeclinacion = fenomeno.getDeclinacion().minus(declinacionAstronomica).Reduction();
                variacionAcimut = new SexagesimalDegree(abs(cosine(declinacionAstronomica) * variacionDeclinacion.getSignedValue() / (sine(A) * cos(L) * cosine(h))));
                lError.setText(String.format("%dº%02d'", variacionAcimut.getDegrees(), variacionAcimut.getMinutes()));
                SexagesimalDegree error = SexagesimalDegree.valueOf(lError.getText());
                if (error.getSignedValue() <= 1)
                {
                    ledValidacion.setLedColor(Color.LIME);
                    alineamiento.setDescripcion(fenomeno.toString() + "," + tfAnno.getText());
                    estudio.setCambio(true);
                }
                else
                {
                    ledValidacion.setLedColor(Color.RED);
                }

                ledValidacion.setOn(true);

            }
            catch (ProcessException ex)
            {

            }
        });

        tfAnno.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {
            try
            {
                updateAnno(Integer.valueOf(tfAnno.getText()));
            }
            catch (NumberFormatException ex)
            {

            }
        });

    }

    public void interfazDetalles()
    {
        VBox panel = new VBox();
        tabDetalles.setContent(panel);
        HBox.setHgrow(panel, Priority.ALWAYS);
        Text t;
        panel.setPadding(new Insets(10));
        panel.getChildren().add(t = new Text("Origen"));
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
        gridPane.add(new Label("Nombre"), 0, 0);
        gridPane.add(new Label("Coordenadas"), 0, 1);
        gridPane.add(labelNombreOrigen, 1, 0);
        gridPane.add(labelCoordenadasOrigen, 1, 1);
        panel.getChildren().add(t = new Text("Referencia"));
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
        gridPane.add(new Label("Nombre"), 0, 0);
        gridPane.add(new Label("Coordenadas"), 0, 1);
        gridPane.add(labelNombreReferencia, 1, 0);
        gridPane.add(labelCoordenadasReferencia, 1, 1);
        panel.getChildren().add(gridPane);
        panel.getChildren().add(t = new Text("Alineamiento"));
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
        gridPane.add(new Label("Acimut"), 0, 0);
        gridPane.add(new Label("Elevacion"), 0, 1);
        gridPane.add(new Label("Declinacion"), 0, 2);
        gridPane.add(labelAcimut, 1, 0);
        gridPane.add(labelElevacion, 1, 1);
        gridPane.add(labelDeclinacion, 1, 2);
        panel.getChildren().add(gridPane);

        Button btnActualizar;
        Button btnBorrar;
        HBox h;
        panel.getChildren().addAll(t = new Text("Imagen"), new Label(""));
        t.setFont(Font.font("Verdana", 16));
        t.setFill(Color.ORANGE);
        ImageViewPane v;
        vistaImagen = new ImageView();
        vistaImagen.setOnMouseClicked((MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                Tab tab = principal.NuevoTab(alineamiento.getOrigen().getNombre() + "-" + alineamiento.getReferencia().getNombre());
                ImageViewPane ivp = new ImageViewPane(new ImageView(vistaImagen.getImage()));
                tab.setContent(ivp);
            }
        });
        panel.getChildren().add(v = new ImageViewPane(vistaImagen));
        panel.getChildren().addAll(h = new HBox(10, btnBorrar = new Button("Borrar"), btnActualizar = new Button("Actualizar")), new Label(""));
        h.setAlignment(Pos.BOTTOM_RIGHT);
        HBox.setHgrow(v, Priority.ALWAYS);
        HBox.setHgrow(vistaImagen, Priority.ALWAYS);
        VBox.setVgrow(v, Priority.ALWAYS);
        vistaImagen.fitWidthProperty().bind(v.widthProperty());
        vistaImagen.setPreserveRatio(true);
        HBox.setHgrow(v, Priority.ALWAYS);

        btnBorrar.setOnAction((ActionEvent event) ->
        {
            vistaImagen.setImage(null);
            alineamiento.setImagenPath("");
        });

        btnActualizar.setOnAction((ActionEvent event) ->
        {
            ImageManager im = new ImageManager(principal.skeleton, estudio.getDirTrabajo());            
            if (im.ShowModal())
            {
                try
                {                                                                                
                    BufferedInputStream bis;                                    
                    vistaImagen.setImage(new Image(bis=new BufferedInputStream(new FileInputStream(estudio.getDirTrabajo()+"imagenes"+System.getProperty("file.separator")+im.getImagen().getName()))));    
                    bis.close();
                    //vistaImagen.setCache(true);                                       
                    alineamiento.setImagenPath(im.getImagen().getName());                                       
                }
                //catch (IOException | NullPointerException ex)
                catch (Exception ex)
                {
                    Global.info.Log(ex);
                }

            }
        });
    }

    public PanelAlineamiento(Principal principal, Estudio estudio)
    {
        this.estudio = estudio;
        this.principal = principal;
        HBox.setHgrow(this, Priority.ALWAYS);

        tabDetalles.setText("Detalles");
        tabDetalles.setClosable(false);
        getTabs().add(tabDetalles);
        interfazDetalles();

        tabValidacion.setText("Validación");
        tabValidacion.setClosable(false);
        getTabs().add(tabValidacion);
        interfazValidacion();

    }
}
