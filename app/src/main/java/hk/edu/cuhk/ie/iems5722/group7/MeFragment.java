package hk.edu.cuhk.ie.iems5722.group7;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import hk.edu.cuhk.ie.iems5722.group7.authentication.LoginActivity;
import hk.edu.cuhk.ie.iems5722.group7.chatview.ChatActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RequestQueue userInfoQueue;
    private static final String ARG_PARAM1 = "user_id";
    private static final String ARG_PARAM2 = "user_name";
    private static final String ARG_PARAM3 = "age";
    private static final String ARG_PARAM4 = "email";
    private static final String ARG_PARAM5 = "uid";

    private String TAG = "MeFragment";

    // get information of current user

    // TODO: Rename and change types of parameters
    private int user_id;
    private String user_name;
    private String age;
    private String email;
    private String uid;
    private Button logout;
    private TextView userID;
    private TextView userName;
    private TextView userAge;
    private TextView userEmail;


    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_id Parameter 1.
     * @param user_name Parameter 2.
     * @param age Parameter 3.
     * @param email Parameter 4.
     * @param uid Parameter 5.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    // get userID from mainActivity
    public static MeFragment newInstance(int user_id, String user_name, String age, String email, String uid) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, user_id);
        args.putString(ARG_PARAM2, user_name);
        args.putString(ARG_PARAM3, age);
        args.putString(ARG_PARAM4, email);
        args.putString(ARG_PARAM5, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            user_id = getArguments().getInt(ARG_PARAM1);
            user_name = getArguments().getString(ARG_PARAM2);
            age = getArguments().getString(ARG_PARAM3);
            email = getArguments().getString(ARG_PARAM4);
            uid = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_me, container, false);
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        initView(view);
        // Inflate the layout for this fragment
        return view;
    }
    private void initView(View view){

        logout = view.findViewById(R.id.signout_button);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.signout_button:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        userID = view.findViewById(R.id.profile_ID);
        userName = view.findViewById(R.id.profile_Name);
        userAge = view.findViewById(R.id.profile_Age);
        userEmail = view.findViewById(R.id.profile_email);


        // set textview
        userID.setText(Integer.toString(user_id));
        userName.setText(user_name);
        userAge.setText(age);
        userEmail.setText(email);
        getUserInfo(uid);

    }


    public void getUserInfo(String uid){
        String urlGetUserInfo = String.format(getString(R.string.API_get_userInfo), uid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlGetUserInfo, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("OK")){
//                                Log.d(TAG,response.toString());
                                JSONArray jsonArray=response.getJSONArray("data");
                                Log.e(TAG, "get the data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    userID.setText(jsonObject.getString("user_id"));
                                    userName.setText(jsonObject.getString("user_name"));
                                    userAge.setText(jsonObject.getString("age"));
                                    userEmail.setText(jsonObject.getString("email"));
                                    Log.e(TAG, "set the informations");
//                                    userInfo.put("user_id",jsonObject.getString("user_id"));
//                                    userInfo.put("user_name",jsonObject.getString("user_name"));
//                                    userInfo.put("age",jsonObject.getString("age"));
//                                    userInfo.put("email",jsonObject.getString("email"));
//                                    userInfos.add(userInfo);
                                }
                            }
                            else {
                                Log.e(TAG,response.toString());
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
        VolleyController.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}