package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJRol;
import sistema.spger.modelo.POJO.POJRolRespuesta;
import sistema.spger.utils.Constantes;


public class DAORol {
    
    public static POJRolRespuesta obtenerRoles(int idUsuario) throws SQLException {
        POJRolRespuesta respuesta = new POJRolRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if (conexion != null){
            try {
                String consulta = "SELECT descripcion from rol where Usuario_idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(!resultado.next()){
                   respuesta.setCodigoRespuesta(Constantes.ERROR_INFORMACION_VACIA);
                } else {
                    ArrayList<POJRol> listaRoles = new ArrayList<>();
                    do{
                        POJRol rolUsuario = new POJRol();
                        rolUsuario.setDescripcion(resultado.getString("descripcion"));
                        listaRoles.add(rolUsuario);
                    } while (resultado.next());
                    respuesta.setListaRoles(listaRoles);
                }
                conexion.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    public static POJRol registrarRolUsuario(POJRol rolARegistrar) throws SQLException{
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try{
                String descripcion = rolARegistrar.getDescripcion();
                int idUsuario = rolARegistrar.getIdUsuario();
                String consulta = "INSERT INTO rol (descripcion, Usuario_idUsuario) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, descripcion);
                prepararSentencia.setInt(2, idUsuario);
                prepararSentencia.executeUpdate();
                rolARegistrar.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch(SQLException ex){
                rolARegistrar.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            rolARegistrar.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return rolARegistrar;
    }
}
