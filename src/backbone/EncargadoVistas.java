package backbone;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vistas.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Se encarga de inicializar las vistas y sus respectivos controladores.
 * También está a cargo de aplciar estilos css.
 */
public class EncargadoVistas {
    private final FabricaModelosVista fabricaModelosVista;
    private final String titulo = "Codificación Digital";

    private final String archivoPantallaPrincipal = "../vistas/fxml/Principal.fxml";
    private final String archivoPortada = "../vistas/fxml/Portada.fxml";
    private final String archivoTutorial = "../vistas/fxml/Tutorial.fxml";
    private final String archivoAcercaDe = "../vistas/fxml/AcercaDe.fxml";
    private final String archivoIntro  = "../vistas/fxml/Intro.fxml";

    private final String archivoEstilosBase = "../vistas/css/estilosBase.css";
    private final String archivoTemaOscuro = "../vistas/css/temaOscuro.css";
    private final String archivoAcentoAzul = "../vistas/css/acentoAzul.css";
    private final String archivoAcentoVerde = "../vistas/css/acentoVerde.css";
    private String archivoLetraCss;

    private final String archivoIcono = "../vistas/imagenes/icono.png";

    private Image icono;
    private Scene escena;
    private String estilosBase;
    private ArrayList<String> temas, acentos;
    private ObservableList<String> stylesheets;
    private String letraCss;
    private int fontSize = 9;

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

        letraCss = ".root { -fx-font-size: " + fontSize + "pt; }";
        archivoLetraCss = "data:text/css;base64," +
                Base64.getEncoder().encodeToString(letraCss.getBytes(StandardCharsets.UTF_8));
        stylesheets = FXCollections.observableArrayList(estilosBase, archivoLetraCss);
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
    public void abrirAcercaDe() throws Exception {
        abrirVista(archivoAcercaDe);
    }

    public void abrirIntro() throws Exception {
        abrirVista(archivoIntro);
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
                escenario.setMinWidth(180);
                escenario.setMinHeight(100);
                escenario.show();
                TutorialControlador controlador = loader.getController();
                controlador.init(this);
            }
            case archivoAcercaDe -> {
                escenario.setMinWidth(400);
                escenario.setMinHeight(300);
                escenario.show();
                AcercaDeControlador controlador = loader.getController();
                controlador.init(this);
            }
            case archivoIntro -> {
                escenario.setMinWidth(180);
                escenario.setMinHeight(100);
                escenario.show();
                IntroControlador controlador = loader.getController();
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

    public void agrandarLetra() {
        fontSize++;
        if (fontSize < 25) {
            letraCss = ".root { -fx-font-size: " + fontSize + "pt; }";
            stylesheets.remove(archivoLetraCss);
            archivoLetraCss = "data:text/css;base64," +
                    Base64.getEncoder().encodeToString(letraCss.getBytes(StandardCharsets.UTF_8));
            stylesheets.add(archivoLetraCss);
        }
        else {
            fontSize--;
        }
    }

    public void menguarLetra() {
        fontSize--;
        if (fontSize > 0) {
            letraCss = ".root { -fx-font-size: " + --fontSize + "pt; }";
            stylesheets.remove(archivoLetraCss);
            archivoLetraCss = "data:text/css;base64," +
                    Base64.getEncoder().encodeToString(letraCss.getBytes(StandardCharsets.UTF_8));
            stylesheets.add(archivoLetraCss);
        }
        else {
            fontSize++;
        }
    }
}
