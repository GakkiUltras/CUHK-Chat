package hk.edu.cuhk.ie.iems5722.group7.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import hk.edu.cuhk.ie.iems5722.group7.MainActivity;
import hk.edu.cuhk.ie.iems5722.group7.R;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView register;
    private EditText editTextEmail, editTextPwd;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initialize the components
        register = (TextView) findViewById(R.id.to_regist);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.singIn_button);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email_register);
        editTextPwd = (EditText) findViewById(R.id.pwd_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.to_regist:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.singIn_button:
                userLogin();
        }
    }

    /** Login validation from input
     * 1. take the values from input,
     * 2. validate
    **/
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPwd.getText().toString().trim();
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // redirect to mainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your password or email address.", Toast.LENGTH_LONG).show();
                }
                }
            }
        );

    }
}