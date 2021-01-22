package com.example.qlsv.Activity;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import android.os.Bundle;

import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.qlsv.Adapter.SpinerClassAdapter;
import com.example.qlsv.Adapter.StudentsAdapter;
import com.example.qlsv.Model.Class;
import com.example.qlsv.Model.Students;
import com.example.qlsv.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ManageSVActivity extends AppCompatActivity {
    EditText edtTenSV, edtDate,edtSDT,edtDiaChi,edtEmail;
    DatePickerDialog picker;
    Button btnAddSV,btnAddAnhSVManage;
    Toolbar toolbar;
    int iduser, idclass;
    String user;
    String tenlop;

    final int REQUSE_CODE_CAMERA = 123;
    final int REQUES_CODE_FILE = 456;
    Spinner spinner;
    ImageView imgAnhSVTam;
    ArrayList<Class> addClassArrayListSpiner;
    SpinnerAdapter adapterSpiner;
    StudentsAdapter studentsAdapter;
    ListView listViewSV;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sv);
        anhxa();
        Toolbar();
        //xử lí chọn ngày tháng năm sinh
        edtDate();
        //xử lí đổ dữ liệu lên spiner
        Spiner();
        //xử lí btnAddSV
        btnAddSV();
        //xử lí btn chọn ảnh
        btnAddAnhSVManage();
        GetDataClass_SV();

        edtDate.setInputType(InputType.TYPE_NULL);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ManageSVActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });



    }
    //xử lí btnAddSV
    private void btnAddSV() {
        btnAddSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //idclass = 0 -> "Chọn lớp" -> null
//                for(int i = 0; i < imgAnhSV.length ; i++){
//                    if(imgAnhSV[i] != 0){
//                        Toast.makeText(ManageSVActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                    }else{
//
//                    }
//
//                }
                if(idclass == 0){
                    Toast.makeText(ManageSVActivity.this, "Vui lòng chọn một lớp!", Toast.LENGTH_SHORT).show();
                }else if(edtTenSV.length() == 0){
                    Toast.makeText(ManageSVActivity.this, "Vui lòng nhập Tên", Toast.LENGTH_SHORT).show();
                }else if(edtDate.length() == 0){
                    Toast.makeText(ManageSVActivity.this, "Vui lòng nhập Ngày sinh", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        insertDataSV();
                        GetDataClass_SV();
                    }catch (Exception e ){
                        Toast.makeText(ManageSVActivity.this, "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();


                    }

                }
            }
        });


    }

    private void Spiner() {
        addClassArrayListSpiner = new ArrayList<>();
        addClassArrayListSpiner.add(new Class(0, "null" , "Chọn lớp" , iduser ));
        adapterSpiner = new SpinerClassAdapter(this, R.layout.custom_list_spiner_class, addClassArrayListSpiner);
        spinner.setAdapter(adapterSpiner);
        GetDataClass_Spiner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idclass = addClassArrayListSpiner.get(position).getId();
                tenlop = addClassArrayListSpiner.get(position).getTenlop();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void edtDate() {
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDate();
            }
        });
    }

    private void btnAddAnhSVManage(){
        btnAddAnhSVManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_ChooseAddAnhSV();

            }
        });

    }
    private void dialog_ChooseAddAnhSV(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_chonanhsv);
        ImageView btnChooseAnhFile = dialog.findViewById(R.id.btnchooseanhFile);
        ImageView btnChooseAnhCamera = dialog.findViewById(R.id.btnchooseanhCamera);


        btnChooseAnhCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ManageSVActivity.this, new String[]{Manifest.permission.CAMERA}, REQUSE_CODE_CAMERA);
                dialog.dismiss();
            }
        });
        btnChooseAnhFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ManageSVActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUES_CODE_FILE);
                dialog.dismiss();
            }
        });


        dialog.show();

    }
    //xử lí xin quyền camera và file

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUSE_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUSE_CODE_CAMERA);
                }else {

                    Toast.makeText(this, "Bạn chưa cấp quyền Camera!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUES_CODE_FILE:

                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUES_CODE_FILE);
                }else {
                    Toast.makeText(this, "Bạn chưa cấp quyền truy cập thư viện!", Toast.LENGTH_SHORT).show();
                }
                break;

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // đổ ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUSE_CODE_CAMERA && resultCode == RESULT_OK && data !=  null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhSVTam.setImageBitmap(bitmap);
        }
        if(requestCode == REQUES_CODE_FILE && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhSVTam.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
            }
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    //dialog date
    public  void DialogDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // i i1 i2 -> năm tháng ngày
                calendar.set(i, i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year,month,day);
        datePickerDialog.show();
    }
    private void anhxa() {
        toolbar = findViewById(R.id.toolbar_Manage);
        edtTenSV = findViewById(R.id.edtTenSV);
        edtDate = findViewById(R.id.edtDate);
        edtSDT= findViewById(R.id.edtSDT);
        edtEmail= findViewById(R.id.edtEmailSv);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        spinner = findViewById(R.id.spinnerClass);
        btnAddSV = findViewById(R.id.btnAddSV);
        imgAnhSVTam = findViewById(R.id.imgAnhSVTam);
        btnAddAnhSVManage = findViewById(R.id.btnAddAnhSVManage);
        progressBar = findViewById(R.id.progressbar_manager);

        //get iduser từ màn hình home
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        iduser = intent.getIntExtra("iduser" , -1);


    }
    //GetData
    private void GetDataClass_Spiner() {
        Cursor dataSpiner = MainActivity.database.GetData("SELECT * FROM Class WHERE iduser = '" + iduser + "'");
        while (dataSpiner.moveToNext()) {
            int id = dataSpiner.getInt(0);
            String malop = dataSpiner.getString(1);
            String tenlop = dataSpiner.getString(2);
            iduser = dataSpiner.getInt(3);
            addClassArrayListSpiner.add(new Class(id, malop, tenlop, iduser));
        }
    }
    public void insertDataSV() {
        final String tenSV = edtTenSV.getText().toString().trim();
        final String date = edtDate.getText().toString().trim();
        final String sdt = edtSDT.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String diaChi = edtDiaChi.getText().toString().trim();
        //chuyeern img ->byte
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnhSVTam.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        final ByteArrayOutputStream arr_byte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, arr_byte);

        final byte[] imgAnhSV =   arr_byte.toByteArray();

        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed (new Runnable() {
            @Override
            public void run() {
                try {

                    MainActivity.database.INSERT_SV(tenSV, date, idclass, iduser, tenlop, sdt, email, diaChi, imgAnhSV);

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ManageSVActivity.this, "Đã thêm thành công Sinh viên " + tenSV, Toast.LENGTH_SHORT).show();
                    GetDataClass_SV();
                }catch (Exception e){
                    Toast.makeText(ManageSVActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();

                }


            }
        }, 1000);


    }
    private void GetDataClass_SV(){
        Cursor dataSV;
        if (user.equals("admin")){
            dataSV = MainActivity.database.GetData("SELECT * FROM Students");
        }
        else {
            dataSV = MainActivity.database.GetData("SELECT * FROM Students WHERE iduser = '"+ iduser+"'");
        }
        while (dataSV.moveToNext()){
            int id = dataSV.getInt(0);
            String tenSV = dataSV.getString(1);
            String date = dataSV.getString(2);
            String tenlop = dataSV.getString(5);
            String sdt = dataSV.getString(6);
            String email = dataSV.getString(7);
            String place = dataSV.getString(8);
        }
    }
    private void Toolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}

