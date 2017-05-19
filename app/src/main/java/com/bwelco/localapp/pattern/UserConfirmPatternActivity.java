package com.bwelco.localapp.pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bwelco.localapp.LoginActivity;
import com.bwelco.localapp.MyAPP;
import com.bwelco.localapp.http.ExceptionService;
import com.bwelco.localapp.http.NormalResponse;
import com.bwelco.localapp.sp.PatternSp;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.LoginUtil;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bwelco on 2017/5/19.
 */

public class UserConfirmPatternActivity extends ConfirmPatternActivity {

    public static final String USERNAME_EXTRA = "username_extra";
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra(USERNAME_EXTRA);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        // TODO: Return the value from SharedPreferences.
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        // TODO: Get saved pattern sha1.
        String patternSha1 = PatternSp.getUserPattern(username);
        boolean correct = TextUtils.equals(PatternUtils.patternToSha1String(pattern), patternSha1);
        if (!correct) {
            HttpUtil.getRetrofitInstance().create(ExceptionService.class)
                    .sendLoginFailException(LoginUtil.getUserName(MyAPP.application),
                            System.currentTimeMillis())
                    .enqueue(new Callback<NormalResponse>() {
                        @Override
                        public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<NormalResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        return correct;
    }

    @Override
    protected void onForgotPassword() {

        startActivity(new Intent(this, LoginActivity.class));

        // Finish with RESULT_FORGOT_PASSWORD.
        super.onForgotPassword();
    }
}
