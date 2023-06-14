package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadRespuesta;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLConsultarActividadesDeAnteproyectosController implements Initializable {

    @FXML
    private TableView<POJActividad> tvActividadesAnteproyectos;
    @FXML
    private TableColumn tcNombreActividad;
    @FXML
    private TableColumn tcFechaLimiteEntrega;
    @FXML
    private TableColumn tcEstado;
    int idUsuario;
    
    private ObservableList<POJActividad> actividades;
    @FXML
    private Button bttEvaluar;
    @FXML
    private TextField tfBusqueda;
    private boolean inicioConExito;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacion(int idUsuarioActividad) throws SQLException{
        inicioConExito = false; 
        idUsuario = idUsuarioActividad;
        try {
            configurarTablaUsuarios();
            cargarInformacionTabla();
            inicioConExito = true;
        }catch(SQLException e){
            Utilidades.mostrarDialogoSimple("Error", "Error, inténtelo más tarde", Alert.AlertType.ERROR);
        }
        
    }
    
    public boolean isInicioConExito() {
        return inicioConExito;
    }
    
    public void configurarTablaUsuarios(){
        tcNombreActividad.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaLimiteEntrega.setCellValueFactory(new PropertyValueFactory("fechaLimiteEntrega"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    public void cargarInformacionTabla() throws SQLException {
        actividades = FXCollections.observableArrayList();
        POJActividadRespuesta respuestaBD = DAOActividad.obtenerActividadesPorUsuario(idUsuario);

        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                actividades.addAll(respuestaBD.getActividades());
                tvActividadesAnteproyectos.setItems(actividades);
                break;
        }
    }

    @FXML
    private void dobleClicFila(MouseEvent event) {
        if ( event.getClickCount() == 2){
            POJActividad actividad = tvActividadesAnteproyectos.getSelectionModel().getSelectedItem();
            irFormulario("Ver detalle", actividad, 0, 0);
        } 
        if(event.getClickCount() == 1){
            String estado = tvActividadesAnteproyectos.getSelectionModel().getSelectedItem().getEstado();
            if (estado.equals("Entregada")){
                bttEvaluar.setDisable(false);
            } else {
                bttEvaluar.setDisable(true);
            }
        }
    }
    
    private void irFormulario(String tipoBoton, POJActividad actividadInformacion, int idEstudiante, int idCurso) {
    try {
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLFormularioActividad.fxml"));
        Parent vista = loader.load();
        FXMLFormularioActividadController formularioActividad = loader.getController();
        formularioActividad.inicializarInformacionFormulario(tipoBoton, actividadInformacion, idEstudiante, idCurso);
        Scene escena = new Scene(vista);
        Stage escenarioBase = new Stage();
        escenarioBase.initModality(Modality.APPLICATION_MODAL);
        escenarioBase.setScene(escena);
        if (formularioActividad.isInicioConExito()) {
            escenarioBase.showAndWait();
        }
    } catch (IOException ex) {
        Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Utilidades.mostrarDialogoSimple("Error", "Error, inténtelo más tarde", Alert.AlertType.ERROR);
        Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    private void irFormularioEvaluar(int idActividad) throws SQLException{

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLEvaluarEntrega.fxml"));
            Parent vista = loader.load();
            FXMLEvaluarEntregaController formularioActividad = loader.getController();
            formularioActividad.inicializarInformacionFormulario(idActividad);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void clicBotonEvaluar(MouseEvent event) throws SQLException {
        int idActividad = tvActividadesAnteproyectos.getSelectionModel().getSelectedItem().getIdActividad();
        irFormularioEvaluar(idActividad);
    }

    @FXML
    private void clicBotonSalir(MouseEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) tfBusqueda.getScene().getWindow();
        escenarioBase.close();
    }
    
}
