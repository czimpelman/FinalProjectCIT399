package com.example.zimpelman.finalprojectcit399;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvUsername, tvPassword;
    Button btnCreate, btnLogin;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = (TextView)findViewById(R.id.tvUserName);
        tvPassword = (TextView)findViewById(R.id.tvPassword);

        btnCreate = (Button)findViewById(R.id.btnCreateAccount);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        final UserDB users = new UserDB(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = tvUsername.getText().toString();
                String password = tvUsername.getText().toString();
                User newUser = new User(username,password);
                users.addUser(newUser);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int tempId = -1;
                String username="", password="";
                int count = 0;
                for(User user : userList){
                    user = users.getUser(count);
                    if(user.getUsername() == username){
                        if(user.getPassword() == password){
                            tempId = user.getUserId();
                        }
                    }
                }
                if(tempId != -1){
                    Toast.makeText(getApplicationContext(), "Good Job", Toast.LENGTH_LONG);
                }
                //Intent i = new Intent(getApplicationContext(), UserAlarms.class);
                //i.putExtra("User", tempId);
                //startActivity(i);
            }
        });
    }
}
