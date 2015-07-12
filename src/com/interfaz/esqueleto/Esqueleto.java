/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.interfaz.esqueleto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Esqueleto
{
    protected Stage st;
    public Scene scene;
    public final String dirTrabajo;
    private final String nombre;
    
    
    public Stage getStage()
    {
        return st;
    }
    
    public void setStage(Stage st)
    {
        this.st=st;
    }

    public Scene getScene()
    {
        return scene;
    }
    
    public void setScene(Scene scene)
    {
        this.scene=scene;
    }
                
    
         
    
    public Esqueleto(String dirTrabajo, String nombre, Stage st, Scene sc)
    {
        this.st=st;
        this.scene=sc;
        if(!dirTrabajo.endsWith(System.getProperty("file.separator"))) dirTrabajo+=System.getProperty("file.separator");
        this.dirTrabajo=dirTrabajo;
        this.nombre=nombre;
        
        Properties props = new Properties();
        try
        {
            try (InputStream configStream = Esqueleto.class.getResourceAsStream("log.properties"))
            {
                props.load(configStream);           
            }
            
            props.setProperty("log4j.appender.DAILYFILE.file", dirTrabajo + "log"+System.getProperty("file.separator")+nombre);
            LogManager.resetConfiguration();
            PropertyConfigurator.configure(props);
        }
        catch (IOException e)
        {
            PropertyConfigurator.configure(Esqueleto.class.getResource("log.properties"));
        }
    }        
       
    
}
