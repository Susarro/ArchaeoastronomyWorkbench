/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astronomia.estudio;

import com.Excepcion;
import com.Global;
import com.interfaz.Principal;
import com.FX.ImageViewPane;
import com.FX.LabelBorde;
import com.FX.Led;
import com.astronomia.DiaJuliano;
import com.astronomia.Disco;
import com.astronomia.Sol;
import com.calendario.FiestasMediaEstacion;
import static com.enumPlaneta.LUNA;
import static com.enumPlaneta.SOL;
import com.tipoCalculo;
import static com.tipoCalculo.PRECISO;
import com.unidades.Grados;
import static com.unidades.Grados.cos;
import static com.unidades.Herramienta.coseno;
import static com.unidades.Herramienta.seno;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;

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
    Grados DESolsticioInvierno;
    Grados DESolsticioVerano;
    Grados DEImbolcSamain;
    Grados DEBeltaineLugnasad;
    Grados DEEquinoccios;
    Grados DELunasticioMayorNorte;
    Grados DELunasticioMayorSur;
    Grados DELunasticioMenorNorte;
    Grados DELunasticioMenorSur;
    DiaJuliano dia;
    private TextField tfAnno;
    private ComboBox lista;

    

    Tab tabDetalles = new Tab();
    Tab tabValidacion = new Tab();
    LabelBorde labelNombreOrigen = new LabelBorde();
    LabelBorde labelCoordenadasOrigen = new LabelBorde();
    LabelBorde labelNombreReferencia = new LabelBorde();
    LabelBorde labelCoordenadasReferencia = new LabelBorde();
    LabelBorde labelAcimut = new LabelBorde();
    LabelBorde labelElevacion = new LabelBorde();
    LabelBorde labelDeclinacion = new LabelBorde();

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
        labelAcimut.setText(alinemiento.getDireccion().getAcimut().toString());
        labelElevacion.setText(String.format("%.1fº", alinemiento.getDireccion().getElevacion().getSignedValue()).replace(",", "."));
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
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                vistaImagen.setImage(image);
                vistaImagen.setCache(true);
            }
            catch (IOException ex)
            {
                Global.info.Registra(ex);
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
            Fenomeno.BELTAINE_LUGNASAD.setDeclinacion(new Grados((Sol.getPosicionAparente(fme.beltaine, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()
                    + Sol.getPosicionAparente(fme.lugnasad, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()) / 2));
            Fenomeno.IMBOLC_SAMAIN.setDeclinacion(new Grados((Sol.getPosicionAparente(fme.imbolc, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()
                    + Sol.getPosicionAparente(fme.samain, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()) / 2));
            Fenomeno.SOLSTICIO_INVIERNO.setDeclinacion(new Grados(Sol.getPosicionAparente(fme.solsticioInvierno, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()));
            Fenomeno.SOLSTICIO_VERANO.setDeclinacion(new Grados(Sol.getPosicionAparente(fme.solsticioVerano, tipoCalculo.PRECISO).getDeclinacion().getSignedValue()));
            Fenomeno.LUNASTICIO_MAYOR_NORTE.setDeclinacion(dia.getOblicuidadEcliptica(PRECISO).plus(new Grados(5.145)));
            Fenomeno.LUNASTICIO_MAYOR_SUR.setDeclinacion(Grados.negative(dia.getOblicuidadEcliptica(PRECISO)).minus(new Grados(5.145)));
            Fenomeno.LUNASTICIO_MENOR_NORTE.setDeclinacion(dia.getOblicuidadEcliptica(PRECISO).minus(new Grados(5.145)));
            Fenomeno.LUNASTICIO_MENOR_SUR.setDeclinacion(Grados.negative(dia.getOblicuidadEcliptica(PRECISO)).plus(new Grados(5.145)));

        }
        catch (Excepcion ex)
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
            Grados variacionAcimut;
            try
            {
                Fenomeno fenomeno = (Fenomeno) lista.getSelectionModel().getSelectedItem();
                if (fenomeno == null)
                {
                    lError.setText("");
                    ledValidacion.setOn(false);
                    return;
                }
                Grados declinacionAstronomica = null;
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
                                .getDireccion().toAstronomico(SOL, Disco.SUPERIOR)
                                .toEcuatorial(
                                        alineamiento.getOrigen().getCoordenadas().getLatitud(),
                                        alineamiento.getOrigen().getCoordenadas().getLongitud(),
                                        dia)
                                .getDeclinacion().AjusteSignado();

                        break;
                    case LUNASTICIO_MAYOR_NORTE:
                    case LUNASTICIO_MAYOR_SUR:
                    case LUNASTICIO_MENOR_SUR:
                    case LUNASTICIO_MENOR_NORTE:
                        declinacionAstronomica = alineamiento
                                .getDireccion().toAstronomico(LUNA, Disco.SUPERIOR)
                                .toEcuatorial(
                                        alineamiento.getOrigen().getCoordenadas().getLatitud(),
                                        alineamiento.getOrigen().getCoordenadas().getLongitud(),
                                        dia)
                                .getDeclinacion();

                        break;
                }

                Grados L = alineamiento.getOrigen().getCoordenadas().getLatitud();
                Grados l = alineamiento.getOrigen().getCoordenadas().getLongitud();
                Grados A = alineamiento.getDireccion().getAcimut();
                Grados h = alineamiento.getDireccion().getElevacion();
                Grados variacionDeclinacion = fenomeno.getDeclinacion().minus(declinacionAstronomica).AjusteSignado();
                variacionAcimut = new Grados(abs(coseno(declinacionAstronomica) * variacionDeclinacion.getSignedValue() / (seno(A) * cos(L) * coseno(h))));
                lError.setText(String.format("%dº%02d'", variacionAcimut.getGrados(), variacionAcimut.getMinutos()));
                Grados error = Grados.valueOf(lError.getText());
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
            catch (Excepcion ex)
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
            ImageManager im = new ImageManager(principal.esqueleto, estudio.getDirTrabajo());
            if (im.ShowModal())
            {
                try
                {
                    BufferedImage bufferedImage = ImageIO.read(im.getImagen());
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    vistaImagen.setImage(image);
                    vistaImagen.setCache(true);
                    alineamiento.setImagenPath(im.getImagen().getName());
                }
                catch (IOException | NullPointerException ex)
                {
                    Global.info.Registra(ex);
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
