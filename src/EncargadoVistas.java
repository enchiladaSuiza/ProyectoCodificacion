import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import vistas.PrincipalControlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class EncargadoVistas {
    private final Stage escenario;
    private Scene escena;
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital - Comunicaciones en Redes";
    private final String archivoPantallaPrincipal = "vistas/Principal.fxml";
    private final String archivoEstilosBase = "vistas/css/estilosBase.css";
    private final String archivoTemaOscuro = "vistas/css/temaOscuro.css";
    private String estilosBase;
    private ArrayList<String> temas;

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

        escena = new Scene(raiz, 600, 400);

        estilosBase = Objects.requireNonNull(getClass().getResource(archivoEstilosBase)).toExternalForm();
        escena.getStylesheets().add(estilosBase);

        temas = new ArrayList<>();
        String temaOscuro = Objects.requireNonNull(getClass().getResource(archivoTemaOscuro)).toExternalForm();
        temas.add(temaOscuro);

        escena.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.F1) {
                aplicarTemaOscuro();
                ke.consume();
            }
            else if (ke.getCode() == KeyCode.F2) {
                quitarTemas();
                ke.consume();
            }
        });

        escenario.setMinWidth(400);
        escenario.setMinHeight(300);
        escenario.setScene(escena);
        escenario.show();

        if (vista.equals(archivoPantallaPrincipal)) {
            PrincipalControlador controlador = loader.getController();
            controlador.init(fabricaModelosVista.getPrincipalModeloVista());
            controlador.getEntradaController().init(fabricaModelosVista.getEntradaModeloVista());
            controlador.getSalidaController().init(fabricaModelosVista.getSalidaModeloVista());
            controlador.getGraficaController().init(fabricaModelosVista.getGraficaModeloVista());
            escenario.setTitle(titulo);
        }
    }

    public void aplicarTemaOscuro() {
        quitarTemas();
        escena.getStylesheets().add(temas.get(0));
    }

    public void quitarTemas() {
        ObservableList<String> stylesheets = escena.getStylesheets();
        for (int i = 0; i < stylesheets.size(); i++) {
            if (temas.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }
}
