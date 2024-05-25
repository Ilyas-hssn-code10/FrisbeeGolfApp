module FrisbeeGolfAppMain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires javafx.base;
   


    opens application.controller to javafx.fxml;
    exports application;
}
