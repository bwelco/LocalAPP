package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bwelco.localapp.NFC.NfcIniter;
import com.bwelco.localapp.http.DoorService;
import com.bwelco.localapp.http.NormalResponse;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.LoginUtil;
import com.bwelco.localapp.utils.ToastUtil;
import com.bwelco.localapp.utils.UserConfig;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoorControllerActicity extends BaseActivity implements NfcIniter.OnTagFoundListener {

    private ProgressDialog progressDialog;
    private NfcIniter nfcIniter;
    private DoorService doorService;


    @Bind(R.id.admin_user)
    TextView adminUser;

    @Bind(R.id.username)
    TextView userName;

    @Bind(R.id.user_container)
    CardView userContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        nfcIniter = new NfcIniter(this);
        nfcIniter.setOnTagFoundListener(this);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.open_door).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFound(false);
            }
        });

        findViewById(R.id.close_door).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DoorController.doAction(DoorControllerActicity.this, false);
            }
        });

        adminUser.setVisibility(UserConfig.isAdmin ? View.VISIBLE : View.GONE);
        userName.setText(UserConfig.username);

        if (UserConfig.isAdmin) {
            userContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoorControllerActicity.this,
                            BackgroundManagerActivity.class));
                }
            });
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LoginUtil.clearUser();
                UserConfig.isAdmin = false;
                UserConfig.passwd = "";
                UserConfig.username = "";
                UserConfig.hasPassed = false;
                startActivity(new Intent(DoorControllerActicity.this, LoginActivity.class));
                finish();
                return true;
            }
        });

        doorService = HttpUtil.getRetrofitInstance().create(DoorService.class);
    }

    @Override
    public String toolBarTitle() {
        return "开锁";
    }

    @Override
    public boolean needBack() {
        return false;
    }

    @Override
    public int getLayoutID() {
        return R.layout.acticity_door_controller;
    }

    @Override
    public void tagFound(final boolean delay) {
        // DoorController.doAction(this, true);
        progressDialog.setMessage("正在开锁中");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (delay) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doorService.openDoor(UserConfig.username, System.currentTimeMillis())
                                .enqueue(new Callback<NormalResponse>() {
                                    @Override
                                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                                        ToastUtil.showMessage("开锁成功");
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                                        ToastUtil.showMessage("开锁失败");
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                });
            }
        });

    }


    public void closeDoor() {
        doorService.closrDoor(UserConfig.username, System.currentTimeMillis())
                .enqueue(new Callback<NormalResponse>() {
                    @Override
                    public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                        ToastUtil.showMessage("成功关锁");
                    }

                    @Override
                    public void onFailure(Call<NormalResponse> call, Throwable t) {
                        ToastUtil.showMessage("关锁失败");
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.door_menu, menu);
        return true;
    }
}
