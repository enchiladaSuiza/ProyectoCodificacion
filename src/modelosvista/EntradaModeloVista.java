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

    public EntradaModeloVista(CodificadorModelo modelo) {
        this.modelo = modelo;
        entradaBinario = new SimpleStringProperty();
        entradaTexto = new SimpleStringProperty();
        entradaNumero = new SimpleStringProperty();
        tipoEntrada = new SimpleStringProperty();
        entradaBinario.bindBidirectional(modelo.getEntradaBinario());
        entradaTexto.bindBidirectional(modelo.getEntradaTexto());
        entradaNumero.bindBidirectional(modelo.getEntradaNumero(), new StringConverter<>() {
            @Override
            public String toString(Number number) {
                if ((long)number == 0) {
                    return "";
                } else {
                    return number.toString();
                }
            }

            @Override
            public Number fromString(String s) {
                try {
                    return Long.parseLong(s);
                } catch (Exception e) {
                    return null;
                }
            }
        });
        tipoEntrada.bindBidirectional(modelo.getTipoEntrada());
    }

    public StringProperty getEntradaNumero() { return entradaNumero; }
    public StringProperty getEntradaBinario() { return entradaBinario; }
    public StringProperty getEntradaTexto() { return entradaTexto; }
    public StringProperty getTipoEntrada() { return tipoEntrada; }
}
