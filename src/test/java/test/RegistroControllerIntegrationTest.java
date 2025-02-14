package test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import controllers.LoginController;
import javafx.stage.Stage;
import utils.HibernateUtil;

@ExtendWith(ApplicationExtension.class)
public class RegistroControllerIntegrationTest {

	private LoginController controller;

	@Start
	public void start(Stage stage) throws Exception {
		controller = new LoginController();
		controller.cargarFXML(stage);
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		HibernateUtil.getSession();
	}


	@Test
	public void testInvalidRegistration(FxRobot robot) {

		// Primero, simula el clic en el link de registro
		robot.clickOn("#linkRegister");

		// Verifica si el campo de nombre es visible en la pantalla de registro
		verifyThat("#txtName", node -> node.isVisible());

		// Simula la entrada de datos incorrectos para el registro
		robot.clickOn("#txtName").write(""); // Nombre vacío
		robot.clickOn("#txtSurname").write("Pérez");
		robot.clickOn("#txtUsername").write("juanperez");
		robot.clickOn("#txtEmail").write("juan@ejemplo.com");
		robot.clickOn("#txtPassword").write("123"); // Contraseña demasiado corta
		robot.clickOn("#txtConfirmPassword").write("123");



		robot.clickOn("#btnRegister");

		// Espera hasta que se procesen los eventos (2 segundos)
		WaitForAsyncUtils.waitForFxEvents();
		WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);

		// Verifica que el diálogo de error sea visible, ya que la validación falló
		verifyThat(".dialog-pane", isVisible());
	}
}
