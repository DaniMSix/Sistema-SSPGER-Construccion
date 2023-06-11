package sistema.spger.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.spger.dbArchivos.DBAUsuarioBD;
import sistema.spger.dbArchivos.DBAObtenerUsuario;


public class ModConexionBD {
    private Connection connection;
    private final String url="jdbc:mysql://localhost:3307/spger?allowPublicKeyRetrieval=true&useSSL=false";
    private DBAUsuarioBD usuarioBD;

    public Connection getConnection() {
        connect();
        
        return connection;
    }

    private void connect() {
        usuarioBD = DBAObtenerUsuario.leerArchivoUsuario();
        try {
            connection = DriverManager.getConnection(url,usuarioBD.getUsuario(), usuarioBD.getContrasenia());
        } catch (SQLException ex) {
            Logger.getLogger(ModConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public void closeConection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ModConexionBD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
