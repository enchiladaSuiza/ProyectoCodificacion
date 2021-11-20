package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.XYChart;

public interface GraficadorModelo {
    String[] codificaciones = { "Unipolar", "NRZ-L", "NRZ-I", "RZ", "AMI", "Pseudoternaria", "B8ZS", "B6ZS", "B3ZS",
            "HDB3", "Manchester", "Manchester Diferencial" };

    StringProperty entradaBitsProperty();
    StringProperty tipoCodificacionProperty();
    ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty();
}
