package hk.edu.cuhk.ie.iems5722.group7.authentication.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Objects;

@IgnoreExtraProperties
public class UserValidate implements Serializable{
    public String username, userID, age, email;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID= userID;}

    public String getAge() {
        return age;
    }
    public void setAge(String age) { this.age= age;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email= email;}


    public UserValidate(){

    }
    public UserValidate(String username, String userID, String age, String email){
        this.username = username;
        this.userID = userID;
        this.age = age;
        this.email= email;
    }
}
