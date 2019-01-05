package mobile.una.com.flowledge.model;


import java.io.Serializable;

public class Sesion implements Serializable {
    private   String pid;
    private   String nombre;
    private   String estado;



    public Sesion(){
    }

    public Sesion(String pid, String nombre, String estado) {
        this.pid = pid;
        this.nombre = nombre;
        this.estado = estado;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return pid;
    }
}


