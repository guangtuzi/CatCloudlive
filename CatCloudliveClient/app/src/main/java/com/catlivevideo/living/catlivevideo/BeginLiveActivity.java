package com.catlivevideo.living.catlivevideo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.catlivevideo.living.catlivevideo.bean.Url;
import com.catlivevideo.living.catlivevideo.utlis.PreferenceUtil;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncoder;
import net.ossrs.yasea.SrsMp4Muxer;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.rtmp.RtmpPublisher;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Random;

public class BeginLiveActivity extends Activity {
    public static final int CAMERA_REQ_CODE = 10;
    public static final int RECORD_REQ_CODE = 11;
    public static final int WRITE_REQ_CODE = 12;
    public static final int REQ_CODE = 13;
    private static final String TAG = "Yasea";

    Button btnPublish = null;
    Button btnSwitchCamera = null;
    Button btnRecord = null;
    Button btnSwitchEncoder = null;
    EditText efu;
    //private SharedPreferences sp;
    //private String rtmpUrl = "rtmp://0.0.0.0/" + getRandomAlphaString(3) + '/' + getRandomAlphaDigitString(5);
    private String rtmpUrl = "rtmp://ossrs.net:1935/pan/01";
    private String recPath = Environment.getExternalStorageDirectory().getPath() + "/test.mp4";
    private SrsPublisher mPublisher;
    private String mAccountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_beginlive);
        mAccountId = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","AccountID","",this);
        // response screen rotation event
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        // restore data.
       // sp = getSharedPreferences("Yasea", MODE_PRIVATE);
       // rtmpUrl = sp.getString("rtmpUrl", rtmpUrl);
        String accountID = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","AccountID","",BeginLiveActivity.this);
        String userName = PreferenceUtil.getString("com.catlivevideo.living.catlivevideo","USERNAME","",BeginLiveActivity.this);
        rtmpUrl = "rtmp://ossrs.net:1935/"+userName+"/"+accountID;
        // initialize url.
        efu = (EditText) findViewById(R.id.url);
        efu.setText(rtmpUrl);

        btnPublish = (Button) findViewById(R.id.publish);
        btnSwitchCamera = (Button) findViewById(R.id.swCam);
        btnRecord = (Button) findViewById(R.id.record);
        btnSwitchEncoder = (Button) findViewById(R.id.swEnc);
        mPublisher = new SrsPublisher((SrsCameraView) findViewById(R.id.preview));

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnPublish.getText().toString().contentEquals("publish")) {
                    setLive(mAccountId,"1");
                    rtmpUrl = efu.getText().toString();
                    Log.i(TAG, String.format("RTMP URL changed to %s", rtmpUrl));
                   /* SharedPreferences.Editor editor = sp.edit();
                    editor.putString("rtmpUrl", rtmpUrl);
                    editor.apply();*/

                    mPublisher.setPreviewResolution(1280, 720);
                    mPublisher.setOutputResolution(384, 640);
                    mPublisher.setVideoSmoothMode();
                    mPublisher.startPublish(rtmpUrl);

                    if (btnSwitchEncoder.getText().toString().contentEquals("soft enc")) {
                       // Toast.makeText(getApplicationContext(), "Use hard encoder", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Use soft encoder", Toast.LENGTH_SHORT).show();
                    }
                    btnPublish.setText("stop");
                    btnSwitchEncoder.setEnabled(false);
                } else if (btnPublish.getText().toString().contentEquals("stop")) {
                    setLive(mAccountId,"0");
                    finish();
                   /* mPublisher.stopPublish();
                    mPublisher.stopRecord();
                    btnPublish.setText("publish");
                    btnRecord.setText("record");
                    btnSwitchEncoder.setEnabled(true);*/
                }
            }
        });

        btnSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Camera.getNumberOfCameras() > 0) {
                    mPublisher.switchCameraFace((mPublisher.getCamraId() + 1) % Camera.getNumberOfCameras());
                }
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRecord.getText().toString().contentEquals("record")) {
                    mPublisher.startRecord(recPath);
                    btnRecord.setText("pause");
                } else if (btnRecord.getText().toString().contentEquals("pause")) {
                    mPublisher.pauseRecord();
                    btnRecord.setText("resume");
                } else if (btnRecord.getText().toString().contentEquals("resume")) {
                    mPublisher.resumeRecord();
                    btnRecord.setText("pause");
                }
            }
        });

        btnSwitchEncoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSwitchEncoder.getText().toString().contentEquals("soft enc")) {
                    mPublisher.swithToSoftEncoder();
                    btnSwitchEncoder.setText("hard enc");
                } else if (btnSwitchEncoder.getText().toString().contentEquals("hard enc")) {
                    mPublisher.swithToHardEncoder();
                    btnSwitchEncoder.setText("soft enc");
                }
            }
        });

        mPublisher.setPublishEventHandler(new RtmpPublisher.EventHandler() {
            @Override
            public void onRtmpConnecting(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRtmpConnected(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRtmpVideoStreaming(final String msg) {
            }

            @Override
            public void onRtmpAudioStreaming(final String msg) {
            }

            @Override
            public void onRtmpStopped(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRtmpDisconnected(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRtmpOutputFps(final double fps) {
               // Log.i(TAG, String.format("Output Fps: %f", fps));
            }
        });

        mPublisher.setRecordEventHandler(new SrsMp4Muxer.EventHandler() {
            @Override
            public void onRecordPause(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRecordResume(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRecordStarted(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Recording file: " + msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onRecordFinished(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "MP4 file saved: " + msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mPublisher.setNetworkEventHandler(new SrsEncoder.EventHandler() {
            @Override
            public void onNetworkResume(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNetworkWeak(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                final String msg = ex.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        mPublisher.stopPublish();
                        mPublisher.stopRecord();
                        btnPublish.setText("publish");
                        btnRecord.setText("record");
                        btnSwitchEncoder.setEnabled(true);
                    }
                });
            }
        });
    }
    private void requestPermission() {
        int CAMERA_PERMISSION = ContextCompat.checkSelfPermission(BeginLiveActivity.this, Manifest.permission.CAMERA);
        int RECORD_PERMISSION = ContextCompat.checkSelfPermission(BeginLiveActivity.this, Manifest.permission.RECORD_AUDIO);
        int WRITE_PERMISSION =  ContextCompat.checkSelfPermission(BeginLiveActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((CAMERA_PERMISSION != PackageManager.PERMISSION_GRANTED&&RECORD_PERMISSION != PackageManager.PERMISSION_GRANTED&&RECORD_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(BeginLiveActivity.this,new String[] {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_CODE);

        }
     /*   if(RECORD_PERMISSION != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BeginLiveActivity.this,new String[] {Manifest.permission.RECORD_AUDIO},RECORD_REQ_CODE);
        }
        if(WRITE_PERMISSION!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BeginLiveActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_REQ_CODE);
        }*/

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQ_CODE: {

                if(!permissions[0].equals(Manifest.permission.CAMERA)||grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getString(R.string.givepcamerapermission), Toast.LENGTH_SHORT).show();
                }
                if(!permissions[1].equals(Manifest.permission.RECORD_AUDIO)||grantResults[1] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getString(R.string.giverecordpermission), Toast.LENGTH_SHORT).show();
                }
                if(!permissions[2].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)||grantResults[2] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getString(R.string.givewritepermission), Toast.LENGTH_SHORT).show();
                }

            }
           /* case RECORD_REQ_CODE:{
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults.length>0 ) {

                } else {
                    Toast.makeText(this, getString(R.string.giverecordpermission), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case WRITE_REQ_CODE:{
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults.length>0 ) {

                } else {
                    Toast.makeText(this, getString(R.string.givewritepermission), Toast.LENGTH_SHORT).show();
                }
                break;
            }*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
        final Button btn = (Button) findViewById(R.id.publish);
        btn.setEnabled(true);
        mPublisher.resumeRecord();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
    }

 /*   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mPublisher.setPreviewRotation(90);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPublisher.setPreviewRotation(0);
        }
        mPublisher.stopEncode();
        mPublisher.stopRecord();
        btnRecord.setText("record");
        mPublisher.setScreenOrientation(newConfig.orientation);
        if (btnPublish.getText().toString().contentEquals("stop")) {
            mPublisher.startEncode();
        }
    }*/

    private static String getRandomAlphaString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String getRandomAlphaDigitString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    private void setLive(String accountID,String isLiving){
        RequestParams params = new RequestParams(Url.SET_ISLIVE);
        params.addQueryStringParameter("accountID", accountID);
        params.addQueryStringParameter("isLiving", isLiving);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null){
                    Toast.makeText(x.app(), result, Toast.LENGTH_SHORT).show();
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
