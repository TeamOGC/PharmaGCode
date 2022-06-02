package com.ogc.pharmagcode.GestioneFarmaci;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Function;

public class VoceOrdine extends HBox {
    private Label[] labels=new Label[4];
    private Button button=new Button();

    public VoceOrdine(String field1, String field2, String field3, String field4, String bottone,
                      EventHandler<ActionEvent> eventHandler){
        setMinHeight(50);

        labels[0]=new Label(field1);
        labels[0].setMaxWidth(Double.MAX_VALUE);
        HBox.setMargin(labels[0],new Insets(0,0,0,10));
        labels[1]=new Label(field2);
        labels[1].setMaxWidth(Double.MAX_VALUE);
        labels[2]=new Label(field3);
        labels[2].setMaxWidth(Double.MAX_VALUE);
        labels[3]=new Label(field4);
        labels[3].setMaxWidth(Double.MAX_VALUE);
        if(bottone.isBlank()){
            button.setVisible(false);
        }
        button.setText(bottone);
        HBox.setMargin(button,new Insets(0,10,0,0));
        button.getStyleClass().add("btn");
        button.setOnAction(eventHandler);
        getStyleClass().add("entries");
        setAlignment(Pos.CENTER_LEFT);
        for(Label l : labels){
            l.getStyleClass().add("labelentries");
            HBox.setHgrow(l, Priority.ALWAYS);
        }

        this.getChildren().addAll(labels);
        this.getChildren().add(button);
    }


}
