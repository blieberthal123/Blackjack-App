module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens com.blackjack to javafx.fxml;
    exports com.blackjack;
}
