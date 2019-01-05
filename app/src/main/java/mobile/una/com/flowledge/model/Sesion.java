package mobile.una.com.flowledge.model;

import com.orm.SugarRecord;

public class Sesion extends SugarRecord{
    private   String pid;
    private   String nombre;
    private   String correo;
    private   String contra;



    public Sesion() {
    }

    public Sesion(String pid, String nombre, String correo, String contra) {
        this.pid = pid;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
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


    @Override
    public String toString() {
        return pid;
    }
}


