package backbone;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Llama al método start para iniciar la aplicación.
 * Inicializa a las fábricas y modelos vistas.
 * Abre la pantalla principal.
 */
public class ProgramaCodificacion extends Application {
    public static void main(String[] args) {
        String directorio = System.getProperty("user.dir");
        System.out.println("ProgramaCodificacion: " + directorio);
        Application.launch(args);
    }

    @Override
    public void start(Stage escenario) throws Exception {
        FabricaModelos fabricaModelos = new FabricaModelos();
        FabricaModelosVista fabricaModelosVista = new FabricaModelosVista(fabricaModelos);
        EncargadoVistas encargadoVistas = new EncargadoVistas(fabricaModelosVista);
        encargadoVistas.abrirProgramaPrincipal();
    }
}
