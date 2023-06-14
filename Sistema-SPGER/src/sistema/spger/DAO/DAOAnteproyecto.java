package sistema.spger.DAO;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJAnteproyecto;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistema.spger.modelo.POJO.POJAnteproyectoRespuesta;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;

public class DAOAnteproyecto {
    public static int guardarAnteproyecto(POJAnteproyecto anteproyectoNuevo) throws SQLException, FileNotFoundException{
        int respuesta;
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD !=null){
            try{
                String sentencia = "INSERT INTO anteproyecto(nombre,modalidad,DirectorDeTrabajo_idDirectorDeTrabajo, archivoAnteproyecto, LGAC_idLGAC, Estado_idEstado) "+
                        "VALUES (?,?,?,?,?,?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoNuevo.getNombreAnteproyecto());
                prepararSentencia.setString(2, anteproyectoNuevo.getModalidad());
                prepararSentencia.setInt(3, anteproyectoNuevo.getIdDirectorDeTrabajo());
                prepararSentencia.setBytes(4,anteproyectoNuevo.getArchivoAnteproyecto());
                prepararSentencia.setInt(5, anteproyectoNuevo.getIdLGAC());
                prepararSentencia.setInt(6, anteproyectoNuevo.getIdEstado());
                System.out.println("nombre "+anteproyectoNuevo.getNombreAnteproyecto());
                System.out.println("modalidad "+anteproyectoNuevo.getModalidad());
                System.out.println("directorDeTrabajo "+anteproyectoNuevo.getIdDirectorDeTrabajo());                
                System.out.println("archivo "+anteproyectoNuevo.getArchivoAnteproyecto().length);
                System.out.println("LGAC "+anteproyectoNuevo.getIdLGAC());
                System.out.println("estado "+anteproyectoNuevo.getIdEstado());
                int filasAfectadas = prepararSentencia.executeUpdate();
                prepararSentencia.close();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA:Constantes.ERROR_CONSULTA;
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
                
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static POJAnteproyectoRespuesta obtenerAnteproyectosAsignados(int idDirectorDeTrabajo) throws SQLException{
        POJAnteproyectoRespuesta respuestaBD = new POJAnteproyectoRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        ArrayList<POJAnteproyecto> anteproyectos = new ArrayList();

        if (conexion != null) {
            try {
                String consulta = "SELECT a.idAnteproyecto, a.nombre, a.modalidad "
                        + "FROM anteproyecto a "
                        + "WHERE a.Estado_idEstado = 3 AND a.DirectorDeTrabajo_idDirectorDeTrabajo = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idDirectorDeTrabajo);
                ResultSet respuestaAnteproyectosBD = prepararSentencia.executeQuery();

                while (respuestaAnteproyectosBD.next()) {
                    POJAnteproyecto anteproyecto = new POJAnteproyecto();
                    anteproyecto.setIdAnteproyecto(respuestaAnteproyectosBD.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(respuestaAnteproyectosBD.getString("nombre"));
                    anteproyecto.setModalidad(respuestaAnteproyectosBD.getString("modalidad"));
                    
                    anteproyectos.add(anteproyecto);
                }
                respuestaBD.setAnteproyectos(anteproyectos);

                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch (SQLException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }

        return respuestaBD;
    }
    
    public static POJUsuario obtenerEstudiantePorIdAnteproyecto(int idAnteproyecto) throws SQLException{
        POJUsuario respuestaBD = new POJUsuario();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "SELECT Usuario_idUsuario FROM estudiante WHERE Anteproyecto_idAnteproyecto = ? LIMIT 1";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet respuestaEstudianteDB = prepararSentencia.executeQuery();

                if (respuestaEstudianteDB.next()) {
                    respuestaBD.setIdUsuario(respuestaEstudianteDB.getInt("Usuario_idUsuario"));
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