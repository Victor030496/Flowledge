package mobile.una.com.flowledge.model;

/**
 * Created by Luis Bogantes on 06/01/2019.
 */

public class Question {

    String userNickname;
    String category;
    String description;


    //------------------------------------------------------------------------

    public Question(String userNickname, String category, String description) {
        this.userNickname = userNickname;
        this.category = category;
        this.description = description;
    }

    public Question() {
        this.userNickname = "";
        this.category = "";
        this.description = "";

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
