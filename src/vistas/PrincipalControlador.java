package vistas;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Window;
import modelosvista.PrincipalModeloVista;

public class PrincipalControlador {
    private PrincipalModeloVista modeloVista;

    @FXML private Parent entrada;
    @FXML private EntradaControlador entradaController;
    @FXML private Parent salida;
    @FXML private SalidaControlador salidaController;
    @FXML private Parent grafica;
    @FXML private GraficaControlador graficaController;

    public void init(PrincipalModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }

    public EntradaControlador getEntradaController() {
        return entradaController;
    }
    public SalidaControlador getSalidaController() {
        return salidaController;
    }
    public GraficaControlador getGraficaController() { return graficaController; }
}
