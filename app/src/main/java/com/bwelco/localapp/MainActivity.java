package com.bwelco.localapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwelco.localapp.NFC.NfcIniter;

public class MainActivity extends AppCompatActivity {

    NfcIniter nfcIniter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcIniter = new NfcIniter(this);
    }
}
