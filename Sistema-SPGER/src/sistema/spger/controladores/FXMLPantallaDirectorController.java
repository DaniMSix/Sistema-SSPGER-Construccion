package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJUsuario;

public class FXMLPantallaDirectorController implements Initializable {

    POJUsuario usuarioProfesor;
    private boolean inicioConExito;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void prepararRolesUsuario(POJUsuario usuarioActual) throws SQLException{ 
        usuarioProfesor = usuarioActual;
        
    }
    

    @FXML
    private void clicBotonConsultarAnteproyectos(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLConsultarAnteproyecto.fxml"));
            Parent vista = loader.load();
            FXMLConsultarAnteproyectoController formularioAnteproyecto = loader.getController();
            formularioAnteproyecto.inicializarInformacion(usuarioProfesor);
            Scene escena = new Scene(vista);
            Stage escenarioBase = new Stage();
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setScene(escena);
            if (formularioAnteproyecto.isInicioConExito()) {
                escenarioBase.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioActividadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
