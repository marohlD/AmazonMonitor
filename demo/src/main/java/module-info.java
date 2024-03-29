module pad.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;

    requires jakarta.mail;
   // requires org.eclipse.angus;


    opens pad.test to javafx.fxml;
    exports pad.test;
}
