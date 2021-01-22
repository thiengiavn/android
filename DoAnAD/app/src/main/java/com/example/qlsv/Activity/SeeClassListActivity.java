package com.example.qlsv.Activity;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.qlsv.Adapter.ClassAdapter;
import com.example.qlsv.Model.Class;
import com.example.qlsv.R;


import java.util.ArrayList;

import static com.example.qlsv.Activity.MainActivity.database;

public class SeeClassListActivity extends AppCompatActivity {
    ListView listaddclass;
    ArrayList<Class> addClassArrayList, arrayDataCopy;
    ClassAdapter classAdapter;
    ImageView img_huy_updateClass;
    EditText edtMalopUdate, edtTenlopupdate;
    Button btnUpdateClass,btndeleteUpdate;
    Toolbar toolbar;
    int iduser;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_class_list);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        iduser = intent.getIntExtra("iduser" , -1);

        anhxa();
        Toolbar();
        GetDataClass();
        clickListview();



    }
    //click item listview chọn edit hay xóa
    private void clickListview() {
        listaddclass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(SeeClassListActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_choose_delete_edit);
                ImageView imgChooseEdit, imgChooseDelete;
                imgChooseEdit = dialog.findViewById(R.id.imgChooseEdit);
                imgChooseDelete = dialog.findViewById(R.id.imgChooseDelete);
                final Class addClass = addClassArrayList.get(position);
                imgChooseEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUpdateClass(addClass.getId(), addClass.getMalop(),addClass.getTenlop());
                        dialog.dismiss();
                    }
                });
                imgChooseDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDeleteClass(addClass.getId(), addClass.getTenlop());
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });



    }

    public void DialogUpdateClass(final int id, String malop , final String tenlop){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_update_class);
        //ánh xạ các view
        edtMalopUdate = dialog.findViewById(R.id.edtMaLop_update);
        edtTenlopupdate = dialog.findViewById(R.id.edtTenLop_update);
        btnUpdateClass = dialog.findViewById(R.id.btnaddclass_dialog_update);
        btndeleteUpdate = dialog.findViewById(R.id.btndelete_dialog_update);
        img_huy_updateClass = dialog.findViewById(R.id.imgHuy_dialogAdd_update);
        edtMalopUdate.setText(malop);
        edtTenlopupdate.setText(tenlop);
        //btn delete -> xóa trắng
        btndeleteUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMalopUdate.setText("");
                edtTenlopupdate.setText("");
            }
        });
        //btn update class
        btnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassArrayList.clear();
                String malopMoi = edtMalopUdate.getText().toString().trim();
                String tenlopMoi = edtTenlopupdate.getText().toString().trim();
                if(malopMoi.length() ==0 || tenlopMoi.length() == 0){
                    Toast.makeText(SeeClassListActivity.this, "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("UPDATE Class SET malop = '"+malopMoi+"' , tenlop = '"+tenlopMoi+"' WHERE id = '"+id+"'");
                    GetDataClass();
                    dialog.dismiss();
                    Toast.makeText(SeeClassListActivity.this, "Đã cập nhật lớp" + tenlopMoi , Toast.LENGTH_SHORT).show();
                }
            }
        });
        //btn hủy ->đóng dialog
        img_huy_updateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //dialog xóa lớp khỏi database
    public void DialogDeleteClass(final int id, final String tenlop){
        final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setIcon(R.drawable.error);
        aBuilder.setTitle("Thông báo");
        aBuilder.setMessage("Bạn có muốn xóa lớp " + tenlop);

        aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addClassArrayList.clear();
                database.QueryData("DELETE FROM Class WHERE id = '"+id+"'");
                database.QueryData("UPDATE  Students SET idclass = '0' , tenlop = 'Chưa chọn lớp!' WHERE idclass = '"+id+"'");
                Toast.makeText(getApplicationContext(), "Đã xóa lớp "+ tenlop + " thành công!", Toast.LENGTH_SHORT).show();
                GetDataClass();
            }
        });
        aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        aBuilder.show();
    }

    private void GetDataClass() {
        Cursor dataClass;
        if(user.equals("admin@gmail.com")){
            dataClass = database.GetData("SELECT * FROM Class");
        }
        else {
            dataClass = database.GetData("SELECT * FROM Class WHERE iduser = '"+ iduser +"'");
        }

//        addClassArrayList.clear();
        while (dataClass.moveToNext()){
            int id = dataClass.getInt(0);
            String malop = dataClass.getString(1);
            String tenlop = dataClass.getString(2);
            iduser = dataClass.getInt(3);
            addClassArrayList.add(new Class(id, malop, tenlop, iduser));
        }

        classAdapter = new ClassAdapter(this, R.layout.custom_listclass  , addClassArrayList );
        listaddclass.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();
    }

    private void anhxa() {

        toolbar = findViewById(R.id.toolbar_SeeClass);
        listaddclass = findViewById(R.id.listClass);
        //xử lí listview
        addClassArrayList = new ArrayList<>();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() == 0){
                    listaddclass.setAdapter(classAdapter);
                }else {
                    arrayDataCopy = new ArrayList<>();
                    Cursor cursor = database.GetData("SELECT * FROM Class WHERE tenlop like '%" + s + "%'");
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String malop = cursor.getString(1);
                        String tenlop = cursor.getString(2);
                        iduser = cursor.getInt(3);
                        arrayDataCopy.add(new Class(id, malop, tenlop, iduser));
                    }
                    classAdapter = new ClassAdapter(SeeClassListActivity.this, R.layout.activity_see_class_list, arrayDataCopy);
                    classAdapter.notifyDataSetChanged();
                    listaddclass.setAdapter(classAdapter);

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                classAdapter.search(s);
                classAdapter.notifyDataSetChanged();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


}
