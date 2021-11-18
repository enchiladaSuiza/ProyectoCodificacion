package backbone;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Llama al método start para iniciar la aplicación.
 * Inicializa a las fábricas y modelos vistas.
 * Abre la pantalla principal.
 */
public class ProgramaCodificacion extends Application {
    @Override
    public void start(Stage escenario) throws Exception {
        FabricaModelos fabricaModelos = new FabricaModelos();
        FabricaModelosVista fabricaModelosVista = new FabricaModelosVista(fabricaModelos);
        EncargadoVistas encargadoVistas = new EncargadoVistas(fabricaModelosVista);
        encargadoVistas.abrirProgramaPrincipal();
    }
}
