package sistema.spger.modelo.POJO;

public class POJLgac {
    private int idLgac;
    private String nombre;
    private String descripcion;

    public POJLgac() {
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public POJLgac(int idLgac, String nombre, String descripcion) {
        this.idLgac = idLgac;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
