package hk.edu.cuhk.ie.iems5722.group7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }
}