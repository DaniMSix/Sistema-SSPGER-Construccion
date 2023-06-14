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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.DAO.DAOAnteproyecto;
import sistema.spger.DAO.DAODirectorDeTrabajo;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import sistema.spger.modelo.POJO.POJAnteproyectoRespuesta;
import sistema.spger.modelo.POJO.POJDirectorDeTrabajo;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLConsultarAnteproyectoController implements Initializable {

    @FXML
    private TableView<POJAnteproyecto> tvAnteproyectos;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcModalidad;
    private ObservableList<POJAnteproyecto> anteproyectos;
    private int directorActual;
    @FXML
    private TextField tfBusqueda;
    private boolean inicioConExito;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public boolean isInicioConExito() {
        return inicioConExito;
    }

    public void inicializarInformacion(POJUsuario directorLogueado) {
        inicioConExito = false;
        try {
            POJDirectorDeTrabajo respuestaObtenerIdDirector = DAODirectorDeTrabajo.obtenerIdDirectorDeTrabajo(directorLogueado.getIdUsuario());
            if (respuestaObtenerIdDirector != null) {

                switch (respuestaObtenerIdDirector.getCodigoRespuesta()) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                        directorActual = respuestaObtenerIdDirector.getIdDirectorDeTrabajo();
                        inicioConExito = true;
                        break;
                }
            }
            configurarTablaUsuarios();
            cargarInformacionTabla();
        } catch (SQLException e) {
            Utilidades.mostrarDialogoSimple("Error", "Error, inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }

    public void configurarTablaUsuarios() {
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        tcModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
    }

    public void cargarInformacionTabla() throws SQLException {
        anteproyectos = FXCollections.observableArrayList();
        try {
            POJAnteproyectoRespuesta respuestaBD = DAOAnteproyecto.obtenerAnteproyectosAsignados(directorActual);
            switch (respuestaBD.getCodigoRespuesta()) {
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                    break;
                case Constantes.OPERACION_EXITOSA:
                    anteproyectos.addAll(respuestaBD.getAnteproyectos());
                    tvAnteproyectos.setItems(anteproyectos);
                    configurarBusquedaTabla();
                    break;
            }
        } catch (SQLException e) {
            Utilidades.mostrarDialogoSimple("Error de base de datos", "Hubo un error al acceder a la base de datos, revise su conexión", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void dobleClicFila(MouseEvent event) {
    if (event.getClickCount() == 2) {
        try {
            int idAnteproyecto = tvAnteproyectos.getSelectionModel().getSelectedItem().getIdAnteproyecto();
            int idUsuario = DAOAnteproyecto.obtenerEstudiantePorIdAnteproyecto(idAnteproyecto).getIdUsuario();
            irFormulario(idUsuario);
        } catch (SQLException ex) {
            Utilidades.mostrarDialogoSimple("Error", "Error al obtener el estudiante", Alert.AlertType.ERROR);
        }
    }
}


    private void irFormulario(int idUsuario) throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLConsultarActividadesDeAnteproyectos.fxml"));
            Parent vista = loader.load();
            FXMLConsultarActividadesDeAnteproyectosController formularioActividad = loader.getController();
            formularioActividad.inicializarInformacion(idUsuario);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            if (formularioActividad.isInicioConExito()) {
                escenarioBase.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void configurarBusquedaTabla() {
        if (!anteproyectos.isEmpty()) {
            FilteredList<POJAnteproyecto> filtradoActividades = new FilteredList<>(anteproyectos, p -> true);
            tfBusqueda.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                filtradoActividades.setPredicate(actividadFiltro -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerNewValue = newValue.toLowerCase();
                    String nombreActividad = actividadFiltro.getNombreAnteproyecto().toLowerCase();
                    return nombreActividad.contains(lowerNewValue);
                });
            });
            SortedList<POJAnteproyecto> sortedListaActividades = new SortedList<>(filtradoActividades);
            sortedListaActividades.comparatorProperty().bind(tvAnteproyectos.comparatorProperty());
            tvAnteproyectos.setItems(sortedListaActividades);
        }
    }
}
