package mobile.una.com.flowledge.model;

import java.io.Serializable;

public class Respuesta implements Serializable {
    private String respuesta;
    private String nickRespuesta;
    private String pregunta;
    private int likes;
    private String imagen;

    public Respuesta(String respuesta, String nickRespuesta, String pregunta, int likes) {
        this.respuesta = respuesta;
        this.nickRespuesta = nickRespuesta;
        this.pregunta = pregunta;
        this.likes = likes;
    }

    public Respuesta() {
        this.respuesta = "";
        this.nickRespuesta = "";
        this.pregunta = "";
        this.likes = 0;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getNickRespuesta() {
        return nickRespuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public int getLikes() {
        return likes;
    }

    public String getImagen() {
        return imagen;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setNickRespuesta(String nickRespuesta) {
        this.nickRespuesta = nickRespuesta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
