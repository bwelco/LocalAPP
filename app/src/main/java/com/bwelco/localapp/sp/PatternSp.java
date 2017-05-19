package com.bwelco.localapp.sp;

import android.app.Activity;
import android.content.SharedPreferences;

import com.bwelco.localapp.MyAPP;
import com.google.gson.Gson;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by bwelco on 2017/5/19.
 */

public class PatternSp {

    public static final String PATTERN_TABLE = "pattern_table";
    public static final String PATTERN_TABLE_KEY = "pattern_table_key";

    static class PatternKey {
        String userName;
        String patternSha1;
    }

    public static void saveUser(String userName, List<PatternView.Cell> pattern) {
        SharedPreferences sharedPreferences =
                MyAPP.application.getSharedPreferences(PATTERN_TABLE, Activity.MODE_PRIVATE);
        PatternKey patternKey = new PatternKey();
        patternKey.patternSha1 = PatternUtils.patternToSha1String(pattern);
        patternKey.userName = userName;
        sharedPreferences.edit().putString(PATTERN_TABLE_KEY,
                new Gson().toJson(patternKey)).apply();
    }

    public static String getUserPattern(String userName) {
        SharedPreferences sharedPreferences =
                MyAPP.application.getSharedPreferences(PATTERN_TABLE, Activity.MODE_PRIVATE);
        PatternKey patternKey = new Gson().fromJson(
                sharedPreferences.getString(PATTERN_TABLE_KEY, ""),
                PatternKey.class);
        if (patternKey == null) {
            return null;
        }
        return patternKey.patternSha1;
    }
}
