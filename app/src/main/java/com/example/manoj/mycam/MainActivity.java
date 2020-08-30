package com.example.manoj.mycam;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText username,password;

   static String userstr,passstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
         Button loginbtn=findViewById(R.id.loginbutton);
         username=findViewById(R.id.usernameedit);
         password=findViewById(R.id.passwordedit);
         loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View z)
            {
                userstr=username.getText().toString();
                passstr=password.getText().toString();
                new ExecuteTask(MainActivity.this).execute(userstr,passstr);
            }
        });

    }
}