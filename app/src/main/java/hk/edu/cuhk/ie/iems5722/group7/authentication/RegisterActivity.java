package hk.edu.cuhk.ie.iems5722.group7.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hk.edu.cuhk.ie.iems5722.group7.R;
import hk.edu.cuhk.ie.iems5722.group7.authentication.model.UserValidate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView banner, registerUser;
    private EditText editTextUserName,editTextUserID, editTextAge, editTextEmail, editTextPwd;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // firebase authentication service object
        mAuth = FirebaseAuth.getInstance();

        // the banner shows our welcome
        banner = (TextView) findViewById(R.id.banner_register);
        banner.setOnClickListener(this);

        // the register button
        registerUser = (Button) findViewById(R.id.regist_user_button);
        registerUser.setOnClickListener(this);

        // the input text boxes
        editTextUserName = (EditText) findViewById(R.id.regist_name);
        editTextUserID = (EditText) findViewById(R.id.regist_userid);
        editTextAge = (EditText) findViewById(R.id.regist_age);
        editTextEmail = (EditText) findViewById(R.id.email_register);
        editTextPwd = (EditText) findViewById(R.id.pwd_register);
        // the spinning indicating it's loading
        progressBar = (ProgressBar) findViewById(R.id.regist_progress_bar);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            // go back to login page if click the banner in register
            case R.id.banner_register:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.regist_user_button:
                // register action
                registerUser();
                break;

        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPwd.getText().toString().trim();
        String userID = editTextUserID.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        if(userName.isEmpty()){
            editTextUserName.setError("User name is required!");
            editTextUserName.requestFocus();
            return;
        }
        // age info is not empty
        if(age.isEmpty()){
            editTextAge.setError("How old are you?");
            editTextUserName.requestFocus();
            return;
        }

        // userID is not empty nor valid
        if(userID.isEmpty()){
            editTextUserID.setError("User ID is required!");
            editTextUserID.requestFocus();
            return;
        }
        if(userID.length() == 5){
            editTextEmail.setError("User ID shoud be 5 digits, please check.");
            editTextEmail.requestFocus();
            return;
        }

        // email is not empty nor valid
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email is not valid, please check.");
            editTextEmail.requestFocus();
            return;
        }

        // password is not empty nor valid
        if(password.isEmpty()){
            editTextPwd.setError("Password is required!");
            editTextPwd.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPwd.setError("Password should be at least 6 characters.");
            editTextPwd.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if the information is validated in firebase authentication service
                        if(task.isSuccessful()){
                            UserValidate userValidate = new UserValidate(userName, userID, age, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    // getCurrentUser.getUid can return the user ID from firebase database
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    // return a value to check if the user information is saved to firebase db successfully
                                    .setValue(userValidate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Welcome to join CUHK Chat!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        // redirect to login layout
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Failed to register, try again with patience~ :)",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    }
                });



    }
}