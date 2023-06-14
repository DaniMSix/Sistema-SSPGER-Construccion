package sistema.spger.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLFormularioActividadController implements Initializable {

    @FXML
    private TextField txfNombre;
    @FXML
    private TextArea txAreaDescripcion;
    @FXML
    private DatePicker dpFechaEntrega;
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dpFechaCreacion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    private String tipoBoton;
    private int idEstudiante;
    private int idCurso;
    private boolean inicioConExito;
    String EXPRESION_CARACTERES_ESPECIALES = "[`~!@#$%^&*+=|{}°':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}‘；：”“’。，、]";

    POJActividad actividadInformacion = new POJActividad();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaEntrega.setDayCellFactory(getDayCellFactory());
        txAreaDescripcion.setWrapText(true);
    }

    public boolean isInicioConExito() {
        return inicioConExito;
    }

    public void inicializarInformacionFormulario(String tipoClicBoton, POJActividad actividadEdicion, int idEstudiante, int idCurso) throws SQLException {
    inicioConExito = false;
    actividadInformacion = actividadEdicion;
    tipoBoton = tipoClicBoton;
    switch (tipoClicBoton) {
        case "Modificar":
            lbTitulo.setText("Modificar actividad");
            asignarInformacionCampos(actividadInformacion);
            dpFechaCreacion.setEditable(false);
            break;
        case "Registrar":
            lbTitulo.setText("Registrar actividad");
            dpFechaCreacion.setEditable(false);
            dpFechaCreacion.setValue(LocalDate.now());
            this.idEstudiante = idEstudiante;
            this.idCurso = idCurso;
            break;
    }
    inicioConExito = true;
}


    public void desactivarCampos() {
        txfNombre.setEditable(false);
        txAreaDescripcion.setEditable(false);
        dpFechaCreacion.setEditable(false);
        dpFechaEntrega.setEditable(false);
    }

    public void asignarInformacionCampos(POJActividad informacionActividad) {
        txfNombre.setText(informacionActividad.getNombre());
        txAreaDescripcion.setText(informacionActividad.getDescripcion());
        dpFechaEntrega.setValue(LocalDate.parse(informacionActividad.getFechaLimiteEntrega()));
        dpFechaCreacion.setValue(LocalDate.parse(informacionActividad.getFechaCreacion()));
    }

    @FXML
    private void clicBotonGuardar(ActionEvent event) throws SQLException {
        if (tipoBoton.equals("Modificar")) {
            POJActividad actividadAModificar = obtenerInformacionIngresada();

            if (actividadAModificar != null) {
                if ((validarInformacion(actividadAModificar))) {
                    actividadAModificar.setIdActividad(actividadInformacion.getIdActividad());

                    int respuestaBD = DAOActividad.modificarActividad(actividadAModificar);
                    switch (respuestaBD) {
                        case Constantes.ERROR_CONEXION:
                            Utilidades.mostrarDialogoSimple("Error de conexión",
                                    "Por el momento no hay conexión, intentelo más tarde",
                                    Alert.AlertType.ERROR);
                            break;
                        case Constantes.ERROR_CONSULTA:
                            Utilidades.mostrarDialogoSimple("Error en la solicitud",
                                    "Por el momento no se puede procesar la solicitud",
                                    Alert.AlertType.ERROR);
                            break;
                        case Constantes.OPERACION_EXITOSA:
                            Utilidades.mostrarDialogoSimple("Información modificada correctamente", "La actividad se ha modificado correctamente",
                                    Alert.AlertType.INFORMATION);
                            Stage escenarioActual = (Stage) lbTitulo.getScene().getWindow();
                            escenarioActual.close();
                    }
                }
            }
        }

        if (tipoBoton.equals("Registrar")) {

            POJActividad actividadARegistrar = obtenerInformacionIngresada();
            if (actividadARegistrar != null) {
                if ((validarInformacion(actividadARegistrar))) {
                    actividadARegistrar.setEstado("Sin entregar");
                    int respuestaBD = DAOActividad.registrarActividad(actividadARegistrar);
                    if ((comprobarCodigoRespuesta(respuestaBD))) {
                        POJActividad actividadId = DAOActividad.obtenerIdActividad(actividadARegistrar);
                        if ((comprobarCodigoRespuesta(actividadId.getCodigoRespuesta()))) {
                            int respuestaRegistroCursoActividad = DAOActividad.registrarCursoActividad(idCurso, actividadId.getIdActividad());
                            if ((comprobarCodigoRespuesta(respuestaRegistroCursoActividad))) {
                                int respuestaRegistroUsuarioActividad = DAOActividad.registrarUsuarioActividad(idEstudiante, actividadId.getIdActividad());
                                if ((comprobarCodigoRespuesta(respuestaRegistroUsuarioActividad))) {
                                    Utilidades.mostrarDialogoSimple("Información registrada correctamente", "La actividad se ha registrado correctamente",
                                            Alert.AlertType.INFORMATION);
                                    Stage escenarioActual = (Stage) lbTitulo.getScene().getWindow();
                                    escenarioActual.close();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean comprobarCodigoRespuesta(int codigoRespuesta) {
        boolean esExitosa = false;
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión",
                        "Por el momento no hay conexión, intentelo más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud",
                        "Por el momento no se puede procesar la solicitud",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                esExitosa = true;
        }
        return esExitosa;
    }

    public POJActividad obtenerInformacionIngresada() {
        POJActividad actividadInformacionIngresada = new POJActividad();
        String nombre = txfNombre.getText();
        String descripcion = txAreaDescripcion.getText();
        LocalDate fechaEntrega = dpFechaEntrega.getValue();
        actividadInformacionIngresada.setNombre(nombre);
        actividadInformacionIngresada.setDescripcion(descripcion);
        actividadInformacionIngresada.setFechaCreacion(dpFechaCreacion.getValue().toString());

        if (dpFechaEntrega.getValue() != null) {
            actividadInformacionIngresada.setFechaLimiteEntrega(fechaEntrega.toString());
        } else {
            Utilidades.mostrarDialogoSimple("Fecha incorrecta", "Por favor seleccione una"
                    + " fecha", Alert.AlertType.ERROR);
            actividadInformacionIngresada = null;
        }
        return actividadInformacionIngresada;
    }

    public boolean validarInformacion(POJActividad actividadInformacionIngresada) {

        boolean datosValidos = true;

        if ((actividadInformacionIngresada.getNombre().isEmpty()) || (actividadInformacionIngresada.getDescripcion().isEmpty())) {
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

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }

    private void cerrarVentana() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }

    @FXML
    private void validarCaracteresCampoNombre(KeyEvent event) {
        if (txfNombre.getText().length() >= 135) {
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
    private void validarCaracteresCampoDescripcion(KeyEvent event) {
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

}
