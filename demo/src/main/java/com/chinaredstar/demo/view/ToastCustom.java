package com.chinaredstar.demo.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hairui.xiang on 2017/9/15.
 */

public class ToastCustom {
    private WindowManager wdm;
    private double time;
    private View mView;
    private WindowManager.LayoutParams params;
    private Timer timer;

    private ToastCustom(Context context, String text, double time){
        wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        timer = new Timer();

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mView = toast.getView();
        params =  new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = toast.getView().getAnimation().INFINITE;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.y = -30;

        this.time = time;
    }

    public static ToastCustom makeText(Context context, String text, double time){
        ToastCustom toastCustom = new ToastCustom(context, text, time);
        return toastCustom;
    }

    public void show(){
        wdm.addView(mView, params);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                wdm.removeView(mView);
            }
        }, (long)(time * 1000));
    }

    public void cancel(){
        wdm.removeView(mView);
        timer.cancel();
    }

}
