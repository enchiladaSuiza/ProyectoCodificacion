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
    private DoubleProperty anchoGrafica;
    private BooleanProperty scrollPaneSeAjusta;
    private boolean breakpointAncho = true;

    private ListProperty<XYChart.Data<Double, Double>> puntosGrafica;

    public GraficaModeloVista(CodificadorModelo codificadorModelo, GraficadorModelo graficadorModelo) {
        this.codificadorModelo = codificadorModelo;
        this.graficadorModelo = graficadorModelo;
        entradaBits = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        limiteAbscisas = new SimpleDoubleProperty();
        anchoGrafica = new SimpleDoubleProperty();
        scrollPaneSeAjusta = new SimpleBooleanProperty();
        puntosGrafica = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));

        puntosGrafica.bindBidirectional(graficadorModelo.getPuntosGrafica());
        entradaBits.bindBidirectional(codificadorModelo.getSalidaBinario());
        tipoCodificacion.bindBidirectional(graficadorModelo.getTipoCodificacion());
    }

    public void ajustarAnchoGrafica(double anchoMinimoVoltaje, double anchoEscena) {
        int numeroValores = puntosGrafica.getSize();
        double limiteSuperior = 0;
        if (numeroValores > 0) {
            limiteSuperior = puntosGrafica.get(numeroValores - 1).getXValue();
            limiteAbscisas.set(limiteSuperior);
        }

        double relacionAnchoValores = anchoEscena / limiteSuperior;
        if (relacionAnchoValores < anchoMinimoVoltaje) {
            anchoGrafica.set(anchoMinimoVoltaje * limiteSuperior);
            if (!breakpointAncho) {
                scrollPaneSeAjusta.set(false);
                breakpointAncho = true;
            }
        }
        else if (breakpointAncho) {
            anchoGrafica.set(Region.USE_COMPUTED_SIZE);
            scrollPaneSeAjusta.set(true);
            breakpointAncho = false;
        }
    }

    public StringProperty getEntradaBits() { return entradaBits; }
    public StringProperty getTipoCodificacion() { return tipoCodificacion; }
    public DoubleProperty getAnchoGrafica() { return anchoGrafica; }
    public DoubleProperty getLimiteAbscisas() { return limiteAbscisas; }
    public BooleanProperty getScrollPaneSeAjusta() { return scrollPaneSeAjusta; }
    public ListProperty<XYChart.Data<Double, Double>> getPuntosGrafica() { return puntosGrafica; }
}
