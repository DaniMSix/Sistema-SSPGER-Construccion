package sistema.spger.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistema.spger.DAO.DAORol;
import sistema.spger.DAO.DAOSesion;
import sistema.spger.SistemaSPGER;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;
import sistema.spger.utils.Utilidades;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJRolRespuesta;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfCorreo;
    
    @FXML
    private PasswordField pfContrasenia;
    
    String EXPRESION_COMPROBAR_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        obtenerInformacionIngresada();
    }
    
    private void obtenerInformacionIngresada(){
        String correo = tfCorreo.getText();
        String contrasenia = pfContrasenia.getText();
        validarInformacion(correo, contrasenia);
    }
    
    public void validarInformacion(String correo, String contrasenia){
        
        boolean datosValidos = true;
        
        if((correo.isEmpty()) && (contrasenia.isEmpty()) ){
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Campos vacíos", "Por favor llene todos los campos "
                    + "con la información necesaria", Alert.AlertType.ERROR);
        }
        
        if(!(correo.matches(EXPRESION_COMPROBAR_EMAIL))){
            datosValidos = false;
            Utilidades.mostrarDialogoSimple("Correo inválido", "Por favor ingrese un "
                    + "correo válido", Alert.AlertType.ERROR);
        } 
        if(datosValidos){
            validarCredencialesUsuario(correo, contrasenia);
        }
        
    }
    
    public void validarCredencialesUsuario(String correo, String contrasenia) {
        List<POJRol> listaRoles = new ArrayList<>();
        POJUsuario usuarioRespuesta = DAOSesion.verificarSesionUsuario(correo, contrasenia);
        
        switch(usuarioRespuesta.getCodigoRespuesta()){
            
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, intentelo más tarde", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                if (usuarioRespuesta.getIdUsuario()>0){
                    Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                        "Bienvenido(a) "+usuarioRespuesta.toString()+" al sistema...", 
                        Alert.AlertType.INFORMATION);
                    int idUsuario = usuarioRespuesta.getIdUsuario();
                    POJRolRespuesta respuestaBD = new POJRolRespuesta();
                    respuestaBD = DAORol.obtenerRoles(idUsuario);
                    mostrarPantallaPrincipal(respuestaBD, usuarioRespuesta);
                } else {
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                            "El usuario y/o contraseña no son correctos, por favor verifica la información", 
                            Alert.AlertType.WARNING);
                }
        }
    }
    
   
    
    public void mostrarPantallaPrincipal(POJRolRespuesta respuestaBD, POJUsuario usuarioVerificado) {
       
        Stage escenarioBase = (Stage) tfCorreo.getScene().getWindow();
        Scene escena = null;
        FXMLLoader loader = new FXMLLoader(SistemaSPGER.class.getResource("vistas/FXMLPantallaPrincipal.fxml"));
        try {
            Parent vista = loader.load();
            FXMLPantallaPrincipalController pantallaPrincipal = loader.getController();
            pantallaPrincipal.prepararRolesUsuario(respuestaBD, usuarioVerificado);
            escena = new Scene(vista);
            escenarioBase.setScene(escena);
            escenarioBase.setAlwaysOnTop(true);
            escenarioBase.show();
            escenarioBase.setTitle("SSPGER");
        } catch (IOException ex) {
            Utilidades.mostrarDialogoSimple("Error al cargar", "Hubo un error al intentar cargar la ventana, "
                    + "intentélo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    
    
    // <a href="https://storyset.com/user">User illustrations by Storyset</a>
       
}
