package sistema.spger.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJAnteproyecto;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistema.spger.modelo.POJO.POJAnteproyectoRespuesta;
import sistema.spger.modelo.POJO.POJArchivos;
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
    /*
    public static POJAnteproyectoRespuesta obtenerInformacionAnteproyecto() throws SQLException{
        POJAnteproyectoRespuesta respuesta = new POJAnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        System.out.println("antes");
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyecto.nombre,"+
                                  " modalidad, archivoAnteproyecto, DirectorDeTrabajo_idDirectorDeTrabajo, usuario.nombre AS nombre_director, LGAC_idLGAC, lgac.nombre AS lgac_nombre,"+
                                  " Estado_idEstado, estado.descripcion AS Estado_anteproyecto " +
                                  "FROM anteproyecto "+
                                  "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC "+
                                  "INNER JOIN directordetrabajo ON anteproyecto.DirectorDeTrabajo_idDirectorDeTrabajo = directordetrabajo.idDirectorDeTrabajo "+
                                  "INNER JOIN usuario ON directordetrabajo.Usuario_idUsuario = usuario.idUsuario "+
                                  "INNER JOIN estado ON anteproyecto.Estado_idEstado = estado.idEstado;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);;
                ResultSet resultado = prepararSentencia.executeQuery();
                System.out.println("despues del exquery`");
                ArrayList<POJAnteproyecto> anteproyectos = new ArrayList();
                while(resultado.next()){
                    POJAnteproyecto anteproyecto = new POJAnteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(resultado.getString("nombre"));
                    anteproyecto.setIdDirectorDeTrabajo(resultado.getInt("DirectorDeTrabajo_idDirectorDeTrabajo"));
                    anteproyecto.setNombreDirectorDeTrabajo(resultado.getString("nombre_director"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setArchivoAnteproyecto(resultado.getBytes("archivoAnteproyecto"));                 
                    anteproyecto.setNombreLGAC(resultado.getString("lgac_nombre"));
                    anteproyecto.setIdLGAC(resultado.getInt("LGAC_idLGAC"));
                    anteproyecto.setIdEstado(resultado.getInt("Estado_idEstado"));
                    anteproyecto.setNombreEstado(resultado.getString("Estado_anteproyecto"));
                    anteproyectos.add(anteproyecto);
                    System.out.println("Nombre anteproyecto"+anteproyecto.getNombreAnteproyecto()+"\nestado"+anteproyecto.getNombreEstado());
                }
                respuesta.setAnteproyectos(anteproyectos);
                conexionBD.close();
                
            } catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    public static POJAnteproyectoRespuesta obtenerInfoAnteproyectoPorDirector(int director) throws SQLException{
        POJAnteproyectoRespuesta respuesta = new POJAnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyecto.nombre,"+
                                  " modalidad, archivoAnteproyecto, DirectorDeTrabajo_idDirectorDeTrabajo, usuario.nombre AS nombre_director, LGAC_idLGAC, lgac.nombre AS lgac_nombre,"+
                                  " Estado_idEstado, estado.descripcion AS Estado_anteproyecto " +
                                  "FROM anteproyecto "+
                                  "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC "+
                                  "INNER JOIN directordetrabajo ON anteproyecto.DirectorDeTrabajo_idDirectorDeTrabajo = directordetrabajo.idDirectorDeTrabajo "+
                                  "INNER JOIN usuario ON directordetrabajo.Usuario_idUsuario = usuario.idUsuario "+
                                  "INNER JOIN estado ON anteproyecto.Estado_idEstado = estado.idEstado "+
                                  "WHERE DirectorDeTrabajo_idDirectorDeTrabajo = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, director);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<POJAnteproyecto> anteproyectos = new ArrayList();
                while(resultado.next()){
                    POJAnteproyecto anteproyecto = new POJAnteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(resultado.getString("nombre"));
                    anteproyecto.setIdDirectorDeTrabajo(resultado.getInt("DirectorDeTrabajo_idDirectorDeTrabajo"));
                    anteproyecto.setNombreDirectorDeTrabajo(resultado.getString("nombre_director"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setArchivoAnteproyecto(resultado.getBytes("archivoAnteproyecto"));                 
                    anteproyecto.setNombreLGAC(resultado.getString("lgac_nombre"));
                    anteproyecto.setLGAC(resultado.getInt("LGAC_idLGAC"));
                    anteproyecto.setIdEstado(resultado.getInt("Estado_idEstado"));
                    anteproyecto.setNombreEstado(resultado.getString("Estado_anteproyecto"));
                    anteproyectos.add(anteproyecto);
                    System.out.println("Nombre anteproyecto"+anteproyecto.getNombreAnteproyecto()+"\nestado"+anteproyecto.getNombreEstado());
                }
                respuesta.setAnteproyectos(anteproyectos);
                conexionBD.close();
                
            } catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    

    public static int modificarAnteproyecto(POJAnteproyecto nuevoAnteproyecto) throws SQLException {
        int respuesta=Constantes.OPERACION_EXITOSA;
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
         if(conexionBD != null){
            try{
                String consulta = "UPDATE anteproyecto SET nombre = ?, modalidad = ?, archivoAnteproyecto=?, LGAC_idLGAC = ?, Estado_idEstado=? "+
                        "WHERE idAnteproyecto = ?";
                System.out.println(nuevoAnteproyecto.getNombreAnteproyecto()+"\n"+nuevoAnteproyecto.getModalidad()+"\n"+nuevoAnteproyecto.getIdLGAC()+"\n"+
                        nuevoAnteproyecto.getArchivoAnteproyecto().length+"\n"+nuevoAnteproyecto.getIdEstado()+"\n"+ nuevoAnteproyecto.getIdAnteproyecto());
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nuevoAnteproyecto.getNombreAnteproyecto());
                prepararSentencia.setString(2, nuevoAnteproyecto.getModalidad());
                prepararSentencia.setBytes(3, nuevoAnteproyecto.getArchivoAnteproyecto());
                prepararSentencia.setInt(4,nuevoAnteproyecto.getIdLGAC());
                prepararSentencia.setInt(5, nuevoAnteproyecto.getIdEstado());
                prepararSentencia.setInt(6, nuevoAnteproyecto.getIdAnteproyecto());
                prepararSentencia.executeUpdate();
                conexionBD.close();
            } catch(SQLException e){
               respuesta=Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta=Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    public static int eliminarAnteproyecto(POJAnteproyecto anteproyectoEliminacion) throws SQLException{
        int respuesta=Constantes.OPERACION_EXITOSA;
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "DELETE FROM anteproyecto WHERE idAnteproyecto = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, anteproyectoEliminacion.getIdAnteproyecto());
                prepararSentencia.executeUpdate();
                conexionBD.close();
            }catch(SQLException e){
                respuesta=Constantes.ERROR_CONSULTA;
            }              
        }else{
            respuesta=Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    public static POJAnteproyectoRespuesta obtenerInfoAnteproyectosParaAsignar(int director) throws SQLException{
        POJAnteproyectoRespuesta respuesta = new POJAnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ModConexionBD conexion = new ModConexionBD() ;
        Connection conexionBD = conexion.getConnection();
        if(conexionBD != null){
            try{
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyecto.nombre,"+
                                  " modalidad, archivoAnteproyecto, DirectorDeTrabajo_idDirectorDeTrabajo, usuario.nombre AS nombre_director, LGAC_idLGAC, lgac.nombre AS lgac_nombre,"+
                                  " Estado_idEstado, estado.descripcion AS Estado_anteproyecto " +
                                  "FROM anteproyecto "+
                                  "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC "+
                                  "INNER JOIN directordetrabajo ON anteproyecto.DirectorDeTrabajo_idDirectorDeTrabajo = directordetrabajo.idDirectorDeTrabajo "+
                                  "INNER JOIN usuario ON directordetrabajo.Usuario_idUsuario = usuario.idUsuario "+
                                  "INNER JOIN estado ON anteproyecto.Estado_idEstado = estado.idEstado "+
                                  "WHERE DirectorDeTrabajo_idDirectorDeTrabajo = ? and estado.descripcion='aprobado'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, director);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<POJAnteproyecto> anteproyectos = new ArrayList();
                while(resultado.next()){
                    POJAnteproyecto anteproyecto = new POJAnteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(resultado.getString("nombre"));
                    anteproyecto.setIdDirectorDeTrabajo(resultado.getInt("DirectorDeTrabajo_idDirectorDeTrabajo"));
                    anteproyecto.setNombreDirectorDeTrabajo(resultado.getString("nombre_director"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setArchivoAnteproyecto(resultado.getBytes("archivoAnteproyecto"));                 
                    anteproyecto.setNombreLGAC(resultado.getString("lgac_nombre"));
                    anteproyecto.setLGAC(resultado.getInt("LGAC_idLGAC"));
                    anteproyecto.setIdEstado(resultado.getInt("Estado_idEstado"));
                    anteproyecto.setNombreEstado(resultado.getString("Estado_anteproyecto"));
                    anteproyectos.add(anteproyecto);
                    System.out.println("Nombre anteproyecto"+anteproyecto.getNombreAnteproyecto()+"\nestado"+anteproyecto.getNombreEstado());
                }
                respuesta.setAnteproyectos(anteproyectos);
                conexionBD.close();
                
            } catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
*/
    
    public static POJAnteproyectoRespuesta obtenerAnteproyectosAsignados(int idDirectorDeTrabajo){
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
    
    public static POJUsuario obtenerEstudiantePorIdAnteproyecto(int idAnteproyecto){
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