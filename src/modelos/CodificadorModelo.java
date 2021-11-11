package modelos;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public interface CodificadorModelo {
    String[] tiposEntradas = { "Binario", "Número", "Texto" };

    StringProperty getEntradaBinario();
    LongProperty getEntradaNumero();
    StringProperty getEntradaTexto();
    StringProperty getTipoEntrada();
    StringProperty getSalidaBinario();
}
