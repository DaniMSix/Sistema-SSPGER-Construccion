package sistema.spger.modelo.POJO;

import java.util.ArrayList;


public class POJAnteproyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList<POJAnteproyecto> anteproyectos;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJAnteproyecto> getAnteproyectos() {
        return anteproyectos;
    }

    public void setAnteproyectos(ArrayList<POJAnteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }
    
    
}
