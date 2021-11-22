package modelos;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

/**
 * Interfaz implementada por el codificador gestor. Conteine las definiciones
 * para getters y un arreglo con el tipo de entradas.
 */
public interface CodificadorModelo {
    String[] tiposEntradas = { "Binario", "Número", "Texto" };

    StringProperty entradaBinarioProperty();
    LongProperty entradaNumeroProperty();
    StringProperty entradaTextoProperty();
    StringProperty tipoEntradaProperty();
    StringProperty salidaBinarioProperty();
    String numeroAscii(Long numero);

    String convertirNumero(Long numero);
}
