package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividadEntrega;
import sistema.spger.modelo.POJO.POJActividadEntregaRespuesta;
import sistema.spger.utils.Constantes;

public class DAOActividadEntrega {
    
    public static POJActividadEntregaRespuesta obtenerEntregaActividades(int idEstudiante, int idCurso) throws SQLException {
        POJActividadEntregaRespuesta respuestaBD = new POJActividadEntregaRespuesta();
        ArrayList<POJActividadEntrega> actividadConsulta = new ArrayList();
        
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if(conexion != null){
            try{
                String consulta = "SELECT ca.idCurso, ca.idActividad, a.nombre, a.descripcion, a.estado, a.fechaLimiteEntrega, " +
                "e.idEntrega, e.calificacion, e.observacionProfesor, e.comentariosAlumno, e.fechaEntrega, a.fechaCreacion,  ua.idUsuario " +
                "FROM curso_actividad ca " +
                "LEFT JOIN usuario_actividad ua ON ca.idActividad = ua.idActividad " +
                "LEFT JOIN actividad a ON a.idActividad = ua.idActividad " +
                "LEFT JOIN entrega e ON a.idActividad = e.actividad_idActividad " +
                "WHERE ua.idUsuario = ? AND ca.idCurso = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, idCurso);
                ResultSet resultado =  prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                while(resultado.next()){
                    POJActividadEntrega actividadEntrega = new POJActividadEntrega();
                    actividadEntrega.setIdCurso(resultado.getInt("idCurso"));
                    actividadEntrega.setIdActividad(resultado.getInt("idActividad"));
                    actividadEntrega.setNombre(resultado.getString("nombre"));
                    actividadEntrega.setDescripcion(resultado.getString("descripcion"));
                    actividadEntrega.setEstado(resultado.getString("estado"));
                    actividadEntrega.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    actividadEntrega.setCalificacion(resultado.getFloat("calificacion"));
                    actividadEntrega.setObservacionProfesor(resultado.getString("observacionProfesor"));
                    actividadEntrega.setComentariosAlumno(resultado.getString("comentariosAlumno"));
                    actividadEntrega.setFechaEntrega(resultado.getString("fechaEntrega"));
                    actividadEntrega.setIdEntrega(resultado.getInt("idEntrega"));
                    actividadEntrega.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividadEntrega.setIdUsuario(resultado.getInt("idUsuario"));
                    actividadConsulta.add(actividadEntrega);
                }
                respuestaBD.setActividadesEntregas(actividadConsulta);
            }catch (SQLException e){
               respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    
    public static POJActividadEntrega obtenerActividadPorId(int idActividad) throws SQLException {
        POJActividadEntrega respuestaBD = new POJActividadEntrega();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT nombre, descripcion, fechaCreacion, fechaLimiteEntrega, estado FROM actividad WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    respuestaBD.setNombre(resultado.getString("nombre"));
                    respuestaBD.setDescripcion(resultado.getString("descripcion"));
                    respuestaBD.setFechaCreacion(resultado.getString("fechaCreacion"));
                    respuestaBD.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    respuestaBD.setEstado(resultado.getString("estado"));
                }
            } catch (SQLException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    
    public static POJActividadEntrega obtenerActividadEntregaPorIdActividad(int idActividad) throws SQLException{
        POJActividadEntrega respuestaBD = new POJActividadEntrega();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT nombre, descripcion, comentariosAlumno, idEntrega, calificacion, observacionProfesor FROM actividad " +
                    "LEFT JOIN entrega a ON idActividad = Actividad_idActividad " +
                    "WHERE idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idActividad);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                respuestaBD.setNombre(resultado.getString("nombre"));
                respuestaBD.setDescripcion(resultado.getString("descripcion"));
                respuestaBD.setComentariosAlumno(resultado.getString("comentariosAlumno"));
                respuestaBD.setIdEntrega(resultado.getInt("idEntrega"));
                respuestaBD.setCalificacion(resultado.getFloat("calificacion"));
                respuestaBD.setObservacionProfesor(resultado.getString("observacionProfesor"));
                }
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch (SQLException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }

    
    
}
