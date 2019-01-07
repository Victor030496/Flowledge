package mobile.una.com.flowledge.model;

/**
 * Created by Luis Bogantes on 06/01/2019.
 */

public class Question {
    String Pid;
    String userNickname;
    String category;
    String description;


    //------------------------------------------------------------------------

    public Question(String pid,String userNickname, String category, String description) {
        this.Pid= pid;
        this.userNickname = userNickname;
        this.category = category;
        this.description = description;
    }

    public Question() {
        this.Pid= "";
        this.userNickname = "";
        this.category = "";
        this.description = "";

    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
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

    @Override
    public String toString() {
        return Pid;
    }
}
