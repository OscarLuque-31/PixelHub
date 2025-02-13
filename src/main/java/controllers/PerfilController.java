package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.JuegosBibliotecaDaoImpl;
import dao.PreferenciasDaoImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.Preferencias;
import models.Usuario;
import utils.DateUtils;
import utils.HibernateUtil;
import java.sql.Date;


public class PerfilController implements Initializable {


    @FXML
    private VBox vboxContainer;

    @FXML
    private Label lblPerfil;

    @FXML
    private ImageView imgEditar;

    @FXML
    private TextField txtFNombreUsuario;

    @FXML
    private TextField txtFNombre;

    @FXML
    private TextField txtFFechaNacimiento;

    @FXML
    private TextField txtFApellidos;

    @FXML
    private TextField txtFCorreoElectronico;

    @FXML
    private PasswordField txtFContrasena;

    @FXML
    private ListView<String> listViewGenero;

    @FXML
    private ListView<String> listViewPlataforma;

    @FXML
    private Label lblPreferencias;

    @FXML
    private Label juegosAnadidos;

    @FXML
    private Label creacionFecha;
    
    private Usuario usuario;

    private void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private Usuario getUsuario() {
        return this.usuario;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vboxContainer.getStylesheets().add(getClass().getResource("/styles/stylePerfil.css").toExternalForm());

        // Inicializar imágenes y efectos hover (si aplica)
        initializeImagesBar();

        // Asume que BibliotecaController.getUsuario() retorna el usuario actual.
        setUsuario(BibliotecaController.getUsuario());

        // Rellena los TextField con los datos del usuario
        initializeTextFields(getUsuario());
        // Rellena los ListView con las preferencias del usuario
        initializeListViews(getUsuario());
        desactivarBarritasListView();
        

        // Actualizar el Label de juegos añadidos
        actualizarJuegosAnadidos();
        // Formatear y mostrar la fecha de creación
        actualizarFechaCreacion();
    }

    private void desactivarBarritasListView() {
        Platform.runLater(() -> {
            // Obtener las barras de desplazamiento de los ListView
            ScrollBar scrollBarVerticalGenero = (ScrollBar) listViewGenero.lookup(".scroll-bar:vertical");
            ScrollBar scrollBarVerticalPlataforma = (ScrollBar) listViewPlataforma.lookup(".scroll-bar:vertical");

            // Asegurarse de que los ScrollBars fueron encontrados
            if (scrollBarVerticalGenero != null) {
                scrollBarVerticalGenero.setVisible(false); // Ocultar la barra vertical de género
            }

            if (scrollBarVerticalPlataforma != null) {
                scrollBarVerticalPlataforma.setVisible(false); // Ocultar la barra vertical de plataforma
            }
        });
    }
    
    /**
     * Actualiza el Label de juegos añadidos con el número de juegos del usuario.
     */
    private void actualizarJuegosAnadidos() {
        JuegosBibliotecaDaoImpl juegosDao = new JuegosBibliotecaDaoImpl(HibernateUtil.getSession());
        int numJuegos = juegosDao.contarJuegosPorUsuario(usuario.getId());
        juegosAnadidos.setText("Juegos añadidos: " + numJuegos);
    }

    /**
     * Formatea y muestra la fecha de creación del usuario.
     */
    private void actualizarFechaCreacion() {
        // Suponiendo que la fecha de creación es un LocalDate
        LocalDate fechaCreacionLocal = usuario.getFecha_creacion(); // Aquí asumo que usuario.getFecha_creacion() devuelve un LocalDate

        // Convertir LocalDate a Date (java.util.Date)
        Date fechaCreacion = Date.valueOf(fechaCreacionLocal);

        // Formatear la fecha utilizando tu método de formateo
        String fechaFormateada = DateUtils.formatearFecha(fechaCreacion);
        creacionFecha.setText("Fecha de creación: " + fechaFormateada);
    }

    /**
     * Método que inicializa la imagen del botón de edición.
     */
    private void initializeImagesBar() {
        imgEditar.setImage(new Image(getClass().getResourceAsStream("/images/Edit.png")));
    }

    /**
     * Método que rellena los TextField con los datos del usuario.
     */
    private void initializeTextFields(Usuario usuario) {
        txtFApellidos.setText(usuario.getApellidos());
        txtFApellidos.setEditable(false);
        txtFContrasena.setText(usuario.getPassword());
        txtFContrasena.setEditable(false);
        txtFFechaNacimiento.setText(usuario.getFecha_nacimiento().toString());
        txtFFechaNacimiento.setEditable(false);
        txtFCorreoElectronico.setText(usuario.getEmail());
        txtFCorreoElectronico.setEditable(false);
        txtFNombre.setText(usuario.getNombre());
        txtFNombre.setEditable(false);
        txtFNombreUsuario.setText(usuario.getUsername());
        txtFNombreUsuario.setEditable(false);
    }

    /**
     * Método que rellena los ListView con las preferencias del usuario.
     * Se asume que en la tabla Preferencias:
     * - Si el campo tipo_preferencia (mapeado en el modelo como 'tipo') es 1, se trata de un género.
     * - Si es 2, se trata de una plataforma.
     */
    private void initializeListViews(Usuario usuario) {
        // Crear la instancia del DAO utilizando la sesión de Hibernate.
        PreferenciasDaoImpl preferenciasDao = new PreferenciasDaoImpl(HibernateUtil.getSession());
        
        // Obtener la lista de preferencias del usuario.
        List<Preferencias> preferencias = preferenciasDao.getPreferenciasByUsuario(usuario.getId());
        
        // Listas para almacenar las preferencias de género y plataforma.
        List<String> generos = new ArrayList<>();
        List<String> plataformas = new ArrayList<>();
        
        // Recorrer las preferencias y separarlas según el tipo.
        for (Preferencias pref : preferencias) {
            if (pref.getTipo() == 1) {
                generos.add(pref.getPreferencia());
            } else if (pref.getTipo() == 2) {
                plataformas.add(pref.getPreferencia());
            }
        }
        
        // Convertir las listas en ObservableList para asignarlas a los ListView.
        ObservableList<String> observableGeneros = FXCollections.observableArrayList(generos);
        ObservableList<String> observablePlataformas = FXCollections.observableArrayList(plataformas);
        
        // Asignar las listas a los ListView.
        listViewGenero.setItems(observableGeneros);
        listViewPlataforma.setItems(observablePlataformas);
        
    }
}
