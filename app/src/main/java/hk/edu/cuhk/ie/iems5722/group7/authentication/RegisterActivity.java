package hk.edu.cuhk.ie.iems5722.group7.authentication;

import androidx.appcompat.app.AppCompatActivity;
import hk.edu.cuhk.ie.iems5722.group7.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView banner, registerUser;
    private EditText editTextUserName, editTextAge, editTextEmail, editTextPwd;
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
        String userName = editTextUserName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        if(userName.isEmpty()){
            editTextUserName.setError("User name is required!");
            editTextUserName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("We assume you are 100 years old if you leave age blank.");
            age = "100";
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

        if(){

        }


    }
}