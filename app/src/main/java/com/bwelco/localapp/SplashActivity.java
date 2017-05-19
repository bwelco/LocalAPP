package com.bwelco.localapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.bwelco.localapp.pattern.UserConfirmPatternActivity;
import com.bwelco.localapp.utils.LoginUtil;

/**
 * Created by bwelco on 2017/5/19.
 */

public class SplashActivity extends AppCompatActivity {

    public static final int REQUEST_UNLOCK = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String username = LoginUtil.getUserName(SplashActivity.this);
                        if (!TextUtils.isEmpty(username)) {
                            Intent intent = new Intent(SplashActivity.this, UserConfirmPatternActivity.class);
                            startActivityForResult(intent, REQUEST_UNLOCK);
                        } else {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UNLOCK) {
            Intent intent = new Intent(SplashActivity.this, DoorControllerActicity.class);
            startActivity(intent);
            finish();
        }
    }
}
