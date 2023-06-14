package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.modelo.POJO.POJUsuarioRespuesta;
import sistema.spger.utils.Constantes;


public class DAOProfesor {
    public static POJUsuarioRespuesta obtenerInformacionProfesores() throws SQLException{
        POJUsuarioRespuesta respuesta = new POJUsuarioRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexionBD = abrirConexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "SELECT usuario.idUsuario, concat(usuario.nombre, ' ', usuario.apellidoPaterno, ' ', usuario.apellidoMaterno) " +
                "AS nombreCompleto FROM usuario INNER JOIN rol ON usuario.idUsuario = rol.Usuario_idusuario " +
                "WHERE rol.descripcion = 'Profesor'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado =  prepararSentencia.executeQuery();
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                ArrayList<POJUsuario> profesoresConsulta = new ArrayList();
                while(resultado.next()){
                    POJUsuario usuarioConsulta = new POJUsuario();
                    usuarioConsulta.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioConsulta.setNombreCompleto(resultado.getString("nombreCompleto"));
                    profesoresConsulta.add(usuarioConsulta);
                }
                respuesta.setProfesores(profesoresConsulta);
                conexionBD.close();
            }catch (SQLException e){
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    

}
