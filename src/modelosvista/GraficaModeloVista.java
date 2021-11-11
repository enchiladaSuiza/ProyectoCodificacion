package modelosvista;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import modelos.CodificadorModelo;
import modelos.GraficadorModelo;

import java.util.ArrayList;

public class GraficaModeloVista {
    private CodificadorModelo codificadorModelo;
    private GraficadorModelo graficadorModelo;

    private StringProperty entradaBits;
    private StringProperty tipoCodificacion;
    private ListProperty<XYChart.Data<Number, Number>> puntosGrafica;

    public GraficaModeloVista(CodificadorModelo codificadorModelo, GraficadorModelo graficadorModelo) {
        this.codificadorModelo = codificadorModelo;
        this.graficadorModelo = graficadorModelo;
        entradaBits = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        puntosGrafica = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));

        puntosGrafica.bindBidirectional(graficadorModelo.getPuntosGrafica());
        entradaBits.bindBidirectional(codificadorModelo.getSalidaBinario());
        tipoCodificacion.bindBidirectional(graficadorModelo.getTipoCodificacion());
    }

    public StringProperty getEntradaBits() { return entradaBits; }
    public StringProperty getTipoCodificacion() { return tipoCodificacion; }
    public ListProperty<XYChart.Data<Number, Number>> getPuntosGrafica() { return puntosGrafica; }
}
