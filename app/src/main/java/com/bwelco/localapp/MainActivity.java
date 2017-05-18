package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.bwelco.localapp.NFC.NfcIniter;

public class MainActivity extends BaseActivity implements NfcIniter.OnTagFoundListener {

    ProgressDialog progressDialog;
    NfcIniter nfcIniter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nfcIniter = new NfcIniter(this);
        nfcIniter.setOnTagFoundListener(this);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.open_door).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFound();
            }
        });

        findViewById(R.id.close_door).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DoorController.doAction(MainActivity.this, false);
            }
        });
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
        return R.layout.activity_main;
    }

    @Override
    public void tagFound() {
        // DoorController.doAction(this, true);
    }
}
