/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Image view pane
 */
public class ImageViewPane extends Region
{

    /**
     * Image view property
     */
    final private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<>();

    /**
     *
     * @return image view property
     */
    public ObjectProperty<ImageView> imageViewProperty()
    {
        return imageViewProperty;
    }

    /**
     *
     * @return Image view
     */
    public ImageView getImageView()
    {
        return imageViewProperty.get();
    }

    /**
     *
     * @param imageView Image view to set
     */
    public void setImageView(ImageView imageView)
    {
        this.imageViewProperty.set(imageView);
    }

    /**
     * Constructor
     */
    public ImageViewPane()
    {
        this(new ImageView());
    }

    /**
     * Constructor
     *
     * @param imageView An image view
     */
    public ImageViewPane(ImageView imageView)
    {
        imageViewProperty.addListener(new ChangeListener<ImageView>()
        {

            @Override
            public void changed(ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV)
            {
                if (oldIV != null)
                {
                    getChildren().remove(oldIV);
                }
                if (newIV != null)
                {
                    getChildren().add(newIV);
                }
            }
        });
        this.imageViewProperty.set(imageView);
    }

    /**
     * Layout children
     */
    @Override
    protected void layoutChildren()
    {
        try
        {
            ImageView imageView = imageViewProperty.get();
            if (imageView != null)
            {
                imageView.setFitWidth(getWidth());
                imageView.setFitHeight(getHeight());
                layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
            }
        }
        catch (Exception ex)
        {

        }
        super.layoutChildren();
    }

}
