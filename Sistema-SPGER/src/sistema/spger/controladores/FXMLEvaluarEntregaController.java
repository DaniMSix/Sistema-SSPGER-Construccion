package sistema.spger.controladores;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOActividadEntrega;
import sistema.spger.DAO.DAOEntrega;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJArchivos;
import sistema.spger.modelo.POJO.POJEntrega;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLEvaluarEntregaController implements Initializable {

    private int idActividad;
    @FXML
    private TextArea txAreaComentariosEstudiante;
    @FXML
    private ListView<String> listVArchivosEntrega;
    @FXML
    private TextArea txAreaComentariosProfesor;
    @FXML
    private TextField txfCalificacion;
    @FXML
    private Label lbNombreActividad;
    @FXML
    private TextArea txAreaDescripcion;
    String EXPRESION_CARACTERES_ESPECIALES = "[`~!@#$%^&*+=|{}°':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}‘；：”“’。，、]";
    String EXPRESION_NUMEROS_DECIMALES = "\\\\d+(\\\\.\\\\d+)?";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarInformacionFormulario(int idActividad) throws SQLException {
        this.idActividad = idActividad;
        asignarInformacionCampos();

    }

    public void asignarInformacionCampos() throws SQLException {
        POJActividadEntrega informacionActividadEntrega = DAOActividadEntrega.obtenerActividadEntregaPorIdActividad(idActividad);
        txAreaDescripcion.setText(informacionActividadEntrega.getDescripcion());
        lbNombreActividad.setText(informacionActividadEntrega.getNombre());
        txAreaComentariosEstudiante.setText(informacionActividadEntrega.getComentariosAlumno());
        txAreaComentariosProfesor.setText(informacionActividadEntrega.getObservacionProfesor());
        txfCalificacion.setText(Float.toString(informacionActividadEntrega.getCalificacion()));
        List<POJArchivos> archivosEntrega = DAOEntrega.obtenerArchivosEntregaPorId(informacionActividadEntrega.getIdEntrega()).getArchivosEntrega();
        if (!archivosEntrega.isEmpty()) {
            for (POJArchivos archivo : archivosEntrega) {
                File archivoEntrega = archivo.getArchivosEntrega();
                listVArchivosEntrega.getItems().add(archivoEntrega.getName());
            }
        }

    }

    @FXML
    private void clicBotonGuardar(ActionEvent event) throws SQLException {
        POJEntrega informacionEntrega = obtenerInformacionIngresada();

        if (informacionEntrega != null) {
            if (validarInformacion(informacionEntrega)) {
                int respuestaRegistrarEntrega = DAOEntrega.registrarCalificacion(informacionEntrega);
                switch (respuestaRegistrarEntrega) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión",
                                "Por el momento no hay conexión, inténtelo más tarde",
                                Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la solicitud",
                                "Por el momento no se puede procesar la solicitud",
                                Alert.AlertType.ERROR);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Información modificada correctamente",
                                "La calificación se ha registrado correctamente",
                                Alert.AlertType.INFORMATION);
                        break;
                }
            }
        }
    }

    public POJEntrega obtenerInformacionIngresada() {
        POJEntrega informacionEntrega = new POJEntrega();
        informacionEntrega.setObservacionProfesor(txAreaComentariosProfesor.getText());
        String calificacion = txfCalificacion.getText();

        if (!calificacion.isEmpty()) {
            try {
                float calificacionCasting = Float.parseFloat(calificacion);
                informacionEntrega.setCalificacion(calificacionCasting);
            } catch (NumberFormatException e) {
                Utilidades.mostrarDialogoSimple("Valor inválido", "La calificación debe ser un número válido",
                        Alert.AlertType.ERROR);
                return null;
            }
        } else {
            Utilidades.mostrarDialogoSimple("Sin calificación", "Por favor asigne una calificación al alumno",
                    Alert.AlertType.ERROR);
            return null;
        }

        informacionEntrega.setActividad_idActividad(idActividad);
        return informacionEntrega;
    }

    public boolean validarInformacion(POJEntrega entregaInformacionIngresada) {

        boolean datosValidos = true;

        if ((entregaInformacionIngresada.getObservacionProfesor().isEmpty())) {
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                    + "con la información necesaria", Alert.AlertType.ERROR);
        }

        if (datosValidos) {
            datosValidos = true;
        }

        return datosValidos;
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage escenarioBase = (Stage) lbNombreActividad.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void validarCampoComentariosProfesor(KeyEvent event) {
        if (txAreaDescripcion.getText().length() >= 600) {
            event.consume();
        }
        String textoIngresado = event.getCharacter();
        if (textoIngresado.matches(EXPRESION_CARACTERES_ESPECIALES)) {
            Utilidades.mostrarDialogoSimple("Caracteres no permitidos",
                    "Pof favor no ingrese números ni caracteres especiales "
                    + "en este campo", Alert.AlertType.WARNING);
            event.consume();
        }
    }

    @FXML
    private void validarCampoCalificacion(KeyEvent event) {
        if (txfCalificacion.getText().length() >= 4) {
            event.consume();
        }
        String textoIngresado = event.getCharacter();
        if (textoIngresado.matches(EXPRESION_NUMEROS_DECIMALES)) {
            Utilidades.mostrarDialogoSimple("Caracteres no permitidos",
                    "Pof favor no ingrese números ni caracteres especiales "
                    + "en este campo", Alert.AlertType.WARNING);
            event.consume();
        }
    }
}
