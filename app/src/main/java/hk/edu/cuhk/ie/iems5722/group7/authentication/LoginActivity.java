package hk.edu.cuhk.ie.iems5722.group7.authentication;

import androidx.appcompat.app.AppCompatActivity;
import hk.edu.cuhk.ie.iems5722.group7.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.to_regist);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.to_regist:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}