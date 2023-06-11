package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJCurso;
import sistema.spger.modelo.POJO.POJCursoRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.modelo.POJO.POJUsuarioRespuesta;
import sistema.spger.utils.Constantes;

public class DAOCurso {
    
    public static POJCursoRespuesta comprobarInformacionDuplicada(POJCurso cursoARegistrar){
        POJCursoRespuesta cursoRespuesta = new POJCursoRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try{
                int nrc = cursoARegistrar.getNrc();
                int bloque = cursoARegistrar.getBloque();
                int seccion = cursoARegistrar.getSeccion();
                String consulta = "SELECT COUNT(*) > 0 AS resultado FROM curso "
                        + "WHERE nrc = ? AND bloque = ? AND seccion = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, nrc);
                prepararSentencia.setInt(2, bloque);
                prepararSentencia.setInt(3, seccion);
                ResultSet resultado = prepararSentencia.executeQuery();
                cursoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    boolean cursoDuplicado = resultado.getBoolean("result");
                    cursoRespuesta.setCursoDuplicado(cursoDuplicado);
                }
            } catch (SQLException ex) {
                cursoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            cursoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return cursoRespuesta;
    }
          
    public static int registrarCurso(POJCurso cursoARegistrar) {
        int respuesta;
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        if (conexion != null){
            try {
                String nombre = cursoARegistrar.getNombre();
                int nrc = cursoARegistrar.getNrc();
                String descripcion = cursoARegistrar.getDescripcion();
                int bloque = cursoARegistrar.getBloque();
                int seccion = cursoARegistrar.getSeccion();
                String periodo = cursoARegistrar.getPeriodo();
                int idProfesor = cursoARegistrar.getIdUsuario();
                String consulta = "INSERT INTO curso (nombre, nrc, descripcion, bloque, seccion, periodo, Usuario_idUsuario) " +
                        "VALUES (? , ?, ? , ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setInt(2, nrc);
                prepararSentencia.setString(3, descripcion);
                prepararSentencia.setInt(4, bloque);
                prepararSentencia.setInt(5, seccion);
                prepararSentencia.setString(6, periodo);
                prepararSentencia.setInt(7, idProfesor);
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
    
    public static POJCursoRespuesta obtenerCursosDeUsuario(int idUsuario) {
        POJCursoRespuesta respuesta = new POJCursoRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if (conexion != null){
            try {
                String consulta = "SELECT c.nombre FROM curso_usuario cu LEFT JOIN curso c ON c.idCurso = cu.idCurso "
                        + "WHERE cu.idUsuario = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(!resultado.next()){
                   respuesta.setCodigoRespuesta(Constantes.ERROR_INFORMACION_VACIA);
                } else {
                    ArrayList<POJCurso> listaRoles = new ArrayList<>();
                    do{
                        POJCurso nombreCurso = new POJCurso();
                        nombreCurso.setNombre(resultado.getString("nombre"));
                        listaRoles.add(nombreCurso);
                    } while (resultado.next());
                    respuesta.setCursos(listaRoles);
                }
                respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexion.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
}
