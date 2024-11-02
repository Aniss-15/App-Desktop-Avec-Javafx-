module minds_hub.pijava {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires  de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires com.jfoenix;

    opens minds_hub to javafx.fxml;
    opens entities to javafx.base;
    exports minds_hub;
}