module es.rubenmejias.jugadoresbaloncesto {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.instrument;
    requires java.persistence;
    requires java.sql;
    
    opens es.rubenmejias.jugadoresbaloncesto.entities;
    opens es.rubenmejias.jugadoresbaloncesto to javafx.fxml;
    exports es.rubenmejias.jugadoresbaloncesto;
}
