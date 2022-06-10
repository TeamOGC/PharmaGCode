package com.ogc.pharmagcode.GestioneOrdini;

import com.itextpdf.text.DocumentException;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.PDFCreator;
import com.ogc.pharmagcode.Utils.TableEntities.RecordOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InterfacciaVisualizzaOrdiniAzienda {

    public Ordine[] listaOrdiniAzienda;

    private List<RecordOrdine> ordini;
    public TableView<RecordOrdine> tableViewOrdini;

    public InterfacciaVisualizzaOrdiniAzienda(Ordine[] listaOrdiniAzienda){
        this.listaOrdiniAzienda = listaOrdiniAzienda;
    }
    @FXML
    public void initialize(){
//        listaOrdiniAzienda.setFixedCellSize(55);
        this.ordini = Arrays.stream(listaOrdiniAzienda).map(ordine -> {
            if(ordine.getStato().equalsIgnoreCase("da verificare")){
                return RecordOrdine.fromOrdine(
                        ordine,
                        "Correggi",
                        correggi->{
                            Main.log.info("Cliccato sul bottone correggi");
                            new GestoreCorrezioneOrdine(ordine);
                        }
                );

            }
            else if (ordine.getStato().equalsIgnoreCase("consegnato")){
                return RecordOrdine.fromOrdine(ordine, "Ricevuta", creaPDF ->{
                            try {
                                PDFCreator.creaPDF((DBMSDaemon.queryCollo(ordine)));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (DocumentException e) {
                                throw new RuntimeException(e);
                            }
                            Main.log.info("Cliccato sul bottone Ricevuta");
                }
                );
            }
            else return RecordOrdine.fromOrdine(ordine);
        }).collect(Collectors.toList());
        ObservableList<RecordOrdine> ol= FXCollections.observableList(this.ordini);
        this.tableViewOrdini.setItems(ol);

    }


}
