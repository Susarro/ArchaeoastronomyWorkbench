/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import static com.main.Main.skeleton;
import com.interfaz.skeleton.ProgressDialog;
import javafx.concurrent.Task;

/**
 * A template for calculation tasks
 * 
 * @author MIGUEL_ANGEL
 * @param <T> Time object
 */
public abstract class TemporalTaskTemplate<T> extends Task<String>
{
    /**
     * Progress dialog
     */
    ProgressDialog progressDialog;
    /**
    * Task thread
    */
    Thread t;
    
    /**
     * Task name
     */
    String name;
    
    /**
     * Time of start
     */
    private final T start;
    
    /**
     * Time of end
     */
    private final T end;
    /**
     * Time increment per cycle
     */
    private T increment;
    
    /**
     * Current time
     */
    private T current;

    /**
     * 
     * @return Current time
     */
    public T getCurrent()
    {
        return current;
    }

    /**
     * 
     * @param input Current time to set
     */
    public void setCurrent(T input)
    {
        current = input;
    }

    /**
     * 
     * @param input Increment time to set
     */
    public void setIncrement(T input)
    {
        increment = input;
    }

    /**
     * 
     * @param input Time to clone as current time
     */
    abstract public void cloneCurrent(T input);

    /**
     * 
     * @param input Time
     * @return Time as double
     */
    abstract public double doubleValue(T input);

    /**
     * 
     * @param input Time
     * @return Time as string
     */
    abstract public String toString(T input);

    /**
     * 
     * @param increment Time to add to current time
     */
    public abstract void addToCurrent(T increment);

    /**
     * Comapare times
     * @param time_1 Julian day 1
     * @param time_2 Julian day 2
     * @return a negative integer, zero, or a positive integer as time_1 is less than, equal to, or greater than time_2
     */
    public abstract int compare(T time_1, T time_2);

    /**
     *      
     * @param name Task name
     * @param start Start time
      * @param end End time
     * @param increment Increment time 
     */
    public TemporalTaskTemplate(String name, T start, T end, T increment)
    {
        progressDialog = new ProgressDialog(skeleton, this);
        this.name = name;
        this.start = start;
        this.end = end;
        this.increment = increment;
    }

    /**
     * Task start
     */
    public void taskStart()
    {
        t = new Thread(this);
        t.start();
        progressDialog.show();
    }

    @Override
    protected String call() throws Exception
    {
        initialize();
        return "finished";
    }

    @Override
    protected void succeeded()
    {
        super.succeeded();
        t.interrupt();
        progressDialog.OK();
        taskEnd();
    }

    /**
     * Task initialization 
     */
    private void initialize()
    {
        updateTitle(name);
        updateMessage("Initiating...");

        updateProgress(0, doubleValue(end) - doubleValue(start));

        cloneCurrent(start);

        while (compare(current, end) < 0)
        {
            if (isCancelled())
            {
                updateMessage("Cancelled");
                break;
            }
            cycle();
            updateProgress(doubleValue(current) - doubleValue(start), doubleValue(end) - doubleValue(start));
            updateMessage("Processing: " + toString(current));
            addToCurrent(increment);
        }

    }

    /**
     * Task end
     */
    abstract public void taskEnd();

    /**
     * Cyclic process
     */
    abstract public void cycle();
}
