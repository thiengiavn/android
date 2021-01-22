package com.example.qlsv.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qlsv.Adapter.UserAdapter;
import com.example.qlsv.Model.User;
import com.example.qlsv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.example.qlsv.Adapter.UserAdapter.user;

public class InfoUserActivity extends AppCompatActivity {

    Button btnEditUser, btnDeleteUser, btnUpdate, btnHuy;
    EditText edtUserUpdate, edtNameUpdate, edtPlaceUpdate, edtPhoneUpdate;
    Cursor dataUser;
    ListView listViewInfo;
    ArrayList<User> userArrayList;
    UserAdapter userArrayAdapter;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String username, name, place, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        anhxa();
        btnEditUser();
        btnDeleteUser();
        //select data
        GetDataUser();
        Toolbar();
    }

    private void btnDeleteUser() {
        //xóa user
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete(user.getId(), user.getUser());
            }
        });
    }

    public void GetDataUser(){
        Intent intent = getIntent();
        int iduser = intent.getIntExtra("iduser",-1);
        dataUser = MainActivity.database.GetData("SELECT * FROM User WHERE id = '"+ iduser +"'");
        //clear mảng trước khi thêm
        userArrayList.clear();
        while (dataUser.moveToNext()){
            int id = dataUser.getInt(0);
            username = dataUser.getString(1);
            name = dataUser.getString(3);
            place = dataUser.getString(4);
            phone = dataUser.getString(5);
            userArrayList.add(new User(id, username, name, place, phone));


        }

        userArrayAdapter.notifyDataSetChanged();
    }

    //cập nhật user
    private void btnEditUser() {
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate(user.getId(), user.getUser() , user.getName(),
                        user.getPlace() , user.getPhone());
            }
        });

    }
    //dialog delete user
    private  void dialogDelete(final int id, final String user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.name);
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có muốn xóa tài khoản " + user );
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            MainActivity.database.QueryData("DELETE FROM User WHERE id = '" + id+"'");
                            Toast.makeText(InfoUserActivity.this, "Đã xóa tài khoản " + user, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InfoUserActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(InfoUserActivity.this, task.getException().getMessage() + user, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }
    //dialog update
    private void dialogUpdate(final int id, String user, String name, String place, String phone){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_editinfo);
        dialog.show();
        //ánh xạ view custom dialog
        edtUserUpdate  = dialog.findViewById(R.id.edtUserEdit);
        edtNameUpdate  = dialog.findViewById(R.id.edtNameEdit);
        edtPlaceUpdate = dialog.findViewById(R.id.edtPlaceEdit);
        edtPhoneUpdate = dialog.findViewById(R.id.edtPhoneEdit);
        btnHuy = dialog.findViewById(R.id.btnHuy);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);

        edtUserUpdate.setFocusable(false);
        edtUserUpdate.setClickable(false);
//        //settext cho dialog
        edtUserUpdate.setText(user);
        if (edtNameUpdate.equals(null) == false){
            edtNameUpdate.setText(name);
        }
        if (edtPlaceUpdate.equals(null) == false){
            edtPlaceUpdate.setText(place);
        }
        if (edtPhoneUpdate.equals(null) == false){
            edtPhoneUpdate.setText(phone);
        }
        //btn dismiss
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //btn update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy dữ liệu mới
//                String userupdate = edtUserUpdate.getText().toString().trim();
                String nameupdate = edtNameUpdate.getText().toString().trim();
                String placeupdate = edtPlaceUpdate.getText().toString().trim();
                String phoneupdate = edtPhoneUpdate.getText().toString().trim();
                MainActivity.database.QueryData("UPDATE User SET name = '" + nameupdate+"', place = '"+ placeupdate+"' ,  phone = '"+phoneupdate+"'  WHERE id = '"+ id+"'");
//                MainActivity.database.QueryData("UPDATE User SET user = '" + userupdate+"', name = '" + nameupdate+"', place = '"+ placeupdate+"' ,  phone = '"+phoneupdate+"'  WHERE id = '"+ id+"'");
                Toast.makeText(InfoUserActivity.this, "Đã Cập nhật!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataUser();
            }
        });

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
    private void anhxa() {
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        btnEditUser = findViewById(R.id.btnEditUser);
        toolbar = findViewById(R.id.toolbar_InfoUser);
        //list view
        listViewInfo = findViewById(R.id.listInfoUser);
        userArrayList = new ArrayList<>();
        userArrayAdapter = new UserAdapter(this, R.layout.custom_listview_userinfo, userArrayList);
        listViewInfo.setAdapter(userArrayAdapter);
    }

}
