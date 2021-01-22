package com.example.qlsv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qlsv.Model.User;
import com.example.qlsv.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<User> userList;
    public static User user;

    public UserAdapter(Context context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txtUser,txtName,txtPlace, txtPhone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtUser = view.findViewById(R.id.txtUser);
            holder.txtName = view.findViewById(R.id.txtName);
            holder.txtPlace = view.findViewById(R.id.txtPlace);
            holder.txtPhone = view.findViewById(R.id.txtPhone);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        user = userList.get(i);
        holder.txtUser.setText(user.getUser());
        holder.txtName.setText(user.getName());
        holder.txtPlace.setText(user.getPlace());
        holder.txtPhone.setText(user.getPhone());
        return view;
    }
}
