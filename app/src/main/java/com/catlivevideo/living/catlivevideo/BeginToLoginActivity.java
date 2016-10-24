package com.catlivevideo.living.catlivevideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.catlivevideo.living.catlivevideo.utlis.PreferenceUtil;


public class BeginToLoginActivity extends AppCompatActivity {
    private Button mStartTologin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userName = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","USERNAME","",this);
        if(!userName.equalsIgnoreCase("")){
            startActivity(new Intent(this,HomeActivity.class));
            this.finish();
        }
        setContentView(R.layout.activity_begintologin);
        init();
        mStartTologin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeginToLoginActivity.this,LoginActivity.class));
            }
        });

    }
    private void init(){
        mStartTologin_btn = (Button)findViewById(R.id.startTologin_btn);
    }
}

