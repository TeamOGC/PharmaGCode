module com.ogc.pharmagcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
//    requires java.mail;
    requires java.net.http;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires org.mariadb.jdbc;
    requires java.desktop;


    exports com.ogc.pharmagcode.Entity;
    opens com.ogc.pharmagcode to javafx.fxml;
    exports com.ogc.pharmagcode;
    opens com.ogc.pharmagcode.Autenticazione to javafx.fxml;
    opens com.ogc.pharmagcode.GestioneFarmaci to javafx.fxml;
    opens com.ogc.pharmagcode.GestioneProduzione to javafx.fxml;
    opens com.ogc.pharmagcode.GestioneConsegna to javafx.fxml;
    exports com.ogc.pharmagcode.Utils;
    opens com.ogc.pharmagcode.Utils to javafx.fxml;
    opens com.ogc.pharmagcode.GestioneOrdini to javafx.fxml;
    opens com.ogc.pharmagcode.Pannelli to javafx.fxml;
    exports com.ogc.pharmagcode.Common;
    opens com.ogc.pharmagcode.Common to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneAzienda.Interface;
    opens com.ogc.pharmagcode.GestioneAzienda.Interface to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneAzienda.Control;
    opens com.ogc.pharmagcode.GestioneAzienda.Control to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneFarmaci.Interface;
    opens com.ogc.pharmagcode.GestioneFarmaci.Interface to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneFarmaci.Control;
    opens com.ogc.pharmagcode.GestioneFarmaci.Control to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneConsegna.Interface;
    opens com.ogc.pharmagcode.GestioneConsegna.Interface to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneConsegna.Control;
    opens com.ogc.pharmagcode.GestioneConsegna.Control to javafx.fxml;
    exports com.ogc.pharmagcode.Autenticazione.Interface;
    opens com.ogc.pharmagcode.Autenticazione.Interface to javafx.fxml;
    exports com.ogc.pharmagcode.Autenticazione.Control;
    opens com.ogc.pharmagcode.Autenticazione.Control to javafx.fxml;
}