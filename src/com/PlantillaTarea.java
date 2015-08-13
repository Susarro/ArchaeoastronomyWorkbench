/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.interfaz.esqueleto.DialogoProgreso;
import com.interfaz.esqueleto.Esqueleto;
import javafx.concurrent.Task;

/**
 *
 * @author MIGUEL_ANGEL
 * @param <T>
 */
public abstract class PlantillaTarea<T> extends Task<String>
{

    DialogoProgreso dialogoProgreso;
    Thread t;
    String nombre;
    private final T desde;
    private final T hasta;
    private T incremento;
    private T actual;

    public T getActual()
    {
        return actual;
    }

    public void setActual(T entrada)
    {
        actual = entrada;
    }

    public void setIncremento(T entrada)
    {
        incremento = entrada;
    }

    abstract public void cloneActual(T entrada);

    abstract public double doubleValue(T entrada);

    abstract public String toString(T entrada);

    public abstract void addActual(T incremento);

    public abstract int compara(T objeto, T referencia);

    public PlantillaTarea(Esqueleto esqueleto, String nombre, T desde, T hasta, T incremento)
    {
        dialogoProgreso = new DialogoProgreso(esqueleto, this);
        this.nombre = nombre;
        this.desde = desde;
        this.hasta = hasta;
        this.incremento = incremento;
    }

    public void Inicia()
    {
        t = new Thread(this);
        t.start();
        dialogoProgreso.Show();
    }

    @Override
    protected String call() throws Exception
    {
        inicia();
        return "terminado";
    }

    @Override
    protected void succeeded()
    {
        super.succeeded();
        t.interrupt();
        dialogoProgreso.Aceptar();
        terminado();
    }

    private void inicia()
    {
        updateTitle(nombre);
        updateMessage("Iniciando...");

        updateProgress(0, doubleValue(hasta) - doubleValue(desde));

        cloneActual(desde);

        while (compara(actual, hasta) < 0)
        {
            if (isCancelled())
            {
                updateMessage("Cancelled");
                break;
            }
            ciclo();
            updateProgress(doubleValue(actual) - doubleValue(desde), doubleValue(hasta) - doubleValue(desde));
            updateMessage("Procesando: " + toString(actual));
            addActual(incremento);
        }

    }

    abstract public void terminado();

    abstract public void ciclo();
}
