package com.wangheart.ffmpegdemo.ffmpeg;

import android.content.Context;

/**
 * Author : eric
 * CreateDate : 2017/11/1  15:32
 * Email : ericli_wang@163.com
 * Version : 2.0
 * Desc :
 * Modified :
 */

public class FFmpegHandle {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("ffmpeg-handle");
    }
    public static native int setCallback(PushCallback pushCallback);
    public static native int pushRtmpFile(String path,String outUrl);
}
