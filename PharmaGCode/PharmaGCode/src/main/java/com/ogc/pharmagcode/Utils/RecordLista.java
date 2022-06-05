package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class RecordLista extends HBox{
    private Label[] labels;
    private Button bottone;
    public RecordLista(EventHandler<ActionEvent> e, int width, String... args){
        setMinHeight(50);
        labels=new Label[args.length-1];
        for(int i=0;i<args.length-1;i++){
            labels[i]=new Label(args[i]);
            labels[i].setMinWidth((width)/args.length);
            labels[i].setMaxWidth((width)/args.length);
        }

        bottone=new Button(args[args.length-1]);
        bottone.setMinWidth((width)/args.length);
        bottone.setMaxWidth((width)/args.length);
        if(args[args.length-1].isBlank()){
            bottone.setVisible(false);
        }
        HBox.setMargin(bottone,new Insets(0,10,0,0));
        HBox.setMargin(labels[0],new Insets(0,0,0,10));
        bottone.getStyleClass().add("btn");
        bottone.setOnAction(e);
        getStyleClass().add("entries");
        setAlignment(Pos.CENTER_LEFT);
        for(Label l : labels){
            l.getStyleClass().add("labelentries");
            HBox.setHgrow(l, Priority.ALWAYS);
        }
        HBox.setHgrow(bottone, Priority.ALWAYS);

        this.getChildren().addAll(labels);
        this.getChildren().add(bottone);
    }
}
