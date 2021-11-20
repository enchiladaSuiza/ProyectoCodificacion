package vistas;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import modelosvista.GraficaModeloVista;

/**
 * Comunicación directa con el archivo fxml y sus nodos.
 */
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

    /**
     * Enlaza las propiedades con las del modelo vista.
     * Añade los listeners para ajustar el ancho.
     * @param modeloVista
     */
    public void init(GraficaModeloVista modeloVista) {
        this.modeloVista = modeloVista;

        serie = new XYChart.Series<>();
        serie.dataProperty().bindBidirectional(modeloVista.puntosGraficaProperty());

        grafica.getData().add(serie);
        serie.dataProperty().addListener((observableValue, data, t1) -> ajustarAnchoGrafica());
        grafica.getScene().widthProperty().addListener((observableValue, anterior, nuevo) -> ajustarAnchoGrafica());
    }

    /**
     * Configura el límite superior del eje x, el ancho de la gráfica y si el scrollpane aparece.
     */
    private void ajustarAnchoGrafica() {
        double maximoAbscisas = modeloVista.valorMaximoAbscisas();
        ejeX.setUpperBound(maximoAbscisas);
        double relacionAnchoValores = grafica.getScene().getWidth() / maximoAbscisas;
        if (relacionAnchoValores < anchoMinVoltaje) {
            grafica.setPrefWidth(anchoMinVoltaje * maximoAbscisas);
            scrollPane.setFitToWidth(false);
        }
        else {
            grafica.setPrefWidth(Region.USE_COMPUTED_SIZE);
            scrollPane.setFitToWidth(true);
        }
    }

    /**
     * Agrega o quita los símbolos de la gráfica (los circulitos que aparecen en cada punto).
     */
    public void alternarSimbolos() {
        grafica.setCreateSymbols(!grafica.getCreateSymbols());
    }

    public void intercambiarCerosConUnos() {
        modeloVista.intercambiarCerosConUnos();
    }

    public void alternarVoltajePorDefecto() {
        modeloVista.alternarVoltajePorDefecto();
    }
}
