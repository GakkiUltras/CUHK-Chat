package hk.edu.cuhk.ie.iems5722.group7.authentication.model;

public class UserValidate {
    public String username, userID, age, email;
    public UserValidate(){

    }
    public UserValidate(String username, String userID, String age, String email){
        this.username = username;
        this.userID = userID;
        this.age = age;
        this.email= email;
    }
}
