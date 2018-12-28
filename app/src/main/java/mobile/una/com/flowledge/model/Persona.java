package mobile.una.com.flowledge.model;

public class Persona {
    private   String pid;
    private   String nombre;


    public Persona() {
    }

    public Persona(String nombre) {
        //this.pid = pid;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre;
    }
}


