package com.ogc.pharmagcode.GestioneFarmaci;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class VoceFarmaco extends HBox {
    private Label[] labels=new Label[5];
    private Button button=new Button();

    public VoceFarmaco(String nome, String princAtt, int disponibilita,boolean inScadenza,String dataDiDisponibilita, String bottone,
                      EventHandler<ActionEvent> eventHandler){
        setMinHeight(50);

        labels[0]=new Label(nome);
        labels[0].setMaxWidth(Double.MAX_VALUE);
        HBox.setMargin(labels[0],new Insets(0,0,0,10));
        labels[1]=new Label(princAtt);
        labels[1].setMaxWidth(Double.MAX_VALUE);
        labels[2]=new Label(disponibilita+"");
        labels[2].setMaxWidth(Double.MAX_VALUE);
        labels[3]=new Label(dataDiDisponibilita);
        labels[3].setMaxWidth(Double.MAX_VALUE);
        labels[4]=new Label(inScadenza?"Si":"No");
        labels[4].setMaxWidth(Double.MAX_VALUE);
        if(inScadenza)
            labels[4].setStyle("-fx-text-fill: #ff0000 !important");
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
