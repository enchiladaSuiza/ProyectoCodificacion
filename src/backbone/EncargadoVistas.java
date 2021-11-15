package backbone;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vistas.PortadaControlador;
import vistas.PrincipalControlador;

import java.io.IOException;
import java.util.ArrayList;

public class EncargadoVistas {
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital - Comunicaciones en Redes";
    private final String archivoPantallaPrincipal = "../vistas/Principal.fxml";
    private final String archivoPortada = "../vistas/Portada.fxml";
    private final String archivoEstilosBase = "../vistas/css/estilosBase.css";
    private final String archivoTemaOscuro = "../vistas/css/temaOscuro.css";
    private Scene escena;
    private String estilosBase;
    private ArrayList<String> temas;
    private ObservableList<String> stylesheets;

    public EncargadoVistas(FabricaModelosVista fabricaModelosVista) {
        this.fabricaModelosVista = fabricaModelosVista;

        estilosBase = getClass().getResource(archivoEstilosBase).toExternalForm();

        temas = new ArrayList<>();
        String temaOscuro = getClass().getResource(archivoTemaOscuro).toExternalForm();
        temas.add(temaOscuro);

        stylesheets = FXCollections.observableArrayList(estilosBase);
        stylesheets.addListener((ListChangeListener<String>) change -> {
            if (escena != null) {
                escena.getStylesheets().clear();
                escena.getStylesheets().addAll(change.getList());
            }
        });
    }

    public void abrirProgramaPrincipal() throws Exception {
        abrirVista(archivoPantallaPrincipal);
    }

    public void abrirPortada() throws Exception {
        abrirVista(archivoPortada);
    }

    private void abrirVista(String vista) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
        Parent raiz = loader.load();
        Stage escenario = new Stage();
        if (vista.equals(archivoPantallaPrincipal)) {
            Scene escena = new Scene(raiz, 600, 400);
            this.escena = escena;
            escena.getStylesheets().addAll(stylesheets);

            escenario.setMinWidth(400);
            escenario.setMinHeight(300);
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.show();

            PrincipalControlador controlador = loader.getController();
            controlador.init(fabricaModelosVista.getPrincipalModeloVista(), this);
            controlador.getEntradaController().init(fabricaModelosVista.getEntradaModeloVista());
            controlador.getSalidaController().init(fabricaModelosVista.getSalidaModeloVista());
            controlador.getGraficaController().init(fabricaModelosVista.getGraficaModeloVista());
        }

        else if (vista.equals(archivoPortada)) {
            Scene escena = new Scene(raiz);
            escena.getStylesheets().addAll(stylesheets);

            escenario.setMinWidth(750);
            escenario.setMinHeight(445);
            escenario.setScene(escena);
            escenario.show();

            PortadaControlador controlador = loader.getController();
            controlador.init(this);
        }
    }

    public void aplicarTemaOscuro() {
        quitarTemas();
        stylesheets.add(temas.get(0));
    }

    public void quitarTemas() {
        for (int i = 0; i < stylesheets.size(); i++) {
            if (temas.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }
}
