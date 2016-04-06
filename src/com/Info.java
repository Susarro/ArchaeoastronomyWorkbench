/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

/**
 *
 * @author MIGUEL_ANGEL
 */
public class Info
{
    /**
     * Logger
     */
    public static Logger logger;
    
    /**
     * Class name
     */
    String className;
    
    /**
     * Text area for log messages
     */
    protected TextArea textArea = null;
    /**
     * Log items
     */
    ObservableList<String> logItems = FXCollections.observableArrayList();
    /**
     * List view
     */
    ListView<String> list = new ListView<>();
    
    
 /**
  * 
  * @param className Class name
  */
    public Info(String className)
    {
        this.className = className;
        logger = Logger.getLogger(className);
        textArea = new TextArea();
        textArea.setPrefRowCount(100);
        textArea.setWrapText(true);
        textArea.setPrefWidth(1000);
        logItems = FXCollections.observableArrayList();
        list = new ListView<>();

        list.setItems(logItems);

        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
            @Override
            public ListCell<String> call(ListView<String> p)
            {
                ListCell<String> cell = new ListCell<String>()
                {
                    @Override
                    protected void updateItem(String t, boolean bln)
                    {
                        super.updateItem(t, bln);
                        Rectangle rect = new Rectangle(100, 20);
                        if (t != null)
                        {
                            setText(t);
                            if (t.contains("INFO"))
                            {
                                setTextFill(Color.DARKBLUE);
                            }
                            else if (t.contains("ERROR"))
                            {
                                setTextFill(Color.RED);
                            }
                            else if (t.contains("WARNING"))
                            {
                                setTextFill(Color.ORANGE);
                            }
                            else if (t.contains("DEBUG"))
                            {
                                setTextFill(Color.GRAY);
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    /**
     * 
     * @return list view
     */
    public ListView getListView()
    {
        return list;
    }

    /**
     * 
     * @param e Exception
     * @return Stack trace of exception
     */
    public static String getStackTrace(Exception e)
    {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        return sWriter.toString();
    }

    /**
     * Message thread
     */
    class Msg implements Runnable
    {
/**
 * Message
 */
        private final String msg;

        /**
         * 
         * @param msg Exception message 
         */
        public Msg(String msg)
        {
            this.msg = msg;
        }

        @Override
        public void run()
        {
            logItems.add(msg);
            if (logItems.size() > 20)
            {
                logItems.remove(0);
            }
            list.scrollTo(logItems.size());
        }

    }

    public void log(String text, InfoOption infoOption)
    {
        long millisecs = System.currentTimeMillis();
        Timestamp ts = new java.sql.Timestamp(millisecs);

        String strTipo = "";

        if (infoOption == InfoOption.DEBUG)
        {
            logger.debug(text);
            strTipo = "DEBUG";
        }
        if (infoOption == InfoOption.INFO)
        {
            logger.info(text);
            strTipo = "INFO";
        }
        else if (infoOption == InfoOption.WARNING)
        {
            logger.warn(text);
            strTipo = "WARNING";
        }
        else if (infoOption == InfoOption.ERROR)
        {
            logger.error(text);
            strTipo = "ERROR";
        }
       
        Platform.runLater(new Msg(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\t" + strTipo + "\t" + text));

        
    }
    /**
     * 
     * @param text Debug text
     */    
    public void debug(String text)
    {
        log(text,InfoOption.DEBUG);
    }
    
    /**
     * 
     * @param text Information text
     */
    public void info(String text)
    {
        log(text,InfoOption.INFO);
    }
    
    /**
     * 
     * @param text Warning text
     */
    public void warning(String text)
    {
        log(text,InfoOption.WARNING);
    }
    
    /**
     * 
     * @param text Error text
     */
    public void error(String text)
    {
        log(text,InfoOption.ERROR);
    }
   
    public void log(Exception e)
    {
        log("Exception: " + getStackTrace(e), InfoOption.ERROR);
    }
}
