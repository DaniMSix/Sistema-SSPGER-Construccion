package sistema.spger.dbArchivos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DBAObtenerUsuario {
    
    public static DBAUsuarioBD leerArchivoUsuario(){
        String usuario;
        String contrasenia;
        DBAUsuarioBD usuarioDB = new DBAUsuarioBD();
        try{
            FileReader fr= new FileReader("recursosBD/RecUsuarios.txt");
            BufferedReader br= new BufferedReader(fr);
            try{
                usuario = br.readLine();
                contrasenia = br.readLine();
                usuarioDB.setUsuario(usuario);
                usuarioDB.setContrasenia(contrasenia);
                br.close();
            }catch(IOException e1){
                System.out.println("Error en la lectura");
            }
        }catch(FileNotFoundException ex){
            System.out.println("No existe el archivo");
        }
        return usuarioDB;
    }
}
