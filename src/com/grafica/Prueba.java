/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grafica;

import com.interfaz.esqueleto.Esqueleto;
import static java.lang.Math.random;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Prueba extends Application
{

    Esqueleto esqueleto;
    VBox raiz;
    private Button btn;

    @Override
    public void start(Stage primaryStage)
    {
        esqueleto = new Esqueleto(System.getProperty("user.home") + "\\N2ROLDAN", "prueba", primaryStage, null);
        raiz = new VBox();
        Scene scene = new Scene(raiz);
        scene.getStylesheets().add(Esqueleto.class.getResource("general.css").toExternalForm());
        esqueleto.scene = scene;
        primaryStage.setTitle("Prueba");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        raiz.getStyleClass().add("fondo");
        
        List<CategoriaGrafica> categorias = new ArrayList<CategoriaGrafica>();
        CategoriaGrafica cg;
        categorias.add(cg = new CategoriaGrafica("Patata"));
        cg.listaConfigSerie.add(new SimpleConfigSerie("1 Esto es una prueba muy muy larga"));
        cg.listaConfigSerie.add(new SimpleConfigSerie("2  Esto es una prueba muy muy larga"));

        categorias.add(cg = new CategoriaGrafica("Castaña"));
        cg.listaConfigSerie.add(new SimpleConfigSerie("3"));
        categorias.add(cg = new CategoriaGrafica("Cebolla"));
        cg.listaConfigSerie.add(new SimpleConfigSerie("4"));
        categorias.add(cg = new CategoriaGrafica("Morcilla"));
        cg.listaConfigSerie.add(new SimpleConfigSerie("5"));

        GraficaSwingTiempoAuto grafica = new GraficaSwingTiempoAuto("Prueba",null, categorias, "Prueba", "dd/MM/yy HH:mm:ss", Duration.seconds(1))
        {
            @Override
            public void addMuestra()
            {
                addMuestra(LocalDateTime.now(), new double[]
                {
                    random() * 20, random() * 20, random() * 20, random() * 20, random() * 20
                });
            }
        };
        grafica.setMaxItemAge(60000);
        raiz.getChildren().add(grafica.getGrafica());
        primaryStage.show();
        
        
       

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
