package com.catlivevideo.living.catlivevideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.catlivevideo.living.catlivevideo.bean.ResponseObject;
import com.catlivevideo.living.catlivevideo.bean.Url;
import com.catlivevideo.living.catlivevideo.bean.User;
import com.catlivevideo.living.catlivevideo.utlis.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.catlivevideo.living.catlivevideo.R.id.login_back;
import static com.catlivevideo.living.catlivevideo.R.id.registe_btn;

public class LoginActivity extends AppCompatActivity {
    private Animation mMove2left;
    private Animation mMove2right;
    private RadioGroup mRg_login;
    private View mFlyView;
    private EditText mUserName;
    private EditText mPwd;
    private Button mVerifyBtn;
    private Button mLogin_btn;
    private TextWatcher mTextWatcher;
    private TextView mRegiste_btn;
    private TextView mLogin_back;
    private View.OnClickListener mClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        mUserName.addTextChangedListener(mTextWatcher);
        mPwd.addTextChangedListener(mTextWatcher);
        mRg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.account_login :
                        mFlyView.startAnimation(mMove2left);
                        mVerifyBtn.setVisibility(View.GONE);
                        break;
                    case R.id.quick_login :
                        mFlyView.startAnimation(mMove2right);
                        mVerifyBtn.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        mRegiste_btn.setOnClickListener(mClickListener);
        mLogin_back.setOnClickListener(mClickListener);
        mLogin_btn.setOnClickListener(mClickListener);
        mVerifyBtn.setOnClickListener(mClickListener);

    }
    private void init(){
        mMove2left = AnimationUtils.loadAnimation(this,R.anim.move2left);
        mMove2right = AnimationUtils.loadAnimation(this,R.anim.move2right);
        mRg_login = (RadioGroup)findViewById(R.id.rg_login);
        mFlyView = findViewById(R.id.flyView);
        mUserName = (EditText) findViewById(R.id.userName);
        mPwd = (EditText)findViewById(R.id.pwd);
        mVerifyBtn = (Button)findViewById(R.id.verify_btn);
        mLogin_btn = (Button)findViewById(R.id.login_btn);
        mRegiste_btn = (TextView)findViewById(registe_btn);
        mLogin_back = (TextView)findViewById(login_back);
        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.login_back :
                        finish();
                        break;
                    case R.id.registe_btn :
                        startActivity(new Intent(LoginActivity.this,RegisteActivity.class));
                        break;
                    case R.id.login_btn :
                        login();
                        break;
                    case R.id.verify_btn:

                        break;
                }
            }
        };
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mUserName.getText().toString().trim().length()>0&&mPwd.getText().toString().trim().length()>0){
                    mLogin_btn.setEnabled(true);
                }else{
                    mLogin_btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
    private void login() {
        final String userName = mUserName.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();
        if (userName.length() == 0) {
            mUserName.setError(getString(R.string.usernameNotEmpty));
            mUserName.requestFocus();
            return;
        }
        if (pwd.length() == 0) {
            mPwd.setError(getString(R.string.pwdNotEmpty));
            mPwd.requestFocus();
            return;
        }
        RequestParams params = new RequestParams(Url.LOGIN_PATH);
        params.addQueryStringParameter("userName", userName);
        params.addQueryStringParameter("pwd", pwd);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    ResponseObject<User> responseObject = new Gson().fromJson(result,new TypeToken< ResponseObject<User>>(){}.getType());
                    User user = (User)responseObject.getObj();
                    if(responseObject.getState() == 1){
                        Toast.makeText(x.app(), R.string.login_success, Toast.LENGTH_SHORT).show();
                        PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","USERNAME",userName,LoginActivity.this);
                        if(user!=null){
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","AccountID",user.getmAccountID(),LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","User_icon",user.getmUser_icon(),LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","Lived_count",user.getmLived_count()+"",LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","Fans_count",user.getmFans_count()+"",LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","Fav_count",user.getmFav_count()+"",LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","Money_count",user.getmMoney_count()+"",LoginActivity.this);
                            PreferenceUtil.putString("com.catlivevideo.living.catlivevideo","Grade_count",user.getmGrade_count()+"",LoginActivity.this);
                            PreferenceUtil.putBoolean("com.catlivevideo.living.catlivevideo","IsLiving",user.getmIsLiving(),LoginActivity.this);
                        }
                        Intent intent = new Intent(LoginActivity.this, BeginToLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
                    }else{
                        Toast.makeText(x.app(), R.string.pwd_name_erro, Toast.LENGTH_SHORT).show();
                        LoginActivity.this.mPwd.setText("");
                        LoginActivity.this.mUserName.setText("");
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
