package modelosvista;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelos.CodificadorModelo;
import modelos.GraficadorModelo;

public class SalidaModeloVista {
    private CodificadorModelo modelo;

    private StringProperty salidaBinario;
    private StringProperty tipoCodificacion;

    public SalidaModeloVista(CodificadorModelo codificadorModelo, GraficadorModelo graficadorModelo) {
        this.modelo = codificadorModelo;
        salidaBinario = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        salidaBinario.bindBidirectional(codificadorModelo.getSalidaBinario());
        salidaBinario.bindBidirectional(graficadorModelo.getEntradaBits());
        tipoCodificacion.bindBidirectional(graficadorModelo.getTipoCodificacion());
    }

    public StringProperty getSalidaBinario() { return salidaBinario; }
    public StringProperty getTipoCodificacion() { return tipoCodificacion; }
}
