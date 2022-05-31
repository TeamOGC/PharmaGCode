package com.ogc.pharmagcode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BoundaryDiSistema {
    GestoreDiSistema g=new GestoreDiSistema();
    public BoundaryDiSistema(){
        GestoreDiSistema g1;
        try {
            ObjectInputStream o= new ObjectInputStream(new FileInputStream("gds.time"));
            g1=(GestoreDiSistema) o.readObject();
            g=g1;
        } catch (FileNotFoundException e) {
            System.out.println("Gestore da creare");
        } catch (IOException e) {
            System.err.println("Errore deserializzazione");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void chiediOrario(){
        g.chiediOrario();
    }
}
