/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.LongProperty;
import javafx.beans.property.LongPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * Led
 * 
 */
public class Led extends Region
{

    private static final double PREFERRED_SIZE = 16;
    private static final double MINIMUM_SIZE = 8;
    private static final double MAXIMUM_SIZE = 1024;
    private static final long SHORTEST_INTERVAL = 50_000_000l;
    private static final long LONGEST_INTERVAL = 5_000_000_000l;

    private ObjectProperty<Color> ledColor;
    private BooleanProperty on;
    private boolean _blinking = false;
    private BooleanProperty blinking;
    private boolean _frameVisible = true;
    private BooleanProperty frameVisible;
    private InnerShadow ledOnShadow;
    private InnerShadow ledOffShadow;
    private LinearGradient frameGradient;
    private LinearGradient ledOnGradient;
    private LinearGradient ledOffGradient;
    private RadialGradient highlightGradient;
    private long lastTimerCall;
    private long _interval = 500_000_000l;
    private LongProperty interval;
    private AnimationTimer timer;

    private Canvas canvas;
    private GraphicsContext ctx;

   
    /**
     * Constructor
     */
    public Led()
    {
        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer()
        {
            @Override
            public void handle(final long NOW)
            {
                if (NOW > lastTimerCall + getInterval())
                {
                    setOn(!isOn());
                    lastTimerCall = NOW;
                }
            }
        };
        init();
        initGraphics();
        registerListeners();
    }

    /**
     * Initialization
     */
    private void init()
    {
        if (Double.compare(getWidth(), 0) <= 0 || Double.compare(getHeight(), 0) <= 0
                || Double.compare(getPrefWidth(), 0) <= 0 || Double.compare(getPrefHeight(), 0) <= 0)
        {
            setPrefSize(PREFERRED_SIZE, PREFERRED_SIZE);
        }
        if (Double.compare(getMinWidth(), 0) <= 0 || Double.compare(getMinHeight(), 0) <= 0)
        {
            setMinSize(MINIMUM_SIZE, MINIMUM_SIZE);
        }
        if (Double.compare(getMaxWidth(), 0) <= 0 || Double.compare(getMaxHeight(), 0) <= 0)
        {
            setMaxSize(MAXIMUM_SIZE, MAXIMUM_SIZE);
        }
    }

    /**
     * Init graphics
     */
    private void initGraphics()
    {
        canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();
        getChildren().add(canvas);
    }

    /**
     * Register listener
     */
    private void registerListeners()
    {
        widthProperty().addListener(observable -> recalc());
        heightProperty().addListener(observable -> recalc());
        frameVisibleProperty().addListener(observable -> draw());
        onProperty().addListener(observable -> draw());
        ledColorProperty().addListener(observable -> recalc());
    }

    /**
     * 
     * @return True if ON
     */
    public final boolean isOn()
    {
        return null == on ? false : on.get();
    }

    /**
     * 
     * @param ON ON/OFF state to set
     */
    public final void setOn(final boolean ON)
    {
        onProperty().set(ON);
    }

    /**
     * 
     * @return On property
     */
    public final BooleanProperty onProperty()
    {
        if (null == on)
        {
            on = new SimpleBooleanProperty(this, "on", false);
        }
        return on;
    }

    /**
     * 
     * @return True if blinking
     */
    public final boolean isBlinking()
    {
        return null == blinking ? _blinking : blinking.get();
    }

    /**
     * 
     * @param blnkng Blinking state to set
     */
    public final void setBlinking(final boolean blnkng)
    {
        if (null == blinking)
        {
            _blinking = blnkng;
            if (blnkng)
            {
                timer.start();
            }
            else
            {
                timer.stop();
                setOn(false);
            }
        }
        else
        {
            blinking.set(blnkng);
        }
    }

    /**
     * 
     * @return Blinking property
     */
    public final BooleanProperty blinkingProperty()
    {
        if (null == blinking)
        {
            blinking = new BooleanPropertyBase()
            {
                @Override
                public void set(final boolean BLINKING)
                {
                    super.set(BLINKING);
                    if (BLINKING)
                    {
                        timer.start();
                    }
                    else
                    {
                        timer.stop();
                        setOn(false);
                    }
                }

                @Override
                public Object getBean()
                {
                    return Led.this;
                }

                @Override
                public String getName()
                {
                    return "blinking";
                }
            };
        }
        return blinking;
    }

    /**
     * 
     * @return Interval
     */
    public final long getInterval()
    {
        return null == interval ? _interval : interval.get();
    }

    /**
     * 
     * @param ntrvl Interval to set 
     */
    public final void setInterval(final long ntrvl)
    {
        if (null == interval)
        {
            _interval = clamp(SHORTEST_INTERVAL, LONGEST_INTERVAL, ntrvl);
        }
        else
        {
            interval.set(ntrvl);
        }
    }

    /**
     * 
     * @return Interval property
     */
    public final LongProperty intervalProperty()
    {
        if (null == interval)
        {
            interval = new LongPropertyBase()
            {
                @Override
                public void set(final long INTERVAL)
                {
                    super.set(clamp(SHORTEST_INTERVAL, LONGEST_INTERVAL, INTERVAL));
                }

                @Override
                public Object getBean()
                {
                    return Led.this;
                }

                @Override
                public String getName()
                {
                    return "interval";
                }
            };
        }
        return interval;
    }

    /**
     * 
     * @return True if frame visible
     */
    public final boolean isFrameVisible()
    {
        return null == frameVisible ? _frameVisible : frameVisible.get();
    }

    /**
     * 
     * @param frmvsbl Visibility of frame to set
     */
    public final void setFrameVisible(final boolean frmvsbl)
    {
        if (null == frameVisible)
        {
            _frameVisible = frmvsbl;
        }
        else
        {
            frameVisible.set(frmvsbl);
        }
    }

    /**
     * 
     * @return Frame visibility property
     */
    public final BooleanProperty frameVisibleProperty()
    {
        if (null == frameVisible)
        {
            frameVisible = new SimpleBooleanProperty(this, "frameVisible", _frameVisible);
        }
        return frameVisible;
    }

    /**
     * 
     * @return Led color
     */
    public final Color getLedColor()
    {
        return null == ledColor ? Color.RED : ledColor.get();
    }

    /**
     * 
     * @param ld_clr Led color to set 
     */
    public final void setLedColor(final Color ld_clr)
    {
        ledColorProperty().set(ld_clr);
    }

    /**
     * 
     * @return Led color property
     */
    public final ObjectProperty<Color> ledColorProperty()
    {
        if (null == ledColor)
        {
            ledColor = new SimpleObjectProperty<>(this, "ledColor", Color.RED);
        }
        return ledColor;
    }

    /**
     * 
     * @param MIN Minimum value
     * @param MAX Maximum value
     * @param VALUE Value
     * @return Value limited by maximum ad minimum values
     */
    public static long clamp(final long MIN, final long MAX, final long VALUE)
    {
        if (VALUE < MIN)
        {
            return MIN;
        }
        if (VALUE > MAX)
        {
            return MAX;
        }
        return VALUE;
    }

   
    /***
     * Resize/Redraw
     */
    private void recalc()
    {
        double size = getWidth() < getHeight() ? getWidth() : getHeight();

        ledOffShadow = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 0.07 * size, 0, 0, 0);

        ledOnShadow = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 0.07 * size, 0, 0, 0);
        ledOnShadow.setInput(new DropShadow(BlurType.TWO_PASS_BOX, ledColor.get(), 0.36 * size, 0, 0, 0));

        frameGradient = new LinearGradient(0.14 * size, 0.14 * size,
                0.84 * size, 0.84 * size,
                false, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(20, 20, 20, 0.65)),
                new Stop(0.15, Color.rgb(20, 20, 20, 0.65)),
                new Stop(0.26, Color.rgb(41, 41, 41, 0.65)),
                new Stop(0.26, Color.rgb(41, 41, 41, 0.64)),
                new Stop(0.85, Color.rgb(200, 200, 200, 0.41)),
                new Stop(1.0, Color.rgb(200, 200, 200, 0.35)));

        ledOnGradient = new LinearGradient(0.25 * size, 0.25 * size,
                0.74 * size, 0.74 * size,
                false, CycleMethod.NO_CYCLE,
                new Stop(0.0, ledColor.get().deriveColor(0d, 1d, 0.77, 1d)),
                new Stop(0.49, ledColor.get().deriveColor(0d, 1d, 0.5, 1d)),
                new Stop(1.0, ledColor.get()));

        ledOffGradient = new LinearGradient(0.25 * size, 0.25 * size,
                0.74 * size, 0.74 * size,
                false, CycleMethod.NO_CYCLE,
                new Stop(0.0, ledColor.get().deriveColor(0d, 1d, 0.20, 1d)),
                new Stop(0.49, ledColor.get().deriveColor(0d, 1d, 0.13, 1d)),
                new Stop(1.0, ledColor.get().deriveColor(0d, 1d, 0.2, 1d)));

        highlightGradient = new RadialGradient(0, 0,
                0.3 * size, 0.3 * size,
                0.29 * size,
                false, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.WHITE),
                new Stop(1.0, Color.TRANSPARENT));
        draw();
    }

    /**
     * Draw
     */
    private void draw()
    {
        double width = getWidth();
        double height = getHeight();
        if (width <= 0 || height <= 0)
        {
            return;
        }

        double size = width < height ? width : height;

        canvas.setWidth(size);
        canvas.setHeight(size);
        if (width > height)
        {
            canvas.relocate(0.5 * (width - size), 0);
        }
        else if (height > width)
        {
            canvas.relocate(0, 0.5 * (height - size));
        }

        ctx.clearRect(0, 0, size, size);

        if (isFrameVisible())
        {
            ctx.setFill(frameGradient);
            ctx.fillOval(0, 0, size, size);
        }

        if (isOn())
        {
            ctx.save();
            ctx.setEffect(ledOnShadow);
            ctx.setFill(ledOnGradient);
            ctx.fillOval(0.14 * size, 0.14 * size, 0.72 * size, 0.72 * size);
            ctx.restore();
        }
        else
        {
            ctx.save();
            ctx.setEffect(ledOffShadow);
            ctx.setFill(ledOffGradient);
            ctx.fillOval(0.14 * size, 0.14 * size, 0.72 * size, 0.72 * size);
            ctx.restore();
        }

        ctx.setFill(highlightGradient);
        ctx.fillOval(0.21 * size, 0.21 * size, 0.58 * size, 0.58 * size);
    }
}
