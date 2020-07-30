package com.anwahh.designitandvideomenu.commonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @describe 通用提示消息类
 * @author Anwahh
 * @date 2020-04-10
 */
public class ShowToastUtils {

    private static Toast mToast;

    public static void ShowToast(Context mcontext, String text, int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(mcontext, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void ShowAlertDialog(final Activity mcontext, String msg, float time) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mcontext).setTitle("提示").setMessage(msg).show();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mcontext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.hide();
                        alertDialog.dismiss();

                        timer.cancel();
                    }
                });

            }
        }, (int)(time*1000));
    }
}
