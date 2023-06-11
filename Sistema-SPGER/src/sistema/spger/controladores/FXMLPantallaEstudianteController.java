package sistema.spger.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import sistema.spger.DAO.DAOCurso;
import sistema.spger.modelo.POJO.POJCurso;
import sistema.spger.modelo.POJO.POJCursoRespuesta;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;

public class FXMLPantallaEstudianteController implements Initializable {

    int usuarioEstudiante;
    @FXML
    private Button btnProyectoGuiado;
    @FXML
    private Button btnExperienciaRecepcional;
    private int idEstudiante;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void prepararRolesUsuario(int idUsuario){
        idEstudiante = idUsuario;
        obtenerCursosDeUsuario();
    }
    
    public void obtenerCursosDeUsuario(){
        POJCursoRespuesta listaCursos = DAOCurso.obtenerCursosDeUsuario(idEstudiante);
        
        switch (listaCursos.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Sin conexión", "Error de conexión", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                System.err.println("OPERACION_EXITOSA");
                activarBotonPorCurso(listaCursos.getCursos());
                break;
        }
    }
    
    public void activarBotonPorCurso(ArrayList<POJCurso> listaCursosUsuario){
        System.err.println("activarBotonPorCurso");
        for (int indice=0; indice<listaCursosUsuario.size(); indice++){
            System.out.println("Curso " + listaCursosUsuario.get(indice).getNombre());
            String opcionBotonCurso = listaCursosUsuario.get(indice).getNombre();
            switch(opcionBotonCurso){
                case "Proyecto Guiado":
                    btnProyectoGuiado.setDisable(false);
                    break;
                case "Experiencia Recepcional":
                    btnExperienciaRecepcional.setDisable(false);
                    break;
            }
        } 
        
    }
    
}
