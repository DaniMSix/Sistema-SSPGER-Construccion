package sistema.spger.modelo.POJO;

import java.util.ArrayList;


public class POJActividadRespuesta {
    
    private boolean actividadDuplicada;
    private int codigoRespuesta;
    private ArrayList<POJActividad> actividades;

    public boolean isActividadDuplicada() {
        return actividadDuplicada;
    }

    public void setActividadDuplicada(boolean actividadDuplicada) {
        this.actividadDuplicada = actividadDuplicada;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJActividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<POJActividad> actividades) {
        this.actividades = actividades;
    }
    
    

}
