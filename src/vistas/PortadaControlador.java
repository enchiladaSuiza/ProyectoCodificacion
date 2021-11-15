package vistas;

import backbone.EncargadoVistas;

public class PortadaControlador {
    private EncargadoVistas encargadoVistas;

    public void init(EncargadoVistas encargadoVistas) {
        this.encargadoVistas = encargadoVistas;
    }
    public void mostrarProgramaPrincipal() throws Exception {
        encargadoVistas.abrirProgramaPrincipal();
    }
}
