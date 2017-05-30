package com.bwelco.localapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bwelco.localapp.utils.IPConfigUtil;
import com.bwelco.localapp.utils.KeyBoardManager;
import com.bwelco.localapp.utils.ToastUtil;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText editText = (EditText) findViewById(R.id.ip_address);

        findViewById(R.id.save_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPConfigUtil.setIpconfig(SettingsActivity.this,
                        editText.getText().toString());
                KeyBoardManager.closeKeyboard(SettingsActivity.this);

                finish();
                ToastUtil.showMessage("保存成功");
            }
        });
    }
}
