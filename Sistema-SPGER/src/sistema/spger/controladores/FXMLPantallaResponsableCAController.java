package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLPantallaResponsableCAController implements Initializable {

    int usuarioResponsableCA;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioResponsableCA = idUsuario;
    }
    
}
