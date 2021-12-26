package hk.edu.cuhk.ie.iems5722.group7;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private static final String ARG_PARAM1 = "user_id";
    private static final String ARG_PARAM2 = "user_name";
    private static final String ARG_PARAM3 = "age";
    private static final String ARG_PARAM4 = "email";

    // get information of current user

    // TODO: Rename and change types of parameters
    private int user_id;
    private String user_name;
    private String age;
    private String email;
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
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    // get userID from mainActivity
    public static MeFragment newInstance(int user_id, String user_name, String age, String email) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, user_id);
        args.putString(ARG_PARAM2, user_name);
        args.putString(ARG_PARAM3, age);
        args.putString(ARG_PARAM4, email);
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
    }

}