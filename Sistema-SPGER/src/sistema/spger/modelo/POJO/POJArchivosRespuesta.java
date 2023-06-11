package sistema.spger.modelo.POJO;

import java.util.List;

public class POJArchivosRespuesta {
    private int codigoRespuesta;
    private List<POJArchivos> archivosEntrega;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public List<POJArchivos> getArchivosEntrega() {
        return archivosEntrega;
    }

    public void setArchivosEntrega(List<POJArchivos> archivosEntrega) {
        this.archivosEntrega = archivosEntrega;
    }
}
