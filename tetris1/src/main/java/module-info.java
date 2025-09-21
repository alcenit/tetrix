module com.cenit.tetris1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cenit.tetris1 to javafx.fxml;
    exports com.cenit.tetris1;
}
