package com.bwelco.localapp.pattern;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bwelco.localapp.DefaultPasswdActivity;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by bwelco on 2017/5/19.
 */

public class SuperPatternActivity extends ConfirmPatternActivity {

    public static final String DEFAULT_PATTERN = "cX6VuJzGU9jod5NI4XnDq+O/VWY=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected boolean isStealthModeEnabled() {
        // TODO: Return the value from SharedPreferences.
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        // TODO: Get saved pattern sha1.
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), DEFAULT_PATTERN);
    }

    @Override
    protected void onForgotPassword() {

        startActivity(new Intent(this, DefaultPasswdActivity.class));

        // Finish with RESULT_FORGOT_PASSWORD.
        // super.onForgotPassword();
    }
}
