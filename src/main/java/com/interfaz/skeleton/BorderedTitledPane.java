/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaz.skeleton;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Bordered titled pane
 * 
 */
public class BorderedTitledPane extends StackPane
{

    private StringProperty title = new SimpleStringProperty();
    private ObjectProperty<Node> graphic = new SimpleObjectProperty<>();
    private ObjectProperty<Node> content = new SimpleObjectProperty<>();
    private ObjectProperty<Pos> titleAlignment = new SimpleObjectProperty<>();
    // todo other than TOP_LEFT other alignments aren't really supported correctly, due to translation fudge for indentation of the title label in css => best to implement layoutChildren and handle layout there.
    // todo work out how to make content the default node for fxml so you don't need to write a <content></content> tag.

    public BorderedTitledPane()
    {
        this("", null);
    }

    /**
     * 
     * @param titleString Title
     * @param contentNode Content
     */
    public BorderedTitledPane(String titleString, Node contentNode)
    {
        final Label titleLabel = new Label();
        titleLabel.textProperty().bind(Bindings.concat(title, " "));
        titleLabel.getStyleClass().add("bordered-titled-title");
        titleLabel.graphicProperty().bind(graphic);

        titleAlignment.addListener((Observable observable) ->
        {
            StackPane.setAlignment(titleLabel, titleAlignment.get());
        });

        final StackPane contentPane = new StackPane();

        getStyleClass().add("bordered-titled-border");
        getChildren().addAll(titleLabel, contentPane);
        setMargin(titleLabel, new Insets(10,0,0,0));
        setMargin(contentPane, new Insets(10,0,0,0));

        content.addListener((Observable observable) ->
        {
            if (content.get() == null)
            {
                contentPane.getChildren().clear();
            }
            else
            {
                if (!content.get().getStyleClass().contains("bordered-titled-content"))
                {
                    content.get().getStyleClass().add("bordered-titled-content");  // todo would be nice to remove this style class when it is no longer required.
                }
                contentPane.getChildren().setAll(content.get());
            }
        });

        titleAlignment.set(Pos.TOP_LEFT);
        this.title.set(titleString);
        this.content.set(contentNode);

    }

    /**
     * 
     * @return title
     */
    public String getTitle()
    {
        return title.get();
    }

    /**
     * 
     * @return property title
     */
    public StringProperty getTitleStringProperty()
    {
        return title;
    }

    /**
     * 
     * @param title to set
     */
    public void setTitle(String title)
    {
        this.title.set(title);
    }

    /**
     * 
     * @return title alignment
     */
    public Pos getTitleAlignment()
    {
        return titleAlignment.get();
    }

    /**
     * 
     * @return property of tithe alignment
     */
    public ObjectProperty<Pos> titleAlignmentProperty()
    {
        return titleAlignment;
    }

    /**
     * 
     * @param titleAlignment title alignment to set
     */
    public void setTitleAlignment(Pos titleAlignment)
    {
        this.titleAlignment.set(titleAlignment);
    }

    /**
     * 
     * @return content
     */
    public Node getContent()
    {
        return content.get();
    }

    /**
     * 
     * @return property content
     */
    public ObjectProperty<Node> contentProperty()
    {
        return content;
    }

    /**
     * 
     * @param content Content to set
     */
    public void setContent(Node content)
    {
        this.content.set(content);
    }

    /**
     * 
     * @return graphic
     */
    public Node getGraphic()
    {
        return graphic.get();
    }

    /**
     * 
     * @return property graphic
     */
    public ObjectProperty<Node> graphicProperty()
    {
        return graphic;
    }

    /**
     * 
     * @param graphic Graphic to set
     */
    public void setGraphic(Node graphic)
    {
        this.graphic.set(graphic);
    }
}
