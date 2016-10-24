package com.catlivevideo.living.catlivevideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catlivevideo.living.catlivevideo.utlis.PreferenceUtil;

public class MyActivity extends AppCompatActivity {
    private TextView mMy_back;
    private ImageView mMy_refresh;
    private ImageView mUser_icon;
    private TextView mUserName;
    private TextView mAccountID;
    private ViewGroup mUser_info;
    private Button mLived_count;
    private Button mFans_count;
    private Button mFav_count;
    private ViewGroup mMoney;
    private TextView mMoney_count;
    private ViewGroup mGrade;
    private TextView mGrade_count;
    private ViewGroup mSettings;
    private View.OnClickListener mClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        init();
        loadUserInfo();
        mMy_back.setOnClickListener(mClickListener);
    }
    private void init(){
        mMy_back = (TextView)findViewById(R.id.my_back);
        mMy_refresh = (ImageView)findViewById(R.id.my_refresh);
        mUser_icon = (ImageView)findViewById(R.id.user_icon);
        mUserName = (TextView)findViewById(R.id.userName);
        mAccountID = (TextView)findViewById(R.id.accountID);
        mUser_info = (LinearLayout)findViewById(R.id.user_info);
        mLived_count = (Button)findViewById(R.id.lived_count);
        mFans_count = (Button)findViewById(R.id.fans_count);
        mFav_count = (Button)findViewById(R.id.fav_count);
        mMoney = (LinearLayout)findViewById(R.id.money);
        mMoney_count = (TextView)findViewById(R.id.money_count);
        mGrade = (LinearLayout)findViewById(R.id.grade);
        mGrade_count = (TextView)findViewById(R.id.grade_count);
        mSettings = (LinearLayout)findViewById(R.id.settings);
        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.my_back :
                        finish();
                        break;
                }
            }
        };
    }
    private void loadUserInfo(){
        String userName = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","USERNAME","",this);
        String accountID = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","AccountID","",this);
        String user_icon = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","User_icon","",this);
        String live_count = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","Lived_count",0+"",this);
        String fans_count = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","Fans_count",0+"",this);
        String fav_count = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","Fav_count",0+"",this);
        String money_count = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","Money_count",0+"",this);
        String grade_count = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","Grade_count",1+"",this);
        mUserName.setText(userName);
        mAccountID.setText(accountID);
        mLived_count.setText(getString(R.string.lived_count).substring(0,3)+live_count);
        mFans_count.setText(getString(R.string.fans_count).substring(0,3)+fans_count);
        mFav_count.setText(getString(R.string.fav_count).substring(0,3)+fav_count);
        mMoney_count.setText(money_count);
        mGrade_count.setText(grade_count);
    }
}
