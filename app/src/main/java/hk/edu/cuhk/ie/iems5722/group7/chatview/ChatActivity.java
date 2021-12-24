package hk.edu.cuhk.ie.iems5722.group7.chatview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import hk.edu.cuhk.ie.iems5722.group7.CustomRequest;
import hk.edu.cuhk.ie.iems5722.group7.R;
import hk.edu.cuhk.ie.iems5722.group7.VolleyController;
import hk.edu.cuhk.ie.iems5722.group7.chatview.model.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private ArrayList<ChatMessage> messages;
    private ChatAdapter adapter;
    private ListView listView;
    private EditText editText;
    private int roomID;
    private int totalPages;
    private int currentPage;
    private String urlGetMessages;
    private String urlPostMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.roomID = bundle.getInt("id");
        String roomName = bundle.getString("name");
        this.setTitle(roomName);
//        this.urlGetMessages = "http://18.217.125.61/api/a3/get_messages?chatroom_id=%d&page=%d";
        this.urlGetMessages = getString(R.string.API_get_messages);
//        this.urlPostMessage ="http://18.217.125.61/api/a3/send_message";
        this.urlPostMessage = getString(R.string.API_send_messages);

        this.messages = new ArrayList<ChatMessage>();
        this.adapter = new ChatAdapter(this, messages);
        this.editText = (EditText) findViewById(R.id.editTextMessage);
        this.listView = (ListView) findViewById(R.id.messages);
        this.listView.setAdapter(adapter);
        this.currentPage = 0;
        this.totalPages = 0;
        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView viewint, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Your code here
//                if (listView.getFirstVisiblePosition()==0&&currentPage<totalPages){
//                    getMessages(url,roomID,currentPage+1);
//                }
//                else if(firstVisibleItem==0&&visibleItemCount==totalItemCount){
//
//                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getFirstVisiblePosition() == 0 && currentPage < totalPages) {
                        getMessages(urlGetMessages, roomID, currentPage + 1);
                    }
                }
            }
        });

        getMessages(this.urlGetMessages, roomID, 1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();//实例化对象
        inflater.inflate(R.menu.chat, menu);//解析菜单资源文件
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                getMessages(this.urlGetMessages, this.roomID, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getMessages(String url, int roomID, int page) {
        url = String.format(url, roomID, page);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("OK")) {
                                JSONObject jsonData = response.getJSONObject("data");
                                JSONArray jsonMessages = jsonData.getJSONArray("messages");
                                if (page == 1) {
                                    if (adapter == null) {
                                        adapter = new ChatAdapter(ChatActivity.this, messages);
                                    } else {
                                        if (adapter.isEmpty()) {

                                        } else {
                                            adapter.clear();
                                        }
                                    }
//                                    totalPages=jsonData.getInt("total_pages");
                                } else {

                                }
                                for (int i = 0; i < jsonMessages.length(); i++) {
                                    JSONObject jsonMessage = jsonMessages.getJSONObject(i);
//                                    adapter.insert(new ChatMessage(jsonMessage),0);
                                    messages.add(0, new ChatMessage(jsonMessage));
                                }
                                //notify the adapter that the data set has been changed
                                updateData();
                                // set selection of listview according to page
                                if (page == 1) {
                                    //listview scrolls to the last item
                                    listView.setSelection(adapter.getCount() - 1);
                                } else {
                                    //listview scrolls to previous position
                                    listView.setSelection(jsonMessages.length());
                                }
                                //update the page data
                                currentPage = jsonData.getInt("current_page");
                                totalPages = jsonData.getInt("total_pages");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e(TAG, error.getMessage(), error);
                    }
                });
        VolleyController.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void postMessage(String url, @NonNull ChatMessage message) {
//        url = String.format(url, roomID, page);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("chatroom_id", Integer.toString(roomID));
        params.put("user_id", Integer.toString(message.getUserID()));
        params.put("name", message.getName());
        params.put("message", message.getContent());

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (response.getString("status").equals("OK")) {
//                        adapter.add(message);
//                        listView.setSelection(adapter.getCount() - 1);
//                    }
//                    else {
//                        Log.d(TAG,"status==Error");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, error.getMessage(), error);
//            }
//        });
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("OK")) {
                        adapter.add(message);
                        listView.setSelection(adapter.getCount() - 1);
                    } else {
                        Log.d(TAG, "status==Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    //notify the adapter that the data set has been changed and change the item views in the listview
    public void updateData() {
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        } else {
            this.adapter = new ChatAdapter(this, messages);
            this.listView.setAdapter(adapter);
        }
    }


    public void sendMessage(View view) {
        String content = editText.getText().toString();
        if (!content.isEmpty()) {
//            adapter.add(new ChatMessage(content,"bot",new Date(),23132));
            postMessage(this.urlPostMessage, new ChatMessage(content, "bot", new Date(), 1155161730));
            editText.setText("");
//            adapter.add(new ChatMessage(content,new Date(),ChatMessage.RECEIVE));
        }
    }

}