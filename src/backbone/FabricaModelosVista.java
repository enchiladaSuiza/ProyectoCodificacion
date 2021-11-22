package backbone;

import modelosvista.*;

/**
 * Clase que porta los modelos vista, para asegurar que sólo exista uno de cada uno.
 */
public class FabricaModelosVista {
    private PrincipalModeloVista principalModeloVista;
    private EntradaModeloVista entradaModeloVista;
    private SalidaModeloVista salidaModeloVista;
    private GraficaModeloVista graficaModeloVista;
    private AsciiModeloVista asciiModeloVista;

    public FabricaModelosVista(FabricaModelos fabricaModelos) {
        principalModeloVista = new PrincipalModeloVista(fabricaModelos.getCodificadorModelo());
        entradaModeloVista = new EntradaModeloVista(fabricaModelos.getCodificadorModelo());
        salidaModeloVista = new SalidaModeloVista(
                fabricaModelos.getCodificadorModelo(), fabricaModelos.getGraficadorModelo());
        graficaModeloVista = new GraficaModeloVista(
                fabricaModelos.getCodificadorModelo(), fabricaModelos.getGraficadorModelo());
        asciiModeloVista = new AsciiModeloVista(fabricaModelos.getCodificadorModelo());
    }

    public PrincipalModeloVista getPrincipalModeloVista() {
        return principalModeloVista;
    }
    public EntradaModeloVista getEntradaModeloVista() { return entradaModeloVista; }
    public SalidaModeloVista getSalidaModeloVista() { return salidaModeloVista; }
    public GraficaModeloVista getGraficaModeloVista() { return graficaModeloVista; }
    public AsciiModeloVista getAsciiModeloVista() { return asciiModeloVista; }
}
