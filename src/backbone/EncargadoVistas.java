package backbone;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vistas.AcercaDeControlador;
import vistas.PortadaControlador;
import vistas.PrincipalControlador;
import vistas.TutorialControlador;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Se encarga de inicializar las vistas y sus respectivos controladores.
 * También está a cargo de aplciar estilos css.
 */
public class EncargadoVistas {
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital";

    private final String archivoPantallaPrincipal = "../vistas/Principal.fxml";
    private final String archivoPortada = "../vistas/Portada.fxml";
    private final String archivoTutorial = "../vistas/Tutorial.fxml";
    private final String archivoAcercaDe = "../vistas/AcercaDe.fxml";

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

    /**
     * Inicializa los estilos y los añade a sus respectivas listas, así como la escena y
     * el ícono de la aplicación.
     * @param fabricaModelosVista
     */
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

    /**
     * Llama abrirVista con el archivo de la pantalla principal.
     * @throws Exception
     */
    public void abrirProgramaPrincipal() throws Exception {
        abrirVista(archivoPantallaPrincipal);
    }

    /**
     * Llama abrirVista con el archivo de la portada.
     * @throws Exception
     */
    public void abrirPortada() throws Exception {
        abrirVista(archivoPortada);
    }

    /**
     * Llama abrirVista con el archivo del tutorial.
     * @throws Exception
     */
    public void abrirTutorial() throws Exception {
        abrirVista(archivoTutorial);
    }

    /**
     * Llama abrirVista con el archivo del acerca de.
     * @throws Exception
     */
    public void abrirAcercaDe() throws  Exception {
        abrirVista(archivoAcercaDe);
    }

    /**
     * Inicializa y muestra el archivo fxml que le es dado.
     * Inicializa los controladores correspondienes.
     * @param vista
     * @throws IOException
     */
    private void abrirVista(String vista) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
        Parent raiz = loader.load();
        Stage escenario = new Stage();
        Scene escena = new Scene(raiz);
        escena.getStylesheets().addAll(stylesheets);
        escenario.setScene(escena);
        escenario.setTitle(titulo);
        escenario.getIcons().add(icono);

        switch (vista) {
            case archivoPantallaPrincipal -> {
                this.escena = escena;

                escenario.setWidth(600);
                escenario.setHeight(400);
                escenario.setMinWidth(400);
                escenario.setMinHeight(300);
                escenario.show();

                PrincipalControlador controlador = loader.getController();
                controlador.init(fabricaModelosVista.getPrincipalModeloVista(), this);
                controlador.getEntradaController().init(fabricaModelosVista.getEntradaModeloVista());
                controlador.getSalidaController().init(fabricaModelosVista.getSalidaModeloVista());
                controlador.getGraficaController().init(fabricaModelosVista.getGraficaModeloVista());
            }
            case archivoPortada -> {
                escenario.setMinWidth(750);
                escenario.setMinHeight(445);
                escenario.show();

                PortadaControlador controlador = loader.getController();
                controlador.init(this);
            }
            case archivoTutorial -> {
                escenario.show();
                TutorialControlador controlador = loader.getController();
                controlador.init(this);
            }
            case archivoAcercaDe -> {
                escenario.show();
                AcercaDeControlador controlador = loader.getController();
                controlador.init(this);
            }
        }
    }

    /**
     * Añade el css del tema oscuro a la lista de estilos.
     */
    public void aplicarTemaOscuro() {
        quitarTemas();
        stylesheets.add(temas.get(0));
    }

    /**
     * Quita los css destinados para temas de la lista de estilos.
     */
    public void quitarTemas() {
        for (int i = 0; i < stylesheets.size(); i++) {
            if (temas.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }

    /**
     * Añade el css del acento azul a la lista de estilos.
     */
    public void aplicarAcentoAzul() {
        quitarAcentos();
        stylesheets.add(acentos.get(0));
    }

    /**
     * Añade el css del acento verde a la lista de estilos.
     */
    public void aplicarAcentoVerde() {
        quitarAcentos();
        stylesheets.add(acentos.get(1));
    }

    /**
     * Quita los css destinados para los acentos de la lista de estilos.
     */
    public void quitarAcentos() {
        for (int i = 0; i < stylesheets.size(); i++) {
            if (acentos.contains(stylesheets.get(i))) {
                stylesheets.remove(stylesheets.get(i));
                break;
            }
        }
    }
}
