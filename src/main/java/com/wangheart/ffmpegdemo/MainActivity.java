package com.wangheart.ffmpegdemo;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.wangheart.ffmpegdemo.ffmpeg.FFmpegHandle;
import com.wangheart.ffmpegdemo.ffmpeg.PushCallback;

import java.io.File;

public class MainActivity extends Activity {
    private TextView tvPushInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE
            }, 5);
        }
    }

    private void initView() {
        tvPushInfo = findViewById(R.id.tv_push_info);
    }


    private void initData() {
        int res = FFmpegHandle.setCallback(new PushCallback() {
            @Override
            public void videoCallback(final long pts, final long dts, final long duration, final long index) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(pts == -1){
                            tvPushInfo.setText("播放结束");
                            return ;
                        }
                        tvPushInfo.setText("播放时间："+dts/1000+"秒");
                    }
                });
            }
        });
        log("result " + res);
    }

    public void btnPush(View view) {
        final String path = "/storage/emulated/0/pauseRecordDemo/video/video.mp4";
        final String outUrl = "rtmp://192.168.43.122:1935/zbcs/room";
        File file = new File(path);
        log(path + "  " + file.exists());
        new Thread() {
            @Override
            public void run() {
                super.run();
                int result = FFmpegHandle.pushRtmpFile(path,outUrl);
                log("result " + result);
            }
        }.start();
    }


    public void log(String content) {
        Log.w("eric", content);
    }
}
