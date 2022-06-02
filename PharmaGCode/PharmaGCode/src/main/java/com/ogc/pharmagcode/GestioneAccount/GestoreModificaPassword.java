package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaPassword {
    public GestoreModificaPassword(){
        Utils.cambiaInterfaccia("GestioneAccount/ModificaPassword.FXML",new Stage(),e->{return new ModuloModificaPassword(this);});
    }

    private boolean controllaPassword(String new_pwd, String re_pwd){
        return new_pwd.equals(re_pwd);
    }

    public void modificaPassword(String old_pwd, String new_pwd, String re_pwd){
        if(controllaPassword(new_pwd,re_pwd)){
            if(DBMSDaemon.verificaEsistenzaMail("cicciopasticcio@sium.it")){  //vamoraga3
                int t=DBMSDaemon.modificaPassword("cicciopasticcio@sium.it",Utils.hash(old_pwd), Utils.hash(new_pwd));
                Main.log.info("return: "+t);
            }
        }
    }
}
