package com.catlivevideo.living.catlivevideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.catlivevideo.living.catlivevideo.adapters.FragmentAdapter;
import com.catlivevideo.living.catlivevideo.fragments.AllLiveFragment;
import com.catlivevideo.living.catlivevideo.fragments.LivedFragment;
import com.catlivevideo.living.catlivevideo.fragments.LivingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.catlivevideo.living.catlivevideo.R.id.pager;

public class HomeActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mPager;
    private ProgressBar mPb;
    private RadioGroup mTopSelectBar;
    private TextView mHome;
    private TextView mBeginLive;
    private TextView mMy;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private AllLiveFragment mAllLiveFragment;
    private LivedFragment mLivedFragment;
    private LivingFragment mLivingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        mTopSelectBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.living :  mPager.setCurrentItem(0);break;
                    case R.id.lived :  mPager.setCurrentItem(1);break;
                    case R.id.alllive :  mPager.setCurrentItem(2);break;

                }

            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0 : mTopSelectBar.check(R.id.living);break;
                    case 1 : mTopSelectBar.check(R.id.lived);break;
                    case 2 : mTopSelectBar.check(R.id.alllive);break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ButterKnife.bind(this);
    }

    @OnClick({R.id.home,R.id.beginlive,R.id.my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home : mPager.setCurrentItem(0);break;
            case R.id.beginlive : startActivity(BeginLiveActivity.class); break;
            case R.id.my : startActivity(MyActivity.class);break;

        }
    }
    private void initView(){
        mPager = (ViewPager)findViewById(pager);
        mPb = (ProgressBar)findViewById(R.id.loading_progress);
        mTopSelectBar = (RadioGroup)findViewById(R.id.top_select_bar);
        mHome = (TextView)findViewById(R.id.home);
        mBeginLive = (TextView)findViewById(R.id.beginlive);
        mMy = (TextView)findViewById(R.id.my);
        mAllLiveFragment = new AllLiveFragment();
        mLivedFragment = new LivedFragment();
        mLivingFragment = new LivingFragment();
        mFragmentList.add(mLivingFragment);
        mFragmentList.add(mLivedFragment);
        mFragmentList.add(mAllLiveFragment);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mPager.setAdapter(mFragmentAdapter);
        mPager.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTopSelectBar.check(R.id.living);


    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);
    }
}
