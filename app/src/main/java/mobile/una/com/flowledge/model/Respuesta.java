package mobile.una.com.flowledge.model;

public class Respuesta {
    String respuesta;


    public Respuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Respuesta() {
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return  respuesta;
    }
}
