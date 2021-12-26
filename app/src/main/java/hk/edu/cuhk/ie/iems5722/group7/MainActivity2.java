package hk.edu.cuhk.ie.iems5722.group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import hk.edu.cuhk.ie.iems5722.group7.authentication.LoginActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mauth;
    DatabaseReference mDatabaseReference;
    ArrayList<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //init FirebaseAuth
        mauth=FirebaseAuth.getInstance();

        fragments = new ArrayList<>();
        fragments.add(ChatsListFragment.newInstance());
        fragments.add(FriendsListFragment.newInstance("",""));
        fragments.add(MeFragment.newInstance("",""));
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, fragments.get(0), "CHATS")
                .add(R.id.fragment_container, fragments.get(1), "FRIENDS")
                .add(R.id.fragment_container, fragments.get(2), "ME")
                .commit();

        //Show chats page by default
        fm.beginTransaction()
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .commit();

        //Config the bottom navigation,set a listener on it
        navigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_chats:
                    fm.beginTransaction()
                            .hide(fragments.get(1))
                            .hide(fragments.get(2))
                            .show(fragments.get(0))
                            .commit();
                    return true;
                case R.id.menu_friends:
                    fm.beginTransaction()
                            .hide(fragments.get(0))
                            .hide(fragments.get(2))
                            .show(fragments.get(1))
                            .commit();
                    return true;
                case R.id.menu_me:
                    fm.beginTransaction()
                            .hide(fragments.get(0))
                            .hide(fragments.get(1))
                            .show(fragments.get(2))
                            .commit();
                    return true;
            }
            return false;
        });

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
                        Toast.makeText(MainActivity2.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        //init the DatabseReference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    //----SHOWING ALERT DIALOG FOR EXITING THE APP----
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("Really Exit ??");
        builder.setTitle("Exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",new MainActivity2.MyListener());
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }

    public class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }

    //---IF USER IS NULL , THEN GOTO LOGIN PAGE----
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mauth.getCurrentUser();
        if(user==null){
            startfn();
        }
        else{
            //---IF LOGIN , ADD ONLINE VALUE TO TRUE---
            mDatabaseReference.child(user.getUid()).child("online").setValue("true");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

     /* //-----for disabling online function when appliction runs in background----
        FirebaseUser user=mauth.getCurrentUser();
        if(user!=null){
            mDatabaseReference.child(user.getUid()).child("online").setValue(ServerValue.TIMESTAMP);
        }
        */
    }

    //--OPENING LOGIN ACTIVITY--
    private void startfn(){
        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}