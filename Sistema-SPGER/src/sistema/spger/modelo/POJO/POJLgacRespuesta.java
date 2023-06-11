package sistema.spger.modelo.POJO;


public class POJLgacRespuesta {
    
    private boolean lgacDuplicado;
    private int codigoRespuesta;

    public POJLgacRespuesta(boolean lgacDuplicado, int codigoRespuesta) {
        this.lgacDuplicado = lgacDuplicado;
        this.codigoRespuesta = codigoRespuesta;
    }

    public POJLgacRespuesta() {
    }

    public boolean isLgacDuplicado() {
        return lgacDuplicado;
    }

    public void setLgacDuplicado(boolean lgacDuplicado) {
        this.lgacDuplicado = lgacDuplicado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
