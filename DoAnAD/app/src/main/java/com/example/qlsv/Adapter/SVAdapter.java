package com.example.qlsv.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlsv.Model.Students;
import com.example.qlsv.R;

import java.util.ArrayList;
import java.util.List;

public class SVAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Students> studentsList;
    public ArrayList<Students> arrayListCopy;
    public static Students students;

    public SVAdapter(Context context, int layout, List<Students> studentsList) {
        this.context = context;
        this.layout = layout;
        this.studentsList = studentsList;
        this.arrayListCopy = new ArrayList<>();
        arrayListCopy.addAll(studentsList);
    }

    @Override
    public int getCount() {
        return studentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{

        ImageView imgAnhSV;
        TextView txtTenSv, txtDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.imgAnhSV = convertView.findViewById(R.id.imgAnhSV);
            viewHolder.txtTenSv = convertView.findViewById(R.id.txtTensv_SeeSV);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDatesv_SeeSV);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        students = studentsList.get(position);
        viewHolder.txtTenSv.setText(students.getTenSV());
        viewHolder.txtDate.setText(students.getDate());
        byte[] imgSV = students.getImages();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgSV , 0, imgSV.length);
        viewHolder.imgAnhSV.setImageBitmap(bitmap);

        //gán animotion
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview);
        convertView.startAnimation(animation);


        return convertView;
    }
    //searchclass
    public void search(String text){

        text = text.toLowerCase();

        //nếu ô tìm kiếm không có -> add lại mảng  :
        if(text.length() == 0){
            studentsList.addAll(arrayListCopy);
        }else{
            studentsList.clear();
            for(Students students  : arrayListCopy){

                if(students.getTenSV().toLowerCase().contains(text)){

                    studentsList.add(students);
                }
                notifyDataSetChanged();
            }
        }
    }

}
