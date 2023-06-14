package sistema.spger.controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOEntrega;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJArchivos;
import sistema.spger.modelo.POJO.POJArchivosRespuesta;
import sistema.spger.modelo.POJO.POJEntrega;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLFormularioEntregaController implements Initializable {

    @FXML
    private Label lbNombreActividad;
    @FXML
    private ListView<String> listViewArchivos;
    @FXML
    private Label lbTamanioMaximo;
    POJActividadEntrega actividadInformacion = new  POJActividadEntrega();
    String tipoBoton;
    POJArchivosRespuesta archivosAGuardar = new POJArchivosRespuesta();

    public static final long TAMANIO_MAXIMO = 110 * 110;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextArea txAreaDescripcion;
    private int idActividad;
    @FXML
    private TextArea txAreaComentariosAlumno;
    int idEntrega;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarInformacionFormulario(String tipoClicBoton, POJActividadEntrega actividadEdicion){
       
       // this.interfazNotificacion = interfazNotificacion;
        actividadInformacion =  actividadEdicion;
        idActividad = actividadInformacion.getIdActividad();
        tipoBoton = tipoClicBoton;
       
       switch(tipoClicBoton){
            case "Modificar":
                lbTitulo.setText("Modificar actividad");
                idEntrega = actividadInformacion.getIdEntrega();
                lbNombreActividad.setText(actividadInformacion.getNombre());
                txAreaDescripcion.setText(actividadInformacion.getDescripcion());
                txAreaDescripcion.setEditable(false);
                asignarCamposInformacion(idEntrega);
                break;
            case "Registrar":
                lbTitulo.setText("Entregar actividad");
                lbNombreActividad.setText(actividadInformacion.getNombre());
                txAreaDescripcion.setText(actividadInformacion.getDescripcion());
                txAreaDescripcion.setEditable(false);
                break;
       }
    }


    @FXML
    private void clicBotonAgregar(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        archivosAGuardar.setArchivosEntrega(new ArrayList<>());

        Stage escenarioBase = (Stage) lbNombreActividad.getScene().getWindow();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(escenarioBase);
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                POJArchivos archivosASubir = new POJArchivos();
                long tamanioArchivo = file.length();
                if (tamanioArchivo > 2147483647) {
                    lbTamanioMaximo.setText("El archivo supera el tamaño permitido (214748 bytes permitidos).");
                    break;
                }
                archivosASubir.setArchivosEntrega(file);
                archivosAGuardar.getArchivosEntrega().add(archivosASubir);
                listViewArchivos.getItems().add(file.getName());
            }

            if (selectedFiles.size() > TAMANIO_MAXIMO) { 
                lbTamanioMaximo.setText("Ha sobrepasado el tamaño permitido");
            } else {
            }
        }
    }

    private POJArchivosRespuesta asignarIdEntregaAArchivos(int entregaId) {
        List<POJArchivos> archivos = archivosAGuardar.getArchivosEntrega();
        for (POJArchivos archivo : archivos) {
            archivo.setEntrega_idEntrega(entregaId);
        }
        return archivosAGuardar;
    }

    public POJEntrega obtenerInformacionIngresada(){
        POJEntrega informacionEntrega= new POJEntrega();
        String comentariosAlumno = txAreaComentariosAlumno.getText();
        informacionEntrega.setComentariosAlumno(comentariosAlumno);
        return informacionEntrega;
    }
    
    public void asignarCamposInformacion(int idEntrega){
        lbNombreActividad.setText(actividadInformacion.getNombre());
        txAreaDescripcion.setText(actividadInformacion.getDescripcion());
        txAreaComentariosAlumno.setText(actividadInformacion.getComentariosAlumno());
        List<POJArchivos> archivosEntrega = DAOEntrega.obtenerArchivosEntregaPorId(idEntrega).getArchivosEntrega();
        for (POJArchivos archivo : archivosEntrega) {
            // Accede a los atributos o métodos de cada archivo
            File archivoEntrega = archivo.getArchivosEntrega();
            listViewArchivos.getItems().add(archivoEntrega.getName());
        }
    }
    
    public boolean validarInformacion(POJEntrega actividadInformacionIngresada) {

        boolean datosValidos = true;

        if ((actividadInformacionIngresada.getComentariosAlumno().isEmpty())) {
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campo vacío", "Por favor llene el campo de comentarios", Alert.AlertType.ERROR);
        }

        if (datosValidos) {
            datosValidos = true;
        }

        return datosValidos;
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
    
    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        if (tipoBoton.equals("Registrar")) {
            POJEntrega entregaInformacion = new POJEntrega();

            if (archivosAGuardar.getArchivosEntrega() != null) {
                entregaInformacion = obtenerInformacionIngresada();
                if ((validarInformacion(entregaInformacion))) {
                    entregaInformacion.setFechaEntrega(LocalDate.now().toString());
                    entregaInformacion.setActividad_idActividad(idActividad);
                    int respuestaRegistroEntrega = DAOEntrega.registrarEntrega(entregaInformacion);
                    if ((comprobarCodigoRespuesta(respuestaRegistroEntrega))) {
                        POJEntrega respuestaBDIdEntrega = DAOEntrega.obtenerIdEntrega(entregaInformacion);
                        if ((comprobarCodigoRespuesta(respuestaBDIdEntrega.getCodigoRespuesta()))) {
                            archivosAGuardar = asignarIdEntregaAArchivos(respuestaBDIdEntrega.getIdEntrega());
                            int respuestaRegistrarArchivos = DAOEntrega.registrarArchivosEntrega(archivosAGuardar);
                            if ((comprobarCodigoRespuesta(respuestaRegistrarArchivos))) {
                                int respuestaActualizacionEstado = DAOEntrega.actualizarEstadoActividad("Entregada", idActividad);

                                switch (respuestaActualizacionEstado) {
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
                                        Utilidades.mostrarDialogoSimple("Información registrada correctamente",
                                                "La entrega se ha registrado correctamente",
                                                Alert.AlertType.INFORMATION);
                                        cerrarVentana();
                                        break;
                                }
                            }
                        }
                    }
                }
            } else {
                Utilidades.mostrarDialogoSimple("Sin archivos", "Seleccione al menos un archivo para la entrega",
                        Alert.AlertType.WARNING);
            }
        }
        
        if (tipoBoton.equals("Modificar")) {
            POJEntrega entregaAModificar = obtenerInformacionIngresada();

            if (archivosAGuardar.getArchivosEntrega() != null) {
                if (entregaAModificar != null) {
                    if ((validarInformacion(entregaAModificar))) {
                        entregaAModificar.setActividad_idActividad(actividadInformacion.getIdActividad());
                        int respuestaBDActualizarComentarioProfesor = DAOEntrega.actualizarComentarioProfesor(entregaAModificar);
                        if (comprobarCodigoRespuesta(respuestaBDActualizarComentarioProfesor)) {
                            POJEntrega respuestaBDIdEntrega = DAOEntrega.obtenerIdEntrega(entregaAModificar);
                            if ((comprobarCodigoRespuesta(respuestaBDIdEntrega.getCodigoRespuesta()))) {
                                archivosAGuardar = asignarIdEntregaAArchivos(respuestaBDIdEntrega.getIdEntrega());
                                int respuestaRegistrarArchivos = DAOEntrega.registrarArchivosEntrega(archivosAGuardar);
                                if ((comprobarCodigoRespuesta(respuestaRegistrarArchivos))) {
                                    int respuestaActualizacionEstado = DAOEntrega.actualizarEstadoActividad("Entregada", idActividad);

                                    switch (respuestaActualizacionEstado) {
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
                                                    "La entrega se ha modificado correctamente",
                                                    Alert.AlertType.INFORMATION);
                                            cerrarVentana();
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if ((validarInformacion(entregaAModificar))) {
                    entregaAModificar.setActividad_idActividad(actividadInformacion.getIdActividad());
                    System.out.println("Comentario" + entregaAModificar.getComentariosAlumno());
                    int respuestaBDActualizarComentarioProfesor = DAOEntrega.actualizarComentarioProfesor(entregaAModificar);
                    switch (respuestaBDActualizarComentarioProfesor) {
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
                                    "La entrega se ha modificado correctamente",
                                    Alert.AlertType.INFORMATION);
                            cerrarVentana();
                            break;
                    }
                }
            }

        }

    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
}
