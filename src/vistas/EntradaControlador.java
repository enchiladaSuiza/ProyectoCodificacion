package vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import modelos.CodificadorModelo;
import modelosvista.EntradaModeloVista;

import java.util.Arrays;

/**
 * Comunicación directa con el archivo fxml de entrada y sus nodos declardados.
 */
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

    /**
     * Inicializa los nodos y listeners.
     * Aquéllos que se agregarán programáticamente, según el tipo de entrada.
     * @param modeloVista
     */
    public void init(EntradaModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        nodosVBox = entradaVBox.getChildren();

        entradaBitsTextArea = new TextArea();
        entradaBitsTextArea.setWrapText(true);
        VBox.setVgrow(entradaBitsTextArea, Priority.ALWAYS);
        entradaBitsTextArea.textProperty().addListener(
                (observableValue, anterior, nuevo) -> validarBinario(nuevo, entradaBitsTextArea));
        entradaBitsTextArea.textProperty().bindBidirectional(modeloVista.entradaBinarioProperty());

        entradaNumeroTextField = new TextField();
        entradaNumeroTextField.setPrefWidth(200);
        entradaNumeroTextField.setMaxWidth(Region.USE_PREF_SIZE);
        VBox.setMargin(entradaNumeroTextField, new Insets(10));
        entradaNumeroTextField.textProperty().addListener(
                (observableValue, anterior, nuevo) -> validarNumero(nuevo, entradaNumeroTextField));
        entradaNumeroTextField.textProperty().bindBidirectional(modeloVista.entradaNumeroProperty());

        entradaTextoTextArea = new TextArea();
        entradaTextoTextArea.setWrapText(true);
        VBox.setVgrow(entradaTextoTextArea, Priority.ALWAYS);
        entradaTextoTextArea.textProperty().bindBidirectional(modeloVista.entradaTextoProperty());

        tipoEntradaComboBox
                .setItems(FXCollections.observableList(Arrays.asList(CodificadorModelo.tiposEntradas)));
        tipoEntradaComboBox.valueProperty().addListener(
                (observableValue, anterior, nuevo) -> cambioTipoDeEntrada(nuevo));
        tipoEntradaComboBox.valueProperty().bindBidirectional(modeloVista.tipoEntradaProperty());
        tipoEntradaComboBox.getSelectionModel().selectFirst();

        KeyCombination abrirComboBoxCombinacion = new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN);
        tipoEntradaComboBox.getScene().addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (abrirComboBoxCombinacion.match(keyEvent)) {
                tipoEntradaComboBox.show();
            }
        });
    }

    /**
     * Quita el último nodo y agrega el nodo de captura de datos correspondiente.
     * @param nuevoValor
     */
    private void cambioTipoDeEntrada(String nuevoValor) {
        if (nodosVBox.size() > 1) { // Si sólo está el FlowPane con la Label y ComboBox, no quita nada
            nodosVBox.remove(nodosVBox.size() - 1);
        }
        if (nuevoValor.equals(CodificadorModelo.tiposEntradas[0])) {
            nodosVBox.add(entradaBitsTextArea);
        }
        else if (nuevoValor.equals(CodificadorModelo.tiposEntradas[1])) {
            nodosVBox.add(entradaNumeroTextField);
        }
        else if (nuevoValor.equals(CodificadorModelo.tiposEntradas[2])) {
            nodosVBox.add(entradaTextoTextArea);
        }
    }

    /**
     * Reemplaza el texto por cadenas vacías sí no cumple como cadena válida binaria.
     * @param valor
     * @param nodoEntrada
     */
    private void validarBinario(String valor, TextInputControl nodoEntrada) {
        if (valor != null && !valor.matches("[01]+")) { // 0 o 1, una o más veces
            nodoEntrada.setText(valor.replaceAll("[^01]", "")); // Quita lo que no es 0 o 1
        }
    }

    /**
     * Reemplaza el texto por cadenas vacías si no cumple como cadena válida numérica.
     * @param valor
     * @param nodoEntrada
     */
    private void validarNumero(String valor, TextInputControl nodoEntrada) {
        if (valor != null && !valor.matches("\\d+")) { // Dígito cualquiera, una o más veces
            nodoEntrada.setText(valor.replaceAll("\\D", "")); // Quita lo que no es dígito
        }
    }

    public void enfocarInputControl(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            int indice = tipoEntradaComboBox.getSelectionModel().getSelectedIndex();
            switch (indice) {
                case 0 -> entradaBitsTextArea.requestFocus();
                case 1 -> entradaNumeroTextField.requestFocus();
                case 2 -> entradaTextoTextArea.requestFocus();
            }
        }
    }
}
