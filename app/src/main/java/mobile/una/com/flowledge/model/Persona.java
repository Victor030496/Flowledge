package mobile.una.com.flowledge.model;

public class Persona {
    private   String pid;
    private   String nombre;
    private   String correo;
    private   String contra;
    private   String cocontra;


    public Persona() {
    }

    public Persona(String pid, String nombre, String correo, String contra, String cocontra) {
        this.pid = pid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.cocontra = cocontra;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCocontra() {
        return cocontra;
    }

    public void setCocontra(String cocontra) {
        this.cocontra = cocontra;
    }

    @Override
    public String toString() {
        return pid;
    }
}


