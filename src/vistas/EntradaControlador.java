package vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import modelos.CodificadorModelo;
import modelosvista.EntradaModeloVista;

import java.util.Arrays;

public class EntradaControlador {
    private EntradaModeloVista modeloVista;

    @FXML
    private ComboBox<String> tipoEntradaComboBox;

    @FXML
    private VBox entradaVBox;

    private ObservableList<Node> nodosVBox;
    private TextArea entradaBitsTextArea;
    private TextField entradaNumeroTextField;
    private TextArea entradaTextoTextArea;

    public void init(EntradaModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        nodosVBox = entradaVBox.getChildren();

        entradaBitsTextArea = new TextArea();
        entradaBitsTextArea.setWrapText(true);
        VBox.setVgrow(entradaBitsTextArea, Priority.ALWAYS);
        entradaBitsTextArea.textProperty().addListener(
                (observableValue, anterior, nuevo) -> validarBinario(nuevo, entradaBitsTextArea));
        entradaBitsTextArea.textProperty().bindBidirectional(modeloVista.getEntradaBinario());

        entradaNumeroTextField = new TextField();
        entradaNumeroTextField.setPrefWidth(200);
        entradaNumeroTextField.setMaxWidth(Region.USE_PREF_SIZE);
        entradaNumeroTextField.textProperty().addListener(
                (observableValue, anterior, nuevo) -> validarNumero(nuevo, entradaNumeroTextField));
        entradaNumeroTextField.textProperty().bindBidirectional(modeloVista.getEntradaNumero());

        entradaTextoTextArea = new TextArea();
        entradaTextoTextArea.setWrapText(true);
        VBox.setVgrow(entradaTextoTextArea, Priority.ALWAYS);
        entradaTextoTextArea.textProperty().bindBidirectional(modeloVista.getEntradaTexto());

        tipoEntradaComboBox
                .setItems(FXCollections.observableList(Arrays.asList(CodificadorModelo.tiposEntradas)));
        tipoEntradaComboBox.valueProperty().addListener(
                (observableValue, anterior, nuevo) -> cambioTipoDeEntrada(nuevo));
        tipoEntradaComboBox.valueProperty().bindBidirectional(modeloVista.getTipoEntrada());
        tipoEntradaComboBox.getSelectionModel().selectFirst();
    }

    private void cambioTipoDeEntrada(String nuevoValor) {
        if (nodosVBox.size() > 1) { // Si sólo está el FlowPane con la Label y ComboBox, no quita nada
            nodosVBox.remove(nodosVBox.size() - 1);
        }
        if (nuevoValor.equals(CodificadorModelo.tiposEntradas[0])) {
            entradaVBox.setSpacing(0);
            nodosVBox.add(entradaBitsTextArea);
        }
        else if (nuevoValor.equals(CodificadorModelo.tiposEntradas[1])) {
            entradaVBox.setSpacing(20);
            nodosVBox.add(entradaNumeroTextField);
        }
        else if (nuevoValor.equals(CodificadorModelo.tiposEntradas[2])) {
            entradaVBox.setSpacing(0);
            nodosVBox.add(entradaTextoTextArea);
        }
    }

    private void validarBinario(String valor, TextInputControl nodoEntrada) {
        if (valor != null && !valor.matches("[01]+")) {
            nodoEntrada.setText(valor.replaceAll("[^01]", ""));
        }
    }

    private void validarNumero(String valor, TextInputControl nodoEntrada) {
        if (valor != null && !valor.matches("\\d+")) {
            nodoEntrada.setText(valor.replaceAll("\\D", ""));
        }
    }
}
