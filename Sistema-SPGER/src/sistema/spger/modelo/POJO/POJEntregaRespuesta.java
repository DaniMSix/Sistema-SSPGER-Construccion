package sistema.spger.modelo.POJO;

import java.util.List;

public class POJEntregaRespuesta {
    private int codigoRespuesta;
    private List<POJActividadEntrega> entregas;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public List<POJActividadEntrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<POJActividadEntrega> entregas) {
        this.entregas = entregas;
    }
}
