package sistema.spger.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJArchivos;
import sistema.spger.modelo.POJO.POJArchivosRespuesta;
import sistema.spger.modelo.POJO.POJEntrega;
import sistema.spger.utils.Constantes;


public class DAOEntrega {
    
    public static int registrarArchivosEntrega(POJArchivosRespuesta archivosInformacion) throws SQLException {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "INSERT INTO archivos (archivos, nombreArchivo, Entrega_idEntrega) VALUES (?,?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                for (POJArchivos archivoEntrega : archivosInformacion.getArchivosEntrega()) {
                    File archivo = archivoEntrega.getArchivosEntrega();
                    String nombreArchivo = archivoEntrega.getArchivosEntrega().getName();
                    byte[] contenidoArchivo = Files.readAllBytes(archivo.toPath());
                    
                    prepararSentencia.setBytes(1, contenidoArchivo);
                    prepararSentencia.setString(2, nombreArchivo);
                    prepararSentencia.setInt(3, archivoEntrega.getEntrega_idEntrega());
                    prepararSentencia.executeUpdate();
                }

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException | IOException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }

    public static int registrarEntrega(POJEntrega entregaInformacion) throws SQLException {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "INSERT INTO entrega (comentariosAlumno, fechaEntrega, Actividad_idActividad) "
                        + "VALUES (?, ?, ?);";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                prepararSentencia.setString(1, entregaInformacion.getComentariosAlumno());
                prepararSentencia.setString(2, entregaInformacion.getFechaEntrega());
                prepararSentencia.setInt(3, entregaInformacion.getActividad_idActividad());
                prepararSentencia.executeUpdate();

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }
    
    public static int actualizarEstadoActividad(String estadoActividad, int idActividad) throws SQLException {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "UPDATE actividad SET estado = ? WHERE idActividad = ?;";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                    prepararSentencia.setString(1, estadoActividad);
                    prepararSentencia.setInt(2, idActividad);
                    prepararSentencia.executeUpdate();

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }
    
    public static int actualizarComentarioProfesor(POJEntrega entregaNuevaInformacion) throws SQLException {
        int codigoRespuesta;

        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();

        if (conexion != null) {
            try {
                String consulta = "UPDATE entrega SET comentariosAlumno = ? WHERE Actividad_idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                    prepararSentencia.setString(1, entregaNuevaInformacion.getComentariosAlumno());
                    prepararSentencia.setInt(2, entregaNuevaInformacion.getActividad_idActividad());
                    System.out.println("idActividad " +  entregaNuevaInformacion.getActividad_idActividad());
                    prepararSentencia.executeUpdate();

                codigoRespuesta = Constantes.OPERACION_EXITOSA;
            } catch (SQLException e) {
                codigoRespuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            codigoRespuesta = Constantes.ERROR_CONEXION;
        }

        return codigoRespuesta;
    }

    public static POJEntrega obtenerIdEntrega(POJEntrega entregaRegistrada) throws SQLException{
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        POJEntrega respuestaBD = new POJEntrega();
        
        if (conexion != null){
            try{
                String consulta = "SELECT idEntrega FROM entrega WHERE Actividad_idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, entregaRegistrada.getActividad_idActividad());
                ResultSet resultado = prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    int idEntrega = resultado.getInt("idEntrega");
                    respuestaBD.setIdEntrega(idEntrega);
                }
            } catch (SQLException ex) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }
    
    public static POJArchivosRespuesta obtenerArchivosEntregaPorId(int idEntrega) throws SQLException{
        POJArchivosRespuesta respuestaBD = new POJArchivosRespuesta();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        ArrayList<POJArchivos> archivos = new ArrayList();

        if (conexion != null) {
            try {
                String consulta = "SELECT nombreArchivo, archivos FROM archivos WHERE Entrega_idEntrega = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEntrega);
                ResultSet respuestaArchivosBD = prepararSentencia.executeQuery();
                
                while (respuestaArchivosBD.next()) {
                    byte[] contenidoArchivo = respuestaArchivosBD.getBytes("archivos");
                    String nombreArchivo = respuestaArchivosBD.getString("nombreArchivo");

                    // Crear un objeto File sin especificar la ruta
                    File archivo = new File(nombreArchivo);

                    // Guardar el contenido del archivo en disco
                    FileOutputStream outputStream = new FileOutputStream(archivo);
                    outputStream.write(contenidoArchivo);
                    outputStream.close();

                    // Crear un objeto POJArchivos y agregarlo a la lista
                    POJArchivos archivoEntrega = new POJArchivos();
                    //archivoEntrega.setNombreArchivo(nombreArchivo);
                    archivoEntrega.setArchivosEntrega(archivo);
                    archivos.add(archivoEntrega);                    
                }
                
                respuestaBD.setArchivosEntrega(archivos);
                
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch (SQLException | IOException e) {
                respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }

        return respuestaBD;
    }
    
    public static int registrarCalificacion(POJEntrega entregaARegistrar) throws SQLException {
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        int codigoRespuesta;
        if (conexion != null) {
            try {
                String consulta = "UPDATE entrega SET calificacion = ?, observacionProfesor = ? WHERE Actividad_idActividad = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setFloat(1, entregaARegistrar.getCalificacion());
                prepararSentencia.setString(2, entregaARegistrar.getObservacionProfesor());
                prepararSentencia.setInt(3, entregaARegistrar.getActividad_idActividad());
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

}
