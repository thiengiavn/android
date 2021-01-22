package com.example.qlsv.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.qlsv.Database.Database;
import com.example.qlsv.R;

public class MainActivity extends AppCompatActivity {
    public static Database database;
    ImageView imglogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imglogo = (ImageView) findViewById(R.id.splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000L );
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.trans_start);
        imglogo.setAnimation(animation);


        database = new Database(this, "assignment.sqlite", null, 1);
        //create table user
        database.QueryData("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR(20), password VARCHAR(20), name VARCHAR(30), place VARCHAR(40), phone VARCHAR(10))");
        //create table Class
        database.QueryData("CREATE TABLE IF NOT EXISTS Class(id INTEGER PRIMARY KEY AUTOINCREMENT, malop VARCHAR(20), tenlop VARCHAR(20), iduser INTEGER(20))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Students(id INTEGER PRIMARY KEY , tensv VARCHAR(20), ngaysinh VARCHAR(20), idclass INTEGER(20),iduser INTEGER(20)," +
                " tenlop VARCHAR(20), sdt VARCHAR(20),email VARCHAR(20),place VARCHAR(20), images BLOB)");

    }
}
