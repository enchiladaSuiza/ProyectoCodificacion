package modelosvista;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import modelos.CodificadorModelo;
import modelos.GraficadorModelo;

import java.util.ArrayList;

public class GraficaModeloVista {
    private CodificadorModelo codificadorModelo;
    private GraficadorModelo graficadorModelo;

    private StringProperty entradaBits;
    private StringProperty tipoCodificacion;

    private DoubleProperty limiteAbscisas;

    private ListProperty<XYChart.Data<Double, Double>> puntosGrafica;

    /**
     * Conecta las propiedades de la vista con los controaldores.
     * Sí, contraladores.
     * @param codificadorModelo
     * @param graficadorModelo
     */
    public GraficaModeloVista(CodificadorModelo codificadorModelo, GraficadorModelo graficadorModelo) {
        this.codificadorModelo = codificadorModelo;
        this.graficadorModelo = graficadorModelo;
        entradaBits = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        limiteAbscisas = new SimpleDoubleProperty();
        puntosGrafica = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));

        puntosGrafica.bindBidirectional(graficadorModelo.puntosGraficaProperty());
        entradaBits.bindBidirectional(codificadorModelo.salidaBinarioProperty());
        tipoCodificacion.bindBidirectional(graficadorModelo.tipoCodificacionProperty());
    }

    /**
     * Devuelve el valor x del último voltaje agregado.
     * @return
     */
    public double valorMaximoAbscisas() {
        int numeroValores = puntosGrafica.getSize();
        double limiteSuperior = 1;
        if (numeroValores > 0) {
            limiteSuperior = puntosGrafica.get(numeroValores - 1).getXValue();
        }
        if (limiteSuperior >= 2000) {
            limiteSuperior = 2000;
        }
        return limiteSuperior;
    }

    public ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty() { return puntosGrafica; }
}
