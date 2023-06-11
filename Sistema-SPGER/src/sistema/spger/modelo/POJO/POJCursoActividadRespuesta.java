package sistema.spger.modelo.POJO;

import java.util.List;


public class POJCursoActividadRespuesta {
    private int codigoRespuesta;
    private List<POJCursoActividad> listaIdActividades;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public List<POJCursoActividad> getListaIdActividades() {
        return listaIdActividades;
    }

    public void setListaIdActividades(List<POJCursoActividad> listaIdActividades) {
        this.listaIdActividades = listaIdActividades;
    }
    
    
}
