package vistas;

import backbone.EncargadoVistas;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Comunicación directa con los nodos del fxml tutorial.
 */
public class TutorialControlador {
    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane;

    public void init(EncargadoVistas encargadoVistas) {
        String vBoxCss = """
                -fx-border-color: transparent;
                -fx-box-border: transparent;
                -fx-background-color: transparent;
                """;
//        vBox.setStyle(vBoxCss);
//        scrollPane.setStyle(vBoxCss);
    }
}
