/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.spger.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import sistema.spger.modelo.POJO.POJUsuario;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLPantallaProfesorController implements Initializable {

    int usuarioProfesor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void prepararRolesUsuario(int idUsuario){
        usuarioProfesor = idUsuario;
    }
    
}
