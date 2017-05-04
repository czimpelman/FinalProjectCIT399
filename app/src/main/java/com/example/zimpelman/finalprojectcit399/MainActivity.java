package com.example.zimpelman.finalprojectcit399;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvUsername, tvPassword;
    EditText etUsername, etPassword;
    Button btnCreate, btnLogin;
    private List<User> userList = new ArrayList<>();
    String username, password;
    UserDB usersDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = (TextView)findViewById(R.id.tvUserName);
        tvPassword = (TextView)findViewById(R.id.tvPassword);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        btnCreate = (Button)findViewById(R.id.btnCreateAccount);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        usersDB = new UserDB(this);

        if(usersDB.getUserCount() > 0){
            userList.addAll(usersDB.getAllUsers());
        }

        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getUsername();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int tempId = -1;
                String tempUsername=etUsername.getText().toString();
                String tempPassword=etPassword.getText().toString();
                int count = usersDB.getUserCount();
                for(int i = 1; i <= count; i++){
                    User user = usersDB.getUser(count);
                    if(user.getUsername().equals(tempUsername)){
                        if(user.getPassword().equals(tempPassword)){
                            tempId = user.getUserId();
                        }
                    }
                }
                if(tempId != -1){
                    Toast.makeText(getApplicationContext(), "Good Job", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect Username/Password", Toast.LENGTH_LONG).show();
                }
                //Intent i = new Intent(getApplicationContext(), UserAlarms.class);
                //i.putExtra("User", tempId);
                //startActivity(i);
            }
        });
    }

    public void getUsername(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Desired Username: ");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();
                String valid = "True";
                for(int i = 1; i <= usersDB.getUserCount(); i++){
                    if(username.equals(userList.get(i).getUsername())){
                        valid = "Repeat";
                    }
                }
                if(username.contains(" ") || username.equals("")){
                    valid = "";
                }
                if(valid.equals("True")){
                    getPassword();
                }else if(valid.equals("Repeat")){
                    Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid username (no spaces allowed, must be at least 5 characters long)", Toast.LENGTH_SHORT);
                }

            }
        });

        builder.show();
    }

    public void getPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Desired Password: ");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = input.getText().toString();
                User newUser = new User(username,password);
                usersDB.addUser(newUser);
                userList.add(newUser);
            }
        });

        builder.show();
    }
}
