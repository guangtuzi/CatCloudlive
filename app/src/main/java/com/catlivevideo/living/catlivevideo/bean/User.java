package com.catlivevideo.living.catlivevideo.bean;

/**
 * Created by pan on 2016/10/7.
 */

public class User {
    private String mId;
    private String mUserName;
    private String mPwd;
    private String mAccountID;
    private String mUser_icon;
    private Long mLived_count;
    private Long mFans_count;
    private Long mFav_count;
    private Long mMoney_count;
    private Long mGrade_count;
    private boolean mIsLiving;
	public boolean getmIsLiving() {
		return mIsLiving;
	}

	public void setmIsLiving(boolean mIsLiving) {
		this.mIsLiving = mIsLiving;
	}

	public String getmId() {
        return mId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmPwd() {
        return mPwd;
    }

    public String getmAccountID() {
        return mAccountID;
    }

    public String getmUser_icon() {
        return mUser_icon;
    }

    public Long getmLived_count() {
        return mLived_count;
    }

    public Long getmFans_count() {
        return mFans_count;
    }

    public Long getmFav_count() {
        return mFav_count;
    }

    public Long getmMoney_count() {
        return mMoney_count;
    }

    public Long getmGrade_count() {
        return mGrade_count;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public void setmAccountID(String mAccountID) {
        this.mAccountID = mAccountID;
    }

    public void setmUser_icon(String mUser_icon) {
        this.mUser_icon = mUser_icon;
    }

    public void setmLived_count(Long mLived_count) {
        this.mLived_count = mLived_count;
    }

    public void setmFans_count(Long mFans_count) {
        this.mFans_count = mFans_count;
    }

    public void setmFav_count(Long mFav_count) {
        this.mFav_count = mFav_count;
    }

    public void setmMoney_count(Long mMoney_count) {
        this.mMoney_count = mMoney_count;
    }

    public void setmGrade_count(Long mGrade_count) {
        this.mGrade_count = mGrade_count;
    }
}
