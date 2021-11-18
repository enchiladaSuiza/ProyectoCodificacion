package vistas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
    }
}
