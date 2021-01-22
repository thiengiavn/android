package com.example.qlsv.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.view.MenuItem;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.qlsv.Model.Class;
import com.example.qlsv.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView txtUser;
    TextView colorRed, colorViolet, colorBlue, colorYellow;

    NavigationView navigationView;
    private  String user;
    private int iduser;
    Button btnAddclass, btnSeeClass, btnManageClass , btnAddclass_dialog, btnDelete_Dialog, btnSeeListSV;
    EditText edtMaLop, edtTenLop;
    ImageView imgHuy_Dialog_add;
//    public static String userId;
    Toast toast;
    public static ArrayList<Class> arLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.nav_view);
        anhxa();
        setSupportActionBar(toolbar);

        //xử lí navgation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //xử lí get user từ Login
        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        iduser = intent.getIntExtra("id" , -1);

        Toast.makeText(this, "Chào user:" + user + " id:" + iduser, Toast.LENGTH_SHORT).show();


        //xử lí addClass
        btnAddClass();
        //xử lí btnSeeClass
        btnSeeClass();
        //xử lí btnManageSV
        btnManageSV();
        //xử lí btn SeeListSV
        btnSeeListSV();


    }

    private void btnSeeListSV() {

        btnSeeListSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SeeStudentsListActivity.class);
                intent.putExtra("iduser" , iduser);
                intent.putExtra("user" , user);
                startActivity(intent);
            }
        });

    }

    //dialog addclass
    private void btnAddClass() {

        btnAddclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddclass();
            }
        });
    }
    public void DialogAddclass(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_addclass);
        //ánh xạ dialog
        edtMaLop = dialog.findViewById(R.id.edtMaLop);
        edtTenLop = dialog.findViewById(R.id.edtTenLop);
        btnAddclass_dialog = dialog.findViewById(R.id.btnaddclass_dialog);
        btnDelete_Dialog = dialog.findViewById(R.id.btndelete_dialog);
        imgHuy_Dialog_add = dialog.findViewById(R.id.imgHuy_dialogAdd);


        btnAddclass_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDataClass();
                dialog.dismiss();
            }
        });
        btnDelete_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenLop.setText("");
                edtMaLop.setText("");
            }
        });
        imgHuy_Dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    public void InsertDataClass(){
        String malop = edtMaLop.getText().toString().trim();
        String tenlop = edtTenLop.getText().toString().trim();
        if(malop.length() == 0 || tenlop.length() == 0){
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else{
            MainActivity.database.INSERT_CLASS(malop,tenlop,iduser);
            Toast.makeText(this, "Đã thêm thành công lớp " + tenlop, Toast.LENGTH_SHORT).show();
        }

    }
    private void btnManageSV() {
        btnManageClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ManageSVActivity.class);
                intent.putExtra("user" , user);
                intent.putExtra("iduser" , iduser);
                startActivity(intent);
            }
        });
    }

    private void btnSeeClass() {
        btnSeeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SeeClassListActivity.class);
                intent.putExtra("user" , user);
                intent.putExtra("iduser" ,  iduser);
                startActivity(intent);
            }
        });

    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        drawer  = findViewById(R.id.drawer_layout);
        txtUser = navigationView.findViewById(R.id.txtUser_nav);
        btnAddclass = findViewById(R.id.btnAddClass);
        btnSeeClass = findViewById(R.id.btnSeeClass);
        btnManageClass = findViewById(R.id.btnManageClass);
        btnSeeListSV = findViewById(R.id.btnSeeListClass);

    }


    //phím back
    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            DialogLogOut();
        }
    }
    private  void DialogLogOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.error);
        builder.setMessage("Bạn có muốn đăng xuất tài khoản?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    //clicked item trong navigation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addClass) {

            DialogAddclass();
        } else if (id == R.id.nav_seeClass) {
            Intent intent = new Intent(HomeActivity.this, SeeClassListActivity.class);
            intent.putExtra("user" , user);
            intent.putExtra("iduser" ,  iduser);
            startActivity(intent);
        }else if(id == R.id.nav_manageClass){
            Intent intent = new Intent(HomeActivity.this, ManageSVActivity.class);
            intent.putExtra("user" , user);
            intent.putExtra("iduser" ,  iduser);
            startActivity(intent);
        }else if (id == R.id.info_user) {
            Intent intent = new Intent(this, InfoUserActivity.class);
            intent.putExtra("user" , user);
            intent.putExtra("iduser" ,  iduser);
            startActivity(intent);
        } else if (id == R.id.info_tacgia) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_info);
            dialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            },2000);


        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_color, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.choose_color:
                Dialog_choose_color();
                break;
            case R.id.logout:
                DialogLogOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Dialog_choose_color(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_color);
        //ánh xạ dialog
        colorRed = findViewById(R.id.colorRed);
        colorBlue = findViewById(R.id.colorBlue);
        colorViolet = findViewById(R.id.colorViolet);
        colorYellow = findViewById(R.id.colorYellow);


        dialog.show();


    }
}
