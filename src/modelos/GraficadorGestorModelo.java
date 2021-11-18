package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactúa con los datos de la gráfica. Los modifica en base a la entrada en binario.
 * Es el que implementa los códigos de línea en sí.
 */
public class GraficadorGestorModelo implements GraficadorModelo {
    private float ALTO = 0.9f;
    private float NULO = 0;
    private float BAJO = -0.9f;

    private StringProperty entradaBits;
    private StringProperty tipoCodificacion;
    private ListProperty<XYChart.Data<Double, Double>> puntosGrafica;

    /**
     * Inicializa las propiedades y adhiere listeners.
     */
    public GraficadorGestorModelo() {
        entradaBits = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        puntosGrafica = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
        entradaBits.addListener(
                (observableValue, anterior, nuevo) -> actualizarDatos(nuevo, tipoCodificacion.get()));
        tipoCodificacion.addListener(
                (observableValue, anterior, nuevo)-> actualizarDatos(entradaBits.get(), nuevo));
    }

    /**
     * Función de utilidad para agregar dos puntos de en la gráfica en el voltaje especificado.
     * Uno en la posición X dada, y otro en la posición X + 1.
     * @param puntos
     * @param posicionX
     * @param voltaje
     */
    private void agregarUnidadVoltaje(
            ArrayList<XYChart.Data<Double, Double>> puntos, double posicionX, double voltaje) {
        puntos.add(new XYChart.Data<>(posicionX, voltaje));
        puntos.add(new XYChart.Data<>(posicionX + 1, voltaje));
    }

    /**
     * Función de utilidad para agregar dos putnos en la gráfica en el voltaje especificado.
     * Uno en la posición X dada, y otro en la posición X + 0.5.
     * @param puntos
     * @param posicionX
     * @param voltaje
     */
    private void agregarMitadVoltaje(
            ArrayList<XYChart.Data<Double, Double>> puntos, double posicionX, double voltaje) {
        puntos.add(new XYChart.Data<>(posicionX, voltaje));
        puntos.add(new XYChart.Data<>(posicionX + 0.5, voltaje));
    }

    /**
     * Busca la serie de puntos dada para encontrar los dos puntos en X, y les asigna un voltaje nuevo.
     * @param puntos
     * @param posicionX
     * @param voltajeNuevo
     */
    private void modificarUnidadVoltaje(
            ArrayList<XYChart.Data<Double, Double>> puntos, double posicionX, double voltajeNuevo) {
        // Lista de datos que están en la misma posición x
        List<XYChart.Data<Double, Double>> lista = puntos.stream()
                .filter(i -> i.getXValue() == posicionX).toList();
        // Obtenemos el último que se agregó, pues el anterior pertenece a otra unidad de voltaje.
        XYChart.Data<Double, Double> dato1 = lista.get(lista.size() - 1);
        // Obtenenmos el primer punto agregado en la posción x + 1, que corresponde a la misma unidad de voltaje.
        XYChart.Data<Double, Double> dato2 =
                puntos.stream().filter(i -> i.getXValue() == posicionX + 1).toList().get(0);
        dato1.setYValue(voltajeNuevo);
        dato2.setYValue(voltajeNuevo);
    }

    private void actualizarDatos(String bits, String tipoCodificacion) {
        ArrayList<XYChart.Data<Double, Double>> puntos = new ArrayList<>();
        if (tipoCodificacion != null && bits != null) {
            if (tipoCodificacion.equals(codificaciones[0])) {
                codificacionUnipolar(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[1])) {
                codificacionNRZL(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[2])) {
                codificacionNRZI(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[3])) {
                codificacionAMI(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[4])) {
                codificacionPseudoternaria(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[5])) {
                codificacionB8ZS(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[6])) {
                codificacionManchester(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[7])) {
                codificacionManchesterDiferencial(puntos, bits);
            }
        }
        puntosGrafica.set(FXCollections.observableArrayList(puntos));
    }

    /**
     * 1 es voltaje alto, 0 es nulo.
     * @param bits
     */
    private void codificacionUnipolar(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarUnidadVoltaje(puntos, i, ALTO);
            }
            else {
                agregarUnidadVoltaje(puntos, i, NULO);
            }
        }
    }

    /**
     * 0 es voltaje alto, 1 es voltaje bajo
     * @param puntos
     * @param bits
     */
    private void codificacionNRZL(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarUnidadVoltaje(puntos, i, BAJO);
            }
            else {
                agregarUnidadVoltaje(puntos, i, ALTO);
            }
        }
    }

    /**
     * 0 es no transición, 1 es transición
     * @param puntos
     * @param bits
     */
    private void codificacionNRZI(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') { // Cuando me encuentro con unos cambio de voltaje.
                voltajeEsAlto = !voltajeEsAlto;
            }
            if (voltajeEsAlto) {
                agregarUnidadVoltaje(puntos, i, ALTO);
            }
            else {
                agregarUnidadVoltaje(puntos, i, BAJO);
            }
        }
    }

    /**
     * 0 es nulo, 1 va alternando entre alto y bajo.
     * @param puntos
     * @param bits
     */
    private void codificacionAMI(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                voltajeEsAlto = !voltajeEsAlto; // Cambio de voltaje
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(puntos, i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(puntos, i, BAJO);
                }
            }
            else {
                agregarUnidadVoltaje(puntos, i, NULO);
            }
        }
    }

    /**
     * 0 alterna entre alto y bajo, 1 es nulo.
     * @param puntos
     * @param bits
     */
    private void codificacionPseudoternaria(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '0') {
                voltajeEsAlto = !voltajeEsAlto; // Cambio de voltaje
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(puntos, i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(puntos, i, BAJO);
                }
            }
            else {
                agregarUnidadVoltaje(puntos, i, NULO);
            }
        }
    }

    /**
     * AMI, pero
     * Si aparecen ocho ceros después de un positivo: 000+-0-+
     * Si aparecen ocho ceros después de un negativo: 000-+0-+
     * @param puntos
     * @param bits
     */
    private void codificacionB8ZS(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = false;
        int cerosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                cerosAcumulados = 0;
                voltajeEsAlto = !voltajeEsAlto;
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(puntos, i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(puntos, i, BAJO);
                }
            }
            else {
                cerosAcumulados++;
                if (cerosAcumulados == 8) {
                    if (voltajeEsAlto) {
                        modificarUnidadVoltaje(puntos, i - 4, ALTO);
                        modificarUnidadVoltaje(puntos, i - 3, BAJO);
                        modificarUnidadVoltaje(puntos, i - 1, BAJO);
                        agregarUnidadVoltaje(puntos, i, ALTO);
                    }
                    else {
                        modificarUnidadVoltaje(puntos, i - 4, BAJO);
                        modificarUnidadVoltaje(puntos, i - 3, ALTO);
                        modificarUnidadVoltaje(puntos, i - 1, ALTO);
                        agregarUnidadVoltaje(puntos, i, BAJO);
                    }
                    cerosAcumulados = 0;
                }
                else {
                    agregarUnidadVoltaje(puntos, i, NULO);
                }
            }
        }
    }

    /**
     * 0 de alto a bajo, 1 de bajo a alto.
     * @param puntos
     * @param bits
     */
    private void codificacionManchester(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarMitadVoltaje(puntos, i, BAJO);
                agregarMitadVoltaje(puntos, i + 0.5f, ALTO);
            }
            else {
                agregarMitadVoltaje(puntos, i, ALTO);
                agregarMitadVoltaje(puntos, i + 0.5f, BAJO);
            }
        }
    }

    /**
     * 0 cambia al inicio, 1 cambia a la mitad.
     * @param puntos
     * @param bits
     */
    private void codificacionManchesterDiferencial(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeActual = true;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                if (voltajeActual) {
                    agregarMitadVoltaje(puntos, i, ALTO);
                    agregarMitadVoltaje(puntos, i + 0.5f, BAJO);
                }
                else {
                    agregarMitadVoltaje(puntos, i, BAJO);
                    agregarMitadVoltaje(puntos, i + 0.5f, ALTO);
                }
                voltajeActual = !voltajeActual;
            }
            else {
                if (voltajeActual) {
                    agregarMitadVoltaje(puntos, i, BAJO);
                    agregarMitadVoltaje(puntos, i + 0.5f, ALTO);
                }
                else {
                    agregarMitadVoltaje(puntos, i, ALTO);
                    agregarMitadVoltaje(puntos, i + 0.5f, BAJO);
                }
            }
        }
    }

    @Override
    public StringProperty entradaBitsProperty() { return entradaBits; }

    @Override
    public StringProperty tipoCodificacionProperty() { return tipoCodificacion; }

    @Override
    public ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty() { return puntosGrafica; }
}
