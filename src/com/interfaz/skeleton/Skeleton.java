/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.interfaz.skeleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * A skeleton for javaFX programs
 * @author MIGUEL_ANGEL
 */
public class Skeleton
{
    /**
     * Main stage
     */
    protected Stage mainStage;
    /**
     * Main scene
     */
    public Scene mainScene;
    
    /**
     * Work directory
     */
    public final String workDir;
    /**
     * Name
     */
    private final String name;
    
    
    /**
     * 
     * @return main stage
     */
    public Stage getStage()
    {
        return mainStage;
    }
    
    /**
     * 
     * @param st Stage to set
     */
    public void setStage(Stage st)
    {
        this.mainStage=st;
    }

    /**
     * 
     * @return Main scene
     */
    public Scene getScene()
    {
        return mainScene;
    }
    
    /**
     * 
     * @param scene Scene to set
     */
    public void setScene(Scene scene)
    {
        this.mainScene=scene;
    }
                
    
         
    /**
     * 
     * @param workDir Work directory
     * @param name Name
     * @param st Main Stage
     * @param sc Main Scene
     */
    public Skeleton(String workDir, String name, Stage st, Scene sc)
    {
        this.mainStage=st;
        this.mainScene=sc;
        if(!workDir.endsWith(System.getProperty("file.separator"))) workDir+=System.getProperty("file.separator");
        this.workDir=workDir;
        this.name=name;
        
        Properties props = new Properties();
        try
        {
            try (InputStream configStream = Skeleton.class.getResourceAsStream("log.properties"))
            {
                props.load(configStream);           
            }
            
            props.setProperty("log4j.appender.DAILYFILE.file", workDir + "log"+System.getProperty("file.separator")+name);
            LogManager.resetConfiguration();
            PropertyConfigurator.configure(props);
        }
        catch (IOException e)
        {
            PropertyConfigurator.configure(Skeleton.class.getResource("log.properties"));
        }
    }        
       
    
}
