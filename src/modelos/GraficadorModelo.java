package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.XYChart;

public interface GraficadorModelo {
    String[] codificaciones = { "Unipolar", "NRZ-L", "NRZ-I", "AMI", "Pseudoternaria", "B8ZS", "Manchester",
            "Manchester Diferencial" };

    StringProperty entradaBitsProperty();
    StringProperty tipoCodificacionProperty();
    ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty();
}
