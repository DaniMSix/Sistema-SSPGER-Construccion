package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJCurso;
import sistema.spger.modelo.POJO.POJCursoRespuesta;
import sistema.spger.modelo.POJO.POJLgac;
import sistema.spger.modelo.POJO.POJLgacRespuesta;
import sistema.spger.utils.Constantes;


public class DAOLgac {
    
    public static POJLgacRespuesta comprobarInformacionDuplicada(POJLgac lgacARegistrar){
        POJLgacRespuesta lgacConsulta = new POJLgacRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if (conexion != null){
            try{
                String nombre = lgacARegistrar.getNombre();
                String descripcion = lgacARegistrar.getDescripcion();
                String consulta = "SELECT COUNT(*) > 0 AS resultado FROM lgac WHERE nombre = ? "
                        + "AND descripcion = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, descripcion);
                ResultSet resultado = prepararSentencia.executeQuery();
                lgacConsulta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    boolean lgacDuplicado = resultado.getBoolean("resultado");
                    lgacConsulta.setLgacDuplicado(lgacDuplicado);
                }
            } catch (SQLException ex) {
                lgacConsulta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            lgacConsulta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return lgacConsulta;
    }

    public static int registrarLgac(POJLgac lgacARegistrar) {
        int respuesta;
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String nombre = lgacARegistrar.getNombre();
                String descripcion = lgacARegistrar.getDescripcion();
                String consulta = "INSERT INTO lgac (nombre, descripcion) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, descripcion);
                prepararSentencia.executeUpdate();
                respuesta = Constantes.OPERACION_EXITOSA;
                }
            catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }  
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
