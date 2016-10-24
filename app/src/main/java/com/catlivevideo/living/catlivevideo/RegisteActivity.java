package com.catlivevideo.living.catlivevideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.catlivevideo.living.catlivevideo.bean.ResponseObject;
import com.catlivevideo.living.catlivevideo.bean.Url;
import com.catlivevideo.living.catlivevideo.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisteActivity extends AppCompatActivity {
    private TextView mReg_back;
    private EditText mUserName;
    private EditText mPwd;
    private EditText mRe_pwd;
    private Button mReg_btn;
    private View.OnClickListener mOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        init();
        mReg_btn.setOnClickListener(mOnClickListener);
        mReg_back.setOnClickListener(mOnClickListener);
    }
    private void init(){
        mReg_back = (TextView)findViewById(R.id.reg_back);
        mUserName = (EditText)findViewById(R.id.userName);
        mPwd = (EditText)findViewById(R.id.pwd);
        mRe_pwd = (EditText)findViewById(R.id.re_pwd);
        mReg_btn = (Button)findViewById(R.id.reg_btn);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.reg_back :
                        finish();
                        break;
                    case R.id.reg_btn :
                        register();
                        break;
                }
            }
        };
    }
    private void register(){
        String userName = mUserName.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        String re_pwd = mRe_pwd.getText().toString().trim();
        if (userName.length() == 0) {
            mUserName.setError(getString(R.string.usernameNotEmpty));
            mUserName.requestFocus();
            return;
        }
        if(pwd.length() == 0){
            mPwd.setError(getString(R.string.pwdNotEmpty));
            mPwd.requestFocus();
            return;
        }
        if(!pwd.equalsIgnoreCase(re_pwd)){
            mRe_pwd.setError(getString(R.string.pwdNotSame));
            mRe_pwd.requestFocus();
            return;
        }
        RequestParams params = new RequestParams(Url.REGISTE_PATH);
        params.addQueryStringParameter("userName", userName);
        params.addQueryStringParameter("pwd",pwd);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ResponseObject<User> responseObject = new Gson().fromJson(result,new TypeToken< ResponseObject<User>>(){}.getType());
                    if(responseObject.getState() == 1){
                        Toast.makeText(RegisteActivity.this, R.string.reg_success, Toast.LENGTH_SHORT).show();
                        RegisteActivity.this.finish();
                    }else{
                        Toast.makeText(RegisteActivity.this, R.string.userNameIsExist, Toast.LENGTH_SHORT).show();
                        RegisteActivity.this.mPwd.setText("");
                        RegisteActivity.this.mRe_pwd.setText("");
                        mUserName.requestFocus();
                    }
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), R.string.internet_erro, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }

        });
    }
}
