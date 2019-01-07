package mobile.una.com.flowledge.model;

import java.util.List;

/**
 * Created by Luis Bogantes on 06/01/2019.
 */

public class Question {
    String userNickname;
    String category;
    String description;
    List<Respuesta> respuesta;


    //------------------------------------------------------------------------


    public Question(String userNickname, String category, String description, List<Respuesta> respuesta) {
        this.userNickname = userNickname;
        this.category = category;
        this.description = description;
        this.respuesta = respuesta;
    }

    public Question() {
        //this.Pid= "";
        this.userNickname = "";
        this.category = "";
        this.description = "";

    }


    public List<Respuesta> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(List<Respuesta> respuesta) {
        this.respuesta = respuesta;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
