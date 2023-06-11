package sistema.spger.modelo.POJO;


public class POJEntrega {
    private int idEntrega;
    private float calificacion;
    private String observacionProfesor;
    private String comentariosAlumno;
    private String fechaEntrega;
    private int Actividad_idActividad;
    private int codigoRespuesta;
    
    

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public String getObservacionProfesor() {
        return observacionProfesor;
    }

    public void setObservacionProfesor(String observacionProfesor) {
        this.observacionProfesor = observacionProfesor;
    }

    public String getComentariosAlumno() {
        return comentariosAlumno;
    }

    public void setComentariosAlumno(String comentariosAlumno) {
        this.comentariosAlumno = comentariosAlumno;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getActividad_idActividad() {
        return Actividad_idActividad;
    }

    public void setActividad_idActividad(int Actividad_idActividad) {
        this.Actividad_idActividad = Actividad_idActividad;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }
    
    

}
