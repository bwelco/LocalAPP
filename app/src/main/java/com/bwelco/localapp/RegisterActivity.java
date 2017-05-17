package com.bwelco.localapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bwelco.localapp.http.LoginService;
import com.bwelco.localapp.http.RegisterResponse;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_user)
    EditText user;

    @Bind(R.id.register_password)
    EditText passwd;

    @Bind(R.id.apply_reason)
    EditText applyInfo;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
    }

    @OnClick({R.id.register})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.register) {

            if (user.length() == 0) {
                user.setError("请输入账号");
                user.requestFocus();
                return;
            } else if (passwd.length() == 0) {
                passwd.setError("请输入密码");
                passwd.requestFocus();
                return;
            }

            dialog.setMessage("正在登录中");
            dialog.setCancelable(true);
            dialog.show();

            HttpUtil.getRetrofitInstance().create(LoginService.class)
                    .sendRegister(user.getText().toString(), passwd.getText().toString(),
                            applyInfo.getText().toString())
                    .enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            dialog.dismiss();
                            if (response != null && response.body() != null) {
                                if (response.body().success) {
                                    AlertDialog.Builder builder = new
                                            AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("注册申请已经发送成功，请进入后台管理查看。");
                                    builder.setTitle("提示");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder.show();
                                } else {
                                    ToastUtil.showMessage("注册失败");
                                }
                            } else {
                                ToastUtil.showMessage("注册失败");
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            ToastUtil.showMessage("注册失败");
                            dialog.dismiss();
                        }
                    });
        }
    }
}
