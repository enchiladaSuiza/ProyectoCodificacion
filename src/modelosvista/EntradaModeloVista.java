package modelosvista;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import modelos.CodificadorModelo;

public class EntradaModeloVista {
    private CodificadorModelo modelo;

    private StringProperty entradaBinario;
    private StringProperty entradaTexto;
    private StringProperty entradaNumero;
    private StringProperty tipoEntrada;

    /**
     * Conecta las propiedades del controlador con el controlador.
     * Por ahora su principal función es convertir la entrada de números.
     * @param modelo
     */
    public EntradaModeloVista(CodificadorModelo modelo) {
        this.modelo = modelo;
        entradaBinario = new SimpleStringProperty();
        entradaTexto = new SimpleStringProperty();
        entradaNumero = new SimpleStringProperty();
        tipoEntrada = new SimpleStringProperty();
        entradaBinario.bindBidirectional(modelo.entradaBinarioProperty());
        entradaTexto.bindBidirectional(modelo.entradaTextoProperty());
        entradaNumero.bindBidirectional(modelo.entradaNumeroProperty(), new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return number.toString();
            }

            @Override
            public Number fromString(String s) {
                try {
                    return Long.parseLong(s);
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        tipoEntrada.bindBidirectional(modelo.tipoEntradaProperty());
    }

    public StringProperty entradaNumeroProperty() { return entradaNumero; }
    public StringProperty entradaBinarioProperty() { return entradaBinario; }
    public StringProperty entradaTextoProperty() { return entradaTexto; }
    public StringProperty tipoEntradaProperty() { return tipoEntrada; }
}
