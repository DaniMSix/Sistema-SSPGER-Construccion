package sistema.spger.controladores;

import java.net.URL;
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
    
    
    POJActividad actividadInformacion = new  POJActividad();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaEntrega.setDayCellFactory(getDayCellFactory());
    }    
    
    public void inicializarInformacionFormulario(String tipoClicBoton, POJActividad actividadEdicion, int idEstudiante, int idCurso){

        actividadInformacion =  actividadEdicion;
        tipoBoton = tipoClicBoton;
              
       switch(tipoClicBoton){
            case "Ver detalle":
                lbTitulo.setText("Detalle actividad");
                cargarInformacionDetalle(actividadEdicion);
                break;
            case "Modificar":
                lbTitulo.setText("Modificar actividad");
                asignarInformacionCampos(actividadInformacion);
                dpFechaCreacion.setEditable(false);
                break;
            case "Registrar":
                dpFechaCreacion.setEditable(false);
                dpFechaCreacion.setValue(LocalDate.now());
                this.idEstudiante = idEstudiante;
                this.idCurso = idCurso;
                break;
       }
    }
    
    private void cargarInformacionDetalle(POJActividad informacionActividad){
        asignarInformacionCampos(informacionActividad);
        desactivarCampos();
        btnGuardar.setDisable(true);
        btnCancelar.setDisable(true);
    }
    
    public void desactivarCampos(){
        txfNombre.setEditable(false);
        txAreaDescripcion.setEditable(false);
        dpFechaCreacion.setEditable(false);
        dpFechaEntrega.setEditable(false);
    }

    public void asignarInformacionCampos(POJActividad informacionActividad){
        txfNombre.setText(informacionActividad.getNombre());
        txAreaDescripcion.setText(informacionActividad.getDescripcion());
        dpFechaEntrega.setValue(LocalDate.parse(informacionActividad.getFechaLimiteEntrega()));
        dpFechaCreacion.setValue(LocalDate.parse(informacionActividad.getFechaCreacion()));
        
    }
    
    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        if(tipoBoton.equals("Modificar")){
            POJActividad actividadAModificar = obtenerInformacionIngresada();
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
                    Utilidades.mostrarDialogoSimple("Información modificada correctamente", "El usuario se ha modificado correctamente",
                            Alert.AlertType.INFORMATION);
            }
            
        } 
        
        if(tipoBoton.equals("Registrar")){
            
            POJActividad actividadARegistrar = obtenerInformacionIngresada();
            actividadARegistrar.setEstado("Sin entregar");
                
            // REGISTRAR ACTIVIDAD
            int respuestaBD = DAOActividad.registrarActividad(actividadARegistrar);
            
            // OBTENER ID DE LA ACTIVIDAD
            POJActividad actividadId = DAOActividad.obtenerIdActividad(actividadARegistrar);
            
            // REGISTRAR CURSO_ACTIVIDAD
            int respuestaRegistroCursoActividad = DAOActividad.registrarCursoActividad(idCurso, actividadId.getIdActividad());
            
            // REGISTRAR USUARIO_ACTIVIDAD
            int respuestaRegistroUsuarioActividad = DAOActividad.registrarUsuarioActividad(idEstudiante, actividadId.getIdActividad());
            
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
                    Utilidades.mostrarDialogoSimple("Información registrada correctamente", "La actividad se ha registrado correctamente",
                            Alert.AlertType.INFORMATION);
        }
        }
    }
    
    
        
    public POJActividad obtenerInformacionIngresada(){
        POJActividad actividadInformacionIngresada = new POJActividad();
        String nombre = txfNombre.getText();
        String descripcion = txAreaDescripcion.getText();
        LocalDate fechaNacimiento = dpFechaEntrega.getValue();
        actividadInformacionIngresada.setNombre(nombre);
        actividadInformacionIngresada.setDescripcion(descripcion);
        actividadInformacionIngresada.setFechaLimiteEntrega(fechaNacimiento.toString());
        actividadInformacionIngresada.setFechaCreacion(LocalDate.now().toString());
        return actividadInformacionIngresada;
    }
    
    public boolean validarInformacion(POJActividad actividadInformacionIngresada) {
        
        boolean datosValidos = true;
        
        if(actividadInformacionIngresada.getFechaLimiteEntrega() == null){
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Fecha incorrecta", "Por favor seleccione una"
                    + " fecha", Alert.AlertType.ERROR);
        }
        
        if ((actividadInformacionIngresada.getNombre().isEmpty()) || (actividadInformacionIngresada.getDescripcion().isEmpty())) {
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                    + "con la información necesaria", Alert.AlertType.ERROR);
        }
        
        if(datosValidos){
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

                if(date.isBefore(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
}
