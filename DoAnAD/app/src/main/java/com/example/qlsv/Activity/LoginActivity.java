package com.example.qlsv.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qlsv.Model.common;
import com.example.qlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser;
    EditText edtPass;
    Button btnSignIn,btnSignUp;
    CheckBox checkUser;
    FirebaseAuth mAuthencation;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    TextView forgotPassword;
    SharedPreferences sharedPreferences;
    private String use,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();

        //xử lí lưu đăng nhập
        sharedPreferences = getSharedPreferences("dataLogin" , MODE_PRIVATE);
        //lấy giá trị từ dataLogin
        edtUser.setText(sharedPreferences.getString("user" , ""));

        edtPass.setText(sharedPreferences.getString("pass" , ""));

        checkUser.setChecked(sharedPreferences.getBoolean("checked" , false));

        mAuthencation = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        //chuyển màn hình sang đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkValidation();

                progressDialog = ProgressDialog.show(LoginActivity.this, "Đăng nhập",
                        "Xin chờ !!!", true);
                progressDialog.show();
                DangNhap();

            }
        });
    }
    private void DangNhap(){
        String TaiKhoan = edtUser.getText().toString().trim();
        String MatKhau = edtPass.getText().toString().trim();

        if (TaiKhoan.isEmpty()  || !Patterns.EMAIL_ADDRESS.matcher(TaiKhoan).matches()) {
            edtUser.setError("Vui lòng nhập địa chỉ email hợp lệ");
            progressDialog.dismiss();
        }else
        if(MatKhau.isEmpty() || !MatKhau.matches("[a-zA-Z0-9]+") ){
            edtPass.requestFocus();
            edtPass.setError("Mật khẩu không được để trống và không được có ký tự đặc biệt");
            progressDialog.dismiss();
        }else

            mAuthencation.signInWithEmailAndPassword(TaiKhoan, MatKhau)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {

                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                String user = edtUser.getText().toString();
                                String pass = edtPass.getText().toString();
                                Cursor dataUser = MainActivity.database.GetData("SELECT * FROM User");
                                while (dataUser.moveToNext()) {
                                    int iddata = dataUser.getInt(0);
                                    String userdata = dataUser.getString(1);

                                    if (user.equals(userdata)) {
                                        //user checkbox
                                        if (checkUser.isChecked()) {
                                            editor = sharedPreferences.edit();
                                            editor.putString("user", user);
                                            editor.putString("pass", pass);
                                            editor.putBoolean("checked", true);
                                            editor.commit();
                                        } else {
                                            editor = sharedPreferences.edit();
                                            editor.remove("user");
                                            editor.remove("pass");
                                            editor.remove("checked");
                                            editor.commit();
                                        }

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.putExtra("user", userdata);
//                                        Toast.makeText(LoginActivity.this, "Hello " + user, Toast.LENGTH_SHORT).show();
                                        intent.putExtra("id", iddata);
//                                        Toast.makeText(LoginActivity.this, "id " + iddata, Toast.LENGTH_SHORT).show();
                                        startActivity(intent);

                                        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                                        progressDialog.dismiss();
                                        editor.commit();
                                    }

//                                    Intent intent = new Intent(getApplication(), HomeActivity.class);
//                                    startActivity(intent);

//                                    editor.putString("email", edtUser.getText().toString().trim());  // Saving string
//                                    editor.putString("pass", edtPass.getText().toString().trim());
                                    // Save the changes in SharedPreferences
                                    // commit changes

                                }
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }


    private void checkValidation() {
        interview();
        if(!validate()){
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this, "Đăng nhập Thành Công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplication(),HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
        }
    }
    public  boolean validate(){
        boolean valid = true;


        if(pwd.isEmpty() || !pwd.matches("[a-zA-Z0-9]+") ){
            edtPass.requestFocus();
            edtPass.setError("Mật khẩu không được để trống và không được có ký tự đặc biệt");
            valid=false;

        }

        if(use.isEmpty()|| use.length() <=6 || use.length() >=18){
            edtUser.setError("Không được để trống. Tài khoản phải lớn hơn 6 và bé hơn 18 ký tự");
            valid=false;

        }

        return valid;

    }
    public void interview(){

        use = edtUser.getText().toString().trim();
        pwd = edtPass.getText().toString().trim();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode ==KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(this);
            alertDialogBuiler.setMessage("Bạn có muốn thoát không");
            alertDialogBuiler.setPositiveButton("có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            alertDialogBuiler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuiler.create();
            alertDialog.show();
            return true;
        }else if ((keyCode==KeyEvent.KEYCODE_MENU)){
            return true;

        }

        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onResume() {
        if (common.email!=null){
            edtUser.setText(common.email);
            edtPass.setText(common.pass);
        }

        super.onResume();
    }
    private void anhxa() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp_New);
        progressBar = findViewById(R.id.progressbar);
        checkUser = findViewById(R.id.checkbox_User);
        forgotPassword = findViewById(R.id.forgotPassword);
    }
}
