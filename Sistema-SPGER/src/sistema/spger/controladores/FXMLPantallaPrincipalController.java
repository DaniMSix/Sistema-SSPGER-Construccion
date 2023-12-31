package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Utilidades;

public class FXMLPantallaPrincipalController implements Initializable {

    @FXML
    private MenuItem mItemProfesor;

    @FXML
    private MenuItem mItemResponsableCA;

    @FXML
    private MenuItem mItemDirector;

    @FXML
    private MenuItem mItemAdministrador;

    @FXML
    private MenuBar menuBOpciones;

    POJUsuario usuarioLogueado = null;
    @FXML
    private AnchorPane anchoPnPrincipal;

    private POJUsuario usuarioActual;

    private POJRolRespuesta respuestaBD;
    @FXML
    private Button btnProyectoGuiado;
    @FXML
    private Button btnExperienciaRecepcional;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desactivarItems();
    }

    public void mostrarRoles(POJRolRespuesta respuestaBD) {

        ArrayList<POJRol> listaRoles = respuestaBD.getListaRoles();

        for (int indice = 0; indice < listaRoles.size(); indice++) {
            String opcionRol = listaRoles.get(indice).getDescripcion();
            switch (opcionRol) {
                case "Administrador":
                    mItemAdministrador.setVisible(true);
                    break;
                case "Profesor":
                    mItemProfesor.setVisible(true);
                    break;
                case "Director de trabajo":
                    mItemDirector.setVisible(true);
                    break;
                case "Responsable CA":
                    mItemResponsableCA.setVisible(true);
                    break;
                case "Estudiante":
                    btnProyectoGuiado.setVisible(true);
                    btnExperienciaRecepcional.setVisible(true);
            }
        }
    }

    public void prepararRolesUsuario(POJRolRespuesta respuestaBD, POJUsuario usuarioLogueado) {
        usuarioActual = usuarioLogueado;
        mostrarRoles(respuestaBD);
    }

    public void desactivarItems() {
        mItemAdministrador.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemProfesor.setVisible(false);
        mItemDirector.setVisible(false);
        mItemResponsableCA.setVisible(false);
    }

    @FXML
    private void clicMItemAdministrador(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaAdministrador.fxml"));
            Parent vista = loader.load();
            FXMLPantallaAdministradorController administradorControler = loader.getController();
            administradorControler.recibirInformaciónAdministrador(usuarioLogueado);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Administrador");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicMItemDirector(ActionEvent event) throws SQLException {
    try {
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaDirector.fxml"));
        Parent vista = loader.load();
        FXMLPantallaDirectorController pantallaDirector = loader.getController();
        pantallaDirector.prepararRolesUsuario(usuarioActual);
        Scene escena = new Scene(vista);
        Stage escenarioBase = new Stage();
        escenarioBase.initModality(Modality.APPLICATION_MODAL);
        escenarioBase.setScene(escena);
        escenarioBase.setTitle("SSPGER Director");
        escenarioBase.showAndWait();
    } catch (IOException ex) {
        Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, intentélo más tarde", Alert.AlertType.ERROR);
   }
}

    @FXML
    private void clicMItemResponsableCA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLResponsableCA.fxml"));
            Parent vista = loader.load();
            FXMLPantallaResponsableCAController pantallaResponsableCA = loader.getController();
            //pantallaResponsableCA.prepararRolesUsuario(idUsuarioActual);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Responsable CA");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicMItemProfesor(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaProfesor.fxml"));
            Parent vista = loader.load();
            FXMLPantallaProfesorController pantallaProfesor = loader.getController();
            pantallaProfesor.prepararRolesUsuario(usuarioActual.getIdUsuario());
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBotonSalir(ActionEvent event) {
        Stage escenarioActual = (Stage) menuBOpciones.getScene().getWindow();
        escenarioActual.close();
    }

    @FXML
    private void clicBotonProyectoGuiado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLCurso.fxml"));
            Parent vista = loader.load();
            FXMLCursoController pantallaProyectoGuiado = loader.getController();
            pantallaProyectoGuiado.inicializarInformacion(usuarioActual.getIdUsuario(), "Proyecto Guiado");
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Proyecto Guiado");
            
            if (pantallaProyectoGuiado.isInicioConExito()) {
                escenarioBase.showAndWait();
            }
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBotonExperienciaRecepcional(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLCurso.fxml"));
            Parent vista = loader.load();
            FXMLCursoController pantallaExperienciaRecepcional = loader.getController();
            pantallaExperienciaRecepcional.inicializarInformacion(usuarioActual.getIdUsuario(), "Experiencia Recepcional");
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Experiencia recepcional");
            
            if (pantallaExperienciaRecepcional.isInicioConExito()) {
                escenarioBase.showAndWait();
            }
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }
}

// <a href="https://storyset.com/online">Online illustrations by Storyset</a>
//mouse clic
//stage escenario
//secene es lo que esta adentro
//escena lleva al escenario
//Crear escenario
