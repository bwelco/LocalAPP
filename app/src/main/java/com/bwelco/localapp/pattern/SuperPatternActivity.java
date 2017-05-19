package com.bwelco.localapp.pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bwelco.localapp.DefaultPasswdActivity;
import com.bwelco.localapp.http.ExceptionService;
import com.bwelco.localapp.http.NormalResponse;
import com.bwelco.localapp.utils.HttpUtil;

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

public class SuperPatternActivity extends ConfirmPatternActivity {

    public static final String DEFAULT_PATTERN = "cX6VuJzGU9jod5NI4XnDq+O/VWY=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean isStealthModeEnabled() {
        // TODO: Return the value from SharedPreferences.
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        // TODO: Get saved pattern sha1.
        boolean correct = TextUtils.equals(PatternUtils.patternToSha1String(pattern), DEFAULT_PATTERN);

        if (!correct) {
            HttpUtil.getRetrofitInstance().create(ExceptionService.class)
                    .sendTrySuperAdminFail(System.currentTimeMillis())
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

        startActivity(new Intent(this, DefaultPasswdActivity.class));

        // Finish with RESULT_FORGOT_PASSWORD.
        // super.onForgotPassword();
    }
}
