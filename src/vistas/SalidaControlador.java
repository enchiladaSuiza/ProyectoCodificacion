package vistas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import modelos.GraficadorModelo;
import modelosvista.SalidaModeloVista;

import java.util.Arrays;

/**
 * Comunicación directa con el archivo fxml de salida.
 */
public class SalidaControlador {
    private SalidaModeloVista modeloVista;

    @FXML
    private ComboBox<String> tipoCodificacionComboBox;

    @FXML
    private TextArea salidaBinarioTextArea;

    /**
     * Enlaza las propiedades con las del modelo vista.
     * @param modeloVista
     */
    public void init(SalidaModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        salidaBinarioTextArea.textProperty().bindBidirectional(modeloVista.salidaBinarioProperty());
        tipoCodificacionComboBox.valueProperty().bindBidirectional(modeloVista.tipoCodificacionProperty());
        tipoCodificacionComboBox
                .setItems(FXCollections.observableList(Arrays.asList(GraficadorModelo.codificaciones)));
        tipoCodificacionComboBox.getSelectionModel().selectFirst();

        KeyCombination abrirComboBoxCombinacion = new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN);
        tipoCodificacionComboBox.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (abrirComboBoxCombinacion.match(keyEvent)) {
                tipoCodificacionComboBox.show();
            }
        });
    }
}
