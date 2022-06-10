package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreDiSistemaFarmacista;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreDiSistemaImpiegato;
import com.ogc.pharmagcode.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BoundaryDiSistemaFarmacista {
    GestoreDiSistemaFarmacista gF = new GestoreDiSistemaFarmacista();
    GestoreDiSistemaImpiegato gI = new GestoreDiSistemaImpiegato();

    public BoundaryDiSistemaFarmacista() {
        try (ObjectInputStream o = new ObjectInputStream(new FileInputStream("gds.time"))) {
                gF = (GestoreDiSistemaFarmacista) o.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Gestore da creare");
        } catch (IOException e) {
            System.err.println("Errore deserializzazione");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chiediOrario() {
        gF.chiediOrario();
    }
}
