package com.bwelco.localapp;

import android.os.Bundle;

public class DefaultPasswdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_default_passwd;
    }

    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public String toolBarTitle() {
        return "默认管理员密码";
    }
}
