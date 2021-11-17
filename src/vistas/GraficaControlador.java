package vistas;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import modelosvista.GraficaModeloVista;

public class GraficaControlador {
    private GraficaModeloVista modeloVista;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private LineChart<Double, Double> grafica;

    @FXML
    private NumberAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    private XYChart.Series<Double, Double> serie;
    private final int anchoMinVoltaje = 10;

    public void init(GraficaModeloVista modeloVista) {
        this.modeloVista = modeloVista;

        serie = new XYChart.Series<>();
        serie.dataProperty().bindBidirectional(modeloVista.getPuntosGrafica());

        grafica.prefWidthProperty().bindBidirectional(modeloVista.getAnchoGrafica());
        ejeX.upperBoundProperty().bindBidirectional(modeloVista.getLimiteAbscisas());
        scrollPane.fitToWidthProperty().bindBidirectional(modeloVista.getScrollPaneSeAjusta());

        grafica.getData().add(serie);
        serie.getData().addListener((ListChangeListener<XYChart.Data<Double, Double >>) change ->
                modeloVista.ajustarAnchoGrafica(anchoMinVoltaje, grafica.getScene().getWidth()));
        grafica.getScene().widthProperty().addListener((observableValue, anterior, nuevo) ->
                modeloVista.ajustarAnchoGrafica(anchoMinVoltaje, nuevo.doubleValue()));
    }

    public void alternarSimbolos() {
        grafica.setCreateSymbols(!grafica.getCreateSymbols());
    }
}
