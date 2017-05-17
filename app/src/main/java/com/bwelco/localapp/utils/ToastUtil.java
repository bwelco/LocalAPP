package com.bwelco.localapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bwelco on 2017/5/17.
 */

public class ToastUtil {
    public enum ToastMgr {
        builder;
        private View view;
        private TextView tv;
        private Toast toast;

        /**
         * 初始化Toast
         *
         * @param context
         */
        public void init(Context context) {
            view = LayoutInflater.from(context).inflate(
                    Resources.getSystem().getIdentifier("transient_notification", "layout", "android"),
                    null);
            tv = (TextView) view.findViewById(Resources.getSystem().getIdentifier("message", "id", "android"));
            toast = new Toast(context);
            toast.setView(view);
        }

        /**
         * 显示Toast
         *
         * @param content
         * @param duration Toast持续时间
         */
        public void display(CharSequence content, int duration) {
            if (content.length() != 0) {
                tv.setText(content);
                toast.setDuration(duration);
                toast.show();
            }
        }
    }

    /**
     * 显示toast
     *
     * @param content  内容
     * @param duration 持续时间
     */
    public static void showMessage(String content, int duration) {
        if (content == null) {
            return;
        } else {
            ToastMgr.builder.display(content, duration);
        }
    }

    /**
     * 显示默认持续时间为short的Toast
     *
     * @param content 内容
     */
    public static void showMessage(String content) {
        if (content == null) {
            return;
        } else {
            ToastMgr.builder.display(content, Toast.LENGTH_SHORT);
        }
    }
}
