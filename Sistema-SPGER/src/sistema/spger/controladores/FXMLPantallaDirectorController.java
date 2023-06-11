package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLPantallaDirectorController implements Initializable {

    int usuarioProfesor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioProfesor = idUsuario;
    }
    
}
