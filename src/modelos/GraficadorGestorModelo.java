package modelos;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactúa con los datos de la gráfica. Los modifica en base a la entrada en binario.
 * Es el que implementa los códigos de línea en sí.
 */
public class GraficadorGestorModelo implements GraficadorModelo {
    private StringProperty entradaBits;
    private StringProperty tipoCodificacion;
    private ListProperty<XYChart.Data<Double, Double>> puntosGrafica;

    private char _uno = UNO;
    private char _cero = CERO;

    private boolean voltajePorDefecto = VOLTAJE_POR_DEFECTO;

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
                codificacionRZ(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[4])) {
                codificacionAMI(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[5])) {
                codificacionPseudoternaria(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[6])) {
                codificacionB8ZS(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[7])) {
                codificacionB6ZS(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[8])) {
                codificacionB3ZS(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[9])) {
                codificacionHDB3(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[10])) {
                codificacionManchester(puntos, bits);
            }
            else if (tipoCodificacion.equals(codificaciones[11])) {
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
            if (bits.charAt(i) == _uno) {
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
            if (bits.charAt(i) == _uno) {
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
        boolean voltajeEsAlto = voltajePorDefecto;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) { // Cuando me encuentro con unos cambio de voltaje.
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
     * 1 de alto a nulo, 0 de bajo a nulo
     * @param puntos
     * @param bits
     */
    private void codificacionRZ(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
                agregarMitadVoltaje(puntos, i, ALTO);
            }
            else {
                agregarMitadVoltaje(puntos, i, BAJO);
            }
            agregarMitadVoltaje(puntos, i + 0.5, NULO);
        }
    }

    /**
     * 0 es nulo, 1 va alternando entre alto y bajo.
     * @param puntos
     * @param bits
     */
    private void codificacionAMI(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = voltajePorDefecto;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
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
        boolean voltajeEsAlto = voltajePorDefecto;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _cero) {
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
        boolean voltajeEsAlto = voltajePorDefecto;
        int cerosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
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
     * AMI, pero
     * Si aparecen seis ceros después de un positivo: 0+-0-+
     * Si aparecen seis ceros después de un negativo: 0-+0-+
     * @param puntos
     * @param bits
     */
    private void codificacionB6ZS(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = voltajePorDefecto;
        int cerosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
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
                if (cerosAcumulados == 6) {
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
     * AMI, pero
     * Si aparecen tres ceros, serán reemplazados según
     * Polaridad del pulso anterior     Número de 1 desde la última substitución
     *                                      Impar               Par
     *              -                       00-                 +0+
     *              +                       00+                 -0-
     * @param puntos
     * @param bits
     */
    private void codificacionB3ZS(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = voltajePorDefecto;
        int cerosAcumulados = 0;
        int unosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
                unosAcumulados++;
                cerosAcumulados = 0;
                voltajeEsAlto = !voltajeEsAlto; // Cambio de voltaje
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(puntos, i, ALTO);
                } else {
                    agregarUnidadVoltaje(puntos, i, BAJO);
                }
            }
            else {
                cerosAcumulados++;
                if (cerosAcumulados == 3) {
                    if (unosAcumulados % 2 == 0) { // # par de unos anteriores
                        if (voltajeEsAlto) { // Se quedó en +
                            modificarUnidadVoltaje(puntos, i - 2, BAJO);
                            agregarUnidadVoltaje(puntos, i, BAJO);
                        }
                        else { // Se quedó en -
                            modificarUnidadVoltaje(puntos, i - 2, ALTO);
                            agregarUnidadVoltaje(puntos, i, ALTO);
                        }
                        voltajeEsAlto = !voltajeEsAlto;
                    }
                    else { // # impar de unos anteriores
                        if (voltajeEsAlto) { // Se quedó en +
                            agregarUnidadVoltaje(puntos, i, ALTO);
                        }
                        else { // Se quedó en -
                            agregarUnidadVoltaje(puntos, i, BAJO);
                        }
                    }
                    cerosAcumulados = 0;
                    unosAcumulados = 0;
                }
                else {
                    agregarUnidadVoltaje(puntos, i, NULO);
                }
            }
        }
    }

    /**
     * AMI, pero
     * Si aparecen cuatro ceros, serán reemplazados según
     * Polaridad del pulso anterior     Número de 1 desde la última substitución
     *                                      Impar               Par
     *              -                       000-                +00+
     *              +                       000+                -00-
     * @param puntos
     * @param bits
     */
    private void codificacionHDB3(ArrayList<XYChart.Data<Double, Double>> puntos, String bits) {
        boolean voltajeEsAlto = voltajePorDefecto;
        int cerosAcumulados = 0;
        int unosAcumulados = 0;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
                unosAcumulados++;
                cerosAcumulados = 0;
                voltajeEsAlto = !voltajeEsAlto; // Cambio de voltaje
                if (voltajeEsAlto) {
                    agregarUnidadVoltaje(puntos, i, ALTO);
                } else {
                    agregarUnidadVoltaje(puntos, i, BAJO);
                }
            }
            else {
                cerosAcumulados++;
                if (cerosAcumulados == 4) {
                    if (unosAcumulados % 2 == 0) { // # par de unos anteriores
                        if (voltajeEsAlto) { // Se quedó en +
                            modificarUnidadVoltaje(puntos, i - 3, BAJO);
                            agregarUnidadVoltaje(puntos, i, BAJO);
                        }
                        else { // Se quedó en -
                            modificarUnidadVoltaje(puntos, i - 3, ALTO);
                            agregarUnidadVoltaje(puntos, i, ALTO);
                        }
                        voltajeEsAlto = !voltajeEsAlto;
                    }
                    else { // # impar de unos anteriores
                        if (voltajeEsAlto) { // Se quedó en +
                            agregarUnidadVoltaje(puntos, i, ALTO);
                        }
                        else { // Se quedó en -
                            agregarUnidadVoltaje(puntos, i, BAJO);
                        }
                    }
                    cerosAcumulados = 0;
                    unosAcumulados = 0;
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
            if (bits.charAt(i) == _uno) {
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
        boolean voltajeActual = voltajePorDefecto;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == _uno) {
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

    public void intercambiarCerosConUnos() {
        char temporal = _uno;
        _uno = _cero;
        _cero = temporal;
        actualizarDatos(entradaBits.get(), tipoCodificacion.get());
    }

    public void alternarVoltajePorDefecto() {
        voltajePorDefecto = !voltajePorDefecto;
        actualizarDatos(entradaBits.get(), tipoCodificacion.get());
    }
    
    @Override
    public StringProperty entradaBitsProperty() { return entradaBits; }

    @Override
    public StringProperty tipoCodificacionProperty() { return tipoCodificacion; }

    @Override
    public ListProperty<XYChart.Data<Double, Double>> puntosGraficaProperty() { return puntosGrafica; }
}
