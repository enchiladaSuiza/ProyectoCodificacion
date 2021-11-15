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
    private boolean breakpointAncho = false;

    public void init(GraficaModeloVista modeloVista) {
        this.modeloVista = modeloVista;

        serie = new XYChart.Series<>();
        serie.dataProperty().bindBidirectional(modeloVista.getPuntosGrafica());

        grafica.getData().add(serie);
        serie.getData().addListener((ListChangeListener<XYChart.Data<Double, Double>>) change ->
                ajustarAnchoGrafica());
        grafica.getScene().widthProperty().addListener((observableValue, number, t1) ->
                ajustarAnchoGrafica());
    }

    private void ajustarAnchoGrafica() {
        int numeroValores = serie.getData().size();
        double limiteSuperior = 0;
        if (numeroValores > 0) {
            limiteSuperior = serie.getData().get(numeroValores - 1).getXValue();
            ejeX.setUpperBound(limiteSuperior);
        }
        double relacionAnchoValores = (grafica.getScene().getWidth() / limiteSuperior);
        if (relacionAnchoValores < anchoMinVoltaje) {
            if (breakpointAncho) {
                grafica.setPrefWidth(anchoMinVoltaje * limiteSuperior);
            }
            else {
                grafica.setPrefWidth(anchoMinVoltaje * limiteSuperior);
                scrollPane.setFitToWidth(false);
                breakpointAncho = true;
            }
        }
        else if (breakpointAncho) {
            grafica.setPrefWidth(Region.USE_COMPUTED_SIZE);
            scrollPane.setFitToWidth(true);
            breakpointAncho = false;
        }
    }
}
