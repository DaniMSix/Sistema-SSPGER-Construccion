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
                
                //dpFechaCreacion.setEditable(false);
                break;
            case "Registrar":
                System.err.println("IdActividad " + actividadInformacion.getIdActividad());
                lbTitulo.setText("Entregar actividad");
                lbNombreActividad.setText(actividadInformacion.getNombre());
                txAreaDescripcion.setText(actividadInformacion.getDescripcion());
                txAreaDescripcion.setEditable(false);
                break;
       }
    }

    @FXML
    private void clicBotonEliminar(ActionEvent event) {
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
                System.err.println("Tamaño excedido");
                lbTamanioMaximo.setText("Ha sobrepasado el tamaño permitido");
            } else {
            }
        }

    }

    private POJArchivosRespuesta asignarIdEntregaAArchivos(int entregaId) {
        List<POJArchivos> archivos = archivosAGuardar.getArchivosEntrega();
        for (POJArchivos archivo : archivos) {
            archivo.setEntrega_idEntrega(entregaId);
            System.out.println("id asignar" + archivo.getEntrega_idEntrega());
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
    
    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        
        POJEntrega entregaInformacion = new POJEntrega();
        entregaInformacion = obtenerInformacionIngresada();
        entregaInformacion.setFechaEntrega(LocalDate.now().toString());
        entregaInformacion.setActividad_idActividad(idActividad);
        // REGISTRAR ENTREGA
        int respuestaRegistroEntrega = DAOEntrega.registrarEntrega(entregaInformacion);
        
        // OBTENER ID DE LA ENTREGA
        POJEntrega respuestaBDIdEntrega = DAOEntrega.obtenerIdEntrega(entregaInformacion);
        
        // REGISTRAR ARCHIVOS 
        archivosAGuardar = asignarIdEntregaAArchivos(respuestaBDIdEntrega.getIdEntrega());
        int codigoRespuesta = DAOEntrega.registrarArchivosEntrega(archivosAGuardar);
        
        // ACTUALIZAR ESTADO
        int respuestaActualizacionEstado = DAOEntrega.actualizarEstadoActividad("Entregada", idActividad);

                switch (respuestaRegistroEntrega) {
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
                                "El usuario se ha modificado correctamente",
                                Alert.AlertType.INFORMATION);
                        break;
                }
            
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }

}
