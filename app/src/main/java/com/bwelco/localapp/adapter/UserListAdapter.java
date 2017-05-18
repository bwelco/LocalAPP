package com.bwelco.localapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bwelco.localapp.R;
import com.bwelco.localapp.UserManagerActivity;
import com.bwelco.localapp.bean.UserBean;
import com.bwelco.localapp.http.NormalResponse;
import com.bwelco.localapp.http.UserManagerService;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bwelco on 2017/5/18.
 */

public class UserListAdapter extends ArrayAdapter<UserBean> {

    Context context;
    LayoutInflater layoutInflater;
    List<UserBean> userList;
    UserManagerService userManagerService;

    public UserListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.userList = objects;
        this.userManagerService = HttpUtil.getRetrofitInstance().
                create(UserManagerService.class);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        UserBean userBean = userList.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_user, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnLongClickListener(viewHolder);
        viewHolder.setPosition(position);
        viewHolder.agreeButton.setOnClickListener(viewHolder);
        viewHolder.updateToAdmin.setOnClickListener(viewHolder);
        viewHolder.avatarImageView.setTextAndColor(userBean.username.substring(0, 1),
                AvatarImageView.COLORS[position % AvatarImageView.COLORS.length]);

        if (userBean.isAdmin) {
            viewHolder.updateToAdmin.setVisibility(View.GONE);
            viewHolder.agreeButton.setVisibility(View.GONE);
            viewHolder.detail.setText("管理员:" + userBean.username);
        } else {
            if (userBean.hasPassed) {
                viewHolder.updateToAdmin.setVisibility(View.VISIBLE);
                viewHolder.agreeButton.setVisibility(View.GONE);
                viewHolder.detail.setText("非管理员:" + userBean.username);
            } else {
                viewHolder.updateToAdmin.setVisibility(View.GONE);
                viewHolder.agreeButton.setVisibility(View.VISIBLE);
                viewHolder.detail.setText(userBean.username + "申请注册，理由是：" +
                        userBean.applyReason);
            }
        }

        return convertView;

    }

    class ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView detail;
        Button agreeButton;
        Button updateToAdmin;

        AvatarImageView avatarImageView;
        int position;

        public void setPosition(int position) {
            this.position = position;
        }

        ViewHolder(View view) {
            this.detail = (TextView) view.findViewById(R.id.detail);
            this.agreeButton = (Button) view.findViewById(R.id.agree);
            this.updateToAdmin = (Button) view.findViewById(R.id.update_to_admin);
            this.avatarImageView = (AvatarImageView) view.findViewById(R.id.item_avatar);
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.agree) {
                ToastUtil.showMessage("同意");
                userManagerService.
                        agreeUserApply(userList.get(position).username).
                        enqueue(new Callback<NormalResponse>() {
                            @Override
                            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                                ToastUtil.showMessage("成功同意");
                                ((UserManagerActivity) context).onRefresh();
                            }

                            @Override
                            public void onFailure(Call<NormalResponse> call, Throwable t) {
                                ToastUtil.showMessage("同意失败");
                            }
                        });
            } else if (id == R.id.update_to_admin) {
                userManagerService.updateUserToAdmin(userList.get(position).username)
                        .enqueue(new Callback<NormalResponse>() {
                            @Override
                            public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                                ToastUtil.showMessage("升级为管理员成功");
                                ((UserManagerActivity) context).onRefresh();
                            }

                            @Override
                            public void onFailure(Call<NormalResponse> call, Throwable t) {
                                ToastUtil.showMessage("升级为管理员失败");
                            }
                        });
            }
        }

        @Override
        public boolean onLongClick(View v) {
            final CharSequence[] delete = new String[]{"删除"};
            new AlertDialog.Builder(context)
                    .setItems(delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userManagerService.deleteUser(userList.get(position).username)
                                    .enqueue(new Callback<NormalResponse>() {
                                        @Override
                                        public void onResponse(Call<NormalResponse> call, Response<NormalResponse> response) {
                                            ToastUtil.showMessage("删除成功");
                                            ((UserManagerActivity) context).onRefresh();
                                        }

                                        @Override
                                        public void onFailure(Call<NormalResponse> call, Throwable t) {
                                            ToastUtil.showMessage("删除失败");
                                            t.printStackTrace();
                                        }
                                    });
                        }
                    }).show();

            return true;
        }
    }
}
