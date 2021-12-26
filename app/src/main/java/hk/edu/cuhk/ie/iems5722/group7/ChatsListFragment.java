package hk.edu.cuhk.ie.iems5722.group7;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "ChatListFragment";

    ArrayList<HashMap<String,Object>> chatrooms;
    SimpleAdapter chatroomsAdapter;
    ListView listView;

    public ChatsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment ChatsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatsListFragment newInstance() { //String param1, String param2
        ChatsListFragment fragment = new ChatsListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chats_list, container, false);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        initView(view);
        chatrooms=new ArrayList<HashMap<String,Object>>();
        //Set ItemClickListener to open ChatActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",(int)chatrooms.get(position).get("id"));
                bundle.putString("name",(String) chatrooms.get(position).get("name"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getChatrooms();
        return view;
    }
    private void initView(View view){
        listView=view.findViewById(R.id.chatrooms);
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
                                chatroomsAdapter=new SimpleAdapter(getActivity(),chatrooms,R.layout.item_chatroom,new String[]{"name"},new int[]{R.id.room_name});
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
        VolleyController.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }
}