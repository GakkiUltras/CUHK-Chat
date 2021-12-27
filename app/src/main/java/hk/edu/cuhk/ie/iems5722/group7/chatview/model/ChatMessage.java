package hk.edu.cuhk.ie.iems5722.group7.chatview.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    //发送类型
    public static final int SEND = 1;
    //接收类型
    public static final int RECEIVE = 0;
    //类型总数
    public static final int TYPE_COUNT = 2;
    private int profilePic;
    private String content;
    private String name;
    private Date date;
    private int userID;
    public String timeFormat="yyyy-MM-dd HH:mm";



    public ChatMessage(String content,String name, Date date, int userID) {
        this.content = content;
        this.name=name;
        this.date = date;
        this.userID = userID;
    }

    //    public ChatMessage(String content,String name,String time,int user_id){
//        this.content=content;
//        this.name=name;
//        SimpleDateFormat ft=new SimpleDateFormat();
//        try {
//            this.date=ft.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        this.userID=user_id;
//    }
    public ChatMessage(JSONObject jsonObject) {
        try {
            this.content = jsonObject.getString("message");
            this.name = jsonObject.getString("name");
            this.date = parseStringToTime(jsonObject.getString("message_time"));
            this.userID = jsonObject.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Date parseStringToTime(String time) {
        SimpleDateFormat ft = new SimpleDateFormat(this.timeFormat);
        Date date = new Date();
        try {
            date = ft.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return date;
        }
    }

    public String parseTimeToString(String format){
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(this.date);
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getType(int localID) {
        return localID==this.userID?ChatMessage.SEND:ChatMessage.RECEIVE;
    }

    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }
}
