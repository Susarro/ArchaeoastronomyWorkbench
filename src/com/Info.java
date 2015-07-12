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

    public static Logger logger;
    
    String clase;
    protected TextArea areaTexto = null;
    ObservableList<String> items = FXCollections.observableArrayList(
            "Single", "Double", "Suite", "Family App");
    ListView<String> list = new ListView<>();
    
    

    public Info(String clase)
    {
        this.clase = clase;
        logger = Logger.getLogger(clase);
        areaTexto = new TextArea();
        areaTexto.setPrefRowCount(100);
        areaTexto.setWrapText(true);
        areaTexto.setPrefWidth(1000);
        items = FXCollections.observableArrayList();
        list = new ListView<>();

        list.setItems(items);

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
                            else if (t.contains("AVISO"))
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

    public ListView getListView()
    {
        return list;
    }

    public static String getStackTrace(Exception e)
    {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        return sWriter.toString();
    }

    class Msg implements Runnable
    {

        private final String msg;

        public Msg(String msg)
        {
            this.msg = msg;
        }

        @Override
        public void run()
        {
            items.add(msg);
            if (items.size() > 20)
            {
                items.remove(0);
            }
            list.scrollTo(items.size());
        }

    }

    public void Registra(String texto, tipoInfo tipo)
    {
        long millisecs = System.currentTimeMillis();
        Timestamp ts = new java.sql.Timestamp(millisecs);

        String strTipo = "TIPO";

        if (tipo == tipoInfo.DEBUG)
        {
            logger.debug(texto);
            strTipo = "DEBUG";
        }
        if (tipo == tipoInfo.INFO)
        {
            logger.info(texto);
            strTipo = "INFO";
        }
        else if (tipo == tipoInfo.AVISO)
        {
            logger.warn(texto);
            strTipo = "AVISO";
        }
        else if (tipo == tipoInfo.ERROR)
        {
            logger.error(texto);
            strTipo = "ERROR";
        }
        //final String msg = ffh.format(new Date()) + "\t" + strTipo + "\t" + texto;
        Platform.runLater(new Msg(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\t" + strTipo + "\t" + texto));

        
    }
        
    public void Debug(String texto)
    {
        Registra(texto,tipoInfo.DEBUG);
    }
    
    public void Info(String texto)
    {
        Registra(texto,tipoInfo.INFO);
    }
    
    public void Aviso(String texto)
    {
        Registra(texto,tipoInfo.AVISO);
    }
    
    public void Error(String texto)
    {
        Registra(texto,tipoInfo.ERROR);
    }
   
    public void Registra(Exception e)
    {
        Registra("Ocurrió una excepción: " + getStackTrace(e), tipoInfo.ERROR);
    }
}
