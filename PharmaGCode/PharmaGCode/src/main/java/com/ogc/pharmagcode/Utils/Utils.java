package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Utils {
    public static FXMLLoader creaLoader(String path){
        FXMLLoader loader=new FXMLLoader(Main.class.getResource(path));
        return loader;
    }
    public static void creaInterfaccia(FXMLLoader loader, int w, int h, Stage stage){
        stage.setResizable(false);
        try{
            Scene s=new Scene(loader.load(),w,h);
            stage.setScene(s);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void creaPannelloErrore(String messaggio){
        Stage stage=new Stage();
        stage.setResizable(false);
        FXMLLoader loader=creaLoader("Pannelli/ErroreGenericoPopup.fxml");
        loader.setControllerFactory(c->{return new PannelloErrore(messaggio, stage);});
        creaInterfaccia(loader,600,400,stage);
    }
    public static void creaPannelloAvviso(){

    }
    public static void creaPannelloConferma(String messaggio){
        Stage stage=new Stage();
        FXMLLoader loader=creaLoader("Pannelli/ConfermaPopup.fxml");
        loader.setControllerFactory(c->{return new PannelloConferma(messaggio, stage);});
        creaInterfaccia(loader,600,400,stage);
    }

    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c){
        FXMLLoader loader=creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, 1280,800, stage);
        return loader.getController();
    }
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c,int w,int h){
        FXMLLoader loader=creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, w,h, stage);
        return loader.getController();
    }
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, int w,int h){
        FXMLLoader loader=creaLoader(interfaccia);
        creaInterfaccia(loader, w,h, stage);
        return loader.getController();
    }
}
