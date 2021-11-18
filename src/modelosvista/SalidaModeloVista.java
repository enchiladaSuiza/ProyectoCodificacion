package modelosvista;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelos.CodificadorModelo;
import modelos.GraficadorModelo;

public class SalidaModeloVista {
    private CodificadorModelo modelo;

    private StringProperty salidaBinario;
    private StringProperty tipoCodificacion;

    /**
     * Conecta las propiedades entre controlador y modelos.
     * Modelos.
     * @param codificadorModelo
     * @param graficadorModelo
     */
    public SalidaModeloVista(CodificadorModelo codificadorModelo, GraficadorModelo graficadorModelo) {
        this.modelo = codificadorModelo;
        salidaBinario = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        salidaBinario.bindBidirectional(codificadorModelo.salidaBinarioProperty());
        salidaBinario.bindBidirectional(graficadorModelo.entradaBitsProperty());
        tipoCodificacion.bindBidirectional(graficadorModelo.tipoCodificacionProperty());
    }

    public StringProperty salidaBinarioProperty() { return salidaBinario; }
    public StringProperty tipoCodificacionProperty() { return tipoCodificacion; }
}
