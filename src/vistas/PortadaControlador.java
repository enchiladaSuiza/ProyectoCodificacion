package vistas;

import backbone.EncargadoVistas;

/**
 * Comunicación directa con el archivo fxml de la portada
 */
public class PortadaControlador {
    private EncargadoVistas encargadoVistas;

    public void init(EncargadoVistas encargadoVistas) {
        this.encargadoVistas = encargadoVistas;
    }
    public void mostrarProgramaPrincipal() throws Exception {
        encargadoVistas.abrirProgramaPrincipal();
    }
}
