package sistema.spger.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistema.spger.modelo.ModConexionBD;
import sistema.spger.modelo.POJO.POJCursoActividad;
import sistema.spger.modelo.POJO.POJCursoActividadRespuesta;
import sistema.spger.utils.Constantes;


public class DAOCursoActividad {
    
    public static POJCursoActividadRespuesta obtenerListaDeIdDeActividades(int idCurso) throws SQLException{
        POJCursoActividadRespuesta respuestaBD = new POJCursoActividadRespuesta();
        ArrayList<POJCursoActividad> actividadConsulta = new ArrayList();
        
        ModConexionBD abrirConexion = new ModConexionBD();
        Connection conexion = abrirConexion.getConnection();
        
        if(conexion != null){
            try{
                String consulta = "SELECT idActividad FROM Curso_Actividad WHERE idCurso = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setInt(1, idCurso);
                ResultSet resultado =  prepararSentencia.executeQuery();
                respuestaBD.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                while(resultado.next()){
                    POJCursoActividad actividadEntrega = new POJCursoActividad();
                    actividadEntrega.setIdActividad(resultado.getInt("idActividad"));
                    actividadConsulta.add(actividadEntrega);
                }
                respuestaBD.setListaIdActividades(actividadConsulta);
            }catch (SQLException e){
               respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuestaBD.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuestaBD;
    }


}
