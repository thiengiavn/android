package com.example.qlsv.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlsv.Model.Students;
import com.example.qlsv.R;

import java.util.List;


public class StudentsAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Students> studentsList;

    public StudentsAdapter(Context context, int layout, List<Students> studentsList) {
        this.context = context;
        this.layout = layout;
        this.studentsList = studentsList;
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
    private class ViewHodler{
        TextView txtSTT, txtTenSV, txtDate;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView == null){
            viewHodler = new ViewHodler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHodler.txtSTT = convertView.findViewById(R.id.txtSTT);
            viewHodler.txtTenSV = convertView.findViewById(R.id.txtTenSV);
            viewHodler.txtDate = convertView.findViewById(R.id.txtDate);
            convertView.setTag(viewHodler);

        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }
        Students students = studentsList.get(position);
        viewHodler.txtSTT.setText("" +(position+1));
        viewHodler.txtTenSV.setText(students.getTenSV());
        viewHodler.txtDate.setText(students.getDate());
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_listview);
        convertView.startAnimation(animation);
        return convertView;
    }
}
