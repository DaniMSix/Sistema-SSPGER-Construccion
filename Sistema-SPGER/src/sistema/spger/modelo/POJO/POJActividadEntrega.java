package sistema.spger.modelo.POJO;


public class POJActividadEntrega {
    private int idCurso;
    private int idActividad;
    private String nombre;
    private String descripcion;
    private String estado;
    private String fechaLimiteEntrega;
    private float calificacion;
    private String observacionProfesor;
    private String comentariosAlumno;
    private String fechaEntrega;
    private int Estudiante_idUsuario;
    private String fechaCreacion;
    private int idEntrega;
    private int idUsuario;
    private int codigoRespuesta;
    
    

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaLimiteEntrega() {
        return fechaLimiteEntrega;
    }

    public void setFechaLimiteEntrega(String fechaLimiteEntrega) {
        this.fechaLimiteEntrega = fechaLimiteEntrega;
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

    public int getEstudiante_idUsuario() {
        return Estudiante_idUsuario;
    }

    public void setEstudiante_idUsuario(int Estudiante_idUsuario) {
        this.Estudiante_idUsuario = Estudiante_idUsuario;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
