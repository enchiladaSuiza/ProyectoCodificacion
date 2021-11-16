package backbone;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vistas.PortadaControlador;
import vistas.PrincipalControlador;

import java.io.IOException;
import java.util.ArrayList;

public class EncargadoVistas {
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital";
    private final String archivoPantallaPrincipal = "../vistas/Principal.fxml";
    private final String archivoPortada = "../vistas/Portada.fxml";
    private final String archivoEstilosBase = "../vistas/css/estilosBase.css";
    private final String archivoTemaOscuro = "../vistas/css/temaOscuro.css";
    private final String archivoAcentoAzul = "../vistas/css/acentoAzul.css";
    private final String archivoAcentoVerde = "../vistas/css/acentoVerde.css";
    private final String archivoIcono = "../vistas/imagenes/icono.png";
    private Image icono;
    private Scene escena;
    private String estilosBase;
    private ArrayList<String> temas, acentos;
    private ObservableList<String> stylesheets;

    public EncargadoVistas(FabricaModelosVista fabricaModelosVista) {
        this.fabricaModelosVista = fabricaModelosVista;

        estilosBase = getClass().getResource(archivoEstilosBase).toExternalForm();

        temas = new ArrayList<>();
        String temaOscuro = getClass().getResource(archivoTemaOscuro).toExternalForm();
        temas.add(temaOscuro);

        acentos = new ArrayList<>();
        String acentoAzul = getClass().getResource(archivoAcentoAzul).toExternalForm();
        String acentoVerde = getClass().getResource(archivoAcentoVerde).toExternalForm();
        acentos.add(acentoAzul);
        acentos.add(acentoVerde);

        stylesheets = FXCollections.observableArrayList(estilosBase);
        stylesheets.addListener((ListChangeListener<String>) change -> {
            if (escena != null) {
                escena.getStylesheets().clear();
                escena.getStylesheets().addAll(change.getList());
            }
        });

        icono = new Image(getClass().getResourceAsStream(archivoIcono));
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
        Scene escena;
        if (vista.equals(archivoPantallaPrincipal)) {
            escena = new Scene(raiz, 600, 400);
            this.escena = escena;
            escena.getStylesheets().addAll(stylesheets);

            escenario.setMinWidth(400);
            escenario.setMinHeight(300);
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.getIcons().add(icono);
            escenario.show();

            PrincipalControlador controlador = loader.getController();
            controlador.init(fabricaModelosVista.getPrincipalModeloVista(), this);
            controlador.getEntradaController().init(fabricaModelosVista.getEntradaModeloVista());
            controlador.getSalidaController().init(fabricaModelosVista.getSalidaModeloVista());
            controlador.getGraficaController().init(fabricaModelosVista.getGraficaModeloVista());
        }

        else if (vista.equals(archivoPortada)) {
            escena = new Scene(raiz);
            escena.getStylesheets().addAll(stylesheets);

            escenario.setMinWidth(750);
            escenario.setMinHeight(445);
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.getIcons().add(icono);
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

    public void aplicarAcentoAzul() {
        quitarAcentos();
        stylesheets.add(acentos.get(0));
    }

    public void aplicarAcentoVerde() {
        quitarAcentos();
        stylesheets.add(acentos.get(1));
    }

    public void quitarAcentos() {
        for (int i = 0; i < stylesheets.size(); i++) {
            if (acentos.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }
}
