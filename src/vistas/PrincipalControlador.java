package vistas;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import modelosvista.PrincipalModeloVista;

import java.util.ArrayList;
import java.util.Objects;

public class PrincipalControlador {
    private PrincipalModeloVista modeloVista;

    @FXML
    private Parent entrada;
    @FXML
    private EntradaControlador entradaController;
    @FXML
    private Parent salida;
    @FXML
    private SalidaControlador salidaController;
    @FXML
    private Parent grafica;
    @FXML
    private GraficaControlador graficaController;

    private Scene escena;
    private final String archivoTemaOscuro = "css/temaOscuro.css";
    private ArrayList<String> temas;

    public void init(PrincipalModeloVista modeloVista, Scene escena) {
        this.modeloVista = modeloVista;
        this.escena = escena;

        temas = new ArrayList<>();
        String temaOscuro = getClass().getResource(archivoTemaOscuro).toExternalForm();
        temas.add(temaOscuro);
    }

    public void aplicarTemaOscuro() {
        quitarTemas();
        escena.getStylesheets().add(temas.get(0));
    }

    public void quitarTemas() {
        ObservableList<String> stylesheets = escena.getStylesheets();
        for (int i = 0; i < stylesheets.size(); i++) {
            if (temas.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }

    public EntradaControlador getEntradaController() {
        return entradaController;
    }
    public SalidaControlador getSalidaController() {
        return salidaController;
    }
    public GraficaControlador getGraficaController() { return graficaController; }
}
