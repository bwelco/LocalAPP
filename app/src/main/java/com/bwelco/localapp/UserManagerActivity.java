package com.bwelco.localapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.bwelco.localapp.adapter.UserListAdapter;
import com.bwelco.localapp.bean.UserBean;
import com.bwelco.localapp.http.UserManagerService;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagerActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.user_list)
    ListView userListView;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    UserListAdapter listAdapter;
    UserManagerService userManagerService;
    List<UserBean> userBeanList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        userManagerService = HttpUtil.getRetrofitInstance().
                create(UserManagerService.class);
        userBeanList = new ArrayList<>();
        listAdapter = new UserListAdapter(this, R.layout.item_user,
                userBeanList);
        userListView.setAdapter(listAdapter);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
    }


    @Override
    public int getLayoutID() {
        return R.layout.activity_user_manager;
    }

    @Override
    public String toolBarTitle() {
        return "用户管理";
    }


    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public void onRefresh() {
        userManagerService.getUserList()
                .enqueue(new Callback<List<UserBean>>() {
                    @Override
                    public void onResponse(Call<List<UserBean>> call, Response<List<UserBean>> response) {
                        userBeanList.clear();
                        userBeanList.addAll(response.body());
                        listAdapter.notifyDataSetChanged();
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserBean>> call, Throwable t) {
                        ToastUtil.showMessage("拉取失败");
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        t.printStackTrace();
                    }
                });
    }
}
