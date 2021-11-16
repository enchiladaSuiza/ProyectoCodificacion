package vistas;

import backbone.EncargadoVistas;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import modelosvista.PrincipalModeloVista;

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

    private EncargadoVistas encargadoVistas;

    public void init(PrincipalModeloVista modeloVista, EncargadoVistas encargadoVistas) {
        this.modeloVista = modeloVista;
        this.encargadoVistas = encargadoVistas;
    }

    public void aplicarTemaOscuro() {
        encargadoVistas.aplicarTemaOscuro();
    }

    public void quitarTemas() {
        encargadoVistas.quitarTemas();
    }

    public void mostrarPortada() throws Exception {
        encargadoVistas.abrirPortada();
    }

    public void alternarSimbolos() {
        graficaController.alternarSimbolos();
    }

    public EntradaControlador getEntradaController() {
        return entradaController;
    }
    public SalidaControlador getSalidaController() {
        return salidaController;
    }
    public GraficaControlador getGraficaController() { return graficaController; }
}
