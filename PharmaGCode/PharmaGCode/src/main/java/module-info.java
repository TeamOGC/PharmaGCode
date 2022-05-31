module com.ogc.pharmagcode {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
//    requires java.mail;
    requires java.net.http;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires org.mariadb.jdbc;

    opens com.ogc.pharmagcode to javafx.fxml;
    exports com.ogc.pharmagcode;
    exports com.ogc.pharmagcode.GestioneAccount;
    opens com.ogc.pharmagcode.GestioneAccount to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneFarmaci;
    opens com.ogc.pharmagcode.GestioneFarmaci to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneProduzione;
    opens com.ogc.pharmagcode.GestioneProduzione to javafx.fxml;
    exports com.ogc.pharmagcode.GestioneConsegna;
    opens com.ogc.pharmagcode.GestioneConsegna to javafx.fxml;
    exports com.ogc.pharmagcode.Utils;
    opens com.ogc.pharmagcode.Utils to javafx.fxml;
}