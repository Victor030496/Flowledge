package mobile.una.com.flowledge.model;

public class Persona {
    private String pid;
    private String nombre;
    private String correo;
    private String contra;
    private String rol;
    private String imageURL;

    public Persona() {
        this.pid = "";
        this.nombre = "";
        this.correo = "";
        this.contra = "";
        this.rol = "";
        this.imageURL = "default";
    }

   /* public Persona(String pid, String nombre, String correo, String contra) {
        this.pid = pid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
    }


    public Persona(String pid, String nombre, String correo, String contra, String rol) {
        this.pid = pid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.rol = rol;
    }*/

    public Persona(String pid, String nombre, String correo, String contra, String rol, String imageURL) {
        this.pid = pid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.rol = rol;
        this.imageURL = imageURL;
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


    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return pid;
    }
}


