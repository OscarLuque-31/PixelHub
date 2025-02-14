package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.HibernateUtil;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerIntegrationTest {

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
    public void testValidLogin(FxRobot robot) {
        // Simula la entrada correcta
        robot.clickOn("#txtUsername").write("usuarioCorrecto");
        robot.clickOn("#txtPassword").write("claveCorrecta123");
        robot.clickOn("#btnLogin");

        // Espera hasta que se procesen los eventos (2 segundos)
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);

        // Verifica que el diálogo de error no esté visible
        assertFalse(robot.lookup(".dialog-pane").queryAll().stream().anyMatch(Node::isVisible));
    }

    void testInvalidLogin(FxRobot robot) {
        // Simula la entrada incorrecta
        robot.clickOn("#txtUsername").write("usuarioIncorrecto");
        robot.clickOn("#txtPassword").write("claveIncorrecta");
        robot.clickOn("#btnLogin");

        // Espera hasta 2 segundos a que aparezca el mensaje
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);

        // Verifica si el diálogo existe y es visible
        verifyThat(".dialog-pane", isVisible());
    }

}
