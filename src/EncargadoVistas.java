import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vistas.PrincipalControlador;

import java.io.IOException;
import java.util.Objects;

public class EncargadoVistas {
    private final Stage escenario;
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital - Comunicaciones en Redes";
    private final String archivoPantallaPrincipal = "vistas/Principal.fxml";
    private final String archivoEstilosBase = "vistas/css/estilosBase.css";
    private String estilosBase;

    public EncargadoVistas(Stage escenario, FabricaModelosVista fabricaModelosVista) {
        this.escenario = escenario;
        this.fabricaModelosVista = fabricaModelosVista;
    }

    public void empezar() throws Exception {
        abrirVista(archivoPantallaPrincipal);
    }

    public void abrirVista(String vista) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
        Parent raiz = loader.load();
        Scene escena = new Scene(raiz, 600, 400);

        escenario.setMinWidth(400);
        escenario.setMinHeight(300);
        escenario.setScene(escena);
        escenario.show();

        estilosBase = getClass().getResource(archivoEstilosBase).toExternalForm();
        escena.getStylesheets().add(estilosBase);

        if (vista.equals(archivoPantallaPrincipal)) {
            PrincipalControlador controlador = loader.getController();
            controlador.init(fabricaModelosVista.getPrincipalModeloVista(), escena);
            controlador.getEntradaController().init(fabricaModelosVista.getEntradaModeloVista());
            controlador.getSalidaController().init(fabricaModelosVista.getSalidaModeloVista());
            controlador.getGraficaController().init(fabricaModelosVista.getGraficaModeloVista());
            escenario.setTitle(titulo);
        }
    }
}
