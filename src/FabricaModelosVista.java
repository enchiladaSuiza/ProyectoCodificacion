import modelosvista.EntradaModeloVista;
import modelosvista.GraficaModeloVista;
import modelosvista.PrincipalModeloVista;
import modelosvista.SalidaModeloVista;

public class FabricaModelosVista {
    private PrincipalModeloVista principalModeloVista;
    private EntradaModeloVista entradaModeloVista;
    private SalidaModeloVista salidaModeloVista;
    private GraficaModeloVista graficaModeloVista;

    public FabricaModelosVista(FabricaModelos fabricaModelos) {
        principalModeloVista = new PrincipalModeloVista(fabricaModelos.getCodificadorModelo());
        entradaModeloVista = new EntradaModeloVista(fabricaModelos.getCodificadorModelo());
        salidaModeloVista = new SalidaModeloVista(
                fabricaModelos.getCodificadorModelo(), fabricaModelos.getGraficadorModelo());
        graficaModeloVista = new GraficaModeloVista(
                fabricaModelos.getCodificadorModelo(), fabricaModelos.getGraficadorModelo());
    }

    public PrincipalModeloVista getPrincipalModeloVista() {
        return principalModeloVista;
    }
    public EntradaModeloVista getEntradaModeloVista() { return entradaModeloVista; }
    public SalidaModeloVista getSalidaModeloVista() { return salidaModeloVista; }
    public GraficaModeloVista getGraficaModeloVista() { return graficaModeloVista; }
}
