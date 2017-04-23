package com.bwelco.localapp.NFC;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by bwelco on 2017/4/23.
 */

public class NfcIniter {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    private String[][] techList;

    private Activity activity;

    public NfcIniter(Activity context) {
        this.activity = context;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        checkNFCFunction();
    }

    public void checkNFCFunction() {
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                new AlertDialog.Builder(activity)
                        .setTitle("NFC没有打开")
                        .setMessage("去设置打开?")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                }).show();
            } else {
                setNFCForeground();
            }
        } else {
            new AlertDialog.Builder(activity).setTitle("本机不支持NFC")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    }).show();
        }
    }

    public void setNFCForeground() {
        pendingIntent = PendingIntent.getActivity(activity, 0,
                new Intent(activity.getApplicationContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        intentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)};
        techList = new String[][]{new String[]{}};
        setReaderMode();
    }

    public void setReaderMode() {
        if (nfcAdapter != null) {
            nfcAdapter.enableReaderMode(activity, new NfcAdapter.ReaderCallback() {
                @Override
                public void onTagDiscovered(Tag tag) {
                    Log.i("admin", "tag discover");
                }
            }, NfcAdapter.FLAG_READER_NFC_A, Bundle.EMPTY);
        }
    }
}
