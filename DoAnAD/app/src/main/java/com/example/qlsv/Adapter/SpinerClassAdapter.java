package com.example.qlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlsv.Model.Class;
import com.example.qlsv.R;

import java.util.ArrayList;
import java.util.List;


public class SpinerClassAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Class> addClassList;

    public SpinerClassAdapter(Context context, int layout, List<Class> addClassList) {
        this.context = context;
        this.layout = layout;
        this.addClassList = addClassList;
    }

    @Override
    public int getCount() {
        return addClassList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtSpinerClass = view.findViewById(R.id.txtSpinerClass);
        Class addClass = addClassList.get(i);
        txtSpinerClass.setText("" + addClass.getTenlop());

        return view;
    }
}
