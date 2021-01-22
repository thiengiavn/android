package com.example.qlsv.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlsv.Model.common;
import com.example.qlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class RegisterActivity extends AppCompatActivity {
    EditText edtUser, edtPass, edtRePass;
    private String name, pwdln, pwd;
    Button btnSignUp,btnSignInAgain;
    ProgressBar progressBar;
    FirebaseAuth mAuthencation;
    DatabaseReference mData;
    private void anhxa() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRePass);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignInAgain = findViewById(R.id.btnSignInAgain);
        progressBar = findViewById(R.id.progressbar);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuthencation = FirebaseAuth.getInstance();
        anhxa();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();


            }
        });
        btnSignInAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void DangKy() {


        final String email = edtUser.getText().toString().trim();
        final String pass = edtPass.getText().toString().trim();
        String passnn = edtRePass.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtUser.setError("Vui lòng nhập địa chỉ email hợp lệ");
        } else if (pass.isEmpty() || !pass.matches("[a-zA-Z0-9]+")) {
            edtPass.requestFocus();
            edtPass.setError("Mật khẩu không được để trống và không được có ký tự đặc biệt");

        } else if (passnn.isEmpty() || !passnn.matches("[a-zA-Z0-9]+") || !passnn.matches(pass)) {

            edtRePass.setError("Nhập lại mật khẩu không đúng ");
        } else if(passnn.length() < 6){
            edtRePass.setError("Mật khẩu phải có ít nhât 6 ký tự ");

        } else


            mAuthencation.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                MainActivity.database.INSERT_USER(
                                        edtUser.getText().toString().trim(),
                                        edtPass.getText().toString().trim(),
                                        "null", "null", "null"
                                );
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công " + email, Toast.LENGTH_SHORT).show();
                                common.email = edtUser.getText().toString();
                                common.pass = edtPass.getText().toString();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Lỗi trong quá trình đăng kí", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

    }


    private void checkValidation() {
        interview();

        if (!validate()) {
            Toast.makeText(this, "Đăng Kí thất bại", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();

//            finish();
        }

    }

    public boolean validate() {
        boolean valid = true;
        if (name.isEmpty() || name.length() <= 6 || name.length() >= 18) {
            edtUser.setError("Không được để trống. Tài khoản phải lớn hơn 6 và bé hơn 18 ký tự");
            valid = false;
        } else


            if (pwd.isEmpty() || !pwd.matches("[a-zA-Z0-9]+")) {
                edtPass.requestFocus();
                edtPass.setError("PassWord không được để trống và không được có ký tự đặc biệt");
                valid = false;
            } else if (pwdln.isEmpty() || !pwdln.matches("[a-zA-Z0-9]+") || edtRePass == edtPass) {
                edtRePass.requestFocus();
                edtRePass.setError("PassWord không được để trống và không được có ký tự đặc biệt");
                valid = false;

            }


        return valid;

    }

    public void interview() {
        name = edtUser.getText().toString().trim();
        pwdln = edtRePass.getText().toString().trim();
        pwd = edtPass.getText().toString().trim();
    }
}
