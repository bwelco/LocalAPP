package com.bwelco.localapp.pattern;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bwelco.localapp.sp.PatternSp;

import java.util.List;

import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by bwelco on 2017/5/19.
 */

public class SetPatternActivity extends me.zhanghai.android.patternlock.SetPatternActivity {


    public static final String USERNAME_EXTRA = "userName_extra";
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra(USERNAME_EXTRA);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternSp.saveUser(userName, pattern);
    }
}
