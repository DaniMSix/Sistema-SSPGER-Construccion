package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOCurso;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJCurso;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLCursoController implements Initializable {

    private int idEstudiante;
    private int idCurso;
    @FXML
    private Button btnConsultarActividades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private boolean inicioConExito;

    public boolean isInicioConExito() {
        return inicioConExito;
    }

    public void inicializarInformacion(int idEstudianteLogueado, String nombreCurso) {
        idEstudiante = idEstudianteLogueado;
        inicioConExito = false; 
        try {
            POJCurso respuestaBDObtenerIdCurso = DAOCurso.obtenerIdCurso(nombreCurso);
            if (respuestaBDObtenerIdCurso != null) {
                switch (respuestaBDObtenerIdCurso.getCodigoRespuesta()) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                        idCurso = respuestaBDObtenerIdCurso.getIdCurso();
                        inicioConExito = true;
                        break;
                }
            }
        } catch (SQLException e) {
            Utilidades.mostrarDialogoSimple("Error", "Error, inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBotonConsultarActividades(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLConsultarActividadProgramada.fxml"));
            Parent vista = loader.load();
            FXMLConsultarActividadProgramadaController pantallaActividadProgramadaController = loader.getController();
            pantallaActividadProgramadaController.inicializarInformacion(idEstudiante, idCurso);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Proyecto Guiado");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBotonConsultarEntregas(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLConsultarEntregas.fxml"));
            Parent vista = loader.load();
            FXMLConsultarEntregasController pantallaConsultarEntregas = loader.getController();
            pantallaConsultarEntregas.inicializarInformacion(idEstudiante, idCurso);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.setTitle("SSPGER Proyecto Guiado");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }

}
