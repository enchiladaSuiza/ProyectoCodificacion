package vistas;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import modelos.GraficadorModelo;
import modelosvista.SalidaModeloVista;

import java.util.Arrays;

public class SalidaControlador {
    private SalidaModeloVista modeloVista;

    @FXML
    private ComboBox<String> tipoCodificacionComboBox;

    @FXML
    private TextArea salidaBinarioTextArea;

    public void init(SalidaModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        salidaBinarioTextArea.textProperty().bindBidirectional(modeloVista.getSalidaBinario());
        tipoCodificacionComboBox.valueProperty().bindBidirectional(modeloVista.getTipoCodificacion());
        tipoCodificacionComboBox
                .setItems(FXCollections.observableList(Arrays.asList(GraficadorModelo.codificaciones)));
    }
}
