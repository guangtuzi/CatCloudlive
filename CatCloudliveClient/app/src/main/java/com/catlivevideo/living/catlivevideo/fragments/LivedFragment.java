package com.catlivevideo.living.catlivevideo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.catlivevideo.living.catlivevideo.PlayerLiveActivity;
import com.catlivevideo.living.catlivevideo.R;
import com.catlivevideo.living.catlivevideo.adapters.UserLivingAdapter;
import com.catlivevideo.living.catlivevideo.bean.ResponseObject;
import com.catlivevideo.living.catlivevideo.bean.Url;
import com.catlivevideo.living.catlivevideo.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LivedFragment extends Fragment {
	private PullToRefreshListView mPullToRefreshListView;
	private UserLivingAdapter mAdapter;
	private boolean mIsDown = true;
	private List<User> mUserList = new ArrayList<User>();
	private int mPage = 0;
	private int mSize = 20;
	private long mCount = 0;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View livedView = inflater.inflate(R.layout.activity_tab_lived, container,false);
		initView(livedView);
		mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
		mPullToRefreshListView.setScrollingWhileRefreshingEnabled(true);
		mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mIsDown = true;
				loadDatas(mIsDown);

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				mIsDown = false;
				loadDatas(mIsDown);
			}
		});
		mPullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				Toast.makeText(LivedFragment.this.getContext(), R.string.load_more, Toast.LENGTH_SHORT).show();
			}
		});
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this.getContext());
		soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
		mPullToRefreshListView.setOnPullEventListener(soundListener);
		mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(mUserList!=null){
					User user = (User)parent.getAdapter().getItem(position);
					String rtmpUrl = "rtmp://ossrs.net:1935/"+user.getmUserName()+"/"+user.getmAccountID();
					Intent intent = new Intent(getContext(), PlayerLiveActivity.class);
					intent.putExtra("rtmpUrl",rtmpUrl);
					getActivity().startActivity(intent);
				}
			}
		});
		return livedView;


	}
	private void initView(View view){
		mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.pull_refresh_list);
		new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				mPullToRefreshListView.setRefreshing();
				return false;
			}
		}).sendEmptyMessageDelayed(0,200);


	}
	private void loadDatas(final boolean isDown){
		if(isDown){
			mPage = 0;
		}else{
			mPage++;
		}
		RequestParams params = new RequestParams(Url.GET_LIVE_COUNT);
		params.addQueryStringParameter("page", mPage+"");
		params.addQueryStringParameter("size", mSize+"");
		params.addQueryStringParameter("isLive", "0");
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if(result!=null){
					ResponseObject<List<User>> responseObject = new Gson().fromJson(result,new TypeToken< ResponseObject<List<User>>>(){}.getType());
					if(responseObject.getState() == 1){
						mPage = responseObject.getPage();
						mSize = responseObject.getSize();
						mCount = responseObject.getCount();
						if(mCount == mPage+1){
							mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
						}
						if(isDown){
							mUserList = (ArrayList<User>)responseObject.getObj();
							mAdapter = new UserLivingAdapter(mUserList);
							mPullToRefreshListView.setAdapter(mAdapter);

						}else{
							mUserList.addAll((ArrayList<User>)responseObject.getObj());
							mAdapter.notifyDataSetChanged();
						}
					}else{
						if(isDown){
							mAdapter = new UserLivingAdapter(new ArrayList<User>());
							mPullToRefreshListView.setAdapter(mAdapter);
						}
						Toast.makeText(x.app(), responseObject.getMsg(), Toast.LENGTH_SHORT).show();
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
				mPullToRefreshListView.onRefreshComplete();
			}

		});



	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
}
