package hk.edu.cuhk.ie.iems5722.group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.cuhk.ie.iems5722.group7.chatview.ChatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ArrayList<HashMap<String,Object>> chatrooms;
    SimpleAdapter chatroomsAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.chatrooms);
        chatrooms=new ArrayList<HashMap<String,Object>>();
        //Set ItemClickListener to open ChatActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",(int)chatrooms.get(position).get("id"));
                bundle.putString("name",(String) chatrooms.get(position).get("name"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getChatrooms();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getChatrooms(){
        String urlGetChatrooms = getString(R.string.API_get_chatrooms);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlGetChatrooms, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        textView.setText("Response: " + response.toString());
                        try {
                            if (response.getString("status").equals("OK")){
//                                Log.d(TAG,response.toString());
                                JSONArray jsonArray=response.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    HashMap<String,Object> chatroom=new HashMap<String,Object>();
                                    chatroom.put("id",jsonObject.getInt("id"));
                                    chatroom.put("name",jsonObject.getString("name"));
                                    chatrooms.add(chatroom);
                                }
                                //Set SimpleAdapter to return item view to ListView
                                chatroomsAdapter=new SimpleAdapter(MainActivity.this,chatrooms,R.layout.item_chatroom,new String[]{"name"},new int[]{R.id.room_name});
                                listView.setAdapter(chatroomsAdapter);
                            }
                            else {
                                Log.d(TAG,response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e(TAG,error.getMessage(),error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void openChatroom(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }
}