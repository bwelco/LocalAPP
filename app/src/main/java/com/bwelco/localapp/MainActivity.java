package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bwelco.localapp.NFC.DoorController;
import com.bwelco.localapp.NFC.NfcIniter;

public class MainActivity extends AppCompatActivity implements NfcIniter.OnTagFoundListener {

    ProgressDialog progressDialog;
    NfcIniter nfcIniter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                DoorController.doAction(MainActivity.this, false);
            }
        });
    }

    @Override
    public void tagFound() {
        DoorController.doAction(this, true);
    }
}
