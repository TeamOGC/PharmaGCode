package com.ogc.pharmagcode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BoundaryDiSistema {
    GestoreDiSistemaFarmacista gF = new GestoreDiSistemaFarmacista();
    GestoreDiSistemaImpiegato gI = new GestoreDiSistemaImpiegato();

    public BoundaryDiSistema() {
        GestoreDiSistemaFarmacista g1;
        try (ObjectInputStream o = new ObjectInputStream(new FileInputStream("gds.time"))) {
            if (Main.sistema == 0) {
                g1 = (GestoreDiSistemaFarmacista) o.readObject();
                gF = g1;
            } else if (Main.sistema == 2) {
                gI = (GestoreDiSistemaImpiegato) o.readObject();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Gestore da creare");
        } catch (IOException e) {
            System.err.println("Errore deserializzazione");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chiediOrario() {
        if (Main.sistema == 0)
            gF.chiediOrario();
        if (Main.sistema == 2)
            gI.chiediData();
    }
}
