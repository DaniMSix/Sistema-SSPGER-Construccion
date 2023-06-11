/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sistema.spger.modelo.POJO.POJUsuario;


/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLPantallaAdministradorController implements Initializable {
    
    POJUsuario usuarioAdministrador = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void recibirInformaciónAdministrador(POJUsuario usuarioLogueado){
        usuarioAdministrador = usuarioLogueado;
    }

    @FXML
    private void clicAnadirUsuario(ActionEvent event) {
        
    }
}
