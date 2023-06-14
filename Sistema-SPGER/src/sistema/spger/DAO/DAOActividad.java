package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJActividad;
import sistema.spger.modelo.POJO.POJActividadRespuesta;
import sistema.spger.utils.Constantes;

public class DAOActividad {

    public static int registrarActividad(POJActividad actividadARegistrar) throws SQLException {
        System.out.println("nombre"+actividadARegistrar.getNombre());
        System.out.println("descripción"+actividadARegistrar.getDescripcion());
        System.out.println("fechaCreación"+actividadARegistrar.getFechaCreacion());
        System.out.println("fechaLimiteEntrega"+actividadARegistrar.getFechaLimiteEntrega());
        System.out.println("estado"+actividadARegistrar.getEstado());
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO actividad (nombre, descripcion, fechaCreacion, fechaLimiteEntrega, estado) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividadARegistrar.getNombre());
                prepararSentencia.setString(2, actividadARegistrar.getDescripcion());
                prepararSentencia.setString(3, actividadARegistrar.getFechaCreacion());
                prepararSentencia.setString(4, actividadARegistrar.getFechaLimiteEntrega());
                prepararSentencia.setString(5, actividadARegistrar.getEstado());
                int filasInsertadas = prepararSentencia.executeUpdate();
                if (filasInsertadas > 0) {
                    codigoRespuesta = Constantes.OPERACION_EXITOSA;
                } else {
                    codigoRespuesta = Constantes.ERROR_CONSULTA;
                }
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;

    }

    public static POJActividadRespuesta obtenerActividadesProgramadas(int idEstudiante, int idCurso) throws SQLException {
        System.out.println("DAO");
        System.out.println("idEstudiante" + idEstudiante);
        System.out.println("idCurso" + idCurso);
        POJActividadRespuesta respuesta = new POJActividadRespuesta();
        ArrayList<POJActividad> actividadConsulta = new ArrayList();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null) {
            try {
                String consulta = "SELECT ca.idActividad, a.nombre, a.descripcion, a.estado, a.fechaLimiteEntrega, a.fechaCreacion " +
                "FROM curso_actividad ca " +
                "LEFT JOIN usuario_actividad ua ON ca.idActividad = ua.idActividad " +
                "LEFT JOIN actividad a ON a.idActividad = ua.idActividad " +
                "WHERE ua.idUsuario = ? AND ca.idCurso = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                prepararSentencia.setInt(2, idCurso);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    POJActividad actividad = new POJActividad();
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                    actividad.setNombre(resultado.getString("nombre"));
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setEstado(resultado.getString("estado"));
                    actividad.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                    actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                    actividadConsulta.add(actividad);
                }
                    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                
                respuesta.setActividades(actividadConsulta);
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    

    public static int modificarActividad(POJActividad actividadModificar) throws SQLException {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "UPDATE actividad SET nombre = ?, descripcion = ?, "
                        + "fechaLimiteEntrega = ? WHERE  idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividadModificar.getNombre());
                prepararSentencia.setString(2, actividadModificar.getDescripcion());
                prepararSentencia.setString(3, actividadModificar.getFechaLimiteEntrega());
                prepararSentencia.setInt(4, actividadModificar.getIdActividad());
                prepararSentencia.executeUpdate();
                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;
    }

    public static POJActividad obtenerIdActividad(POJActividad actividadRegistrada) throws SQLException {
        POJActividad respuestaBD = new POJActividad();

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT idActividad FROM actividad WHERE nombre = ? AND descripcion = ? AND fechaCreacion = ? AND fechaLimiteEntrega = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividadRegistrada.getNombre());
                prepararSentencia.setString(2, actividadRegistrada.getDescripcion());
                prepararSentencia.setString(3, actividadRegistrada.getFechaCreacion());
                prepararSentencia.setString(4, actividadRegistrada.getFechaLimiteEntrega());
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    respuestaBD.setIdActividad(resultado.getInt("idActividad"));
                }
            } catch (SQLException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    
    public static int registrarCursoActividad(int idCurso, int idActividad) throws SQLException {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO curso_actividad(idCurso, idActividad) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCurso);
                prepararSentencia.setInt(2, idActividad);
                    codigoRespuesta = Constantes.OPERACION_EXITOSA;
                prepararSentencia.executeUpdate();
                
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;

    }
    
    public static int registrarUsuarioActividad(int idUsuario, int idActividad) throws SQLException {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO usuario_actividad(idUsuario, idActividad) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                prepararSentencia.setInt(2, idActividad);
                codigoRespuesta = Constantes.OPERACION_EXITOSA;
                prepararSentencia.executeUpdate();
                
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }
        return codigoRespuesta;

    }
    
    public static POJActividadRespuesta obtenerActividadesPorUsuario(int idUsuario) throws SQLException{
        POJActividadRespuesta respuestaBD = new POJActividadRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        ArrayList<POJActividad> actividades = new ArrayList();

        if (conexion != null) {
            try {
                String consulta = "SELECT a.idActividad, a.nombre, a.descripcion, a.fechaCreacion, a.fechaLimiteEntrega, estado FROM actividad a " +
                    "LEFT JOIN usuario_actividad ua ON ua.idactividad = a.idActividad " +
                    "WHERE ua.idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    POJActividad actividad = new POJActividad();
                actividad.setIdActividad(resultado.getInt("idActividad"));
                actividad.setNombre(resultado.getString("nombre"));
                actividad.setDescripcion(resultado.getString("descripcion"));
                actividad.setFechaCreacion(resultado.getString("fechaCreacion"));
                actividad.setFechaLimiteEntrega(resultado.getString("fechaLimiteEntrega"));
                actividad.setEstado(resultado.getString("estado"));
                actividades.add(actividad);
                
                }
                respuestaBD.setActividades(actividades);
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
