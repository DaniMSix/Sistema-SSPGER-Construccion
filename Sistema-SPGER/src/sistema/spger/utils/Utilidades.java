package sistema.spger.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.spger.SistemaSPGER;


public class Utilidades {
    public static void mostrarDialogoSimple(String titulo, String mensaje,
        Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        
        //alertaSimple.initModality(Modality.APPLICATION_MODAL);
        
        //Stage stage = (Stage) alertaSimple.getDialogPane().getScene().getWindow();
    //stage.initOwner(null); // Establecer el propietario como null para asegurarse de que se muestre por encima de otras ventanas

        
        alertaSimple.showAndWait();
    }
    
    public static Scene inicializarEscena(String ruta) throws IOException {
        Scene escena = null;
        try
        {
            //Parent vista = FXMLLoader.load(JFXControlEscolar.class.getResource(ruta));
            Parent vista = FXMLLoader.load(SistemaSPGER.class.getResource(ruta));
            escena = new Scene(vista);
        } catch (IOException ex)
        {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return escena;
    }
    
    public static String encriptarContraseñaSHA512(String contrasenia) throws UnsupportedEncodingException {
        String contrasenaEncriptada = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(contrasenia.getBytes("utf8"));
            contrasenaEncriptada = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException nsaException) {
            //Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, nsaException);
        } catch (UnsupportedEncodingException ueException) {
            //Logger.getLogger(SHA512.class.getName()).log(Level.SEVERE, null, ueException);
        }
        return contrasenaEncriptada;
    }
    
    
}
