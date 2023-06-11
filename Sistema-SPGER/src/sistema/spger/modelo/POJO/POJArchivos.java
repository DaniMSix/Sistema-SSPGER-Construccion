package sistema.spger.modelo.POJO;

import java.io.File;


public class POJArchivos {
    private int idArchivos;
    private String nombreArchivo;
    private File archivosEntrega;
    private int entrega_idEntrega;

    public int getIdArchivos() {
        return idArchivos;
    }

    public void setIdArchivos(int idArchivos) {
        this.idArchivos = idArchivos;
    }
    
    public File getArchivosEntrega() {
        return archivosEntrega;
    }

    public void setArchivosEntrega(File archivosEntrega) {
        this.archivosEntrega = archivosEntrega;
    }

    public int getEntrega_idEntrega() {
        return entrega_idEntrega;
    }

    public void setEntrega_idEntrega(int entrega_idEntrega) {
        this.entrega_idEntrega = entrega_idEntrega;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
