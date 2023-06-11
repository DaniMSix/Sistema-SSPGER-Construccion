package sistema.spger.modelo.POJO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import sistema.spger.utils.Utilidades;

public class POJAnteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String modalidad;
    private byte[] archivoAnteproyecto;
    private int idDirectorDeTrabajo;
    private int idLGAC;
    private int idEstado;
    private String nombreEstado;
    private String nombreLGAC;
    private String nombreDirectorDeTrabajo;
    private Button boton = new Button("Descargar archivo");

    public POJAnteproyecto() {
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public POJAnteproyecto(int idAnteproyecto, String nombreAnteproyecto, String modalidad, byte[] archivoAnteproyecto, int idDirectorDeTrabajo, int idLGAC, String nombreLGAC, String nombreDirectorDeTrabajo, String estado) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.modalidad = modalidad;
        this.archivoAnteproyecto = archivoAnteproyecto;
        this.idDirectorDeTrabajo = idDirectorDeTrabajo;
        this.idLGAC = idLGAC;
        this.nombreLGAC = nombreLGAC;
        this.nombreDirectorDeTrabajo = nombreDirectorDeTrabajo;
        this.nombreEstado = estado;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public void setNombreLGAC(String nombreLGAC) {
        this.nombreLGAC = nombreLGAC;
    }

    public void setNombreDirectorDeTrabajo(String nombreDirectorDeTrabajo) {
        this.nombreDirectorDeTrabajo = nombreDirectorDeTrabajo;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public String getNombreLGAC() {
        return nombreLGAC;
    }

    public String getNombreDirectorDeTrabajo() {
        return nombreDirectorDeTrabajo;
    }
    

    public Button getBoton() {
        return boton;
    }

    public void setBoton(Button boton) {
        this.boton = boton;
    }

    /*
    public POJAnteproyecto() {
        boton.setOnMouseReleased(new EventHandler() {
	@Override
            public void handle(Event arg0) {
                System.out.println("Click en boton de "+nombreAnteproyecto);
            try {
                Utilidades.descargarPDF(nombreAnteproyecto,archivoAnteproyecto);
            } catch (IOException ex) {
                Logger.getLogger(POJAnteproyecto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
	});
    }
    */

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public void setNombreAnteproyecto(String nombreAnteproyecto) {
        this.nombreAnteproyecto = nombreAnteproyecto;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setArchivoAnteproyecto(byte[] archivoAnteproyecto) {
        this.archivoAnteproyecto = archivoAnteproyecto;
    }

    public void setIdDirectorDeTrabajo(int nombreDirectorDeTrabajo) {
        this.idDirectorDeTrabajo = nombreDirectorDeTrabajo;
    }

    public void setLGAC(int LGAC) {
        this.idLGAC = LGAC;
    }

    public void setNombreEstado(String estado) {
        this.nombreEstado = estado;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public String getNombreAnteproyecto() {
        return nombreAnteproyecto;
    }

    public String getModalidad() {
        return modalidad;
    }

    public byte[] getArchivoAnteproyecto() {
        return archivoAnteproyecto;
    }

    public int getIdDirectorDeTrabajo() {
        return idDirectorDeTrabajo;
    }

    public int getLGAC() {
        return idLGAC;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public POJAnteproyecto(String nombreAnteproyecto, String modalidad, byte[] archivoAnteproyecto, int idDirectorDeTrabajo, int LGAC) {
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.modalidad = modalidad;
        this.archivoAnteproyecto = archivoAnteproyecto;
        this.idDirectorDeTrabajo = idDirectorDeTrabajo;
        this.idLGAC = LGAC;
    }
    
    
}