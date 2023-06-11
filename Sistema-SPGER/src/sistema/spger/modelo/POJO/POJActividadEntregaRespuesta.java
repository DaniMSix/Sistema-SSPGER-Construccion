package sistema.spger.modelo.POJO;

import java.util.ArrayList;


public class POJActividadEntregaRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<POJActividadEntrega> actividadesEntregas;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJActividadEntrega> getActividadesEntregas() {
        return actividadesEntregas;
    }

    public void setActividadesEntregas(ArrayList<POJActividadEntrega> actividadesEntregas) {
        this.actividadesEntregas = actividadesEntregas;
    }
    
    

}
