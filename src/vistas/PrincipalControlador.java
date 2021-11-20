package vistas;

import backbone.EncargadoVistas;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import modelosvista.PrincipalModeloVista;

/**
 * Acceso a los controladores y nodos padre de entrada, salida y gráfica.
 * Tiene una referencia a EncargadoVistas para manipular los estilos.
 */
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

    public void aplicarAcentoAzul() { encargadoVistas.aplicarAcentoAzul(); }

    public void aplicarAcentoVerde() { encargadoVistas.aplicarAcentoVerde(); }

    public void quitarAcentos() { encargadoVistas.quitarAcentos(); }

    public void mostrarPortada() throws Exception {
        encargadoVistas.abrirPortada();
    }

    public void mostrarTutorial() throws Exception {
        encargadoVistas.abrirTutorial();
    }

    public void mostrarAcercaDe() throws Exception {
        encargadoVistas.abrirAcercaDe();
    }

    public void alternarSimbolos() {
        graficaController.alternarSimbolos();
    }

    public void intercambiarCerosConUnos() {
        graficaController.intercambiarCerosConUnos();
    }

    public void alternarVoltajeInicial() {
        graficaController.alternarVoltajePorDefecto();
    }

    public EntradaControlador getEntradaController() {
        return entradaController;
    }
    public SalidaControlador getSalidaController() {
        return salidaController;
    }
    public GraficaControlador getGraficaController() { return graficaController; }
}
