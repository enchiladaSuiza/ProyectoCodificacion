package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.XYChart;

public interface GraficadorModelo {
    float ALTO = 0.9f;
    float NULO = 0;
    float BAJO = -0.9f;

    char CERO = '0';
    char UNO = '1';

    boolean VOLTAJE_POR_DEFECTO = false;

    String[] codificaciones = { "Unipolar", "NRZ-L", "NRZ-I", "RZ", "AMI", "Pseudoternaria", "B8ZS", "B6ZS", "B3ZS",
            "HDB3", "Manchester", "Manchester Diferencial" };

    void intercambiarCerosConUnos();
    void alternarVoltajePorDefecto();

    StringProperty entradaBitsProperty();
    StringProperty tipoCodificacionProperty();
    ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty();
}
