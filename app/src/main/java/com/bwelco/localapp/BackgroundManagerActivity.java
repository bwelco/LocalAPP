package com.bwelco.localapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackgroundManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_bakground_manager;
    }

    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public String toolBarTitle() {
        return "后台管理";
    }

    @OnClick({R.id.open_door, R.id.user_manager, R.id.warning})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_door) {

        } else if (id == R.id.warning) {

        } else if (id == R.id.user_manager) {
            Intent intent = new Intent(BackgroundManagerActivity.this,
                    UserManagerActivity.class);
            startActivity(intent);
        }
    }
}
