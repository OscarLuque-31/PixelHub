package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import models.Usuario;

public class PerfilController implements Initializable{

    @FXML
    private VBox vboxContainer;

    @FXML
    private Label lblPerfil;

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

    @FXML
    private Label lblPreferencias;
    
    @FXML
    private ImageView imgEditar;
    
	private Usuario usuario;
	

	private void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	private Usuario getUsuario() {
		return this.usuario;
	}

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar imágenes
		initializeImagesBar();
		// Efectos de hover
		hoverEffect();
		
		setUsuario(BibliotecaController.getUsuario());
		
		initializeTextFields(getUsuario());
	}

	/**
	 * Método que recopila todos los hoverEffect
	 */
	private void hoverEffect() {
		// UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
		// UtilsViews.hoverEffectButton(btnBiblioteca, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnBuscarJuegos, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnCerrarSesion, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnRecomendaciones, "#415A6C", "#212E36");
	}

	/**
	 * Método que inicializa las imagenes
	 */
	private void initializeImagesBar() {
		imgEditar.setImage(new Image(getClass().getResourceAsStream("/images/Edit.png")));
	}

	/**
	 * Método que rellena los textField con los datos del usuario
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
	
	

}
