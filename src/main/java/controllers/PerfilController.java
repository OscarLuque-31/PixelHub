package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.PreferenciasDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.Preferencias;
import models.Usuario;
import utils.HibernateUtil;

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
    private TextField txtFContrasena;

    // Cambia el tipo a ListView<String>
    @FXML
    private ListView<String> listViewGenero;

    // Cambia el tipo a ListView<String>
    @FXML
    private ListView<String> listViewPlataforma;

    @FXML
    private Label lblPreferencias;

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
        hoverEffect();

        // Asume que BibliotecaController.getUsuario() retorna el usuario actual.
        setUsuario(BibliotecaController.getUsuario());

        // Rellena los TextField con los datos del usuario
        initializeTextFields(getUsuario());
        // Rellena los ListView con las preferencias del usuario
        initializeListViews(getUsuario());
    }

    /**
     * Método para aplicar efectos hover a los controles (si es necesario).
     */
    private void hoverEffect() {
        // Implementa aquí tus efectos hover, si los necesitas.
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
