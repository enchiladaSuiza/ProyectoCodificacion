package modelosvista;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import modelos.CodificadorModelo;

import java.util.ArrayList;

public class AsciiModeloVista {
    private CodificadorModelo modelo;

    private ListProperty<ObservableList<Label>> labels;

    public AsciiModeloVista(CodificadorModelo modelo) {
        this.modelo = modelo;

        labels = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void crearLabels(int limite) {
        ObservableList<ObservableList<Label>> listas = FXCollections.observableArrayList();
        for (int i = 0; i < limite; i++) { // Filas
            ObservableList<Label> fila = FXCollections.observableArrayList();
            Label caracter = new Label(Character.toString((char) i));
            GridPane.setHalignment(caracter, HPos.CENTER);
            Label decimal = new Label(Integer.toString(i));
            Label binario = new Label(modelo.convertirNumero((long) i));
            fila.addAll(caracter, decimal, binario);
            listas.add(fila);
        }
        labels.setAll(listas);
    }

    public ListProperty<ObservableList<Label>> labelsProperty() { return labels;}
}
