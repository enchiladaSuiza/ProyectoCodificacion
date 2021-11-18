package modelos;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Se encarga de convertir las entradas a sus respectivas formas en binario.
 */
public class CodificadorGestorModelo implements CodificadorModelo {
    private final StringProperty entradaBinario;
    private final LongProperty entradaNumero;
    private final StringProperty entradaTexto;
    private final StringProperty tipoEntrada;
    private final StringProperty salidaBinario;

    /**
     * Inicializa las propiedades y asgina los listeners correspondientes.
     */
    public CodificadorGestorModelo() {
        entradaBinario = new SimpleStringProperty();
        entradaNumero = new SimpleLongProperty();
        entradaTexto = new SimpleStringProperty();
        tipoEntrada = new SimpleStringProperty();
        salidaBinario = new SimpleStringProperty();
        entradaBinario.addListener(
                (observableValue, anterior, nuevo) -> {
                    if (nuevo == null || nuevo.isEmpty()) {
                        salidaBinario.set("0");
                    }
                    else {
                        salidaBinario.set(nuevo);
                    }
                });
        entradaNumero.addListener(
                (observableValue, anterior, nuevo) -> salidaBinario.set(convertirNumero(nuevo.longValue())));
        entradaTexto.addListener(
                (observableValue, anterior, nuevo) -> {
                    if (nuevo == null || nuevo.isEmpty())
                    {
                        salidaBinario.set("0");
                    }
                    else {
                        salidaBinario.set(convertirTexto(nuevo));
                    }
                });

        tipoEntrada.addListener((observableValue, anterior, nuevo) -> {
            if (nuevo.equals(tiposEntradas[0])) {
                salidaBinario.setValue(entradaBinario.get());
            }
            else if (nuevo.equals(tiposEntradas[1])) {
                salidaBinario.setValue(convertirNumero(entradaNumero.get()));
            }
            else if (nuevo.equals(tiposEntradas[2])) {
                salidaBinario.setValue(convertirTexto(entradaTexto.get()));
            }
        });
    }

    /**
     * Devuelve la representación en binario de una cadena de texto.
     * @param texto
     * @return
     */
    public String convertirTexto(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder binarioCompleto = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            String binario = Integer.toBinaryString(texto.charAt(i));
            binarioCompleto.append(binario);
        }
        return binarioCompleto.toString();
    }

    /**
     * Devuelve la representación en binario de un número.
     * @param numero
     * @return
     */
    public String convertirNumero(Long numero) {
        if (numero == null) {
            return "";
        }
        return Long.toBinaryString(numero);
    }

    @Override
    public StringProperty entradaBinarioProperty() {
        return entradaBinario;
    }

    @Override
    public LongProperty entradaNumeroProperty() {
        return entradaNumero;
    }

    @Override
    public StringProperty entradaTextoProperty() {
        return entradaTexto;
    }

    @Override
    public StringProperty tipoEntradaProperty() {
        return tipoEntrada;
    }

    @Override
    public StringProperty salidaBinarioProperty() {
        return salidaBinario;
    }
}
