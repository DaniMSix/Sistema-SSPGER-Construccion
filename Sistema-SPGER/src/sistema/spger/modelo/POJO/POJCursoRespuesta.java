package sistema.spger.modelo.POJO;

import java.util.ArrayList;

public class POJCursoRespuesta {
    private boolean cursoDuplicado;
    private int codigoRespuesta;
    private ArrayList<POJCurso> cursos;

    public POJCursoRespuesta() {
        
    }

    public boolean isCursoDuplicado() {
        return cursoDuplicado;
    }

    public void setCursoDuplicado(boolean cursoDuplicado) {
        this.cursoDuplicado = cursoDuplicado;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<POJCurso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<POJCurso> cursos) {
        this.cursos = cursos;
    }

    public POJCursoRespuesta(boolean cursoDuplicado, int codigoRespuesta, ArrayList<POJCurso> cursos) {
        this.cursoDuplicado = cursoDuplicado;
        this.codigoRespuesta = codigoRespuesta;
        this.cursos = cursos;
    }
}
