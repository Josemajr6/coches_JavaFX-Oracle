module javafx2526.coches_JavaOracle {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens javafx2526.coches_JavaOracle to javafx.fxml;
    exports javafx2526.coches_JavaOracle;
}
