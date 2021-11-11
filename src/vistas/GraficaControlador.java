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
    private LineChart<Number, Number> grafica;

    @FXML
    private NumberAxis ejeX;

    @FXML
    private NumberAxis ejeY;

    private XYChart.Series<Number, Number> serie;
    private final int relacionAnchoValoresMinimo = 5;
    private boolean breakpointAncho = false;

    public void init(GraficaModeloVista modeloVista) {
        this.modeloVista = modeloVista;

        serie = new XYChart.Series<>();
        serie.dataProperty().bindBidirectional(modeloVista.getPuntosGrafica());

        grafica.getData().add(serie);
        serie.getData().addListener((ListChangeListener<XYChart.Data<Number, Number>>) change ->
                ajustarAnchoGrafica());
        grafica.getScene().widthProperty().addListener((observableValue, number, t1) ->
                ajustarAnchoGrafica());
    }

    private void ajustarAnchoGrafica() {
        int numeroValores = serie.getData().size();
        int relacionAnchoValores = (int) (grafica.getScene().getWidth() / numeroValores);
        if (relacionAnchoValores < relacionAnchoValoresMinimo) {
            if (breakpointAncho) {
                grafica.setPrefWidth(relacionAnchoValoresMinimo * numeroValores);
            }
            else {
                grafica.setPrefWidth(relacionAnchoValoresMinimo * numeroValores);
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
