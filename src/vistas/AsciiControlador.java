package vistas;

import backbone.EncargadoVistas;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import modelosvista.AsciiModeloVista;

import java.util.ArrayList;

public class AsciiControlador {
    private EncargadoVistas encargadoVistas;

    @FXML
    private GridPane gridPane;

    private ListProperty<ObservableList<Label>> labels;
    private int limiteCaracteres = 1023;

    public void init(AsciiModeloVista modeloVista, EncargadoVistas encargadoVistas) {
        this.encargadoVistas = encargadoVistas;

        labels = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
        labels.bindBidirectional(modeloVista.labelsProperty());

        modeloVista.crearLabels(limiteCaracteres);

        for (int i = 0; i < limiteCaracteres; i++) {
                gridPane.addRow(i + 1,
                        labels.get(i).get(0), labels.get(i).get(1), labels.get(i).get(2));
        }
    }
}
