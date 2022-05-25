module com.ogc.pharmagcode {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.mail;

    opens com.ogc.pharmagcode to javafx.fxml;
    exports com.ogc.pharmagcode;
}