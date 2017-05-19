package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bwelco.localapp.pattern.SetPatternActivity;
import com.bwelco.localapp.utils.KeyBoardManager;
import com.bwelco.localapp.utils.LoginUtil;
import com.bwelco.localapp.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SET_LOCK = 1;

    @Bind(R.id.phone)
    EditText userNameTextView;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.regist)
    TextView register;
    @Bind(R.id.background_manager)
    TextView backgroundManager;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
    }

    @Override
    public boolean needBack() {
        return false;
    }

    @Override
    public String toolBarTitle() {
        return "登录";
    }


    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.login, R.id.regist, R.id.background_manager})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:
                if (userNameTextView.length() == 0) {
                    userNameTextView.setError("请输入账号");
                    userNameTextView.requestFocus();
                    return;
                } else if (password.length() == 0) {
                    password.setError("请输入密码");
                    password.requestFocus();
                    return;
                }

                dialog.setMessage("正在登录中");
                dialog.setCancelable(true);
                dialog.show();
                LoginUtil.sendLogin(userNameTextView.getText().toString(),
                        password.getText().toString(), new LoginUtil.LoginCallBack() {

                            @Override
                            public void loginSuccess() {
                                dialog.dismiss();
                                ToastUtil.showMessage("登录成功");
                                LoginUtil.saveUser(LoginActivity.this,
                                        userNameTextView.getText().toString(),
                                        password.getText().toString());
                                KeyBoardManager.closeKeyboard(LoginActivity.this);
                                Intent i = new Intent(LoginActivity.this, SetPatternActivity.class);
                                i.putExtra(SetPatternActivity.USERNAME_EXTRA, userNameTextView.getText().toString());
                                startActivityForResult(i, REQUEST_SET_LOCK);
                            }

                            @Override
                            public void loginFail() {
                                dialog.dismiss();
                                ToastUtil.showMessage("登录失败");
                                KeyBoardManager.closeKeyboard(LoginActivity.this);
                            }
                        });
                break;
            case R.id.regist:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.background_manager:
                Intent intent1 = new Intent(LoginActivity.this,
                        BackgroundManagerActivity.class);
                startActivity(intent1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SET_LOCK) {
            startActivity(new Intent(this, DoorControllerActicity.class));
        }
    }
}
