package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJDirectorDeTrabajo;
import sistema.spger.utils.Constantes;


public class DAODirectorDeTrabajo {
    public static POJDirectorDeTrabajo obtenerIdDirectorDeTrabajo(int idUsuario) throws SQLException{
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        POJDirectorDeTrabajo respuestaBD = new POJDirectorDeTrabajo();
        
        if (conexion != null){
            try{
                String consulta = "SELECT idDirectorDeTrabajo FROM directordetrabajo WHERE Usuario_idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    int idEntrega = resultado.getInt("idDirectorDeTrabajo");
                    respuestaBD.setIdDirectorDeTrabajo(idEntrega);
                }
            } catch (SQLException ex) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }

}
