package backbone;

import modelos.CodificadorGestorModelo;
import modelos.CodificadorModelo;
import modelos.GraficadorGestorModelo;
import modelos.GraficadorModelo;

/**
 * Clase que porta referencias a los modelos, para asegurar que sólo exista uno
 * de cada uno a la vez.
 */
public class FabricaModelos {
    private CodificadorModelo codificadorModelo;
    private GraficadorModelo graficadorModelo;

    public CodificadorModelo getCodificadorModelo() {
        if (codificadorModelo == null) {
            codificadorModelo = new CodificadorGestorModelo();
        }
        return codificadorModelo;
    }

    public GraficadorModelo getGraficadorModelo() {
        if (graficadorModelo == null) {
            graficadorModelo = new GraficadorGestorModelo();
        }
        return graficadorModelo;
    }
}
