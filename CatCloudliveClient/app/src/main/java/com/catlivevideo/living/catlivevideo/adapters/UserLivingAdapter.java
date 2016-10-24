package com.catlivevideo.living.catlivevideo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catlivevideo.living.catlivevideo.R;
import com.catlivevideo.living.catlivevideo.bean.User;

import java.util.List;

/**
 * Created by pan on 2016/10/8.
 */

public class UserLivingAdapter extends BaseAdapter {
    private List<User> mUserList;
    public  UserLivingAdapter(List<User> userList){
        mUserList = userList;
    }
    @Override
    public int getCount() {
        return mUserList==null?0:mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return (mUserList==null||position>=mUserList.size())?null:mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_live_item,null);
            holder = new ViewHolder();
            holder.userName = (TextView)convertView.findViewById(R.id.userName);
            holder.userId = (TextView)convertView.findViewById(R.id.userId);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        User user = mUserList.get(position);
        holder.userName.setText(user.getmUserName());
        holder.userId.setText(user.getmAccountID());
        return convertView;
    }
    class ViewHolder{
        TextView userName;
        TextView userId;
    }
}
