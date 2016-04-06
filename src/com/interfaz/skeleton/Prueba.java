/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

import com.Global;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Prueba extends Application
{

    Skeleton esqueleto;
    VBox raiz;
    private Button btn;

    @Override
    public void start(Stage primaryStage)
    {
        esqueleto = new Skeleton(System.getProperty("user.home") + "\\N2ROLDAN", "prueba", primaryStage, null);
        raiz = new VBox();
        Scene scene = new Scene(raiz);
        scene.getStylesheets().add(Skeleton.class.getResource("general.css").toExternalForm());
        esqueleto.setScene(scene);
        primaryStage.setTitle("Prueba");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        raiz.getStyleClass().add("fondo");
        primaryStage.show();
        RoundButton rb;
        HBox h = new HBox(10);
        //h.getChildren().addAll(new Label("Prueba"), new CheckBox("Pica"), new TitledPane("Hola", new Label("Hola")));
        GridPane grid = new GridPane();
        h.getChildren().addAll(new BorderedTitledPane("Hola", grid));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(new Label("Patata"), 0, 0);
        grid.add(new Label("Castaña"), 0, 1);
        grid.add(new Label("Melón"), 1, 0);
        grid.add(new Label("Pimiento"), 1, 1);
        raiz.getChildren().addAll(new Label("Hola"), h, new ComboBox(FXCollections.observableArrayList("Todos", "A", "B", "C", "D", "E", "F")), btn = new Button("Hola"));
        btn.setTooltip(new Tooltip("Hola"));
        Menu m;
        MenuItem mi;
        MenuBar menu=new MenuBar();
        menu.getMenus().add(m = new Menu("Menú"));
        CheckMenuItem cmi = new CheckMenuItem("Check");
        m.getItems().add(cmi);
        mi = new MenuItem("Normal");
        m.getItems().add(mi);
        
        raiz.getChildren().add(menu);

        Global.info.info("Hola");

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
