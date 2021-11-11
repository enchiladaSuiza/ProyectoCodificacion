package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class GraficadorGestorModelo implements GraficadorModelo {
    private int ALTO = 1;
    private int NULO = 0;
    private int BAJO = -1;

    private StringProperty entradaBits;
    private StringProperty tipoCodificacion;
    private ListProperty<XYChart.Data<Number, Number>> puntosGrafica;

    public GraficadorGestorModelo() {
        entradaBits = new SimpleStringProperty();
        tipoCodificacion = new SimpleStringProperty();
        puntosGrafica = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
        entradaBits.addListener(
                (observableValue, anterior, nuevo) -> actualizarDatos(nuevo, tipoCodificacion.get()));
        tipoCodificacion.addListener(
                (observableValue, anterior, nuevo)-> actualizarDatos(entradaBits.get(), nuevo));
    }

    private void agregarUnidadVoltaje(int posicionX, int voltaje) {
        puntosGrafica.add(new XYChart.Data<>(posicionX, voltaje));
        puntosGrafica.add(new XYChart.Data<>(posicionX + 1, voltaje));
    }

    private void agregarMitadVoltaje(float posicionX, int voltaje) {
        puntosGrafica.add(new XYChart.Data<>(posicionX, voltaje));
        puntosGrafica.add(new XYChart.Data<>(posicionX + 0.5, voltaje));
    }

    // función fea :(
    private void modificarUnidadVoltaje(int posicionX, int voltajeNuevo) {
        // lista de los datos que están en la misma posiciónX
        List<XYChart.Data<Number, Number>> lista = puntosGrafica.stream()
                .filter(i -> (int)i.getXValue() == posicionX).toList();
        // normalmente queremos el que se agregó al último
        XYChart.Data<Number, Number> dato1 = lista.get(lista.size() - 1);
        // después, como queremos cambiar la unidad entera, necesitamos el siguiente punto
        // este es el primero que agregamos
        XYChart.Data<Number, Number> dato2 =
                puntosGrafica.stream().filter(i -> (int)i.getXValue() == posicionX + 1).toList().get(0);
        dato1.setYValue(voltajeNuevo);
        dato2.setYValue(voltajeNuevo);
    }

    private void actualizarDatos(String bits, String tipoCodificacion) {
        puntosGrafica.clear();
        if (tipoCodificacion != null && bits != null) {
            if (tipoCodificacion.equals(codificaciones[0])) {
                codificacionUnipolar(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[1])) {
                codificacionNRZL(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[2])) {
                codificacionNRZI(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[3])) {
                codificacionAMI(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[4])) {
                codificacionPseudoternaria(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[5])) {
                codificacionB8ZS(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[6])) {
                codificacionManchester(bits);
            }
            else if (tipoCodificacion.equals(codificaciones[7])) {
                codificacionManchesterDiferencial(bits);
            }
        }
    }

    // 1 es voltaje alto, 0 es nulo
    private void codificacionUnipolar(String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarUnidadVoltaje(i, ALTO);
            }
            else {
                agregarUnidadVoltaje(i, NULO);
            }
        }
    }

    // 0 es voltaje alto, 1 es voltaje bajo
    private void codificacionNRZL(String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarUnidadVoltaje(i, BAJO);
            }
            else {
                agregarUnidadVoltaje(i, ALTO);
            }
        }
    }

    // 0 es no transición, 1 es transición
    private void codificacionNRZI(String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                voltajeEsAlto = !voltajeEsAlto;
            }
            if (voltajeEsAlto) {
                agregarUnidadVoltaje(i, ALTO);
            }
            else {
                agregarUnidadVoltaje(i, BAJO);
            }
        }
    }

    // 0 es nulo, 1 va alternando entre alto y bajo
    private void codificacionAMI(String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                voltajeEsAlto = !voltajeEsAlto;
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(i, BAJO);
                }
            }
            else {
                agregarUnidadVoltaje(i, NULO);
            }
        }
    }

    // 0 alterna entre alto y bajo, 1 es nulo
    private void codificacionPseudoternaria(String bits) {
        boolean voltajeEsAlto = false;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '0') {
                voltajeEsAlto = !voltajeEsAlto;
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(i, BAJO);
                }
            }
            else {
                agregarUnidadVoltaje(i, NULO);
            }
        }
    }

    // AMI, pero
    // Si aparecen ocho ceros después de un positivo: 000+-0-+
    // Si aparecen ocho ceros después de un negativo: 000-+0-+
    private void codificacionB8ZS(String bits) {
        boolean voltajeEsAlto = false;
        int cerosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                cerosAcumulados = 0;
                voltajeEsAlto = !voltajeEsAlto;
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(i, ALTO);
                }
                else {
                    agregarUnidadVoltaje(i, BAJO);
                }
            }
            else {
                cerosAcumulados++;
                if (cerosAcumulados == 8) {
                    if (voltajeEsAlto) {
                        modificarUnidadVoltaje(i - 4, ALTO);
                        modificarUnidadVoltaje(i - 3, BAJO);
                        modificarUnidadVoltaje(i - 1, BAJO);
                        agregarUnidadVoltaje(i, ALTO);
                    }
                    else {
                        modificarUnidadVoltaje(i - 4, BAJO);
                        modificarUnidadVoltaje(i - 3, ALTO);
                        modificarUnidadVoltaje(i - 1, ALTO);
                        agregarUnidadVoltaje(i, BAJO);
                    }
                    cerosAcumulados = 0;
                }
                else {
                    agregarUnidadVoltaje(i, NULO);
                }
            }
        }
    }

    // 0 de alto a bajo, 1 de bajo a alto
    private void codificacionManchester(String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                agregarMitadVoltaje(i, BAJO);
                agregarMitadVoltaje(i + 0.5f, ALTO);
            }
            else {
                agregarMitadVoltaje(i, ALTO);
                agregarMitadVoltaje(i + 0.5f, BAJO);
            }
        }
    }

    // 0 cambia al inicio, 1 cambia a la mitad
    private void codificacionManchesterDiferencial(String bits) {
        boolean voltajeActual = true;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                if (voltajeActual) {
                    agregarMitadVoltaje(i, ALTO);
                    agregarMitadVoltaje(i + 0.5f, BAJO);
                }
                else {
                    agregarMitadVoltaje(i, BAJO);
                    agregarMitadVoltaje(i + 0.5f, ALTO);
                }
                voltajeActual = !voltajeActual;
            }
            else {
                if (voltajeActual) {
                    agregarMitadVoltaje(i, BAJO);
                    agregarMitadVoltaje(i + 0.5f, ALTO);
                }
                else {
                    agregarMitadVoltaje(i, ALTO);
                    agregarMitadVoltaje(i + 0.5f, BAJO);
                }
            }
        }
    }

    public StringProperty getEntradaBits() { return entradaBits; }
    public StringProperty getTipoCodificacion() { return tipoCodificacion; }
    public ListProperty<XYChart.Data<Number, Number>> getPuntosGrafica() { return puntosGrafica; }
}
