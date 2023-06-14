package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOActividad;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLConsultarActividadProgramadaController implements Initializable {

    @FXML
    private TableView<POJActividad> tvActividadesProgramadas;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcFechaLimiteEntrega;
    private ObservableList<POJActividad> actividades;
    POJUsuario estudianteActual;
    private int idEstudiante;
    private int idCurso;
    @FXML
    private TextField tfBusqueda;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarInformacion(int idEstudianteLogueado, int idCursoEstudiante) throws SQLException {
        idEstudiante = idEstudianteLogueado;
        idCurso = idCursoEstudiante;
        configurarTablaUsuarios();
        cargarInformacionTabla();
        System.out.println("Pantalla actividad");
        System.out.println("idEstudiante " + idEstudiante);
        System.out.println("idCurso " + idCurso);
    }

    public void configurarTablaUsuarios() {
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcFechaLimiteEntrega.setCellValueFactory(new PropertyValueFactory("fechaLimiteEntrega"));
    }

   public void cargarInformacionTabla() throws SQLException {
    actividades = FXCollections.observableArrayList();
    POJActividadRespuesta respuestaBD = DAOActividad.obtenerActividadesProgramadas(idEstudiante, idCurso);
       System.err.println("Actividades tabla");
       System.err.println("codigo " + respuestaBD.getCodigoRespuesta());

    switch (respuestaBD.getCodigoRespuesta()) {
        case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
            break;
        case Constantes.OPERACION_EXITOSA:
            actividades.addAll(respuestaBD.getActividades());
            tvActividadesProgramadas.setItems(actividades);
            configurarBusquedaTabla();
            break;
    }
}



    @FXML
    private void clicBotonModificar(ActionEvent event) throws SQLException {
        POJActividad actividadSeleccionada = tvActividadesProgramadas.getSelectionModel().getSelectedItem();
        if (actividadSeleccionada != null) {
            irFormulario("Modificar", actividadSeleccionada, 0, 0);
        } else {
            Utilidades.mostrarDialogoSimple("Atención", "Selecciona el registro "
                    + "en la tabla para poder editarlo", Alert.AlertType.WARNING);
        }
        
        cargarInformacionTabla();
        tvActividadesProgramadas.refresh();

    }

    private void irFormulario(String tipoBoton, POJActividad actividadInformacion, int idEstudiante, int idCurso) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLFormularioActividad.fxml"));
            Parent vista = loader.load();
            FXMLFormularioActividadController formularioActividad = loader.getController();
            formularioActividad.inicializarInformacionFormulario(tipoBoton, actividadInformacion, idEstudiante, idCurso);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            escenarioBase.showAndWait();
            cargarInformacionTabla();
            tvActividadesProgramadas.refresh();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void clicBotonAgregarTarea(ActionEvent event) throws SQLException {
        irFormulario("Registrar", null, idEstudiante, idCurso);
        
    }

    private void configurarBusquedaTabla() {
    if (!actividades.isEmpty()) {
        FilteredList<POJActividad> filtradoActividades = new FilteredList<>(actividades, p -> true);
        tfBusqueda.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filtradoActividades.setPredicate(actividadFiltro -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerNewValue = newValue.toLowerCase();
                String nombreActividad = actividadFiltro.getNombre().toLowerCase();
                return nombreActividad.contains(lowerNewValue);
            });
        });
        SortedList<POJActividad> sortedListaActividades = new SortedList<>(filtradoActividades);
        sortedListaActividades.comparatorProperty().bind(tvActividadesProgramadas.comparatorProperty());
        tvActividadesProgramadas.setItems(sortedListaActividades);
    }
}



}
