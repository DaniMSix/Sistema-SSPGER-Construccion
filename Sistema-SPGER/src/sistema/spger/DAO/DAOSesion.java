package sistema.spger.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJUsuario;
import sistema.spger.utils.Constantes;

public class DAOSesion {

    public static POJUsuario verificarSesionUsuario(String correoUsuario, String contrasenia) throws SQLException {
        POJUsuario usuarioVerificado = new POJUsuario();
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = null;

        try {
            conexion = abrirConexion.getConnection();

            if (conexion != null) {
                String consulta = "SELECT * FROM usuario WHERE correo = ? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, correoUsuario);
                prepararSentencia.setString(2, contrasenia);
                ResultSet resultado = prepararSentencia.executeQuery();

                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

                if (resultado.next()) {
                    int idUsuario = resultado.getInt("idUsuario");
                    String correo = resultado.getString("correo");
                    String password = resultado.getString("password");
                    String nombre = resultado.getString("nombre");
                    String apellidoPaterno = resultado.getString("apellidoPaterno");
                    String apellidoMaterno = resultado.getString("apellidoMaterno");

                    usuarioVerificado.setIdUsuario(idUsuario);
                    usuarioVerificado.setCorreo(correo);
                    usuarioVerificado.setContrasenia(password);
                    usuarioVerificado.setNombre(nombre);
                    usuarioVerificado.setApellidoPaterno(apellidoPaterno);
                    usuarioVerificado.setApellidoMaterno(apellidoMaterno);
                }
                abrirConexion.closeConection();
            } else {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
            }
        } catch (SQLException ex) {
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        } 
        return usuarioVerificado;
    }

}
